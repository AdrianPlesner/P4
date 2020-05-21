package P4.contextualAnalysis;

import P4.CodeGenarator.SemanticException;
import P4.Sable.analysis.DepthFirstAdapter;
import P4.Sable.lexer.Lexer;
import P4.Sable.node.*;
import P4.Sable.parser.Parser;
import P4.contextualAnalysis.Symbol.*;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.PushbackReader;
import java.util.Arrays;
import java.util.Collections;
import java.util.LinkedList;

public class STBuilder extends DepthFirstAdapter {

    protected Start ast;

    protected SymbolTable st;

    protected Symbol current;

    private String path;

    private TokenFinder tf = new TokenFinder();

    public STBuilder(Start ast){
        this.ast = ast;
    }

    public STBuilder(Start ast, String contentPath) {
        this.ast = ast;
        this.path = contentPath;
    }

    // Construct the given symbol table from the ast
    public SymbolTable BuildST(SymbolTable st) throws TypeException, SemanticException {
        this.st = st;
        // Add standard environment
        createStdEnv();

        ast.apply(this);

        return this.st;
    }

    private void addToCurrent(Variable v) throws TypeException, SemanticException {
        if(current == null){
            st.enterSymbol(v);
        }
        else if(current instanceof SubClass){
            ((SubClass) current).addLocal(v);
        }
        else if(current instanceof Function){
            ((Function) current).addArg(v);
        }
        else{
            throw new TypeException(null,"An unknown type error occurred");
        }
    }
    protected void addToCurrent(Function f) throws TypeException, SemanticException {
        if(current == null){
            st.enterSymbol(f);
        }
        else if(current instanceof SubClass){
            ((SubClass) current).addMethod(f);
        }
        else{
            throw new TypeException(null,"An unknown type error occurred");
        }
    }

    private void createStdEnv(){
        st.enterSymbol(new SubClass("int",null,null));
        st.enterSymbol(new SubClass("float",null,null));
        st.enterSymbol(new SubClass("bool",null,null));
        st.enterSymbol(new SubClass("void",null,null));
        st.enterSymbol(new SubClass("card",null,null));
        st.enterSymbol(new SubClass("player",null,null));

        var string = new SubClass("string",null,null);
        st.enterSymbol(string);
        string.addLocal(new Variable("length",new ADclStmt(new AVarType(new TId("int")),new LinkedList<>()),"int"));

        var stringIndex = new Function("index",new AMethodDcl(new TId("index"),new LinkedList<PParamDcl>(Collections.singletonList(new AParamDcl(new AVarType(new TId("int")),new TId("i")))),new AVarType(new TId("string")),new LinkedList<>()),"string");
        string.addMethod(stringIndex);
        stringIndex.addArg(new Variable("i",null,"int"));

        var list = new GenericClass("list",null,null);
        st.enterSymbol(list);
        list.addLocal(new Variable("length",new ADclStmt(new AVarType(new TId("int")),new LinkedList<>()),"int"));

        var take = new Function("take",new AMethodDcl(new TId("take"),new LinkedList<PParamDcl>(Collections.singletonList(new AParamDcl(new AVarType(new TId("int")),new TId("num")))),new AListType(new TId("list of element")),new LinkedList<>()),"list of element");
        take.addArg(new Variable("num",null,"int"));
        list.addMethod(take);

        var add = new Function("add",new AMethodDcl(new TId("add"),new LinkedList<PParamDcl>(Collections.singletonList(new AParamDcl(new AVarType(new TId("element")),new TId("e")))),new AVarType(new TId("void")),new LinkedList<>()),"void");
        list.addMethod(add);
        add.addArg(new Variable("e",null,"void"));

        var remove = new Function("remove",new AMethodDcl(new TId("remove"),new LinkedList<PParamDcl>(Collections.singletonList(new AParamDcl(new AVarType(new TId("element")),new TId("e")))),new AVarType(new TId("void")),new LinkedList<>()),"void");
        list.addMethod(remove);
        remove.addArg(new Variable("e",null,"void"));

        list.addMethod(new Function("clear",new AMethodDcl(new TId("clear"),new LinkedList<>(),new AVarType(new TId("void")),new LinkedList<>()),"void"));

        var index = new Function("index",new AMethodDcl(new TId("index"),new LinkedList<PParamDcl>(Collections.singletonList(new AParamDcl(new AVarType(new TId("int")),new TId("i")))),new AVarType(new TId("element")),new LinkedList<>()),"element");
        list.addMethod(index);
        index.addArg(new Variable("i",null,"int"));

        var message = new Function("Message",new AMethodDcl(new TId("Message"),new LinkedList<PParamDcl>(Arrays.asList(new AParamDcl(new AVarType(new TId("player")), new TId("p")), new AParamDcl(new AVarType(new TId("string")), new TId("m")))),new AVarType(new TId("void")),new LinkedList<>()),"void");
        st.enterSymbol(message);
        message.addArg(new Variable("p",null,"player"));
        message.addArg(new Variable("m",null,"string"));

        var messageAll = new Function("MessageAll",new AMethodDcl(new TId("MessageAll"),new LinkedList<PParamDcl>(Collections.singletonList(new AParamDcl(new AVarType(new TId("string")),new TId("m")))),new AVarType(new TId("void")),new LinkedList<>()),"void");
        st.enterSymbol(messageAll);
        messageAll.addArg(new Variable("m",null,"string"));


        st.enterSymbol(new Function("Read",new AMethodDcl(new TId("Read"),new LinkedList<PParamDcl>(),new AVarType(new TId("string")),new LinkedList<>()),"string"));

        st.enterSymbol(new SubClass("null",null,null));
        st.enterSymbol(new Variable("null",null,"null"));
        st.enterSymbol(new SubClass("element",null,null));
    }

