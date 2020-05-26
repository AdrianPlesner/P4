package P4.contextualAnalysis;

import P4.CodeGenarator.SemanticException;
import P4.Sable.analysis.DepthFirstAdapter;
import P4.Sable.node.*;
import P4.contextualAnalysis.Symbol.Function;
import P4.contextualAnalysis.SymbolTable;

import java.lang.reflect.Type;
import java.nio.MappedByteBuffer;
import java.util.LinkedList;

public class TypeChecker extends DepthFirstAdapter {

    SymbolTable st;
    private TokenFinder tf = new TokenFinder();

    public TypeChecker(Start ast, SymbolTable _st) throws TypeException, SemanticException {
        st = _st;
        ast.apply(this);
    }

    private void CheckParams(LinkedList<PParamDcl> dclparams, LinkedList<PExpr> actparams) throws SemanticException, TypeException {
        if(dclparams.size() == actparams.size()){
            for(int i = 0; i < dclparams.size(); i++){
                String dclType = "";
                String actType = actparams.get(i).type;
                var pdtype = ((AParamDcl)dclparams.get(i)).getType();
                if(pdtype instanceof AListType){
                    dclType = "list of ";
                }
                dclType += pdtype.toString().trim();
                if(!dclType.equals(actType) && !dclType.equals("element")){
                    actparams.get(i).apply(tf);
                    throw new TypeException(tf.getToken(),"Actual parameter type does not correspond to the type of formal parameter");
                }
            }
        }
    }

    @Override
    public void caseAProg(AProg node) throws TypeException, SemanticException {
        //Apply everything!
        for(Start s : node.includes){
            s.apply(this);
        }
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
    }

