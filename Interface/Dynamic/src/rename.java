import java.io.File;

public class rename {
	
	public static File directory;
	public static String directoryName = "/home/camaraadmin/Desktop/Interface/images/screenshots/";
	public static File file;
	public static String fileName;
	
	public static void main(String[] args) {
		
	}
	
	public static void split() {
		
		String name = "Libreoffice-base";
		
		String[] names = name.split("-");
		names[0].replaceAll("Libreoffice", "LibreOffice");
		String upper = Character.toUpperCase(names[1].charAt(0)) + names[1].substring(1);
		names[1] = upper;
		
		String newName = names[0].replaceAll("Libreoffice", "LibreOffice") + " " + names[1];
		
		System.out.println(newName);
		
	/*
	//TODO Check if directory exists
	File directory = new File(directoryName);
	
	if(!directory.isDirectory()) {
		System.out.println("Directory does not exist");
		System.out.println("Creating directory" + directoryName);
		directory.mkdir();
	}
	else
		System.out.println("Directory exists!!!");
	
	
	//TODO Rename to lower case
	String[] contents = directory.list();
		System.out.println(contents);
		
	for (int i = 0; i < contents.length; i++) {
		String fileName = contents[i];
		File oldFile = new File(fileName);
		
		String lowerCase = fileName.toLowerCase();
		File newFile = new File(lowerCase);
		oldFile.renameTo(newFile);
		
		System.out.println(oldFile);
		System.out.println(newFile);
		
		
		}
	
	*/

	
	
	}
}
