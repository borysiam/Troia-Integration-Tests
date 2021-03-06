package test.java.gal.integration.helpers;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.Map;
import java.util.Scanner;
import java.util.Set;
import java.util.StringTokenizer;
import com.datascience.gal.MisclassificationCost;

public class FileReaders {
	
	public FileReaders(){
		
	}
	
	/**
	 * Loads the misclassification cost data
	 * @param filename
	 * @throws FileNotFoundException
	 */
	public Set<MisclassificationCost> loadMisclassificationCostData(String filename)
	throws FileNotFoundException{
		
		Set<MisclassificationCost> misclassificationCosts = new HashSet<MisclassificationCost>();
		FileInputStream stream = new FileInputStream(filename);
		Scanner scanner = new Scanner(stream);
		String line, categoryFrom, categoryTo;
		double cost;
		
		while (scanner.hasNextLine()) {
			line = scanner.nextLine();
			StringTokenizer st = new StringTokenizer(line, "\t");
			categoryFrom = st.nextToken();
			categoryTo = st.nextToken();
			cost = Double.valueOf(st.nextToken());
			misclassificationCosts.add(new MisclassificationCost(categoryFrom, categoryTo, cost));
		}
		return misclassificationCosts;		
	}
	
	/**
	 * Loads the summary.txt file with formatting <Worker><tabulation><Errors><tabulation><Quality><tabulation><Submissions>
	 * @param filename 
	 * 			Name of file containing the expected worker summary
	 * @return LinkedList that contains the summary of the quality reports
	 */
	public LinkedList <Map<String, Object>> loadWorkerSummaryFile(String filename) 
	throws FileNotFoundException {
	
		LinkedList <Map<String, Object>> workerSummaries = new LinkedList<Map<String, Object>>();
		FileInputStream stream = new FileInputStream(filename);
		Scanner scanner = new Scanner(stream);
		String line, workerName, errorRate, quality, submissions;

		//scan the first line (containing the titles) - don't add it into the results list
		line = scanner.nextLine();
		while (scanner.hasNextLine()) {
			line = scanner.nextLine();
			StringTokenizer st = new StringTokenizer(line, "\t");
			workerName = st.nextToken();
			errorRate = st.nextToken();
			quality = st.nextToken();
			submissions = st.nextToken();
			
			Map<String, Object> workerSummary = new HashMap<String, Object>();
			workerSummary.put("Worker", workerName);
			workerSummary.put("Error rate", errorRate);
			workerSummary.put("Quality (Expected)", quality);
			workerSummary.put("Number of  Annotations", submissions);
			
			workerSummaries.add(workerSummary);
		}
		
		scanner.close();
		return workerSummaries;
	}
}
