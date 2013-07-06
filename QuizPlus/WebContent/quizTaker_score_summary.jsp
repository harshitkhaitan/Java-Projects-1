<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>

<%@ page import="web.config.*"%>
<%@ page import="quiz.*"%>
<%@ page import="java.util.Iterator"%>
<%@page import="dbAccess.DBAccess"%>


<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Quiz Score Summary</title>
<link rel="stylesheet" type="text/css" href="quiz_default.css" />
</head>
<body>

	<div class="organize">
	<div class="putleft">
	<h3>Score Sheet</h3>
		<%
			Quiz quiz = (Quiz) request.getSession().getAttribute("current_quiz");
			String user_name = (String) session.getAttribute("userName");
			DBAccess dbAccess = (DBAccess) session.getAttribute("dbAccess");
			DataBaseConnection db = (DataBaseConnection) request.getServletContext().getAttribute("Database");
		%>

		<p>You scored <b><%=quiz.getScore().getScore()%></b> points.</p>

		<%
			Iterator<QuizQuestion> question_itr = quiz.getQuestionsinQuiz().iterator();
			int count = 0;
			
			if(quiz.in_practice_mode) {
				out.println("<p>[PRACTICE MODE]</p>");
				db.addAchievements(user_name, 6);
			} else {
				if(quiz.score2.isHighScore()) {
					out.println("<p>Congratulations, you have a high score!</p>");
					db.addAchievements(user_name, 5);
				}
			}

			out.println("<table class=\"list\" >");
			out.println("<tr>");
			out.println("<th>#");
			out.println("<th>Question");
			out.println("<th>Preferred Answer");
			out.println("<th>Your Response");
			out.println("<th>Grade");
			
			while (question_itr.hasNext()) {
				QuizQuestion this_question = question_itr.next();
				int score = this_question.getScore();
				String question_asString = this_question.getQuestionSummary();
				String answer_asString = this_question.getCorrectAnswerSummary();
				String response_asString = this_question.getResponseSummary();
				String result_img_url = "";

				if (score > 0) {
					result_img_url="images/right_answer.png";
				} else {
					result_img_url="images/wrong_answer.png";
				}

				String question_line = String.format(QuizServlet.jq(
						"<tr>"
						+ "<td>%d"
						+ "<td>%s"
						+ "<td>%s"
						+ "<td>%s"
						+ "<td><img src=|%s| style=|max-width:25px; max-height:25px;|>"
						+ "\n"),
						this_question.getQuestionNumber(),
						question_asString, answer_asString, response_asString,
						result_img_url);
				out.println(question_line);
			}
			out.println("</table>");
		%>
		<a href="quiz_main.jsp"><input type="button" name="Back to quiz list" value="Back to quiz list"></a>
	</div>
	
	<div class="putleft twolistwidediv" >
	<h3>Quiz Info</h3>
		<%	out.println(quiz.toHTML(user_name, dbAccess));	%>
		<div class="endpanel">&nbsp;</div>
	</div>
	
	<div class="endpanel"></div>
	</div>
	
</body>
</html>