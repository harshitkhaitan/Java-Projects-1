package quiz;

import java.util.ArrayList;


public class QuizQuestionPR extends QuizQuestion  { 

	public QuizQuestionPR(String quizName,
			int questionNumber, ArrayList<String> questionString1,
			ArrayList<String> correctAnswer1,
			ArrayList<String> multipleChoice1, String imgURL1) {
		super( QuestionTypes.PR, quizName, questionNumber, questionString1, correctAnswer1,
				multipleChoice1, imgURL1);
	}

	public QuizQuestionPR() {
		super();
	}

	//@Override
	public String toHtmlForm() {
		QuizQuestionPR q = this;

		String rv = "";
		String tag = q.jspPrefix();

		rv += String.format( 
				jq(
						"<table class=|invisible| >" +
								"<tr>" +
								"<td> Question:" +
								"<td> %s " +

								"<tr>" +
								"<td>" +
								"<td> <img src=|%s|> " +

								"<tr>" +
								"<td> Answer:" +
								"<td> <input type=|text| size=|50| name=|%s_Answer| value=|| />" +
								"</table>" +
						""),
						q.getQuestionString().get(0), q.getImageUrl(), tag);
		return rv;
	}

	@Override
	boolean checkQuestionForErr() {

		if(!super.checkQuestionForErr())
			return false;

		if (imageUrl == null ){
			if (DEBUG) System.out.println("Picture Response Question should have a Valid Image Url");
			return false;
		}

		//Reached here so the Question is safe to be added.
		return true;
	}

	@Override
	public String toString() {


		return "QuizQuestion [questionString=" + questionString
				+ ", correctAnswer=" + correctAnswer +  ", questionType=" + questionType +  ", quizName=" + quizName + ", questionNumber="
				+ questionNumber +  ", imageUrl=" + imageUrl + "]";

	}


}
