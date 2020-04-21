package P4.symbolTable;

import P4.Sable.node.Token;

public class InvalidTypeException extends TypeException {
    public InvalidTypeException(Token token, String message) {
        super(token, message);
        message = token.getText() + "Is not a valid type." + message;
    }
}
