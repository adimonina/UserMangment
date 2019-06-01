package database.files;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import database.DataMangementDB;
import model.Post;
import model.User;

public class postDB implements DataMangementDB<Post> {

	String fileName = "C:\\eclipse\\posts.txt";
	
	/**the method gets an post object, checks (by using read method) if the post already exist- if it does, the method returns false.
	if it does'nt , the method write the post fields to the end of the file, add a new line, flushes, closes the file and return true.
	in case of IO exception, the method prints the reason for the exception and returns false.*/
	@Override
	public boolean save(Post obj) 
	{
		Post post = obj;
		if(!read(post)) {
			try {
				FileWriter out = new FileWriter(fileName,true);
				BufferedWriter writerTopostFile= new BufferedWriter(out);
				writerTopostFile.write(post.getID()+ "," +post.getTimestamp() + "," + post.getAuthor().getEmail() + "," + post.getTitle() + "," + post.getContent());
				writerTopostFile.write("\n");
				writerTopostFile.flush();
				writerTopostFile.close();
				return true;
			}
			catch(IOException e)
			{
				System.out.println("save failed. reason:" + e.getMessage());
				return false;
			}
		}
		System.out.println("post already exist");
		return false;
	}
	
	/**the method get array of posts, for each post, the method calls save method, if save method returned false, the method stops and
	 returns false, if all the posts successfully saved, the method returns true  */
	@Override
	public boolean saveArray(Post[] obj) {
		Post []posts= obj;
		for(int i=0;i<posts.length;i++)
		{
			if(save(posts[i])==false)
			{
				return false;
			}

		}	
		return true;
	}	

	/**the method gets post object. if the post exist, it is printed and the method returns true.
	if it doesn't the method returns false.
	in case of IO exception, the method  prints the reason for the exception and returns false.*/
	@Override
	public boolean read(Post obj) {
		
		try {
			Post post = obj;
			FileReader fr=new FileReader(fileName);    
			BufferedReader r= new BufferedReader(fr);
			String line=r.readLine(); //temporary, checking if this is the line we wish to print.
			while(line!=null){
				String [] fields =line.split(",");
				String postID=post.getID().toString();
				if((fields[0].equals(postID))) //compares fields[0] (postID from file) to post ID
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
	
	
	/**the method get post object, if the post is not exist, the method returns false
	 if id does, it changes the name and the password according to the input and returns true.
	in case of IO exception, the method  prints the reason for the exception and returns false.*/
@Override
	public boolean update(Post obj) {
		try {
			Post post= obj;
			if(!read(post)) //checks if the post exist
			{
				System.out.println("the post is not found");
				return false;
			}
			FileReader fr=new FileReader(fileName);    
			BufferedReader r= new BufferedReader(fr);
			String line=r.readLine(); //temporary, checking if this is the line we wish to update.
			String total=""; //temporary,holds the text that eventually will be save to the file.
			while(line!=null){
				/*if line contain the post we wish to update, line get the input post's parameters. 
			 	if total is empty, it means that this is the  first line of the text, then the line will be saved to total.
				if total is not empty, it means that this is not the  first line of the text, then the line will be saved to total variable
				after adding a new line. 
				*/
				String [] fields =line.split(",");
				String postID=post.getID().toString();
				if((fields[0].equals(postID))) //compares fields[0] (postID from file) to post ID
				{	
					line=fields[0]+","+fields[1] +","+fields[2]+ "," + post.getTitle() + "," + post.getContent();
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

/**the method get post object, if the post is not exist, the method returns false
if id does, the method delete it and returns true.
in case of IO exception, the method  prints the reason for the exception and returns false.*/
	@Override
	public boolean delete(Post obj) {
		try {
			Post post = obj;
			FileReader fr=new FileReader(fileName);    
			BufferedReader r= new BufferedReader(fr);
			if(!read(post)) //checks if the post exist
			{
				System.out.println("the post is not found");
				return false;
			}
			String line=r.readLine();  //temporary, checking if this is the line we wish to delete.
			String total=""; //temporary, holds the text that eventually will be save to the file.
			while(line!=null){
				/*if line contain the post we wish to delete, line is not saved to total. 
				else, if total is empty, it means that this is the  first line of the text, then the line will be saved to total variable
				without adding a new line.
				if total is not empty, it means that this is not the  first line of the text, then the line will be saved to total variable
				after adding a new line. 
				 */
				String [] fields =line.split(",");
				//SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");//convert timestamp to string
				//String postStringTimestamp  = dateFormat.format(post.getTimestamp());
				String postID=post.getID().toString();
				if((fields[0].equals(postID))&&(fields[2].equals(post.getAuthor().getEmail()))) //compares fields[0] (Email from file) to user Email				{
					line=r.readLine(); 
				

				else {
					if(total=="") 
					{
						total= fields[0] + "," + fields[1] + "," + fields[2] + "," + fields[3]+ "," + fields[4];}
					else {
						total= total+ "\n" + fields[0] +  "," + fields[1] + "," + fields[2] + "," + fields[3] + "," + fields[4];
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
			System.out.println("save failed. reason:" + e.getMessage());
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
			System.out.println("save failed. reason:" + e.getMessage());
			return false;
		}
		return true;
	}

}
