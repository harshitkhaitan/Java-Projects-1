package quiz;

import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.Iterator;
import dbAccess.DBAccess;
/**
 * This class generates the quiz object.
 * Holds multiple questions.
 * adds the quiz to the database
 * adds the questions to the database
 * retrieves the questions from the database
 * checks the answers for the questions and updates the database.
 * @author jayakarr
 *
 */
public class Quiz {

	private String quizName;
	private String creator;

	public QuizCreateDetails details;

	//FIXME 
	//private Scores score;
	public QuizStats score2;

	//Date format
	DateFormat dateFormat ;
	Date date ;

	//DBConnector
	DBAccess dbAccess;

	//list of Questions
	protected  ArrayList<QuizQuestion> questionsinQuiz;

	private boolean DEBUG = false;

	/**
	 * Constructor while adding questions to the database
	 * @param quizName
	 * @param creator
	 * @param dbAccess2
	 * @param questionOrderRandom
	 */

	//Misc quiz state
	public int current_question = 0;
	public long clientTime = -1;
	public boolean in_practice_mode = false;
	public long beginTime = 0;

	// Creates a blank quiz
	public Quiz(String quizName, String creator, DBAccess dbAccess2,
			QuizCreateDetails details
			){

		this.quizName = quizName;
		this.creator  = creator;
		this.dbAccess = dbAccess2;
		this.details = details;

		date = new Date();
		questionsinQuiz = new ArrayList<QuizQuestion>();


		if  (DEBUG) System.out.println("End of constructor ");

		//score = new Scores(0);
		score2 = new QuizStats(quizName);
	}

	/**
	 * constructor while retrieving the questions from the database. 
	 * @param quizName
	 * @param dbAccess2
	 */
	public Quiz(String quizName, DBAccess dbAccess2){
		this.quizName = quizName;
		this.dbAccess = dbAccess2;

		// Place older for result to save back to database
		score2 = new QuizStats(quizName);
		beginTime = System.currentTimeMillis();

		// Fetch quiz and populate object deails from database
		details = dbAccess.getQuizCreateDetails(quizName);
		//getQuestionsinQuiz();
	}


	/**
	 * Saving the quiz to the database.
	 */
	public void saveQuiz(){
		if  (DEBUG) System.out.printf("Saving Quiz %s\n", quizName);

		Iterator <QuizQuestion> question_itr = questionsinQuiz.iterator();
		int count =0;
		while(question_itr.hasNext()){
			QuizQuestion this_question = question_itr.next();
			int this_question_number = this_question.getQuestionNumber();

			int numOfRecordsAdded = dbAccess.insertQuestionToDB(this_question);	
			if (numOfRecordsAdded == 2) {
				if  (DEBUG) System.out.println("INFO: Saved question "+this_question_number);
				count ++;
			} else {
				if  (DEBUG) if  (DEBUG) System.out.println("ERROR: Cannot save question"+this_question_number);
			}

		}
		if  (DEBUG) if  (DEBUG) System.out.printf("Saved Quiz %s with %d questions\n", quizName, count);
		dbAccess.quizCreate(quizName, creator, date, details.questionOrderRandom, count, details);
	}

	/**
	 * retrieveing the quiz details from the database 
	 * this returns the QuizCreateDetails object. 
	 * @param string
	 * @return QuizCreateDetails
	 */
	public QuizCreateDetails getQuizCreateDetails(String string) {
		return dbAccess.getQuizCreateDetails(string) ;
	}

	static public QuizCreateDetails getQuizCreateDetails(String string, DBAccess db) {
		return db.getQuizCreateDetails(string) ;
	}


