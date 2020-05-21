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
    protected Boolean Static = false;

    protected int getLocal(String name){
        for(int i = locals.size()-1; i >= 0; i--){
            var c = locals.get(i);
            if(c.getValue0().equals(name)){
                return i;
            }
        }
        return -1;
    }

    protected int addLocal(String name){
        locals.add(Pair.with(name,scope));
        return locals.size()-1;
    }
    protected void closeScope(){
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

    private String typeOnStack ="";

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
                else if(c instanceof AFieldCallField) {
                    //AFieldCallField
                    if(st.retrieveSymbol(((AFieldCallField) c).getId().getText().trim()) == null) {
                        emit("\t" + extractPrefix(extractClass(c)) + "store " + getLocal(((AFieldCallField) c).getId().getText()) + "\n");
                    }
                    else{
                        emit("\tputstatic " + name +"/" + ((AFieldCallField)c).getId().getText().trim() +
                                " " + extractType(c,true) + "\n"
                        );
                    }
                }
                else{
                    throw new SemanticException(node, "Function call cannot be on left hand side og assignment");
                }
            }
            else {
                firstPCallField(cfs.getFirst());
                for (int i = 1; i < cfs.size() - 1; i++) {
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
            case "element":
                if(type){
                    return "Ljava/lang/Object;";
                }
                return "java/lang/Object";

            default:
                if(type)
                    return "L"+s+";";
                else
                    return s;

        }
    }
    private String extractClass(PVal node){
        var last = ((AVal)node).getCallField().getLast();
        return extractClass(last);
    }
    private String extractClass(PCallField node){
        if(node instanceof ACallCallField){
            var mdcl =(AMethodDcl)((ACallCallField) node).getId().declarationNode;
            return mdcl.getReturntype().toString().trim();
        }
        else if(node instanceof AFieldCallField){
            var dcl = ((AFieldCallField) node).getId().declarationNode;
            if(dcl instanceof ADclStmt){
                return extractClass(((ADclStmt) dcl).getType());
            }
            else if(dcl instanceof AForeachStmt){
                return extractClass(((AForeachStmt) dcl).getList());
            }
            else if(dcl instanceof AParamDcl){
                return extractClass(((AParamDcl) dcl).getType());
            }
        }
        return null;
    }
    private String extractClass(PType node){
        if(node instanceof AListType){
            return "List";
        }
        else if (node instanceof AVarType){
            return ((AVarType) node).getType().getText().trim();
        }
        else
            return null;
    }
    private String extractType(Node node,boolean type){
        if(node instanceof PType){
            return extractType(extractClass((PType)node),type);
        }
        else if(node instanceof  PCallField){
            return extractType(extractClass((PCallField) node),type);
        }
        else if(node instanceof  PVal){
            return extractType(extractClass((PVal)node),type);
        }
        else
            return null;

    }
    private String extractType(Node node){
        return extractType(node,false);
    }

    private String extractDclType(Node dcl, boolean type) throws SemanticException {
        String result;
        if(dcl == null) {
            return null;
        }
        else {
            if (dcl instanceof ADclStmt) {
                result = extractType(((ADclStmt) dcl).getType(), type);
            } else if (dcl instanceof AForeachStmt) {
                result = extractType(((AForeachStmt) dcl).getList(),type);
            } else if (dcl instanceof AParamDcl) {
                result = extractType(((AParamDcl) dcl).getType());
            }
            else if(dcl instanceof AMethodDcl){
                result = extractType(((AMethodDcl) dcl).getReturntype(),type);
            }
            else if(dcl instanceof AConstruct){
                result = "V";
            }
            else{
                throw new SemanticException(dcl,"An unknown error occurred");
            }
            return result;
        }
    }
    private String extractDclType(Node dcl) throws SemanticException {
        return extractDclType(dcl, false);
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

    private String operationType(PExpr l, PExpr r) throws SemanticException {
        var ltype = l.type;
        var rtype = r.type;
        String optype;
        if(ltype.equals(rtype)){
            optype = ltype;
        }
        else if(ltype.equals("null") || rtype.equals("null")){
            optype = "null";
        }
        else {
            switch (ltype) {
                case "int":
                    if (rtype.equals("float")) {
                        emit("\tswap\n" +
                                "\ti2f\n" +
                                "\tswap\n"
                        );
                        optype = "float";
                    }
                    else if(rtype.equals("string")){
                        emit("\tswap\n"+
                                "\tinvokestatic java/lang/String/valueOf(I)Ljava/lang/String;\n"+
                                "\tswap\n"
                        );
                        optype = "string";
                    }
                    else {
                        throw new SemanticException(r, "An unknown error occurred");
                    }
                    break;
                case "float":
                    if (rtype.equals("int")) {
                        emit("\ti2f\n");
                    }
                    else if(rtype.equals("string")){
                        emit("\tswap\n"+
                                "\tinvokestatic java/lang/String/valueOf(F)Ljava/lang/String;\n"+
                                "\tswap\n"
                        );
                        optype = "string";
                    }
                    else {
                        throw new SemanticException(r, "An unknown error occurred");
                    }
                    optype = "float";
                    break;
                case "string":
                    if(rtype.equals("int")){
                        emit("\tinvokestatic java/lang/String/valueOf(I)Ljava/lang/String;\n");
                    }
                    else if(rtype.equals("float")) {
                        emit("\tinvokestatic java/lang/String/valueOf(F)Ljava/lang/String;\n");
                    }
                    else{
                        throw new SemanticException(r, "An unknown error occurred");
                    }
                    optype = "string";
                default:
                    throw new SemanticException(r, "An unknown error occurred");
            }
        }
        return optype;
    }

    @Override
    public void inAProg(AProg node) {
        String s = ".class " + name + "\n"
                + ".super java/lang/Object\n";
        emit(name,s);



        files.put("player","");
        s = ".class player\n"
            + ".super java/lang/Object\n";
        emit("player",s);
        files.put("card","");
        s = ".class card\n"
            + ".super java/Lang/Object\n";
        emit("card",s);

    }

    @Override
    public void caseAProg(AProg node) throws TypeException, SemanticException {
        inAProg(node);
        current = name;
        fg.current = current;
        fg.Static = true;
        for(PStmt s : ((ASetup)node.getSetup()).getGame()){
            if(s instanceof ADclStmt){
                s.apply(fg);
            }
        }
        fg.Static = false;

        emit("\n" +
                ".method public <init>()V\n"
                + "\taload_0\n"
                + "\tinvokenonvirtual java/lang/Object/<init>()V\n"
                + "\treturn\n"
                + ".end method\n\n");
        emit(".method public static main([Ljava/lang/String)V\n");
        // fields fase
        node.apply(fg);
        // Methods fase
        node.apply(mg);
        // Subclasses fase
        node.apply(sg);

        //todo: emit stack and locals sizes

        node.getSetup().apply(this);

        //TODO: turn - endcondition loop

        // main end
        emit(name,".end method\n\n");
        mg.current = name;
        mg.locals = locals;
        mg.scope = scope;
        mg.SetStatic(true);
        mg.setMove(false);
        for(PMethodDcl s : node.getMethods()){
            s.apply(mg);
        }
        mg.setMove(true);
        for(PMethodDcl s : node.getMoves()){
            s.apply(mg);
        }
        for(Start s : node.includes){
            for(PMethodDcl m : ((AProg)s.getPProg()).getMoves()){
                m.apply(mg);
            }
            mg.setMove(false);
            for(PMethodDcl m : ((AProg)s.getPProg()).getMethods()){
                m.apply(mg);
            }
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
        emit("LList;");
    }

    @Override
    public void caseAAssignStmt(AAssignStmt node) throws TypeException, SemanticException {
        var op = node.getOperation().getText();
        if(!op.equals("=")){
            node.getVar().apply(this);
            node.getExpr().apply(this);
            var ltype = extractClass( node.getVar());
            var rtype = node.getExpr().type;
            String restype = "";
            if(ltype == null || rtype == null){
                throw new SemanticException(node,"An unknown error occurred");
            }
            switch (ltype) {
                case "int":
                    if (!rtype.equals("int")) {
                        if (rtype.equals("float")) {
                            emit("\tswap\n" +
                                    "\ti2f\n" +
                                    "\tswap\n"
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
                            emit("\ti2f\n");
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
                            emit("\tiadd\n");
                            break;
                        case "float":
                            emit("\tfadd\n");
                            break;
                        case "string":
                            emit("\tinvokevirtual java/lang/String/concat(Ljava/lang/String;)Ljava/lang/String;\n");
                            break;
                        case "istring":
                            emit("\tswap\n"+
                                    "\tinvokestatic java/lang/String/valueOf(I)Ljava/lang/String;\n"+
                                    "\tswap\n"+
                                    "\tinvokevirtual java/lang/String/concat(Ljava/lang/String;)Ljava/lang/String;\n"
                            );
                            break;
                        case "fstring":
                            emit("\tswap\n"+
                                    "\tinvokestatic java/lang/String/valueOf(F)Ljava/lang/String;\n"+
                                    "\tswap"+
                                    "\tinvokevirtual java/lang/String/concat(Ljava/lang/String;)Ljava/lang/String;\n"
                            );
                            break;
                        case "stringi":
                            emit("\tinvokestatic java/lang/String/valueOf(I)Ljava/lang/String;\n"+
                                    "\tinvokevirtual java/lang/String/concat(Ljava/lang/String;)Ljava/lang/String;\n"
                            );
                            break;
                        case "stringf":
                            emit("\tinvokestatic java/lang/String/valueOf(F)Ljava/lang/String;\n"+
                                    "\tinvokevirtual java/lang/String/concat(Ljava/lang/String;)Ljava/lang/String;\n"
                            );
                            break;
                        default:
                            throw new SemanticException(node, "An unknown error occurred");
                    }
                    break;
                case "-=":
                    switch(restype){
                        case "int":
                            emit("\tisub\n");
                            break;
                        case "float":
                            emit("\tfsub\n");
                            break;
                        default:
                            throw new SemanticException(node, "An unknown error occurred");
                    }
                    break;
                case "*=":
                    switch(restype){
                        case "int":
                            emit("\timul\n");
                            break;
                        case "float":
                            emit("\tfmul\n");
                            break;
                        default:
                            throw new SemanticException(node, "An unknown error occurred");
                    }
                    break;
                case "/=":
                    switch(restype){
                        case "int":
                            emit("\tidiv\n");
                            break;
                        case "float":
                            emit("\tfdiv\n");
                            break;
                        default:
                            throw new SemanticException(node, "An unknown error occurred");
                    }
                    break;
                case "%=":
                    if(restype.equals("int")){
                        emit("\tirem\n");
                    }
                    else{
                        throw new SemanticException(node, "An unknown error occurred");
                    }
                    break;
                case "&=":
                    emit("\tiadd\n"+
                            "\ticonst_2\n"+
                            "\tisub\n"+
                            "\tifeq true" + ++labelcounter + "\n" +
                            "\ticonst_0\n" +
                            "\tgoto done" + labelcounter +"\n"+
                            "true" + labelcounter + ":\n"+
                            "\ticonst_1\n"+
                            "done" + labelcounter+":\n"
                    );
                    break;
                case "|=":
                    emit("\tiadd\n"+
                            "\tifgt true" + ++labelcounter + "\n"+
                            "\ticonst_0\n"+
                            "\tgoto done" + labelcounter + "\n" +
                            "true" + labelcounter + ":\n"+
                            "\ticonst_1\n"+
                            "done" + labelcounter + ":\n"
                    );
                    break;
                default:
                    throw new SemanticException(node,"An unknown error occurred");
            }

        }
        int size = ((AVal)node.getVar()).getCallField().size();
        if(size > 1){
            // Field
            LHSVisitor(node.getVar());
            node.getExpr().apply(this);
            emit("\tputfield ");
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
            LHSVisitor(node.getVar());
        }
    }

    @Override
    public void caseAVal(AVal node) throws TypeException, SemanticException {
        var cfList = node.getCallField();
        var first = cfList.getFirst();
        firstPCallField(first);
        for(int i = 1; i < cfList.size(); i++){
            cfList.get(i).apply(this);
        }

    }

    public void firstPCallField(PCallField first) throws SemanticException, TypeException {
        if(first instanceof ACallCallField){
            var dcl = ((ACallCallField) first).getId().declarationNode;
            if(dcl instanceof AMethodDcl){
                boolean instance = st.retrieveSymbol(((AMethodDcl) dcl).getName().getText().trim())==null;
                if(instance){
                    // get object reference
                    emit("\taload 0\n");
                    typeOnStack = current;
                }
                for(PExpr e : ((ACallCallField) first).getParams()){
                    e.apply(this);
                }
                if(instance){
                    // instance method
                    emit("\tinvokevirtual ");
                }
                else{
                    // static method
                    emit("\tinvokestatic ");

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
                emit("\tnew " + type + "\n"+
                        "\tdup\n");
                for(PExpr e : ((ACallCallField) first).getParams()){
                    e.apply(this);
                }
                emit("\tinvokespecial " + type + "/<init(");
                for(PParamDcl p : ((AConstruct) dcl).getParams()){
                    ((AParamDcl)p).getType().apply(this);
                }
                emit(")V\n");
            }
            else{
                throw new SemanticException(first,"An unknown error occurred");
            }
        }
        else if(first instanceof AFieldCallField){
            var dcl = ((AFieldCallField) first).getId().declarationNode;
            String Fname = ((AFieldCallField) first).getId().getText().trim();
            boolean staticfield = current.equals(this.name) && (!Fname.equals("this") && !Fname.equals("null"));
            if(staticfield && st.retrieveSymbol(Fname) != null){
                typeOnStack = extractDclType(dcl,false);
                emit("\tgetstatic " + name + "/" + ((AFieldCallField) first).getId().getText().trim() +
                        " L" + typeOnStack + ";\n"
                );
            }
            else {
                String type = extractDclType(dcl);
                if (type != null) {
                    var localnr = getLocal(((AFieldCallField) first).getId().getText().trim());
                    if(localnr == -1){
                        emit("\tgetstatic " + current + "/" + Fname + " L" + typeOnStack + ";\n");
                    }
                    else {
                        emit("\t" + extractPrefix(type) + "load " + localnr + "\n");
                        typeOnStack = type;
                    }
                } else if (first.type.equals("null")) {
                    emit("\taconst_null\n");
                } else {
                    throw new SemanticException(first, "An unknown error occurred");
                }
            }
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
                        emit("\ticonst_0\n");
                        break;
                    case "F":
                        emit("\tfconst_0\n");
                        break;
                    case "java/lang/String":
                        emit("\tldc \"\"\n");
                        break;
                    case "List":
                        emit("\tnew List\n"+
                                "\tdup\n" +
                                "\tinvokespecial List/<init>()V\n");
                        break;
                    default :
                        emit("\tnew " + type + "\n");
                        break;
                }
            }

            if(current.equals(name) && st.retrieveSymbol(dcl.getId().getText().trim()) != null){
                if(dcl.getExpr() != null){
                    emit("\tputstatic " + name +"/" + dcl.getId().getText().trim() +
                            " " + extractType(node.getType(),true) + "\n"
                    );
                }
            }
            else {
                emit("\t" + extractPrefix(node.getType()) + "store " +
                        addLocal(((ASingleDcl) s).getId().getText().trim()) + "\n");
            }
        }
    }

    @Override
    public void caseAIfStmt(AIfStmt node) throws TypeException, SemanticException {
        // put predicate result on top of stack
        node.getPredicate().apply(this);
        // jump if true
        var truelbl = ++labelcounter;
        emit("\tifgt true"+ truelbl + "\n");
        var elseifs = node.getElseifs();
        int[] elselbl = new int[elseifs.size()];

        for(int i = 0; i < elseifs.size(); i++){
            elselbl[i] = ++labelcounter;
            ((AElseIf)elseifs.get(i)).getPredicate().apply(this);
            emit("\tifgt else" + labelcounter + "\n");
        }

        scope++;
        for(PStmt s : node.getElse()){
            s.apply(this);
        }
        closeScope();
        emit("\tgoto done" + truelbl +"\n");

        scope++;
        emit("true" + truelbl + ":\n");
        for(PStmt s : node.getThen()){
            s.apply(this);
        }
        closeScope();
        emit("\tgoto done" + truelbl + "\n");

        for(int i = 0; i < elseifs.size(); i++){
            scope++;
            emit("else" + elselbl[i] + ":\n");
            for(PStmt s : ((AElseIf)elseifs.get(i)).getThen()){
                s.apply(this);
            }
            closeScope();
            emit("\tgoto done" + truelbl + "\n");
        }

        emit("done" + truelbl + ":\n");



    }

    @Override
    public void caseACallStmt(ACallStmt node) throws TypeException, SemanticException {
        node.getVal().apply(this);
    }

    @Override
    public void caseAForStmt(AForStmt node) throws TypeException, SemanticException {
        scope++;
        node.getInit().apply(this);
        int loopLbl =  ++labelcounter;
        emit("loop" +loopLbl + ":\n" );
        node.getPredicate().apply(this);
        emit("\tifeq done" + loopLbl + "\n");
        for(PStmt s : node.getThen()){
            s.apply(this);
        }
        node.getUpdate().apply(this);
        emit("\tgoto " + "loop" + loopLbl + "\n");
        emit("done" + loopLbl + ":\n");
        closeScope();
    }

    @Override
    public void caseAWhileStmt(AWhileStmt node) throws TypeException, SemanticException {
        scope++;
        int loopLbl = ++labelcounter;
        emit("loop" + loopLbl + ":\n");
        node.getPredicate().apply(this);
        emit("\tifeq done" + loopLbl + "\n");
        for(PStmt s : node.getThen()){
            s.apply(this);
        }
        emit("\tgoto " + loopLbl + "\n");
        emit("done" + loopLbl + ":\n");
        closeScope();
    }

    @Override
    public void caseAForeachStmt(AForeachStmt node) throws TypeException, SemanticException {
        scope++;
        addLocal(node.getId().getText().trim());
        // initialise iteration variable
        int loopLbl = ++labelcounter;
        emit("\ticonst_0\n" +
                "loop"+ loopLbl + ":\n" +
                "\tdup\n");
        // get list reference
        node.getList().apply(this);
        // retrieve item from list
        emit("\tdup2\n" +
                "\tgetfield List/length I\n" +
                "\tisub\n" +
                "\tifgt done" + loopLbl + "\n" +
                "\tswap\n" +
                "\tinvokevirtual List/index(I)Ljava/lang/Object;\n"+
                "\tcheckcast "
                );
        // retrieve list type
        var listType = node.getList().type.split(" ");
        // cast list element to type
        switch(listType[2]){
            case "int":
                emit("java/lang/Integer\n"+
                        "\tinvokevirtual java/lang/Integer/intValue()I" +
                        "\tistore");
                break;
            case "float":
                emit("java/lang/Float\n"+
                        "\tinvokevirtual java/lang/Float/floatValue()F"+
                        "\tfstore");
                break;
            case "string":
                emit("java/lang/String\n"+
                        "\tastore");
                break;
            case "list":
                emit("List\n"+
                        "\tastore");
                break;
            default:
                emit(listType[2] + "\n" +
                        "\tastore");
                break;
        }
        // get local variable number
        emit(getLocal(node.getId().getText()) + "\n");

        // emit loop body
        for(PStmt s : node.getThen()){
            s.apply(this);
        }
        // increment iteration variable
        emit("\ticonst_1\n" +
                "\tiadd\n");
        //jump to loop start and done label
        emit("\tgoto loop" + loopLbl + "\n" +
                "done" + loopLbl +":\n");
        closeScope();
    }

    @Override
    public void caseAReturnStmt(AReturnStmt node) throws TypeException, SemanticException {
        node.getExpr().apply(this);
        emit("\t" + extractPrefix(node.getExpr().type)+ "return\n");
    }

    @Override
    public void caseASwitchStmt(ASwitchStmt node) throws TypeException, SemanticException {
        var caseList = node.getCases();
        var type = extractClass(node.getVariable());
        // put switch variable on top of stack
        node.getVariable().apply(this);
        ADefaultCase def = new ADefaultCase();
        var labelOffset = labelcounter +1;
        // Compare to each case
        for(PCase c : caseList){
            if(c instanceof ACaseCase){
                emit("\tdup\n");
                ((ACaseCase) c).getCase().apply(this);
                switch(type){
                    case "int":
                        emit("\tisub\n");
                        break;
                    case "float":
                        emit("\tfsub\n"+
                                "\tf2i\n");
                        break;
                    case "string":
                        emit("\tinvokevirtual java/lang/String/equals(Ljava/lang/Object;)Z\n"+
                                "\ticonst_1\n"+
                                "\tisub\n");
                        break;
                    default:
                        throw new SemanticException(node,"An unknown error occured");
                }
                emit("\tifeq case" + ++ labelcounter + "\n");
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
        emit("\tgoto done" + labelOffset + "\n");
        // emit each case body
        for(int i = 0; i < caseList.size(); i++){
            PCase c = caseList.get(i);
            if(! (c instanceof ADefaultCase)){
                scope++;
                emit("case" + (labelOffset+i) + ":\n");
                for(PStmt s : ((ACaseCase)c).getThen()){
                    s.apply(this);
                }
                emit("\tgoto done" + labelOffset + "\n");
                closeScope();
            }
        }

        emit("done" + labelOffset + ":\n");
    }

    @Override
    public void caseARelationExpr(ARelationExpr node) throws TypeException, SemanticException {
        node.getL().apply(this);
        node.getR().apply(this);
        String optype = operationType(node.getL(),node.getR());
        var op = node.getOperator().getText().trim();
        if(optype.equals("int")){
            switch(op){
                case "<":
                    emit("\tif_icmplt ");
                    break;
                case ">":
                    emit("\tif_icmpgt ");
                    break;
                case "<=":
                    emit("\tif_icmple ");
                    break;
                case ">=":
                    emit("\tif_icmpge ");
                    break;
                default:
                    throw new SemanticException(node,"An unknown error occurred");
            }
        }
        else if(optype.equals("float")) {
            emit("\tfcmpg\n");
            switch(op){
                case "<":
                    emit("\tiflt ");
                    break;
                case "<=":
                    emit("\tifle ");
                    break;
                case ">":
                    emit("\tifgt ");
                    break;
                case ">=":
                    emit("\tifge ");
                default :
                    throw new SemanticException(node,"An unknown error occurred");
            }
        }
        else{
            throw new SemanticException(node,"An unknown error occurred");
        }
        emit("true" + ++labelcounter + "\n"+
                "\ticonst_0\n"+
                "\tgoto done" + labelcounter + "\n" +
                "true" + labelcounter + ":\n"+
                "\ticonst_1\n"+
                "done" + labelcounter + ":\n"
        );
    }

    @Override
    public void caseAEqualityExpr(AEqualityExpr node) throws TypeException, SemanticException {
        node.getL().apply(this);
        node.getR().apply(this);
        var op = node.getOperator().getText().trim();
        String optype = operationType(node.getL(),node.getR());
        switch (optype) {
            case "string":
                emit("\tinvokevirtual java/lang/String/equals(Ljava/lang/Object;)Z\n");
                if (op.equals("!=")) {
                    emit("\ticonst_1\n" +
                            "\tisub\n" +
                            "\tifeq done" + ++labelcounter + "\n" +
                            "\tineg\n" +
                            "done" + labelcounter + ":\n"
                    );
                } else if (!op.equals("==")) {
                    throw new SemanticException(node, "An unknown error occurred");
                }
                break;
            case "int":
            case "bool":
                if (op.equals("==")) {
                    emit("\tif_icmpeq ");
                } else if (op.equals("!=")) {
                    emit("\tif_icmpne ");
                } else {
                    throw new SemanticException(node, "An unknown error occurred");
                }
                emit("true" + ++labelcounter + "\n" +
                        "\ticonst_0\n" +
                        "\tgoto done" + labelcounter + "\n" +
                        "true" + labelcounter + ":\n" +
                        "\ticonst_1\n" +
                        "done" + labelcounter + ":\n"
                );
                break;
            case "float":
                emit("\tfcmpg\n");
                if (op.equals("==")) {
                    emit("\tifeq ");
                } else if (op.equals("!=")) {
                    emit("\tifne ");
                } else {
                    throw new SemanticException(node, "An unknown error occurred");
                }
                emit("true" + ++labelcounter + "\n" +
                        "\ticonst_0\n" +
                        "\tgoto done" + labelcounter + "\n" +
                        "true" + labelcounter + ":\n" +
                        "\ticonst_1\n" +
                        "done" + labelcounter + ":\n"
                );
                break;
            default:
                if(op.equals("==")){
                    emit("\tif_acmpeq ");
                }
                else if(op.equals("!=")){
                    emit("\tif_acmpne ");
                }
                else{
                    throw new SemanticException(node, "An unknown error occurred");
                }
                emit("true" + ++labelcounter + "\n" +
                        "\ticonst_0\n" +
                        "\tgoto done" + labelcounter + "\n" +
                        "true" + labelcounter + ":\n" +
                        "\ticonst_1\n" +
                        "done" + labelcounter + ":\n"
                );
                break;
        }
    }

    @Override
    public void caseAMultOpExpr(AMultOpExpr node) throws TypeException, SemanticException {
        node.getL().apply(this);
        node.getR().apply(this);
        var op = node.getOperator().getText().trim();
        var optype = operationType(node.getL(),node.getR());
        if(optype.equals("int") || optype.equals("float")) {
            switch (op) {
                case "*":
                    emit("\t" +extractPrefix(optype) + "mul\n");
                    break;
                case "/":
                    emit("\t" +extractPrefix(optype) + "div\n");
                    break;
                case "%":
                    if (optype.equals("int")) {
                        emit("\tirem\n");
                    } else {
                        throw new SemanticException(node, "An unknown error occurred");
                    }
                default:
                    throw new SemanticException(node, "An unknown error occurred");
            }
        }
        else{
            throw new SemanticException(node,"An unknown error occurred");
        }
    }

    @Override
    public void caseABoolOpExpr(ABoolOpExpr node) throws TypeException, SemanticException {
        node.getL().apply(this);
        node.getR().apply(this);
        var op = node.getOperator().getText().trim();
        var optype = operationType(node.getL(),node.getR());
        if(optype.equals("bool")){
            emit("\tiadd\n");
            if(op.equals("&")){
                emit("\ticonst_2\n"+
                        "\tif_icmpeq true" + ++labelcounter + "\n"+
                        "\ticonst_0\n"+
                        "\tgoto done" + labelcounter + "\n"+
                        "true" + labelcounter + ":\n"+
                        "\ticonst_1\n"+
                        "done" + labelcounter + ":\n"
                );
            }
            else if(!op.equals("|")){
                throw new SemanticException(node,"An unknown error occurred");
            }
        }
        else{
            throw new SemanticException(node,"An unknown error occurred");
        }
    }

    @Override
    public void caseAAddOpExpr(AAddOpExpr node) throws TypeException, SemanticException {
        node.getL().apply(this);
        node.getR().apply(this);
        var op = node.getOperator().getText().trim();
        var optype = operationType(node.getL(),node.getR());
        switch(op){
            case "+":
                if(optype.equals("string")){
                    emit("\tinvokevirtual java/lang/Strig/concat(Ljava/lang/String;)Ljava/lang/String;\n");
                }
                else{
                    emit("\t" +extractPrefix(optype) + "add\n");
                }
                break;
            case "-":
                emit("\t" +extractPrefix(optype) + "sub\n");
                break;
            default:
                throw new SemanticException(node, "An unknown error occurred");
        }

    }

    @Override
    public void caseAValueExpr(AValueExpr node) throws TypeException, SemanticException {
        node.getVal().apply(this);
    }

    @Override
    public void caseAListExpr(AListExpr node) throws TypeException, SemanticException {
        emit("\tnew List\n"+
                "\tdup\n"+
                "\tinvokespecial List/<init>()V\n"
        );
        for(PExpr e : node.getElements()){
            emit("\tdup\n");
            e.apply(this);
            switch(e.type){
                case "int":
                    emit("\tnew java/lang/Integer\n"+
                            "\tdup_x1\n"+
                            "\tswap\n"+
                            "\tinvokespecial java/lang/Integer/<init>(I)V\n"+
                            "\tinvokevirtual List/add(Ljava/lang/Object;)V\n"
                    );
                    break;
                case "float":
                    emit("\tnew java/lang/Float\n"+
                            "\tdup_x1\n"+
                            "\tswap\n"+
                            "\tinvokespecial java/lang/Float/<init>(F)V\n"+
                            "\tinvokevirtual List/add(Ljava/lang/Object;)V\n"
                    );
                    break;
                default:
                    emit("\tinvokevirtual List/add(Ljava/lang/Object;)V\n");
                    break;
            }
        }
    }

    @Override
    public void caseALiteralExpr(ALiteralExpr node) throws TypeException, SemanticException {
        node.getValue().apply(this);
    }

    @Override
    public void caseAStringLiteral(AStringLiteral node) {
        emit("\tldc " + node.getValue().getText() + "\n");
    }

    @Override
    public void caseAIntLiteral(AIntLiteral node) {
        emit("\tldc " + node.getValue().getText() + "\n");
    }

    @Override
    public void caseAFloatLiteral(AFloatLiteral node) {
        emit("\tldc " + node.getValue().getText() + "\n");
    }

    @Override
    public void caseABoolLiteral(ABoolLiteral node) throws SemanticException {
        var val = node.getValue().getText();
        String con;
        if(val.equals("true")){
            con = "iconst_1";
        }
        else if(val.equals("false")){
            con = "iconst_0";
        }
        else{
            throw new SemanticException(node, "An unknown error occurred");
        }
        emit("\t"+con + " \n");

    }

    @Override
    public void caseAParamDcl(AParamDcl node) throws TypeException, SemanticException {
        addLocal(node.getName().getText().trim());
        emit(extractType(node.getType(),true));
    }

    @Override
    public void caseACallCallField(ACallCallField node) throws TypeException, SemanticException {
        var nextStackType = extractDclType(node.getId().declarationNode,true);
        var currentStackType = typeOnStack.endsWith(";") ? typeOnStack.substring(1,typeOnStack.length()-1) : typeOnStack;
        for(PExpr e : node.getParams()){
            e.apply(this);
        }
        emit("\tinvokevirtual " + currentStackType + "/" + node.getId().getText().trim() +
                "(");
        for(PExpr e : node.getParams()){
            emit(extractType(e.type,true));
        }
        emit(")" + nextStackType + "\n");
        typeOnStack = nextStackType;
        if(typeOnStack.contains("java/lang/Object")){
            emit("\tcheckcast ");
            switch(node.type){
                case "int":
                    emit("java/lang/Integer\n"+
                            "\tinvokevirtual java/lang/Integer/intValue()I\n"
                    );
                    break;
                case "float":
                    emit("java/lang/Float\n"+
                            "\tinvokevirtual java/lang/Float/floatValue()F\n"
                    );
                    break;
                case "string":
                    emit("java/lang/String\n");
                    break;
                default:
                    emit(node.type + "\n");
                    break;
            }
        }

    }

    @Override
    public void caseAFieldCallField(AFieldCallField node) throws TypeException, SemanticException {
        var nextStackType = extractDclType(node.getId().declarationNode,true);
        emit("\tgetfield " + typeOnStack + "/" + node.getId().getText().trim() +
                " " + nextStackType + "\n");
        typeOnStack = nextStackType;
    }
}
