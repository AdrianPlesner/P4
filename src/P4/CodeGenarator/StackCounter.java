package P4.CodeGenarator;

import P4.Sable.analysis.DepthFirstAdapter;
import P4.Sable.node.*;
import P4.contextualAnalysis.TypeException;

import java.util.List;


public class StackCounter extends DepthFirstAdapter {

    private int count;

    public int Count(Node node) throws TypeException, SemanticException {
        count = 0;
        node.apply(this);
        if(count < 0){
            throw new SemanticException(node,"Could not count stack size of given node.");
        }
        return count;
    }

    private int visitList(List<? extends Node> l) throws TypeException, SemanticException {
        int c = -1;
        for(var s : l){
            s.apply(this);
            c = Math.max(count,c);
        }
        return c;
    }

    @Override
    public void caseAMethodDcl(AMethodDcl node) throws TypeException, SemanticException {
        count = visitList(node.getBody());

    }

    @Override
    public void caseADclStmt(ADclStmt node) throws TypeException, SemanticException {
        count = visitList(node.getDcls());
    }

    @Override
    public void caseASingleDcl(ASingleDcl node) throws TypeException, SemanticException {
        var exp = node.getExpr();
        if(exp == null){
            count = 2;
        }
        else{
            exp.apply(this);
        }
        count++;
    }

    @Override
    public void caseAWhileStmt(AWhileStmt node) throws TypeException, SemanticException {
        int c = -1;
        node.getPredicate().apply(this);
        c = Math.max(count, c);
        c = Math.max(visitList(node.getThen()),c);
        count = c;
    }

    @Override
    public void caseAReturnStmt(AReturnStmt node) throws TypeException, SemanticException {
        node.getExpr().apply(this);
    }

    @Override
    public void caseASwitchStmt(ASwitchStmt node) throws TypeException, SemanticException {
        int c = -1;
        node.getVariable().apply(this);
        c = Math.max(count,c);
        c = Math.max(visitList(node.getCases()) ,c);
        count = c;
    }

    @Override
    public void caseACaseCase(ACaseCase node) throws TypeException, SemanticException {
        int c = -1;
        node.getCase().apply(this);
        c = Math.max(count + 3,c); //3 is minimum switch compare size
        c = Math.max(visitList(node.getThen()),c);
        count = c;
    }

    @Override
    public void caseADefaultCase(ADefaultCase node) throws TypeException, SemanticException {
        count = visitList(node.getThen());
    }

    @Override
    public void caseAIfStmt(AIfStmt node) throws TypeException, SemanticException {
        int c = 2; // 2 stack needed for comparison
        node.getPredicate().apply(this);
        c = Math.max(count,c);
        c = Math.max(visitList(node.getThen()),c);
        c = Math.max(visitList(node.getElseifs()),c);
        c = Math.max(visitList(node.getElse()),c);
        count = c;
    }

    @Override
    public void caseAElseIf(AElseIf node) throws TypeException, SemanticException {
        int c = 2; // 2 stack needed for comparison
        node.getPredicate().apply(this);
        c = Math.max(count,c);
        c = Math.max(visitList(node.getThen()),c);
        count = c;
    }

    @Override
    public void caseAForStmt(AForStmt node) throws TypeException, SemanticException {
        int c = 2; // 2 stack needed for comparison
        node.getInit().apply(this);
        c = Math.max(count,c);
        node.getPredicate().apply(this);
        c = Math.max(count,c);
        node.getUpdate().apply(this);
        c = Math.max(count,c);
        c = Math.max(visitList(node.getThen()),c);
        count = c;
    }

    @Override
    public void caseAForeachStmt(AForeachStmt node) throws TypeException, SemanticException {
        int c = 5; // 5 stack needed for list iteration
        node.getList().apply(this);
        c += count;
        c = Math.max(visitList(node.getThen())+1,c);
        count = c;
    }

    @Override
    public void caseACallStmt(ACallStmt node) throws TypeException, SemanticException {
        int c = -1;
        node.getVal().apply(this);
        count = Math.max(count,c);
    }

    @Override
    public void caseAAssignStmt(AAssignStmt node) throws TypeException, SemanticException {
        // putfield takes at least 2 stack space
        int c = 2;
        node.getVar().apply(this);
        c = Math.max(count,c);
        node.getExpr().apply(this);
        c = Math.max(count+1,c);
        if(!node.getOperation().getText().trim().equals("=")){
            // compound assignment needs at least 3 stack space
            c = Math.max(c,3);
        }
        count = c;
    }

    @Override
    public void caseAVal(AVal node) throws TypeException, SemanticException {
        count = visitList(node.getCallField());
    }

    @Override
    public void caseAFieldCallField(AFieldCallField node) throws TypeException {
        // getfield needs 1 stack space
        count = 1;
    }

    @Override
    public void caseACallCallField(ACallCallField node) throws TypeException, SemanticException {
        // invoke virtual takes at least 1 stack space
        int c = 1;
        int exp = -1;
        for(PExpr e : node.getParams()){
            // For each parameter it needs 1 more
            c++;
            e.apply(this);
            // A parameter might take a few stack spaces to calculate
            exp = Math.max(exp,count+c);
        }
        count = Math.max(c,exp);
    }

    @Override
    public void caseALiteralExpr(ALiteralExpr node) throws TypeException, SemanticException {
        count = 1;
    }

    @Override
    public void caseAValueExpr(AValueExpr node) throws TypeException, SemanticException {
        node.getVal().apply(this);
    }

    @Override
    public void caseAListExpr(AListExpr node) throws TypeException, SemanticException {
        //Initiate new list, takes at least 5 stack space
        count = Math.max(visitList(node.getElements())+2,5);
    }

    @Override
    public void caseAEqualityExpr(AEqualityExpr node) throws TypeException, SemanticException {
        node.getL().apply(this);
        int l = count;
        node.getR().apply(this);
        int r = count;
        count = Math.max(l,r) + 1;
    }

    @Override
    public void caseARelationExpr(ARelationExpr node) throws TypeException, SemanticException {
        node.getL().apply(this);
        int l = count;
        node.getR().apply(this);
        int r = count;
        count = Math.max(l,r) + 1;
    }

    @Override
    public void caseAMultOpExpr(AMultOpExpr node) throws TypeException, SemanticException {
        node.getL().apply(this);
        int l = count;
        node.getR().apply(this);
        int r = count;
        count = Math.max(l,r) + 1;
    }

    @Override
    public void caseABoolOpExpr(ABoolOpExpr node) throws TypeException, SemanticException {
        node.getL().apply(this);
        int l = count;
        node.getR().apply(this);
        int r = count;
        count = Math.max(l,r) + 1;
    }

    @Override
    public void caseAAddOpExpr(AAddOpExpr node) throws TypeException, SemanticException {
        node.getL().apply(this);
        int l = count;
        node.getR().apply(this);
        int r = count;
        count = Math.max(l,r) + 1;
    }
}
