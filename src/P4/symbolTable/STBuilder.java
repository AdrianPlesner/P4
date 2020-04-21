package P4.symbolTable;

import P4.Sable.analysis.DepthFirstAdapter;
import P4.Sable.node.*;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class STBuilder extends DepthFirstAdapter {

    private Start ast;

    private SymbolTable st;

    private Symbol current;

    public STBuilder(Start ast) {
        this.ast = ast;
    }

    // Construct the given symbol table from the ast
    public SymbolTable BuildST(SymbolTable st) throws TypeException {
        this.st = st;
        ast.apply(this);
        return this.st;
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
        var card = new SubClass("Card",node);
        current = card;
        node.getCard().apply(this);
        current = null;
        st.enterSymbol(card.getIdentifier(),card);
    }

    @Override
    public void caseAClassBody(AClassBody node) throws TypeException {
        if(current != null){
            // Get local list
            var locals = ((SubClass)current).getLocals();
            // Add locals
            for(PStmt dcl : node.getDcls()){
                // Check if statement is a declare
                if(dcl instanceof ADclStmt){
                    // Each var in declare statement
                    for(PSingleDcl sDcl : ((ADclStmt) dcl).getDcls() ){
                        //Extract expression
                        var exp = ((ASingleDcl) sDcl).getExpr();

                        if(exp != null) {
                            // Determine type of expression
                            exp.apply(this);

                            // Check if expression and declare is same type
                            if (!exp.type.equals(((ADclStmt) dcl).getType().toString())) {
                                throw new TypeException(null,"Cannot assign " + exp.type + " to variable of type " + ((ADclStmt) dcl).getType().toString());
                            }
                        }
                        // Everything OK add variable to list
                        locals.add(new Variable(((ASingleDcl) sDcl).getId().getText(), dcl,((ADclStmt) dcl).getType().toString()));
                    }
                }
                else{
                    // Statement is not a declare
                    throw new TypeException(null,"A subclass can only have declare statements.");
                }
            }
        }
        //report error
    }
}
