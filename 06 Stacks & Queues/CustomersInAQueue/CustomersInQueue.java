//ejurj shell modified
//name: Dennis Tislin    date: 1/28

import java.util.Queue;
import java.util.Random;
import java.util.LinkedList;
import java.util.Scanner;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

public class CustomersInQueue
{
   public static final double CHANCE_OF_CUSTOMER = 0.8; // 
  
   public static void outfileServiceAreasAndQueue(PrintWriter outfile, int min, Customer[] atServiceWindow, Queue<Customer> queue)
   { 
      outfile.print(min + ": ");
      for(int i=0; i< atServiceWindow.length; i++)
      {
         
         // comment for format required by autograder
         outfile.printf("SA%s: %-10s", i+1,atServiceWindow[i]);

         // Uncomment for format required by autograder
         // outfile.print(atServiceWindow[i] != null?atServiceWindow[i]+" ":"");
      }
      outfile.println(queue);
   }
  
   public static double calculateAverage(int totalMinutes, int customers)
   {
      return (int)(1.0 * totalMinutes/customers * 10)/10.0;
   }

   public static PrintWriter setUpFile()
   {
      PrintWriter outfile = null; 
      try
      {
         outfile = new PrintWriter(new FileWriter("customersSimulation.txt"));
      }
      catch(IOException e)
      {
         System.out.println("File not created");
         System.exit(0);
      }
      return outfile;
   }
            
   public static void main(String[] args)
   {     
      PrintWriter outfile = setUpFile();      
      
      System.out.println("Customers in a Queue Simulation! ");
      Scanner kb = new Scanner(System.in);
      System.out.print("How many service areas? ");
      int sa = kb.nextInt();
      System.out.print("How long, in minutes, should the simulation run? ");
      int time = kb.nextInt();
      
      serveTheCustomers(time, sa, outfile);  //run the simulation

      if(kb != null) {
         kb.close();
         kb = null;
      }
   }
   
   public static void serveTheCustomers(int time, int number_of_serviceAreas, PrintWriter outfile)
   {
      /***************************************
        call outfileTimeAndQueue() to store the queue to the file.  
        **********************************/
        Queue<Customer> queue = new LinkedList<Customer>();
        Customer[] atServiceWindow = new Customer[number_of_serviceAreas];
        for(int i = 0; i < number_of_serviceAreas; i++) {
            atServiceWindow[i] = null;
        }
        
        //extension - finish serving the people in the queue and service areas - OPTIONAL
        
      //   /*   report the data to the screen    */
      //   System.out.println("How many service areas are there? " + number_of_serviceAreas);
      //   System.out.println("How long, in minutes, should the simulation run? " + time);
      //   System.out.println("Number of service areas = " + number_of_serviceAreas);
      //   System.out.println("Probability of arrival = " + CHANCE_OF_CUSTOMER);
      //   System.out.println("Number of minutes = " + time);
      //   System.out.println("Average wait time = " + calculateAverage(time, currentCustomerId)); 
        /* report the data to the file */
        int currentCustomerId = 0;
        int maxWaitTime = 0;
        int longestQueue = 0;
        for(int i = 0; i < time; i++) {
         double customerJoinsQueueChance = new Random().nextDouble();
         if(customerJoinsQueueChance <= CHANCE_OF_CUSTOMER) {
            int randomServiceTime = new Random().nextInt(7) + 3;
            queue.add(new Customer(currentCustomerId, randomServiceTime));
            currentCustomerId++;
            if(randomServiceTime > maxWaitTime){
               maxWaitTime = randomServiceTime;
            }
            if(queue.size() > longestQueue){
               longestQueue = queue.size();
            }
         }

         for(int j = 0; j < atServiceWindow.length; j++) {
            if(atServiceWindow[j] != null) {
               Customer customer = atServiceWindow[j];
               customer.reduceRemainingTime();
               if(customer.getRemainingTime() == 0) {
                  // comment for format required by autograder
                  outfile.println("Customer: " + j + " leaves minute " + i + ", His/her total time is " + customer.getServiceTime());
                  atServiceWindow[j] = null;
               }
            } else {
               atServiceWindow[j] = queue.poll();
            }
         }

         outfileServiceAreasAndQueue(outfile, i, atServiceWindow, queue);
        }
        /*   report the data to the screen    */
      // Uncomment for format required by autograder
      //   System.out.println("Total customers served = " + currentCustomerId);
      //   System.out.println("Average wait time = " + calculateAverage(time, currentCustomerId));
      //   System.out.println("Longest wait time = " + maxWaitTime);
      //   System.out.println("Longest queue = " + longestQueue);

      //   outfile.println();
      //   outfile.println("Total customers served = " + currentCustomerId);
      //   outfile.println("Average wait time = " + calculateAverage(time, currentCustomerId));
      //   outfile.println("Longest wait time = " + maxWaitTime);
      //   outfile.println("Longest queue = " + longestQueue);

      // comment for format required by autograder
        System.out.println("How many service areas? " + number_of_serviceAreas);
        System.out.println("How long, in minutes, should the simulation run? " + time);
      //   System.out.println("Probability of arrival = " + CHANCE_OF_CUSTOMER);
      //   System.out.println("Number of minutes = " + time);
        System.out.println("Total customers served = " + currentCustomerId);
        System.out.println("Average wait time = " + calculateAverage(time, currentCustomerId));
        System.out.println("Longest wait time " + maxWaitTime);
        System.out.println("Longest queue = " + longestQueue);

      // comment for format required by autograder
        outfile.println();
        outfile.println("How long, in minutes, should the simulation run? " + time);
        outfile.println("Number of service areas = " + number_of_serviceAreas);
        outfile.println("Probability of arrival = " + CHANCE_OF_CUSTOMER);
        outfile.println("Number of minutes = " + time);
        outfile.println("Average wait time = " + calculateAverage(time, currentCustomerId));
        outfile.println("Longest wait time " + maxWaitTime);
        outfile.println("Longest queue = " + longestQueue);
      
         outfile.close();
      }
           
   }
   
   class Customer {
      private int serviceTime;
      private int remainingTime;
      private int id;

      public Customer(int id, int serviceTime){
         this.id = id;
         this.serviceTime = serviceTime;
         this.remainingTime = serviceTime;
      }

      public int getServiceTime() {
         return serviceTime;
      }

      public void setServiceTime(int serviceTime) {
         this.serviceTime = serviceTime;
      }

      public int getRemainingTime() {
         return remainingTime;
      }

      public void reduceRemainingTime() {
         this.remainingTime = this.remainingTime - 1;
      }

      public int getId() {
         return this.id;
      }

      public String toString() {
         
         // comment for format required by autograder
         return "" + this.getId() + ":" + this.getRemainingTime();

         // Uncomment for format required by autograder
         // return "" + this.getRemainingTime();
      }

      // public String toString() {
      //    return "" + this.getRemainingTime();
      // }

   }


/******************************************************
 Customers in a Queue Simulation! 
 How many service areas? 4
 How long, in minutes, should the simulation run? 60
 Total customers served = 33
 Average wait time = 9.6
 Longest wait time = 16
 Longest queue = 11

****************************************************/