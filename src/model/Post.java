package model;

import java.sql.Timestamp;
import java.util.UUID;

public class Post {
	
	private UUID ID;
	private Timestamp timestamp;
	private User author;
	private String title;
	private String content;
	
	public Post(UUID ID, Timestamp timestamp, User author, String title, String content) {
		super();
		this.ID=ID;
		this.timestamp = timestamp;
		this.author = author;
		this.title = title;
		this.content = content;
	}
	

	public UUID getID() {
		return ID;
	}


	public void setID(UUID iD) {
		ID = iD;
	}


	public Timestamp getTimestamp() {
		return timestamp;
	}
	

	public void setTimestamp(Timestamp timestamp) {
		this.timestamp = timestamp;
	}
	

	public User getAuthor() {
		return author;
	}
	

	public void setAuthor(User author) {
		this.author = author;
	}
	

	public String getTitle() {
		return title;
	}
	

	public void setTitle(String title) {
		this.title = title;
	}
	

	public String getContent() {
		return content;
	}
	

	public void setContent(String content) {
		this.content = content;
	}
	


	@Override
	public String toString() {
		return "Post [timestamp=" + timestamp + ", author=" + author + ", title=" + title + ", content=" + content
				+ "]";
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Post other = (Post) obj;
		if (author == null) {
			if (other.author != null)
				return false;
		} else if (!author.equals(other.author))
		if (timestamp == null) {
			if (other.timestamp != null)
				return false;
		} else if (!timestamp.equals(other.timestamp))
			return false;
		return true;
	}

	
	
	
	
	
}
