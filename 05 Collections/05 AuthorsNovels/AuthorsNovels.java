//  Name: Dennis Tislin   
//  Date: 1/3

import java.io.*;
import java.util.*;

public class AuthorsNovels
{
   public static void main(String[] args) throws IOException
   {
      /*   test the AuthorEntry object  */
      AuthorEntry a = new AuthorEntry("Aaa");
      System.out.println("name: " + a.getName());
      System.out.println("novels: " + a.getNovels());
      System.out.println("toString(): " + a);
      AuthorEntry b = new AuthorEntry("bbb", "y");
      System.out.println("name: " + b.getName());
      b.addNovel("z");
      b.addNovel("y");
      b.addNovel("x");
      System.out.println("novels: " + b.getNovels());
      System.out.println("toString(): " + b);
      System.out.println(b.compareTo(a));   // 1
      System.out.println(a.compareTo(b));   // -1
      System.out.println("AAA".compareTo(a.getName())); // 0
      
      
      /*  start the lab  */
      Scanner keyboard = new Scanner(System.in);
      System.out.print("\nEnter input file name: ");
      String inFileName = keyboard.nextLine().trim()+".txt";
      Scanner inputFile = new Scanner(new File(inFileName));
      //System.out.print("\nEnter output file name: ");
      //String outFileName = keyboard.nextLine().trim();
      AuthorList authors = readAndMakeTheList(inputFile);
      String outFileName = "authorsNovelsOut.txt";
      PrintWriter outputFile = new PrintWriter(new FileWriter(outFileName));
      outputFile.println( authors.toString() );
      inputFile.close(); 						
      outputFile.close();
      System.out.println("Done.");
   }
   
   public static AuthorList readAndMakeTheList(Scanner inputFile)
   {
      AuthorList theList = new AuthorList();
      while(inputFile.hasNextLine())
      {
         theList.readOneLine(inputFile.nextLine());
      }
      return theList;
   }
}

class AuthorList extends ArrayList<AuthorEntry>
{
    /**   you get an ArrayList for free   **/
   public AuthorList()
   {
      super();
   }
     /** extracts the author and book from oneLine.
         calls addAuthorEntry      
      */
   public void readOneLine(String oneLine) 
   {  
      String[] entries = oneLine.split(", ");
      addAuthorEntry(entries[0], entries[1]);
   }
   
    /** use a listIterator.  Needs to call .previous() 
          either inserts a new AuthorEntry object, or 
          adds a novel to a previous AuthorEntry object,
          in alphabetic order
    */
   public void addAuthorEntry(String name, String book)
   {
      boolean entryExists = false;
      for(ListIterator<AuthorEntry> iterator = this.listIterator(); iterator.hasNext();){
         AuthorEntry entry = iterator.next();
         if(entry.getName().equalsIgnoreCase(name)) {
            entryExists = true;
            entry.addNovel(book);
         }
      }
      if(!entryExists) {
         this.add(new AuthorEntry(name, book));
      }
      Collections.sort(this);
   }
   
   public String toString()
   {  
      String output = "";
      for(ListIterator<AuthorEntry> iterator = this.listIterator(); iterator.hasNext();){
         AuthorEntry entry = iterator.next();
         output += entry.toString() + "\n";
      }
      return output;
   }
}

class AuthorEntry implements Comparable<AuthorEntry>
{
   //fields
   private String name;
   private ArrayList<String> novels;
   
   //two constructors. argument n may be in lowercase. 
   public AuthorEntry(String n)
   {
      name = n;
      novels = new ArrayList<>();
   }
   public AuthorEntry(String n, String book)
   {
      name = n;
      this.addNovel(book);
   }
   
   /**  appends book to novels, but only if it is not already in that list.    
       */
   public void addNovel(String book)
   {

      if(novels == null){
         novels = new ArrayList<>();
      }

      boolean novelExists = false;
      for(int i = 0; i < novels.size(); i++){
         if(novels.get(i).equalsIgnoreCase(book)) {
            novelExists = true;
         }
      }
      if(!novelExists) {
         novels.add(book);
      }


   }
   
   /** two standard accessor methods  */
   
   public String getName() {
      return this.name.toUpperCase();
   }

   public ArrayList<String> getNovels() {
      return this.novels;
   }

   // public String getNovels() {
   //    return "[" + novelsToString() + "]";
   // }
        
   /**  pre:  name is not an empty string.  novels might be an empty ArrayList.
       uses:  either a for-each loop or an iterator
       post:  returns a string representation of this AuthorEntry in the format as 
              shown on each line of the output file.  
     */
   public String toString()
   {
      return getName() + ((this.novels.size() > 0)?": " + novelsToString():"");
   }

   private String novelsToString() {

      if(this.novels == null) {
         return "";
      }

      String novels = "";
      for(int i = 0; i < this.novels.size(); i++){
         if(i > 0) {
            novels += ", ";
         }
         novels += this.novels.get(i);
      }
      return novels;
   }

   @Override
   public int compareTo(AuthorEntry o){
      return getName().compareToIgnoreCase(o.name);
      
   }

}


/***************************************
     Extension    
     use this header to implement AuthorEntry a different way
***************************************************/
// class AuthorEntryExt extends ArrayList<String> implements Comparable<AuthorEntryExt>
// {
// }


/**********************  SAMPLE RUN  ********************************
 name: AAA
 novels: []
 toString(): AAA
 name: BBB
 novels: [y, z, x]
 toString(): BBB: y, z, x
 1
 -1
 0
 
 Enter input file name: infile2
 Done.
 
 **********************************************************/
   /******** Output file for infile2:
   
   DOSTOEVSKI: Crime and Punishment, The Possessed, The Brothers Karamazov, The Grand Inquisitor
   FLAUBERT: Madame Bovary, A Simple Heart, Memoirs of a Madman, Sentimental Education
   STENDHAL: The Red and the Black
   TOLSTOY: Anna Karenina, War and Peace, The Death of Ivan Illyich, The Kreutzer Sonata
   
    */