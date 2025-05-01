// mlbillington@fcps.edu   4-10-2019  
  /*******************
    Driver class for a generic BST. 
**********************/
public class BST_Generic_Driver
{
   public static void main(String[] args)
   {
       /*************** basic operations  ***********/
      BST_Generic<Double> tree = new BST_Generic<>();
      tree.add(2.0);
      Double x = tree.getRoot().getValue();   //no casting
      System.out.println ("2.0 * 3 should be " + x*3);  //6.0
      tree.add(1.0);
      tree.add(3.0);
      Double y = tree.getRoot().getRight().getValue(); 
      Double z = tree.getRoot().getLeft().getValue();
      System.out.println("2.0 + 1.0 + 3.0 should be " + (x + y + z));  //6.0
      System.out.println(tree.display());
      System.out.println("As a string: " + tree.toString());
      System.out.println("Size: " + tree.size());
      Double min = tree.min();            //no casting
      System.out.println("Min: " + min);
      Double max = tree.max();            //no casting
      System.out.println("Max: " + max);
      System.out.println("Sum of min and max: " + (min+max));
     
      /*      BST with Integer objects 	*/
      BST_Generic<Integer> treeOfIntegers = new BST_Generic<Integer>();
      System.out.println("\nAdd random amount of random Integers to the BST_Generic<Integer> tree");
      int N =  (int)(Math.random() * 7)+3; 
      for(int n=0; n<N; n++)
      {
         Integer a = (int)(Math.random() * 10); 
         treeOfIntegers.add(a);
      }
      System.out.println(treeOfIntegers.display());
      System.out.println("As a string: " + treeOfIntegers.toString());
      System.out.println("Size: " + treeOfIntegers.size());
      Integer minInt = treeOfIntegers.min();   //no casting
      System.out.println("Min: " + minInt);
      Integer maxInt = treeOfIntegers.max();   //no casting   
      System.out.println("Max: " + maxInt);
      System.out.println("Sum of min and max: " + (minInt + maxInt));
      System.out.println();
   
      /*   BST with Widget objects 	*/
      BST_Generic<Widget> treeOfWidgets = new BST_Generic<Widget>();
      System.out.println("Add some Widgets to the BST_Generic<Widget> tree.");
      for(int n=0; n<(int)(Math.random() * 7)+3; n++)
         treeOfWidgets.add( new Widget( (int)(Math.random() * 10), (int)(Math.random() * 10) ));
   
      System.out.println(treeOfWidgets.display());
      System.out.println("As a string: " + treeOfWidgets.toString());
      System.out.println("Size: " + treeOfWidgets.size());
      Widget minWidget = treeOfWidgets.min();  //no casting
      System.out.println("Min: " + minWidget );
      Widget maxWidget = treeOfWidgets.max();  //no casting
      System.out.println("Max: " + maxWidget);
   }
}



/***************************************************************
  
    2.0 * 3 should be 6.0
 2.0 + 1.0 + 3.0 should be 6.0
 	3.0
 2.0
 	1.0
 
 As a string: 1.0 2.0 3.0 
 Size: 3
 Min: 1.0
 Max: 3.0
 Sum of min and max: 4.0
 
 Add random amount of random Integers to the BST_Generic<Integer> tree
 	9
 8
 	8
 		8
 			6
 					5
 				4
 					0
 						0
 
 As a string: 0 0 4 5 6 8 8 8 9 
 Size: 9
 Min: 0
 Max: 9
 Sum of min and max: 9
 
 Add some Widgets to the BST_Generic<Widget> tree.
 		9 cubits 4 hands
 	8 cubits 9 hands
 8 cubits 4 hands
 		6 cubits 7 hands
 	3 cubits 2 hands
 
 As a string: 3 cubits 2 hands 6 cubits 7 hands 8 cubits 4 hands 8 cubits 9 hands 9 cubits 4 hands 
 Size: 5
 Min: 3 cubits 2 hands
 Max: 9 cubits 4 hands

 

**************************************************/
