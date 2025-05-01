// Name: Dennis Tislin
// Date: 1/26

interface BST_Generic_interface<E>
{
   public int size();
   public TreeNode<E> getRoot() ;
   public boolean contains(E obj);
   public void add(E obj);         //does not balance
  //  public void addBalanced(E obj); //AVL
  //  public void remove(E obj);      //does not balance
   public E min();
   public E max();
   public String display();
   public String toString();
}

/*******************
Copy your BST code.  Implement generics.
If you skipped remove() and/or addBalanced(), just leave the method bodies empty.
**********************/
public class BST_Generic<E extends Comparable<E>> implements BST_Generic_interface<E>
{
  private TreeNode<E> root;
  private int size;
  public BST_Generic()
  {      
    root = null;
    size = 0;
  }
  public int size()
  {
    return size;
  }
  public TreeNode<E> getRoot()   //accessor method
  {
    return root;
  }
  /***************************************
 @param s -- one string to be inserted
  ****************************************/
  public void add(E s) 
  {
    size++;
    root = add(root, s);
  }
  private TreeNode<E> add(TreeNode<E> t, E s) //recursive helper method
  {     
    if(t == null){
        TreeNode<E> newRoot = new TreeNode(s, null, null);
        root = newRoot;
        return newRoot;
    } else if(s.compareTo(t.getValue()) < 0 || s.compareTo(t.getValue()) == 0){
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

  private String display(TreeNode<E> t, int level) //recursive helper method
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
  
  public boolean contains(E obj)
  {
    return contains(root, obj);
  }
  private boolean contains(TreeNode<E> t, E x) //recursive helper method
  {
    if(t == null){
        return false;
    } else if(x.compareTo(t.getValue()) < 0){
        return contains(t.getLeft(), x);
    } else if(x.compareTo(t.getValue()) > 0){
        return contains(t.getRight(), x);
    } else {
        return true;
    }
  }
  
  public E min()
  {
    return min(root);
  }
  private E min(TreeNode<E> t)  //use iteration
  {
    if(t == null) return null;
    while(t.getLeft() != null){
        t = t.getLeft();
    }
    return t.getValue();
  }
  
  public E max()
  {
    return max(root);
  }
  private E max(TreeNode<E> t)  //recursive helper method
  {
    if(t == null) return null;
    if(t.getRight() == null){
        return t.getValue();
    }
    return max(t.getRight());
  }
  
  public String toString()
  {
    return toString(root);
  }
  private String toString(TreeNode<E> t)  //an in-order traversal.  Use recursion.
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

/*******************
  Copy your TreeNode code.  Implement generics.
**********************/
class TreeNode<E>
{
  private E value; 
  private TreeNode<E> left, right;

  public TreeNode(E initValue)
  { 
    value = initValue; 
    left = null; 
    right = null; 
  }

  public TreeNode(E initValue, TreeNode<E> initLeft, TreeNode<E> initRight)
  { 
    value = initValue; 
    left = initLeft; 
    right = initRight; 
  }

  public E getValue()
  { 
    return value; 
  }

  public TreeNode<E> getLeft() 
  { 
    return left; 
  }

  public TreeNode<E> getRight() 
  { 
    return right; 
  }

  public void setValue(E theNewValue) 
  { 
    value = theNewValue; 
  }

  public void setLeft(TreeNode<E> theNewLeft) 
  { 
    left = theNewLeft;
  }

  public void setRight(TreeNode<E> theNewRight)
  { 
    right = theNewRight;
  }
}