import java.util.*;
import java.io.*;
/*  
    Test your AVL tree with this driver.
    Your rotations should match the rotations at
       https://www.cs.usfca.edu/~galles/visualization/AVLtree.html  
*/

public class BST_AVL_driver
{
   public static void main( String[] args ) throws Exception
   {	
      BST balancedTree = new BST(); 
      //Scanner in = new Scanner(System.in);
      //System.out.print("Type in a line: ");  
      //String line = in.nextLine();
   
    // String line = "5 3 2";  //left-left case (right rotation) 
      //String line = "3 5 7";  //right-right case (left rotation)
      //String line = "5 3 4";  //left-right case (left rotation then right rotation)
     //String line = "3 5 4";  //right-left case (right rotation then left rotation)
      //String line = "a b c d e f g";  //many left rotations
      //String line = "g f e d c b a";  //many right rotations
      //String line = "J E H B F G D"; //J E H right rot., B F right rot., G right rot., D
      //String line = "L P N U R O"; //L P N right-left case, U R right-left case, O right-left case
    //   String line = "M H D F E J U L X I P K"; //M H D left rot., F E left-right case, J right rot., U L X left rot., I right-left case, P left rot., K right rot.
	  String line = "H D Y P L J R K I E A Z U X N";

      String[] str = line.split(" ");
      for(String item : str)
      {
         balancedTree.addBalanced( item );  //implement addBalanced() in your BST class
         System.out.println(balancedTree.display());
         System.out.println("------------------------------");
      }
	  System.out.println("--- size: " + balancedTree.size());
   }
}

  /****** String line = "M H D F E J U L X I P K"; //M H D left rot., F E left-right case, J right rot., U L X left rot., I right-left case, P left rot., K right rot.
      
 rotation at M = 0
 M
 
 ------------------------------
 rotation at H = 0
 rotation at M = -1
 M
 	H
 
 ------------------------------
 rotation at D = 0
 rotation at H = -1
 rotation at M = -2
 	M
 H
 	D
 
 ------------------------------
 rotation at F = 0
 rotation at D = 1
 rotation at M = 0
 rotation at H = -1
 	M
 H
 		F
 	D
 
 ------------------------------
 rotation at E = 0
 rotation at F = -1
 rotation at D = 2
 rotation at M = 0
 rotation at H = -1
 	M
 H
 		F
 	E
 		D
 
 ------------------------------
 rotation at D = 0
 rotation at F = 0
 rotation at E = 0
 rotation at J = 0
 rotation at M = -1
 rotation at H = 0
 	M
 		J
 H
 		F
 	E
 		D
 
 ------------------------------
 rotation at D = 0
 rotation at F = 0
 rotation at E = 0
 rotation at J = 0
 rotation at U = 0
 rotation at M = 0
 rotation at H = 0
 		U
 	M
 		J
 H
 		F
 	E
 		D
 
 ------------------------------
 rotation at D = 0
 rotation at F = 0
 rotation at E = 0
 rotation at L = 0
 rotation at J = 1
 rotation at U = 0
 rotation at M = -1
 rotation at H = 1
 		U
 	M
 			L
 		J
 H
 		F
 	E
 		D
 
 ------------------------------
 rotation at D = 0
 rotation at F = 0
 rotation at E = 0
 rotation at L = 0
 rotation at J = 1
 rotation at X = 0
 rotation at U = 1
 rotation at M = 0
 rotation at H = 1
 			X
 		U
 	M
 			L
 		J
 H
 		F
 	E
 		D
 
 ------------------------------
 rotation at D = 0
 rotation at F = 0
 rotation at E = 0
 rotation at I = 0
 rotation at L = 0
 rotation at J = 0
 rotation at X = 0
 rotation at U = 1
 rotation at M = 0
 rotation at H = 1
 			X
 		U
 	M
 			L
 		J
 			I
 H
 		F
 	E
 		D
 
 ------------------------------
 rotation at D = 0
 rotation at F = 0
 rotation at E = 0
 rotation at I = 0
 rotation at L = 0
 rotation at J = 0
 rotation at P = 0
 rotation at X = 0
 rotation at U = 0
 rotation at M = 0
 rotation at H = 1
 			X
 		U
 			P
 	M
 			L
 		J
 			I
 H
 		F
 	E
 		D
 
 ------------------------------
 rotation at D = 0
 rotation at F = 0
 rotation at E = 0
 rotation at I = 0
 rotation at K = 0
 rotation at L = -1
 rotation at J = 1
 rotation at P = 0
 rotation at X = 0
 rotation at U = 0
 rotation at M = -1
 rotation at H = 2
 			X
 		U
 			P
 	M
 		L
 			K
 J
 		I
 	H
 			F
 		E
 			D
 
 ------------------------------
 
  ----jGRASP: operation complete.
************************************/