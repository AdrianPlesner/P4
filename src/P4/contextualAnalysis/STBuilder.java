package P4.contextualAnalysis;

import P4.Sable.analysis.DepthFirstAdapter;
import P4.Sable.lexer.Lexer;
import P4.Sable.node.*;
import P4.Sable.parser.Parser;
import P4.contextualAnalysis.Symbol.*;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.PushbackReader;
import java.util.LinkedList;

public class STBuilder extends DepthFirstAdapter {

    private Start ast;

    private SymbolTable st;

    private Symbol current;

    private String path;

    private LinkedList<TypeError> errors = new LinkedList<>();

    public STBuilder(Start ast, String contentPath) {
        this.ast = ast;
        this.path = contentPath;
    }

    // Construct the given symbol table from the ast
    public SymbolTable BuildST(SymbolTable st) throws TypeException {
        this.st = st;
        // Add standard environment
        createStdEnv();
        ast.apply(this);

        return this.st;
    }

    public String getErrorList(){
        String result = "";
        return result;
    }

    public boolean hasTypeErrors(){
        return errors.size() > 0;
    }

    private void addToCurrent(Variable v) throws TypeException {
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
    private void addToCurrent(Function f) throws TypeException {
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
        st.enterSymbol(new SubClass("string",null,null));
        st.enterSymbol(new SubClass("bool",null,null));
        st.enterSymbol(new SubClass("void",null,null));
        st.enterSymbol(new SubClass("card",null,null));
        st.enterSymbol(new SubClass("player",null,null));
        var list = new GenericClass("list",null,null);
        st.enterSymbol(list);
        list.addLocal(new Variable("length",null,"int"));
        var take = new Function("take",null,null);
        take.addArg(new Variable("num",null,"int"));
        list.addMethod(take);
        var find = new Function("find",null,null);
        list.addMethod(find);
        find.addArg(new Variable("n",null,"string"));
        st.enterSymbol(new Function("GetStdDeck",null,"list of card"));
        var Turn = new SubClass("Turn",null,null);
        st.enterSymbol(Turn);
        Turn.addLocal(new Variable("current",null,"player"));
        st.enterSymbol(new Variable("turn",null,"Turn"));
        var chooseFrom = new Function("chooseFrom",null,null);
        chooseFrom.addArg(new GenerecVariable("l",null,"list",null));
    }

    @Override
    public void caseAProg(AProg node) throws TypeException {

        var includes = node.getIncludes();
        for(TId inc : includes){
            // Something about reading a file and adding everything to symboltable
            //TODO: include includes
            try {
                Lexer lexer = new Lexer(new PushbackReader(new BufferedReader(new FileReader(path.concat(inc.getText()).concat(".cl"))), 1024));

                // parser program
                Parser parser = new Parser(lexer);
                // Debug med System.out.println(this.token.getClass().getSimpleName() + ": [" + token.getText() + "]");
                Start ast = parser.parse();
                node.includes.add(ast);

            }
            catch(Exception e){
                System.out.println(e);
            }
        }
        for(Start ast : node.includes){
            ast.apply(this);
        }

        // Do Setup
        if(node.getSetup() != null){
            node.getSetup().apply(this);
        }

        // Do method declarations
        var methods = node.getMethods();
        for(PMethodDcl m : methods){
            m.apply(this);
        }

        // Do Moves
        var moves = node.getMoves();
        for(PMethodDcl move : moves)
        {
            move.apply(this);
        }

        // Do Turn
        var turn = node.getTurn();
        for(PStmt stmt : turn){
            stmt.apply(this);
        }

        // Do EndCondition
        var endcon = node.getEndCondition();
        for(PStmt stmt : endcon){
            stmt.apply(this);
        }

        // Check method bodies
        for(PMethodDcl m : methods){
            m.apply(this);
        }
        // check move bodies
        for(PMethodDcl move : moves)
        {
            move.apply(this);
        }

    }

    @Override
    public void caseASetup(ASetup node) throws TypeException {
        // Add card
        SubClass card;
        card = (SubClass) st.retrieveSymbol("card");
        if(card == null) {
            card = new SubClass("card", node.getCard(), null);
            st.enterSymbol(card);
        }
        current = card;
        node.getCard().apply(this);
        current = null;

        // Add player
        SubClass player;
        player = (SubClass) st.retrieveSymbol("player");
        if(player == null) {
            player = new SubClass("player", node.getPlayer(), null);
            st.enterSymbol(player);
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
    public void caseAMethodDcl(AMethodDcl node) throws TypeException {
        // check name
        var name = node.getName().getText();
        if(st.declaredLocally(name)){
            var fun = st.retrieveSymbol(name);
            if(fun.getDeclarationNode().equals(node)){
                st.openScope();
                for(var p : ((Function)fun).getArgs()){
                    if(st.declaredLocally(p.getIdentifier())){
                        throw new IdentifierAlreadyExistsException(null,"Parameter " + p.getIdentifier() + " in function " + fun.getIdentifier());
                    }
                    st.enterSymbol(p);
                }
                // Check body
                var prev = current;
                current = null;
                for (PStmt stmt : node.getBody()) {
                    stmt.apply(this);
                }
                current = prev;
                st.closeScope();
            }
            throw new IdentifierAlreadyExistsException(node.getName(), fun + " already exsists");
        }
        else {
            // Declare function
            var returnType = node.getReturntype();
            //Check returntype
            returnType.apply(this);
            var method = new Function(name, node, returnType.toString());

            var prev = current;
            current = method;
            // Check arguments
            for (PParamDcl pd : node.getParams()) {
                pd.apply(this);
            }

            current = prev;
            addToCurrent(method);
        }

    }

    @Override
    public void caseAParamDcl(AParamDcl node) throws TypeException {
        // Check type is valid
        node.getType().apply(this);

        // Check name is valid
        var name = node.getName().getText();
        if(((Function)current).containsArg(name)){
            throw new IdentifierAlreadyExistsException(node.getName(),"A parameter with " + name
                    + " already exists in method "+ ((AMethodDcl)node.parent()).getName().getText());
        }
        addToCurrent(new Variable(name, node, node.getType().toString()));
    }

    @Override
    public void caseAClassBody(AClassBody node) throws TypeException {
        if(current != null){

            // Add locals
            for(PStmt dcl : node.getDcls()){
                // Check if statement is a declare
                if(dcl instanceof ADclStmt){
                    // Each var in declare statement
                    dcl.apply(this);
                }
                else{
                    // Statement is not a declare
                    throw new TypeException(null,"A subclass can only have declare statements.");
                }
            }

            // Add method declarations
            for(PMethodDcl md : node.getMethods()){
                md.apply(this);
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
    public void caseASubclass(ASubclass node) throws TypeException {
        // check name
        var name = node.getName().getText();
        if(st.containsClass(name)){
            // Name already exists
            throw new TypeException(node.getName(),"A (sub)class named " + name + " already exists.");
        }
        // Do class body
        var sub = new SubClass(name,node, (SubClass) current);
        st.enterSymbol(sub);
        current = sub;
        node.getBody().apply(this);
        current = sub.getSuperClass();
        // Add subclass to superclass
        ((SubClass)current).addSubclass(sub);

    }

    @Override
    public void caseADclStmt(ADclStmt node) throws TypeException {
        // Validate type
        var typeNode = node.getType();
        typeNode.apply(this);
        var type = node.getType().toString().trim();
        for(PSingleDcl sDcl : node.getDcls()){
            // Validate singleDcl
            sDcl.apply(this);

            // expression type and declare type are the same
            Variable v;
            if(typeNode instanceof AVarType) {
                v = new Variable(((ASingleDcl) sDcl).getId().getText(), sDcl, type);
            }
            else if(typeNode instanceof AListType){
                v = new GenerecVariable(((ASingleDcl) sDcl).getId().getText(), sDcl,"list",type);
            }
            else{
                throw new TypeException(null,"An unknown type error occurred");
            }
            addToCurrent(v);
        }
    }

    //TODO: check for subclass
    @Override
    public void caseAVarType(AVarType node) throws TypeException {
        var s = st.retrieveSymbol(node.getType().getText());
        if(! (s instanceof SubClass)){
            //Type is not a valid type
            throw new InvalidTypeException(node.getType(),"");
        }
    }

    @Override
    public void caseAListType(AListType node) throws TypeException {
        var s = st.retrieveSymbol(node.getType().getText());
        if(! (s instanceof SubClass)){
            //Type is not a valid type
            throw new InvalidTypeException(node.getType(),"");
        }
    }

    @Override
    public void caseASingleDcl(ASingleDcl node) throws TypeException {
        // Check if ID exists locally
        if( st.declaredLocally(node.getId().getText())){
            throw new TypeException(node.getId(),"Identifier already exists locally");
        }
        // Check that expression is OK
        if(node.getExpr() != null) {
            node.getExpr().apply(this);
        }
    }

    @Override
    public void caseAAssignStmt(AAssignStmt node) throws TypeException {
        node.getVar().apply(this);
        node.getExpr().apply(this);
    }

    @Override
    public void caseAVal(AVal node) throws TypeException {
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
    public void caseACallCallField(ACallCallField node) throws TypeException {
        var name = node.getId().getText();
        Symbol dcl;
        if(current == null){
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
            var type = ((Function) dcl).getReturnType();

            if(type == null){
                // unknown classvariable
                type = ((GenericClass)st.retrieveSymbol("list")).getClassVariable();
            }
            else if(type.startsWith("list of")){
                // known class variable
                ((GenericClass)st.retrieveSymbol("list")).setClassVariable(type.substring(8));
                type = "list";
            }

            current = st.retrieveSymbol(type);
            if(current == null){
                // Return type dont exist
                throw new InvalidTypeException(node.getId(),((Function) dcl).getReturnType());
            }
        }
        else{
            throw new TypeException(node.getId(),"Method " + node.getId().getText() + " does not exists in the current context");
        }


        node.getId().declarationNode = dcl.getDeclarationNode();
    }

    @Override
    public void caseAFieldCallField(AFieldCallField node) throws TypeException {
        var name = node.getId().getText();
        Symbol dcl = null;
        if(current == null){
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
        current = st.retrieveSymbol(dcl.getType());
        // Handle list
        if(current instanceof GenericClass && dcl instanceof GenerecVariable){
            ((GenericClass) current).setClassVariable(((GenerecVariable) dcl).getClassVariable());
        }

        node.getId().declarationNode = dcl.getDeclarationNode();
    }

    @Override
    public void caseAReturnStmt(AReturnStmt node) throws TypeException {
        node.getExpr().apply(this);
    }

    @Override
    public void caseACallStmt(ACallStmt node) throws TypeException {
        node.getVal().apply(this);
    }

    @Override
    public void caseAIfStmt(AIfStmt node) throws TypeException {
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
    public void caseAForeachStmt(AForeachStmt node) throws TypeException {

        node.getList().apply(this);
        var type = node.getList().type;
        if(!type.startsWith("List of")){
            throw new TypeException(node.getId(),"Iteration variable must be a list");
        }
        type = type.substring(7);

        st.openScope();
        st.enterSymbol(new Variable(node.getId().getText(),node,type));
        for(PStmt s : node.getThen()){
            s.apply(this);
        }
        st.closeScope();
    }

    @Override
    public void caseAForStmt(AForStmt node) throws TypeException {
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
    public void caseASwitchStmt(ASwitchStmt node) throws TypeException {
        node.getVariable().apply(this);
        for(PCase c : node.getCases()){
            c.apply(this);
        }
    }

    @Override
    public void caseACaseCase(ACaseCase node) throws TypeException {
        node.getCase().apply(this);
        st.openScope();
        for(PStmt s : node.getThen()){
            s.apply(this);
        }
        st.closeScope();
    }

    @Override
    public void caseADefaultCase(ADefaultCase node) throws TypeException {
        st.openScope();
        for(PStmt s : node.getThen()){
            s.apply(this);
        }
        st.closeScope();
    }

    @Override
    public void caseAWhileStmt(AWhileStmt node) throws TypeException {
        node.getPredicate().apply(this);
        st.openScope();
        for(PStmt s : node.getThen()){
            s.apply(this);
        }
        st.closeScope();
    }

    @Override
    public void caseAElseIf(AElseIf node) throws TypeException {
        node.getPredicate().apply(this);
        st.openScope();
        for(PStmt s : node.getThen()){
            s.apply(this);
        }
        st.closeScope();
    }

    @Override
    public void caseAEqualityExpr(AEqualityExpr node) throws TypeException {
        node.getL().apply(this);
        node.getR().apply(this);
    }

    @Override
    public void caseARelationExpr(ARelationExpr node) throws TypeException {
        node.getL().apply(this);
        node.getR().apply(this);
    }

    @Override
    public void caseAAddOpExpr(AAddOpExpr node) throws TypeException {
        node.getL().apply(this);
        node.getR().apply(this);
    }

    @Override
    public void caseABoolOpExpr(ABoolOpExpr node) throws TypeException {
        node.getL().apply(this);
        node.getR().apply(this);
    }

    @Override
    public void caseAMultOpExpr(AMultOpExpr node) throws TypeException {
        node.getL().apply(this);
        node.getR().apply(this);
    }

    @Override
    public void caseAListExpr(AListExpr node) throws TypeException {
        for(PElement e : node.getElements()){
            e.apply(this);
        }
    }

    @Override
    public void caseAElement(AElement node) throws TypeException {
        for(PExpr e : node.getValues()){
            e.apply(this);
        }
    }

    @Override
    public void caseAValueExpr(AValueExpr node) throws TypeException {
        node.getVal().apply(this);
    }

}
