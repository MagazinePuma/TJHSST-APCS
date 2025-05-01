// name: Dennis Tislin
// date: 3/9

import java.io.*;
import java.util.*;

public class AuthorsNovelsMap
{
   public static void main(String[] args) throws IOException
   {
      Scanner keyboard = new Scanner(System.in);
      System.out.print("\nEnter input file name: ");
      String inFileName = keyboard.nextLine().trim()+".txt";
      Scanner inputFile = new Scanner(new File(inFileName));
      //System.out.print("\nEnter output file name: ");
      //String outFileName = keyboard.nextLine().trim();
   
      AuthorsMap authors = readAndMakeTheList(inputFile);
      PrintWriter outputFile = new PrintWriter(new FileWriter("authorsNovelsOut.txt"));
      outputFile.println( authors.toString() );
      inputFile.close(); 						
      outputFile.close();
      System.out.println("File created.");
   }
   
   public static AuthorsMap readAndMakeTheList(Scanner inputFile)
   {
      AuthorsMap theAuthors = new AuthorsMap();
      while(inputFile.hasNextLine())
      {
         theAuthors.readOneLine(inputFile.nextLine());
      }
      return theAuthors;
   }
}

class AuthorsMap extends TreeMap<String, Set<String>>
{
  /**   when you extend a class, the constructor is optional  **/

    
   /** extracts the author and book from oneLine.
       calls addAuthorOrNovel      
      */
   public void readOneLine(String oneLine) 
   { 
      String[] arr = oneLine.split(", ");
      String author = arr[0].toUpperCase();
      String book = arr[1];
      addAuthorOrNovel(author, book);
   }
   
   /**  either inserts a new Author mapping, or updates a previous Author mapping
        */
   public void addAuthorOrNovel(String name, String book)
   {
      name = name.toUpperCase();
      if(!this.containsKey(name)){
         TreeSet<String> set = new TreeSet<String>();
         set.add(book);
         this.put(name, set);
      } else {
         this.get(name).add(book);
      }
   }
   
   public String toString()
   {
      Set<String> authors = this.keySet();
      String ret = "";
      for(String author : authors){
         ret += author + ": ";
         Iterator<String> it = this.get(author).iterator();
         while(it.hasNext()){
            ret += it.next();
            if(it.hasNext()){
               ret += ", ";
            }
         }
         ret += "\n";
      }
      return ret;
   }
}
   

/**********************  SAMPLE RUN  ********************************
   /******** Output file for infile2:
   
   DOSTOEVSKI: Crime and Punishment, The Possessed, The Brothers Karamazov, The Grand Inquisitor
   FLAUBERT: Madame Bovary, A Simple Heart, Memoirs of a Madman, Sentimental Education
   STENDHAL: The Red and the Black
   TOLSTOY: Anna Karenina, War and Peace, The Death of Ivan Illyich, The Kreutzer Sonata
   
    **********************************/