import java.util.EmptyStackException;
import java.util.LinkedList;

// Name: Dennis Tislin
// Date: 12/2

public class Stack {
    private ListNode top;
    private LinkedList<ListNode> list= new LinkedList<>();

    /*public Stack(){
        this.list = new LinkedList<>();
        this.top = null;
    }*/

    public void push(Object item){
        top = new ListNode(item, top);
        list.add(top);
    }

    public Object pop(){
        if(top == null){
            throw new EmptyStackException();
        }
        ListNode node = list.removeLast();
        return node.getValue();
    }

    public Object peek(){
        if(top == null) {
            throw new EmptyStackException();

        }
        return top.getValue();
    }

    public boolean isEmpty(){
        return list.isEmpty();
    }

    public String toString(){
        String result = "[";
        LinkedList<ListNode> copyList = list;
        ListNode temp = copyList.getLast();
        /*while(list != null && list.getLast().getValue() != null){
            result += list.getLast().getValue() ;
            list.remove(list.getLast().getValue());
        }*/
        // while(top != null && temp.pop() != null){
        //     result += temp.pop();
        //     temp.pop();
        // }
        // result += "]";
        // return result;

        while(temp != null){
            result += temp.getValue();
            if(temp != copyList.getFirst()){
                result += ", ";
            }
            copyList.remove(copyList.getLast());
        }
        result += "]";
        return result;
    }

    /*(private String recursiveToString(ListNode node){
        if(list == null)
            return "";
        else if()
    }*/

}