    @Override
    public void caseAClassBody(AClassBody node) throws TypeException, SemanticException{
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
    public void caseAConstruct(AConstruct node) throws TypeException, SemanticException{
        for (PStmt ps : node.getBody()){
            ps.apply(this);
        }

        for (PParamDcl pParamDcl : node.getParams()){
            pParamDcl.apply(this);
        }
    }

    @Override
    public void caseALiteralExpr(ALiteralExpr node) throws TypeException, SemanticException {
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
    public void caseAValueExpr(AValueExpr node) throws TypeException, SemanticException {
        // visit children
        node.getVal().apply(this);
        // Set type to type of child
        node.type = node.getVal().type;
    }

    @Override
    public void caseAAddOpExpr(AAddOpExpr node) throws TypeException, SemanticException {
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
                    throw new TypeException(node.getOperator(),"Operation cannot be done with operands of type " + L.type);
                }
            }
        }
        else{
            //Operands are of different types
            //mix of int and float results in float
            if (L.type.equals("int") && R.type.equals("float") || L.type.equals("float") && R.type.equals("int")){
                node.type = "float";
            } // String can mix with int and float, results in string
            else if(L.type.equals("string") || R.type.equals("string") && L.type.equals("int") || L.type.equals("float") || R.type.equals("int") || R.type.equals("float") ){
                node.type = "string";
            }
            else {
                throw new TypeException(node.getOperator(), "Operation cannot be done on operands of type " + L.type + " and " + R.type);
            }
        }
    }

    @Override
    public void caseAMultOpExpr(AMultOpExpr node) throws TypeException, SemanticException{
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
    public void caseAListExpr(AListExpr node) throws TypeException, SemanticException{
        // Each element is an expression
        for (PExpr p : node.getElements()){
            p.apply(this);
        }

        node.type = "list";

        if(!node.getElements().isEmpty()){
            // check if all elements are of the same type
            String type = node.getElements().getFirst().type;
            for(int i = 1; i < node.getElements().size(); i++){
                if(!type.equals(node.getElements().get(i).type)){
                    node.getElements().get(i).apply(tf);
                    throw new TypeException(tf.getToken(),"Elements in list are not of the same type");
                }
            }
            node.type += " of " + type;
        }
    }

    @Override
    public void caseABoolOpExpr(ABoolOpExpr node) throws TypeException, SemanticException {
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
    public void caseARelationExpr(ARelationExpr node) throws TypeException, SemanticException {
        var L = node.getL();
        L.apply(this);
        var R = node.getR();
        R.apply(this);

        if(L.type.equals(R.type)){
            switch (L.type){
                case "int": case "float": {
                    node.type = "bool";
                    break;
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
    public void caseAEqualityExpr(AEqualityExpr node) throws TypeException, SemanticException {
        // Check children
        var L = node.getL();
        L.apply(this);
        var R = node.getR();
        R.apply(this);

        //Only possible if operands are of same type
        if (L.type.equals(R.type) || R.type.equals("null") || L.type.equals("null")){
            node.type = "bool";
        }
        else{
            throw new TypeException(node.getOperator(), "Cannot compare operands of type " + L.type + " and " + R.type);
        }
    }

    @Override
    public void caseAVal(AVal node) throws TypeException, SemanticException {
        // Check children
        var cfs = node.getCallField();
        cfs.getFirst().apply(this);
        for(int i = 1; i < cfs.size() ; i++ ){
            var c = cfs.get(i);
            c.apply(this);
            if(c.type.endsWith("element")){
                var element = cfs.get(i-1).type.split(" ");
                c.type = c.type.replace("element",element[element.length-1]);
            }
        }
        // Node type = type of last element in call sequence
        node.type = node.getCallField().getLast().type;
        if(node.type.contains("element")){
            boolean cont = true;
            int i = 0;
            PCallField c = null;
            while(cont) {
                c = cfs.get(i);
                cont = !c.type.contains("element");
            }
            c.apply(tf);
            throw new TypeException(tf.getToken(),"Unable to determine type");
        }
    }

    @Override
    public void caseAFieldCallField(AFieldCallField node) throws TypeException, SemanticException {
        // Get declaration node
        var dcl = node.getId().declarationNode;
        var dcl2 = st.retrieveSymbol(node.getId().getText());

        if(dcl instanceof ADclStmt){
            // Type of variable is type of declaration node
            var type = ((ADclStmt) dcl).getType();
            var typeTxt = "";
            if(type instanceof AListType){
                typeTxt = "list of ";
            }
            typeTxt += type.toString().trim();
            node.type = typeTxt;
        }
        else if(dcl instanceof AParamDcl){
            // Type of variable is type of declaration node
            var type = ((AParamDcl) dcl).getType();
            var typeTxt = "";
            if(type instanceof AListType){
                typeTxt = "list of ";
            }
            typeTxt += type.toString().trim();
            node.type = typeTxt.trim();
        }
        else if(dcl instanceof AForeachStmt){
            var x = ((AForeachStmt) dcl).getList().type;
            if(x.trim().startsWith("list of")){
                node.type = x.substring(8);
            }
            else{
                throw new TypeException(node.getId(), "Must be a list");
            }
        }
        else if(dcl2 != null && dcl2.getType().equals("null")){
            node.type = "null";
        }
        else{
            throw new TypeException(node.getId(),"An unknown type error occurred");
        }

    }

    @Override
    public void caseACallCallField(ACallCallField node) throws TypeException, SemanticException{
        // Apply on parameters
        node.type = "";
        for (PExpr p: node.getParams()){
            p.apply(this);
        }
        var dclnode = node.getId().declarationNode;

        if (dclnode instanceof AMethodDcl){
            CheckParams(((AMethodDcl) dclnode).getParams(),node.getParams());
            if(((AMethodDcl) dclnode).getReturntype() instanceof AListType){
                node.type = "list of ";
            }
            node.type += ((AMethodDcl) dclnode).getReturntype().toString().trim();
        }
        else if(dclnode instanceof AConstruct){
            CheckParams(((AConstruct) dclnode).getParams(),node.getParams());
            node.type = ((AConstruct) dclnode).getName().getText().trim();
        }
        else {
            throw new TypeException(node.getId(), "An unknown type error has occurred");
        }
    }

    private LinkedList<AReturnStmt> getReturns(LinkedList<PStmt> list ){
        LinkedList<AReturnStmt> result = new LinkedList<>();
        for(PStmt s : list){
            if(s instanceof AReturnStmt){
                result.add((AReturnStmt) s);
            }
            else if( s instanceof AIfStmt){
                result.addAll(getReturns(((AIfStmt) s).getThen()));
                result.addAll(getReturns(((AIfStmt) s).getElse()));
                for(PElseIf e : ((AIfStmt) s).getElseifs()){
                    result.addAll(getReturns(((AElseIf)e).getThen()));
                }
            }
            else if( s instanceof AWhileStmt){
                result.addAll(getReturns(((AWhileStmt) s).getThen()));
            }
            else if(s instanceof AForStmt){
                result.addAll(getReturns(((AForStmt) s).getThen()));
            }
            else if(s instanceof AForeachStmt){
                result.addAll((getReturns(((AForeachStmt) s).getThen())));
            }
        }
        return result;
    }

    @Override
    public void caseAMethodDcl(AMethodDcl node) throws TypeException, SemanticException{
        // Apply on method body
        LinkedList<AReturnStmt> returns = getReturns(node.getBody());
        for(PStmt s : node.getBody() ){
            s.apply(this);
        }
        if(returns.isEmpty() && !node.getReturntype().toString().trim().equals("void")){
            throw new SemanticException(node,"Method does not return a value");
        }
        else{
            String returntype = "";
            if(node.getReturntype() instanceof AListType){
                returntype = "list of ";
            }
            returntype += node.getReturntype().toString().trim();
            for(AReturnStmt s : returns){
                if(!s.getExpr().type.equals(returntype)){
                    if(!s.getExpr().type.equals("null") || ( (returntype.equals("int") || returntype.equals("float") || returntype.startsWith("list")))) {
                        // returning null is allowed, but only with reference typed
                        s.apply(tf);
                        throw new TypeException(tf.getToken(), "Type of return statement does not correspond to return type of function");
                    }
                }
            }
        }
    }

    @Override
    public void caseAAssignStmt(AAssignStmt node) throws TypeException, SemanticException{
        var pVal = node.getVar();
        pVal.apply(this);
        var pExpr = node.getExpr();
        pExpr.apply(this);


        if(pExpr.type.equals("list of element")){
            String[] type = pVal.type.split(" ");
            pExpr.type = "list of " + type[type.length-1];
        }
        else if(pExpr.type.equals("element")){
            pExpr.type = pVal.type;
        }

        if (!pVal.type.equals(pExpr.type)){
            // If types are incompatible, throw exception.
            throw new TypeException(node.getOperation(), "Cannot assign type " + pExpr.type + " to type " + pVal.type);
        }

    }

    @Override
    public void caseAReturnStmt(AReturnStmt node) throws TypeException, SemanticException{
        var expr = node.getExpr();
        expr.apply(this);
    }

    @Override
    public void caseAForStmt(AForStmt node) throws TypeException, SemanticException{
        // init is an assign statement. Has already been type checked.
        var initStmt = node.getInit();
        initStmt.apply(this);

        // Predicate is an expression.
        var predicate = node.getPredicate();
        predicate.apply(this);
        if (!predicate.type.equals("bool")){
            node.apply(tf);
            throw new TypeException(tf.getToken(), "Predicate is not of type boolean");
        }

        // Apply to the statement body
        for (PStmt s : node.getThen()){
            s.apply(this);
        }

        // Update is a statement.
        var update = node.getUpdate();
        update.apply(this);

    }

    @Override
    public void caseAWhileStmt(AWhileStmt node) throws TypeException, SemanticException{
        // Predicate is an expression.
        var predicate = node.getPredicate();
        predicate.apply(this);
        if (!(predicate.type.equals("bool"))){
            node.apply(tf);
            throw new TypeException(tf.getToken(), "Predicate does not return type boolean");
        }

        // Apply on statement body
        for (PStmt s : node.getThen()){
            s.apply(this);
        }

    }

    @Override
    public void caseASwitchStmt(ASwitchStmt node) throws TypeException, SemanticException{

        node.getVariable().apply(this);

        var vartype = node.getVariable().type;
        if(!(vartype.equals("int") || vartype.equals("float") || vartype.equals("string"))){
            throw new SemanticException(node,"Switch can only be performed on variables of type int, float or string");
        }

        // Apply on each case
        for (PCase pc : node.getCases()){
            pc.apply(this);
            if(pc instanceof ACaseCase){
                if(!((ACaseCase) pc).getCase().type.equals(vartype)){
                    pc.apply(tf);
                    throw new TypeException(tf.getToken(),"Type of case statement does not correspond to type of switch variable");
                }
            }
        }
    }

    @Override
    public void caseACaseCase(ACaseCase node) throws TypeException, SemanticException{
        // Each case is an expression
        var pExpr = node.getCase();
        pExpr.apply(this);

        // Apply on body of each case
        for (PStmt s : node.getThen()){
            s.apply(this);
        }

    }

    @Override
    public void caseADefaultCase(ADefaultCase node) throws TypeException, SemanticException{
        // Apply to body of the default statement
        for (PStmt ps : node.getThen()){
            ps.apply(this);
        }
    }

    @Override
    public void caseAForeachStmt(AForeachStmt node) throws TypeException, SemanticException{
        // Foreach stmt only works on collections
        var pVal = node.getList();
        pVal.apply(this);
        if (!pVal.type.startsWith("list")){
            throw new TypeException(node.getId(), pVal.type + " is not a list");
        }
        // Apply on statement body
        for (PStmt ps : node.getThen()){
            ps.apply(this);
        }


    }

    @Override
    public void caseAIfStmt(AIfStmt node) throws TypeException, SemanticException{
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
    public void caseAElseIf(AElseIf node) throws TypeException, SemanticException{
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
    public void caseADclStmt(ADclStmt node) throws TypeException, SemanticException{
        node.getType().apply(this);

        String type = "";
        if(node.getType() instanceof AListType){
            type = "list of ";
        }
        type += node.getType().toString().trim();

        for (PSingleDcl dcl : node.getDcls()){
            dcl.apply(this);
            var expr = ((ASingleDcl)dcl).getExpr();

            if(expr !=null && !expr .type.equals(type)){
                if(expr instanceof AListExpr && !((AListExpr) expr).getElements().isEmpty()){
                    dcl.apply(tf);
                    throw new TypeException(tf.getToken(),"Type of expression does not match type of declaration");
                }
            }
        }
    }

    @Override
    public void caseASubclass(ASubclass node) throws TypeException, SemanticException{
        var body = node.getBody();
        body.apply(this);
    }

    @Override
    public void caseASetup(ASetup node) throws TypeException, SemanticException{
        var card = node.getCard();
        card.apply(this);
        var player = node.getPlayer();
        player.apply(this);

        for(PStmt p : node.getGame()){
            p.apply(this);
        }
    }

    @Override
    public void caseASingleDcl(ASingleDcl node) throws TypeException, SemanticException{
        var expr = node.getExpr();
        if (expr != null){
            expr.apply(this);
        }
    }

    @Override
    public void caseACallStmt(ACallStmt node) throws TypeException, SemanticException{
        var val = node.getVal();
        val.apply(this);
    }
}
