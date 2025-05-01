import java.util.LinkedList;

// Name: Dennis Tislin
// Date: 12/2

public class Queue{

    private ListNode first;
    private ListNode last;
    private LinkedList<ListNode> list;
  

    public Queue(){
        this.list = new LinkedList<>();
        this.first = null;
        this.last = null;
    }

    public void add(Object obj){
        ListNode node = new ListNode(obj, last);
        if(last == null) {
            first = node;
        }
        list.addLast(node);
        last = node;
    }

    public Object remove(){
        if(list.isEmpty()) {
            return "";
        }
        ListNode node = list.removeFirst();
        return node.getValue();
    }

    public boolean isEmpty(){
        return list.isEmpty();
    }

    public Object peek() {
        if(list.isEmpty()) {
            return null;
        }
        return first.getValue();
    }

    public String toString(){
        String result = "[";
        for(int i = 0; i < list.size(); i++){
            ListNode node = list.get(i);
            result += node.getValue() + ((i<list.size()-1)?", ":"");
        }
        result += "]";
        return result;
    }

}
