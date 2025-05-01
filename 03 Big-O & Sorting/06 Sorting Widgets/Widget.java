// Name: Dennis Tislin
// Date: 11/18

public class Widget implements Comparable<Widget>
{
   //fields
   private int cubits;
   private int hands;
   
   //constructors
   public Widget(int c, int h){     //2 arg
      cubits = c;
      hands = h;
   }
   public Widget(Widget widget){    //copy
      this.cubits = widget.cubits;
      this.hands = widget.hands;
   }
   public Widget(){                 //default

   }
   
   //accessors and modifiers
   public int getCubits(){
      return cubits;
   }
   public int getHands(){
      return hands;
   }
   public void setCubits(int newCubits){
      cubits = newCubits;
   }
   public void setHands(int newHands){
      hands = newHands;
   }
   
   //compareTo(Widget) and equals(Widget)
   public int compareTo(Widget other){
      if(this.cubits == other.cubits){
         return this.hands - other.hands;
      }
      return this.cubits - other.cubits;
   }
   public boolean equals(Widget other){
      return this.cubits == other.cubits && this.hands == other.hands;
   }
   
   //toString
   public String toString(){
      return "" + cubits + " cubits " + hands + " hands";
   }
   
}
