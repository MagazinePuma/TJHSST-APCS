// Name:  Dennis Tislin
// Date: 9/19/23

public class Sentence {
   private String mySentence;
   private int myNumWords;

   // Precondition: str is not empty.
   // Words in str separated by exactly one blank.
   public Sentence(String str) {
      mySentence = str;
      myNumWords = mySentence.split(" ").length;
   }

   public int getNumWords() {
      return myNumWords;
   }

   public String getSentence() {
      return mySentence;
   }

   // Returns true if mySentence is a palindrome, false otherwise.
   // calls the 3-arg isPalindrome(String, int, int)
   public boolean isPalindrome() {

      mySentence = removeBlanks(mySentence);
      mySentence = lowerCase(mySentence);
      mySentence = removePunctuation(mySentence);

      if (mySentence.length() == 0){
         return false;
      }

      return isPalindrome(mySentence, 0, mySentence.length()-1);
   }

   // Precondition: s has no blanks, no punctuation, and is in lower case.
   // Recursive method.
   // Returns true if s is a palindrome, false otherwise.
   public static boolean isPalindrome(String s, int start, int end)
   {
      boolean isPalindrome = s.charAt(start) == s.charAt(end);
      if (end - start > 1 && isPalindrome){
         String nextString = s.substring(start + 1, end);
         isPalindrome = isPalindrome(nextString, 0, nextString.length()-1);
      }
      return isPalindrome;
      /*String startS = "";
      String endS = "";

      //if (s.length()%2 == 0){
      for (int i = start; i < s.length()/2; i++){
         startS += s.charAt(i);
      }

      for (int i = end; i > s.length()/2; i--){
         endS += s.charAt(i);
      }

      return startS.equalsIgnoreCase(endS);
   //}*/
   }

   // Returns copy of String s with all blanks removed.
   // Postcondition: Returned string contains just one word.
   public static String removeBlanks(String s) {
      //return null;
      return s.replace(" ", "");
   }

   // Returns copy of String s with all letters in lowercase.
   // Postcondition: Number of words in returned string equals
   // number of words in s.
   public static String lowerCase(String s) {
      return s.toLowerCase();
   }

   // Returns copy of String s with all punctuation removed.
   // Postcondition: Number of words in returned string equals
   // number of words in s.
   public static String removePunctuation(String s) {
      String punct = ".,'?!:;\"(){}[]<>";
      String result = "";
      //return null;
      for (int i = 0; i < s.length(); i++){
         if (!punct.contains("" + s.charAt(i))){
            result += s.charAt(i);
         }
      }
      return result;
}
}