import java.io.*;
import java.util.*;

public class Stack_driver
{
   public static void main(String[] args)
   {
      Stack stk = new Stack();
      System.out.println(stk.toString());
      System.out.println(stk.isEmpty());  
      stk.push("Alright");
      System.out.println(stk.toString());
      System.out.println(stk.isEmpty());
      stk.push("Uptown Funk");
      System.out.println(stk.toString());
      stk.push("Shallow");
      System.out.println(stk.toString());
   
      System.out.println(stk.peek());
      System.out.println(stk.toString());
      
      System.out.println(stk.pop());
      System.out.println(stk.toString());
      System.out.println(stk.pop());
      System.out.println(stk.toString());
      System.out.println(stk.pop());
      System.out.println(stk.toString());
      System.out.println(stk.isEmpty());
      
     // using pop incorrectly by not using isEmpty
      Stack stk2 = new Stack();   //empty stack
      try{
            // System.out.println(stk2.peek());    //throws Exception
         System.out.println(stk2.pop());  //throws Exception
      }
      catch(EmptyStackException e)
      {
         System.out.println("The stack is empty so you can't pop!");
      }
      //correct way
      if(!stk2.isEmpty())
        System.out.println(stk2.pop()); 
      else
        System.out.println("IsEmpty checked: The stack is empty so you can't pop!");

      
      System.out.println("Program ends gracefully!");
   }
}

/********************************************
 []
 true
 [Alright]
 false
 [Alright, Uptown Funk]
 [Alright, Uptown Funk, Shallow]
 Shallow
 [Alright, Uptown Funk, Shallow]
 Shallow
 [Alright, Uptown Funk]
 Uptown Funk
 [Alright]
 Alright
 []
 true
 The stack is empty so you can't pop!
 IsEmpty checked: The stack is empty so you can't pop!
Program ends gracefully!

 
 ************************************/