<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Create Account</title>
</head>
<body>
<h1>The name <%= request.getParameter("userName") %> is already in use</h1>
Please enter another name and password. <br /><br />
<form action="NewAccount" method="post">
	<b>User Name:</b><input type="text" name="userName" /><br /><br />
	<b>Password:</b><input type="text" name="password" />
	<input type="submit" value="Login" />
</form>
</body>
</html>