package lse;
import java.io.FileNotFoundException;
public class Driver {

	public static void main(String[] args) throws FileNotFoundException{
		LittleSearchEngine test = new LittleSearchEngine();
		String noise = "noisewords.txt";
		String docs = "docs.txt";
		test.makeIndex(docs, noise);
		System.out.println("WaTer.!:;b,???? : " + test.getKeyword("WaTer.!:;b,????"));
		System.out.println("sWord : " + test.getKeyword("sWord"));
		System.out.println("paraphrase; : " + test.getKeyword("paraphrase;"));
		System.out.println("really?!?! : " + test.getKeyword("really?!?!"));
		System.out.println("Between, : " + test.getKeyword("Between,"));
		System.out.println("either:or : " + test.getKeyword("either:or"));
		
		System.out.println(test.keywordsIndex);
		System.out.println(test.keywordsIndex.get("curious"));
		System.out.println(test.keywordsIndex.get("forgot"));
		System.out.println(test.top5search("curious","forgot"));
	}

}
/*package search;

import java.io.*;
import java.util.*;

public class Driver 
{
	static Scanner sc = new Scanner(System.in);
	
	static String getOption() 
	{
		System.out.print("getKeyWord(): ");
		String response = sc.next();
		return response;
	}
	
	public static void main(String args[])
	{
		LittleSearchEngine lse = new LittleSearchEngine();
		
		try
		{
			lse.makeIndex("docs.txt", "noisewords.txt");
		} 
		catch (FileNotFoundException e)
		{
		}		
		
//		String input;
//
		for (String hi : lse.keywordsIndex.keySet())
			System.out.println(hi+" "+lse.keywordsIndex.get(hi));
//				
//		while (!(input = getOption()).equals("q"))
//		{
//				System.out.println(lse.getKeyWord(input));
//		}
		
		System.out.println(lse.top5search("deep", "world"));
	}
}
*/