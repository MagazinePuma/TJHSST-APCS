// Name: Dennis Tislin
// Date: 10/10
  
import java.util.*;
public class Fibonacci
{
   public static void main(String[] args)
   {
      long start, end, fib; //why long?
      int lastFibNumber = 43;
      int[] fibNumber = {1};
      System.out.println("\tFibonacci\tBy Iteration\tTime\tby Recursion\t Time");
      for(int n = fibNumber[0]; n <= lastFibNumber; n++)
      { 
         start = System.nanoTime();
         fib = fibIterate(n);
         end = System.nanoTime();
         System.out.print("\t\t" + n + "\t\t\t" + fib + "\t\t\t\t\t\t" + (end-start)/1000.);
         start = System.nanoTime();   	
         fib = fibRecur(n);      
         end = System.nanoTime();
         System.out.println("\t\t\t" + fib + "\t\t\t\t" + (end-start)/1000.);
      }
   }
   
   /**
    * Calculates the nth Fibonacci number by interation
    * @param n A variable of type int representing which Fibonacci number
    *          to retrieve
    * @returns A long data type representing the Fibonacci number
    */
   public static long fibIterate(int n)
   {  
      int last = 1;
      int previous = 0;
      int result = 1;

      for (int i = 1; i < n; i++){
         result = last + previous;
         previous = last;
         last = result;
      }
      return result;

   }

   /**
    * Calculates the nth Fibonacci number by recursion
    * @param n A variable of type int representing which Fibonacci number
    *          to retrieve
    * @returns A long data type representing the Fibonacci number
    */
    public static long fibRecur(int n)
   {
        if (n == 1){
         return 1;
        } else if (n == 0){
         return 0;
        }
        return fibRecur(n - 1) + fibRecur(n - 2);
         
   }
}