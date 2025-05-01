// Name: Dennis Tislin
// Date: 1/12

import java.util.Stack;
import java.util.Queue;
import java.util.ArrayList;
import java.util.LinkedList;

public class ParenMatch
{
   public static final String LEFT  = "([{<";
   public static final String RIGHT = ")]}>";
   
   public static void main(String[] args)
   {
      System.out.println("Parentheses Match");
      ArrayList<String> parenExp = new ArrayList<String>();
      /* enter test cases here */
      parenExp.add("5 + 7");
      parenExp.add(") 5 + 7 (");
      parenExp.add("[ ( 5 + 7 ) * ] 3");
      parenExp.add("( ( 5.0 - 7.3 ) * 3.5 )");
      parenExp.add("( ) [ ]");
      parenExp.add("( [ ( 5 + 7 ) * 3 ]");
   
      for( String s : parenExp )
      {
         boolean good = checkParen(s);
         if(good)
            System.out.println(s + "\t good!");
         else
            System.out.println(s + "\t BAD");
      }
   }
     
   //returns the index of the left parentheses or -1 if it is not there
   public static int isLeftParen(String p)
   {
      return LEFT.indexOf(p);
   }
  
   //returns the index of the right parentheses or -1 if it is not there
   public static int isRightParen(String p)
   {
      return RIGHT.indexOf(p);
   }
   public static boolean checkParen(String exp)
   {
     /* enter your code here */
     Stack<String> stkParen = new Stack<String>();
     String[] expArr = exp.split(" ");
      for(int i = 0; i < expArr.length; i++){
         if(isLeftParen(expArr[i]) != -1) { // if is left
            stkParen.push(expArr[i]);
         } else if (isRightParen(expArr[i]) != -1) {
            if (stkParen.empty() || isLeftParen(stkParen.pop()) != isRightParen(expArr[i]) ) {
               return false;
            }
         }
      }     
         
     return stkParen.empty();  //so it compiles
   //   Stack<String> stk = new Stack<String>();
   //   ArrayList<String> leftAl = new ArrayList<String>(); 
   //   String[] leftArr = LEFT.split("");
   //   for(int i = 0; i < leftArr.length; i++){
   //       leftAl.add(leftArr[i]);
   //   }
   //   for(ListIterator<String> lit = leftAl.listIterator(); lit.hasNext();){
   //       lit.next();
   //       for(int k = 0; k < leftArr.length; k++){
   //          if(lit.getValue().equals(lit.getValue()))
   //       }
   //   }    
   }
}

/*****************************************

Parentheses Match
5 + 7		 good!
( 15 + -7 )		 good!
) 5 + 7 (		 BAD
( ( 5.0 - 7.3 ) * 3.5 )		 good!
< { 5 + 7 } * 3 >		 good!
[ ( 5 + 7 ) * ] 3		 good!
( 5 + 7 ) * 3		 good!
5 + ( 7 * 3 )		 good!
( ( 5 + 7 ) * 3		 BAD
[ ( 5 + 7 ] * 3 )		 BAD
[ ( 5 + 7 ) * 3 ] )		 BAD
( [ ( 5 + 7 ) * 3 ]		 BAD
( ( ( ) $ ) )		 good!
( ) [ ]		 good!
 
 *******************************************/
