package P4.symbolTable;

import P4.Sable.analysis.DepthFirstAdapter;
import P4.Sable.node.*;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedList;
import java.util.List;

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
        //TODO: include includes
        var includes = node.getIncludes();
        for(TId inc : includes){
            // Something about reading a file and adding everything to symboltable
        }

        // Do Setup
        if(node.getSetup() != null){
            node.getSetup().apply(this);
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

        // Do methods
        var methods = node.getMethods();
        for(PMethodDcl m : methods){
            m.apply(this);
        }
    }

    @Override
    public void caseASetup(ASetup node) throws TypeException {
        // Add card
        var card = new SubClass("card",node.getCard(),null);
        current = card;
        node.getCard().apply(this);
        current = null;
        st.enterSymbol(card.getIdentifier(),card);

        // Do public
        for(PStmt ps : node.getPublic()){
            ps.apply(this);
        }

        // Add player
        var player = new SubClass("player",node.getPrivate(),null);
        current = player;
        node.getPrivate().apply(this);
        current = null;
        st.enterSymbol(player.getIdentifier(),player);

        // Do further dcls
        for(PStmt ps : node.getDcls()){
            ps.apply(this);
        }
    }

    @Override
    public void caseAMethodDcl(AMethodDcl node) throws TypeException {
        // check name
        var name = node.getName().getText();
        if(st.declaredLocally(name)){
            throw new TypeException(node.getName(),"Identifier already exists locally");
        }

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
        //report error
        throw new TypeException(null,"An unknown type error occurred.");
    }

    @Override
    public void caseASubclass(ASubclass node) throws TypeException {
        // check name
        var name = node.getName().getText();
        if(! st.containsClass(name)){
            // Name already exists
            throw new TypeException(node.getName(),"A (sub)class named " + name);
        }
        // Do class body
        var sub = new SubClass(name,node, (SubClass) current);
        current = sub;
        node.getBody().apply(this);
        current = sub.getSuperClass();
        // Add subclass to superclass
        ((SubClass)current).addSubclass(sub);
        st.enterSymbol(sub.getIdentifier(),sub);
    }

    @Override
    public void caseADclStmt(ADclStmt node) throws TypeException {
        // Validate type
        node.getType().apply(this);
        var type = node.getType().toString();
        for(PSingleDcl sDcl : node.getDcls()){
            // Validate singleDcl
            sDcl.apply(this);
            // If returned without exception, it is valid
            var exp = ((ASingleDcl) sDcl).getExpr();
            if(exp != null){
                if(!exp.type.equals(type)){
                    throw new TypeException(null,"Cannot assign " + exp.type + " to variable of type " + type);
                }
            }
            // expression type and declare type are the same
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
}