    @Override
    public void caseAProg(AProg node) throws TypeException, SemanticException {

        var includes = node.getIncludes();
        int i = 0;
        try {
            for (TId inc : includes) {
                // TODO: only include each file once
                // Read included .cl file
                File f = new File(path.concat(inc.getText()).concat(".cl"));
                if(f.exists()){
                    System.out.println("Found include: " + f.getAbsolutePath());
                }
                else{
                    throw new TypeException(inc,"Could not find file to include: " + f.getPath());
                }
                Lexer lexer = new Lexer(new PushbackReader(new BufferedReader(new FileReader(f)), 1024));

                // parser program
                Parser parser = new Parser(lexer);

                Start ast = parser.parse();
                node.includes.add(ast);
                i++;
            }
            i = 0;

            for (Start s : node.includes) {
                s.apply(this);
                i++;
            }
        }
        catch(Exception e){
            System.out.println("In "+includes.get(i).getText()+ "\n" + e);
        }

        this.st = new DeclarationBuilder((Start)node.parent()).BuildST(st);
        // Do Setup
        if(node.getSetup() != null){
            node.getSetup().apply(this);
        }

        // Do Turn
        var turn = node.getTurn();
        for(PStmt stmt : turn){
            stmt.apply(this);
        }

        // Do EndCondition
        st.openScope();
        var endcon = node.getEndCondition();
        for(PStmt stmt : endcon){
            stmt.apply(this);
        }
        st.closeScope();

        // Check method bodies
        for(PMethodDcl m : node.getMethods()){
            m.apply(this);
        }
        // check move bodies
        for(PMethodDcl move : node.getMoves())
        {
            move.apply(this);
        }

    }

    @Override
    public void caseASetup(ASetup node) throws TypeException, SemanticException {
        // Add card
        SubClass card;
        card = (SubClass) st.retrieveSymbol("card", SubClass.class);
        if(card == null) {
            throw new TypeException(null,"An unknown type error occurred");
        }
        current = card;
        node.getCard().apply(this);
        current = null;

        // Add player
        SubClass player;
        player = (SubClass) st.retrieveSymbol("player", SubClass.class);
        if(player == null) {
            throw new TypeException(null,"An unknown type error occurred");
        }
        current = player;
        node.getPlayer().apply(this);
        current = null;

        // Do game
        for(PStmt ps : node.getGame()){
            ps.apply(this);
        }
    }