	/**
	 * Add a Fill in the Blank question to the ArrayList of questions.
	 * @param questions
	 * @param answer
	 * @param questionNumber
	 */
	public void addQuestion_FIB(ArrayList<String> questions, ArrayList<String> answers, 	int questionNumber) {
		String imgURL = "";
		ArrayList<String> multiplechoices = new ArrayList<String>();
		QuizQuestion quizQuestionVar = new  QuizQuestionFIB(quizName ,questionNumber , questions, answers, multiplechoices, imgURL );
		boolean canQuestionBeAdded = quizQuestionVar.addQuestionToQuiz();
		if (canQuestionBeAdded ){
			if  (DEBUG) System.out.println("All Checks passed in FIB");
			questionsinQuiz.add(quizQuestionVar);
		}else{
			if  (DEBUG) System.out.println("Something failed ");
		}
	}
	/**
	 * Add a Picture Response  question to the ArrayList of questions.
	 * @param question
	 * @param answer
	 * @param imgURL
	 * @param questionNumber
	 */
	public void addQuestion_PR(String question, ArrayList<String> answers, String imgURL,	int questionNumber) {
		ArrayList<String> questions= new ArrayList<String>();
		questions.add(question);

		ArrayList<String> multiplechoices = new ArrayList<String>();

		QuizQuestion quizQuestionVar = new  QuizQuestionPR( quizName ,questionNumber , questions, answers, multiplechoices, imgURL );
		boolean canQuestionBeAdded = quizQuestionVar.addQuestionToQuiz();
		if (canQuestionBeAdded ){
			if  (DEBUG) System.out.println("All Checks passed for PR");
			questionsinQuiz.add(quizQuestionVar);
		}else{
			if  (DEBUG) System.out.println("Something failed ");
		}

	}

	/**
	 * Add a Question Response question to the ArrayList of questions.
	 * @param question
	 * @param answer
	 * @param questionNumber
	 */
	public void addQuestion_QR (String question, ArrayList<String> answers, int questionNumber){
		ArrayList<String> questions= new ArrayList<String>();
		questions.add(question);

		//ArrayList<String> answers = new ArrayList<String>();
		//answers.add(answer);

		ArrayList<String> multiplechoices = new ArrayList<String>();
		String imgURL = "";

		System.out.printf("Q=%s, A=%s, N=%d\n", question, answers.toString(), questionNumber);

		QuizQuestion quizQuestionVar = new  QuizQuestionQR(quizName ,questionNumber , questions, answers, multiplechoices, imgURL );

		boolean canQuestionBeAdded = quizQuestionVar.addQuestionToQuiz();
		if (canQuestionBeAdded ){
			if  (DEBUG) System.out.println("All Checks passed for QR");
			questionsinQuiz.add(quizQuestionVar);
		}else{
			if  (DEBUG) System.out.println("Something failed ");
		}
	}

	/**
	 * Add a Multiple Choice  question to the ArrayList of questions.
	 * @param question
	 * @param answer
	 * @param multiplechoices
	 * @param questionNumber
	 */
	public void addQuestion_MC(String question, String answer, ArrayList<String> multiplechoices, int questionNumber ) {
		ArrayList<String> questions= new ArrayList<String>();
		questions.add(question);

		ArrayList<String> answers = new ArrayList<String>();
		answers.add(answer);
		String imgURL = "";

		QuizQuestion quizQuestionVar = new  QuizQuestionMC(quizName ,questionNumber , questions, answers, multiplechoices, imgURL );
		boolean canQuestionBeAdded = quizQuestionVar.addQuestionToQuiz();
		if (canQuestionBeAdded ){
			if  (DEBUG) System.out.println("All Checks passed in MC add Question");
			questionsinQuiz.add(quizQuestionVar);

		}else{
			if  (DEBUG) System.out.println("Something failed ");
		}
	}


	public QuizStats getScore() {
		return score2;
	}


	public void gradeQuiz() {
		int score = 0;

		Iterator <QuizQuestion> question_itr = questionsinQuiz.iterator();
		while(question_itr.hasNext()){
			QuizQuestion this_question = question_itr.next();
			score += this_question.checkResponses();
		}

		// Score current quiz instance and timestamp it
		this.score2.setScore(score);
		this.score2.setTakenDate( new Date());
		this.score2.setTimeToComplete(System.currentTimeMillis() - beginTime);
	}

