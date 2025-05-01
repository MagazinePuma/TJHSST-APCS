// Name: Dennis Tislin
// Date: 12/8

//  DoubleLinkedList, circular, with a dummy head node
//  implements some of the List and LinkedList interfaces: 
//	 	  size(), add(i, o), remove(i);  addFirst(o), addLast(o); 
//  This class also overrides toString().
//  the list is zero-indexed.
//  Uses DLNode.

class DLL  
{
   private int size;
   private DLNode head; //points to a dummy node--very useful--don't mess with it
   public DLL()  
   {
      this.head = new DLNode();
      this.head.setPrev(this.head);
      this.head.setNext(this.head);
      // make it circular
      // DLNode circle = head;
      // while(circle.getNext() != null){
      //    circle = circle.getNext();
      // }
      // circle.setNext(head);
      // head.setPrev(circle);
   } 
   
   /* two accessor methods  */
   public int size()
   {
      return size;
   }
   public DLNode getHead()
   {
      return head;
   }
   
   /* appends obj to end of list; increases size;
   	  @return true  */
   public boolean add(Object obj)
   {
      addLast(obj);
      return true;   
   }
   
   /* inserts obj at position index (the list is zero-indexed).  
      increments size. 
      no need for a special case when size == 0.
	   */
   public void add(int index, Object obj) throws IndexOutOfBoundsException  //this the way the real LinkedList is coded
   {
      if( index > size || index < 0 )
         throw new IndexOutOfBoundsException("Index: " + index + ", Size: " + size);

      /* enter your code below  */
      DLNode insertObj = head;
      for(int i = 0; i < index; i++){
         insertObj = insertObj.getNext();
      }
      DLNode insert = new DLNode(obj, insertObj, insertObj.getNext());
      insertObj.setNext(insert);
      // insertObj.getNext().setPrev(insert);
      insert.getNext().setPrev(insert);
      this.size++;             
   }
   
    /* return obj at position index (zero-indexed). 
    */
   public Object get(int index) throws IndexOutOfBoundsException
   { 
      if(index >= size || index < 0)
         throw new IndexOutOfBoundsException("Index: " + index + ", Size: " + size);
      /* enter your code below  */
      DLNode getIndex = head;
      for(int i = 0; i <= index; i++){
         getIndex = getIndex.getNext();
      }
      return getIndex.getValue();
   }
   
   /* replaces obj at position index (zero-indexed). 
        returns the obj that was replaced.
        */
   public Object set(int index, Object obj) throws IndexOutOfBoundsException
   {
      if(index >= size || index < 0)
         throw new IndexOutOfBoundsException("Index: " + index + ", Size: " + size);
         
      /* enter your code below */

      DLNode replacedObj = head;
      for(int i = 0; i <= index; i++){
         replacedObj = replacedObj.getNext();
      }
      Object oldObj = replacedObj.getValue();
      replacedObj.setValue(obj);
      return oldObj;
   }
   
   /*  removes the node from position index (zero-indexed).  decrements size.
       @return the object in the node that was removed. 
        */
   public Object remove(int index) throws IndexOutOfBoundsException
   {
      if(index >= size || index < 0)
         throw new IndexOutOfBoundsException("Index: " + index + ", Size: " + size);

      /* enter your code below  */

      DLNode removeObj = head;
      for(int i = 0; i <= index; i++){
         removeObj = removeObj.getNext();
      }
      DLNode previous = removeObj.getPrev();
      previous.setNext(removeObj.getNext());
      removeObj.getNext().setPrev(previous);
      this.size--;
      return removeObj.getValue();
   }
   
  	/* inserts obj to front of list, increases size.
	    */ 
   public void addFirst(Object obj)
   {
      DLNode first = new DLNode(obj, head, head.getNext());
      first.getNext().setPrev(first);
      head.setNext(first);
      this.size++;
   }
   
   /* appends obj to end of list, increases size.
       */
   public void addLast(Object obj)
   {
      DLNode last = new DLNode(obj, head.getPrev(), head);
      head.getPrev().setNext(last);
      head.setPrev(last);
      this.size++;
   }
   
   /* returns the first element in this list  
      */
   public Object getFirst()
   {
   
      return head.getNext().getValue();
   }
   
   /* returns the last element in this list  
     */
   public Object getLast()
   {
   
      return head.getPrev().getValue();
   }
   
   /* returns and removes the first element in this list, or
      returns null if the list is empty  
      */
   public Object removeFirst()
   {
      if(head.getNext() == null){
         return null;
      }
      Object removedNode = head.getNext().getValue();
      DLNode secondNode = head.getNext().getNext();
      secondNode.setPrev(head);
      // DLNode removedNode = head.getNext();
      // removedNode.setNext(null);
      // removedNode.setPrev(null);
      head.setNext(secondNode);
      this.size--;
      return removedNode;
   }
   
   /* returns and removes the last element in this list, or
      returns null if the list is empty  
      */
   public Object removeLast()
   {
      if(head.getNext() == null){
         return null;
      }
      Object lastRemoved = head.getPrev().getValue();
      DLNode lastNode = head.getPrev().getPrev();
      lastNode.setNext(head);
      head.setPrev(lastNode);
      this.size--;
      return lastRemoved;
   }
   
   /*  returns a String with the values in the list in a 
       friendly format, for example   [Apple, Banana, Cucumber]
       The values are enclosed in [], separated by one comma and one space.
       An empty list returns [].
    */
   public String toString()
   {
      if(size==0) 
         return "[]";
            
      String printList = "[";
      DLNode first = head.getNext();
      while(first != head){
         printList += first.getValue();
         if(first != head.getPrev()){
            printList += ", ";
         }
            first = first.getNext();
         }
         // first.getNext() != head?printList += ", ":printList += first.getValue();
      printList += "]";
      return printList;
   }
}