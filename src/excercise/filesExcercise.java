package excercise;

import java.io.BufferedWriter;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;

public class filesExcercise {
	static int name = 2;
	
	public static void main(String[] args) {
		
		filesExcercise.writeOut("start");
		filesExcercise.writeOut("mid");
		filesExcercise.writeOut("end");
		
	}
	
	static void writeOut(String text) {
		try{ 
			 Path path = Paths.get( "C:\\eclipse\\newfile.txt");
			 BufferedWriter out = Files.newBufferedWriter(path, StandardOpenOption.APPEND);
			 out.append(text + "\n");
			 out.append(text + "!\n");
			 out.flush();
			 out.close();
			 System.out.println("writing " + text);
		  }catch (Exception e){
			 System.err.println("Error while writing to file: " + e.getMessage());
		  }
	}
	
	
}
