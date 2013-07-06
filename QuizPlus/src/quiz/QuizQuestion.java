package quiz;

import java.util.ArrayList;
/**
 * This class stores a single question in the Quiz.
 * @author jayakarr
 *
 */
public abstract class QuizQuestion {
	public enum QuestionTypes {QR, FIB, MC , PR};

	protected ArrayList <String> questionString;
	protected ArrayList <String> correctAnswer;
	protected ArrayList 	<String> multipleChoice;

	// Place to hold answers submitted by user
	protected ArrayList <String> responses;

	protected String imageUrl;

	//This variable stores which question type this Question is?
	protected QuestionTypes questionType;

	//This variable stores under which quiz this Question is going to be stored.
	protected String quizName;

	//Within the quiz, what would be the question number for this Quiz. 
	protected int questionNumber;

	// Convenient place for state
	private boolean isGraded;
	private int score;

	protected static boolean DEBUG = true;

	/**
	 * constructor for the Question Class.
	 * @param QuestionType
	 * @param quizName
	 * @param questionNumber
	 */
	public QuizQuestion (QuestionTypes QuestionType, String quizName, int questionNumber , ArrayList <String> questionString1 
			,ArrayList <String> correctAnswer1 , ArrayList <String> multipleChoice1  , String imgURL1 ){
		this.questionType 	= 	QuestionType; 
		this.quizName		=	quizName;
		this.questionNumber	=	questionNumber;
		this.imageUrl = imgURL1;
		this.isGraded = false;


		questionString = new ArrayList<String>();
		correctAnswer  = new ArrayList<String>();
		multipleChoice = new ArrayList<String>();
		responses 		= new ArrayList<String>();
		questionString 		= new ArrayList<String>();
		correctAnswer = new ArrayList<String>();
		multipleChoice = new ArrayList<String>();

		questionString.addAll(questionString1);
		correctAnswer.addAll(correctAnswer1);
		multipleChoice.addAll(multipleChoice1);
	}

	public QuizQuestion(){
		questionString = new ArrayList<String>();
		correctAnswer  = new ArrayList<String>();
		multipleChoice = new ArrayList<String>();
		responses 		= new ArrayList<String>();
	}


	public QuestionTypes getQuestionType() {
		return questionType;
	}


	public String getQuizName() {
		return quizName;
	}

	public int getQuestionNumber() {
		return questionNumber;
	}




	public void setQuestionType(QuestionTypes questionType) {
		this.questionType = questionType;
	}

	public void setQuizName(String quizName) {
		this.quizName = quizName;
	}

	public void setQuestionString(String questionPart , int index){
		questionString.add(index, questionPart);
	}


	public ArrayList<String> getQuestionString() {
		return questionString;
	}

	public void setCorrectAnswer (String answerPart, int index){
		correctAnswer.add(index,answerPart);
	}


	public void setQuestionNumber(int questionNumber) {
		this.questionNumber = questionNumber;
	}

	public ArrayList<String> getCorrectAnswer() {
		return correctAnswer;
	}

	public void setMultipleChoice(String multipleChoicePart, int index) {
		multipleChoice.add(index, multipleChoicePart);
	}

	public ArrayList<String> getMultipleChoice() {
		return multipleChoice;
	}

	public void setImageUrl(String imageUrl) {
		this.imageUrl = imageUrl;
	}


	public String getImageUrl() {
		return imageUrl;
	}



	/**
	 * Adds the Question to the Quiz.
	 */
	public boolean addQuestionToQuiz(){
		boolean canQuestionBeAdded = checkQuestionForErr();
		updateStringsForSQL();
		//Now Send the whole Question Object to the database to be added. 
		//addQuestionDB();
		return canQuestionBeAdded;
	}

	private void updateStringsForSQL() {
		questionString = updateStringList(questionString);
		multipleChoice = updateStringList(multipleChoice);
		correctAnswer  = updateStringList(correctAnswer);
		appendDeLimiters(imageUrl);
	}

	private ArrayList<String> updateStringList(ArrayList<String> StringArrayList) {

		ArrayList<String> tempString = new ArrayList<String>() ;
		tempString.addAll(StringArrayList);
		StringArrayList.clear();

		for (String str : tempString)
			StringArrayList.add(appendDeLimiters(str));

		return StringArrayList;


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

	boolean checkQuestionForErr() {
		//Question String is not Set
		if (questionString.size() < 1 ){
			if (DEBUG) System.out.println("Question String should be atleast have one String");
			return false;
		}
		if (correctAnswer.size() < 1 ){
			if (DEBUG) System.out.println("Answer should have atleast have one String");
			return false;
		}

		//Reached here so the Question is safe to be added.
		return true;
	}

	public QuizQuestion getQuizQuestion(){
		return this;
	}

	public void setResponse (String response) {
		responses.add(response);
	}

	@Override
	public abstract String toString();

	public String jspPrefix () {
		return "question_" + questionNumber;
	}

	public boolean isGraded() {
		return isGraded;
	}

	public int getScore() {
		return score;
	}

	public int checkResponses () {
		int score = 0;
		isGraded = true;
		
		String b = null;
		try {
			b = responses.get(0);
		} catch (IndexOutOfBoundsException ignore) {}
		
		for(int i = 0; i < correctAnswer.size(); i++) {
			String a = correctAnswer.get(i);
			if (DEBUG) System.out.println("Got answer " +b+ ", correct=" +a);

			if(a.equalsIgnoreCase(b)) {
				score = 1;
			}
		}	

		this.score = score;
		return score;
	}

	public String getQuestionSummary () {
		return String.format("%s", questionString.get(0));
	}

	public String getCorrectAnswerSummary () {
		return String.format("%s", correctAnswer.get(0));
	}

	public String getResponseSummary () {
		try {
			String response = responses.get(0);

			if(response == null)
				throw new IndexOutOfBoundsException();

			if(response.isEmpty())
				throw new IndexOutOfBoundsException();

			return String.format("%s", response);

		} catch (IndexOutOfBoundsException e) {
			return "Unanswered";
		}
	}

	public static String jq (String s) {
		return s.replace('|', '"');
	}

	public abstract String toHtmlForm ();

}
