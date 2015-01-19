import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;


public class html {
	
	public static int count = 0;
	public static String name;
	public static String category;
	public static String description;
	public static String level;
	public String image = "images/illustrations/" + name + "/";
	
	//Constructor
	public html(String n, String c, String d, String l) {
		name = n;
		category = c;
		description = d;
		level = l;
	}
	
	public  void run(String name, String category, String description, String level) throws IOException {
		//Parses html file 
		String categoryLink = "../" + category.toLowerCase().replaceAll("\\s", "");
		
		File input = new File("/usr/share/example-content/Interface/blank.html");
		Document doc = Jsoup.parse(input, "UTF-8", "/usr/share/example-content/Interface/blank.html");
		
		Element title = doc.select("title").first();
		title.html("");
		title.append(name);
		
		//Append Screenshot
		File screenShotDir = new File("/usr/share/example-content/Interface/images/screenshots/");
		File [] screenshot = screenShotDir.listFiles();
		
		Element img = doc.select("img").last();
		//Check if screenshot exists
		boolean found = false;
		for (int i = 0; i < screenshot.length; i++) {
			
			if (screenshot[i].toString().contains(name)) {
				System.out.println("Screenshot for " + name + " found");
				found = true;
				break;
				}
			else {
				found = false;
			}
		}
				if(found = true) {
					img.attr("src", "../images/screenshots/" + name.toLowerCase() + ".png");
				}
				else
				img.attr("src", "../images/screenshots/" + "noscreenshot.png");
			
		
		//Append icon
		File iconDir = new File("/usr/share/example-content/Interface/images/applications/");
		File [] icons = iconDir.listFiles();
		
		Elements logo = doc.select("img[src$=tag1]");
		logo.html("");
		
		//Check if icon exists
		boolean foundicon = false;
		for (int i = 0; i < icons.length; i++) {
			
			if (icons[i].toString().contains(name)) {
				foundicon = true;
				break;
			}
			else {	
				foundicon = false;
			}	
		}
			if(foundicon = true) {
				logo.attr("src", "../images/applications/" + name.toLowerCase() + ".png");
			}
			else
				logo.attr("src", "../images/applications/" + "noicon.png");
		
		
		//Modify the bread crumb
			
		Element breadcrumbProg = doc.select("ul.breadcrumb > li").last();
		breadcrumbProg.append(name.replaceAll("-", " "));
		
		Element breadcrumbCat = doc.select("ul.breadcrumb > li > a[href]").last();
		breadcrumbCat.html("");
		breadcrumbCat.append(category);
		
		Element breadCrumbLink = doc.select("ul.breadcrumb > li > a").last();
		breadCrumbLink.attr("href", categoryLink + ".html");

		//Title
		Elements heading = doc.select("h1");
		heading.html("");
		heading.append(name.replaceAll("-", " "));
		
		//Description
		Elements text = doc.select("h3");
		text.html("");
		text.append(description);
		
		//Add link to open program
		String program = name;	//string to specify Ubuntu app
		//Element url = doc.select("a").last().attr("href", "app://" + name.toLowerCase().replaceAll("-", ""));
		Element url;
		if (name.contains("LibreOffice-")) {
			
			url = doc.select("a").last().attr("href", "app://" + name.replaceAll("-", " --").toLowerCase());
		}
		else if (name.contains("VLC")){
			url = doc.select("a").last().attr("href", "app://" + name.replaceAll("-", " ").toLowerCase());
		}
		else {
			 url = doc.select("a").last().attr("href", "app://" + name.toLowerCase().replaceAll("-", ""));
		}
		Element open = doc.select("a").last().attr("title", "Open") ;
		
		
		//Convert file to string to be read
		String html = doc.toString();
			
		//Writes the file to a specific folder
		File output = new File("/usr/share/example-content/Interface/software/" + name.toLowerCase() + ".html");
		    try {
					BufferedWriter bw = new BufferedWriter(new FileWriter(output.getAbsolutePath()));
					bw.write(html);
					bw.close();
					count++;
				} 
		    catch (IOException e) {
				e.printStackTrace();
			}
		
		//Print to show if method works
		//System.out.println(open);
		}
	
}