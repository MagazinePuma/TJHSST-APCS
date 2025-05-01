public class Bts {
    TreeNode parent = root;
         String pValue = (String)(parent.getValue());
         
         // while(parent.getRight() != null || parent.getLeft() != null || !parent.getLeft().getValue().equals((Object)(target)) || !parent.getRight().getValue().equals(target)){
         //    if(pValue.compareTo((String)(current.getValue())) < 0){
         //       parent = parent.getRight();
         //       pValue = (String)(parent.getValue()); 
         //    } else {
         //       parent = parent.getLeft();
         //       pValue = (String)(parent.getValue()); 
         //    }
         // }
      if(contains(current, target)){
      if(target.compareTo((String)(current.getValue())) < 0 && !current.getLeft().getValue().equals((Object)(target))){
         // if((String)(parent.getLeft().getValue()) != target || (String)(parent.getRight().getValue()) != target){
         //    parent = parent.getLeft();
         // }
         if(current.getLeft() != null && !current.getLeft().getValue().equals((Object)(target))){
            remove(current.getLeft(), target);
         }
         // remove(current, target);
      } else if(target.compareTo((String)(current.getValue())) > 0 && !current.getRight().getValue().equals((Object)(target))){
         // if((String)(parent.getLeft().getValue()) != target || (String)(parent.getRight().getValue()) != target){
         //    parent = parent.getRight();
         // }
         if(current.getRight() != null && !current.getRight().getValue().equals((Object)(target))){
            remove(current.getRight(), target);
         }
         // remove(current, target);
      } /*else if(target.compareTo((String)(current.getValue())) == 0){

      }*/
      if(current.getLeft() != null && current.getRight() != null){                     // target.compareTo((String)(current.getValue())) == 0
         if(current.getLeft().getValue().equals((Object)(target))){
            if(current.getLeft().getLeft() == null && current.getLeft().getRight() == null){
               current.setLeft(null);
            } else if(current.getLeft().getLeft() != null && current.getLeft().getRight() != null){
               current.getLeft().setValue((Object)(max(current.getLeft().getLeft())));
               remove(current.getLeft().getLeft(), max(current.getLeft().getLeft()));
            } else {                                                                      // if(current.getLeft() == null && current.getRight() != null)
               // while((String)(parent.getLeft().getValue()) != target || (String)(parent.getRight().getValue()) != target){
               //    if(pValue.compareTo((String)(current.getValue())) < 0){
               //       parent = parent.getRight();
               //       pValue = (String)(parent.getValue()); 
               //    } else {
               //       parent = parent.getLeft();
               //       pValue = (String)(parent.getValue()); 
               //    }
               // }
               if(current.getRight().getRight() != null && current != root){
                  current.setRight(current.getRight().getRight());
               } else {
                  current.setLeft(current.getLeft().getLeft());
               }
            }
         } else if(current.getRight().getValue().equals((Object)(target))){
            if(current.getRight().getRight() == null && current.getRight().getLeft() == null){
               current.setRight(null);
            } else if(current.getRight().getRight() != null && current.getRight().getLeft() != null){
               current.getRight().setValue((Object)(max(current.getRight().getLeft())));
               remove(current.getLeft().getRight(), max(current.getRight().getLeft()));              // .getLeft()
            } else {                                                                      // if(current.getLeft() == null && current.getRight() != null)
               // while((String)(parent.getLeft().getValue()) != target || (String)(parent.getRight().getValue()) != target){
               //    if(pValue.compareTo((String)(current.getValue())) < 0){
               //       parent = parent.getRight();
               //       pValue = (String)(parent.getValue()); 
               //    } else {
               //       parent = parent.getLeft();
               //       pValue = (String)(parent.getValue()); 
               //    }
               // }
               if(current.getRight().getRight() != null){
                  current.setRight(current.getRight().getRight());
               } else {
                  current.setLeft(current.getLeft().getLeft());
               }
            }
         }                                                                             
         // if(current.getLeft() == null && current.getRight() == null){
         //    current.setValue(null);
         // } else if(current.getLeft() != null && current.getRight() != null){
         //    current.setValue((Object)(max(current.getLeft())));
         //    remove(current.getLeft(), max(current.getLeft()));
         // } else {                                                                      // if(current.getLeft() == null && current.getRight() != null)
         //    // while((String)(parent.getLeft().getValue()) != target || (String)(parent.getRight().getValue()) != target){
         //    //    if(pValue.compareTo((String)(current.getValue())) < 0){
         //    //       parent = parent.getRight();
         //    //       pValue = (String)(parent.getValue()); 
         //    //    } else {
         //    //       parent = parent.getLeft();
         //    //       pValue = (String)(parent.getValue()); 
         //    //    }
         //    // }
         //    if(current.getRight().getRight() != null){
         //       current.setRight(current.getRight().getRight());
         //    } else {
         //       current.setLeft(current.getLeft().getLeft());
         //    }
         // }                                                                             // if(current.getLeft() != null && current.getRight() == null)
      } else if(current.getLeft() == null && current.getRight() != null){
         if(current.getRight().getRight() == null && current.getRight().getLeft() == null){
            current.setRight(null);
         } else if(current.getRight().getRight() != null && current.getRight().getLeft() != null){
            current.getRight().setValue((Object)(max(current.getRight().getLeft())));
            remove(current.getRight(), max(current.getRight().getLeft()));
         } else {                                                                      // if(current.getLeft() == null && current.getRight() != null)
            // while((String)(parent.getLeft().getValue()) != target || (String)(parent.getRight().getValue()) != target){
            //    if(pValue.compareTo((String)(current.getValue())) < 0){
            //       parent = parent.getRight();
            //       pValue = (String)(parent.getValue()); 
            //    } else {
            //       parent = parent.getLeft();
            //       pValue = (String)(parent.getValue()); 
            //    }
            // }
            if(current.getRight().getRight() != null){
               current.setRight(current.getRight().getRight());
            } else {
               current.setLeft(current.getLeft().getLeft());
            }
         }
      } else if(current.getLeft() != null && current.getRight() == null){
         if(current.getLeft().getLeft() == null && current.getLeft().getRight() == null){
            current.setLeft(null);
         } else if(current.getLeft().getLeft() != null && current.getLeft().getRight() != null){
            current.getLeft().setValue((Object)(max(current.getLeft().getLeft())));
            remove(current.getLeft().getLeft(), max(current.getLeft().getLeft()));
         } else {                                                                      // if(current.getLeft() == null && current.getRight() != null)
            // while((String)(parent.getLeft().getValue()) != target || (String)(parent.getRight().getValue()) != target){
            //    if(pValue.compareTo((String)(current.getValue())) < 0){
            //       parent = parent.getRight();
            //       pValue = (String)(parent.getValue()); 
            //    } else {
            //       parent = parent.getLeft();
            //       pValue = (String)(parent.getValue()); 
            //    }
            // }
            if(current.getLeft().getRight() != null){
               current.setRight(current.getRight().getRight());
            } else {
               current.setLeft(current.getLeft().getLeft());
            }
         }
      // } else if(target.compareTo((String)(current.getValue())) < 0){
      //    // if((String)(parent.getLeft().getValue()) != target || (String)(parent.getRight().getValue()) != target){
      //    //    parent = parent.getLeft();
      //    // }
      //    if(current.getLeft() != null && !current.getLeft().getValue().equals((Object)(target))){
      //       remove(current.getLeft(), target);
      //    }
      //    remove(current, target);
      // } else {
      //    // if((String)(parent.getLeft().getValue()) != target || (String)(parent.getRight().getValue()) != target){
      //    //    parent = parent.getRight();
      //    // }
      //    if(current.getRight() != null && !current.getRight().getValue().equals((Object)(target))){
      //       remove(current.getRight(), target);
      //    }
      //    remove(current, target);
      // }
      }
      return root;
      // case 2a
      
      // case 2b
      
      // etc.
   }
   return current;
}



