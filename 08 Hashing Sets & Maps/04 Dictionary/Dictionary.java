// Name: Dennis Tislin
// Date: 3/11

import java.io.*;
import java.util.*;

public class Dictionary
{
   public static void main(String[] args) 
   {
      Scanner infile = null;
      PrintWriter outputFile = null;
      try
      {
         infile = new Scanner(new File("spanglish.txt"));
         outputFile = new PrintWriter(new FileWriter("dictionaryOutput.txt"));
      }
      catch(Exception e)
      {
         System.out.println( e );
      }
      
      Map<String, Set<String>> eng2spn = makeDictionary( infile );
      outputFile.println("ENGLISH TO SPANISH");
      outputFile.println(display(eng2spn));
   
      Map<String, Set<String>>spn2eng = reverse(eng2spn);
      outputFile.println("SPANISH TO ENGLISH");
      outputFile.println(display(spn2eng));
      outputFile.close();
      
      System.out.println("File created.");
   }
   
   public static Map<String, Set<String>> makeDictionary(Scanner infile)
   {
		Map<String, Set<String>> diction = new TreeMap<String, Set<String>>();
		while(infile.hasNextLine())
		{
			String nextWord = infile.nextLine();
			String secondWord = null;
			if(infile.hasNextLine()) {
				secondWord = infile.nextLine();
			}
			add(diction, nextWord, secondWord);	
		}
		return diction;
   }
   
   public static void add(Map<String, Set<String>> dictionary, String word, String translation)
   {
		Set set = dictionary.get(word);
		if(set == null){
		   set = new TreeSet<String>();
		   set.add(translation);
		   dictionary.put(word, set);
		} else {
		   set.add(translation);
		}

		// if(!diction.containsKey(value)){
			// 	TreeSet<String> set = new TreeSet<String>();
			// 	set.add(key);
			// 	diction.put(value, set);
			// } else {
			// 	diction.get(value).add(key);
			// } 
   }
   
   public static String display(Map<String, Set<String>> m)
   {
		Set<String> authors = m.keySet();
		String ret = "";
		for(String author : authors){
			ret += author + ": [";
			Iterator<String> it = m.get(author).iterator();
			while(it.hasNext()){
				ret += it.next();
				if(it.hasNext()){
					ret += ", ";
				}
			}
			ret += "]";
			ret += "\n";
		}
		return ret;
   }
   
   public static Map<String, Set<String>> reverse(Map<String, Set<String>> dictionary)
   {
		Map<String, Set<String>> diction = new TreeMap<String, Set<String>>();
		Set<String> keys = dictionary.keySet();
		for(String key : keys){
			for(String value : dictionary.get(key)){
				add(diction, value, key);
			}
		}
		return diction;
   }
}
			// if(!diction.containsKey(value)){
				// 	TreeSet<String> set = new TreeSet<String>();
				// 	set.add(key);
				// 	diction.put(value, set);
				// } else {
				// 	diction.get(value).add(key);
			// }


   /********************
	FILE INPUT:
   	holiday
		fiesta
		holiday
		vacaciones
		party
		fiesta
		celebration
		fiesta
     <etc.>
  *********************************** 
	FILE OUTPUT:
		ENGLISH TO SPANISH
			banana [banana]
			celebration [fiesta]
			computer [computadora, ordenador]
			double [doblar, doble, duplicar]
			father [padre]
			feast [fiesta]
			good [bueno]
			hand [mano]
			hello [hola]
			holiday [fiesta, vacaciones]
			party [fiesta]
			plaza [plaza]
			priest [padre]
			program [programa, programar]
			sleep [dormir]
			son [hijo]
			sun [sol]
			vacation [vacaciones]

		SPANISH TO ENGLISH
			banana [banana]
			bueno [good]
			computadora [computer]
			doblar [double]
			doble [double]
			dormir [sleep]
			duplicar [double]
			fiesta [celebration, feast, holiday, party]
			hijo [son]
			hola [hello]
			mano [hand]
			ordenador [computer]
			padre [father, priest]
			plaza [plaza]
			programa [program]
			programar [program]
			sol [sun]
			vacaciones [holiday, vacation]

**********************/