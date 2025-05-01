 //Name: Dennis Tislin   
 //Date: 4/8/24
 
import java.util.ArrayList;
// import java.util.PriorityQueue.*;

/* implement the API for java.util.PriorityQueue
 *     a min-heap of objects in an ArrayList<E> in a resource class
 * test this class with HeapPriorityQueue_Driver.java.
 * test this class with LunchRoom.java.
 * add(E) and remove()  must work in O(log n) time
 */
public class HeapPriorityQueue<E extends Comparable<E>> 
{
   private ArrayList<E> myHeap;
   
   public HeapPriorityQueue()
   {
      myHeap = new ArrayList<E>();
      myHeap.add(null);
   }
   
   public ArrayList<E> getHeap()   //for Codepost
   {
      return myHeap;
   }
   
   public int lastIndex()
   {
      return myHeap.size() - 1;
   }
   
   public boolean isEmpty()
   {
      return myHeap.size() <= 1;
      // return myHeap.isEmpty();
   }
   
   public boolean add(E obj)
   {
      myHeap.add(obj);
      heapUp(lastIndex());
      return true;
   }
   
   public E remove()
   {
      // return !myHeap.isEmpty() && myHeap.size() > 1?myHeap.remove(1):!myHeap.isEmpty() && myHeap.size() == 1?myHeap.remove(0):null;
      if(myHeap.isEmpty()){
         return null;
      } else {
         // E li = myHeap.get(lastIndex());
         // E toRet = myHeap.get(1);
         // myHeap.set(1, li);
         // myHeap.remove(lastIndex());
         // heapDown(1, lastIndex() - 1);
         // return toRet;

         E toRet = myHeap.get(1);
         swap(1, lastIndex());
         myHeap.remove(lastIndex());
         heapDown(1, lastIndex());
         return toRet;
      }

      //    PriorityQueue<E> heap = new PriorityQueue<E>();
      //    for(int i = 1; i < myHeap.size(); i++){
      //       heap.add(myHeap.get(i));
      //    }
      //    E ret = heap.remove();
      //    myHeap.remove(ret);
      //    return ret;
      // } else {
      //    return null;
   }
   
   public E peek()
   {
      return isEmpty()?null:myHeap.get(1);
      // PriorityQueue<E> heap = new PriorityQueue<E>();
      // for(int i = 1; i < myHeap.size(); i++){
      //    heap.add(myHeap.get(i));
      // }
      // return heap.peek();
      // return !myHeap.isEmpty() && myHeap.size() > 1?myHeap.get(1):!myHeap.isEmpty() && myHeap.size() == 1?myHeap.get(0):null;
      // E ret = myHeap.get(1);
      // if(!myHeap.isEmpty()){
      //    for(int i = 0; i < myHeap.size(); i++){
      //       if(i != myHeap.size() - 1){
      //          if(myHeap.get(i).compareTo(myHeap.get(i + 1)) < 0){
      //             ret = myHeap.get(i + 1);
      //          } else {
      //             ret = myHeap.get(i);
      //          }
      //       }
      //    }
      // } else {
      //    return null;
      // }
      // return ret;
   }
   
   //  it's a min-heap of objects in an ArrayList<E> in a resource class
   public void heapUp(int k)
   {
      while(k > 1 && myHeap.get(k).compareTo(myHeap.get(k/2)) < 0){
         swap(k, k/2);
         k = k/2;
      }
      // for(int i = 0; i < myHeap.size(); i++){
      //    int childIndex = i;
      //    while(myHeap.get(childIndex).compareTo(myHeap.get(childIndex/2)) < 0){
      //       // if(childIndex < 0){
      //          swap(childIndex/2, childIndex);
      //          childIndex = childIndex/2;
      //       // }
      //    }
      // }
   }
   
   private void swap(int a, int b)
   {
      
      E bSwap = myHeap.get(b);
      myHeap.set(b, myHeap.get(a));
      myHeap.set(a, bSwap);
      // for(int i = 0; i < a; i++){
      //    if(i == a){
      //       toSwap = myHeap.get(i);
      //       E swap = myHeap.get(a);
      //       swap = myHeap.get(b);
      //    }
      // }C DX C
   }
   
  //  it's a min-heap of objects in an ArrayList<E> in a resource class
   public void heapDown(int k, int lastIndex)
   {
      int minChildInd = k;
      if(k * 2 + 1 <= lastIndex && myHeap.get(k * 2 + 1).compareTo(myHeap.get(minChildInd)) < 0){
         minChildInd = k * 2 + 1;
      }
      if(k * 2 <= lastIndex && myHeap.get(k * 2).compareTo(myHeap.get(minChildInd)) < 0){
         minChildInd = k * 2;
      }
      if(k != minChildInd){
         swap(k, minChildInd);
         heapDown(minChildInd, lastIndex);
      }

      
      // if(k > lastIndex || k * 2 > lastIndex){
      //    return;
      // } else if(k * 2 + 1 > lastIndex){
      //    if(myHeap.get(k).compareTo(myHeap.get(k * 2)) > 0){
      //       swap(k, k * 2);
      //    }
      // } else {
      //    E childSwap = myHeap.get(k * 2).compareTo(myHeap.get(k * 2 + 1)) < 0?myHeap.get(k * 2):myHeap.get(k * 2 + 1);
      //    if(myHeap.get(k).compareTo(childSwap) > 0){
      //       if(childSwap.compareTo(myHeap.get(k * 2)) == 0){
      //          swap(k, k * 2);
      //          heapDown(k * 2, lastIndex);
      //       } else {
      //          swap(k, k * 2 + 1);
      //          heapDown(k * 2 + 1, lastIndex);
      //       }
      //    }
      // }
      // } else if(k * 2 < lastIndex && k * 2 + 1 > lastIndex){
      //    if(myHeap.get(k).compareTo(myHeap.get(k * 2)) > 0){
      //       swap(k, k * 2);
      //       heapDown(k * 2, lastIndex);
      //    } else {
      //       return;
      //    }
      // } else {
      //    E childSwap = myHeap.get(k * 2);
      //    if(childSwap.compareTo(myHeap.get(k * 2 + 1)) < 0){
      //       if(myHeap.get(k).compareTo(myHeap.get(k * 2)) > 0){
      //          swap(k, k * 2);
      //          heapDown(k * 2, lastIndex);
      //       } else {
      //          return;
      //       }
      //    } else {
      //       if(myHeap.get(k).compareTo(myHeap.get(k * 2)) > 0){
      //          swap(k, k * 2 + 1);
      //          heapDown(k * 2 + 1, lastIndex);
      //       } else {
      //          return;
      //       }
      //    }
      // while(k * 2 < myHeap.size() || k * 2 < myHeap.size() + 1){
      //    if(myHeap.get(k).compareTo(myHeap.get(k * 2)) < 0){
      //       swap(k, k * 2);
      //    } else if(k * 2 + 1 < myHeap.size() && myHeap.get(k).compareTo(myHeap.get(k * 2 + 1)) < 0){
      //       swap(k, k * 2 + 1);
      //    }
      // }
   }
   
   public String toString()
   {
      return myHeap.toString();
   }  
}
