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

    protected int labelcounter = 0;

    private int getLocal(String name){
        for(int i = locals.size() -1; i > 0; i--){
            var c = locals.get(i);
            if(c.getValue0().equals(name)){
                return i;
            }
        }
        return -1;
    }

    private int addLocal(String name){
        locals.add(Pair.with(name,scope));
        return locals.size()-1;
    }
    private void closeScope(){
        var remove = new ArrayList<Pair<String,Integer>>();
        for(var l : locals){
            if(l.getValue1() == scope){
                remove.add(l);
            }
        }
        for(var r : remove){
            locals.remove(r);
        }
        scope--;
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

    private String extractType(String s,boolean type){
        switch(s) {
            case "int":
                return "I";
            case "float":
                return "F";
            case "string":
                if(type)
                    return "Ljava/lang/String;";
                else
                    return "java/lang/String";
            case "bool":
                return "Z";
            case "void":
                return "V";
            default:
                if(type)
                    return "L"+s+";";
                else
                    return s;

        }
    }
    private String extractType(PVal node){
        var last = ((AVal)node).getCallField().getLast();
        return extractType(last);
    }
    private String extractType(PCallField node){
        if(node instanceof ACallCallField){
            var mdcl =(AMethodDcl)((ACallCallField) node).getId().declarationNode;
            return mdcl.getReturntype().toString().trim();
        }
        else if(node instanceof AFieldCallField){
            var dcl = ((AFieldCallField) node).getId().declarationNode;
            if(dcl instanceof ADclStmt){
                return ((ADclStmt) dcl).getType().toString().trim();
            }
            else if(dcl instanceof AForeachStmt){
                return extractType(((AForeachStmt) dcl).getList());
            }
            else if(dcl instanceof AParamDcl){
                return extractType(((AParamDcl) dcl).getType());
            }
        }
        return null;
    }
    private String extractType(PType node){
        if(node instanceof AListType){
            return "java/util/LinkedList";
        }
        else if (node instanceof AVarType){
            return extractType(((AVarType) node).getType().getText().trim(),false);
        }
        else
            return null;
    }

    private String extractPrefix(String s){
        switch(s){
            case "bool":
            case "int":
                return"i";
            case "float":
                return"f";
            default :
                return"a";
        }
    }
    private String extractPrefix(PType node){
        if(node instanceof AListType){
            return "a";
        }
        else if(node instanceof AVarType){
            return extractPrefix(((AVarType) node).getType().getText().trim());
        }
        else
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
        emit(extractType(node.getType().getText(),true));
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
            var ltype = extractType( node.getVar());
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
        if(size > 1){
            // Field
            LHSVisitor(node.getVar());
            node.getExpr().apply(this);
            emit("putfield ");
            var v = ((AVal) node.getVar()).getCallField().get(size-2);
            emit(extractType(v)+"/");

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
            emit(extractPrefix(node.getExpr().type));
            emit("store ");
            // Get variable number
            LHSVisitor(node.getVar());
            emit("\n");
        }
    }

    @Override
    public void caseAVal(AVal node) throws TypeException, SemanticException {
        var cfList = node.getCallField();
        var first = cfList.getFirst();
        if(first instanceof ACallCallField){
            var dcl = ((ACallCallField) first).getId().declarationNode;
            if(dcl instanceof AMethodDcl){
                boolean instance = st.retrieveSymbol(((AMethodDcl) dcl).getName().getText().trim())==null;
                if(instance){
                    // get object reference
                    emit("aload 0\n");
                }
                for(PExpr e : ((ACallCallField) first).getParams()){
                    e.apply(this);
                }
                if(instance){
                    // instance method
                    emit("invokevirtual ");
                }
                else{
                    // static method
                    emit("invokestatic ");

                }
                emit(current + "/" + ((AMethodDcl) dcl).getName().getText() + "(");
                for(PParamDcl p : ((AMethodDcl) dcl).getParams()){
                    ((AParamDcl)p).getType().apply(this);
                }
                emit(")");
                ((AMethodDcl) dcl).getReturntype().apply(this);
                emit("\n");
            }
            else if(dcl instanceof AConstruct){
                String type = ((AConstruct) dcl).getName().getText().trim();
                emit("new " + type + "\n"+
                        "dup\n");
                for(PExpr e : ((ACallCallField) first).getParams()){
                    e.apply(this);
                }
                emit("invokespecial " + type + "/<init(");
                for(PParamDcl p : ((AConstruct) dcl).getParams()){
                    ((AParamDcl)p).getType().apply(this);
                }
                emit(")V\n");
            }
            else{
                throw new SemanticException(node,"An unknown error occurred");
            }
        }
        else if(first instanceof AFieldCallField){
            var dcl = ((AFieldCallField) first).getId().declarationNode;
            String type = null;
            if(dcl instanceof ADclStmt){
                if(((ADclStmt) dcl).getType() instanceof AListType){
                    type = "list";
                }
                else{
                    type = ((ADclStmt) dcl).getType().toString().trim();
                }
            }
            else if(dcl instanceof AForeachStmt){
                type = ((AForeachStmt) dcl).getList().toString();
            }
            else if(dcl instanceof AParamDcl){
                type = extractType(((AParamDcl) dcl).getType());
            }
            else if(node.type.equals("null")){
                emit("aconst_null\n");
            }
            else {
                throw new SemanticException(node,"An unknown error occurred");
            }
            if(type != null) {
                emit(extractPrefix(type) + "load " +
                        getLocal(((AFieldCallField) first).getId().getText().trim()));
            }
        }
        for(int i = 1; i < cfList.size(); i++){
            cfList.get(i).apply(this);
        }

    }

    @Override
    public void caseADclStmt(ADclStmt node) throws TypeException, SemanticException {
        for(PSingleDcl s :node.getDcls()){
            var dcl = (ASingleDcl)s;
            if(dcl.getExpr() != null){
                dcl.getExpr().apply(this);
            }
            else{
                String type = extractType(node.getType());
                switch(type){
                    case "I": case "Z":
                        emit("iconst_0\n");
                        break;
                    case "F":
                        emit("fconst_0\n");
                        break;
                    case "java/lang/String":
                        emit("ldc \"\"\n");
                        break;
                    case "java/util/LinkedList":
                        emit("new java/util/LinkedList\n"+
                                "dup\n" +
                                "invokespecial java/util/LinkedList/<init>()V\n");
                        break;
                    default :
                        emit("new " + type + "\n");
                        break;
                }
            }

            emit(extractPrefix(node.getType()) + "store " +
                    addLocal(((ASingleDcl)s).getId().getText().trim()) + "\n");
        }
    }

    @Override
    public void caseAIfStmt(AIfStmt node) throws TypeException, SemanticException {
        // put predicate result on top of stack
        node.getPredicate().apply(this);
        // jump if true
        var truelbl = ++labelcounter;
        emit("ifgt true"+ truelbl + "\n");
        var elseifs = node.getElseifs();
        int[] elselbl = new int[elseifs.size()];

        for(int i = 0; i < elseifs.size(); i++){
            elselbl[i] = ++labelcounter;
            ((AElseIf)elseifs.get(i)).getPredicate().apply(this);
            emit("ifgt else" + labelcounter + "\n");
        }

        scope++;
        for(PStmt s : node.getElse()){
            s.apply(this);
        }
        closeScope();
        emit("goto done" + ++labelcounter +"\n");

        scope++;
        emit("true" + truelbl + ":\n");
        for(PStmt s : node.getThen()){
            s.apply(this);
        }
        closeScope();
        emit("goto done" + labelcounter + "\n");

        for(int i = 0; i < elseifs.size(); i++){
            scope++;
            emit("else" + elselbl[i] + ":\n");
            for(PStmt s : ((AElseIf)elseifs.get(i)).getThen()){
                s.apply(this);
            }
            closeScope();
            emit("goto done" + labelcounter + "\n");
        }

        emit("done" + labelcounter + ":\n");



    }

    @Override
    public void caseACallStmt(ACallStmt node) throws TypeException, SemanticException {
        node.getVal().apply(this);
    }

    @Override
    public void caseAForStmt(AForStmt node) throws TypeException, SemanticException {
        scope++;
        node.getInit().apply(this);
        String loopLbl = "loop" + ++labelcounter;
        emit(loopLbl + ":\n" );
        node.getPredicate().apply(this);
        emit("ifeq done" + labelcounter + "\n");
        for(PStmt s : node.getThen()){
            s.apply(this);
        }
        node.getUpdate().apply(this);
        emit("goto " + loopLbl + "\n");
        emit("done" + labelcounter + ":\n");
        closeScope();
    }

    @Override
    public void caseAWhileStmt(AWhileStmt node) throws TypeException, SemanticException {
        scope++;
        String loopLbl = "loop" + ++labelcounter;
        emit(loopLbl + ":\n");
        node.getPredicate().apply(this);
        emit("ifeq done" + labelcounter + "\n");
        for(PStmt s : node.getThen()){
            s.apply(this);
        }
        emit("goto " + loopLbl + "\n");
        emit("done" + labelcounter + ":\n");
        closeScope();
    }

    @Override
    public void caseAForeachStmt(AForeachStmt node) throws TypeException, SemanticException {
        scope++;
        // initialise iteration variable
        emit("iconst_0\n" +
                "loop"+ ++labelcounter + ":\n" +
                "dup\n");
        // get list reference
        node.getList().apply(this);
        // retrieve item from list
        emit("dup2\n" +
                "invokevirtual java/util/LinkedList/size()I\n" +
                "isub\n" +
                "ifgt done" + labelcounter + "\n" +
                "swap\n" +
                "invokevirtual java/util/LinkedList/get(I)Ljava/lang/Object;\n"+
                "checkcast "
                );
        // retrieve list type
        var listType = node.getList().type.split(" ");
        // cast list element to type
        switch(listType[2]){
            case "int":
                emit("java/lang/Integer\n"+
                        "invokevirtual java/lang/Integer/intValue()I" +
                        "istore");
                break;
            case "float":
                emit("java/lang/Float\n"+
                        "invokevirtual java/lang/Float/floatValue()F"+
                        "fstore");
                break;
            case "string":
                emit("java/lang/String\n"+
                        "astore");
                break;
            case "list":
                emit("java/util/LinkedList\n"+
                        "astore");
                break;
            default:
                emit(listType[2] + "\n" +
                        "astore");
                break;
        }
        // get local variable number
        emit(getLocal(node.getId().getText()) + "\n");

        // emit loop body
        for(PStmt s : node.getThen()){
            s.apply(this);
        }

        // increment iteration variable
        emit("iconst_1\n" +
                "iadd\n");
        //jump to loop start and done label
        emit("goto loop" + labelcounter + "\n" +
                "done" + labelcounter +":\n");

        closeScope();
    }

    @Override
    public void caseAReturnStmt(AReturnStmt node) throws TypeException, SemanticException {
        node.getExpr().apply(this);
        emit(extractPrefix(node.getExpr().type)+ "return\n");
    }

    @Override
    public void caseASwitchStmt(ASwitchStmt node) throws TypeException, SemanticException {
        var caseList = node.getCases();
        var type = extractType(node.getVariable());
        // put switch variable on top of stack
        node.getVariable().apply(this);
        ADefaultCase def = new ADefaultCase();
        var labelOffset = labelcounter +1;
        // Compare to each case
        for(PCase c : caseList){
            if(c instanceof ACaseCase){
                emit("dup\n");
                ((ACaseCase) c).getCase().apply(this);
                switch(type){
                    case "int":
                        emit("isub\n");
                        break;
                    case "float":
                        emit("fsub\n"+
                                "f2i\n");
                        break;
                    case "string":
                        emit("invokevirtual java/lang/String/equals(Ljava/lang/Object)Z\n"+
                                "iconst_1\n"+
                                "isub\n");
                    default:
                        throw new SemanticException(node,"An unknown error occured");
                }
                emit("ifeq case" + ++ labelcounter + "\n");
            }
            // save default case to end
            else if(c instanceof ADefaultCase){
                def = (ADefaultCase) c;
            }
            else {
                throw new SemanticException(node,"An unknown error occured");
            }
        }
        // emit default case
        for(PStmt s : def.getThen()){
            s.apply(this);
        }
        emit("goto done" + labelOffset + "\n");
        // emit each case body
        for(int i = 0; i < caseList.size(); i++){
            PCase c = caseList.get(i);
            if(! (c instanceof ADefaultCase)){
                scope++;
                emit("case" + (labelOffset+i) + ":\n");
                for(PStmt s : ((ACaseCase)c).getThen()){
                    s.apply(this);
                }
                emit("goto done" + labelOffset + "\n");
                closeScope();
            }
        }

        emit("done" + labelOffset + ":\n");
    }


}
