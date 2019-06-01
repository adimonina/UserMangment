package database.files;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;

import model.Post;
import model.User;

public class mainFiles {
	
	public static void main(String[] args) throws IOException {
		Path path = Paths.get( "C:\\eclipse\\users.txt");
		
		File file = new File( "C:\\eclipse\\users.txt");
		file.createNewFile();
		User firstUser= new User("adimonina@gmail.com" , "123456" , "adi");
		User secondUser= new User("liorsukman@gmail.com" , "123456" , "lior");
		User thirdUser= new User("galmonina@gmail.com" , "abcd" , "gal");
		userDB u = new userDB();
		u.save(firstUser);
		u.save(secondUser);
		//u.save(thirdUser);
		//u.update(firstUser);
		//u.read(firstUser);
		//u.delete(secondUser);
		//System.out.println("\n"+"*");
		//u.readAll();
		//u.deleteAll();
		//User[] userarray=new User[3];
		//userarray[0]=firstUser;
		//userarray[1]=secondUser;
		//userarray[2]=thirdUser;
		//u.saveArray(userarray);
		//DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
		Date date = new Date();
		Timestamp trail = new Timestamp(date.getTime());
		
		// GUID
		UUID Temp = UUID.randomUUID();
		Post firstPost= new Post (Temp,trail, firstUser, "Title" , "content");
		postDB p=new postDB();
		p.save(firstPost);
		p.read(firstPost);
	}

}
