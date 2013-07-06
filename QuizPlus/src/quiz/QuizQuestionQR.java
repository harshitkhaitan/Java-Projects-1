package quiz;

import java.util.ArrayList;

public class QuizQuestionQR extends QuizQuestion  { 

	public QuizQuestionQR(String quizName,
			int questionNumber, ArrayList<String> questionString1,
			ArrayList<String> correctAnswer1,
			ArrayList<String> multipleChoice1, String imgURL1) {
		super( QuestionTypes.QR, quizName, questionNumber, questionString1, correctAnswer1,
				multipleChoice1, imgURL1);
	}

	public QuizQuestionQR() {
		super();
	}

	@Override
	public String toHtmlForm() {
		String rv = "";
		String tag = jspPrefix();

		rv += String.format( 
				jq(
						"<table class=|invisible| >" +
								"<tr>" +
								"<td> Question:" +
								"<td> %s " +

			"<tr>" +
			"<td> Answer:" +
			"<td> <input type=|text| size=|50| name=|%s_Answer| value=|| />" +
			"</table>" +
						""),
						getQuestionString().get(0), tag);
		return rv;
	}

	@Override
	boolean checkQuestionForErr() {
		if(!super.checkQuestionForErr())
			return false;

		//Reached here so the Question is safe to be added.
		return true;
	}

	@Override
	public String toString() {


		return "QuizQuestion [questionString=" + questionString
				+ ", correctAnswer=" + correctAnswer + ", questionType="
				+ questionType + ", quizName=" + quizName + ", questionNumber="
				+ questionNumber + "]";

	}


}
