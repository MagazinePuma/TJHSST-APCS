//  Driver for the Station class.
public class Station_Driver
{
   public static void main(String[] args) 
   {
      Station downtown = new Station("Downtown", 1);
      Station suburb = new Station("Suburb", 4);
      
      System.out.println("Station name: " + downtown.getName());
      System.out.println("Station zone: " + downtown.getZone());
      System.out.println("toString(): " + downtown.toString());
      System.out.println();
      
      System.out.println("Station name: " + suburb.getName());
      System.out.println("Station zone: " + suburb.getZone());
      System.out.println("toString(): " + suburb.toString());
   }
}
