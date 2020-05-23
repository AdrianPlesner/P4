package Game;

public class List {
    public int length = 0;

    public Node first = null;
    public Node last = null;

    public List(){

    }

    public void add(Object o){
        var v = new Node(o);
        if(length==0){
            first = v;
        }
        else{
            last.next = v;
            v.prev = last;
        }
        last = v;
        length++;
    }
    public void remove(Object o){
        var c = first;
        while(c != null){
            if(c.value.equals(o)){
                if(length == 1){
                    first = null;
                    last = null;
                }
                else if(c.equals(first)){
                    first = c.next;
                    first.prev = null;
                }
                else if(c.equals(last)){
                    last = c.prev;
                    last.next = null;
                }
                else {
                    c.prev.next = c.next;
                    c.next.prev = c.prev;
                }
                length--;
                break;
            }
            c = c.next;
        }
    }
    public List take(int i){
        List result = new List();
        while(i > 0){
            result.add(first.value);
            first = first.next;
            first.prev = null;
            i--;
        }
        length -= i;
        return result;
    }

    public Object index(int i){
        if(i > length-1 || i < 0){
            return null;
        }
        var c = first;
        for(int n = 0; n < i; n++){
            c = c.next;
        }
        return c.value;
    }

    public void clear(){
        first = null;
        last = null;
        length = 0;
    }
}