	/**
	 * get the quizName 
	 * @return quizName
	 */
	public String getQuizName() {
		return quizName;
	}

	/**
	 * 
	 * @return creator 
	 */
	public String getCreator() {
		return creator;
	}

	/**
	 * 
	 * @return get the date the quiz was created.
	 */
	public Date getDate() {
		return date;
	}



	/**
	 * returns an arrayList of the questions present. 
	 * @return ArrayList<QuizQuestion>
	 */
	public ArrayList<QuizQuestion> getQuestionsinQuiz() {

		boolean fetch_quiz = false;

		if(questionsinQuiz == null) {
			fetch_quiz = true;
		} else if (questionsinQuiz.size() == 0) {
			fetch_quiz = true;
		}
		if(fetch_quiz) {
			questionsinQuiz = dbAccess.getQuestions(quizName);
			int l = questionsinQuiz.size();

			if  (DEBUG) System.out.println("value of order =" + details.questionOrderRandom);
			if  (DEBUG) System.out.printf("Got %d questions\n", l);
			if (details.questionOrderRandom){
				Collections.shuffle(questionsinQuiz);
			}
		}

		return questionsinQuiz;
	}

	public void setScore (QuizStats stat) {
		score2 = stat;
	}

	public void submitScore () {
		dbAccess.insertStatsToDB(score2);
	}

	/**
	 * checks the responses for each question
	 * updates the database with username, quizname, date , time and score.
	 * @param userName 
	 */
	//	public void checkResponses(String userName, long timeTaken ) {
	//		//		Iterator <QuizQuestion> question_itr = questionsinQuiz.iterator();
	//		//		int numOfCorrectAnswers = 0;
	//		//		while(question_itr.hasNext()){
	//		//			QuizQuestion this_question = question_itr.next();
	//		//			numOfCorrectAnswers += this_question.checkResponses();
	//		//		}
	//		//		if  (DEBUG) System.out.println("Number of correct answers = " + numOfCorrectAnswers );
	//		//		
	//		//		dbAccess.updateScores(userName, quizName , numOfCorrectAnswers,timeTaken);
	//	}

	//	static public ArrayList<String> getQuizNames(DBAccess db) {
	//		return db.getQuizNames();
	//	}

	public String toHtmlTable () {
		String rv = "";

		rv += "<td>";
		rv += String.format("<a href=\"quiz_summary.jsp?quiz_name=%s\">%s</a>", quizName, quizName);	

		rv += "<td>";
		rv += String.format("<a href=\"profile.jsp?username=%s\">%s</a>", details.createdBy, details.createdBy);

		rv += "<td>";
		rv += String.format("%s %s", details.createDate, details.createTime);

		rv += "<td>";
		rv += String.format("%s", details.numberOfTimesTaken);

		return rv;
	}

	static public String toHtmlTable (ArrayList<String> list, String opt, DBAccess db) {
		String rv = String.format("<table class=\"list\" %s>\n", opt);

		rv += "<th style=\"width:125px\">Quiz";
		rv += "<th style=\"width:100px\">Author";
		rv += "<th style=\"width:175px\">Creation Time";
		rv += "<th style=\"width:50px\">Attempts";

		if(list.size() == 0) {
			rv += "<tr>";
			rv += "<td colspan=\"5\" > None";
		} else {
			for(int i = 0; i < list.size(); i++) {
				Quiz q = new Quiz(list.get(i), db);
				rv += "<tr>";
				rv += q.toHtmlTable();
			}
		}
		rv += "</table>";
		return rv;
	}

