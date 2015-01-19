import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class Categories {
	//Instance variables
	public static String name;
	public static String category;
	public static String level;
	public static ArrayList<software> temp;
	//public static int count;
	
	//Constructor
	public Categories(ArrayList<software> t) {
		temp = t;
	}
	
	//Methods
	public static void level(ArrayList<software> temp) throws IOException {
	//input
		
		//iterate through list
		for(int i = 0; i < temp.size(); i++) {
			String name = temp.get(i).name;
			String category = temp.get(i).category;
			String level = temp.get(i).level;
				
			String link;
			String[] levels = level.split(" ");
			
			//For each level read through the html files
			for (int j = 0; j < levels.length; j++) {
				if(category.contains("Games") || category.contains("Resources") || category.contains("Media")) {
					link = "/usr/share/example-content/Interface/" + category.toLowerCase() + ".html";
				}
				else
					link = "/usr/share/example-content/Interface/" + levels[j].toLowerCase() + "/" + category.toLowerCase() + "-" + levels[j].toLowerCase() + ".html";
				
				
				File input = new File(link);
				Document doc = Jsoup.parse(input, "UTF-8", link);
				
				Elements table = doc.getElementsByAttribute("div[class=col-lg-2 col-md-3 col-sm-4 col-xs-6]");
				Iterator<Element> iterator = table.iterator();
				
				while(iterator.hasNext()) {
					//Do this for the number of elements on the html page
					Element title = doc.select("title").first();
					title.html("");
					title.append(category);
					
					Element linkProgram = table.select("a[href]").first();
					linkProgram.html("");
					linkProgram.append("../software/" + name + ".html");
					
					Element linkImage = table.select("img[src]").first();
					linkImage.html("");
					linkImage.append("../images/applications/" + name.toLowerCase() + ".png");
					
					Element footer = table.select("div[class=panel-footer]").first();
					footer.html("");
					footer.append(name);
				}
	//output
				//Convert file to string to be read
				String html = doc.toString();
					
				//Writes the file to a specific folder
				File output = new File(link);
				    try {
							BufferedWriter bw = new BufferedWriter(new FileWriter(output.getAbsolutePath()));
							bw.write(html);
							bw.close();
							
						} 
				    catch (IOException e) {
						e.printStackTrace();
					}
			}		
		}		
	}
	
	public static void category(ArrayList<software> t) throws IOException {
	//input
		//For each level read through the html files
		category = t.get(0).category.toLowerCase();
		String link = "/usr/share/example-content/Interface/" + category + ".html";
		File input = new File(link);
		Document doc = Jsoup.parse(input, "UTF-8", link);
		
		//iterate through list
		for(int i = 0; i < t.size(); i++) {
			String name = t.get(i).name;
			String category = t.get(i).category;	
			
			Elements table = doc.getElementsByAttribute("div[class=col-lg-2 col-md-3 col-sm-4 col-xs-6]");
			Iterator<Element> iterator = table.iterator();
			
			int count = 0;
		
				while(iterator.hasNext()) {
					System.out.println("While loop creating link for:"  + name);
					
					//Do this for the number of elements on the html page
					Element title = table.select("title").first();
					title.html("");
					title.append(category);
					
					Element linkProgram = table.select("a[href]").first();
					linkProgram.html("");
					linkProgram.append("../software/" + name + ".html");
					
					Element linkImage = table.select("img[src]").first();
					linkImage.html("");
					linkImage.append("../images/applications/" + name.toLowerCase() + ".png");
					
					Element footer = table.select("div[class=panel-footer]").first();
					footer.html("");
					footer.append(name);
					
					count++;
					System.out.println("The count is: " + count);
					
			
			//check if there is enough links, if there isn't, generate one
			if((!iterator.hasNext()) && t.size() > count) {
				System.out.println("Creating link that doesn't yet exist");
				
				//copy element method
				Element e = doc.select("div[class=col-lg-2 col-md-3 col-sm-4 col-xs-6]").first();
				Element copy = e.clone();
				
				System.out.println(copy);
				Element title1 = copy.select("title").first();
				title1.html("");
				title1.append(category);
				
				Element linkProgram1 = copy.select("a[href]").first();
				linkProgram1.html("");
				linkProgram1.append("../software/" + name + ".html");
				
				Element linkImage1 = copy.select("img[src]").first();
				linkImage1.html("");
				linkImage1.append("../images/applications/" + name.toLowerCase() + ".png");
				
				Element footer1 = copy.select("div[class=panel-footer]").first();
				footer1.html("");
				footer1.append(name);
				System.out.println(copy);
				
				count++;
			}
				
			else if(!(iterator.hasNext()) && t.size() == count) {
				break;
			}
			
			//check if there too many links
			else if(iterator.hasNext() && t.size() == count) {
				iterator.remove();
			}
				}//while loop
		//for
				
	//output
		//Convert file to string to be read
		String html = doc.toString();

		//Writes the file to a specific folder
		File output = new File(link);
		    try {
					BufferedWriter bw = new BufferedWriter(new FileWriter(output.getAbsolutePath()));
					bw.write(html);
					bw.close();
					
				} 
		    catch (IOException e) {
				e.printStackTrace();
				
		    }	
		}	
	}
	//TODO Method that generates link for program if one does not exist
	public static void createLink(ArrayList<software> temp) throws IOException {
	
		//input
		String blank = "/usr/share/example-content/Interface/blank-category.html";
		File input = new File(blank);
		Document doc = Jsoup.parse(input, "UTF-8", blank);
		String category = temp.get(0).category;
		
		Element e = doc.select("div[class=col-lg-2 col-md-3 col-sm-4 col-xs-6]").first();
		Element title = doc.select("title").first();
		title.append(category);
		
		Element row = doc.select("div[class=container] > div[class=row]").last();
		Element breadcrumb = doc.select("ul.breadcrumb > li").last();
		breadcrumb.append(category);
		
		Elements group = new Elements();
		//Modify the category page
		for(int i = 0; i < temp.size(); i++) {
			String name = temp.get(i).name;
			
			Element copy = e.clone();
			
			Element copyLink = copy.select("a").first();
			copyLink.attr("href", "../Interface/software/" + name.toLowerCase() + ".html");
			
			Element copyImage = copy.select("img").first();
			copyImage.attr("src", "../Interface//images/applications/" + name.toLowerCase() + ".png");
			
			Element copyFooter = copy.select("div[class=panel-footer]").first();
			copyFooter.append(name);
			
			System.out.println("Element " + name + " has been created");
			group.add(i, copy);
			
			row.appendChild(copy);
			
		}
		e.remove();
		System.out.println("Page for " + category );
		System.out.println(doc.toString());
		
	//output
		//Convert file to string to be read
			String link = "/usr/share/example-content/Interface/" + category.toLowerCase() + ".html";
			String html = doc.toString();

				//Writes the file to a specific folder
				File output = new File(link);
				    try {
							BufferedWriter bw = new BufferedWriter(new FileWriter(output.getAbsolutePath()));
							bw.write(html);
							bw.close();
							
						} 
				    catch (IOException e1) {
						e1.printStackTrace();
						
				  }	  
	}
	
}
