//Name: Dennis Tislin
//Date: 2/19

interface BSTinterface
{
   public int size();
   public TreeNode getRoot();
   public boolean contains(String obj);
   public void add(String obj);           //does not balance
   //public void addBalanced(String obj);  //AVL
   //public void remove(String obj);       //BST with remove
   //public void removeBalanced(String obj); //extra lab 
   public String min();
   public String max();
   public String display();
   public String toString();   //in-order traversal
}

/*******************
Represents a binary search tree holding Strings. 
Implements (most of) BSTinterface, above. 
The recursive methods all have a public method calling a private helper method. 
Copy the display() method from TreeLab. 
**********************/
class BST implements BSTinterface
{
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
}
