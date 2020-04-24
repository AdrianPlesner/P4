package P4.contextualAnalysis.Symbol;

import P4.Sable.node.Node;

public class GenerecVariable extends Variable {
    private String classVariable;

    public GenerecVariable(String id, Node dcl, String type, String var) {
        super(id, dcl, type);
        classVariable = var;
    }

    public String getClassVariable() {
        return classVariable;
    }

    @Override
    public String toString() {
        return "GenerecVariable{" +
                "classVariable='" + classVariable + '\'' +
                super.toString() +
                '}';
    }
}