TreeNode parent = root;
         String pValue = (String)(parent.getValue());
         
         // while(parent.getRight() != null || parent.getLeft() != null || !parent.getLeft().getValue().equals((Object)(target)) || !parent.getRight().getValue().equals(target)){
         //    if(pValue.compareTo((String)(current.getValue())) < 0){
         //       parent = parent.getRight();
         //       pValue = (String)(parent.getValue()); 
         //    } else {
         //       parent = parent.getLeft();
         //       pValue = (String)(parent.getValue()); 
         //    }
         // }


         // if((String)(parent.getLeft().getValue()) != target || (String)(parent.getRight().getValue()) != target){
         //    parent = parent.getLeft();
         // }

         while(parent.getRight() != current || parent.getLeft() != current){
            if(target.compareTo((String)(parent.getValue())) < 0){
               parent = parent.getLeft();
            } else {
               parent = parent.getRight();
            }
         }

         private TreeNode parent(TreeNode parent, TreeNode target){
            String pValue = (String)(parent.getValue());
            while(parent.getLeft() != target || parent.getRight() != target){
               if(pValue.compareTo((String)(target.getValue())) < 0){
                  parent = parent.getLeft();
                  pValue = (String)(parent.getValue());
               } else {
                  parent = parent.getRight();
                  pValue = (String)(parent.getValue());
               }
            }
            return parent;
         }
