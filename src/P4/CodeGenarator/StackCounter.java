package P4.CodeGenarator;

import P4.Sable.analysis.DepthFirstAdapter;
import P4.Sable.node.*;
import P4.contextualAnalysis.TypeException;


public class StackCounter extends DepthFirstAdapter {

    private int count;

    public int Count(Node node) throws TypeException {
        count = 0;
        if(!(node instanceof AMethodDcl)) {
            System.out.println("WARNING! Applying StackCounter to a node that is not a method declaration");
        }
        node.apply(this);
        if(count < 0){
            //TODO: something went wrong
        }
        return count;
    }

    @Override
    public void caseAMethodDcl(AMethodDcl node) throws TypeException {
        int c = -1;
        for(PStmt s : node.getBody()){
            s.apply(this);
            c = Math.max(count,c);
        }
        count = c;

    }

    @Override
    public void caseADclStmt(ADclStmt node) throws TypeException {
        int c = -1;
        for(PSingleDcl s : node.getDcls()){
            s.apply(this);
            c = Math.max(count,c);
        }
        count = c;
    }

    @Override
    public void caseASingleDcl(ASingleDcl node) throws TypeException {
        var exp = node.getExpr();
        if(exp == null){
            count = 0;
        }
        else{
            exp.apply(this);
        }
    }

    @Override
    public void caseAWhileStmt(AWhileStmt node) throws TypeException {
        int c = 2; //Stack needed for comparison
        node.getPredicate().apply(this);
        c = Math.max(count, c);
        for(PStmt s: node.getThen()){
            s.apply(this);
            c = Math.max(c,count);
        }
        count = c;
    }

    @Override
    public void caseAReturnStmt(AReturnStmt node) throws TypeException {
        node.getExpr().apply(this);
    }

    @Override
    public void caseASwitchStmt(ASwitchStmt node) throws TypeException {
        super.caseASwitchStmt(node);
    }
}
