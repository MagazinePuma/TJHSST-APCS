//name: Dennis Tislin   date: 9/12/23

import java.text.DecimalFormat;

public class SmartCard 
{
   // instantiate the constants
   public final static DecimalFormat df = new DecimalFormat("$0.00");
   public final static double MIN_FARE = 0.5;
   // declare the private fields
   private double balance;
   private boolean isBoarded;
   private Station boardedAt;


   
   // write the one-arg constructor
   public SmartCard(double balance){
      boardedAt = null;
      this.balance = balance;
      isBoarded = false;
   }



   // write four getter methods 
   public double getBalance(){
      return this.balance;
   }
   public String getFormattedBalance(){
      return df.format(balance);
   }
   public boolean getIsBoarded(){
      return isBoarded;
   }
   public Station getBoardedAt(){
      return boardedAt;
   }


    
   // write the instance methods as described in the handout
   public void board(Station s){
      if (getIsBoarded()){
         System.out.println("Error: already boarded?!");
         return;
      }
      if (getBalance() < 0.50){
         System.out.println("Insufficient funds to board. Please add more money.");
         return;
      }
      boardedAt = s;
      isBoarded = true;
   }

   public double cost(Station s){
      return Math.abs(s.getZone() - boardedAt.getZone()) * 0.75 + 0.50;
   }

   public void exit(Station s){
      if (!getIsBoarded()){
         System.out.println("Error: Did not board?!");
         return;
      }
      if (cost(s) > getBalance()) {
         System.out.println("Insufficient funds to exit. Please add more money.");
         return;
      }
      balance -= cost(s);
      isBoarded = false;
      System.out.println("From " + boardedAt.getName() + " to " + s.getName() + "costs " + df.format(cost(s)) + ". SmartCard has " + getFormattedBalance());
      boardedAt = null;
   }
   public void addMoney(double d){
      balance += d;
      System.out.println(df.format(d) + " added. " + "Your new balance is " + df.format(balance));
   }

} 
