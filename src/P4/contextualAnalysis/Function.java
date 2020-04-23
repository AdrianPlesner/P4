package P4.contextualAnalysis;

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

    public String getReturnType(){
        return this.returnType;
    }

    public boolean containsArg(String name){
        for(var v : args){
            if(v.getIdentifier().equals(name)){
                return true;
            }
        }
        return false;
    }

    public LinkedList<Variable> getArgs() {
        return args;
    }

    @Override
    public String toString() {
        return "Function{" +
                super.toString() +
                ", args=" + args +
                ", returnType='" + returnType + '\'' +
                '}';
    }
}
