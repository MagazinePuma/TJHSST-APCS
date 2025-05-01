// Name: Dennis Tislin
// Date: 2/13 
/*  Represents a binary expression tree.
 *  The BXT builds itself from postorder expressions. It can
 *  evaluate and print itself.  Also prints inorder and postorder strings. 
 */
 
import java.util.*;
import java.util.Stack;

public class BXT
{
   public static final String operators = "+ - * / % ^ !";
   private TreeNode root;   
   
   public BXT()
   {
      root = null;
   }
   public TreeNode getRoot()   
   {
      return root;
   }
    
   public void buildTree(String str)
   {
     	Stack<TreeNode> postfixElements = new Stack<TreeNode>();
      String[] operatorArr = str.split(" ");
      if(!str.isEmpty()){
         for(String op : operatorArr){
            if(isOperator(op)){
               TreeNode toSet = new TreeNode(op);
               toSet.setRight(postfixElements.pop());
               toSet.setLeft(postfixElements.pop());
               postfixElements.push(toSet);
            } else {
               postfixElements.push(new TreeNode(op));
            }
         }
      }
      root = postfixElements.pop();
   }
   
   public double evaluateTree()
   {
      return evaluateNode(root);
   }
   
   private double evaluateNode(TreeNode t)  //recursive
   {
      if(isOperator((String)t.getValue())){
         return computeTerm((String)t.getValue(), evaluateNode(t.getLeft()), evaluateNode(t.getRight()));
      }
      return Double.valueOf((String)t.getValue());
   }
   
   private double computeTerm(String s, double a, double b)
   {
      // return -99.0;
      if("+".equals(s)) {
         return a + b;
      } else if("-".equals(s)) {
         return a - b;
      } else if("*".equals(s)) {
         return a * b;
      } else if("/".equals(s)) {
         return a / b;
      } else if("%".equals(s)) {
         return a % b;
      }
      return 0;
   }
   
   private boolean isOperator(String s)
   {
      if(s.equals("+") || s.equals("-") || s.equals("*") || s.equals("/") || s.equals("%")){
         return true;
      }
      return false;
   }
   
   public String display()
   {
      return display(root, 0);
   }
   
   private String display(TreeNode t, int level)
   {
      String toRet = "";
      if(t == null)
         return "";
      toRet += display(t.getRight(), level + 1); //recurse right
      for(int k = 0; k < level; k++)
         toRet += "\t";
      toRet += t.getValue() + "\n";
      toRet += display(t.getLeft(), level + 1); //recurse left
      return toRet;
   }
    
   public String inorderTraverse()
   {
      return inorderTraverse(root);
   }
   
   private String inorderTraverse(TreeNode t)
   {
      String inOrder = "";
      if(t != null && t.getLeft() == null && t.getRight() == null){
         return " " + t.getValue();
      }
      if(t != null) {
         inOrder += inorderTraverse(t.getLeft());
      }
      inOrder += " " + t.getValue();
      if(t != null) {
         inOrder += inorderTraverse(t.getRight());
      }
      return inOrder;
   }
   
   public String preorderTraverse()
   {
      return preorderTraverse(root);
   }
   
   private String preorderTraverse(TreeNode t)
   {
      String preOrder = "";

      if(t != null && t.getLeft() == null && t.getRight() == null){
         return preOrder += " " + t.getValue();
      }
      preOrder += " " + t.getValue();
      if(t != null) {
         preOrder += preorderTraverse(t.getLeft());
      }
      if(t != null) {
         preOrder += preorderTraverse(t.getRight());
      }
      return preOrder;
   }
   
  /* extension */
   // public String inorderTraverseWithParentheses()
   // {
      // return inorderTraverseWithParentheses(root);
   // }
//    
   // private String inorderTraverseWithParentheses(TreeNode t)
   // {
      // return "";
   // }
}