<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Insert title here</title>
<link rel="stylesheet" type="text/css" href="quiz_default.css" />


<style>
div.maindiv {
	border: 2px solid red;
	border-radius: 2px;
	padding: 10px;
	margin-top: 10px;
	margin-bottom: 10px;
	width: 1000px;
	left: 50%;
	margin-left: auto;
	margin-right: auto;
	background-color:#f0fff0;
	align: center;
}


div.question_div {
	border: 2px solid green;
	border-radius: 2px;
	padding: 10px;
	margin-top: 10px;
	margin-bottom: 10px;
	width: 800px;
	left: 50%;
	margin-left: auto;
	margin-right: auto;
	background-color:#c0ffc0;
}
	
table {
	border: 1px solid green;
}

tr{
	vertical-align: top;
}

td {
	vertical-align: top;
}


</style>  
  
</head>
<body>
	<script src="jquery.js"></script>
	<script src="quizCreator.js" language="Javascript" type="text/javascript"></script>

	<%
		if (request.getSession().getAttribute("userName") == null) {
			String redirectURL = "home.jsp";
			response.sendRedirect(redirectURL);
		}
	%>

	<!-- <p>User <%= request.getSession().getAttribute("userName") %> is building a quiz</p>   --> 

	<form action="QuizServlet?task=SubmitNewQuizQuestions"
	method="post" onsubmit="prepForSubmit()"
	name="main_form">

		<div id="QuizOptions" class="putleft quizoptiondiv">
		<h3>Quiz Options</h3>
		
		<table class="invisible">
		<tr>
		<td>	
			<table class="invisible">
			<tr>
				<td> Name:
				<td>
					<input type="text" name="new_quiz_name" value="NameYourQuiz">
			<tr>
				<td> Description:
				<td>
					<textarea cols="30" rows="5" name="description" >EnterDescription</textarea>
			<tr>
			</table>
		<td>
			<table class="invisible">
			<tr>
				<td>General Options:
				<td>
					<table class="invisible">
						<tr>
						<td><input id="allow_practice" type="checkbox" name="allow_practice" value="yes">Allow practice quiz
					</table>

			<tr>
				<td> Question presentation:
				<td>
					<table class="invisible">
						<tr><td><input id="one_question_per_page" 	type="checkbox" name="one_question_per_page"	value="yes">One question per page
						<tr><td><input id="immediate_correct" 		type="checkbox" name="immediate_correct" 		value="yes">Immediate correction
						<tr><td><input id="randomize_order" 		type="checkbox" name="randomize_order" 			value="yes">Randomization
					</table>
			</table>
		
		<td>
			<div id="QuizStatus">
				<p>Status goes here</p>
			</div>	
			
		<tr>
			<td>
			<input type="submit" name="SubmitNewQuizQuestions" value="Submit New Quiz" disabled="true" >

			<td>
			<input type="button" value="AddQuestion" onClick="addElement('QuizQuestions')">
			

		</table>
		</div>
			
	
		<div id="QuizQuestions" class="putleft quizdiv" >
		<h3>Question List</h3>
		</div>

	</form>

	<script type="text/javascript">
			$(document).ready( function() {
				updateStatus(default_status_div_name);
			});
	</script>
</body>


</html>
