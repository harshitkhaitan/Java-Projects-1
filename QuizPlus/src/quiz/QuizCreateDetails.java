package quiz;

import java.util.ArrayList;
import java.util.Iterator;

/**
 * returns the quizCreate Details back to the user
 * holds the name of quiz
 * holds the creator of the quiz
 * holds the Created Date and Time of the quiz
 * holds the number of times the quiz was taken 
 * holds whether the quiz creator wants the question to be returned in a random order 
 * holds how every user who has taken the quiz has performed. This is in the statsForQuizUsers list. 
 *  
 * @author jayakarr
 *
 */
public class QuizCreateDetails {
	String quizName;
	String createdBy;
	String createDate;
	String createTime;
	
	int numberOfTimesTaken;
	boolean questionOrderRandom;
	private float avg;
	private float std;
	
	public String desciption;
	public boolean allow_practice = false;
	public boolean one_question_per_page = false;
	public boolean immediate_correct = false;
	protected static boolean DEBUG = true;
	
	ArrayList<QuizStats> statsForQuizUsers;

	public boolean isQuestionOrderRandom() {
		return questionOrderRandom;
	}
	
	private  String appendDeLimiters(String str) {
		int index = str.indexOf('\'');   
		if (DEBUG) System.out.println("String before Inserting =" + str);
		if (index > -1) {
			if (DEBUG) System.out.println("found a \' at position =" + index);
			str = str.substring(0, index) + "\\" + str.substring(index, str.length());
			str = str.substring(0, index+2) + appendDeLimiters(str.substring(index+2));
		}
		if (DEBUG) System.out.println("String After Inserting =" + str);
		return str;
	}
	
	public QuizCreateDetails(String quizName, String createdBy,
			int numberOfTimesTaken,
			boolean questionOrderRandom, boolean allow_practice,
			boolean one_question_per_page, boolean immediate_correct,
			String description
		) {
		
		quizName = appendDeLimiters(quizName);
		this.quizName = quizName;
		
		createdBy = appendDeLimiters(createdBy);
		this.createdBy = createdBy;
		this.numberOfTimesTaken = numberOfTimesTaken;
		this.questionOrderRandom = questionOrderRandom;
		this.allow_practice = allow_practice;
		this.one_question_per_page = one_question_per_page;
		this.immediate_correct = immediate_correct;
		
		description = appendDeLimiters(description);
		this.desciption = description;
		this.avg =0;
		this.std =0;
	}
	
	public void setQuestionOrderRandom(boolean questionOrderRandom) {
		this.questionOrderRandom = questionOrderRandom;
	}

	public QuizCreateDetails(){
		statsForQuizUsers = new ArrayList<QuizStats>();
		
	}

	public ArrayList<QuizStats> getStatsForQuiz() {
		return statsForQuizUsers;
	}

	public void setStatsForQuiz(QuizStats statsForThisUser) {
		statsForQuizUsers.add(statsForThisUser);
	}

	public String getQuizName() {
		return quizName;
	}

	public void setQuizName(String quizName) {
		this.quizName = quizName;
	}

	public String getCreatedBy() {
		return createdBy;
	}

	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}

	public String getCreateDate() {
		return createDate;
	}

	public void setCreateDate(String createDate) {
		this.createDate = createDate;
	}

	public String getCreateTime() {
		return createTime;
	}

	public void setCreateTime(String createTime) {
		this.createTime = createTime;
	}

	public int getNumberOfTimesTaken() {
		return numberOfTimesTaken;
	}

	public void setNumberOfTimesTaken(int numberOfTimesTaken) {
		this.numberOfTimesTaken = numberOfTimesTaken;
	}

	
	
	@Override
	public String toString() {
		String str = "QuizCreateDetails [quizName=" + quizName + ", createdBy="
		+ createdBy + ", createDate=" + createDate + ", createTime="
		+ createTime + ", numberOfTimesTaken=" + numberOfTimesTaken
		+ ", questionOrderRandom=" + questionOrderRandom + ", avg="
		+ avg + ", std=" + std + ", desciption=" + desciption
		+ ", allow_practice=" + allow_practice
		+ ", one_question_per_page=" + one_question_per_page
		+ ", immediate_correct=" + immediate_correct + "]";

		if(statsForQuizUsers != null) {
			Iterator<QuizStats> iter = statsForQuizUsers.iterator()   ;

			while (iter.hasNext()){

				str = str + iter.next().toString() +  "\n";

			}
		}
		
		return str;
	}

	public float getAvg() {
		return avg;
	}

	public void setAvg(float avg) {
		this.avg = avg;
	}

	public float getStd() {
		return std;
	}

	public void setStd(float std) {
		this.std = std;
	}

	
	
	
	
}
