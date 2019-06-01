package database.files;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Reader;
import java.io.Writer;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;


import database.DataMangementDB;
import model.User;

public class userDB implements DataMangementDB<User>
{	
	// search for a relational DB (like postgres)
	// READ about ORM - Hibernate in java
	// Check if hibernate supports postgres
	String fileName = "C:\\eclipse\\users.txt";
	
	/**the method gets an user object, checks (by using read method) if the user already exist- if it does, the method returns false.
	if it does'nt , the method write the user fields to the end of the file, add a new line, flushes, closeS the file and return true.
	in case of IO exception, the method prints the reason for the exception and returns false.*/
	@Override
	public boolean save(User obj) 
	{
		User user = obj;
		if(!read(user)) {
			try {
				FileWriter out = new FileWriter(fileName,true);
				BufferedWriter writerToUserFile= new BufferedWriter(out);
				writerToUserFile.write(user.getEmail() + "," + user.getName() + "," + user.getPassword());
				writerToUserFile.write("\n");
				writerToUserFile.flush();
				writerToUserFile.close();
				return true;
			}
			catch(IOException e)
			{
				System.out.println("save failed. reason:" + e.getMessage());
				return false;
			}
		}
		System.out.println("user already exist");
		return false;
	}
	
	/**the method get array of users, for each user, the method calls save method, if save method returned false, the method stops and returns false,
 	if all the users successfully saved, the method returns true  */
	@Override
	public boolean saveArray(User[] obj) {
		User []users= obj;
		for(int i=0;i<users.length;i++)
		{
			if(save(users[i])==false)
			{
				return false;
			}

		}	
		return true;
	}
	
	/**the method gets user object. if the user exist, the user is printed and the method returns true.
	if it doesn't the method returns false.
	in case of IO exception, the method  prints the reason for the exception and returns false.*/
	@Override
	public boolean read(User obj) {
		try {
			User user = obj;
			FileReader fr=new FileReader(fileName);    
			BufferedReader r= new BufferedReader(fr);
			String line=r.readLine(); //temporary, checking if this is the line we wish to print.
			while(line!=null){
				String [] fields =line.split(",");
				if(fields[0].equals(user.getEmail())) //compares fields[0] (Email from file) to user Email
				{
					System.out.println(line);
					return true;
				}
				line=r.readLine();
			}
		}
		catch(IOException e)
		{
			System.out.println("read failed. reason:" + e.getMessage());
			return false;
		}
		return false;
	}
	
	/**the method reads the text by using BufferedReader r.
	as long as line contain text, line will be printed.
	When line is null (it means the method finished reviewing the entire text file) the method returns true.
	in case of IO exception, the method prints the reason for the exception and returns false.*/
	@Override
	public boolean readAll() {
		try {
			FileReader fr=new FileReader(fileName);    
			BufferedReader r= new BufferedReader(fr);
			String line=r.readLine(); //temporary, holds a single text line.
			while(line!=null){
				System.out.println(line);
				line=r.readLine();
			}

		}
		catch(IOException e)
		{
			System.out.println("readAll failed. reason:" + e.getMessage());
			return false;
		}
		return true;
	}
	
	
	/**the method get user object, if the user is not exist, the method returns false
	 if id does, it changes the name and the password according to the input and returns true.
	in case of IO exception, the method  prints the reason for the exception and returns false.*/
@Override
	public boolean update(User obj) {
		try {
			User user= obj;
			if(!read(user)) //checks if the user exist
			{
				System.out.println("the user is not found");
				return false;
			}
			FileReader fr=new FileReader(fileName);    
			BufferedReader r= new BufferedReader(fr);
			String line=r.readLine(); //temporary, checking if this is the line we wish to update.
			String total=""; //temporary,holds the text that eventually will be save to the file.
			while(line!=null){
				/*if line contain the user we wish to update, line get the input user's parameters. 
			 	if total is empty, it means that this is the  first line of the text, then the line will be saved to total.
				if total is not empty, it means that this is not the  first line of the text, then the line will be saved to total variable
				after adding a new line. 
				*/
				String [] fields =line.split(",");
				if(fields[0].equals(user.getEmail())) //compares fields[0] (Email from file) to user Email
				{	
					line=fields[0]+","+user.getName()+","+user.getPassword(); 
				}
				if(total=="") { 
					total =line;
				}
				else {
					total=total+"\n"+line;
				}

				line=r.readLine();
			}
			BufferedWriter br= new BufferedWriter(new FileWriter (fileName));
			br.write(total);
			br.flush();
			br.close();
		}
		catch(IOException e)
		{
			System.out.println("update failed. reason:" + e.getMessage());
			return false;
		}
		return true;
	}

/**the method get user object, if the user is not exist, the method returns false
if id does, the method delete it and returns true.
in case of IO exception, the method  prints the reason for the exception and returns false.*/
	@Override
	public boolean delete(User obj) {
		try {
			User user = obj;
			FileReader fr=new FileReader(fileName);    
			BufferedReader r= new BufferedReader(fr);
			if(!read(user)) //checks if the user exist
			{
				System.out.println("the user is not found");
				return false;
			}
			String line=r.readLine();  //temporary, checking if this is the line we wish to delete.
			String total=""; //temporary, holds the text that eventually will be save to the file.
			while(line!=null){
				/*if line contain the user we wish to delete, line is not saved to total. 
				else, if total is empty, it means that this is the  first line of the text, then the line will be saved to total variable
				without adding a new line.
				if total is not empty, it means that this is not the  first line of the text, then the line will be saved to total variable
				after adding a new line. 
				 */
				String [] fields =line.split(",");
				if(fields[0].equals(user.getEmail()))//compares fields[0] (Email from file) to user Email
				{
					line=r.readLine(); 
				}

				else {
					if(total=="") 
					{
						total= fields[0] + "," + fields[1] + "," + fields[2];}
					else {
						total= total+ "\n" + fields[0] +  "," + fields[1] + "," + fields[2];
					}
					line=r.readLine();
				}
			}
			BufferedWriter br= new BufferedWriter(new FileWriter (fileName));
			br.write(total);
			br.flush();
			br.close();
		}
		catch(IOException e) {
			System.out.println("delete failed. reason:" + e.getMessage());
			return false;
		}
		return true;
	}		
	
	/**the method writes to the file an empty string instead of the existing text, flushes and closes the buffered and returns true, 
	in case of IO exception, the method prints the reason for the exception and returns false.*/
	@Override
	public boolean deleteAll() {
		try {
			BufferedWriter br= new BufferedWriter(new FileWriter (fileName));
			br.write("");
			br.flush();
			br.close();
		}
		catch(IOException e) {
			System.out.println("deleteAll failed. reason:" + e.getMessage());
			return false;
		}
		return true;
	}

}
