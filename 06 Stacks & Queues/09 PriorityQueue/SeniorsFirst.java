//ejurj modified shell
// name: Dennis Tislin    date: 1/30

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.PriorityQueue;
import java.util.Queue;
import java.util.Random;
import java.util.Scanner;
import java.util.logging.LogManager;

public class SeniorsFirst
{
   public static final int CUSTOMERS_PER_MINUTE = 2;  
              
   public static void main(String[] args)
   {     
      PrintWriter outfile = setUpFile();      
      
      System.out.println("Seniors First Simulation! ");
      Scanner kb = new Scanner(System.in);
      System.out.print("How many cashiers? ");
      int number_of_cashiers = kb.nextInt();
      System.out.print("How long, in minutes, should the simulation run? ");
      int time = kb.nextInt();
      
      waitTimes(time, number_of_cashiers, outfile);  //run the simulation

      if(kb != null) {
         kb.close();
         kb = null;
      }
      if(outfile != null) {
         outfile.close();	
         outfile = null;
      }

   } 
    
   public static PrintWriter setUpFile()
   {
      PrintWriter outfile = null; 
      try
      {
         outfile = new PrintWriter(new FileWriter("customerWaitTimes.txt"));
      }
      catch(IOException e)
      {
         System.out.println("File not created");
         System.exit(0);
      }
      return outfile;
   }
  
   public static void outfileCashiersAndQueues(PrintWriter outfile, int min, ArrayList<PriorityQueue<Customer>> cashier)
   { 
      outfile.println("minute " + min + ": ");
      for( Queue<Customer> q : cashier )
      {
         outfile.print("          ");
         if(q.isEmpty()) outfile.print("null");
         for( Customer c : q )
            outfile.print( c.toString()+" ");
         outfile.println();
      }
   }
  
   public static double calculateAverage(int totalMinutes, int customers)
   {
      return (int)(1.0 * totalMinutes/customers * 100)/100.0;
   }
   
   public static void waitTimes(int time, int number_of_cashiers, PrintWriter outfile)
   {
      String[] classes = new String[]{"Senior", "Junior", "Sophomor", "Freshman"};
      int[] served = new int[]{0,0,0,0};
      int[] longestWait = new int[]{0,0,0,0};
      int[] totalWait = new int[]{0,0,0,0};
   
      ArrayList<PriorityQueue<Customer>> cashiers = new ArrayList<>();
      for(int i=0; i<number_of_cashiers; i++)
         cashiers.add( new PriorityQueue<Customer>() );
     /***************************************
           Write your code for the simulation.
           call outfileCashiersAndQueues() to write the queues to the file.  
      **********************************/  
      int nextCashier = 0; 
      for(int i = 0; i < time; i++){
         for(int c = 0; c < CUSTOMERS_PER_MINUTE; c++){
            int grade = new Random().nextInt(4); // Random int from 0 to 3
            int serviceTime = new Random().nextInt(7) + 3; // Random int from 3 to 9 minutes

            // send each new customer to next cashier until last cashier reached. then send to first (0th) cashier
            cashiers.get(nextCashier).add(new Customer(i, c, grade, serviceTime));

            nextCashier++;
            if(nextCashier == number_of_cashiers) {
               nextCashier = 0;
            }
         }

         for(int j = 0; j < number_of_cashiers; j++){
            Customer currentCustomer = cashiers.get(j).peek();
            if(currentCustomer != null) {
               currentCustomer.setIsInFront(true);
               currentCustomer.reduceRemainingTime();
               if(currentCustomer.getRemainingTime() == 0) {
                  currentCustomer = cashiers.get(j).poll();
                  outfile.println("customer " + currentCustomer.toString() + " leaves minute " + i + ", His/her total time is " + currentCustomer.getServiceTime() + " minutes");
   
                  served[currentCustomer.getGrade()]++;
                  int currentCustomerTotalWaitTime = i - currentCustomer.getArrivalTime();
                  totalWait[currentCustomer.getGrade()]+=currentCustomerTotalWaitTime;
                  if(currentCustomerTotalWaitTime >= longestWait[currentCustomer.getGrade()]) {
                     longestWait[currentCustomer.getGrade()] = currentCustomerTotalWaitTime;
                  }
               }
            }
         }

         outfileCashiersAndQueues(outfile, i, cashiers);
         
      }


      /*  report the results to the screen in table form, like this:
         Customer		Total		Longest		Average Wait
         Senior			23			10			4.434782608695652
         Junior			18			40			7.666666666666667
         Sophomor			14			28			13.285714285714286
         Freshman			1			2			2.0
         */  
   
      String formatHeader = "%1$-15s %2$-15s %3$-15s %4$-15s";
      String formattedHeader = String.format(formatHeader, "Customer", "Total", "Longest", "Average Wait");
      String formatRow = "%1$-15s %2$-15d %3$-15d %4$-15.8f";

        System.out.println(formattedHeader);
      //   outfile.println(formattedHeader);
        for(int i = 0; i < classes.length; i++) {
         String formattedRow = String.format(formatRow, classes[i], served[i], longestWait[i], served[i]>0?(double)totalWait[i]/served[i]:Double.NaN);
         System.out.println(formattedRow);
         outfile.println(formattedRow);

        }
   }
    
}
   
   
   /*  copy your Customer class and modify it for priority queues  */
   class Customer implements Comparable<Customer> {
      private int arrivalTime;
      private int position;
      private boolean isInFront;
      private int grade;
      private int remainingTime;
      private int serviceTime;

      public Customer(int arrivalMinute, int position, int grade, int serviceTime){
         arrivalTime = arrivalMinute;
         this.position = position;
         this.grade = grade;
         remainingTime = serviceTime; 
         this.serviceTime = serviceTime;
      }

      public void setArrivalTime(int arrivalTime) {
         this.arrivalTime = arrivalTime;
      }

      public boolean isInFront() {
         return isInFront;
      }
      
      public void setIsInFront(boolean isInFront) {
         this.isInFront = isInFront;
      }

      public String getId() {
         return "" + arrivalTime + "-" + position;
      }

      public int getServiceTime() {
         return serviceTime;
      }

      public String getGradeAsString(int grade) {
         // grade = (int)(Math.random() * (3 + 1));
         if(grade == 0){
            return "Se";
         } else if(grade == 1){
            return "Ju";
         } else if(grade == 2){
            return "So";
         } else if(grade == 3){
            return "Fr";
         } else {
            return "";
         }
      }

      public int getGrade() {
         return grade;
      }

      public int getRemainingTime() {
         return remainingTime;
      }

      public void reduceRemainingTime() {
         this.remainingTime = this.remainingTime - 1;
      }

      public int getPosition() {
         return position; 
      }

      public int getArrivalTime() {
         return arrivalTime;
      }

      public int compareTo(Customer obj) {
         if(isInFront) {
            return -1;
         }
         if(grade == obj.getGrade()){ // if same grade, check arrival minute
            if(arrivalTime == obj.getArrivalTime()){ // if same arrival minute, check position
               if(position < obj.getPosition()){
                  return -1;
               }
               return 1;
            } else {
               if(arrivalTime < obj.getArrivalTime()){
                  return -1;
               }
               return 1;
            } 
         } else {
            if(grade < obj.getGrade()) {
               return -1;
            }
         } 
         return 1;
      }

      public String toString() {
         return "" + arrivalTime + "-" + position + "-" + getGradeAsString(grade) + ":" + remainingTime; 
      }

      // public String toString() {
      //    return "" + this.getRemainingTime();
      // }

   }
   
   

