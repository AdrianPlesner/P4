package P4.contextualAnalysis;

import P4.Sable.node.Token;

public class IdentifierAlreadyExistsException extends TypeException {
    public IdentifierAlreadyExistsException(Token token, String message) {
        super(token, message);
    }
}
