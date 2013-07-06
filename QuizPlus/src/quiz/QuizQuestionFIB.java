package quiz;

import java.util.ArrayList;

public class QuizQuestionFIB extends QuizQuestion { 

	public QuizQuestionFIB(String quizName,
			int questionNumber, ArrayList<String> questionString1,
			ArrayList<String> correctAnswer1,
			ArrayList<String> multipleChoice1, String imgURL1) {
		super( QuestionTypes.FIB, quizName, questionNumber, questionString1, correctAnswer1,
				multipleChoice1, imgURL1);
	}

	public QuizQuestionFIB() {
		super();
	}

	@Override
	public String toHtmlForm () {
		QuizQuestionFIB q = this;
		String rv = "";
		String tag = q.jspPrefix();

		rv += String.format( 
				jq(
						"<table class=|invisible| >" +
								"<tr>" +
								"<td> Fill in the blank:" +
								"<td> %s " +

			"<tr>" +
			"<td> Answer:" +
			"<td> <input type=|text| size=|50| name=|%s_Answer| value=|| />" +
			"</table>" +
						""),
						q.getQuestionString().get(0) + " ______________ " + q.getQuestionString().get(1), tag);
		return rv;

	}

	@Override
	boolean checkQuestionForErr() {

		if(!super.checkQuestionForErr())
			return false;



		if (questionString.size() < 2){
			if (DEBUG) System.out.println("Fill In the Blank Questions should have atleast have 2 parts");
			if (DEBUG) System.out.println("one before the blank and one after the blank");
			return false;
		}

		//Reached here so the Question is safe to be added.
		return true;
	}

	@Override
	public String getQuestionSummary () {
		return String.format("%s ___ %s", questionString.get(0), questionString.get(1));
	}
	
	@Override
	public String toString() {

			return "QuizQuestion [questionString=" + questionString
					+ ", correctAnswer=" + correctAnswer +  ", questionType=" + questionType + ",   quizName=" + quizName + ", questionNumber="
					+ questionNumber +  "]";

	}

}
