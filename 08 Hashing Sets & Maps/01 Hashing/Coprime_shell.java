import java.util.ArrayList;

public class Coprime
{
   public static void main(String[] args)
   {
      ArrayList<Integer> lengths = new ArrayList<>();
      lengths.add(10);
      lengths.add(15);
      lengths.add(6);
      lengths.add(8);
      lengths.add(12);
   
      for( Integer len : lengths )
      {
         int k = coprime( len );
         System.out.println( k + " is the smallest coprime with " + len);
      }
   }
   
   public static int coprime(int length)
   { 
   
   }

}

/******************* Sample Run ************
 
 3 is the smallest coprime with 10
 2 is the smallest coprime with 15
 5 is the smallest coprime with 6
 3 is the smallest coprime with 8
 5 is the smallest coprime with 12
 
 *******************************************/