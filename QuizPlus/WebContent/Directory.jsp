<%@ page import="web.config.*"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<link rel="stylesheet" type="text/css" href="quiz_default.css" />
<title>Send Message from User <%= request.getParameter("userName") %></title>

<script type="text/javascript">
<!--
function gotoURL() {
var newURL = document.url2go.go.value
fullurl = "profile.jsp?username="+ newURL;
document.location.href=fullurl
}
//-->
</script>

</head>
<body>

	<%
		if (request.getSession().getAttribute("userName") == null) {
			String redirectURL = "home.jsp";
			response.sendRedirect(redirectURL);
		}
	%>
	
<div id="main">
	<div class="organize" >
		<div class="putleft" >
	

			<h3>Directory</h3>
 			<br />
			Enter Username 

			<form action="javaScript:gotoURL()" method="get" name="url2go">
			<input type="text" name="go" value="" size="50">
			<input type="submit" value="Go">
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