    @Override
    public void caseAMethodDcl(AMethodDcl node) throws TypeException, SemanticException {
        // get name
        var name = node.getName().getText();
        // check if name already exists
        Symbol local = st.retrieveSymbol(name);
        if(current instanceof SubClass){
            local = ((SubClass) current).containsMethod(name);
        }
        if(local instanceof Function){
            // function with name exists
            var fun = (Function)local;
            if(fun.getDeclarationNode().equals(node)){
                // Go through method body
                st.openScope();
                for(var p : fun.getArgs()){
                    // Declare arguments as local variables
                    if(st.declaredLocally(p.getIdentifier())){
                        throw new IdentifierAlreadyExistsException(null,"Parameter " + p.getIdentifier() + " in function " + fun.getIdentifier());
                    }
                    st.enterSymbol(p);
                }
                // Check body
                var prev = current;

                current = local;
                for (PStmt stmt : node.getBody()) {
                    stmt.apply(this);
                }
                current = prev;
                st.closeScope();
            }
            else {
                throw new IdentifierAlreadyExistsException(node.getName(), fun + " already exsists");
            }
        }
        else{
            throw new IdentifierAlreadyExistsException(node.getName(),"");
        }

    }

    @Override
    public void caseAParamDcl(AParamDcl node) throws TypeException, SemanticException {
        // Check type is valid
        var typeNode = node.getType();
        typeNode.apply(this);
        var type = typeNode.toString().trim();
        // Check name is valid
        var name = node.getName().getText();
        if(((Function)current).containsArg(name)){
            throw new IdentifierAlreadyExistsException(node.getName(),"A parameter with " + name
                    + " already exists in method "+ ((AMethodDcl)node.parent()).getName().getText());
        }
        Variable v;
        if(typeNode instanceof AVarType) {
            v = new Variable(name, node, type);
        }
        else if(typeNode instanceof AListType){
            // variable is a list
            v = new GenerecVariable(name, node,"list",type);
        }
        else{
            throw new TypeException(null,"An unknown type error occurred");
        }
        addToCurrent(v);
    }

    @Override
    public void caseAClassBody(AClassBody node) throws TypeException, SemanticException {
        if(current != null){

            var construct = node.getConstruct();
            if(construct != null){
                construct.apply(this);
            }

            // Do method bodies
            for(PMethodDcl md : node.getMethods()){
                md.apply(this);
            }

            // Add subclasses
            for(PSubclass sc : node.getSubclasses()){
                sc.apply(this);
            }
        }
        else {
            //report error
            throw new TypeException(null, "An unknown type error occurred.");
        }
    }

    @Override
    public void caseASubclass(ASubclass node) throws TypeException, SemanticException {
        var name = node.getName().getText();
        // Do class body
        var sub = st.retrieveSymbol(name);
        if(sub instanceof SubClass) {
            current = sub;
            node.getBody().apply(this);
            current = ((SubClass) sub).getSuperClass();
        }
        else{
            throw new TypeException(node.getName(), "An unknown type error occurred.");
        }

    }

    @Override
    public void caseAConstruct(AConstruct node) throws TypeException, SemanticException {
        Function construct;
        var sub = st.retrieveSymbol(node.getName().getText());
        if(!(sub instanceof Function)){
            if(sub instanceof SubClass){
                construct = ((SubClass) sub).containsMethod(node.getName().getText());
            }
            else{
                throw new TypeException(node.getName(), "An unknown type error occurred.");
            }
        }
        else{
            construct = (Function)sub;
        }
        var prev = current;
        current = null;
        st.openScope();
        current = construct;

        for(PParamDcl pd : node.getParams()){
            pd.apply(this);
        }
        current = null;
        for(Symbol s : construct.getArgs()){
            st.enterSymbol(s);
        }
        current = prev;
        for(PStmt s : node.getBody()){
            s.apply(this);
        }
        st.closeScope();

    }

    @Override
    public void caseADclStmt(ADclStmt node) throws TypeException, SemanticException {
        // Validate type
        var typeNode = node.getType();
        typeNode.apply(this);
        var type = node.getType().toString().trim();
        for(PSingleDcl sDcl : node.getDcls()){
            // Validate singleDcl
            sDcl.apply(this);

            Variable v;
            if(typeNode instanceof AVarType) {
                v = new Variable(((ASingleDcl) sDcl).getId().getText(), node, type);
            }
            else if(typeNode instanceof AListType){
                // variable is a list

                v = new GenerecVariable(((ASingleDcl) sDcl).getId().getText(), node,"list of " + type,type);
            }
            else{
                throw new TypeException(null,"An unknown type error occurred");
            }
            if(current instanceof Function){
                var prev = current;
                current = null;
                addToCurrent(v);
                current = prev;
            }
            else {
                addToCurrent(v);
            }
        }
    }

