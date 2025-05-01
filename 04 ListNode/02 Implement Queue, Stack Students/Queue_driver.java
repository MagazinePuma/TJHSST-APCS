import java.io.*;
import java.util.*;

public class Queue_driver
{
   public static void main(String[] args)
   {
      Queue q = new Queue();
      System.out.println(q.isEmpty());
      System.out.println(q.peek());
      System.out.println(q.remove());
      System.out.println(q.toString());
      System.out.println();
      
      q.add("Kendrick Lamar");
      System.out.println(q.toString());
      System.out.println(q.isEmpty());
      q.add("Mark Ronson & Bruno Mars");
      System.out.println(q.toString());
      q.add("Lady Gaga");
      System.out.println(q.toString());
   
      System.out.println(q.peek());
      System.out.println(q.toString());
       
      System.out.println(q.remove());
      System.out.println(q.toString());
      System.out.println(q.remove());
      System.out.println(q.toString());
      System.out.println(q.remove());
      System.out.println(q.toString());
      System.out.println(q.isEmpty());
   }
}

/********************************************
 true
 null
 null
 []
 
 [Kendrick Lamar]
 false
 [Kendrick Lamar, Mark Ronson & Bruno Mars]
 [Kendrick Lamar, Mark Ronson & Bruno Mars, Lady Gaga]
 Kendrick Lamar
 [Kendrick Lamar, Mark Ronson & Bruno Mars, Lady Gaga]
 Kendrick Lamar
 [Mark Ronson & Bruno Mars, Lady Gaga]
 Mark Ronson & Bruno Mars
 [Lady Gaga]
 Lady Gaga
 []
 true
 
 ************************************/