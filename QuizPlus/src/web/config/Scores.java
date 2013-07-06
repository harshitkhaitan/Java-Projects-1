package web.config;

import java.util.Date;

import quiz.Quiz;

public class Scores {

	private Quiz quiz;
	private User user;
	private int score;
	private Date date ;

	
	public Scores(int s) {
		this.score = s;
	}

	public void setScore(int score) {
		this.score = score;
	}
	
	public void setDateNow() {
		this.date = new Date();
	}

	@Override
	public String toString() {
		return "Scores [score=" + score + ", date=" + date + "]";
	}
	
}
