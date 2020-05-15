package P4.CodeGenarator;

import P4.Sable.analysis.DepthFirstAdapter;
import P4.Sable.node.AMethodDcl;
import P4.Sable.node.Node;
import P4.contextualAnalysis.TypeException;

import java.util.Stack;

public class StackCounter extends DepthFirstAdapter {

    private int count;

    public int Counter(Node node) throws TypeException {
        count = 0;
        node.apply(this);
        //DET HER GØR IKKE NOGET
        Stack<Integer> s = new Stack<>();
        s.add(count);



        return count;
    }

    @Override
    public void caseAMethodDcl(AMethodDcl node) throws TypeException {
        super.caseAMethodDcl(node);
    }
}
