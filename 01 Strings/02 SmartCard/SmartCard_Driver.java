//  Driver for the SmartCard and Station classes.
public class SmartCard_Driver
{
   public static void main(String[] args) 
   {
      Station downtown = new Station("Downtown", 1);
      Station center = new Station("Center City", 1);
      Station uptown = new Station("Uptown", 2);
      Station suburb = new Station("Suburb", 4);
   
      SmartCard buddy = new SmartCard(20.00); 
      
      System.out.println("getBalance() "+ buddy.getBalance());
      System.out.println("getFormattedBalance() "+ buddy.getFormattedBalance());
      System.out.println("getIsBoarded() "+ buddy.getIsBoarded());
      System.out.println("getBoardedAt() "+ buddy.getBoardedAt());        		
      System.out.println();
      
      buddy.board(downtown);
      System.out.println("getIsBoarded() "+ buddy.getIsBoarded());
      System.out.println("getBoardedAt() "+ buddy.getBoardedAt());
      System.out.println();  
               
      SmartCard z = new SmartCard(10.00); 
      z.board(downtown);
      System.out.println("getIsBoarded() "+ z.getIsBoarded());
      System.out.println("getBoardedAt() "+ z.getBoardedAt());
      z.board(suburb);             // boards twice without exiting
      System.out.println("getIsBoarded() "+ z.getIsBoarded());
      System.out.println("getBoardedAt() "+ z.getBoardedAt());
      System.out.println();
      
      SmartCard someone = new SmartCard(.25);    
      someone.board(uptown);            	//  Insufficient funds to board
      System.out.println();
              
      SmartCard b = new SmartCard(10.00);   
      b.board(center);            		    
      b.exit(downtown);					      // Standard case
      System.out.println("getIsBoarded() "+ b.getIsBoarded());
      System.out.println("getBoardedAt() "+ b.getBoardedAt());
      System.out.println();
    
      SmartCard c = new SmartCard(10.00);   
      c.board(downtown);            		    
      c.exit(uptown);					      // Standard case
      System.out.println();
      
      SmartCard d = new SmartCard(10.00);   
      d.board(suburb);            		    
      d.exit(downtown);					      // Standard case
      System.out.println();
         
      SmartCard e = new SmartCard(10.00);   
      e.board(suburb);            		    
      e.exit(suburb);					      // board and exit at same station
      System.out.println(); 
      
      SmartCard susie = new SmartCard(1.00); 
      susie.board(uptown);            		
      susie.exit(suburb);				   // Insufficient funds to  exit
      System.out.println();
       
      SmartCard jimmy = new SmartCard(20.00); 
      jimmy.board(center);               
      jimmy.exit(suburb);              // Standard case
      jimmy.exit(uptown);			   	// Exit twice in a row
      System.out.println();
   
      SmartCard neha = new SmartCard(20.00); 
      neha.addMoney(5);
      System.out.println(neha.getBalance());
      System.out.println(neha.getFormattedBalance());   
   
      //What other tests can you think of that will break the code?
          
   }
} 	


     /*******************  Sample Run ************

 getBalance() 20.0
 getFormattedBalance() $20.00
 getIsBoarded() false
 getBoardedAt() null
 
 getIsBoarded() true
 getBoardedAt() Downtown, zone 1
 
 getIsBoarded() true
 getBoardedAt() Downtown, zone 1
 Error: already boarded?!
 getIsBoarded() true
 getBoardedAt() Downtown, zone 1
 
 Insufficient funds to board. Please add more money.
 
 From Center City to Downtown costs $0.50. SmartCard has $9.50
 getIsBoarded() false
 getBoardedAt() null
 
 From Downtown to Uptown costs $1.25. SmartCard has $8.75
 
 From Suburb to Downtown costs $2.75. SmartCard has $7.25
 
 From Suburb to Suburb costs $0.50. SmartCard has $9.50
 
 Insufficient funds to exit. Please add more money.
 
 From Center City to Suburb costs $2.75. SmartCard has $17.25
 Error: Did not board?!
 
 25.0
 $25.00 
 
************************************************/