// Name: Dennis Tislin
// Date: 2/24

interface BSTinterface
{
   public int size();
   public TreeNode getRoot();
   public boolean contains(String obj);
   public void add(String obj);           //does not balance
   //public void addBalanced(String obj);  //AVL
   public void remove(String obj);    
   //public void removeBalanced(String obj); //extra lab
   public String min();
   public String max();
   public String display();
   public String toString();
}

/*******************
BST. Implement the remove() method.
Test it with BST_Remove_Driver.java
**********************/
public class BST implements BSTinterface
{
   /*  copy your BST code here  */
   private TreeNode root;
   private int size;
   public BST()
   {      
      root = null;
      size = 0;
   }
   public int size()
   {
     return size;
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
      size++;
      root = add(root, s);
   }
   private TreeNode add(TreeNode t, String s) //recursive helper method
   {     
      if(t == null){
         TreeNode newRoot = new TreeNode(s, null, null);
         root = newRoot;
         return newRoot;
      } else if(s.compareTo((String)(t.getValue())) < 0 || s.compareTo((String)(t.getValue())) == 0){
         t.setLeft(add(t.getLeft(), s));
      } else {                                                 // if(s.compareTo((String)(t.getValue())) > 0)
         t.setRight(add(t.getRight(), s));
      }
      return t;
      // TreeNode toAdd = new TreeNode(s); 
      // if((int)(t.getValue()) < (int)(toAdd.getValue())){
      //    t.setRight(toAdd);
      //    return add(root, s);
      // } else if((int)(t.getValue()) > (int)(toAdd.getValue()) || (int)(t.getValue()) == (int)(toAdd.getValue())){
      //    t.setLeft(toAdd);
      //    return add(root, s);
      // } else {
      //    return toAdd;
      // }
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



   /*  precondition:  target must be in the tree.
                      implies that tree cannot be null.
   */
   public void remove(String target)
   {
      root = remove(root, target);
      size--;
   }
   private TreeNode remove(TreeNode current, String target)
   {
      // case 1a
      if(target.compareTo((String)(current.getValue())) == 0){
         if(current != null && current.getLeft() == null && current.getRight() == null){
            current = null;
            return current;
            // current.setValue(null);
            // TreeNode parent = root;
            // while((parent.getRight() != null && !parent.getRight().getValue().equals(current.getValue())) && (parent.getLeft() != null && !parent.getLeft().equals(current.getValue()))){
            //    if(target.compareTo((String)(parent.getValue())) < 0){
            //       parent = parent.getLeft();
            //    } else {
            //       parent = parent.getRight();
            //    }
            //    if(parent.getLeft() != null && parent.getLeft().getValue().equals(target)) {
            //       parent.setLeft(null);
            //    }
            //    if(parent.getRight() != null && parent.getRight().getValue().equals(target)) {
            //       parent.setRight(null);
            //    }
            // }
         } else if(current.getLeft() != null && current.getRight() != null){
            String maxChild = max(current.getLeft());
            current.setValue((Object)maxChild);
            remove(current.getLeft(), maxChild);
         } else {                                                                      // if(current.getLeft() == null && current.getRight() != null)
            if(current.getRight() != null){
               current.setValue(current.getRight().getValue());
               if(current.getRight().getLeft() != null){
                  current.setLeft(current.getRight().getLeft());
               }
               current.setRight(current.getRight().getRight());
            } else {
               current.setValue(current.getLeft().getValue());
               if(current.getLeft().getRight() != null){
                  current.setRight(current.getLeft().getRight());
               }
               current.setLeft(current.getLeft().getLeft());
            }
         }                                                                             // if(current.getLeft() != null && current.getRight() == null)
      } else if(current != null && target.compareTo((String)(current.getValue())) < 0){
         TreeNode node = remove(current.getLeft(), target);
         if(node == null) {
            current.setRight(null);
         }
      } else if(current != null && target.compareTo((String)(current.getValue())) > 0){
         TreeNode node = remove(current.getRight(), target);
         if(node == null) {
            current.setRight(null);
         }
      }
      return current;
      // case 2a
      
      // case 2b
      
      // etc.

   }
}