	public String toHTML (String user_name, DBAccess dbAccess) {
		String rv = "";

		float avg = details.getAvg();
		float std = details.getStd();

		String practice_attr = " disabled=\"true\" ";
		if(details.allow_practice) {
			practice_attr = "";
		}

		rv += "<div>";
		rv += "<div class=\"putleft\">";
		rv += "<table class=\"invisible\" >";
		rv += "<tr>";
		rv += "<td style=\"vertical-align:top;\" >";
		rv += "<table class=\"list invisible\" >";
		rv += String.format("<tr><td style=\"width:100px\" >Quiz Title<td  style=\"width:90px\" >%s", quizName);
		rv += String.format("<tr><td>Quiz Creator<td><a href=\"profile.jsp?username=%s\">%s</a><br>", details.createdBy, details.createdBy);
		rv += String.format("<tr><td>Mean Score<td>%.2f", avg);
		rv += String.format("<tr><td>Standard deviation<td>%.2f<br>", std);
		
		

		// Buttons
		rv += String.format("<tr><td><form action=\"QuizServlet?quiz_name=%s&task=PracticeQuiz\" method=\"post\"><button name=\"QuizTableSubmit\" value=\"%s\" type=\"submit\" %s style=\"width:100px;\">Practice</button></form>", quizName, quizName, practice_attr);
		rv += "<td>";
		rv += String.format("<tr><td><form action=\"QuizServlet?quiz_name=%s&task=TakeQuiz\"     method=\"post\"><button name=\"QuizTableSubmit\" value=\"%s\" type=\"submit\"    style=\"width:100px;\">Take</button></form>",  quizName, quizName);
		rv += "<td>";
		
		rv += "</table>";

		rv += "<td style=\"vertical-align:top;\" >";
		rv += "<table class=\"list invisible\" >";
		rv += String.format("<tr><td style=\"width:130px\" >Allow practice quiz<td>%s", details.allow_practice ? "Y" : "N");
		rv += String.format("<tr><td>One question per page<td>%s", details.one_question_per_page ? "Y" : "N");
		rv += String.format("<tr><td>Immediate correction<td>%s", details.immediate_correct ? "Y" : "N");
		rv += String.format("<tr><td>Randomization<td>%s", details.questionOrderRandom ? "Y" : "N");
		rv += "</table>";


		rv += "</table>";
		rv += "</div>";

		rv += "<div class=\"putleft\">";
		rv += "<td style=\"vertical-align:top;\" >";
		rv += "<table class=\"invisible\" >";
		//rv += String.format("<td>Description:");
		rv += String.format("<td rowspan=\"3\" x><textarea cols=\"45\" rows=\"6\" name=\"description\" >%s</textarea><br>", details.desciption);
		rv += "</table>";
		rv += "</div>";

		rv += "<div class=\"endpanel\"></div>";
		rv += "</div>";


		// User - Quiz history
		rv += "<div class=\"putleft\">";
		rv += "<h3>User Activity</h3>";
		//List of user's best performances
		ArrayList<QuizStats> user_best_quiz_stats = dbAccess.getBestStatsForQuizForUser(quizName, user_name);
		rv += "<p>Best performances</p>";
		rv += QuizStats.toHtmlTable(user_best_quiz_stats, "");

		// List of user's recent performances
		ArrayList<QuizStats> user_quiz_stats = dbAccess.getLatestStatsForQuizForUser(quizName, user_name);
		rv += "<p>Latest performances</p>";
		rv += QuizStats.toHtmlTable(user_quiz_stats, "");			
		rv += "</div>";

		// Site - Quiz history	
		rv += "<div class=\"putleft\">";
		rv += "<h3>Site Activity</h3>";
		// Top Performers of all time
		ArrayList<QuizStats> best_stats = dbAccess.getBestStatsForQuiz(quizName);
		rv += "<p>Best performances of all time</p>";
		rv += QuizStats.toHtmlTable(best_stats, "");

		// Top Performers for day
		ArrayList<QuizStats> best_day_stats = dbAccess.getBestStatsForQuizForDay(quizName);
		rv += "<p>Best performances today</p>";
		rv += QuizStats.toHtmlTable(best_day_stats, "");

		// All Performers
		ArrayList<QuizStats> day_stats = dbAccess.getLatestStatsForQuiz(quizName);
		rv += "<p>Latest performances</p>";
		rv += QuizStats.toHtmlTable(day_stats, "");
		rv += "</div>";

		return rv;
	}
}
