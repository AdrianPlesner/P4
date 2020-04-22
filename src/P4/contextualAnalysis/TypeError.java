package P4.contextualAnalysis;

import P4.Sable.node.Token;

public class TypeError {
    public String message;
    private Token token;

    public TypeError(Token t, String m){
        this.message = m;
        this.token = t;
    }

    public Token getToken(){
        return this.token;
    }
}
