<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Bad request from <%= request.getParameter("userName") %></title>
</head>
<body>
<b>STOP</b>
Either the username your are sending to does not exist, or something is wrong with your request. <br />
If you are sending a friend request, this may mean that the either the person is already your friend, or has blocked you. Sorry !

<script type="text/javascript">
function goBack()
  {
  window.history.back()
  }
</script>
<input type="button" value="Back" onclick="goBack()" />

</body>
</html>