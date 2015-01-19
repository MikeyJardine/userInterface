import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;

import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.JDOMException;
import org.jdom2.input.SAXBuilder;

/** 
 * @author Michael Jardine
 * email michael.jardine2@mydit.ie 
 */
public class getPrograms {
	
	public static int count;
	public static String line;
	public static int matches;
	public static ArrayList<String> installed;
	public static ArrayList<String> software;
	public static ArrayList<String> soft;
	public static HashSet<String> miss;
	public static ArrayList<software> match;
	
	public static void main(String[] args) {
		
		match = new ArrayList<software>();
		
		try {
			compare(software, installed, match);
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		try {
			generateHTML(match);
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		try {
			links.getLinks();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		try {
			categories(match);
			categoriesAndLevel(match);
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		
	}
	
	//Gets a list of programs installed on the computer
	public static ArrayList<String> getInstalled(ArrayList<String> installed2) throws IOException {
		
			//Get a list of all installed programs on the computer
			String[] cmdArray = {"/bin/sh", "-c", "dpkg --get-selections"};
	        Runtime run = Runtime.getRuntime();
	        ProcessBuilder pb = new ProcessBuilder(cmdArray);
	        pb.redirectErrorStream(true);
	        Process p = pb.start();
	        
	    	BufferedReader stdInput = new BufferedReader(new InputStreamReader(p.getInputStream()));
	    	
	    	ArrayList<String> installed = new ArrayList<String>();
	    	int i = 0;
	    	
	    		//Populate array list with program names
		    	while((line = stdInput.readLine()) != null) {
		    		
		    		installed.add(i, line.split("\\s", 2)[0]);//Format string to only print program name
		    		
		    		//System.out.println(i + ": " + installed.get(i));
		    		i++;
			    	}
	
		    p.destroy();
		    
		return installed;
	}
	
	//Reads XML file & populates a list of educational software programs
	public static ArrayList<software> getSoftwareList(ArrayList<String> software2) {
		
		SAXBuilder builder = new SAXBuilder();
		
		Document readDoc = null;
		try {
			readDoc =  builder.build(new File("/usr/share/example-content/Interface/software.xml"));
		}
		catch(JDOMException | IOException e) {
			e.printStackTrace();
		}
		
		Element root = readDoc.getRootElement();
		ArrayList<software> softList = new ArrayList<software>();
		
		for (Element curEle : root.getChildren("software")) {
			
			int i = 0;
			String n = curEle.getChildText("name");
			String c = curEle.getChildText("category");
			String d = curEle.getChildText("description"); 
			String l = curEle.getChildText("level");
			
			software s = new software(n, c, d, l);
			
			s.name = n;
			s.category = c;
			s.description = d;
			s.level = l;
			
			softList.add(i, s);
			i++;
		
		}
		System.out.println("softList" + softList);
		return softList;
	}
	
	/*Searches the installed list for educational software packages & generates HTML pages
	for each match found*/
	public static ArrayList<software> compare(ArrayList<String> software, ArrayList<String> installed, ArrayList<software> mtch) throws IOException {
		
		ArrayList<software> soft = new ArrayList<software>();
		soft = getSoftwareList(software);
		
		ArrayList<String> inst = new ArrayList<String>();
		inst = getInstalled(installed);
		
		//for loop to iterate through educational software
		int count = 0;
		int matches = 0;
		
		for (int i = 0; i < soft.size(); i++) {		
		//Nested for loop to iterate through installed programs
			
			innerloop:
			for(int k = 0; k < inst.size(); k++) {
				String installedName = inst.get(k).toLowerCase().replaceAll("-", "").trim();
				String softName = soft.get(count).name.toLowerCase().replaceAll("-", "").trim();
				
				if (installedName.contains(softName) || softName.contains(installedName)){
					System.out.println(k + ": " + "Match for " + soft.get(count).name );
					matches++;
					match.add(soft.get(count));
					//Generate HTML
					break innerloop;
					}
				}		
			count++;
		
			}
		//Check for missing software
		int missing = soft.size() - match.size();
		
		if (missing > 0) {
			HashSet<String> miss = new HashSet<String>();
			int iterate = 0;
			for(int i = 0; i < match.size(); i++){
				
				innerloop:
					for(int k = 0; k < soft.size(); k++) {
						String m = match.get(iterate).name.toLowerCase().replaceAll("-", "").trim();
						String s = soft.get(k).name.toLowerCase().replaceAll("-", "").trim();
						
						if(!m.contains(s) || !s.contains(m)) {
							miss.add(soft.get(k).name);
							break innerloop;
							
						}
						iterate++;
					}
				
				}
			System.out.println(miss);
		}
		
		System.out.println(inst);
		System.out.println("Number of installed programs: " + inst.size());
		System.out.println("Matches: " + matches + " out of " + soft.size());
		
		for(int i = 0; i < match.size(); i++){
			System.out.println(i + " Matches: " + match.get(i).name);
		}
		if(missing > 0){
			System.out.println("Missing: " + missing + miss);
		}
		else 
			System.out.println("There are no programs missing");
		
		return match;
				
		}
	//Generates a HTML page for every object in the match list
	public static void generateHTML(ArrayList<software> mtch) throws IOException {
	
		System.out.println("Generating HTML files");
		
		for (int i = 0; i < match.size(); i++) {
			
			String name = match.get(i).name;
			String category = match.get(i).category;
			String description = match.get(i).description;
			String level = match.get(i).level;
			
			//Check if any of the fields are null
			if (name == null || category == null || description == null || level == null) {
				System.out.println(name + " could not be found");
				break;
			}
			else if (match.get(i) != null) {
					
				html myHTML = new html(name, category, description, level);
				myHTML.run(name, category, description, level);
		
				System.out.println(i + " Page for: " + name + " has been generated");
			
			}
		}
		System.out.println("Finished generating HTML pages");
	} 
	
	public static void categories(ArrayList<software> m) throws IOException {
		HashSet<String> categoryList = new HashSet<String>();
		
		for(int i = 0; i < match.size(); i++) {
			String cat = match.get(i).category;
			categoryList.add(cat);	
		}
		
		Iterator it = categoryList.iterator();
		//for the current category, search match for software in that category, populate into separate lists
		
		for (int k = 0; k < categoryList.size(); k++) {
			String currentCat = (String) it.next();
			ArrayList<software> temp = new ArrayList<software>();
			System.out.println(currentCat + " " + k);
			
				//search for category & insert into a temporary list when match is found
				for(int j = 0; j < match.size(); j++) {
					
					if(currentCat.contains(match.get(j).category)) {
						temp.add(match.get(j));	
						//System.out.println("Adding " + match.get(j).name + " to " + currentCat);
					}
					
				}
				System.out.println("Editing page: " + currentCat);
				for(int i = 0; i < temp.size(); i++) {
					
					System.out.println(i + " Creating link for " + temp.get(i).name);
				}
					//pass temp list to Categories class to be run
					Categories.category(temp);
					Categories.createLink(temp);
					//then clear list to be so it can be populated the next set of matches
					temp.clear();
		}
	
		
	}
	
	//Organise software by category and geneate pages for each level and their category
	public static void categoriesAndLevel(ArrayList<software> m) throws IOException {
		HashSet<String> categoryList = new HashSet<String>();
		
		for(int i = 0; i < match.size(); i++) {
			String cat = match.get(i).category;
			categoryList.add(cat);	
		}
		
		Iterator it = categoryList.iterator();
		//for the current category, search match for software in that category, populate into separate lists
		
		for (int k = 0; k < categoryList.size(); k++) {
			String currentCat = (String) it.next();
			ArrayList<software> temp = new ArrayList<software>();
			System.out.println(currentCat + k);
			
				//search for category & insert into a temporary list when match is found
				for(int j = 0; j < match.size(); j++) {
					
					if(currentCat.contains(match.get(j).category)) {
						temp.add(match.get(j));	
						//System.out.println("Adding " + match.get(j).name + " to " + currentCat);
					}	
				}
				System.out.println("Editing page: " + currentCat);
				for(int i = 0; i < temp.size(); i++) {
					
					System.out.println(i + " Categories&Level method works " + temp.get(i).name + " " + temp.get(i).category);
				}
					//pass temp list to Categories class to be run
					Categories.level(temp);
					Categories.createLink(temp);
					//then clear list to be so it can be populated the next set of matches
		}
	}
	
	public static ArrayList<software> getMatch() {
		return match;
	}
	public void setMatch(ArrayList<software> mtch) {
		getPrograms.match = mtch;
	}
}