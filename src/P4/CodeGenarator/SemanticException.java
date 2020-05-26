package P4.CodeGenarator;

import P4.Sable.node.Node;
import P4.Sable.node.Token;
import P4.contextualAnalysis.TokenFinder;
import P4.contextualAnalysis.TypeException;

public class SemanticException extends Exception {
    public Node n;

    public Token getToken() throws SemanticException, TypeException {
        var tf = new TokenFinder();
        n.apply(tf);
        return tf.getToken();
    }

    public SemanticException(Node n, String m){
        super(m);
        this.n = n;

    }
}
