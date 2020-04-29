package P4.contextualAnalysis.Symbol;

import P4.Sable.node.Node;

public class GenericClass extends SubClass {

    private String classVariable;

    public GenericClass(String id, Node dcl, SubClass sup) {
        super(id, dcl, sup);
    }

    public void setClassVariable(String classVariable) {
        this.classVariable = classVariable;
    }

    public String getClassVariable() {
        return classVariable;
    }
}
