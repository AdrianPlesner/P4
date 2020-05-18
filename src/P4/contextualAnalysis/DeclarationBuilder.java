package P4.contextualAnalysis;

import P4.CodeGenarator.SemanticException;
import P4.Sable.node.*;
import P4.contextualAnalysis.Symbol.Function;
import P4.contextualAnalysis.Symbol.SubClass;
import P4.contextualAnalysis.Symbol.Symbol;
import P4.contextualAnalysis.Symbol.Variable;

import java.util.LinkedList;

public class DeclarationBuilder extends STBuilder {

    public DeclarationBuilder(Start ast) {
        super(ast);
    }

    @Override
    public SymbolTable BuildST(SymbolTable st) throws TypeException, SemanticException {
        this.st = st;
        ast.apply(this);
        return this.st;
    }

    @Override
    public void caseStart(Start node) throws TypeException, SemanticException {
        node.getPProg().apply(this);
    }

    @Override
    public void caseAProg(AProg node) throws TypeException, SemanticException {

        node.getSetup().apply(this);
        for(PMethodDcl m : node.getMethods()){
            m.apply(this);
        }
        for(PMethodDcl m : node.getMoves()){
            m.apply(this);
        }
    }

    @Override
    public void caseASetup(ASetup node) throws TypeException, SemanticException {
        // Add card
        SubClass card;
        card = (SubClass) st.retrieveSymbol("card", SubClass.class);
        if(card == null) {
            card = new SubClass("card", node.getCard(), null);
            st.enterSymbol(card);
        }
        current = card;
        node.getCard().apply(this);
        current = null;

        // Add player
        SubClass player;
        player = (SubClass) st.retrieveSymbol("player", SubClass.class);
        if(player == null) {
            player = new SubClass("player", node.getPlayer(), null);
            st.enterSymbol(player);
        }
        current = player;
        node.getPlayer().apply(this);
        current = null;
    }

    @Override
    public void caseAClassBody(AClassBody node) throws TypeException, SemanticException {
        if(current != null) {
            var th = new Variable("this", new ADclStmt(new AVarType(new TId(current.getIdentifier())), new LinkedList<>()), current.getIdentifier());
            st.enterSymbol(th);
            ((SubClass)current).addLocal(th);
            // Add locals
            for (PStmt dcl : node.getDcls()) {
                // Check if statement is a declare
                if (dcl instanceof ADclStmt) {
                    // Each var in declare statement
                    dcl.apply(this);
                } else {
                    // Statement is not a declare
                    throw new TypeException(null, "A subclass can only have declare statements.");
                }
            }
            super.caseAClassBody(node);
        }else {
            //report error
            throw new TypeException(null, "An unknown type error occurred.");
        }
    }

    @Override
    public void caseAConstruct(AConstruct node) throws TypeException, SemanticException {
        var name = node.getName();
        if( !name.getText().equals(current.getIdentifier())){
            throw new TypeException(name,"Constructor must have same name as class");
        }
        var construct = new Function(name.getText(),node,name.getText());
        var prev = current;
        current = null;
        st.enterSymbol(construct);
        current = prev;
    }

    @Override
    public void caseAMethodDcl(AMethodDcl node) throws TypeException, SemanticException {
        // get name
        var name = node.getName().getText();
        // check if name already exists
        Symbol local = st.retrieveSymbol(name);
        // Declare function
        var returnType = node.getReturntype();
        //Check returntype
        returnType.apply(this);
        var type = returnType.toString().trim();
        // Handle list return type
        if(returnType instanceof AListType){
            if(type.equals("void")){
                type = "list";
            }
            else{
                type = "list of " + type;
            }
        }
        // Create method symbol
        var method = new Function(name, node, type);

        var prev = current;
        current = method;
        // Check arguments
        for (PParamDcl pd : node.getParams()) {
            pd.apply(this);
        }
        // Globl function in current scope
        current = prev;
        // Add function to current scope
        addToCurrent(method);
    }

    @Override
    public void caseASubclass(ASubclass node) throws TypeException, SemanticException {
        // check name
        var name = node.getName().getText();
        if(st.containsClass(name)){
            // Name already exists
            throw new TypeException(node.getName(),"A (sub)class named " + name + " already exists.");
        }
        var sub = new SubClass(name,node, (SubClass) current);
        st.enterSymbol(sub);
        current = sub;
        node.getBody().apply(this);
        current = sub.getSuperClass();
        // Add subclass to superclass
        ((SubClass) current).addSubclass(sub);
    }
}
