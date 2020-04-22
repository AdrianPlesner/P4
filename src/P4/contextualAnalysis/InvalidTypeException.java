package P4.contextualAnalysis;

import P4.Sable.node.Token;

public class InvalidTypeException extends TypeException {
    public InvalidTypeException(Token token, String message) {
        super(token, message);
    }
}
