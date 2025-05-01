//graph manipulation, lesson #0
//    uses AdjMat

import java.util.*;
import java.io.*;

public class AdjMat_0_Driver
{
   public static void main(String[] args) 
   {
      Scanner kb = new Scanner(System.in);
      System.out.println("Adjacency Matrix!"); 
      System.out.print("Enter file with the matrix: "); //matrix4x4              
      String fileMatrix = kb.next()+".txt";
      
      AdjMat g = new AdjMat(fileMatrix);   //students write AdjMat
           
      System.out.println();
      System.out.println(g.toString());
      System.out.println("Number of edges: " + g.countEdges());
      System.out.println();
      System.out.println("The neighbors of each vertex: ");
      System.out.println( g.showAllNeighbors() );
   
      while(true)
      {
         System.out.println("Enter by number <source> <target>. Is target a neighbor of source?");
         int source = kb.nextInt();
         if( source == -1 ) 
            break;
         int target = kb.nextInt();
         System.out.println( g.isNeighbor(source, target) );
      }
      
      while(true)
      {
         System.out.println("Enter <source>. Its neighbors are: ");
         int source = kb.nextInt();
         if( source == -1 ) 
            break;
         System.out.println( g.getNeighbors(source) );
      }
      
      /***********************  now deal with names  *****************
        comment out the code above. The driver now begins at line 47. 
      *****************************************************/
      Scanner key = new Scanner(System.in);
      System.out.println("Adjacency Matrix with cities!");
      System.out.print("Enter file with the matrix: ");  //matrix4x4
      String matrixFile = key.next()+".txt";
      System.out.print("Enter file with the names: ");   // names4
      String fileNames = key.next()+".txt";
      
      AdjMat gg = new AdjMat(matrixFile, fileNames);   //students write AdjMat
      
      System.out.println();        
      System.out.println(gg.toString());
      System.out.println("Number of edges: " + gg.countEdges() );
      System.out.println();
      System.out.println("Map: " + gg.getNamesAndNumbers() );
      System.out.println("Names and numbers: ");
      System.out.println( gg.toStringNamesAndNumbers() );
      
      while(true)
      {
         System.out.println("Enter by name <source> <target>. Is target a neighbor of source?");
         String source = key.next();
         if( source.equals("-1") ) 
            break;
         String target = key.next();
         System.out.println( gg.isNeighbor(source, target) );
      }
         
   }    
}
/******************   sample run  ***************

Adjacency Matrix! Enter file with the matrix: matrix4x4
 
   0  0  1  0
   1  0  0  0
   0  0  1  1
   1  0  1  0
 
 Number of Warshall edges: 6
 
 The neighbors of each vertex: 
 0: [2]
 1: [0]
 2: [2, 3]
 3: [0, 2]
 
 Enter by number <source> <target>. Is target a neighbor of source?
 3 2
 true
 Enter by number <source> <target>. Is target a neighbor of source?
 -1
 Enter <source>. Its neighbors are: 
 3
 [0, 2]
 Enter <source>. Its neighbors are: 
 -1
 Adjacency Matrix! 
 Enter file with the matrix: matrix4x4
 Enter file with the names: names4
 
   0  0  1  0
   1  0  0  0
   0  0  1  1
   1  0  1  0
 
 Number of Warshall edges: 6
 Map: {Albert=0, Claire=1, Larry=2, Marylin=3}
 Names and numbers: 
 0-Albert
 1-Claire
 2-Larry
 3-Marylin
 
 Enter by name <source> <target>. Is target a neighbor of source?
 Albert Claire
 false
 Enter by name <source> <target>. Is target a neighbor of source?
 Claire Albert
 true
 Enter by name <source> <target>. Is target a neighbor of source?
 -1    
 
 
 ************************************/