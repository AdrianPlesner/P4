package P4.CodeGenarator;

import P4.Sable.analysis.DepthFirstAdapter;
import P4.Sable.node.*;
import P4.contextualAnalysis.SymbolTable;
import P4.contextualAnalysis.TypeException;
import org.javatuples.Pair;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;


public class CodeGenerator extends DepthFirstAdapter {

    private Start ast;
    protected SymbolTable st;

    protected String name;
    protected String current;

    protected HashMap<String,String> files = new HashMap<>();

    protected ArrayList<Pair<String,Integer>> locals = new ArrayList<>();
    protected int scope = 0;

    private int labelcounter = 0;

    private int getLocal(String name){
        for(int i = locals.size() -1; i > 0; i--){
            var c = locals.get(i);
            if(c.getValue0().equals(name)){
                return c.getValue1();
            }
        }
        return -1;
    }

    private FieldGenerator fg;
    private MethodGenerator mg;
    private SubClassGenerator sg;

    public CodeGenerator(){}

    public CodeGenerator(Start ast, SymbolTable st, String n) {
        this.ast = ast;
        this.st = st;
        if(n == null){
            name = "Game";
        }
        else{
            name = n;
        }
        files.put(name,"");
        fg = new FieldGenerator(files);
        mg = new MethodGenerator(files,st);
        sg = new SubClassGenerator(files);
    }

    public void generate() throws TypeException, SemanticException {
        ast.apply(this);
    }

    public void writeFiles() throws IOException {
        writeFiles("");
    }

    public final void writeFiles(String path) throws IOException {
        if(path.equals("")){
            path = System.getProperty("user.dir");
        }
        for(var key : files.keySet()){
            File f = new File(path +"/" + key + ".j");

            if(f.exists()){
                if(!f.delete()){
                    throw new IOException("Could not delete " + f.getAbsolutePath());
                }
            }
            if(f.createNewFile()) {

                FileWriter fw = new FileWriter(f);
                fw.write(files.get(key));
                fw.close();
            }
            else{
                throw new IOException("Could not create directory: " + f.getAbsolutePath());
            }
        }
    }

    protected void emit(String file, String s){
        files.put(file,files.get(file).concat(s));
    }
    protected void emit(String s){
        emit(current,s);
    }

    private void LHSVisitor(PVal node) throws SemanticException, TypeException {
        if(node instanceof AVal) {
            AVal val = (AVal) node;
            var cfs = val.getCallField();
            if(cfs.size() == 1){
                var c = cfs.getFirst();
                if(c instanceof ACallCallField) {
                    throw new SemanticException(node, "Function call cannot be on left hand side og assignment");
                }
                //AFieldCallField
                emit(""+getLocal(((AFieldCallField)c).getId().getText()));
            }
            else {
                for (int i = 0; i < cfs.size() - 1; i++) {
                    cfs.get(i).apply(this);
                }
            }
        }
        else{
            throw new SemanticException(node,"An unknown error occurred");
        }

    }

    private String extractType(String s){
        switch(s) {
            case "int":
                return "I";
            case "float":
                return "F";
            case "string":
                return "Ljava/lang/String;";
            case "bool":
                return "Z";
            case "void":
                return "V";
            default:
                    return "L"+s+";";

        }
    }
    private String extractType(AVal node){
        var last = node.getCallField().getLast();
        if(last instanceof ACallCallField){
            var mdcl =(AMethodDcl)((ACallCallField) last).getId().declarationNode;
            return mdcl.getReturntype().toString().trim();
        }
        else if(last instanceof AFieldCallField){
            var dcl = ((AFieldCallField) last).getId().declarationNode;
            if(dcl instanceof ADclStmt){
                return ((ADclStmt) dcl).getType().toString().trim();
            }
            else if(dcl instanceof AForeachStmt){
                return extractType((AVal) ((AForeachStmt) dcl).getList());
            }
        }
        return null;
    }

