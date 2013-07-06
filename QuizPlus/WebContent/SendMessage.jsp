<%@ page import="web.config.*"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<link rel="stylesheet" type="text/css" href="quiz_default.css" />
<title>Send Message from User <%= request.getParameter("userName") %></title>
</head>
<body>
<div id="main">
	<div class="organize" >
		<div class="putleft" >
	

<h3>Message From 
<% 
String fromUser = (String)  request.getSession().getAttribute("userName");
out.println(fromUser);
%></b></h3>
 <br />
<form action="MessageSendingServlet" method="post">
	<b>To:</b><input type="text" name="ToUser" 
	<%
	if(request.getParameter("toUser") == null) {
	}else{
		out.println("value=" + request.getParameter("toUser"));
	}	
	%>/><br /> <br />
	<b>Message:</b><br />
	<textarea cols="45" rows="9" name="message" ></textarea>	
	<input type="hidden" name="messageType" value="0"/>
	<br> <br /><input type="submit" value="Send" />
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