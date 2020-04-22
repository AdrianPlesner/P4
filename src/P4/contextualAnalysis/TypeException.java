package P4.contextualAnalysis;

import P4.Sable.node.*;

public class TypeException extends Exception {
    private Token token;

    public TypeException(Token token, String  message)
    {
        super(message);

        this.token = token;
    }

    public Token getToken()
    {
        return this.token;
    }
}
