package P4.contextualAnalysis;

import P4.Sable.analysis.DepthFirstAdapter;
import P4.Sable.node.*;

import java.util.LinkedList;

public class STBuilder extends DepthFirstAdapter {

    private Start ast;

    private SymbolTable st;

    private Symbol current;

    private LinkedList<TypeError> errors = new LinkedList<>();

    public STBuilder(Start ast) {
        this.ast = ast;
    }

    // Construct the given symbol table from the ast
    public SymbolTable BuildST(SymbolTable st) throws TypeException {
        this.st = st;
        // Add buildin classes
        st.enterSymbol("int",new SubClass("int",null,null));
        st.enterSymbol("float",new SubClass("float",null,null));
        st.enterSymbol("string",new SubClass("string",null,null));
        st.enterSymbol("bool", new SubClass("bool",null,null));
        st.enterSymbol("void",new SubClass("void",null,null));
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
            st.enterSymbol(v.getIdentifier(),v);
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
            st.enterSymbol(f.getIdentifier(),f);
        }
        else if(current instanceof SubClass){
            ((SubClass) current).addMethod(f);
        }
        else{
            throw new TypeException(null,"An unknown type error occurred");
        }
    }

    @Override
    public void caseAProg(AProg node) throws TypeException {

        var includes = node.getIncludes();
        for(TId inc : includes){
            // Something about reading a file and adding everything to symboltable
            //TODO: include includes
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

        // check move bodies


    }

    @Override
    public void caseASetup(ASetup node) throws TypeException {
        // Add card
        var card = new SubClass("card",node.getCard(),null);
        st.enterSymbol(card.getIdentifier(),card);
        current = card;
        node.getCard().apply(this);
        current = null;

        // Add player
        var player = new SubClass("player",node.getPlayer(),null);
        st.enterSymbol(player.getIdentifier(),player);
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
                    st.enterSymbol(p.getIdentifier(),p);
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

            // Add methods
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
        st.enterSymbol(sub.getIdentifier(),sub);
        current = sub;
        node.getBody().apply(this);
        current = sub.getSuperClass();
        // Add subclass to superclass
        ((SubClass)current).addSubclass(sub);

    }

    @Override
    public void caseADclStmt(ADclStmt node) throws TypeException {
        // Validate type
        node.getType().apply(this);
        var type = node.getType().toString();
        for(PSingleDcl sDcl : node.getDcls()){
            // Validate singleDcl
            sDcl.apply(this);
            //TODO: move to typechecker
            /*
            // If returned without exception, it is valid

            var exp = ((ASingleDcl) sDcl).getExpr();
            if(exp != null){
                if(!exp.type.equals(type)){
                    throw new TypeException(null,"Cannot assign " + exp.type + " to variable of type " + type);
                }
            }
            // expression type and declare type are the same*/
            var v = new Variable(((ASingleDcl) sDcl).getId().getText(),sDcl,type);
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
        node.getType().setText("List of "+s.getIdentifier());
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
        Symbol now;
        var prev = current;
        for(PCallField cf : node.getCallField()){
            //Check call/field is valid
            cf.apply(this);

            // Get next symbol in the trace
            if(cf instanceof ACallCallField){
                now = st.retrieveSymbol(((ACallCallField) cf).getId().getText());
                now = st.retrieveSymbol(((Function)now).getReturnType());
            }
            else if(cf instanceof  AFieldCallField){
                now = st.retrieveSymbol(((AFieldCallField) cf).getId().getText());
            }
            else{
                throw new TypeException(null,"An unknown type error occurred");
            }
            current = now;
        }
        current = prev;
    }

    @Override
    public void caseACallCallField(ACallCallField node) throws TypeException {
        var name = node.getId().getText();
        if(current == null){
            // Base call, lookup at symbol table
            if(st.retrieveSymbol(name) == null ){
                throw new TypeException(node.getId(),"Method " + node.getId().getText() + " does not exists in the current context");
            }
        }
        else{
            // Call on a variable
            if(current instanceof SubClass){
                if(!((SubClass) current).containsMethod(name)){
                    throw new TypeException(node.getId(),"");
                }
            }
            // Call on the result of a function call
            else if( current instanceof Function){
                if(!((SubClass)st.retrieveSymbol(((Function) current).getReturnType())).containsMethod(name)){
                    throw new TypeException(node.getId(),"");
                }
            }
            else{
                throw new TypeException(null,"An unknown type error occurred");
            }
        }
    }

    @Override
    public void caseAFieldCallField(AFieldCallField node) throws TypeException {
        var name = node.getId().getText();
        if(current == null){
            // Base call, lookup at symboltable
            if(st.retrieveSymbol(name) == null){
                throw new TypeException(node.getId(),"Variable " + name + " does not exists in the current context");
            }
        }
        else{
            // Field on variable
            if(current instanceof SubClass){

            }
            // Field on result from function call
        }

    }
}
