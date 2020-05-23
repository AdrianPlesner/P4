package Game;

public class Node {
    public Node next = null;
    public Node prev = null;
    public Object value = null;

    public Node(Object v){
        value = v;
    }
}