    @Override
    public void inAProg(AProg node) {
        String s = ".class " + name + "\n"
                + ".super java/lang/Object\n"
                + ".method public <init>()V\n"
                    + "aload_0\n"
                    + "invokeonvirtual java/lang/Object/<init>()V\n"
                    + "return\n"
                + ".end method\n\n";
        emit(name,s);
        files.put("player","");
        s = ".class player\n"
            + ".super java/lang/Object\n";
        emit("player",s);
        files.put("card","");
        s = ".class card\n"
            + ".super java/Lang/Object\n";
        emit("card",s);
        s = ".method public static main([Ljava/lang/String)V\n";
        emit(name,s);
    }

    @Override
    public void caseAProg(AProg node) throws TypeException, SemanticException {
        inAProg(node);
        // fields fase
        node.apply(fg);
        // Methods fase
        node.apply(mg);
        // Subclasses fase
        node.apply(sg);
        current = name;
        //todo: emit stack and locals sizes

        node.getSetup().apply(this);

        //TODO: turn - endcondition loop

        // main end
        emit(name,".end method\n\n");
        mg.current = name;
        mg.SetStatic(true);
        for(PMethodDcl s : node.getMethods()){
            s.apply(mg);
        }
        for(PMethodDcl s : node.getMoves()){
            s.apply(mg);
        }
    }

    @Override
    public void caseASetup(ASetup node) throws TypeException, SemanticException {
        for(PStmt s : node.getGame()){
            s.apply(this);
        }
    }

    @Override
    public void caseAVarType(AVarType node) throws TypeException {
        emit(extractType(node.getType().getText()));
    }

    @Override
    public void caseAListType(AListType node) {
        emit("Ljava/util/LinkedList;");
    }

