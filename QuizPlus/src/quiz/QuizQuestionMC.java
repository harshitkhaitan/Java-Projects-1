package quiz;

import java.util.ArrayList;

public class QuizQuestionMC extends QuizQuestion  { 

	public QuizQuestionMC(String quizName,
			int questionNumber, ArrayList<String> questionString1,
			ArrayList<String> correctAnswer1,
			ArrayList<String> multipleChoice1, String imgURL1) {
		super( QuestionTypes.MC, quizName, questionNumber, questionString1, correctAnswer1,
				multipleChoice1, imgURL1);
	}

	public QuizQuestionMC() {
		super();
	}

	@Override
	public String toHtmlForm () {
		QuizQuestionMC q = this;
		String rv = "";
		String tag = q.jspPrefix();

		rv += String.format( 
				jq(
						"<table class=|invisible| >" +
								"<tr>" +
								"<td> Question:" +
								"<td> %s " +

						"<tr>" +
						"<td> Options:"), q.getQuestionString().get(0), tag);

		for(int i = 0; i < q.getMultipleChoice().size(); i++) {
			if(i > 0) {
				rv += "<tr><td>";
			}

			rv += String.format( 
					jq(  
							"<td><input type=|radio| name=|%s_Answer| value=|"+i+"|>%s<br>"

							),
							tag, q.getMultipleChoice().get(i)
					);
		}
		rv += "</table>";

		return rv;
	}

	@Override
	boolean checkQuestionForErr() {


		if(!super.checkQuestionForErr())
			return false;

		if (multipleChoice.size() < 2 ){
			if (DEBUG) System.out.println("Multiple Choice Questions should have atleast have 4 Choices");
			return false;
		}

		//Reached here so the Question is safe to be added.
		return true;
	}

	@Override
	public String getCorrectAnswerSummary () {
		return String.format("%s", multipleChoice.get(Integer.parseInt(correctAnswer.get(0))));
	}

	@Override
	public String getResponseSummary () {
		try {
			String response = responses.get(0);

			if(response == null)
				throw new IndexOutOfBoundsException();

			if(response.isEmpty())
				throw new IndexOutOfBoundsException();

			return String.format("%s", multipleChoice.get(Integer.parseInt(response)));

		} catch (IndexOutOfBoundsException e) {
			return "Unanswered";
		}
	}

	@Override
	public String toString() {


		return "QuizQuestion [questionString=" + questionString
				+ ", correctAnswer=" + correctAnswer +  ", questionType=" + questionType +  ", quizName=" + quizName + ", questionNumber="
				+ questionNumber +  
				", multipleChoice=" + multipleChoice  + "]";


	}

}
