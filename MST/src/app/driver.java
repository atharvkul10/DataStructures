package app;

import java.io.IOException;
import java.util.ArrayList;
import structures.Arc;
import structures.Graph;
import structures.PartialTree;

import java.util.Iterator;

public class driver {
	public static void main(String[] args) {

		ArrayList<String> fileNames = new ArrayList<String>();
		fileNames.add("graph1.txt");
		fileNames.add("graph2.txt");

		for (String fileName : fileNames) {
			System.out.println("Now testing file: " + fileName);
			Graph testGraph = null;
			try { 
				testGraph = new Graph(fileNames);
				            }
			            catch (Exception e) {
				System.out.println("Took an L on file-reading");
			}

			PartialTreeList pt1 = PartialTreeList.initialize(testGraph);

			Iterator<PartialTree> iter = pt1.iterator();
			while (iter.hasNext()) {
				System.out.println(iter.next());
			}
			System.out.println("MST: " + PartialTreeList.execute(pt1) + "\n\n\n");
			            
			        }
		    }
}