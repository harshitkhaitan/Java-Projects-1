package web.config;

import java.sql.Timestamp;

public class Message {

	private String from;
	private String to;
	private String text; 
	private String quizName;
	private int myScore;

	private String stringType;
	private int type;
	private int read;
	private int id;
	private Timestamp timestamp;
	public Message(String from, String to, String text, int type, int read, int id, Timestamp timestamp, String quizName, int myScore) {
		this.from = from;
		this.to = to;
		this.text = text;
		this.type = type;
		this.read = read;
		this.id = id;
		this.timestamp = timestamp;
		this.quizName = quizName;
		this.myScore = myScore;
		if(this.type == 1){
			stringType = "Friend Request";
		}else if (this.type == 2){
			stringType = "Challenge";
		}else{
			stringType = "Message";
		}
		
	}
	public String getFrom() {
		return from;
	};
	public String getTo() {
		return to;
	}
	public String getText() {
		return text;
	}
	public int getType() {
		return type;
	}
	public int getRead() {
		return read;
	}
	public int getId() {
		return id;
	}
	public Timestamp getTimestamp() {
		return timestamp;
	}
	public String getStringType() {
		return stringType;
	}
	public String getQuizName() {
		return quizName;
	}
	public int getMyScore() {
		return myScore;
	}
	
	
	
}
