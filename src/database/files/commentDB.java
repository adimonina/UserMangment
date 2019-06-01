package database.files;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

import database.DataMangementDB;
import model.Comment;
import model.Post;

public class commentDB implements DataMangementDB<Comment>{
	
	String fileName = "C:\\eclipse\\comments.txt";

	@Override
	public boolean save(Comment obj){ 	{
		Comment comment= obj;
		if(!read(comment)) {
			try {
				FileWriter out = new FileWriter(fileName,true);
				BufferedWriter writerTopostFile= new BufferedWriter(out);
				writerTopostFile.write(comment.getCommentID()+ "," +comment.getTimestamp() + "," + comment.getAuthor().getEmail() + "," + comment.getPost()+","+comment.getContent());
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
	}
	
	@Override

	public boolean saveArray(Comment[] obj) {
		Comment []comments= obj;
		for(int i=0;i<comments.length;i++)
		{
			if(save(comments[i])==false)
			{
				return false;
			}

		}	
		return true;
	}

	@Override
	public boolean read(Comment obj) {
		try {
			Comment comment = obj;
			FileReader fr=new FileReader(fileName);    
			BufferedReader r= new BufferedReader(fr);
			String line=r.readLine(); //temporary, checking if this is the line we wish to print.
			while(line!=null){
				String [] fields =line.split(",");
				//SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-DD HH:mm:ss.SSS");//convert timestamp to string
				//String postStringTimestamp  = dateFormat.format(post.getTimestamp());
				String commentID=comment.getCommentID().toString();
				if((fields[0].equals(commentID))) //compares fields[0] (Email from file) to user Email
				{
					System.out.println(line);
					return true;
				}
				line=r.readLine();
			}
		}
		catch(IOException e)
		{
			System.out.println("save failed. reason:" + e.getMessage());
			return false;
		}

		return false;
	}

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
			System.out.println("save failed. reason:" + e.getMessage());
			return false;
		}
		return true;
	}

	@Override
	public boolean update(Comment obj) {
		try {
			Comment comment= obj;
			if(!read(comment)) //checks if the post exist
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
				//SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");//convert timestamp to string
				//String postStringTimestamp  = dateFormat.format(post.getTimestamp());
				String commentID=comment.getCommentID().toString();
				if((fields[0].equals(commentID))) //compares fields[0] (Email from file) to user Email
				{	
					line=fields[0]+","+fields[1] +","+fields[2]+ "," + fields[3] + "," + comment.getContent();
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
			System.out.println("save failed. reason:" + e.getMessage());
			return false;
		}
		return true;
	}

	@Override
	public boolean delete(Comment obj) {
Comment comment = obj;
		
		try{
			FileReader fr=new FileReader(fileName);    
		BufferedReader r= new BufferedReader(fr);
		if(!read(comment)) //checks if the post exist
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
			String commentID=comment.getCommentID().toString();
			if((fields[0].equals(commentID))) //compares fields[0] (Email from file) to user Email				{
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

