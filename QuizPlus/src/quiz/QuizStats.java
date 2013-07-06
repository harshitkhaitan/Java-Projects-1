package quiz;

import java.util.ArrayList;
import java.util.Date;
import java.util.concurrent.TimeUnit;

import dbAccess.DBAccess;
import dbAccess.DateAndTime;

/**
 * This holds the quiz stats 
 * holds the quizName 
 * holds the user who took the quiz
 * holds the date when the quiz was taken 
 * holds the time in milliseconds the user took to finish the quiz.
 * use  getTimeToCompleteStr() to get the time in mins.seconds:milliseconds format.
 * holds how the user scored in the quiz. 
 * 
 * @author jayakarr
 *
 */
public class QuizStats {
	String quizName;
	String userName;
	Date takenDate;
	long timeToComplete;

	private int score;
	private boolean isHighScore;


	public QuizStats(){		
	}

	public QuizStats (String quizName) {
		this.quizName = quizName;
	}

	public String getQuizName() {
		return quizName;
	}

	public void setQuizName(String quizName) {
		this.quizName = quizName;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getTakenDate() {
		DateAndTime dateTime = new DateAndTime(takenDate);
		String stringDate = dateTime.getDate();
		return stringDate;
	}

	public Date  getTakenDateInDateFormant() {

		return takenDate;
	}

	public void setTakenDate(Date takenDate) {
		this.takenDate = takenDate;
	}

	public String getTakenTime() {
		DateAndTime dateTime = new DateAndTime(takenDate);
		String stringTime = dateTime.getTime();
		return stringTime;
	}

	public void setTakenTime(String  takenTime) {
	}

	public int getScore() {
		return score;
	}

	public void setScore(int score) {
		this.score = score;
	}
	@Override
	public String toString() {

		String.format("%d min, %d sec", 
				TimeUnit.MILLISECONDS.toMinutes(timeToComplete),
				TimeUnit.MILLISECONDS.toSeconds(timeToComplete) - 
				TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(timeToComplete))
				);

		String str =  "QuizStats [quizName=" + quizName + ", userName=" + userName
				+ ", takenDate=" + getTakenDate() + ", takenTime=" + getTakenTime()
				+ ", timeToComplete=" + getTimeToCompleteStr() + ", score=" + score
				+ "]";

		return str;
	}

	public long getTimeToComplete() {


		return timeToComplete;
	}
	public String getTimeToCompleteStr() {
		int numOfSeconds = (int) (timeToComplete / 1000); 
		int numOfmilliSeconds = (int) (timeToComplete % 1000); 
		int numOfMinutes =0;
		if (numOfSeconds > 60 ){
			numOfMinutes = numOfSeconds / 60 ;
			numOfSeconds = numOfSeconds % 60;
		}

		String str =numOfMinutes + "." +  numOfSeconds + ":" + numOfmilliSeconds + "ms" ;


		return str;
	}

	public void setTimeToComplete(long timeToComplete) {
		this.timeToComplete = timeToComplete;
	}


	public String toHtmlTable () {
		String rv = "";

		rv += "<td>";
		rv += String.format("<a href=\"profile.jsp?username=%s\">%s</a>", userName, userName);
//		rv += "</td>";
		
		rv += "<td>";
		rv += String.format("<a href=\"quiz_summary.jsp?quiz_name=%s\">%s</a>", quizName, quizName);
//		rv += "</td>";
		
		rv += "<td>";
		rv += String.format("%s", score);
//		rv += "</td>";
		
		rv += "<td>";
		rv += String.format("%d", timeToComplete/1000);
//		rv += "</td>";
		
		rv += "<td>";
		rv += String.format("%s", takenDate);
//		rv += "</td>";


		return rv;
	}

	static public String toHtmlTable (ArrayList<QuizStats> list, String opt) {
		String rv = String.format("<table class=\"list\" %s>\n", opt);

		rv += "<th style=\"width:100px\" > User";
		rv += "<th style=\"width:125px\"> Quiz";
		rv += "<th style=\"width:50px\"> Score";
		rv += "<th style=\"width:50px\"> Time<br>(sec)";
		rv += "<th style=\"width:175px\"> Submission date";
		
		if(list.size() == 0) {
			rv += "<tr>";
			rv += "<td colspan=\"5\" > None";
		} else {
			for(int i = 0; i < list.size(); i++) {
				rv += "<tr>";
				rv += list.get(i).toHtmlTable();
				rv += "</tr>\n";
			}
		}
		rv += "</table>";
		return rv;
	}

	public boolean isHighScore() {
		return isHighScore;
	}

	public void setHighScore(boolean isHighScore) {
		this.isHighScore = isHighScore;
	}
	
	
}
