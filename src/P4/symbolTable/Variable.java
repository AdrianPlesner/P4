package P4.symbolTable;

import P4.Sable.node.Node;

public class Variable extends Symbol {

    private String type;

    public Variable(String id, Node dcl, String type) {
        super(id, dcl);
        this.type = type;
    }
}
