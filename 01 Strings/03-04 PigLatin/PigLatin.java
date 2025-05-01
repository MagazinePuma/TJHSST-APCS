// Name: Dennis Tislin  
// Date: 9/17/23
import java.util.*;
import java.io.*;
public class PigLatin
{
   public static void main(String[] args) 
   {
      //part_1_using_pig();
      part_2_using_piglatenizeFile();
      
      /*  extension only    */
      String pigLatin = pig("What!?");
      System.out.print(pigLatin + "\t\t" + pigReverse(pigLatin));   //Yahwta!?
      pigLatin = pig("{(Hello!)}");
      System.out.print("\n" + pigLatin + "\t\t" + pigReverse(pigLatin)); //{(Yaholle!)}
      pigLatin = pig("\"McDonald???\"");
      System.out.println("\n" + pigLatin + "  " + pigReverse(pigLatin));//"YaDcmdlano???"
   }

   public static void part_1_using_pig()
   {
      Scanner sc = new Scanner(System.in); //input from the keyboard
      while(true)
      {
         System.out.print("\nWhat word? ");
         String s = sc.next();     //reads up to white space
         if(s.equals("-1"))
         {
            System.out.println("Goodbye!"); 
            System.exit(0);
         }
         String p = pig(s);
         System.out.println( p );
      }		
   }

   public static final String punct = ",./;:'\"?<>[]{}|`~!@#$%^&*()";
   public static final String letters = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz";
   public static final String vowels = "AEIOUYaeiouy";
   public static String pig(String s)
   {
      if(s.length() == 0) {//check for string length
         return "";
      }
   
      //remove and store the beginning punctuation 
      String beginningPunctuation = "";
      while(s.length() > 0 && punct.contains(Character.toString(s.charAt(0)))) {
         beginningPunctuation += s.charAt(0);
         s = s.substring(1);
      }
           
      //remove and store the ending punctuation 
      String endingPunctuation = "";
      while(s.length() > 0 && punct.contains(Character.toString(s.charAt(s.length() - 1)))) {
         endingPunctuation = s.charAt(s.length() - 1) + endingPunctuation;
         s = s.substring(0, s.length() - 1);
      }
 
      //START HERE with the basic case:
      //     find the index of the first vowel
      //     y is a vowel if it is not the first letter
      //     qu
      int firstVowelIndex = -1;
      int count = 0;
      boolean vowelFound = false;

      while(count < s.length() && !vowelFound) {
         if(count == 0 && s.charAt(0) == 'y') {
         } else if(count > 0 && s.charAt(count) == 'u' && s.charAt(count - 1) == 'q') {
         } else if(vowels.contains(Character.toString(s.charAt(count)))) {
            firstVowelIndex = count;
            vowelFound = true;
         }
         count++;

      }

      //if no vowel has been found
      if(firstVowelIndex == -1) {
         return "**** NO VOWEL ****";
      }

      if (firstVowelIndex == 0) {
         return s + "way";
      }

      String beforeVowel = s.substring(0, firstVowelIndex);
      String firstVowelAndAfter = s.substring(firstVowelIndex);

      if(letters.indexOf(s.charAt(0)) < 26) {
         firstVowelAndAfter = ("" + firstVowelAndAfter.charAt(0)).toUpperCase() + firstVowelAndAfter.substring(1);
         beforeVowel = ("" + beforeVowel.charAt(0)).toLowerCase() + beforeVowel.substring(1);
      }

      String result = beginningPunctuation + firstVowelAndAfter + beforeVowel + "ay" + endingPunctuation;


/*
      for (int i = 0; i < s.length(); i++) {
         if (s.charAt(i) == 'q' && i + 1 < s.length() && s.charAt(i+1) == 'u') {
            i++;
            continue;
         }

         if (s.charAt(i) == 'y' || vowels.contains(String.valueOf(s.charAt(i)))) {
            String answer = s.substring(i + 1, s.length() - 1) + s.substring(0, 1).toLowerCase() + s.substring(1, i) + "ay";

            if (letters.substring(0, 26).contains("" + s.charAt(0))) {
               return answer.substring(0, 1).toUpperCase() + answer.substring(1,answer.length());
            }

            return answer;
         }
         }
      }
*/
           
      
      //is the first letter capitalized?
      
      
      //return the piglatinized word 
      return result;
      
      
   }


   public static void part_2_using_piglatenizeFile() 
   {
      Scanner sc = new Scanner(System.in);
      System.out.print("input filename including .txt: ");
      String fileNameIn = sc.next();
      System.out.print("output filename including .txt: ");
      String fileNameOut = sc.next();
      piglatenizeFile( fileNameIn, fileNameOut );
      System.out.println("Piglatin done!");
   }

/****************************** 
*  piglatinizes each word in each line of the input file
*    precondition:  both fileNames include .txt
*    postcondition:  output a piglatinized .txt file 
******************************/
   public static void piglatenizeFile(String fileNameIn, String fileNameOut) 
   {
      Scanner infile = null;
      try
      {
         infile = new Scanner(new File(fileNameIn));
      }
      catch(IOException e)
      {
         System.out.println("oops");
         System.exit(0);   
      }
   
      PrintWriter outfile = null;
      try
      {
         outfile = new PrintWriter(new FileWriter(fileNameOut));
      }
      catch(IOException e)
      {
         System.out.println("File not created");
         System.exit(0);
      }
   	//process each word in each line
      while (infile.hasNextLine()){
         String line = infile.nextLine();
         //System.out.println(line);

         String delim = " ";
         StringTokenizer str = new StringTokenizer(line, delim, true);
         while (str.hasMoreTokens()){
            String word = str.nextToken();
            if(word.equals(delim)) {
               outfile.print(delim);
            } else {
               String pigWord = pig(word);
               outfile.print(pigWord);
            }
         }
         outfile.println();
      }
      
      
   
      outfile.close();
      infile.close();
   }
   
   /** EXTENSION: Output each PigLatin word in reverse, preserving before-and-after 
       punctuation.  
   */
   public static String pigReverse(String s)
   {
      if(s.length() == 0)
         return "";
         
      return "";   //just to compile   
   }
}