    @Override
    public void caseAAssignStmt(AAssignStmt node) throws TypeException, SemanticException {
        var op = node.getOperation().getText();
        if(!op.equals("==")){
            node.getVar().apply(this);
            node.getExpr().apply(this);
            var ltype = extractType((AVal) node.getVar());
            var rtype = node.getExpr().type;
            String restype = "";
            if(ltype == null || rtype == null){
                throw new SemanticException(node,"An unknown error occurred");
            }
            switch (ltype) {
                case "int":
                    if (!rtype.equals("int")) {
                        if (rtype.equals("float")) {
                            emit("swap\n" +
                                    "i2f\n" +
                                    "swap\n"
                            );
                            restype = "float";
                        } else if (rtype.equals("string")) {
                            restype = "istring";
                        } else {
                            throw new SemanticException(node, "An unknown error occurred");
                        }
                    } else {
                        restype = "int";
                    }
                    break;
                case "float":
                    if (!rtype.equals("float")) {
                        if (rtype.equals("int")) {
                            emit("i2f\n");
                            restype = "float";
                        } else if (rtype.equals("string")) {
                            restype = "fstring";
                        } else {
                            throw new SemanticException(node, "An unknown error occurred");
                        }
                    } else {
                        restype = "float";
                    }
                    break;
                case "string":
                    switch (rtype) {
                        case "string":
                            restype = "string";
                            break;
                        case "int":
                            restype = "stringi";
                            break;
                        case "float":
                            restype = "stringf";
                            break;
                        default:
                            throw new SemanticException(node, "An unknown error occurred");
                    }
                    break;
            }
            switch(op){
                case "+=":
                    switch(restype){
                        case "int":
                            emit("iadd\n");
                            break;
                        case "float":
                            emit("fadd\n");
                            break;
                        case "string":
                            emit("invokevirtual java/lang/String/concat(Ljava/lang/String;)Ljava/lang/String;\n");
                            break;
                        case "istring":
                            emit("swap\n"+
                                    "invokestatic java/lang/String/valueOf(I)Ljava/lang/String;\n"+
                                    "swap"+
                                    "invokevirtual java/lang/String/concat(Ljava/lang/String;)Ljava/lang/String;\n"
                            );
                            break;
                        case "fstring":
                            emit("swap\n"+
                                    "invokestatic java/lang/String/valueOf(F)Ljava/lang/String;\n"+
                                    "swap"+
                                    "invokevirtual java/lang/String/concat(Ljava/lang/String;)Ljava/lang/String;\n"
                            );
                            break;
                        case "stringi":
                            emit("invokestatic java/lang/String/valueOf(I)Ljava/lang/String;\n"+
                                    "invokevirtual java/lang/String/concat(Ljava/lang/String;)Ljava/lang/String;\n"
                            );
                            break;
                        case "stringf":
                            emit("invokestatic java/lang/String/valueOf(F)Ljava/lang/String;\n"+
                                    "invokevirtual java/lang/String/concat(Ljava/lang/String;)Ljava/lang/String;\n"
                            );
                            break;
                        default:
                            throw new SemanticException(node, "An unknown error occurred");
                    }
                    break;
                case "-=":
                    switch(restype){
                        case "int":
                            emit("isub\n");
                            break;
                        case "float":
                            emit("fsub\n");
                            break;
                        default:
                            throw new SemanticException(node, "An unknown error occurred");
                    }
                    break;
                case "*=":
                    switch(restype){
                        case "int":
                            emit("imul\n");
                            break;
                        case "float":
                            emit("fmul\n");
                            break;
                        default:
                            throw new SemanticException(node, "An unknown error occurred");
                    }
                    break;
                case "/=":
                    switch(restype){
                        case "int":
                            emit("idiv\n");
                            break;
                        case "float":
                            emit("fdiv\n");
                            break;
                        default:
                            throw new SemanticException(node, "An unknown error occurred");
                    }
                    break;
                case "%=":
                    if(restype.equals("int")){
                        emit("irem\n");
                    }
                    else{
                        throw new SemanticException(node, "An unknown error occurred");
                    }
                    break;
                case "&=":
                    emit("iadd\n"+
                            "iconst_2\n"+
                            "isub\n"+
                            "ifeq true" + ++labelcounter + "\n" +
                            "iconst_0\n" +
                            "goto done" + labelcounter +"\n"+
                            "true" + labelcounter + ":\n"+
                            "iconst_1\n"+
                            "done" + labelcounter+":\n"
                    );
                    break;
                case "|=":
                    emit("iadd\n"+
                            "ifgt true" + ++labelcounter + "\n"+
                            "iconst_0\n"+
                            "goto done" + labelcounter + "\n" +
                            "true" + labelcounter + ":\n"+
                            "iconst_1\n"+
                            "done" + labelcounter + ":\n"
                    );
                    break;
            }

        }
        int size = ((AVal)node.getVar()).getCallField().size();
        if(size < 1){
            // Field
            LHSVisitor(node.getVar());
            node.getExpr().apply(this);
            emit("putfield ");
            PType type = null;
            var v = ((AVal) node.getVar()).getCallField().get(size-2);
            if(v instanceof ACallCallField){
                // get method declaration node
                var mdcl = (AMethodDcl)((ACallCallField) v).getId().declarationNode;
                // Get return type
                type = mdcl.getReturntype();
            }
            else if(v instanceof AFieldCallField){
                // Get variable declaration
                var dcl = ((AFieldCallField) v).getId().declarationNode;
                if(dcl instanceof ADclStmt) {
                    // Get type of field
                    type =((ADclStmt) dcl).getType();
                }
                else{
                    throw new SemanticException(dcl,"An unknown error occurred");
                }
            }
            //emit class name
            if(type instanceof AListType){
                emit("java/lang/LinkedList");
                //TODO: take er et problem og andre ting
            }
            else if(type instanceof AVarType){
                emit(((AVarType) type).getType().getText());
            }
            emit("/");

            // emit field name
            var field = (AFieldCallField)((AVal) node.getVar()).getCallField().getLast();
            emit(field.getId().getText() + " ");

            // emit field type
            var fdcl = field.getId().declarationNode;
            if(fdcl instanceof ADclStmt){
                ((ADclStmt) fdcl).getType().apply(this);
            }
            else{
                throw new SemanticException(fdcl,"An unknown error occurred");
            }
            emit("\n");
        }
        else{
            // local variable
            node.getExpr().apply(this);
            switch(node.getExpr().type){
                case "bool":
                case "int":
                    emit("i");
                    break;
                case "float":
                    emit("f");
                    break;
                default :
                    emit("a");
                    break;
            }
            emit("store ");
            // Get variable number
            LHSVisitor(node.getVar());
            emit("\n");
        }
    }


}
