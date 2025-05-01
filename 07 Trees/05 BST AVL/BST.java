// Name: Dennis Tislin
// Date: 2/23

interface BSTinterface
{
   public int size();
   public TreeNode getRoot() ;
   public boolean contains(String obj);
   public void add(String obj);          //does not balance
   public void addBalanced(String obj);  //AVL
   public void remove(String obj);       //does not re-balance
   //public void removeBalanced(String obj); //extension
   public String min();
   public String max();
   public String display();
   public String toString();
}

public class BST implements BSTinterface
{
   /*  copy your BST code  here  */
   private TreeNode root;
   private int size;
   public BST()
   {
      root = null;
      size = 0;
   }
   public int size()
   {
     return size(root);
   }

   private int size(TreeNode t) {
    if(t == null) {
      return 0;
    }
    
    return size(t.getLeft()) + 1 + size(t.getRight());
  }

   public TreeNode getRoot()   //accessor method
   {
      return root;
   }
   /***************************************
   @param s -- one string to be inserted
   ****************************************/
   public void add(String s) 
   {
      // size++;
      root = add(root, s);
   }
   private TreeNode add(TreeNode t, String s) //recursive helper method
   {     
      if(t == null){
         TreeNode newRoot = new TreeNode(s, null, null);
         root = newRoot;
         return newRoot;
      } else if(s.compareTo((String)(t.getValue())) < 0 || s.compareTo((String)(t.getValue())) == 0){
         if(t.getLeft() == null) {
            t.setLeft(new TreeNode(s));
         } else {
            add(t.getLeft(), s);
         }
         //t.setLeft(add(t.getLeft(), s));
      } else {    
         if(t.getRight() == null) {
            t.setRight(new TreeNode(s));
         } else {                                            // if(s.compareTo((String)(t.getValue())) > 0)
            add(t.getRight(), s);
         }
         //t.setRight(add(t.getRight(), s));
      }
      return t;
   }
     /*************************
      Copy the display() method from TreeLab. 
      **********************/
   public String display()
   {
      return display(root, 0);
   }

   private String display(TreeNode t, int level) //recursive helper method
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
   
   public boolean contains(String obj)
   {
      return contains(root, obj);
   }
   private boolean contains(TreeNode t, String x) //recursive helper method
   {
      if(t == null){
         return false;
      } else if(x.compareTo((String)(t.getValue())) < 0){
         return contains(t.getLeft(), x);
      } else if(x.compareTo((String)(t.getValue())) > 0){
         return contains(t.getRight(), x);
      } else {
         return true;
      }
   }
   
   public String min()
   {
      return min(root);
   }
   private String min(TreeNode t)  //use iteration
   {
      if(t == null) return null;
      while(t.getLeft() != null){
         t = t.getLeft();
      }
      return (String)(t.getValue());
   }
   
   public String max()
   {
      return max(root);
   }
   private String max(TreeNode t)  //recursive helper method
   {
      if(t == null) return null;
      if(t.getRight() == null){
         return (String)(t.getValue());
      }
      return max(t.getRight());
   }
   
   public String toString()
   {
      return toString(root);
   }
   private String toString(TreeNode t)  //an in-order traversal.  Use recursion.
   {
      String inOrder = "";
      if(t == null) return "";
      if(t != null && t.getLeft() == null && t.getRight() == null){
         return " " + t.getValue();
      }
      if(t != null) {
         inOrder += toString(t.getLeft());
      }
      inOrder += " " + t.getValue();
      if(t != null) {
         inOrder += toString(t.getRight());
      }
      return inOrder;
   }

   /*  start the addBalanced methods */
   private int calcBalance(TreeNode t) //height to right minus 
   {                                         //height to left
      // int right = 0;
      // int left = 0;
      // TreeNode temp = current;
      // while(temp != null){
      //    temp = temp.getRight();
      //    if(temp != null) {
      //       right++;
      //    }
      // }
      // temp = current;
      // while(temp != null){
      //    temp = temp.getLeft();
      //    if(temp != null) {
      //       left++;
      //    }
      // }
      // return right - left;
      // if(current == null) return 0;
      // return Math.subtractExact(calcBalance(current.getRight()), calcBalance(current.getLeft()));

      if(t == null) {
         return 0;
      }
      int countLeft = height(t.getLeft());
      int countRight = height(t.getRight());
      return countRight - countLeft;

   }

   private int height(TreeNode current)   //from TreeLab
   {
      if(current == null) return -1;
      // int heightLeft = height(current.getLeft());
      // int heightRight = height(current.getRight());
      // return Math.max(heightLeft, heightRight) + 1;
      return Math.max(height(current.getRight()), height(current.getLeft())) + 1;
   }

   public void addBalanced(String value)  
   {
   /*
      // first approach
      add(value);
      root = balanceTree( root );
   */
      
   
      // second approach
      // if(root == null) {
      //    root = new TreeNode(value);
      // } else {
         root = addBalanced(root, value);
      // }
   }
   
   // first approach
   // private TreeNode balanceTree( TreeNode root )   
   // {
   //    return null;
   // }
   
