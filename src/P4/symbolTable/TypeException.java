package P4.symbolTable;

import P4.Sable.node.*;

public class TypeException extends Exception {
    private Token token;

    public TypeException(Token token, String  message)
    {
        super(message);
        if(token != null){
            message = "Problem at line " + token.getLine() + ":" + token.getPos() + "\n" + message;
        }

        this.token = token;
    }

    public Token getToken()
    {
        return this.token;
    }
}
