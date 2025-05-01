  // Graphs 2: Floyd
  //    uses AdjMat

import java.util.*;
import java.io.*;
public class FloydDriver
{
   public static void main( String[] args)
   {
      Scanner kb = new Scanner(System.in);
      System.out.println("Floyd's Algorithm!");
      System.out.print("Enter file with the weighted matrix: "); //citymatrixweighted        
      String fileMatrix = kb.next()+".txt";
      System.out.print("Enter file of cities: ");         //cities
      String fileNames = kb.next()+".txt";
   
      AdjMat gg = new AdjMat(fileMatrix, fileNames);   //students write AdjMat
   
      System.out.println();
      System.out.println("Adjacency Matrix");
      System.out.println( gg.toString() );
      
      gg.allPairsWeighted();    //runs Floyd's algorithm
      
      System.out.println( gg.toStringNamesAndNumbers());
      System.out.println("Cost Matrix");
      System.out.print( gg.toStringReachability() );
      
      while(true)
      {
         System.out.print("\nHow much does it cost?  Enter name of start city (-1 to exit): ");
         String from = kb.next().trim();
         if(from.equals("-1"))
            break;
         System.out.print("                Enter name of end city: "); 
         String to = kb.next().trim();  
         System.out.println( gg.getCost(from, to) );
      }
   }
}

/********************************      
 Floyd's Algorithm!
 Enter file with the weighted matrix: citymatrixweighted
 Enter file of cities: cities
 
 Adjacency Matrix
   0  9999  9999  9999  9999  9999  9999  8
   9999  0  9999  5  9999  9999  9999  9999
   9999  9999  0  9999  9999  5  9999  3
   9999  9999  9999  0  9999  10  9999  3
   2  9999  9999  9999  0  9999  9999  9999
   9999  4  9999  10  9999  0  9999  9999
   9999  9999  9999  9999  9999  2  0  9999
   8  9999  9999  9999  3  9999  9999  0
 
 0-Pendleton
 1-Pensacola
 2-Peoria
 3-Phoenix
 4-Pierre
 5-Pittsburgh
 6-Princeton
 7-Pueblo
 
 Cost Matrix
   0  9999  9999  9999  11  9999  9999  8
   13  0  9999  5  11  15  9999  8
   8  9  0  14  6  5  9999  3
   8  14  9999  0  6  10  9999  3
   2  9999  9999  9999  0  9999  9999  10
   17  4  9999  9  15  0  9999  12
   19  6  9999  11  17  2  0  14
   5  9999  9999  9999  3  9999  9999  0
  
 How much does it cost?  Enter name of start city (-1 to exit): Pittsburgh
                 Enter name of end city: Phoenix
 9
 
 How much does it cost?  Enter name of start city (-1 to exit): Pendleton
                 Enter name of end city: Phoenix
 9999
 
 How much does it cost?  Enter name of start city (-1 to exit): -1
 

*************************************************************/