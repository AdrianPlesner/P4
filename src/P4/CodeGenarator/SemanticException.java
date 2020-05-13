package P4.CodeGenarator;

import P4.Sable.node.Node;

public class SemanticException extends Exception {
    public Node n;

    public SemanticException(Node n, String m){
        super(m);
        this.n = n;

    }
}
