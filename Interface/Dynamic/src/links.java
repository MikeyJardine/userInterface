import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;


//Program to assign links to resources based on whether the exist or not
public class links {
	
	public static void getLinks() throws IOException {
		
	String[] name = {"wiki", "khan", "moodle"};
	String[] link = new String[3];
		link[0] = "http://localhost:50900/wikipedia_en_wp1_0.8_45000plus_12_2010/";
		link[1] = "http://localhost/khanacademy";//need to verify this
		link[2] = "http://localhost/moodle";
	
	String[] resourceFile = new String[3];
		resourceFile[0] = "/opt/kiwix/";
		resourceFile[1] = "/var/www/khanacademy/";//need to verify this
		resourceFile[2] = "/var/www/moodle/";
		
		for (int i = 0; i < name.length; i++) {
			File f = new File(resourceFile[i]);
			File input = new File("/home/camaraadmin/Desktop/Interface/resources/" + name[i] + ".html");
			Element url;
			Document doc = 
				Jsoup.parse(input, "UTF-8", "/home/camaraadmin/Desktop/Interface/resources/" + name[i] + ".html");
			
			//Assign link based on whether resource exists
			if(f.exists()) {
				url = doc.select("a").last().attr("href", link[i]);
			}
			else 
				url = doc.select("a").last().attr("href", "notfound.html");
			
			
			//Convert file to string to be read
			String html = doc.toString();
				
			//Writes the file to a specific folder
			File output = 
					new File("/home/camaraadmin/Desktop/Interface/resources/" + name[i] + ".html" );
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
