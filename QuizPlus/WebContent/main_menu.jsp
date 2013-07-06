<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<style type="text/css">

table.menu_table {

	font-family: arial;

	font-size:16px;

	color:#ffffff;

	border: none;

	border-collapse: collapse;

	padding: 0 0 0 0;

}

table.menu_table td {

	border-width: 1px;

	padding: 12px;

	border-top: none;

	border-bottom: none;

	border-left: 1px solid white;

	border-right: 1px solid white;

	background-color: #222222;

	margin: 0;

	border-collapse: collapse;

	vertical-align: center;

	padding-top: 5;

	padding-bottom: 5;

}



</style>

<html>

<style type="text/css">a {text-decoration: none; color:#ffffff;}</style>

<body bgcolor=#222222>

<table class="menu_table">

<tr>

<td><a href="home.jsp" target="body_frame">Home</a></td>

<%
String user = (String) request.getSession().getAttribute("userName");
if(user == null){
    out.println("<td><a href=\"inbox_redirect.jsp\" target=\"body_frame\">Inbox</a></td>");
}else{	
    out.println("<td><a href=\"inbox_redirect.jsp\" target=\"body_frame\">Inbox</a></td>");
}
%>
<td><a href="quizCreator_main.jsp" target="body_frame">Create Quiz</a></td>
<td><a href="quiz_main.jsp" target="body_frame">Take Quiz</a></td>
<td><a href="Directory.jsp" target="body_frame">Directory</a></td>
<%
//System.out.println(user);
if(user == null){
    out.println("<td><a href=\"logout.jsp\" target=\"body_frame\">Logout</a></td>");
}else{	
    out.println("<td><a href=\"logout.jsp\" target=\"body_frame\">Logout</a></td>");
}
%>


</tr>

</table>

</body>

</html>
