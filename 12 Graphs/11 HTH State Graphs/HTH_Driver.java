// Name:   
// Date:
 
import java.util.*;
import java.io.*;

/* For use with Graphs11: State Graphs,
   Heads-Tails-Heads
 */

class HTH_Driver
{
   public static void main(String[] args) throws FileNotFoundException
   {
      System.out.println("Start with three coins arranged in a row, Heads-Tails-Heads (HTH). \n"+
         " The goal is to change the arrangement into THT within the rules, namely, \n"+
         "1) You may flip the middle coin (from H to T or vice versa) whenever you want to. \n"+ 
         "2) You may flip the end coins only if the other two coins \n"+
         "    are the same as each other (both H or both T).  \n"+ 
         "3) You may not change the coins in any other way, for example by shuffling.");
    
      System.out.print("Enter the initial state, three H and/or T:  ");
      Scanner in = new Scanner(System.in);
      String initial = in.next().toUpperCase();
      Vertex v = makeGraph(initial);
      System.out.println("The state graph has been made.");
      
      while(true)
      {
         System.out.print("Enter the final state, three H and/or T:  ");
         String finalState = in.next().toUpperCase();;
         if( finalState.equals("-1") )
            break;
         v = findBreadth(v, finalState);  //BFS
         System.out.println("The shortest path from "+initial+" to "+ finalState+ " is: ");
         System.out.println(initial);
         String s = "";
         while(v.getPrevious()!= null)  //collect the previous fields
         {
            s = v+"\n"+s;
            v = v.getPrevious();
         }
         System.out.println(s);
      }
   }
   
   public static Vertex makeGraph(String s)
   {
   
   }
   
   public static Vertex findBreadth(Vertex v, String goal)
   {
      
   }
}

class Vertex
{
   
   
   
}

/************************
 Start with three coins arranged in a row, say, Heads-Tails-Heads (HTH). 
 The goal is to change the arrangement of Heads and Tails into something else within the rules, namely, 
 1) You may flip the middle coin (from H to T or vice versa) whenever you want to. 
 2) You may flip the end coins only if the other two coins 
     are the same as each other (both H or both T).  
 3) You may not change the coins in any other way, for example by shuffling.
 Enter the initial state, three H and/or T:  HTH
 The state graph has been made.
 Enter the final state, three H and/or T:  THT
 The shortest path from HTH to THT is: 
 HTH
 HHH
 HHT
 HTT
 TTT
 THT
 
 Enter the final state, three H and/or T:  HHH
 The shortest path from HTH to HHH is: 
 HTH
 HHH
 
 Enter the final state, three H and/or T:  -1
 

 *************************************/