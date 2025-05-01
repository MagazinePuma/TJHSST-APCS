// Name: Dennis Tislin
// Date: 1/26
//uses PostfixEval

import java.util.Stack;
import java.util.List;
import java.util.ArrayList;
import java.util.Arrays;
public class InfixPostfixEval
{
   public static final String LEFT  = "([{<";
   public static final String RIGHT = ")]}>";
   public static final String operators = "+ - * / % ^ !";
   
   public static void main(String[] args)
   {
      System.out.println("Infix  \t-->\tPostfix\t\t-->\tEvaluate");
      /*build your list of Infix expressions here  */
      List<String> infixExp = new ArrayList<String>();  
      
      infixExp.add("8 + 1 * 2 - 9 / 3");
      infixExp.add("5 - 1 + 1");
      infixExp.add("12 / 6 / 2");
      infixExp.add("3 + 4 * 5");
      infixExp.add("3 * 4 + 5");
      infixExp.add("1.3 + 2.7 + -6 * 6");   
      infixExp.add("( 33 + -43 ) * ( -55 + 65 )");
      infixExp.add("8 + 1 * 2 - 9 / 3");
      infixExp.add("3 * ( 4 * 5 + 6 )");
      infixExp.add("3 + ( 4 - 5 - 6 * 2 )");
      infixExp.add("2 + 7 % 3");
      infixExp.add("( 2 + 7 ) % 3");
     
         
      for( String infix : infixExp )
      {
         try{
            String pf = infixToPostfix(infix);  //get this conversion to work first
            //System.out.println(infix + "\t\t\t" + pf );  
            System.out.println(infix + "\t\t\t" + pf + "\t\t\t" + eval(pf));  //PostfixEval must work!
         }
         catch(Exception e)
         {
            System.out.println(e.toString());
         } 
      }
   }
   
   public static String infixToPostfix(String infix)
   {
      List<String> nums = new ArrayList<String>(Arrays.asList(infix.split(" ")));
            /* enter your code here  */
      Stack<String> operator = new Stack<String>();
      String postfix = "";
      for(String str : nums){
         if(!isOperator(str) && !LEFT.contains(str) && !RIGHT.contains(str)){
            postfix += str + " ";
         } else if(isOperator(str)) {
            if(!operator.isEmpty()){

               while(!operator.isEmpty() 
                  && !LEFT.contains(operator.peek()) 
                  && !isOperator(operator.peek()) 
               ){
                  postfix += operator.pop() + " ";
               }
               if(isOperator(operator.peek()) && isHigherOrEqual(operator.peek(), str)) {
               } else if(!LEFT.contains(operator.peek())){
                  postfix += operator.pop() + " ";
               }
               operator.push(str);

            } else {
               operator.push(str);
            }
         } else if(LEFT.contains(str)) {
            operator.push(str);
          } else if(RIGHT.contains(str)){
            while(!operator.isEmpty() && RIGHT.indexOf(str) != LEFT.indexOf(operator.peek())) {
               postfix += operator.pop() + " ";
            }
            if(!operator.isEmpty()) {
               operator.pop();
            }
         }
      }
      while(!operator.isEmpty()) {
         postfix += operator.pop() + " ";
      }
      return postfix.trim();
   }

   public static boolean isOperator(String op)
   {
      return operators.contains(op);
   }

   public static int factorial(int n){
      if(n == 1){
         return 1;
      }
      return n * factorial(n - 1);
   }

   public static double eval(String pf)
   {
      List<String> postfixParts = new ArrayList<String>(Arrays.asList(pf.split(" ")));
      // Iterator<String> it = postfixParts.iterator();
      /*  enter your code here  */
      Stack<String> stk = new Stack<String>();
      for(String str : postfixParts){
         if(!isOperator(str)){
            stk.push(str);
         } else if(str.equals("!")) {
            int factorialNum = Double.valueOf(stk.pop()).intValue(); 
            stk.push(String.valueOf(factorial(factorialNum)));
         } else {
            if("!".equals(str)) { // factorial operator
               int factorialValue = Integer.parseInt(stk.pop());
               stk.push(String.valueOf(factorial(factorialValue)));
            } else {
               double valueTwo = Double.valueOf(stk.pop());
               double valueOne = Double.valueOf(stk.pop());
               stk.push(String.valueOf(eval(valueOne, valueTwo, str)));
               }

         }
      }
      return Math.round(Double.valueOf(stk.pop()) * 10) / 10.0;
   }
   
   public static double eval(double a, double b, String op)
   {
      if("+".equals(op)) {
         return a + b;
      } else if("-".equals(op)) {
         return a - b;
      } else if("*".equals(op)) {
         return a * b;
      } else if("/".equals(op)) {
         return a / b;
      } else if("%".equals(op)) {
         return a % b;
      } else if("^".equals(op)) {
         return Math.pow(a,b);
      }
      return -1;
   }
   
   //enter your precedence method below
   public static boolean isHigherOrEqual(String top, String next)
   {
      if(next.equals("!")){
         if(top.equals("!")){
            return false;
         } else {
            return true;
         }
      } else if(top.equals("!")){
         return false;
      } else if(next.equals("*") || next.equals("/") || next.equals("%")){
         if(top.equals("-") || top.equals("+")){
            return true;
         } else {
            return false;
         }
      } else if(top.equals("*") || top.equals("/") || top.equals("%")){
         return false;
      } else if(next.equals("+") || next.equals("-")){
         return false;
         // if(top.equals("*") || top.equals("/") || top.equals("%")){
         //    return false;
         // } else {
         //    return true;
         // }
      } else if(top.equals("+") || top.equals("-")){
         if(next.equals("*") || next.equals("/") || next.equals("%")){
            return true;
         } else {
            return false;
         }
      } else {
         return false;
      }
   }
}


/********************************************

Infix  	-->	Postfix		-->	Evaluate
 5 - 1 - 1			5 1 - 1 -			3.0
 5 - 1 + 1			5 1 - 1 +			5.0
 12 / 6 / 2			12 6 / 2 /			1.0
 3 + 4 * 5			3 4 5 * +			23.0
 3 * 4 + 5			3 4 * 5 +			17.0
 1.3 + 2.7 + -6 * 6			1.3 2.7 + -6 6 * +			-32.0
 ( 33 + -43 ) * ( -55 + 65 )			33 -43 + -55 65 + *			-100.0
 8 + 1 * 2 - 9 / 3			8 1 2 * + 9 3 / -			7.0
 3 * ( 4 * 5 + 6 )			3 4 5 * 6 + *			78.0
 3 + ( 4 - 5 - 6 * 2 )			3 4 5 - 6 2 * - +			-10.0
 2 + 7 % 3			2 7 3 % +			3.0
 ( 2 + 7 ) % 3			2 7 + 3 %			0.0
      
***********************************************/
