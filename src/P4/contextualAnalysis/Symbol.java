package P4.contextualAnalysis;

import P4.Sable.node.Node;

public abstract class Symbol{
    private String identifier;
    private Node declarationNode;
    private int scope;

    public Symbol(String id, Node dcl){
        this.identifier = id;
        this.declarationNode = dcl;
    }

    public int getScope(){
        return this.scope;
    }
    public void setScope(int s){
        this.scope = s;
    }

    public String getIdentifier(){
        return this.identifier;
    }


}
