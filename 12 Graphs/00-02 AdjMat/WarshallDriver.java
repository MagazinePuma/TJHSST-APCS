//mlbillington@fcps.edu, May 2012, June 2014, May 2018, May 2022
//ejurj@fcps.edu, May 2022
//uses AdjMat
import java.util.*;
import java.io.*;
public class WarshallDriver
{
   public static void main(String[] args) throws FileNotFoundException
   {
      Scanner kb = new Scanner(System.in);
      System.out.println("Warshall's Algorithm!");
      System.out.print("Enter file with the matrix: ");  //citymatrix   //matrix4x4      
      String fileMatrix = kb.next()+".txt";
      System.out.print("Enter file of cities: ");         //cities        //names4
      String fileNames = kb.next()+".txt";
   
      AdjMat gg = new AdjMat(fileMatrix, fileNames);   //students write AdjMat
   
      System.out.println();
      System.out.println("Adjacency Matrix");
      System.out.println( gg.toString() );
      System.out.println("Number of Warshall edges: " + gg.countEdges() );
      System.out.println();
      System.out.println("Map: " + gg.getNamesAndNumbers());
      System.out.println("Names and numbers: ");
      System.out.println( gg.toStringNamesAndNumbers());
      
      gg.allPairsReachability();    //runs Warshall's algorithm
      
      System.out.println("Reachability Matrix");
      System.out.println( gg.toStringReachability() );
      System.out.println("Number of reachables: " + gg.countReachables() );
      
      while(true)
      {
         System.out.print("\nIs it reachable?  Enter name of start city (-1 to exit): ");
         String from = kb.next().trim();
         if(from.equals("-1"))
            break;
         System.out.print("                Enter name of end city: "); 
         String to = kb.next().trim();  
         System.out.println( gg.isReachable(from, to) );
      }
      
      System.out.println("List of every city's reachable cities: ");
      for(String city : gg.getNamesAndNumbers().keySet() )
         System.out.println(city + "--> " + gg.getReachables(city) );
            
      while(true)
      {
         System.out.print("\nList the reachable cities from: ");
         String from = kb.next();
         if(from.equals("-1"))
            break;
         System.out.println(gg.getReachables(from));
      }
   
   }
}
/******************************************
  Warshall's Algorithm!
 Enter file with the matrix: citymatrix
 Enter file of cities: cities
 
 Adjacency Matrix
   0  0  0  0  0  0  0  1
   0  0  0  1  0  0  0  0
   0  0  0  0  0  1  0  1
   0  0  0  0  0  1  0  1
   1  0  0  0  0  0  0  0
   0  1  0  1  0  0  0  0
   0  0  0  0  0  1  1  0
   1  0  0  0  1  0  0  0
 
 Number of Warshall edges: 13
 
 Map: {Pendleton=0, Pensacola=1, Peoria=2, Phoenix=3, Pierre=4, Pittsburgh=5, Princeton=6, Pueblo=7}
 Names and numbers: 
 0-Pendleton
 1-Pensacola
 2-Peoria
 3-Phoenix
 4-Pierre
 5-Pittsburgh
 6-Princeton
 7-Pueblo
 
 Reachability Matrix
   1  0  0  0  1  0  0  1
   1  1  0  1  1  1  0  1
   1  1  0  1  1  1  0  1
   1  1  0  1  1  1  0  1
   1  0  0  0  1  0  0  1
   1  1  0  1  1  1  0  1
   1  1  0  1  1  1  1  1
   1  0  0  0  1  0  0  1
 
 Number of reachables: 40    
 Is it reachable?  Enter name of start city (-1 to exit): Pittsburgh
                 Enter name of end city: Pendleton
 true
 
 Is it reachable?  Enter name of start city (-1 to exit): Pendleton
                 Enter name of end city: Pittsburgh
 false
 
 Is it reachable?  Enter name of start city (-1 to exit): -1
 List of every city's reachable cities: 
 Pendleton--> [Pendleton, Pierre, Pueblo]
 Pensacola--> [Pendleton, Pensacola, Phoenix, Pierre, Pittsburgh, Pueblo]
 Peoria--> [Pendleton, Pensacola, Phoenix, Pierre, Pittsburgh, Pueblo]
 Phoenix--> [Pendleton, Pensacola, Phoenix, Pierre, Pittsburgh, Pueblo]
 Pierre--> [Pendleton, Pierre, Pueblo]
 Pittsburgh--> [Pendleton, Pensacola, Phoenix, Pierre, Pittsburgh, Pueblo]
 Princeton--> [Pendleton, Pensacola, Phoenix, Pierre, Pittsburgh, Princeton, Pueblo]
 Pueblo--> [Pendleton, Pierre, Pueblo]
 
 List the reachable cities from: Pittsburgh
 [Pendleton, Pensacola, Phoenix, Pierre, Pittsburgh, Pueblo]
 
 List the reachable cities from: -1 
 
 **************************************************/
 

