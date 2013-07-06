<%@ page import="web.config.*"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<link rel="stylesheet" type="text/css" href="quiz_default.css" />
<title>Send Challenge from User <%= request.getParameter("userName") %></title>
</head>
<body>
<div id="main">
	<div class="organize" >
		<div class="putleft" >
	

<h3>Challenge From 
<% 
String fromUser = (String)  request.getSession().getAttribute("userName");
out.println(fromUser);
%></h3>
 <br>
<form action="MessageSendingServlet" method="post">
	<b>To:</b><input type="text" name="ToUser" 
	<%
	if(request.getParameter("toUser") == null) {
	}else{
		out.println("value=" + request.getParameter("toUser"));
	}	
	%>/><br />
	<b>Message:</b><br />
	<textarea cols="45" rows="9" name="message" >Hi, I would like to challenge you to beat my score</textarea>	
	<b><br>Quiz Name: </b>	
	<input type="hidden" name="messageType" value="2"/>
	<input type="text" name="quizname" value="<%
	String quizname = request.getParameter("quizname");
	if(quizname == null) {
		out.println("");
	}else{
		out.println(quizname);		
	}
	%>"/>
	<br> <br><input type="submit" value="Send Challenge" />
	<input type="button" value="Back" onclick="goBack()" />
</form>

<script type="text/javascript">
function goBack()
  {
  window.history.back()
  }
</script>

		</div>
		<div class="endpanel"></div>		
	</div>
</div>
</body>
</html>