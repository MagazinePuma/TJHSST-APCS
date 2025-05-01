// Name:
// Date:
import java.util.*;
import java.io.*;
import java.text.DecimalFormat;

public class Cemetery
{
   public static void main (String [] args)
   {
      File file = new File("cemetery_short.txt");
      //File file = new File("cemetery.txt");
      int numEntries = countEntries(file);
      Person[] cemetery = readIntoArray(file, numEntries); 
      //uncomment to see if you have read the data properly
      for (int i = 0; i < cemetery.length; i++) 
         System.out.println(cemetery[i]);
         
      int min = locateMinAgePerson(cemetery);
      int max = locateMaxAgePerson(cemetery); 
      System.out.println("\nIn the St. Mary Magdelene Old Fish Cemetery: ");
      System.out.println("Name of youngest person: " + cemetery[min].getName());
      System.out.println("Age of youngest person: " + cemetery[min].getAge());    
      System.out.println("Name of oldest person: " + cemetery[max].getName());
      System.out.println("Age of oldest person: " + cemetery[max].getAge()); 
      //you may create other testing cases here
     
          
   }
   
   /* Counts and returns the number of entries in File f. 
      Returns 0 if the File f is not valid.
      Uses a try-catch block.   
      @param f -- the file object
   */
   public static int countEntries(File f)
   {
      int count = 0;
      Scanner infile = null;
      try
      {
         infile = new Scanner(f);
      }
      catch(IOException e)
      {
         return 0;  
      }
      while (infile.hasNextLine()){
         count++;
         infile.nextLine();
      }
      return count;

   }

   /* Reads the data from file f (you may assume each line has same allignment).
      Fills the array with Person objects. If File f is not valid return null.
      @param f -- the file object 
      @param num -- the number of lines in the File f  
   */
   public static Person[] readIntoArray (File f, int num)
   {
      Person[] people = new Person[num];

      Scanner infile = null;
      try
      {
         infile = new Scanner(f);
      }
      catch(IOException e)
      {
         return null;  
      }
      for(int i =0; i<num; i++){
         people[i] = makeObjects(infile.nextLine());
      }
      
      return people;
   
   }
   
   /* A helper method that instantiates one Person object.
      @param entry -- one line of the input file.
      This method is made public for gradeit testing purposes.
      This method should not be used in any other class!!!
   */
   public static Person makeObjects(String entry)
   {
      int yearColumnNumber = -1;
      String[] fields = entry.split(" ");
      for(int i=0; i<fields.length; i++) {
         if(fields[i].length() == 4) {
            try {
               int year = Integer.parseInt(fields[i]);
               if(year > 1000) {
                  yearColumnNumber = i;
               }
            } catch(Exception ex) {
               // do nothing
            }
         }
      }

      String name = "";
      int count = 0;
      while(count < yearColumnNumber - 2) {
         name = name + " " + fields[count].trim();
         count++;
      }

      String date = fields[yearColumnNumber - 2] + " " + fields[yearColumnNumber - 1] + " " + fields[yearColumnNumber];
      String age = fields[yearColumnNumber + 1];

      return new Person(name, date, age);
   
   }  
   
   /* Finds and returns the location (the index) of the Person
      who is the youngest. (if the array is empty it returns -1)
      If there is a tie the lowest index is returned.
      @param arr -- an array of Person objects.
   */
   public static int locateMinAgePerson(Person[] arr)
   {
      double minAge = arr[0].getAge();
      int result = 0;
      for(int i=0; i<arr.length; i++) {
         if(arr[i].getAge() < minAge) {
            minAge = arr[i].getAge();
            result = i;
         }
      }
      return result;
   }   
   
   /* Finds and returns the location (the index) of the Person
      who is the oldest. (if the array is empty it returns -1)
      If there is a tie the lowest index is returned.
      @param arr -- an array of Person objects.
   */
   public static int locateMaxAgePerson(Person[] arr)
   {
      double maxAge = arr[0].getAge();
      int result = 0;
      for(int i=0; i<arr.length; i++) {
         if(arr[i].getAge() > maxAge) {
            maxAge = arr[i].getAge();
            result = i;
         }
      }
      return result;
   }        
} 

class Person
{
   //constant that can be used for formatting purposes
   private static final DecimalFormat df = new DecimalFormat("0.0000");
   /* private fields */
   private String name;
   private String burialDate;
   private double age;
      
   /* a three-arg constructor  
    @param name, burialDate may have leading or trailing spaces
    It creates a valid Person object in which each field has the leading and trailing
    spaces eliminated*/
   public Person(String name, String burialDate, String age)
   {
      this.name = name;
      this.burialDate = burialDate;
      this.age = calculateAge(age);
   
   }
   /* any necessary accessor methods (at least "double getAge()" and "String getName()" )
   make sure your get and/or set methods use the same data type as the field  */
   
   
   /*handles the inconsistencies regarding age
     @param a = a string containing an age from file. Ex: "12", "12w", "12d"
     returns the age transformed into year with 4 decimals rounding
   */
   public double calculateAge(String a)
   {
      if(a.endsWith("w")){
         double result = Double.parseDouble(a.substring(0, a.length()-1)) * 7 / 365;
         return Double.parseDouble(df. format(result)); 
      } else if (a.endsWith("d")){
         double result = Double.parseDouble(a.substring(0, a.length()-1)) / 365;
         return Double.parseDouble(df.format(result));  
      }
      return Double.parseDouble(a);
   }

   public String getName(){
      return name;
   }

   public String getBurialDate() {
      return burialDate;
   }

   public double getAge() {
      return age;
   }

   public String toString() {
      return "Name: " + this.name + "; Busial Date: " + this.burialDate + "; Age: " + this.age; 
   }
}

/******************************************

 John William ALLARDYCE, 17 Mar 1844, 2.9
 Frederic Alex. ALLARDYCE, 21 Apr 1844, 0.17
 Philip AMIS, 03 Aug 1848, 1.0
 Thomas ANDERSON, 06 Jul 1845, 27.0
 Edward ANGEL, 20 Nov 1842, 22.0
 Lucy Ann COLEBACK, 23 Jul 1843, 0.2685
 Thomas William COLLEY, 08 Aug 1833, 0.011
 Joseph COLLIER, 03 Apr 1831, 58.0
 
 In the St. Mary Magdelene Old Fish Cemetery --> 
 Name of youngest person: Thomas William COLLEY
 Age of youngest person: 0.011
 Name of oldest person: Joseph COLLIER
 Age of oldest person: 58.0
 
 **************************************/