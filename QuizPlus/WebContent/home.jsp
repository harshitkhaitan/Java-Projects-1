<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>QuizPlus Home%></title>
</head>
<body>
<%
String user = (String) request.getSession().getAttribute("userName");
if((String) request.getSession().getAttribute("userName") == null){
	String redirectURL = "index.jsp?status=login";
    response.sendRedirect(redirectURL);
}else if(user.equals("")){
	String redirectURL = "index.jsp?status=login";
    response.sendRedirect(redirectURL);		
}else{
	String redirectURL = "WelcomeUser.jsp";
    response.sendRedirect(redirectURL);
}
%>
</body>
</html>