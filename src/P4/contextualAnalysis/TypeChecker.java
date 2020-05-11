package P4.contextualAnalysis;

import P4.Sable.analysis.DepthFirstAdapter;
import P4.Sable.node.*;
import P4.contextualAnalysis.Symbol.Function;
import P4.contextualAnalysis.SymbolTable;

import java.lang.reflect.Type;
import java.nio.MappedByteBuffer;

public class TypeChecker extends DepthFirstAdapter {

    SymbolTable st;
    private TokenFinder tf = new TokenFinder();

    public TypeChecker(Start ast, SymbolTable _st) throws TypeException {
        st = _st;
        ast.apply(this);
    }

    @Override
    public void caseStart(Start node) throws TypeException {
        // Check everything. No need to change anything
        super.caseStart(node);
    }

    @Override
    public void caseAProg(AProg node) throws TypeException{
        //Apply everything!
        var setup = node.getSetup();
        setup.apply(this);

        for (PMethodDcl moves : node.getMoves()){
            moves.apply(this);
        }
        for (PStmt turn : node.getTurn()){
            turn.apply(this);
        }
        for (PStmt endCondition : node.getEndCondition()){
            endCondition.apply(this);
        }
        for (PMethodDcl methods : node.getMethods()){
            methods.apply(this);
        }

        //TODO: What to do with linked list of TId?
    }

    @Override
    public void caseAClassBody(AClassBody node) throws TypeException{
        // Apply everything
        for (PStmt ps : node.getDcls()){
            ps.apply(this);
        }
        for (PMethodDcl pMetDcl : node.getMethods()){
            pMetDcl.apply(this);
        }
        for (PSubclass pSub : node.getSubclasses()){
            pSub.apply(this);
        }

        var pConstruct = node.getConstruct();
        if(pConstruct != null) {
            pConstruct.apply(this);
        }
    }

