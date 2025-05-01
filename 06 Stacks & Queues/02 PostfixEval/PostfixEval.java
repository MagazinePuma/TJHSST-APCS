// Name: Dennis Tislin
// Date: 1/18

import java.util.Stack;
import java.util.List;
import java.util.ArrayList;
import java.util.Arrays;

public class PostfixEval
{
   public static final String operators = "+ - * / % ^ !";
   
   public static void main(String[] args)
   {
      System.out.println("Postfix  -->  Evaluate");
      ArrayList<String> postfixExp = new ArrayList<String>();
      /*  build your list of expressions here  */
      postfixExp.add("3 4 5 * +");
      postfixExp.add("3 4 * 5 +");
      postfixExp.add("10 20 + -6 6 * +");
      postfixExp.add("3 4 + 5 6 + *");
      postfixExp.add("3 4 5 + * 2 - 5 /");
      postfixExp.add("8 1 2 * + 9 3 / -");
      postfixExp.add("2 3 ^");
      postfixExp.add("20 3 %");
      postfixExp.add("21 3 %");
      postfixExp.add("22 3 %");
      postfixExp.add("23 3 %");
      postfixExp.add("5 !");
      postfixExp.add("1 1 1 1 1 + + + + !");
      
      for( String pf : postfixExp )
      {
         System.out.println(pf + "\t\t" + eval(pf));
      }
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
      return Double.valueOf(stk.pop());
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

//      return op == "+" ? a + b : op == "-" ? a - b : op == "*" ? a * b : op == "/" ? a / b : op == "%" ? a % b : op == "^" ? Math.pow(a,b) : op == "!" ? -1.0 : -1.0;  
   }
   
   public static boolean isOperator(String op)
   {
      // String[] arrOperator = operators.split(" ");
      // String[] arrOp = op.split(" ");
      // for(String str : arrOperator){
      //    for(String ops : arrOp){
      //       if(ops == str){
      //          return true;
      //       }
      //    }
      // }
      // return false;
      
      return operators.contains(op);

      // String[] arrOperator = operators.split(" ");
      // for(String str : arrOperator){
      //    if(op == str){
      //       return true;
      //    }
      // }
      // return false;
   }

   public static int factorial(int n){
      if(n == 1){
         return 1;
      }
      return n * factorial(n - 1);
   }
}

/**********************************************
Postfix  -->  Evaluate
 3 4 5 * +		23
 3 4 * 5 +		17
 10 20 + -6 6 * +		-6
 3 4 + 5 6 + *		77
 3 4 5 + * 2 - 5 /		5
 8 1 2 * + 9 3 / -		7
 2 3 ^		8
 20 3 %		2
 21 3 %		0
 22 3 %		1
 23 3 %		2
 5 !		120
 1 1 1 1 1 + + + + !		120
 
 
 *************************************/