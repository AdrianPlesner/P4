package P4.contextualAnalysis;

import P4.Sable.analysis.DepthFirstAdapter;
import P4.Sable.node.*;
import P4.contextualAnalysis.SymbolTable;

public class TypeChecker extends DepthFirstAdapter {

    SymbolTable st;

    public TypeChecker(Start ast, SymbolTable _st) throws TypeException {
        ast.apply(this);
        st = _st;
    }

    @Override
    public void caseStart(Start node) throws TypeException {
        // Check everything. No need to change anything
        super.caseStart(node);
    }

    @Override
    public void caseALiteralExpr(ALiteralExpr node) throws TypeException {
        // Set type of literal
        node.getValue().apply(this);

        // Set expr type to type of literal
        node.type = node.getValue().type;

    }

    @Override
    public void caseAIntLiteral(AIntLiteral node) {
        // Set type of int literal
        node.type = "int";
    }

    @Override
    public void caseABoolLiteral(ABoolLiteral node) {
        // Set type of boolean literal
        node.type = "bool";
    }

    @Override
    public void caseAFloatLiteral(AFloatLiteral node) {
        // Set type of float literal
        node.type = "float";
    }

    @Override
    public void caseAStringLiteral(AStringLiteral node){
        // Set type of string literal
        node.type = "string";
    }

    @Override
    public void caseAValueExpr(AValueExpr node) throws TypeException {
        // visit children
        node.getVal().apply(this);
        // Set type to type of child
        node.type = node.getVal().type;
    }

    @Override
    public void caseAAddOpExpr(AAddOpExpr node) throws TypeException {
        // check children
        var L = node.getL();
        L.apply(this);
        var R = node.getR();
        R.apply(this);

        if(L.type.equals(R.type)){
            // each operand is of the same time. check it is a type that can do the operation
            switch(L.type){
                case "string":{
                    if(node.getOperator().getText().trim().equals("-")){
                        // cant substract strings
                        throw new TypeException(node.getOperator(),"Cannot substract strings");
                    }
                }
                case "int": case "float":{
                    node.type = L.type;
                    break;
                }
                default:{
                    // incompatible types
                    throw new TypeException(node.getOperator(),"Operation cannot be done on operands of type " + L.type);
                }
            }
        }
        else{
            // operands is of different types
            //TODO: check compatibility
        }
    }

    @Override
    public void caseAMultOpExpr(AMultOpExpr node) throws TypeException{
        var L = node.getL();
        L.apply(this);
        var R = node.getR();
        R.apply(this);

        if (L.type.equals(R.type)){
            //Each operand is of the same type.
            switch (L.type){
                case "int": case "float":{
                    node.type = L.type;
                    break;
                }
                default:{
                    // incompatible types
                    throw new TypeException(node.getOperator(), "Operation cannot be done on operands of type " + L.type);
                }
            }
        }
        else {
            //Operands are of different types
            if (L.type.equals("int") && R.type.equals("float") || L.type.equals("float") && R.type.equals("int")){
                node.type = "float";
            } else {
                throw new TypeException(node.getOperator(), "Operation cannot be done on operands of type " + L.type + " and " + R.type);
            }

        }
    }

    @Override
    public void caseABoolOpExpr(ABoolOpExpr node) throws TypeException {
        var L = node.getL();
        L.apply(this);
        var R = node.getR();
        R.apply(this);

        if(L.type.equals("bool") && R.type.equals("bool")){
            node.type = "bool";
        }
        else {
            throw new TypeException(node.getOperator(), "Expression is not of type boolean");
        }
    }

    @Override
    public void caseARelationExpr(ARelationExpr node) throws TypeException {
        var L = node.getL();
        L.apply(this);
        var R = node.getR();
        R.apply(this);

        if(L.type.equals(R.type)){
            switch (L.type){
                case "int": case "float": {
                    node.type = "bool";
                }
                default:{
                    //Types that cannot be compared
                    throw new TypeException(node.getOperator(), "Cannot compare operands of type " + L.type);
                }
            }
        }
        else{
            if(L.type.equals("int") && R.type.equals("float") || R.type.equals("int") && L.type.equals("float")){
                node.type = "bool";
            }
            else {
                throw new TypeException(node.getOperator(), "Cannot compare operands of type " + L.type + "and " + R.type);
            }
        }
    }

    @Override
    public void caseAEqualityExpr(AEqualityExpr node) throws  TypeException {
        // Check children
        var L = node.getL();
        L.apply(this);
        var R = node.getR();
        R.apply(this);

        //TODO: Reference types?
        //Only possible if operands are of same type
        if (L.type.equals(R.type)){
            node.type = "bool";
        }
        else{
            throw new TypeException(node.getOperator(), "Cannot compare operands of type " + L.type + " and " + R.type);
        }
    }

    @Override
    public void caseAVal(AVal node) throws TypeException {
        // Check children
        for(PCallField cf : node.getCallField()){
            cf.apply(this);
        }

        // Node type = type of last element in call sequence
        node.type = node.getCallField().getLast().type;
    }


    @Override
    public void caseAFieldCallField(AFieldCallField node) throws TypeException {
        // Get declaration node
        var dcl = node.getId().declarationNode;
        if(dcl instanceof ASingleDcl){
            // Type of variable is type of declaration node
            node.type = ((ASingleDcl) dcl).getExpr().type;
        }
        else{
            // if declaration node is not a ASINGLEDCL something went wrong
            throw new TypeException(node.getId(),"An unknown type error occurred");
        }

    }

    @Override
    public void caseACallCallField(ACallCallField node) throws TypeException{
        // Apply on parameters
        for (PExpr p: node.getParams()){
            p.apply(this);
        }

        // Get declaration node
        var dcl = node.getId().declarationNode;
        if (dcl instanceof AMethodDcl){
            node.type = st.retrieveSymbol(((AMethodDcl) dcl).getName().toString()).getType();
        }
        else {
            throw new TypeException(node.getId(), "An unknown type error has occurred");
        }
    }

    @Override
    public void caseAMethodDcl(AMethodDcl node) throws TypeException{
        for(PStmt s: node.getBody()){
            s.apply(this);
        }
    }


}
