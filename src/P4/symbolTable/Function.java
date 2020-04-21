package P4.symbolTable;

import P4.Sable.node.Node;

import java.util.*;

public class Function extends Symbol {
    private LinkedList<Variable> args = new LinkedList<>();

    private String returnType;

    public Function(String id, Node dcl, String returnType) {
        super(id, dcl);
        this.returnType = returnType;
    }

    public void addArg(Variable v){
        args.add(v);
    }
}
