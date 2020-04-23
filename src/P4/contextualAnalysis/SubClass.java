package P4.contextualAnalysis;

import P4.Sable.node.Node;

import java.util.*;

public class SubClass extends Symbol {
    private SubClass superClass;
    private LinkedList<Function> methods = new LinkedList<>();
    private LinkedList<Variable> locals = new LinkedList<>();
    private LinkedList<SubClass> subclasses = new LinkedList<>();

    public SubClass(String id, Node dcl, SubClass sup) {
        super(id, dcl);
        this.superClass = sup;
    }

    public LinkedList<Variable> getLocals(){
        return this.locals;
    }

    public LinkedList<Function> getMethods() {
        return this.methods;
    }

    public void addLocal(Variable v){
        locals.add(v);
    }
    public void addMethod(Function f){
        methods.add(f);
    }
    public void addSubclass(SubClass s){
        subclasses.add(s);
    }

    public SubClass getSuperClass(){
        return this.superClass;
    }

    public boolean containsMethod(String s){
        for(var m : methods){
            if(m.getIdentifier().equals(s)){
                return true;
            }
        }
        return false;
    }

    public boolean containsVariable(String s){
        for(var l : locals)
    }

    @Override
    public String toString() {
        return "SubClass{" +
                super.toString() +
                "superClass=" + superClass +
                '}';
    }
}