/******************************************************
to the screen:
Seniors First Simulation! 
How many cashiers? 6
How long, in minutes, should the simulation run? 100
Customer		Total		Longest		Average Wait
Senior			48			12			4.58
Junior			46			25			6.71
Sophomor			41			38			13.73
Freshman			7			68			29.0


to the file:
minute 0: 
          0-0-Fr:2 
          0-1-So:5 
          null
          null
          null
          null
minute 1: 
          0-0-Fr:1 
          0-1-So:4 
          1-0-Ju:6 
          1-1-Ju:4 
          null
          null
customer 0-0-Fr:0 leaves minute 2, His/her total time is 2 minutes
minute 2: 
          2-0-Fr:2 
          0-1-So:3 
          1-0-Ju:5 
          1-1-Ju:3 
          2-1-Se:6 
          null
minute 3: 
          3-1-Ju:3 2-0-Fr:1 
          0-1-So:2 
          1-0-Ju:4 
          1-1-Ju:2 
          2-1-Se:5 
          3-0-So:4 
minute 4: 
          3-1-Ju:2 2-0-Fr:1 
          4-0-Ju:3 0-1-So:1 
          1-0-Ju:3 4-1-Fr:5 
          1-1-Ju:1 
          2-1-Se:4 
          3-0-So:3 
customer 1-1-Ju:0 leaves minute 5, His/her total time is 4 minutes
minute 5: 
          3-1-Ju:1 2-0-Fr:1 
          4-0-Ju:2 0-1-So:1 
          1-0-Ju:2 4-1-Fr:5 
          5-1-Ju:3 5-0-Fr:4 
          2-1-Se:3 
          3-0-So:2 
customer 3-1-Ju:0 leaves minute 6, His/her total time is 3 minutes
minute 6: 
          6-0-Ju:6 2-0-Fr:1 
          4-0-Ju:1 0-1-So:1 
          1-0-Ju:1 4-1-Fr:5 
          5-1-Ju:2 5-0-Fr:4 
          2-1-Se:2 6-1-Se:6 
          3-0-So:1 
customer 4-0-Ju:0 leaves minute 7, His/her total time is 3 minutes
customer 1-0-Ju:0 leaves minute 7, His/her total time is 6 minutes
customer 3-0-So:0 leaves minute 7, His/her total time is 4 minutes
minute 7: 
          6-0-Ju:5 2-0-Fr:1 
          0-1-So:1 7-1-Fr:2 
          4-1-Fr:5 
          5-1-Ju:1 5-0-Fr:4 
          2-1-Se:1 6-1-Se:6 
          7-0-Se:3 
customer 0-1-So:0 leaves minute 8, His/her total time is 8 minutes
customer 5-1-Ju:0 leaves minute 8, His/her total time is 3 minutes
customer 2-1-Se:0 leaves minute 8, His/her total time is 6 minutes
minute 8: 
          6-0-Ju:4 2-0-Fr:1 
          8-0-Ju:6 7-1-Fr:2 
          8-1-Ju:2 4-1-Fr:4 
          5-0-Fr:4 
          6-1-Se:6 
          7-0-Se:2 
minute 9: 
          6-0-Ju:3 2-0-Fr:1 
          8-0-Ju:5 7-1-Fr:2 
          8-1-Ju:1 4-1-Fr:4 
          5-0-Fr:3 9-0-Fr:4 
          6-1-Se:5 9-1-So:3 
          7-0-Se:1 
customer 8-1-Ju:0 leaves minute 10, His/her total time is 2 minutes
customer 7-0-Se:0 leaves minute 10, His/her total time is 3 minutes
minute 10: 
          6-0-Ju:2 2-0-Fr:1 
          8-0-Ju:4 7-1-Fr:2 
          10-1-So:5 4-1-Fr:4 
          5-0-Fr:2 9-0-Fr:4 
          6-1-Se:4 9-1-So:3 
          10-0-Ju:6 
minute 11: 
          6-0-Ju:1 2-0-Fr:1 11-1-Fr:2 
          8-0-Ju:3 7-1-Fr:2 
          10-1-So:4 4-1-Fr:4 
          5-0-Fr:1 9-0-Fr:4 
          6-1-Se:3 9-1-So:3 
          11-0-Se:5 10-0-Ju:5 
customer 6-0-Ju:0 leaves minute 12, His/her total time is 6 minutes
customer 5-0-Fr:0 leaves minute 12, His/her total time is 7 minutes
minute 12: 
          12-1-So:6 11-1-Fr:2 2-0-Fr:1 
          8-0-Ju:2 7-1-Fr:2 
          10-1-So:3 4-1-Fr:4 
          12-0-So:5 9-0-Fr:4 
          6-1-Se:2 9-1-So:3 
          11-0-Se:4 10-0-Ju:5 
minute 13: 
          12-1-So:5 11-1-Fr:2 2-0-Fr:1 
          8-0-Ju:1 7-1-Fr:2 13-0-Ju:5 
          13-1-Ju:4 4-1-Fr:4 10-1-So:2 
          12-0-So:4 9-0-Fr:4 
          6-1-Se:1 9-1-So:3 
          11-0-Se:3 10-0-Ju:5 
customer 8-0-Ju:0 leaves minute 14, His/her total time is 6 minutes
customer 6-1-Se:0 leaves minute 14, His/her total time is 8 minutes
minute 14: 
          12-1-So:4 11-1-Fr:2 2-0-Fr:1 
          14-1-Se:6 7-1-Fr:2 13-0-Ju:5 
          13-1-Ju:3 4-1-Fr:4 10-1-So:2 
          12-0-So:3 9-0-Fr:4 
          14-0-Ju:6 9-1-So:3 
          11-0-Se:2 10-0-Ju:5 
minute 15: 
          12-1-So:3 11-1-Fr:2 2-0-Fr:1 
          14-1-Se:5 7-1-Fr:2 13-0-Ju:5 
          13-1-Ju:2 4-1-Fr:4 10-1-So:2 
          15-0-Se:2 9-0-Fr:4 12-0-So:2 
          14-0-Ju:5 9-1-So:3 15-1-Ju:2 
          11-0-Se:1 10-0-Ju:5 
customer 11-0-Se:0 leaves minute 16, His/her total time is 5 minutes
minute 16: 
          12-1-So:2 11-1-Fr:2 2-0-Fr:1 
          14-1-Se:4 7-1-Fr:2 13-0-Ju:5 
          13-1-Ju:1 4-1-Fr:4 10-1-So:2 
          15-0-Se:1 9-0-Fr:4 12-0-So:2 
          14-0-Ju:4 9-1-So:3 15-1-Ju:2 
          10-0-Ju:5 16-0-Ju:6 16-1-So:5 
customer 13-1-Ju:0 leaves minute 17, His/her total time is 4 minutes
customer 15-0-Se:0 leaves minute 17, His/her total time is 2 minutes
minute 17: 
          12-1-So:1 11-1-Fr:2 2-0-Fr:1 
          14-1-Se:3 7-1-Fr:2 13-0-Ju:5 
          17-0-Se:2 4-1-Fr:4 10-1-So:2 
          17-1-Se:4 9-0-Fr:4 12-0-So:2 
          14-0-Ju:3 9-1-So:3 15-1-Ju:2 
          10-0-Ju:4 16-0-Ju:6 16-1-So:5 
customer 12-1-So:0 leaves minute 18, His/her total time is 6 minutes
minute 18: 
          18-1-Se:3 18-0-Ju:6 2-0-Fr:1 11-1-Fr:2 
          14-1-Se:2 7-1-Fr:2 13-0-Ju:5 
          17-0-Se:1 4-1-Fr:4 10-1-So:2 
          17-1-Se:3 9-0-Fr:4 12-0-So:2 
          14-0-Ju:2 9-1-So:3 15-1-Ju:2 
          10-0-Ju:3 16-0-Ju:6 16-1-So:5 
customer 17-0-Se:0 leaves minute 19, His/her total time is 2 minutes
minute 19: 
          18-1-Se:2 18-0-Ju:6 2-0-Fr:1 11-1-Fr:2 
          14-1-Se:1 19-1-Se:3 13-0-Ju:5 7-1-Fr:2 
          10-1-So:2 4-1-Fr:4 19-0-Fr:4 
          17-1-Se:2 9-0-Fr:4 12-0-So:2 
          14-0-Ju:1 9-1-So:3 15-1-Ju:2 
          10-0-Ju:2 16-0-Ju:6 16-1-So:5 
customer 14-1-Se:0 leaves minute 20, His/her total time is 6 minutes
customer 14-0-Ju:0 leaves minute 20, His/her total time is 6 minutes
minute 20: 
          18-1-Se:1 18-0-Ju:6 2-0-Fr:1 11-1-Fr:2 
          19-1-Se:3 20-1-Ju:2 13-0-Ju:5 7-1-Fr:2 
          10-1-So:1 4-1-Fr:4 19-0-Fr:4 
          17-1-Se:1 9-0-Fr:4 12-0-So:2 
          15-1-Ju:2 9-1-So:3 20-0-So:4 
          10-0-Ju:1 16-0-Ju:6 16-1-So:5 
customer 18-1-Se:0 leaves minute 21, His/her total time is 3 minutes
customer 10-1-So:0 leaves minute 21, His/her total time is 11 minutes
customer 17-1-Se:0 leaves minute 21, His/her total time is 4 minutes
customer 10-0-Ju:0 leaves minute 21, His/her total time is 11 minutes
minute 21: 
          18-0-Ju:6 11-1-Fr:2 2-0-Fr:1 
          19-1-Se:2 20-1-Ju:2 13-0-Ju:5 7-1-Fr:2 
          21-0-So:5 19-0-Fr:4 4-1-Fr:4 
          12-0-So:2 9-0-Fr:4 21-1-Fr:3 
          15-1-Ju:1 9-1-So:3 20-0-So:4 
          16-0-Ju:6 16-1-So:5 
customer 15-1-Ju:0 leaves minute 22, His/her total time is 7 minutes
minute 22: 
          18-0-Ju:5 11-1-Fr:2 2-0-Fr:1 
          19-1-Se:1 20-1-Ju:2 13-0-Ju:5 7-1-Fr:2 
          21-0-So:4 19-0-Fr:4 4-1-Fr:4 
          12-0-So:1 9-0-Fr:4 21-1-Fr:3 
          9-1-So:3 20-0-So:4 22-0-So:2 
          16-0-Ju:5 16-1-So:5 22-1-Fr:3 
customer 19-1-Se:0 leaves minute 23, His/her total time is 4 minutes
customer 12-0-So:0 leaves minute 23, His/her total time is 11 minutes
minute 23: 
          18-0-Ju:4 11-1-Fr:2 2-0-Fr:1 23-1-Fr:2 
          13-0-Ju:5 20-1-Ju:2 7-1-Fr:2 
          21-0-So:3 19-0-Fr:4 4-1-Fr:4 
          9-0-Fr:4 21-1-Fr:3 23-0-Fr:3 
          9-1-So:2 20-0-So:4 22-0-So:2 
          16-0-Ju:4 16-1-So:5 22-1-Fr:3 
minute 24: 
          18-0-Ju:3 11-1-Fr:2 2-0-Fr:1 23-1-Fr:2 
          24-0-Se:5 13-0-Ju:4 7-1-Fr:2 20-1-Ju:2 
          24-1-Se:2 21-0-So:2 4-1-Fr:4 19-0-Fr:4 
          9-0-Fr:3 21-1-Fr:3 23-0-Fr:3 
          9-1-So:1 20-0-So:4 22-0-So:2 
          16-0-Ju:3 16-1-So:5 22-1-Fr:3 
customer 9-1-So:0 leaves minute 25, His/her total time is 16 minutes
minute 25: 
          18-0-Ju:2 11-1-Fr:2 2-0-Fr:1 23-1-Fr:2 
          24-0-Se:4 13-0-Ju:4 7-1-Fr:2 20-1-Ju:2 
          24-1-Se:1 21-0-So:2 4-1-Fr:4 19-0-Fr:4 
          25-1-Ju:5 9-0-Fr:2 23-0-Fr:3 21-1-Fr:3 
          25-0-Se:3 22-0-So:2 20-0-So:4 
          16-0-Ju:2 16-1-So:5 22-1-Fr:3 
customer 24-1-Se:0 leaves minute 26, His/her total time is 2 minutes
minute 26: 
          18-0-Ju:1 11-1-Fr:2 2-0-Fr:1 23-1-Fr:2 
          24-0-Se:3 13-0-Ju:4 7-1-Fr:2 20-1-Ju:2 
          21-0-So:2 26-0-So:4 4-1-Fr:4 19-0-Fr:4 
          25-1-Ju:4 9-0-Fr:2 23-0-Fr:3 21-1-Fr:3 
          25-0-Se:2 26-1-Ju:5 20-0-So:4 22-0-So:2 
          16-0-Ju:1 16-1-So:5 22-1-Fr:3 
customer 18-0-Ju:0 leaves minute 27, His/her total time is 9 minutes
customer 16-0-Ju:0 leaves minute 27, His/her total time is 11 minutes
minute 27: 
          27-1-Ju:4 2-0-Fr:1 23-1-Fr:2 11-1-Fr:2 
          24-0-Se:2 13-0-Ju:4 7-1-Fr:2 20-1-Ju:2 
          21-0-So:1 26-0-So:4 4-1-Fr:4 19-0-Fr:4 
          25-1-Ju:3 9-0-Fr:2 23-0-Fr:3 21-1-Fr:3 
          25-0-Se:1 26-1-Ju:5 20-0-So:4 22-0-So:2 
          16-1-So:5 22-1-Fr:3 27-0-So:5 
customer 21-0-So:0 leaves minute 28, His/her total time is 7 minutes
customer 25-0-Se:0 leaves minute 28, His/her total time is 3 minutes
minute 28: 
          27-1-Ju:3 2-0-Fr:1 23-1-Fr:2 11-1-Fr:2 
          24-0-Se:1 13-0-Ju:4 7-1-Fr:2 20-1-Ju:2 
          26-0-So:4 19-0-Fr:4 4-1-Fr:4 28-0-Fr:3 
          25-1-Ju:2 9-0-Fr:2 23-0-Fr:3 21-1-Fr:3 
          26-1-Ju:5 28-1-Ju:4 20-0-So:4 22-0-So:2 
          16-1-So:4 22-1-Fr:3 27-0-So:5 
customer 24-0-Se:0 leaves minute 29, His/her total time is 5 minutes
minute 29: 
          27-1-Ju:2 2-0-Fr:1 23-1-Fr:2 11-1-Fr:2 
          29-0-Se:5 13-0-Ju:4 7-1-Fr:2 20-1-Ju:2 
          26-0-So:3 19-0-Fr:4 4-1-Fr:4 28-0-Fr:3 
          25-1-Ju:1 9-0-Fr:2 23-0-Fr:3 21-1-Fr:3 
          26-1-Ju:4 28-1-Ju:4 20-0-So:4 22-0-So:2 
          29-1-Ju:5 16-1-So:3 27-0-So:5 22-1-Fr:3 
customer 25-1-Ju:0 leaves minute 30, His/her total time is 5 minutes
minute 30: 
          27-1-Ju:1 30-1-Ju:3 23-1-Fr:2 11-1-Fr:2 2-0-Fr:1 
          29-0-Se:4 13-0-Ju:4 7-1-Fr:2 20-1-Ju:2 
          26-0-So:2 19-0-Fr:4 4-1-Fr:4 28-0-Fr:3 
          9-0-Fr:2 21-1-Fr:3 23-0-Fr:3 30-0-Fr:6 
          26-1-Ju:3 28-1-Ju:4 20-0-So:4 22-0-So:2 
          29-1-Ju:4 16-1-So:3 27-0-So:5 22-1-Fr:3 
customer 27-1-Ju:0 leaves minute 31, His/her total time is 4 minutes
minute 31: 
          2-0-Fr:1 30-1-Ju:3 23-1-Fr:2 11-1-Fr:2 31-0-Ju:4 
          29-0-Se:3 13-0-Ju:4 7-1-Fr:2 20-1-Ju:2 31-1-Fr:3 
          26-0-So:1 19-0-Fr:4 4-1-Fr:4 28-0-Fr:3 
          9-0-Fr:1 21-1-Fr:3 23-0-Fr:3 30-0-Fr:6 
          26-1-Ju:2 28-1-Ju:4 20-0-So:4 22-0-So:2 
          29-1-Ju:3 16-1-So:3 27-0-So:5 22-1-Fr:3 
customer 2-0-Fr:0 leaves minute 32, His/her total time is 30 minutes
customer 26-0-So:0 leaves minute 32, His/her total time is 6 minutes
customer 9-0-Fr:0 leaves minute 32, His/her total time is 23 minutes
minute 32: 
          30-1-Ju:3 31-0-Ju:4 23-1-Fr:2 11-1-Fr:2 
          29-0-Se:2 13-0-Ju:4 7-1-Fr:2 20-1-Ju:2 31-1-Fr:3 
          32-0-Se:5 4-1-Fr:4 28-0-Fr:3 19-0-Fr:4 
          32-1-So:2 21-1-Fr:3 23-0-Fr:3 30-0-Fr:6 
          26-1-Ju:1 28-1-Ju:4 20-0-So:4 22-0-So:2 
          29-1-Ju:2 16-1-So:3 27-0-So:5 22-1-Fr:3 
customer 26-1-Ju:0 leaves minute 33, His/her total time is 7 minutes
minute 33: 
          30-1-Ju:2 31-0-Ju:4 23-1-Fr:2 11-1-Fr:2 33-1-Ju:2 
          29-0-Se:1 13-0-Ju:4 7-1-Fr:2 20-1-Ju:2 31-1-Fr:3 
          32-0-Se:4 4-1-Fr:4 28-0-Fr:3 19-0-Fr:4 
          32-1-So:1 21-1-Fr:3 23-0-Fr:3 30-0-Fr:6 
          28-1-Ju:4 22-0-So:2 20-0-So:4 33-0-So:6 
          29-1-Ju:1 16-1-So:3 27-0-So:5 22-1-Fr:3 
customer 29-0-Se:0 leaves minute 34, His/her total time is 5 minutes
customer 32-1-So:0 leaves minute 34, His/her total time is 2 minutes
customer 29-1-Ju:0 leaves minute 34, His/her total time is 5 minutes
minute 34: 
          30-1-Ju:1 31-0-Ju:4 23-1-Fr:2 11-1-Fr:2 33-1-Ju:2 
          13-0-Ju:4 20-1-Ju:2 7-1-Fr:2 31-1-Fr:3 
          32-0-Se:3 4-1-Fr:4 28-0-Fr:3 19-0-Fr:4 
          34-0-Ju:4 21-1-Fr:3 23-0-Fr:3 30-0-Fr:6 
          28-1-Ju:3 22-0-So:2 20-0-So:4 33-0-So:6 
          16-1-So:3 22-1-Fr:3 27-0-So:5 34-1-Fr:6 
customer 30-1-Ju:0 leaves minute 35, His/her total time is 5 minutes
minute 35: 
          31-0-Ju:4 33-1-Ju:2 23-1-Fr:2 11-1-Fr:2 35-0-Fr:4 
          13-0-Ju:3 20-1-Ju:2 7-1-Fr:2 31-1-Fr:3 35-1-Fr:3 
          32-0-Se:2 4-1-Fr:4 28-0-Fr:3 19-0-Fr:4 
          34-0-Ju:3 21-1-Fr:3 23-0-Fr:3 30-0-Fr:6 
          28-1-Ju:2 22-0-So:2 20-0-So:4 33-0-So:6 
          16-1-So:2 22-1-Fr:3 27-0-So:5 34-1-Fr:6 
minute 36: 
          31-0-Ju:3 33-1-Ju:2 23-1-Fr:2 11-1-Fr:2 35-0-Fr:4 
          13-0-Ju:2 20-1-Ju:2 7-1-Fr:2 31-1-Fr:3 35-1-Fr:3 
          32-0-Se:1 36-0-So:4 28-0-Fr:3 19-0-Fr:4 4-1-Fr:4 
          34-0-Ju:2 36-1-Ju:4 23-0-Fr:3 30-0-Fr:6 21-1-Fr:3 
          28-1-Ju:1 22-0-So:2 20-0-So:4 33-0-So:6 
          16-1-So:1 22-1-Fr:3 27-0-So:5 34-1-Fr:6 
customer 32-0-Se:0 leaves minute 37, His/her total time is 5 minutes
customer 28-1-Ju:0 leaves minute 37, His/her total time is 9 minutes
customer 16-1-So:0 leaves minute 37, His/her total time is 21 minutes
minute 37: 
          31-0-Ju:2 33-1-Ju:2 23-1-Fr:2 11-1-Fr:2 35-0-Fr:4 
          13-0-Ju:1 20-1-Ju:2 7-1-Fr:2 31-1-Fr:3 35-1-Fr:3 
          4-1-Fr:4 36-0-So:4 28-0-Fr:3 19-0-Fr:4 
          34-0-Ju:1 36-1-Ju:4 23-0-Fr:3 30-0-Fr:6 21-1-Fr:3 
          20-0-So:4 22-0-So:2 33-0-So:6 37-0-So:6 
          37-1-Ju:2 27-0-So:5 34-1-Fr:6 22-1-Fr:3 
customer 13-0-Ju:0 leaves minute 38, His/her total time is 25 minutes
customer 34-0-Ju:0 leaves minute 38, His/her total time is 4 minutes
minute 38: 
          31-0-Ju:1 33-1-Ju:2 23-1-Fr:2 11-1-Fr:2 35-0-Fr:4 
          20-1-Ju:2 38-0-So:2 7-1-Fr:2 35-1-Fr:3 31-1-Fr:3 
          4-1-Fr:3 36-0-So:4 28-0-Fr:3 19-0-Fr:4 38-1-So:3 
          36-1-Ju:4 21-1-Fr:3 23-0-Fr:3 30-0-Fr:6 
          20-0-So:3 22-0-So:2 33-0-So:6 37-0-So:6 
          37-1-Ju:1 27-0-So:5 34-1-Fr:6 22-1-Fr:3 
customer 31-0-Ju:0 leaves minute 39, His/her total time is 8 minutes
customer 37-1-Ju:0 leaves minute 39, His/her total time is 2 minutes
minute 39: 
          33-1-Ju:2 39-1-So:5 23-1-Fr:2 35-0-Fr:4 11-1-Fr:2 
          20-1-Ju:1 38-0-So:2 7-1-Fr:2 35-1-Fr:3 31-1-Fr:3 
          4-1-Fr:2 36-0-So:4 28-0-Fr:3 19-0-Fr:4 38-1-So:3 
          36-1-Ju:3 21-1-Fr:3 23-0-Fr:3 30-0-Fr:6 
          20-0-So:2 22-0-So:2 33-0-So:6 37-0-So:6 
          27-0-So:5 22-1-Fr:3 34-1-Fr:6 39-0-Fr:2 
customer 20-1-Ju:0 leaves minute 40, His/her total time is 20 minutes
minute 40: 
          33-1-Ju:1 39-1-So:5 23-1-Fr:2 35-0-Fr:4 11-1-Fr:2 
          40-0-Se:5 38-0-So:2 7-1-Fr:2 35-1-Fr:3 31-1-Fr:3 
          4-1-Fr:1 36-0-So:4 28-0-Fr:3 19-0-Fr:4 38-1-So:3 
          36-1-Ju:2 21-1-Fr:3 23-0-Fr:3 30-0-Fr:6 40-1-Fr:6 
          20-0-So:1 22-0-So:2 33-0-So:6 37-0-So:6 
          27-0-So:4 22-1-Fr:3 34-1-Fr:6 39-0-Fr:2 
customer 33-1-Ju:0 leaves minute 41, His/her total time is 8 minutes
customer 4-1-Fr:0 leaves minute 41, His/her total time is 37 minutes
customer 20-0-So:0 leaves minute 41, His/her total time is 21 minutes
minute 41: 
          39-1-So:5 41-1-So:3 23-1-Fr:2 35-0-Fr:4 11-1-Fr:2 
          40-0-Se:4 38-0-So:2 7-1-Fr:2 35-1-Fr:3 31-1-Fr:3 
          36-0-So:4 38-1-So:3 28-0-Fr:3 19-0-Fr:4 
          36-1-Ju:1 21-1-Fr:3 23-0-Fr:3 30-0-Fr:6 40-1-Fr:6 
          22-0-So:2 37-0-So:6 33-0-So:6 41-0-So:5 
          27-0-So:3 22-1-Fr:3 34-1-Fr:6 39-0-Fr:2 
customer 36-1-Ju:0 leaves minute 42, His/her total time is 6 minutes
minute 42: 
          39-1-So:4 41-1-So:3 23-1-Fr:2 35-0-Fr:4 11-1-Fr:2 
          40-0-Se:3 38-0-So:2 7-1-Fr:2 35-1-Fr:3 31-1-Fr:3 
          36-0-So:3 38-1-So:3 28-0-Fr:3 19-0-Fr:4 42-0-So:2 
          42-1-Ju:5 21-1-Fr:3 23-0-Fr:3 40-1-Fr:6 30-0-Fr:6 
          22-0-So:1 37-0-So:6 33-0-So:6 41-0-So:5 
          27-0-So:2 22-1-Fr:3 34-1-Fr:6 39-0-Fr:2 
customer 22-0-So:0 leaves minute 43, His/her total time is 21 minutes
minute 43: 
          39-1-So:3 41-1-So:3 23-1-Fr:2 35-0-Fr:4 11-1-Fr:2 
          40-0-Se:2 38-0-So:2 7-1-Fr:2 35-1-Fr:3 31-1-Fr:3 
          36-0-So:2 38-1-So:3 28-0-Fr:3 19-0-Fr:4 42-0-So:2 
          42-1-Ju:4 21-1-Fr:3 23-0-Fr:3 40-1-Fr:6 30-0-Fr:6 
          33-0-So:6 37-0-So:6 41-0-So:5 43-0-Fr:2 43-1-Fr:2 
          27-0-So:1 22-1-Fr:3 34-1-Fr:6 39-0-Fr:2 
customer 27-0-So:0 leaves minute 44, His/her total time is 17 minutes
minute 44: 
          39-1-So:2 41-1-So:3 23-1-Fr:2 35-0-Fr:4 11-1-Fr:2 
          40-0-Se:1 38-0-So:2 7-1-Fr:2 35-1-Fr:3 31-1-Fr:3 
          36-0-So:1 38-1-So:3 28-0-Fr:3 19-0-Fr:4 42-0-So:2 
          42-1-Ju:3 21-1-Fr:3 23-0-Fr:3 40-1-Fr:6 30-0-Fr:6 
          33-0-So:5 37-0-So:6 41-0-So:5 43-0-Fr:2 43-1-Fr:2 
          44-0-So:5 22-1-Fr:3 34-1-Fr:6 39-0-Fr:2 44-1-Fr:4 
customer 40-0-Se:0 leaves minute 45, His/her total time is 5 minutes
customer 36-0-So:0 leaves minute 45, His/her total time is 9 minutes
minute 45: 
          39-1-So:1 41-1-So:3 23-1-Fr:2 35-0-Fr:4 11-1-Fr:2 
          38-0-So:2 45-0-So:4 7-1-Fr:2 35-1-Fr:3 31-1-Fr:3 
          45-1-Se:2 38-1-So:3 28-0-Fr:3 19-0-Fr:4 42-0-So:2 
          42-1-Ju:2 21-1-Fr:3 23-0-Fr:3 40-1-Fr:6 30-0-Fr:6 
          33-0-So:4 37-0-So:6 41-0-So:5 43-0-Fr:2 43-1-Fr:2 
          44-0-So:4 22-1-Fr:3 34-1-Fr:6 39-0-Fr:2 44-1-Fr:4 
customer 39-1-So:0 leaves minute 46, His/her total time is 7 minutes
minute 46: 
          46-0-Se:4 41-1-So:3 23-1-Fr:2 35-0-Fr:4 11-1-Fr:2 46-1-Fr:3 
          38-0-So:1 45-0-So:4 7-1-Fr:2 35-1-Fr:3 31-1-Fr:3 
          45-1-Se:1 38-1-So:3 28-0-Fr:3 19-0-Fr:4 42-0-So:2 
          42-1-Ju:1 21-1-Fr:3 23-0-Fr:3 40-1-Fr:6 30-0-Fr:6 
          33-0-So:3 37-0-So:6 41-0-So:5 43-0-Fr:2 43-1-Fr:2 
          44-0-So:3 22-1-Fr:3 34-1-Fr:6 39-0-Fr:2 44-1-Fr:4 
customer 38-0-So:0 leaves minute 47, His/her total time is 9 minutes
customer 45-1-Se:0 leaves minute 47, His/her total time is 2 minutes
customer 42-1-Ju:0 leaves minute 47, His/her total time is 5 minutes
minute 47: 
          46-0-Se:3 41-1-So:3 23-1-Fr:2 35-0-Fr:4 11-1-Fr:2 46-1-Fr:3 
          45-0-So:4 47-0-So:6 7-1-Fr:2 35-1-Fr:3 31-1-Fr:3 
          47-1-Se:4 38-1-So:3 28-0-Fr:3 19-0-Fr:4 42-0-So:2 
          21-1-Fr:3 30-0-Fr:6 23-0-Fr:3 40-1-Fr:6 
          33-0-So:2 37-0-So:6 41-0-So:5 43-0-Fr:2 43-1-Fr:2 
          44-0-So:2 22-1-Fr:3 34-1-Fr:6 39-0-Fr:2 44-1-Fr:4 
minute 48: 
          46-0-Se:2 41-1-So:3 23-1-Fr:2 35-0-Fr:4 11-1-Fr:2 46-1-Fr:3 
          45-0-So:3 47-0-So:6 48-1-So:5 35-1-Fr:3 31-1-Fr:3 7-1-Fr:2 
          47-1-Se:3 38-1-So:3 28-0-Fr:3 19-0-Fr:4 42-0-So:2 
          48-0-Ju:4 21-1-Fr:2 23-0-Fr:3 40-1-Fr:6 30-0-Fr:6 
          33-0-So:1 37-0-So:6 41-0-So:5 43-0-Fr:2 43-1-Fr:2 
          44-0-So:1 22-1-Fr:3 34-1-Fr:6 39-0-Fr:2 44-1-Fr:4 
customer 33-0-So:0 leaves minute 49, His/her total time is 16 minutes
customer 44-0-So:0 leaves minute 49, His/her total time is 5 minutes
minute 49: 
          46-0-Se:1 41-1-So:3 23-1-Fr:2 35-0-Fr:4 11-1-Fr:2 46-1-Fr:3 
          45-0-So:2 47-0-So:6 48-1-So:5 35-1-Fr:3 31-1-Fr:3 7-1-Fr:2 
          47-1-Se:2 38-1-So:3 28-0-Fr:3 19-0-Fr:4 42-0-So:2 
          48-0-Ju:3 21-1-Fr:2 23-0-Fr:3 40-1-Fr:6 30-0-Fr:6 
          37-0-So:6 43-1-Fr:2 41-0-So:5 43-0-Fr:2 49-0-Fr:6 
          49-1-So:4 22-1-Fr:3 34-1-Fr:6 44-1-Fr:4 39-0-Fr:2 
customer 46-0-Se:0 leaves minute 50, His/her total time is 4 minutes
minute 50: 
          50-0-Se:5 11-1-Fr:2 41-1-So:3 35-0-Fr:4 46-1-Fr:3 23-1-Fr:2 
          45-0-So:1 47-0-So:6 48-1-So:5 35-1-Fr:3 31-1-Fr:3 7-1-Fr:2 
          47-1-Se:1 38-1-So:3 50-1-So:6 19-0-Fr:4 42-0-So:2 28-0-Fr:3 
          48-0-Ju:2 21-1-Fr:2 23-0-Fr:3 40-1-Fr:6 30-0-Fr:6 
          37-0-So:5 43-1-Fr:2 41-0-So:5 43-0-Fr:2 49-0-Fr:6 
          49-1-So:3 22-1-Fr:3 34-1-Fr:6 44-1-Fr:4 39-0-Fr:2 
customer 45-0-So:0 leaves minute 51, His/her total time is 6 minutes
customer 47-1-Se:0 leaves minute 51, His/her total time is 4 minutes
minute 51: 
          50-0-Se:4 11-1-Fr:2 41-1-So:3 35-0-Fr:4 46-1-Fr:3 23-1-Fr:2 
          51-0-Ju:3 7-1-Fr:2 47-0-So:6 35-1-Fr:3 31-1-Fr:3 48-1-So:5 
          38-1-So:3 42-0-So:2 50-1-So:6 19-0-Fr:4 28-0-Fr:3 51-1-Fr:5 
          48-0-Ju:1 21-1-Fr:2 23-0-Fr:3 40-1-Fr:6 30-0-Fr:6 
          37-0-So:4 43-1-Fr:2 41-0-So:5 43-0-Fr:2 49-0-Fr:6 
          49-1-So:2 22-1-Fr:3 34-1-Fr:6 44-1-Fr:4 39-0-Fr:2 
customer 48-0-Ju:0 leaves minute 52, His/her total time is 4 minutes
minute 52: 
          50-0-Se:3 11-1-Fr:2 41-1-So:3 35-0-Fr:4 46-1-Fr:3 23-1-Fr:2 
          51-0-Ju:2 7-1-Fr:2 47-0-So:6 35-1-Fr:3 31-1-Fr:3 48-1-So:5 
          38-1-So:2 42-0-So:2 50-1-So:6 19-0-Fr:4 28-0-Fr:3 51-1-Fr:5 
          52-0-Se:3 21-1-Fr:2 52-1-Se:6 40-1-Fr:6 30-0-Fr:6 23-0-Fr:3 
          37-0-So:3 43-1-Fr:2 41-0-So:5 43-0-Fr:2 49-0-Fr:6 
          49-1-So:1 22-1-Fr:3 34-1-Fr:6 44-1-Fr:4 39-0-Fr:2 
customer 49-1-So:0 leaves minute 53, His/her total time is 4 minutes
minute 53: 
          50-0-Se:2 11-1-Fr:2 41-1-So:3 35-0-Fr:4 46-1-Fr:3 23-1-Fr:2 
          51-0-Ju:1 7-1-Fr:2 47-0-So:6 35-1-Fr:3 31-1-Fr:3 48-1-So:5 
          38-1-So:1 42-0-So:2 50-1-So:6 19-0-Fr:4 28-0-Fr:3 51-1-Fr:5 
          52-0-Se:2 21-1-Fr:2 52-1-Se:6 40-1-Fr:6 30-0-Fr:6 23-0-Fr:3 
          53-1-Se:4 43-1-Fr:2 37-0-So:2 43-0-Fr:2 49-0-Fr:6 41-0-So:5 
          53-0-Ju:2 22-1-Fr:3 34-1-Fr:6 44-1-Fr:4 39-0-Fr:2 
customer 51-0-Ju:0 leaves minute 54, His/her total time is 3 minutes
customer 38-1-So:0 leaves minute 54, His/her total time is 16 minutes
minute 54: 
          50-0-Se:1 11-1-Fr:2 41-1-So:3 35-0-Fr:4 46-1-Fr:3 23-1-Fr:2 
          54-0-Se:6 7-1-Fr:2 47-0-So:6 35-1-Fr:3 31-1-Fr:3 48-1-So:5 
          54-1-Se:5 19-0-Fr:4 42-0-So:2 51-1-Fr:5 28-0-Fr:3 50-1-So:6 
          52-0-Se:1 21-1-Fr:2 52-1-Se:6 40-1-Fr:6 30-0-Fr:6 23-0-Fr:3 
          53-1-Se:3 43-1-Fr:2 37-0-So:2 43-0-Fr:2 49-0-Fr:6 41-0-So:5 
          53-0-Ju:1 22-1-Fr:3 34-1-Fr:6 44-1-Fr:4 39-0-Fr:2 
customer 50-0-Se:0 leaves minute 55, His/her total time is 5 minutes
customer 52-0-Se:0 leaves minute 55, His/her total time is 3 minutes
customer 53-0-Ju:0 leaves minute 55, His/her total time is 2 minutes
minute 55: 
          41-1-So:3 11-1-Fr:2 55-1-So:2 35-0-Fr:4 46-1-Fr:3 23-1-Fr:2 
          54-0-Se:5 7-1-Fr:2 47-0-So:6 35-1-Fr:3 31-1-Fr:3 48-1-So:5 
          54-1-Se:4 19-0-Fr:4 42-0-So:2 51-1-Fr:5 28-0-Fr:3 50-1-So:6 
          21-1-Fr:2 23-0-Fr:3 52-1-Se:6 40-1-Fr:6 30-0-Fr:6 
          53-1-Se:2 43-1-Fr:2 37-0-So:2 43-0-Fr:2 49-0-Fr:6 41-0-So:5 
          55-0-Se:5 22-1-Fr:3 34-1-Fr:6 44-1-Fr:4 39-0-Fr:2 
minute 56: 
          41-1-So:2 11-1-Fr:2 55-1-So:2 35-0-Fr:4 46-1-Fr:3 23-1-Fr:2 
          54-0-Se:4 7-1-Fr:2 47-0-So:6 35-1-Fr:3 31-1-Fr:3 48-1-So:5 
          54-1-Se:3 19-0-Fr:4 42-0-So:2 51-1-Fr:5 28-0-Fr:3 50-1-So:6 
          21-1-Fr:1 23-0-Fr:3 52-1-Se:6 40-1-Fr:6 30-0-Fr:6 56-0-Se:3 
          53-1-Se:1 43-1-Fr:2 37-0-So:2 43-0-Fr:2 49-0-Fr:6 41-0-So:5 
          55-0-Se:4 22-1-Fr:3 56-1-Se:3 44-1-Fr:4 39-0-Fr:2 34-1-Fr:6 
customer 21-1-Fr:0 leaves minute 57, His/her total time is 36 minutes
customer 53-1-Se:0 leaves minute 57, His/her total time is 4 minutes
minute 57: 
          41-1-So:1 11-1-Fr:2 55-1-So:2 35-0-Fr:4 46-1-Fr:3 23-1-Fr:2 
          54-0-Se:3 7-1-Fr:2 47-0-So:6 35-1-Fr:3 31-1-Fr:3 48-1-So:5 
          54-1-Se:2 19-0-Fr:4 42-0-So:2 51-1-Fr:5 28-0-Fr:3 50-1-So:6 
          52-1-Se:6 23-0-Fr:3 56-0-Se:3 40-1-Fr:6 30-0-Fr:6 57-0-Se:3 
          37-0-So:2 43-1-Fr:2 41-0-So:5 43-0-Fr:2 49-0-Fr:6 57-1-So:3 
          55-0-Se:3 22-1-Fr:3 56-1-Se:3 44-1-Fr:4 39-0-Fr:2 34-1-Fr:6 
customer 41-1-So:0 leaves minute 58, His/her total time is 17 minutes
minute 58: 
          58-1-Se:2 11-1-Fr:2 58-0-Ju:5 35-0-Fr:4 46-1-Fr:3 23-1-Fr:2 55-1-So:2 
          54-0-Se:2 7-1-Fr:2 47-0-So:6 35-1-Fr:3 31-1-Fr:3 48-1-So:5 
          54-1-Se:1 19-0-Fr:4 42-0-So:2 51-1-Fr:5 28-0-Fr:3 50-1-So:6 
          52-1-Se:5 23-0-Fr:3 56-0-Se:3 40-1-Fr:6 30-0-Fr:6 57-0-Se:3 
          37-0-So:1 43-1-Fr:2 41-0-So:5 43-0-Fr:2 49-0-Fr:6 57-1-So:3 
          55-0-Se:2 22-1-Fr:3 56-1-Se:3 44-1-Fr:4 39-0-Fr:2 34-1-Fr:6 
customer 54-1-Se:0 leaves minute 59, His/her total time is 5 minutes
customer 37-0-So:0 leaves minute 59, His/her total time is 22 minutes
minute 59: 
          58-1-Se:1 11-1-Fr:2 58-0-Ju:5 35-0-Fr:4 46-1-Fr:3 23-1-Fr:2 55-1-So:2 
          54-0-Se:1 7-1-Fr:2 47-0-So:6 35-1-Fr:3 31-1-Fr:3 48-1-So:5 
          59-0-Ju:3 19-0-Fr:4 42-0-So:2 51-1-Fr:5 28-0-Fr:3 50-1-So:6 
          52-1-Se:4 23-0-Fr:3 56-0-Se:3 40-1-Fr:6 30-0-Fr:6 57-0-Se:3 
          41-0-So:5 43-1-Fr:2 57-1-So:3 43-0-Fr:2 49-0-Fr:6 59-1-So:6 
          55-0-Se:1 22-1-Fr:3 56-1-Se:3 44-1-Fr:4 39-0-Fr:2 34-1-Fr:6 
customer 58-1-Se:0 leaves minute 60, His/her total time is 2 minutes
customer 54-0-Se:0 leaves minute 60, His/her total time is 6 minutes
customer 55-0-Se:0 leaves minute 60, His/her total time is 5 minutes
minute 60: 
          58-0-Ju:5 11-1-Fr:2 55-1-So:2 35-0-Fr:4 46-1-Fr:3 23-1-Fr:2 
          47-0-So:6 7-1-Fr:2 48-1-So:5 35-1-Fr:3 31-1-Fr:3 60-0-So:3 
          59-0-Ju:2 19-0-Fr:4 42-0-So:2 51-1-Fr:5 28-0-Fr:3 50-1-So:6 
          52-1-Se:3 23-0-Fr:3 56-0-Se:3 40-1-Fr:6 30-0-Fr:6 57-0-Se:3 
          41-0-So:4 43-1-Fr:2 57-1-So:3 43-0-Fr:2 49-0-Fr:6 59-1-So:6 
          56-1-Se:3 22-1-Fr:3 60-1-So:3 44-1-Fr:4 39-0-Fr:2 34-1-Fr:6 
minute 61: 
          58-0-Ju:4 11-1-Fr:2 55-1-So:2 35-0-Fr:4 46-1-Fr:3 23-1-Fr:2 61-0-Fr:4 
          61-1-Se:4 7-1-Fr:2 47-0-So:5 35-1-Fr:3 31-1-Fr:3 60-0-So:3 48-1-So:5 
          59-0-Ju:1 19-0-Fr:4 42-0-So:2 51-1-Fr:5 28-0-Fr:3 50-1-So:6 
          52-1-Se:2 23-0-Fr:3 56-0-Se:3 40-1-Fr:6 30-0-Fr:6 57-0-Se:3 
          41-0-So:3 43-1-Fr:2 57-1-So:3 43-0-Fr:2 49-0-Fr:6 59-1-So:6 
          56-1-Se:2 22-1-Fr:3 60-1-So:3 44-1-Fr:4 39-0-Fr:2 34-1-Fr:6 
customer 59-0-Ju:0 leaves minute 62, His/her total time is 3 minutes
minute 62: 
          58-0-Ju:3 11-1-Fr:2 55-1-So:2 35-0-Fr:4 46-1-Fr:3 23-1-Fr:2 61-0-Fr:4 
          61-1-Se:3 7-1-Fr:2 47-0-So:5 35-1-Fr:3 31-1-Fr:3 60-0-So:3 48-1-So:5 
          62-0-Se:3 19-0-Fr:4 42-0-So:2 51-1-Fr:5 28-0-Fr:3 50-1-So:6 62-1-Fr:4 
          52-1-Se:1 23-0-Fr:3 56-0-Se:3 40-1-Fr:6 30-0-Fr:6 57-0-Se:3 
          41-0-So:2 43-1-Fr:2 57-1-So:3 43-0-Fr:2 49-0-Fr:6 59-1-So:6 
          56-1-Se:1 22-1-Fr:3 60-1-So:3 44-1-Fr:4 39-0-Fr:2 34-1-Fr:6 
customer 52-1-Se:0 leaves minute 63, His/her total time is 11 minutes
customer 56-1-Se:0 leaves minute 63, His/her total time is 7 minutes
minute 63: 
          58-0-Ju:2 11-1-Fr:2 55-1-So:2 35-0-Fr:4 46-1-Fr:3 23-1-Fr:2 61-0-Fr:4 
          61-1-Se:2 7-1-Fr:2 47-0-So:5 35-1-Fr:3 31-1-Fr:3 60-0-So:3 48-1-So:5 
          62-0-Se:2 19-0-Fr:4 42-0-So:2 51-1-Fr:5 28-0-Fr:3 50-1-So:6 62-1-Fr:4 
          56-0-Se:3 23-0-Fr:3 57-0-Se:3 40-1-Fr:6 30-0-Fr:6 63-0-Ju:3 
          41-0-So:1 43-1-Fr:2 57-1-So:3 43-0-Fr:2 49-0-Fr:6 59-1-So:6 
          63-1-Se:2 22-1-Fr:3 60-1-So:3 44-1-Fr:4 39-0-Fr:2 34-1-Fr:6 
customer 41-0-So:0 leaves minute 64, His/her total time is 23 minutes
minute 64: 
          58-0-Ju:1 11-1-Fr:2 55-1-So:2 35-0-Fr:4 46-1-Fr:3 23-1-Fr:2 61-0-Fr:4 
          61-1-Se:1 7-1-Fr:2 47-0-So:5 35-1-Fr:3 31-1-Fr:3 60-0-So:3 48-1-So:5 
          62-0-Se:1 19-0-Fr:4 42-0-So:2 51-1-Fr:5 28-0-Fr:3 50-1-So:6 62-1-Fr:4 
          56-0-Se:2 23-0-Fr:3 57-0-Se:3 40-1-Fr:6 30-0-Fr:6 63-0-Ju:3 64-1-Fr:5 
          64-0-Ju:6 43-1-Fr:2 57-1-So:3 43-0-Fr:2 49-0-Fr:6 59-1-So:6 
          63-1-Se:1 22-1-Fr:3 60-1-So:3 44-1-Fr:4 39-0-Fr:2 34-1-Fr:6 
customer 58-0-Ju:0 leaves minute 65, His/her total time is 7 minutes
customer 61-1-Se:0 leaves minute 65, His/her total time is 4 minutes
customer 62-0-Se:0 leaves minute 65, His/her total time is 3 minutes
customer 63-1-Se:0 leaves minute 65, His/her total time is 2 minutes
minute 65: 
          65-1-Se:3 11-1-Fr:2 55-1-So:2 35-0-Fr:4 46-1-Fr:3 61-0-Fr:4 23-1-Fr:2 
          47-0-So:5 7-1-Fr:2 48-1-So:5 35-1-Fr:3 31-1-Fr:3 60-0-So:3 
          42-0-So:2 19-0-Fr:4 50-1-So:6 51-1-Fr:5 28-0-Fr:3 62-1-Fr:4 
          56-0-Se:1 23-0-Fr:3 57-0-Se:3 40-1-Fr:6 30-0-Fr:6 63-0-Ju:3 64-1-Fr:5 
          64-0-Ju:5 43-1-Fr:2 57-1-So:3 43-0-Fr:2 49-0-Fr:6 59-1-So:6 
          60-1-So:3 22-1-Fr:3 65-0-So:3 44-1-Fr:4 39-0-Fr:2 34-1-Fr:6 
customer 56-0-Se:0 leaves minute 66, His/her total time is 10 minutes
minute 66: 
          65-1-Se:2 11-1-Fr:2 55-1-So:2 35-0-Fr:4 46-1-Fr:3 61-0-Fr:4 23-1-Fr:2 
          47-0-So:4 7-1-Fr:2 48-1-So:5 35-1-Fr:3 31-1-Fr:3 60-0-So:3 66-0-So:4 
          42-0-So:1 19-0-Fr:4 50-1-So:6 51-1-Fr:5 28-0-Fr:3 62-1-Fr:4 66-1-Fr:2 
          57-0-Se:3 23-0-Fr:3 63-0-Ju:3 40-1-Fr:6 30-0-Fr:6 64-1-Fr:5 
          64-0-Ju:4 43-1-Fr:2 57-1-So:3 43-0-Fr:2 49-0-Fr:6 59-1-So:6 
          60-1-So:2 22-1-Fr:3 65-0-So:3 44-1-Fr:4 39-0-Fr:2 34-1-Fr:6 
customer 42-0-So:0 leaves minute 67, His/her total time is 25 minutes
minute 67: 
          65-1-Se:1 11-1-Fr:2 55-1-So:2 35-0-Fr:4 46-1-Fr:3 61-0-Fr:4 23-1-Fr:2 
          47-0-So:3 7-1-Fr:2 48-1-So:5 35-1-Fr:3 31-1-Fr:3 60-0-So:3 66-0-So:4 
          67-0-Ju:2 19-0-Fr:4 50-1-So:6 51-1-Fr:5 28-0-Fr:3 66-1-Fr:2 62-1-Fr:4 
          57-0-Se:2 23-0-Fr:3 67-1-Se:6 40-1-Fr:6 30-0-Fr:6 64-1-Fr:5 63-0-Ju:3 
          64-0-Ju:3 43-1-Fr:2 57-1-So:3 43-0-Fr:2 49-0-Fr:6 59-1-So:6 
          60-1-So:1 22-1-Fr:3 65-0-So:3 44-1-Fr:4 39-0-Fr:2 34-1-Fr:6 
customer 65-1-Se:0 leaves minute 68, His/her total time is 3 minutes
customer 60-1-So:0 leaves minute 68, His/her total time is 8 minutes
minute 68: 
          68-1-Ju:3 11-1-Fr:2 55-1-So:2 35-0-Fr:4 46-1-Fr:3 61-0-Fr:4 23-1-Fr:2 
          47-0-So:2 7-1-Fr:2 48-1-So:5 35-1-Fr:3 31-1-Fr:3 60-0-So:3 66-0-So:4 
          67-0-Ju:1 19-0-Fr:4 50-1-So:6 51-1-Fr:5 28-0-Fr:3 66-1-Fr:2 62-1-Fr:4 
          57-0-Se:1 23-0-Fr:3 67-1-Se:6 40-1-Fr:6 30-0-Fr:6 64-1-Fr:5 63-0-Ju:3 
          64-0-Ju:2 43-1-Fr:2 57-1-So:3 43-0-Fr:2 49-0-Fr:6 59-1-So:6 
          65-0-So:3 22-1-Fr:3 68-0-So:3 44-1-Fr:4 39-0-Fr:2 34-1-Fr:6 
customer 67-0-Ju:0 leaves minute 69, His/her total time is 2 minutes
customer 57-0-Se:0 leaves minute 69, His/her total time is 12 minutes
minute 69: 
          68-1-Ju:2 11-1-Fr:2 55-1-So:2 35-0-Fr:4 46-1-Fr:3 61-0-Fr:4 23-1-Fr:2 
          47-0-So:1 7-1-Fr:2 48-1-So:5 35-1-Fr:3 31-1-Fr:3 60-0-So:3 66-0-So:4 
          50-1-So:6 19-0-Fr:4 62-1-Fr:4 51-1-Fr:5 28-0-Fr:3 66-1-Fr:2 69-0-Fr:5 
          67-1-Se:6 23-0-Fr:3 63-0-Ju:3 40-1-Fr:6 30-0-Fr:6 64-1-Fr:5 69-1-Fr:3 
          64-0-Ju:1 43-1-Fr:2 57-1-So:3 43-0-Fr:2 49-0-Fr:6 59-1-So:6 
          65-0-So:2 22-1-Fr:3 68-0-So:3 44-1-Fr:4 39-0-Fr:2 34-1-Fr:6 
customer 47-0-So:0 leaves minute 70, His/her total time is 23 minutes
customer 64-0-Ju:0 leaves minute 70, His/her total time is 6 minutes
minute 70: 
          68-1-Ju:1 11-1-Fr:2 55-1-So:2 35-0-Fr:4 46-1-Fr:3 61-0-Fr:4 23-1-Fr:2 
          48-1-So:5 7-1-Fr:2 60-0-So:3 35-1-Fr:3 31-1-Fr:3 66-0-So:4 70-1-So:4 
          50-1-So:5 19-0-Fr:4 62-1-Fr:4 51-1-Fr:5 28-0-Fr:3 66-1-Fr:2 69-0-Fr:5 
          67-1-Se:5 23-0-Fr:3 63-0-Ju:3 40-1-Fr:6 30-0-Fr:6 64-1-Fr:5 69-1-Fr:3 
          57-1-So:3 43-1-Fr:2 59-1-So:6 43-0-Fr:2 49-0-Fr:6 70-0-So:4 
          65-0-So:1 22-1-Fr:3 68-0-So:3 44-1-Fr:4 39-0-Fr:2 34-1-Fr:6 
customer 68-1-Ju:0 leaves minute 71, His/her total time is 3 minutes
customer 65-0-So:0 leaves minute 71, His/her total time is 6 minutes
minute 71: 
          71-1-Se:4 11-1-Fr:2 55-1-So:2 35-0-Fr:4 46-1-Fr:3 61-0-Fr:4 23-1-Fr:2 
          48-1-So:4 7-1-Fr:2 60-0-So:3 35-1-Fr:3 31-1-Fr:3 66-0-So:4 70-1-So:4 
          50-1-So:4 19-0-Fr:4 62-1-Fr:4 51-1-Fr:5 28-0-Fr:3 66-1-Fr:2 69-0-Fr:5 
          67-1-Se:4 23-0-Fr:3 63-0-Ju:3 40-1-Fr:6 30-0-Fr:6 64-1-Fr:5 69-1-Fr:3 
          57-1-So:2 43-1-Fr:2 59-1-So:6 43-0-Fr:2 49-0-Fr:6 70-0-So:4 
          68-0-So:3 22-1-Fr:3 71-0-So:5 44-1-Fr:4 39-0-Fr:2 34-1-Fr:6 
minute 72: 
          71-1-Se:3 11-1-Fr:2 55-1-So:2 35-0-Fr:4 46-1-Fr:3 61-0-Fr:4 23-1-Fr:2 
          48-1-So:3 7-1-Fr:2 60-0-So:3 35-1-Fr:3 31-1-Fr:3 66-0-So:4 70-1-So:4 
          50-1-So:3 19-0-Fr:4 62-1-Fr:4 51-1-Fr:5 28-0-Fr:3 66-1-Fr:2 69-0-Fr:5 
          67-1-Se:3 23-0-Fr:3 63-0-Ju:3 40-1-Fr:6 30-0-Fr:6 64-1-Fr:5 69-1-Fr:3 
          72-0-Se:4 43-1-Fr:2 57-1-So:1 43-0-Fr:2 49-0-Fr:6 70-0-So:4 59-1-So:6 
          68-0-So:2 22-1-Fr:3 71-0-So:5 44-1-Fr:4 39-0-Fr:2 34-1-Fr:6 72-1-Fr:3 
minute 73: 
          71-1-Se:2 11-1-Fr:2 55-1-So:2 35-0-Fr:4 46-1-Fr:3 61-0-Fr:4 23-1-Fr:2 73-0-Fr:3 
          48-1-So:2 73-1-So:6 60-0-So:3 7-1-Fr:2 31-1-Fr:3 66-0-So:4 70-1-So:4 35-1-Fr:3 
          50-1-So:2 19-0-Fr:4 62-1-Fr:4 51-1-Fr:5 28-0-Fr:3 66-1-Fr:2 69-0-Fr:5 
          67-1-Se:2 23-0-Fr:3 63-0-Ju:3 40-1-Fr:6 30-0-Fr:6 64-1-Fr:5 69-1-Fr:3 
          72-0-Se:3 43-1-Fr:2 57-1-So:1 43-0-Fr:2 49-0-Fr:6 70-0-So:4 59-1-So:6 
          68-0-So:1 22-1-Fr:3 71-0-So:5 44-1-Fr:4 39-0-Fr:2 34-1-Fr:6 72-1-Fr:3 
customer 68-0-So:0 leaves minute 74, His/her total time is 6 minutes
minute 74: 
          71-1-Se:1 11-1-Fr:2 55-1-So:2 35-0-Fr:4 46-1-Fr:3 61-0-Fr:4 23-1-Fr:2 73-0-Fr:3 
          48-1-So:1 73-1-So:6 60-0-So:3 7-1-Fr:2 31-1-Fr:3 66-0-So:4 70-1-So:4 35-1-Fr:3 
          74-1-Ju:6 50-1-So:1 62-1-Fr:4 19-0-Fr:4 28-0-Fr:3 66-1-Fr:2 69-0-Fr:5 51-1-Fr:5 
          67-1-Se:1 23-0-Fr:3 63-0-Ju:3 40-1-Fr:6 30-0-Fr:6 64-1-Fr:5 69-1-Fr:3 
          72-0-Se:2 43-1-Fr:2 57-1-So:1 43-0-Fr:2 49-0-Fr:6 70-0-So:4 59-1-So:6 
          71-0-So:5 22-1-Fr:3 34-1-Fr:6 44-1-Fr:4 39-0-Fr:2 72-1-Fr:3 74-0-Fr:3 
customer 71-1-Se:0 leaves minute 75, His/her total time is 4 minutes
customer 48-1-So:0 leaves minute 75, His/her total time is 27 minutes
customer 67-1-Se:0 leaves minute 75, His/her total time is 8 minutes
minute 75: 
          55-1-So:2 11-1-Fr:2 23-1-Fr:2 35-0-Fr:4 46-1-Fr:3 61-0-Fr:4 73-0-Fr:3 75-1-Fr:4 
          60-0-So:3 73-1-So:6 66-0-So:4 7-1-Fr:2 31-1-Fr:3 35-1-Fr:3 70-1-So:4 
          74-1-Ju:5 50-1-So:1 62-1-Fr:4 19-0-Fr:4 28-0-Fr:3 66-1-Fr:2 69-0-Fr:5 51-1-Fr:5 
          63-0-Ju:3 23-0-Fr:3 75-0-So:6 40-1-Fr:6 30-0-Fr:6 69-1-Fr:3 64-1-Fr:5 
          72-0-Se:1 43-1-Fr:2 57-1-So:1 43-0-Fr:2 49-0-Fr:6 70-0-So:4 59-1-So:6 
          71-0-So:4 22-1-Fr:3 34-1-Fr:6 44-1-Fr:4 39-0-Fr:2 72-1-Fr:3 74-0-Fr:3 
customer 72-0-Se:0 leaves minute 76, His/her total time is 4 minutes
minute 76: 
          55-1-So:1 11-1-Fr:2 23-1-Fr:2 35-0-Fr:4 46-1-Fr:3 61-0-Fr:4 73-0-Fr:3 75-1-Fr:4 
          60-0-So:2 73-1-So:6 66-0-So:4 7-1-Fr:2 31-1-Fr:3 35-1-Fr:3 70-1-So:4 76-1-Fr:6 
          74-1-Ju:4 50-1-So:1 62-1-Fr:4 19-0-Fr:4 28-0-Fr:3 66-1-Fr:2 69-0-Fr:5 51-1-Fr:5 
          63-0-Ju:2 23-0-Fr:3 75-0-So:6 40-1-Fr:6 30-0-Fr:6 69-1-Fr:3 64-1-Fr:5 
          76-0-Se:4 43-1-Fr:2 57-1-So:1 43-0-Fr:2 49-0-Fr:6 70-0-So:4 59-1-So:6 
          71-0-So:3 22-1-Fr:3 34-1-Fr:6 44-1-Fr:4 39-0-Fr:2 72-1-Fr:3 74-0-Fr:3 
customer 55-1-So:0 leaves minute 77, His/her total time is 22 minutes
minute 77: 
          11-1-Fr:2 35-0-Fr:4 23-1-Fr:2 75-1-Fr:4 46-1-Fr:3 61-0-Fr:4 73-0-Fr:3 77-0-Fr:6 
          60-0-So:1 73-1-So:6 66-0-So:4 7-1-Fr:2 31-1-Fr:3 35-1-Fr:3 70-1-So:4 76-1-Fr:6 
          74-1-Ju:3 50-1-So:1 62-1-Fr:4 19-0-Fr:4 28-0-Fr:3 66-1-Fr:2 69-0-Fr:5 51-1-Fr:5 
          77-1-Se:4 63-0-Ju:1 75-0-So:6 23-0-Fr:3 30-0-Fr:6 69-1-Fr:3 64-1-Fr:5 40-1-Fr:6 
          76-0-Se:3 43-1-Fr:2 57-1-So:1 43-0-Fr:2 49-0-Fr:6 70-0-So:4 59-1-So:6 
          71-0-So:2 22-1-Fr:3 34-1-Fr:6 44-1-Fr:4 39-0-Fr:2 72-1-Fr:3 74-0-Fr:3 
customer 60-0-So:0 leaves minute 78, His/her total time is 18 minutes
minute 78: 
          11-1-Fr:1 35-0-Fr:4 23-1-Fr:2 75-1-Fr:4 46-1-Fr:3 61-0-Fr:4 73-0-Fr:3 77-0-Fr:6 
          66-0-So:4 73-1-So:6 70-1-So:4 7-1-Fr:2 31-1-Fr:3 35-1-Fr:3 76-1-Fr:6 78-0-Fr:6 
          74-1-Ju:2 50-1-So:1 62-1-Fr:4 19-0-Fr:4 28-0-Fr:3 66-1-Fr:2 69-0-Fr:5 51-1-Fr:5 
          77-1-Se:3 63-0-Ju:1 75-0-So:6 23-0-Fr:3 30-0-Fr:6 69-1-Fr:3 64-1-Fr:5 40-1-Fr:6 
          76-0-Se:2 78-1-Se:6 57-1-So:1 43-1-Fr:2 49-0-Fr:6 70-0-So:4 59-1-So:6 43-0-Fr:2 
          71-0-So:1 22-1-Fr:3 34-1-Fr:6 44-1-Fr:4 39-0-Fr:2 72-1-Fr:3 74-0-Fr:3 
customer 11-1-Fr:0 leaves minute 79, His/her total time is 68 minutes
customer 71-0-So:0 leaves minute 79, His/her total time is 8 minutes
minute 79: 
          79-1-So:2 23-1-Fr:2 61-0-Fr:4 35-0-Fr:4 46-1-Fr:3 77-0-Fr:6 73-0-Fr:3 75-1-Fr:4 
          66-0-So:3 73-1-So:6 70-1-So:4 7-1-Fr:2 31-1-Fr:3 35-1-Fr:3 76-1-Fr:6 78-0-Fr:6 
          74-1-Ju:1 50-1-So:1 62-1-Fr:4 19-0-Fr:4 28-0-Fr:3 66-1-Fr:2 69-0-Fr:5 51-1-Fr:5 
          77-1-Se:2 63-0-Ju:1 75-0-So:6 23-0-Fr:3 30-0-Fr:6 69-1-Fr:3 64-1-Fr:5 40-1-Fr:6 
          76-0-Se:1 78-1-Se:6 57-1-So:1 43-1-Fr:2 49-0-Fr:6 70-0-So:4 59-1-So:6 43-0-Fr:2 
          79-0-So:4 39-0-Fr:2 22-1-Fr:3 44-1-Fr:4 74-0-Fr:3 72-1-Fr:3 34-1-Fr:6 
customer 74-1-Ju:0 leaves minute 80, His/her total time is 6 minutes
customer 76-0-Se:0 leaves minute 80, His/her total time is 4 minutes
minute 80: 
          79-1-So:1 23-1-Fr:2 61-0-Fr:4 35-0-Fr:4 46-1-Fr:3 77-0-Fr:6 73-0-Fr:3 75-1-Fr:4 
          66-0-So:2 73-1-So:6 70-1-So:4 7-1-Fr:2 31-1-Fr:3 35-1-Fr:3 76-1-Fr:6 78-0-Fr:6 
          80-0-Ju:2 50-1-So:1 62-1-Fr:4 19-0-Fr:4 28-0-Fr:3 66-1-Fr:2 69-0-Fr:5 51-1-Fr:5 
          77-1-Se:1 63-0-Ju:1 75-0-So:6 23-0-Fr:3 30-0-Fr:6 69-1-Fr:3 64-1-Fr:5 40-1-Fr:6 
          78-1-Se:6 80-1-Ju:3 57-1-So:1 43-0-Fr:2 49-0-Fr:6 70-0-So:4 59-1-So:6 43-1-Fr:2 
          79-0-So:3 39-0-Fr:2 22-1-Fr:3 44-1-Fr:4 74-0-Fr:3 72-1-Fr:3 34-1-Fr:6 
customer 79-1-So:0 leaves minute 81, His/her total time is 2 minutes
customer 77-1-Se:0 leaves minute 81, His/her total time is 4 minutes
minute 81: 
          81-0-Ju:5 23-1-Fr:2 61-0-Fr:4 35-0-Fr:4 46-1-Fr:3 77-0-Fr:6 73-0-Fr:3 75-1-Fr:4 
          66-0-So:1 73-1-So:6 70-1-So:4 7-1-Fr:2 31-1-Fr:3 35-1-Fr:3 76-1-Fr:6 78-0-Fr:6 
          80-0-Ju:1 50-1-So:1 62-1-Fr:4 19-0-Fr:4 28-0-Fr:3 66-1-Fr:2 69-0-Fr:5 51-1-Fr:5 
          81-1-Se:4 63-0-Ju:1 75-0-So:6 23-0-Fr:3 30-0-Fr:6 69-1-Fr:3 64-1-Fr:5 40-1-Fr:6 
          78-1-Se:5 80-1-Ju:3 57-1-So:1 43-0-Fr:2 49-0-Fr:6 70-0-So:4 59-1-So:6 43-1-Fr:2 
          79-0-So:2 39-0-Fr:2 22-1-Fr:3 44-1-Fr:4 74-0-Fr:3 72-1-Fr:3 34-1-Fr:6 
customer 66-0-So:0 leaves minute 82, His/her total time is 16 minutes
customer 80-0-Ju:0 leaves minute 82, His/her total time is 2 minutes
minute 82: 
          81-0-Ju:4 23-1-Fr:2 61-0-Fr:4 35-0-Fr:4 46-1-Fr:3 77-0-Fr:6 73-0-Fr:3 75-1-Fr:4 
          70-1-So:4 73-1-So:6 35-1-Fr:3 7-1-Fr:2 31-1-Fr:3 78-0-Fr:6 76-1-Fr:6 82-0-Fr:6 
          82-1-Ju:5 50-1-So:1 62-1-Fr:4 19-0-Fr:4 28-0-Fr:3 66-1-Fr:2 69-0-Fr:5 51-1-Fr:5 
          81-1-Se:3 63-0-Ju:1 75-0-So:6 23-0-Fr:3 30-0-Fr:6 69-1-Fr:3 64-1-Fr:5 40-1-Fr:6 
          78-1-Se:4 80-1-Ju:3 57-1-So:1 43-0-Fr:2 49-0-Fr:6 70-0-So:4 59-1-So:6 43-1-Fr:2 
          79-0-So:1 39-0-Fr:2 22-1-Fr:3 44-1-Fr:4 74-0-Fr:3 72-1-Fr:3 34-1-Fr:6 
customer 79-0-So:0 leaves minute 83, His/her total time is 4 minutes
minute 83: 
          81-0-Ju:3 23-1-Fr:2 61-0-Fr:4 35-0-Fr:4 46-1-Fr:3 77-0-Fr:6 73-0-Fr:3 75-1-Fr:4 
          70-1-So:3 73-1-So:6 35-1-Fr:3 7-1-Fr:2 31-1-Fr:3 78-0-Fr:6 76-1-Fr:6 82-0-Fr:6 
          82-1-Ju:4 50-1-So:1 62-1-Fr:4 19-0-Fr:4 28-0-Fr:3 66-1-Fr:2 69-0-Fr:5 51-1-Fr:5 
          81-1-Se:2 63-0-Ju:1 75-0-So:6 23-0-Fr:3 30-0-Fr:6 69-1-Fr:3 64-1-Fr:5 40-1-Fr:6 
          78-1-Se:3 80-1-Ju:3 57-1-So:1 43-0-Fr:2 49-0-Fr:6 70-0-So:4 59-1-So:6 43-1-Fr:2 
          83-1-Ju:6 83-0-So:5 22-1-Fr:3 39-0-Fr:2 74-0-Fr:3 72-1-Fr:3 34-1-Fr:6 44-1-Fr:4 
minute 84: 
          84-0-Se:3 81-0-Ju:2 61-0-Fr:4 23-1-Fr:2 46-1-Fr:3 77-0-Fr:6 73-0-Fr:3 75-1-Fr:4 35-0-Fr:4 
          70-1-So:2 73-1-So:6 35-1-Fr:3 7-1-Fr:2 31-1-Fr:3 78-0-Fr:6 76-1-Fr:6 82-0-Fr:6 84-1-Fr:2 
          82-1-Ju:3 50-1-So:1 62-1-Fr:4 19-0-Fr:4 28-0-Fr:3 66-1-Fr:2 69-0-Fr:5 51-1-Fr:5 
          81-1-Se:1 63-0-Ju:1 75-0-So:6 23-0-Fr:3 30-0-Fr:6 69-1-Fr:3 64-1-Fr:5 40-1-Fr:6 
          78-1-Se:2 80-1-Ju:3 57-1-So:1 43-0-Fr:2 49-0-Fr:6 70-0-So:4 59-1-So:6 43-1-Fr:2 
          83-1-Ju:5 83-0-So:5 22-1-Fr:3 39-0-Fr:2 74-0-Fr:3 72-1-Fr:3 34-1-Fr:6 44-1-Fr:4 
customer 81-1-Se:0 leaves minute 85, His/her total time is 4 minutes
minute 85: 
          84-0-Se:2 81-0-Ju:2 61-0-Fr:4 23-1-Fr:2 46-1-Fr:3 77-0-Fr:6 73-0-Fr:3 75-1-Fr:4 35-0-Fr:4 
          70-1-So:1 73-1-So:6 35-1-Fr:3 7-1-Fr:2 31-1-Fr:3 78-0-Fr:6 76-1-Fr:6 82-0-Fr:6 84-1-Fr:2 
          82-1-Ju:2 50-1-So:1 62-1-Fr:4 19-0-Fr:4 28-0-Fr:3 66-1-Fr:2 69-0-Fr:5 51-1-Fr:5 85-1-Fr:4 
          63-0-Ju:1 85-0-So:4 75-0-So:6 23-0-Fr:3 30-0-Fr:6 69-1-Fr:3 64-1-Fr:5 40-1-Fr:6 
          78-1-Se:1 80-1-Ju:3 57-1-So:1 43-0-Fr:2 49-0-Fr:6 70-0-So:4 59-1-So:6 43-1-Fr:2 
          83-1-Ju:4 83-0-So:5 22-1-Fr:3 39-0-Fr:2 74-0-Fr:3 72-1-Fr:3 34-1-Fr:6 44-1-Fr:4 
customer 70-1-So:0 leaves minute 86, His/her total time is 16 minutes
customer 63-0-Ju:0 leaves minute 86, His/her total time is 23 minutes
customer 78-1-Se:0 leaves minute 86, His/her total time is 8 minutes
minute 86: 
          84-0-Se:1 81-0-Ju:2 61-0-Fr:4 23-1-Fr:2 46-1-Fr:3 77-0-Fr:6 73-0-Fr:3 75-1-Fr:4 35-0-Fr:4 
          73-1-So:6 7-1-Fr:2 35-1-Fr:3 82-0-Fr:6 31-1-Fr:3 78-0-Fr:6 76-1-Fr:6 84-1-Fr:2 
          82-1-Ju:1 50-1-So:1 62-1-Fr:4 19-0-Fr:4 28-0-Fr:3 66-1-Fr:2 69-0-Fr:5 51-1-Fr:5 85-1-Fr:4 
          75-0-So:6 85-0-So:4 40-1-Fr:6 23-0-Fr:3 30-0-Fr:6 69-1-Fr:3 64-1-Fr:5 86-0-Fr:5 
          80-1-Ju:3 86-1-So:2 57-1-So:1 43-1-Fr:2 49-0-Fr:6 70-0-So:4 59-1-So:6 43-0-Fr:2 
          83-1-Ju:3 83-0-So:5 22-1-Fr:3 39-0-Fr:2 74-0-Fr:3 72-1-Fr:3 34-1-Fr:6 44-1-Fr:4 
customer 84-0-Se:0 leaves minute 87, His/her total time is 3 minutes
customer 82-1-Ju:0 leaves minute 87, His/her total time is 5 minutes
minute 87: 
          87-0-Se:6 81-0-Ju:2 61-0-Fr:4 23-1-Fr:2 46-1-Fr:3 77-0-Fr:6 73-0-Fr:3 75-1-Fr:4 35-0-Fr:4 
          73-1-So:5 87-1-So:2 35-1-Fr:3 7-1-Fr:2 31-1-Fr:3 78-0-Fr:6 76-1-Fr:6 84-1-Fr:2 82-0-Fr:6 
          50-1-So:1 19-0-Fr:4 62-1-Fr:4 51-1-Fr:5 28-0-Fr:3 66-1-Fr:2 69-0-Fr:5 85-1-Fr:4 
          75-0-So:5 85-0-So:4 40-1-Fr:6 23-0-Fr:3 30-0-Fr:6 69-1-Fr:3 64-1-Fr:5 86-0-Fr:5 
          80-1-Ju:2 86-1-So:2 57-1-So:1 43-1-Fr:2 49-0-Fr:6 70-0-So:4 59-1-So:6 43-0-Fr:2 
          83-1-Ju:2 83-0-So:5 22-1-Fr:3 39-0-Fr:2 74-0-Fr:3 72-1-Fr:3 34-1-Fr:6 44-1-Fr:4 
customer 50-1-So:0 leaves minute 88, His/her total time is 38 minutes
minute 88: 
          87-0-Se:5 81-0-Ju:2 61-0-Fr:4 23-1-Fr:2 46-1-Fr:3 77-0-Fr:6 73-0-Fr:3 75-1-Fr:4 35-0-Fr:4 
          73-1-So:4 87-1-So:2 35-1-Fr:3 7-1-Fr:2 31-1-Fr:3 78-0-Fr:6 76-1-Fr:6 84-1-Fr:2 82-0-Fr:6 
          88-0-Ju:5 88-1-Ju:5 62-1-Fr:4 19-0-Fr:4 85-1-Fr:4 66-1-Fr:2 69-0-Fr:5 51-1-Fr:5 28-0-Fr:3 
          75-0-So:4 85-0-So:4 40-1-Fr:6 23-0-Fr:3 30-0-Fr:6 69-1-Fr:3 64-1-Fr:5 86-0-Fr:5 
          80-1-Ju:1 86-1-So:2 57-1-So:1 43-1-Fr:2 49-0-Fr:6 70-0-So:4 59-1-So:6 43-0-Fr:2 
          83-1-Ju:1 83-0-So:5 22-1-Fr:3 39-0-Fr:2 74-0-Fr:3 72-1-Fr:3 34-1-Fr:6 44-1-Fr:4 
customer 80-1-Ju:0 leaves minute 89, His/her total time is 9 minutes
customer 83-1-Ju:0 leaves minute 89, His/her total time is 6 minutes
minute 89: 
          87-0-Se:4 81-0-Ju:2 61-0-Fr:4 23-1-Fr:2 46-1-Fr:3 77-0-Fr:6 73-0-Fr:3 75-1-Fr:4 35-0-Fr:4 
          73-1-So:3 87-1-So:2 35-1-Fr:3 7-1-Fr:2 31-1-Fr:3 78-0-Fr:6 76-1-Fr:6 84-1-Fr:2 82-0-Fr:6 
          88-0-Ju:4 88-1-Ju:5 62-1-Fr:4 19-0-Fr:4 85-1-Fr:4 66-1-Fr:2 69-0-Fr:5 51-1-Fr:5 28-0-Fr:3 
          75-0-So:3 85-0-So:4 40-1-Fr:6 23-0-Fr:3 30-0-Fr:6 69-1-Fr:3 64-1-Fr:5 86-0-Fr:5 
          57-1-So:1 86-1-So:2 59-1-So:6 89-0-So:3 49-0-Fr:6 70-0-So:4 43-0-Fr:2 43-1-Fr:2 
          89-1-Se:2 83-0-So:5 22-1-Fr:3 39-0-Fr:2 74-0-Fr:3 72-1-Fr:3 34-1-Fr:6 44-1-Fr:4 
customer 57-1-So:0 leaves minute 90, His/her total time is 33 minutes
minute 90: 
          87-0-Se:3 81-0-Ju:2 61-0-Fr:4 23-1-Fr:2 46-1-Fr:3 77-0-Fr:6 73-0-Fr:3 75-1-Fr:4 35-0-Fr:4 
          73-1-So:2 87-1-So:2 35-1-Fr:3 7-1-Fr:2 31-1-Fr:3 78-0-Fr:6 76-1-Fr:6 84-1-Fr:2 82-0-Fr:6 
          88-0-Ju:3 88-1-Ju:5 62-1-Fr:4 19-0-Fr:4 85-1-Fr:4 66-1-Fr:2 69-0-Fr:5 51-1-Fr:5 28-0-Fr:3 
          90-1-Se:2 75-0-So:2 40-1-Fr:6 85-0-So:4 30-0-Fr:6 69-1-Fr:3 64-1-Fr:5 86-0-Fr:5 23-0-Fr:3 
          90-0-Ju:5 59-1-So:6 70-0-So:4 86-1-So:2 49-0-Fr:6 43-1-Fr:2 43-0-Fr:2 89-0-So:3 
          89-1-Se:1 83-0-So:5 22-1-Fr:3 39-0-Fr:2 74-0-Fr:3 72-1-Fr:3 34-1-Fr:6 44-1-Fr:4 
customer 89-1-Se:0 leaves minute 91, His/her total time is 2 minutes
minute 91: 
          87-0-Se:2 81-0-Ju:2 61-0-Fr:4 23-1-Fr:2 46-1-Fr:3 77-0-Fr:6 73-0-Fr:3 75-1-Fr:4 35-0-Fr:4 
          73-1-So:1 87-1-So:2 35-1-Fr:3 7-1-Fr:2 31-1-Fr:3 78-0-Fr:6 76-1-Fr:6 84-1-Fr:2 82-0-Fr:6 
          88-0-Ju:2 88-1-Ju:5 62-1-Fr:4 19-0-Fr:4 85-1-Fr:4 66-1-Fr:2 69-0-Fr:5 51-1-Fr:5 28-0-Fr:3 
          90-1-Se:1 75-0-So:2 40-1-Fr:6 85-0-So:4 30-0-Fr:6 69-1-Fr:3 64-1-Fr:5 86-0-Fr:5 23-0-Fr:3 
          90-0-Ju:4 59-1-So:6 70-0-So:4 86-1-So:2 49-0-Fr:6 43-1-Fr:2 43-0-Fr:2 89-0-So:3 91-1-So:4 
          91-0-Se:5 83-0-So:5 22-1-Fr:3 39-0-Fr:2 74-0-Fr:3 72-1-Fr:3 34-1-Fr:6 44-1-Fr:4 
customer 73-1-So:0 leaves minute 92, His/her total time is 19 minutes
customer 90-1-Se:0 leaves minute 92, His/her total time is 2 minutes
minute 92: 
          87-0-Se:1 81-0-Ju:2 61-0-Fr:4 23-1-Fr:2 46-1-Fr:3 77-0-Fr:6 73-0-Fr:3 75-1-Fr:4 35-0-Fr:4 
          87-1-So:2 92-0-So:6 35-1-Fr:3 7-1-Fr:2 31-1-Fr:3 78-0-Fr:6 76-1-Fr:6 84-1-Fr:2 82-0-Fr:6 
          88-0-Ju:1 88-1-Ju:5 62-1-Fr:4 19-0-Fr:4 85-1-Fr:4 66-1-Fr:2 69-0-Fr:5 51-1-Fr:5 28-0-Fr:3 
          92-1-Se:3 75-0-So:2 40-1-Fr:6 85-0-So:4 30-0-Fr:6 69-1-Fr:3 64-1-Fr:5 86-0-Fr:5 23-0-Fr:3 
          90-0-Ju:3 59-1-So:6 70-0-So:4 86-1-So:2 49-0-Fr:6 43-1-Fr:2 43-0-Fr:2 89-0-So:3 91-1-So:4 
          91-0-Se:4 83-0-So:5 22-1-Fr:3 39-0-Fr:2 74-0-Fr:3 72-1-Fr:3 34-1-Fr:6 44-1-Fr:4 
customer 87-0-Se:0 leaves minute 93, His/her total time is 6 minutes
customer 88-0-Ju:0 leaves minute 93, His/her total time is 5 minutes
minute 93: 
          81-0-Ju:2 93-0-Ju:3 61-0-Fr:4 23-1-Fr:2 46-1-Fr:3 77-0-Fr:6 73-0-Fr:3 75-1-Fr:4 35-0-Fr:4 
          87-1-So:1 92-0-So:6 35-1-Fr:3 7-1-Fr:2 31-1-Fr:3 78-0-Fr:6 76-1-Fr:6 84-1-Fr:2 82-0-Fr:6 
          88-1-Ju:5 93-1-Ju:2 62-1-Fr:4 19-0-Fr:4 85-1-Fr:4 66-1-Fr:2 69-0-Fr:5 51-1-Fr:5 28-0-Fr:3 
          92-1-Se:2 75-0-So:2 40-1-Fr:6 85-0-So:4 30-0-Fr:6 69-1-Fr:3 64-1-Fr:5 86-0-Fr:5 23-0-Fr:3 
          90-0-Ju:2 59-1-So:6 70-0-So:4 86-1-So:2 49-0-Fr:6 43-1-Fr:2 43-0-Fr:2 89-0-So:3 91-1-So:4 
          91-0-Se:3 83-0-So:5 22-1-Fr:3 39-0-Fr:2 74-0-Fr:3 72-1-Fr:3 34-1-Fr:6 44-1-Fr:4 
customer 87-1-So:0 leaves minute 94, His/her total time is 7 minutes
minute 94: 
          81-0-Ju:1 93-0-Ju:3 61-0-Fr:4 23-1-Fr:2 46-1-Fr:3 77-0-Fr:6 73-0-Fr:3 75-1-Fr:4 35-0-Fr:4 
          92-0-So:6 7-1-Fr:2 35-1-Fr:3 82-0-Fr:6 31-1-Fr:3 78-0-Fr:6 76-1-Fr:6 84-1-Fr:2 94-0-Fr:5 
          88-1-Ju:4 93-1-Ju:2 62-1-Fr:4 19-0-Fr:4 85-1-Fr:4 66-1-Fr:2 69-0-Fr:5 51-1-Fr:5 28-0-Fr:3 
          92-1-Se:1 75-0-So:2 40-1-Fr:6 85-0-So:4 30-0-Fr:6 69-1-Fr:3 64-1-Fr:5 86-0-Fr:5 23-0-Fr:3 
          90-0-Ju:1 59-1-So:6 70-0-So:4 86-1-So:2 49-0-Fr:6 43-1-Fr:2 43-0-Fr:2 89-0-So:3 91-1-So:4 
          91-0-Se:2 83-0-So:5 22-1-Fr:3 39-0-Fr:2 74-0-Fr:3 72-1-Fr:3 34-1-Fr:6 44-1-Fr:4 94-1-Fr:2 
customer 81-0-Ju:0 leaves minute 95, His/her total time is 14 minutes
customer 92-1-Se:0 leaves minute 95, His/her total time is 3 minutes
customer 90-0-Ju:0 leaves minute 95, His/her total time is 5 minutes
minute 95: 
          93-0-Ju:3 95-0-Ju:6 61-0-Fr:4 23-1-Fr:2 46-1-Fr:3 77-0-Fr:6 73-0-Fr:3 75-1-Fr:4 35-0-Fr:4 
          92-0-So:5 7-1-Fr:2 35-1-Fr:3 82-0-Fr:6 31-1-Fr:3 78-0-Fr:6 76-1-Fr:6 84-1-Fr:2 94-0-Fr:5 
          88-1-Ju:3 93-1-Ju:2 62-1-Fr:4 19-0-Fr:4 85-1-Fr:4 66-1-Fr:2 69-0-Fr:5 51-1-Fr:5 28-0-Fr:3 
          95-1-Se:3 75-0-So:2 40-1-Fr:6 85-0-So:4 30-0-Fr:6 69-1-Fr:3 64-1-Fr:5 86-0-Fr:5 23-0-Fr:3 
          59-1-So:6 86-1-So:2 70-0-So:4 89-0-So:3 49-0-Fr:6 43-1-Fr:2 43-0-Fr:2 91-1-So:4 
          91-0-Se:1 83-0-So:5 22-1-Fr:3 39-0-Fr:2 74-0-Fr:3 72-1-Fr:3 34-1-Fr:6 44-1-Fr:4 94-1-Fr:2 
customer 91-0-Se:0 leaves minute 96, His/her total time is 5 minutes
minute 96: 
          93-0-Ju:2 95-0-Ju:6 61-0-Fr:4 23-1-Fr:2 46-1-Fr:3 77-0-Fr:6 73-0-Fr:3 75-1-Fr:4 35-0-Fr:4 
          92-0-So:4 7-1-Fr:2 35-1-Fr:3 82-0-Fr:6 31-1-Fr:3 78-0-Fr:6 76-1-Fr:6 84-1-Fr:2 94-0-Fr:5 
          88-1-Ju:2 93-1-Ju:2 62-1-Fr:4 19-0-Fr:4 85-1-Fr:4 66-1-Fr:2 69-0-Fr:5 51-1-Fr:5 28-0-Fr:3 
          95-1-Se:2 75-0-So:2 40-1-Fr:6 85-0-So:4 30-0-Fr:6 69-1-Fr:3 64-1-Fr:5 86-0-Fr:5 23-0-Fr:3 
          96-0-Ju:4 59-1-So:5 70-0-So:4 86-1-So:2 49-0-Fr:6 43-1-Fr:2 43-0-Fr:2 91-1-So:4 89-0-So:3 
          96-1-Ju:4 83-0-So:5 22-1-Fr:3 39-0-Fr:2 74-0-Fr:3 72-1-Fr:3 34-1-Fr:6 94-1-Fr:2 44-1-Fr:4 
minute 97: 
          97-0-Se:4 93-0-Ju:1 61-0-Fr:4 23-1-Fr:2 95-0-Ju:6 77-0-Fr:6 73-0-Fr:3 75-1-Fr:4 35-0-Fr:4 46-1-Fr:3 
          92-0-So:3 7-1-Fr:2 35-1-Fr:3 82-0-Fr:6 31-1-Fr:3 78-0-Fr:6 76-1-Fr:6 84-1-Fr:2 94-0-Fr:5 97-1-Fr:3 
          88-1-Ju:1 93-1-Ju:2 62-1-Fr:4 19-0-Fr:4 85-1-Fr:4 66-1-Fr:2 69-0-Fr:5 51-1-Fr:5 28-0-Fr:3 
          95-1-Se:1 75-0-So:2 40-1-Fr:6 85-0-So:4 30-0-Fr:6 69-1-Fr:3 64-1-Fr:5 86-0-Fr:5 23-0-Fr:3 
          96-0-Ju:3 59-1-So:5 70-0-So:4 86-1-So:2 49-0-Fr:6 43-1-Fr:2 43-0-Fr:2 91-1-So:4 89-0-So:3 
          96-1-Ju:3 83-0-So:5 22-1-Fr:3 39-0-Fr:2 74-0-Fr:3 72-1-Fr:3 34-1-Fr:6 94-1-Fr:2 44-1-Fr:4 
customer 88-1-Ju:0 leaves minute 98, His/her total time is 10 minutes
customer 95-1-Se:0 leaves minute 98, His/her total time is 3 minutes
minute 98: 
          97-0-Se:3 93-0-Ju:1 61-0-Fr:4 23-1-Fr:2 95-0-Ju:6 77-0-Fr:6 73-0-Fr:3 75-1-Fr:4 35-0-Fr:4 46-1-Fr:3 
          92-0-So:2 7-1-Fr:2 35-1-Fr:3 82-0-Fr:6 31-1-Fr:3 78-0-Fr:6 76-1-Fr:6 84-1-Fr:2 94-0-Fr:5 97-1-Fr:3 
          93-1-Ju:2 98-0-So:4 62-1-Fr:4 19-0-Fr:4 85-1-Fr:4 66-1-Fr:2 69-0-Fr:5 51-1-Fr:5 28-0-Fr:3 
          75-0-So:2 85-0-So:4 40-1-Fr:6 98-1-So:4 30-0-Fr:6 69-1-Fr:3 64-1-Fr:5 86-0-Fr:5 23-0-Fr:3 
          96-0-Ju:2 59-1-So:5 70-0-So:4 86-1-So:2 49-0-Fr:6 43-1-Fr:2 43-0-Fr:2 91-1-So:4 89-0-So:3 
          96-1-Ju:2 83-0-So:5 22-1-Fr:3 39-0-Fr:2 74-0-Fr:3 72-1-Fr:3 34-1-Fr:6 94-1-Fr:2 44-1-Fr:4 
minute 99: 
          97-0-Se:2 93-0-Ju:1 61-0-Fr:4 23-1-Fr:2 95-0-Ju:6 77-0-Fr:6 73-0-Fr:3 75-1-Fr:4 35-0-Fr:4 46-1-Fr:3 
          92-0-So:1 7-1-Fr:2 35-1-Fr:3 82-0-Fr:6 31-1-Fr:3 78-0-Fr:6 76-1-Fr:6 84-1-Fr:2 94-0-Fr:5 97-1-Fr:3 
          93-1-Ju:1 99-0-Ju:2 62-1-Fr:4 19-0-Fr:4 98-0-So:4 66-1-Fr:2 69-0-Fr:5 51-1-Fr:5 28-0-Fr:3 85-1-Fr:4 
          75-0-So:1 85-0-So:4 40-1-Fr:6 98-1-So:4 99-1-So:4 69-1-Fr:3 64-1-Fr:5 86-0-Fr:5 23-0-Fr:3 30-0-Fr:6 
          96-0-Ju:1 59-1-So:5 70-0-So:4 86-1-So:2 49-0-Fr:6 43-1-Fr:2 43-0-Fr:2 91-1-So:4 89-0-So:3 
          96-1-Ju:1 83-0-So:5 22-1-Fr:3 39-0-Fr:2 74-0-Fr:3 72-1-Fr:3 34-1-Fr:6 94-1-Fr:2 44-1-Fr:4 
Senior			48			12			4.583333333333333
Junior			46			25			6.717391304347826
Sophomor			41			38			13.731707317073171
Freshman			7			68			29.0



****************************************************/