    @Override
    public void caseAVarType(AVarType node) throws TypeException, SemanticException {
        var s = st.retrieveSymbol(node.getType().getText(),SubClass.class);
        if(! (s instanceof SubClass)){
            //Type is not a valid type
            throw new InvalidTypeException(node.getType(),"");
        }
    }

    @Override
    public void caseAListType(AListType node) throws TypeException, SemanticException {
        var s = st.retrieveSymbol(node.getType().getText(),SubClass.class);
        if(! (s instanceof SubClass)){
            //Type is not a valid type
            throw new InvalidTypeException(node.getType(),"");
        }

    }

    @Override
    public void caseASingleDcl(ASingleDcl node) throws TypeException, SemanticException {
        // Check if ID exists locally
        if( st.declaredLocally(node.getId().getText())){
            throw new TypeException(node.getId(),"Identifier already exists locally");
        }
        // Check that expression is OK
        if(node.getExpr() != null) {
            node.getExpr().apply(this);
        }
        if(current instanceof Function)
            ((Function) current).addLocal();

    }

    @Override
    public void caseAAssignStmt(AAssignStmt node) throws TypeException, SemanticException {
        node.getVar().apply(this);
        node.getExpr().apply(this);
    }

    @Override
    public void caseAVal(AVal node) throws TypeException, SemanticException {
        var prev = current;
        for(PCallField cf : node.getCallField()){
            //Check call/field is valid
            cf.apply(this);

        }
        if (current != null) {
            node.type = current.getType();
        }
        else{
            throw new TypeException(null,"An unknown type error occurred");
        }
        current = prev;
    }

    @Override
    public void caseACallCallField(ACallCallField node) throws TypeException, SemanticException {
        var name = node.getId().getText();
        Symbol dcl;
        if(current == null ||current instanceof Function){
            // Base call, lookup at symbol table
            dcl = st.retrieveSymbol(name);
        }
        else{
            // Call on a variable
            if(current instanceof SubClass){
                dcl = ((SubClass) current).containsMethod(name);
            }
            else{
                throw new TypeException(null,"An unknown type error occurred");
            }
        }
        // Set delcaration node
        if(dcl instanceof Function){
            // check arguments
            var prev = current;
            current = null;
            for(PExpr e: node.getParams()){
                e.apply(this);
            }
            current = prev;
            var type = ((Function) dcl).getReturnType().trim();

            if(type.startsWith("list of")){
                // get class variabel
                var classV = type.substring(8).trim();

                if(classV.equals("element")){
                    // unknown classvariable
                    type = ((GenericClass)st.retrieveSymbol("list")).getClassVariable();
                }
                else {
                    // known class variable
                    ((GenericClass) st.retrieveSymbol("list")).setClassVariable(classV);
                    type = "list";
                }
            }
            else if(type.equals("element")){
                type = ((GenericClass)st.retrieveSymbol("list")).getClassVariable();
            }

            current = st.retrieveSymbol(type,SubClass.class);
            if(current == null){
                current = st.retrieveSymbol(type,GenericClass.class);
            }
        }
        else{
            throw new TypeException(node.getId(),"Method " + node.getId().getText() + " does not exists in the current context");
        }


        node.getId().declarationNode = dcl.getDeclarationNode();
    }

    @Override
    public void caseAFieldCallField(AFieldCallField node) throws TypeException, SemanticException {
        var name = node.getId().getText();
        Symbol dcl = null;
        if(current == null ||current instanceof Function){
            // Base call, lookup at symboltable
            dcl = st.retrieveSymbol(name);
        }
        else{
            // Field on variable
            if(current instanceof SubClass){
                dcl = ((SubClass)current).containsVariable(name);
            }
            else{
                throw new TypeException(node.getId(),"Unknown type error occured");
            }
        }
        if(! (dcl instanceof Variable)){
            throw new TypeException(node.getId(),"Variable " + name + " does not exists in the current context");
        }
        current = st.retrieveSymbol(dcl.getType(),SubClass.class);
        if(current==null){
            current = st.retrieveSymbol("list",GenericClass.class);
        }
        // Handle generics(list)
        if(current instanceof GenericClass && dcl instanceof GenerecVariable){
            ((GenericClass) current).setClassVariable(((GenerecVariable) dcl).getClassVariable());
        }

        node.getId().declarationNode = dcl.getDeclarationNode();
    }

