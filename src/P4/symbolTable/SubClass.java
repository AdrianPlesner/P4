package P4.symbolTable;

import P4.Sable.node.Node;

import java.util.*;

public class SubClass extends Symbol {
    private SubClass superClass;
    private LinkedList<Function> methods = new LinkedList<>();
    private LinkedList<Variable> locals = new LinkedList<>();
    private LinkedList<SubClass> subclasses = new LinkedList<>();

    public SubClass(String id, Node dcl) {
        super(id, dcl);
    }

    public LinkedList<Variable> getLocals(){
        return this.locals;
    }
}