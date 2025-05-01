import java.util.*;
import java.io.*;

public class Red_Black_driver
{
   public static void main( String[] args ) throws Exception
   {
      Red_Black balancedTree = new Red_Black();   
      //Scanner in = new Scanner(System.in);
      //System.out.print("Type in a line: ");  
      //String line = in.nextLine();
   
      String line = "5 3 2";  //left-left case (right rotation) 
     // String line = "3 5 7";  //right-right case (left rotation)
      //String line = "5 3 4";  //left-right case (left rotation then right rotation)
     // String line = "3 5 4";  //right-left case (right rotation then left rotation)
      //String line = "a b c d e f g";  //many left rotations
      //String line = "g f e d c b a";  //many right rotations
      //String line = "J E H B F G D"; //J E H right rot., B F right rot., G right rot., D
      //String line = "L P N U R O"; //L P N right-left case, U R right-left case, O right-left case
      //String line = "M H D F E J U L X I P K"; //M H D left rot., F E left-right case, J right rot., U L X left rot., I right-left case, P left rot., K right rot.
      
      
      String[] str = line.split(" ");
      for(String item : str)
      {
         balancedTree.addBalanced( item );  
         System.out.println(balancedTree.display());
         System.out.println("------------------------------");
      }
   }
}