   public void remove(String obj) {
      if(contains(obj)){
         if(root == null){
            return;
         } else if(obj.compareTo((String)(root.getValue())) < 0){
            contains(root.getLeft(), obj);
         } else if(obj.compareTo((String)(root.getValue())) > 0){
            contains(root.getRight(), obj);
         } else {
            if(root.getRight() == null && root.getLeft() == null){
               root = null;
            } else if(height(root) == 0){
               if(root.getLeft().getLeft() != null){
                  root = root.getLeft();
                  root.setLeft(root.getLeft().getLeft());
               } else if(root.getLeft().getRight() != null){
                  root = root.getLeft();
                  root.setLeft(root.getLeft().getRight());
               } else if(root.getRight().getRight() != null){
                  root = root.getRight();
                  root.setRight(root.getRight().getRight());
               } else {
                  root = root.getRight();
                  root.setRight(root.getRight().getLeft());
               }
            }
         }
      }
   }

   //helper method for second approach
   private TreeNode addBalanced(TreeNode t, String strObj)
   {
      if(strObj != null){
         if(!contains(t, strObj)) {
            t = add(t, strObj);
         }
      }
         
      int nodeBalance = calcBalance(t);
      int leftSubtreeBalance = calcBalance(t.getLeft());
      int rightSubtreeBalance = calcBalance(t.getRight());
      // System.out.println("==> Left balance: " + leftSubtreeBalance);
      // System.out.println("==> Right balance: " + rightSubtreeBalance);
     int nodeHeight = height(t);
      // if(nodeBalance > -2 && nodeBalance < 2 ) {
      if(Math.abs(nodeBalance) == 2 || Math.abs(leftSubtreeBalance) == 2 || Math.abs(rightSubtreeBalance) == 2) {
         if(Math.abs(leftSubtreeBalance) == 2) {
            t.setLeft(addBalanced(t.getLeft(), null));
         }
         if(Math.abs(rightSubtreeBalance) == 2) {
            t.setRight(addBalanced(t.getRight(), null));
         }
   
         if(nodeBalance > 0) {
            int righNodeBalance = calcBalance(t.getRight());
            if(righNodeBalance > 0) {
               t = leftLeft(t);
            }
            if(righNodeBalance < 0) {
               t = rightLeft(t);
            }
            
         } else {
            int leftNodeBalance = calcBalance(t.getLeft());
            if(leftNodeBalance < 0) {
               t = rightRight(t);
            }
            if(leftNodeBalance > 0) {
               t = leftRight(t);
            }
         }
         // t = addBalanced(t, null);
      }

         // addBalanced(t.getLeft(), (String)(t.getLeft().getValue()));
         // addBalanced(t.getLeft(), (String)(t.getLeft().getValue()));
         // if(calcBalance(t) == 2 || calcBalance(t) == -2){
         //    if(calcBalance(t) == 2){
         //       if(t.getRight().getRight() != null){
         //          rightRight(t);
         //       } else {
         //          rightLeft(t);
         //       }
         //    } else {
         //       if(t.getLeft().getLeft() != null){
         //          leftLeft(t);
         //       } else {
         //          leftRight(t);
         //       }
         //    }
         // } else {
         //    addBalanced(t, strObj);
         // }

      return t;
   }
   
   /*  write the four rotation methods   */
   private TreeNode rightRight(TreeNode t)
   {
      // TreeNode temp = t;
      // if(calcBalance(temp) == 2 && temp.getRight().getRight() != null){
      //    temp.getRight().setLeft(t);
      // }
      // TreeNode temp = t.getLeft();
      // temp.setRight(new TreeNode(t.getValue()));

      TreeNode newOne = t.getLeft();
      TreeNode newTwo = t;
      newTwo.setLeft(t.getLeft().getRight());
      newOne.setRight(newTwo);

      return newOne;
   }

   private TreeNode rightLeft(TreeNode t)
   {
      // TreeNode temp = t;
      // if(calcBalance(temp) == 2 && temp.getRight().getLeft() != null){
      //    TreeNode nTemp = t.getRight();
      //    temp.getRight().getLeft().setLeft(temp);
      //    temp.getRight().getLeft().setRight(nTemp);
      // }
      // return temp;

      TreeNode temp = t;
      TreeNode newOne = t.getRight().getLeft();
      TreeNode newTwo = t.getRight();
      newTwo.setLeft(t.getRight().getLeft().getRight());
      newOne.setRight(newTwo);

      temp.setRight(newOne);
      return leftLeft(temp);
   }
   
   private TreeNode leftLeft(TreeNode t)
   {
      // TreeNode temp = t;
      // if(calcBalance(temp) == -2 && temp.getLeft().getLeft() != null){
      //    temp.getLeft().setRight(t);
      // }
      // return temp;
      // TreeNode temp = t.getRight();
      // temp.setLeft(new TreeNode(t.getValue()));

      TreeNode newOne = t.getRight();
      TreeNode newTwo = t;
      newTwo.setRight(t.getRight().getLeft());
      newOne.setLeft(newTwo);

      return newOne;
   }

   private TreeNode leftRight(TreeNode t)
   {
      // if(calcBalance(temp) == -2 && temp.getLeft().getRight() != null){
      //    TreeNode nTemp = t.getLeft();
      //    temp.getLeft().getRight().setLeft(nTemp);
      //    temp.getLeft().getRight().setRight(t);
      // }
      // return temp;

      // TreeNode temp = new TreeNode(t.getValue());
      // temp.setLeft(t.getLeft().getRight());
      // temp.getLeft().setLeft(new TreeNode(t.getLeft().getValue()));
      // return rightRight(temp);

      TreeNode temp = t;
      TreeNode newOne = t.getLeft().getRight();
      TreeNode newTwo = t.getLeft();
      newTwo.setRight(t.getLeft().getRight().getLeft());
      newOne.setLeft(newTwo);

      temp.setLeft(newOne);
      return rightRight(temp);
   }
}