    @Override
    public void caseAConstruct(AConstruct node) throws TypeException{
        for (PStmt ps : node.getBody()){
            ps.apply(this);
        }

        for (PParamDcl pParamDcl : node.getParams()){
            pParamDcl.apply(this);
        }

        //TODO: Probably not done
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
            //Operands are of different types
            if (L.type.equals("int") && R.type.equals("float") || L.type.equals("float") && R.type.equals("int")){
                node.type = "float";
            } else {
                throw new TypeException(node.getOperator(), "Operation cannot be done on operands of type " + L.type + " and " + R.type);
            }
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
    public void caseAListExpr(AListExpr node) throws TypeException{
        // Each element is an expression
        for (PExpr p : node.getElements()){
            p.apply(this);
        }
        node.type = node.getElements().getFirst().type;
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
        if(dcl instanceof ADclStmt){
            // Type of variable is type of declaration node
            var type = ((ADclStmt) dcl).getType();
            var typeTxt = "";
            if(type instanceof AListType){
                typeTxt = "list of ";
            }
            typeTxt += type.toString();
            node.type = typeTxt;
        }
        else if(dcl instanceof AParamDcl){
            // Type of variable is type of declaration node
            var type = ((AParamDcl) dcl).getType();
            var typeTxt = "";
            if(type instanceof AListType){
                typeTxt = "list of ";
            }
            typeTxt += type.toString();
            node.type = typeTxt;
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
        var dcl = st.retrieveSymbol(node.getId().getText());

        if (dcl instanceof Function){
            node.type = ((Function) dcl).getReturnType();
        }
        else {
            throw new TypeException(node.getId(), "An unknown type error has occurred");
        }
    }

    @Override
    public void caseAMethodDcl(AMethodDcl node) throws TypeException{
        // Apply on method body
        for(PStmt s: node.getBody()){
            s.apply(this);
        }
    }

    @Override
    public void caseAAssignStmt(AAssignStmt node) throws TypeException{
        var pVal = node.getVar();
        pVal.apply(this);
        var pExpr = node.getExpr();
        pExpr.apply(this);

        if (!pVal.type.equals(pExpr.type)){
            // If types are incompatible, throw exception.
            throw new TypeException(node.getOperation(), "Cannot assign type " + pVal.type + " to type " + pExpr.type);
        }

        //TODO: Finish AAssignStmt
    }


    @Override
    public void caseAReturnStmt(AReturnStmt node) throws TypeException{
        var expr = node.getExpr();
        expr.apply(this);
        //TODO: Fix this
    }

    @Override
    public void caseAForStmt(AForStmt node) throws TypeException{
        // Apply to the statement body
        for (PStmt s : node.getThen()){
            s.apply(this);
        }

        // init is an assign statement. Has already been type checked.
        var initStmt = node.getInit();
        initStmt.apply(this);
        // Update is a statement.
        var update = node.getUpdate();
        update.apply(this);

        // Predicate is an expression.
        var predicate = node.getPredicate();
        predicate.apply(this);
        if (!predicate.type.equals("bool")){
            node.apply(tf);
            throw new TypeException(tf.getToken(), "Predicate does not return type boolean");
        }
    }

    @Override
    public void caseAWhileStmt(AWhileStmt node) throws TypeException{
        // Apply on statement body
        for (PStmt s : node.getThen()){
            s.apply(this);
        }
        // Predicate is an expression.
        var predicate = node.getPredicate();
        predicate.apply(this);
        if (!(predicate.type.equals("bool"))){
            node.apply(tf);
            throw new TypeException(tf.getToken(), "Predicate does not return type boolean");
        }
    }

    @Override
    public void caseASwitchStmt(ASwitchStmt node) throws TypeException{
        // Apply on each case
        for (PCase pc : node.getCases()){
            pc.apply(this);
        }

        //TODO: Do we care about the type of the value?
    }

    @Override
    public void caseACaseCase(ACaseCase node) throws TypeException{
        // Apply on body of each case
        for (PStmt s : node.getThen()){
            s.apply(this);
        }

        // Each case is an expression
        var pExpr = node.getCase();
        pExpr.apply(this);
        //TODO: Make correct.
    }

    @Override
    public void caseADefaultCase(ADefaultCase node) throws TypeException{
        // Apply to body of the default statement
        for (PStmt ps : node.getThen()){
            ps.apply(this);
        }
    }

    @Override
    public void caseAForeachStmt(AForeachStmt node) throws TypeException{
        // Apply on statement body
        for (PStmt ps : node.getThen()){
            ps.apply(this);
        }

        // Foreach stmt only works on collections
        var pVal = node.getList();
        pVal.apply(this);
        if (!pVal.type.equals("list")){
            throw new TypeException(node.getId(), pVal.type + " is not a collection");
        }
    }

    @Override
    public void caseAIfStmt(AIfStmt node) throws TypeException{
        var expr = node.getPredicate();
        expr.apply(this);
        for (PStmt ps : node.getThen()){
            ps.apply(this);
        }
        for (PElseIf ps : node.getElseifs()){
            ps.apply(this);
        }
        for (PStmt ps : node.getElse()){
            ps.apply(this);
        }

        node.apply(tf);
        if(!expr.type.equals("bool")){
            throw new TypeException(tf.getToken(), "Predicate is not of type boolean");
        }
    }

    @Override
    public void caseAElseIf(AElseIf node) throws TypeException{
        // Apply on statement body
        for (PStmt ps : node.getThen()){
            ps.apply(this);
        }
        // Check predicate for type correctness
        var predicate = node.getPredicate();
        predicate.apply(this);
        if (!predicate.type.equals("bool")){
            node.apply(tf);
            throw new TypeException(tf.getToken(), "Predicate is not of type boolean");
        }
    }

    @Override
    public void caseADclStmt(ADclStmt node) throws TypeException{
        for (PSingleDcl dcl : node.getDcls()){
            dcl.apply(this);
        }
    }

    @Override
    public void caseASubclass(ASubclass node) throws TypeException{
        var body = node.getBody();
        body.apply(this);
    }

    @Override
    public void caseASetup(ASetup node) throws TypeException{
        var card = node.getCard();
        card.apply(this);
        var player = node.getPlayer();
        player.apply(this);

        for(PStmt p : node.getGame()){
            p.apply(this);
        }

    }


    @Override
    public void caseASingleDcl(ASingleDcl node) throws TypeException{
        var expr = node.getExpr();

        if (expr != null){
            expr.apply(this);
        }


    }

    @Override
    public void caseACallStmt(ACallStmt node) throws TypeException{
        var val = node.getVal();
        val.apply(this);
    }

}
