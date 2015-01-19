import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import org.jdom2.*;
import org.jdom2.input.SAXBuilder;


public class ReadXML {

public static int i;
public static software s = new software(null, null, null, null);


	public static void main(String[] args) {
		compare();
	}
	
	private static ArrayList<software> compare() {
		
		SAXBuilder builder = new SAXBuilder();
		
		Document readDoc = null;
		try {
			readDoc =  builder.build(new File("/usr/share/example-content/Interface/software.xml"));
		}
		catch(JDOMException | IOException e) {
			e.printStackTrace();
		}
		
		Element root = readDoc.getRootElement();
		ArrayList<software> soft = new ArrayList<software>();
		
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
			
			soft.add(i, s);
			System.out.println(s.name);
			System.out.println(s.category);
			System.out.println(s.level);
			System.out.println(s.description);
			System.out.println("\n");
		
			i++;
			
		}
		return soft;
		
	}

}