    @Override
    public void caseAReturnStmt(AReturnStmt node) throws TypeException, SemanticException {
        node.getExpr().apply(this);
    }

    @Override
    public void caseACallStmt(ACallStmt node) throws TypeException, SemanticException {
        node.getVal().apply(this);
    }

    @Override
    public void caseAIfStmt(AIfStmt node) throws TypeException, SemanticException {
        node.getPredicate().apply(this);

        st.openScope();
        for(PStmt s :node.getThen()){
            s.apply(this);
        }
        st.closeScope();

        for(PElseIf ei : node.getElseifs()){
            ei.apply(this);
        }
        st.openScope();
        for(PStmt s : node.getElse()){
            s.apply(this);
        }
        st.closeScope();
    }

    @Override
    public void caseAForeachStmt(AForeachStmt node) throws TypeException, SemanticException {

        node.getList().apply(this);
        var type = node.getList().type;
        if(type.equals("list")){
            // Generic list
            type = ((GenericClass)st.retrieveSymbol("list")).getClassVariable();
        }
        else if(type.startsWith("list of")){
            // Known list type
            type = type.substring(7);

        }
        else{
            // Not a list
            throw new TypeException(node.getId(),"Iteration variable must be a list");
        }


        st.openScope();
        st.enterSymbol(new Variable(node.getId().getText(),node,type.trim()));
        if(current instanceof Function){
            ((Function) current).addLocal();
        }
        for(PStmt s : node.getThen()){
            s.apply(this);
        }
        st.closeScope();
    }

    @Override
    public void caseAForStmt(AForStmt node) throws TypeException, SemanticException {
        st.openScope();
        node.getInit().apply(this);

        node.getPredicate().apply(this);

        node.getUpdate().apply(this);

        for(PStmt s : node.getThen()){
            s.apply(this);
        }

        st.closeScope();
    }

    @Override
    public void caseASwitchStmt(ASwitchStmt node) throws TypeException, SemanticException {
        node.getVariable().apply(this);
        for(PCase c : node.getCases()){
            c.apply(this);
        }
    }

    @Override
    public void caseACaseCase(ACaseCase node) throws TypeException, SemanticException {
        node.getCase().apply(this);
        st.openScope();
        for(PStmt s : node.getThen()){
            s.apply(this);
        }
        st.closeScope();
    }

    @Override
    public void caseADefaultCase(ADefaultCase node) throws TypeException, SemanticException {
        st.openScope();
        for(PStmt s : node.getThen()){
            s.apply(this);
        }
        st.closeScope();
    }

    @Override
    public void caseAWhileStmt(AWhileStmt node) throws TypeException, SemanticException {
        node.getPredicate().apply(this);
        st.openScope();
        for(PStmt s : node.getThen()){
            s.apply(this);
        }
        st.closeScope();
    }

    @Override
    public void caseAElseIf(AElseIf node) throws TypeException, SemanticException {
        node.getPredicate().apply(this);
        st.openScope();
        for(PStmt s : node.getThen()){
            s.apply(this);
        }
        st.closeScope();
    }

    @Override
    public void caseAEqualityExpr(AEqualityExpr node) throws TypeException, SemanticException {
        node.getL().apply(this);
        node.getR().apply(this);
    }

    @Override
    public void caseARelationExpr(ARelationExpr node) throws TypeException, SemanticException {
        node.getL().apply(this);
        node.getR().apply(this);
    }

    @Override
    public void caseAAddOpExpr(AAddOpExpr node) throws TypeException, SemanticException {
        node.getL().apply(this);
        node.getR().apply(this);
    }

    @Override
    public void caseABoolOpExpr(ABoolOpExpr node) throws TypeException, SemanticException {
        node.getL().apply(this);
        node.getR().apply(this);
    }

    @Override
    public void caseAMultOpExpr(AMultOpExpr node) throws TypeException, SemanticException {
        node.getL().apply(this);
        node.getR().apply(this);
    }

    @Override
    public void caseAListExpr(AListExpr node) throws TypeException, SemanticException {
        for(PExpr e : node.getElements()){
            e.apply(this);
        }
    }

    @Override
    public void caseAValueExpr(AValueExpr node) throws TypeException, SemanticException {
        var prev = current;
        current = null;
        node.getVal().apply(this);
        current = prev;
    }

}
