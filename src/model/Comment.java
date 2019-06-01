package model;

import java.security.Timestamp;
import java.util.UUID;

public class Comment {
	
	private UUID commentID;
	private Timestamp timestamp;
	private User author;
	private Post post;
	private String content;
	
	public Comment(UUID ID, Timestamp timestamp, User author, Post post, String content) {
		super();
		this.commentID=ID;
		this.timestamp = timestamp;
		this.author = author;
		this.post = post;
		this.content = content;
	}

	public UUID getCommentID() {
		return commentID;
	}

	public void setCommentID(UUID commentID) {
		this.commentID = commentID;
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

	public Post getPost() {
		return post;
	}

	public void setPost(Post post) {
		this.post = post;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	

	@Override
	public String toString() {
		return "Comment [timestamp=" + timestamp + ", author=" + author + ", post=" + post + ", content=" + content
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
		Comment other = (Comment) obj;
		if (author == null) {
			if (other.author != null)
				return false;
		} else if (!author.equals(other.author))
			return false;
		if (timestamp == null) {
			if (other.timestamp != null)
				return false;
		} else if (!timestamp.equals(other.timestamp))
			return false;
		return true;
	}
	
	

}
