<%@page import="java.text.DateFormat"%>
<%@ page language="java" import="java.sql.*" %>
<%@ page language="java" import="java.util.*" %>
<%@ page language="java" import="web.config.*" %>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//Dtd HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<link rel="stylesheet" type="text/css" href="quiz_default.css" />
<title>Inbox for <%= request.getSession().getAttribute("userName") %></title>
</head>
<body>
<div id="main">
	<div class="organize" >
		<%
		if(request.getParameter("status") == null){
		}else if(request.getParameter("status").equals("accept")){
			out.println("		<div class=\"putWideleft\" >");
			out.println("<b><FONT COLOR=#009900>Friend Request Accepted </FONT><br></b>");
			out.println("&nbsp</div>		<div class=\"endpanel\"></div>		");	
		}else if(request.getParameter("status").equals("reject")){
			out.println("		<div class=\"putWideleft\" >");
			out.println("<b><FONT COLOR=#009900>Friend Request Rejected </FONT><br></b>");
			out.println("&nbsp");
			out.println("&nbsp</div>		<div class=\"endpanel\"></div>		");	
		}

		%>
	
		<div class="putWideleft" >
	  	    <H3>Inbox for <%= request.getSession().getAttribute("userName") %></H3>
			<br>
			<a href="SendMessage.jsp">New Message</a>
			&nbsp

			<a href="SendFriendRequest.jsp">New Friend Request</a>
			&nbsp 

			<a href="SendChallenge.jsp">New Challenge</a>
			<br /> &nbsp <br />

	
<TABLE class="inbox" >
   <TR>
      <TH>From</TH>
      <TH>Subject</TH>
      <TH>Type</TH>   
      <TH>Date</TH>   
      <TH>Delete</TH>   
      
<%
		
		DataBaseConnection db = (DataBaseConnection) request.getServletContext().getAttribute("Database");
		String username = (String) request.getSession().getAttribute("userName");
    		  
    	String deleteID = (String) request.getParameter("deleteID");
    	if(deleteID!=null){
    		db.deleteFriendRequest(username, deleteID);
    	}
    	
 	 	ArrayList<Message> messages = db.getMyinbox(username,-1);
		for(Message message: messages){
			String sender = message.getFrom();
			String message_text = message.getText();
			int endIndex = 30;
			if (message_text.length() < endIndex) endIndex = message_text.length();
			String subject = message_text.substring(0, endIndex) + "...";
			String timestamp = message.getTimestamp().toString();
			int ID = message.getId();
			String type = message.getStringType();
			int read = message.getRead();
			if (read == 0) out.println("<b>");

			//out.println("<TR ALIGN=\"CENTER\">");
			out.println("<tr>");
			out.println("<td>");
			out.println(sender);
			out.println("</td>");
			
			out.println("<td>");
			out.println("<a href=\"message.jsp?id=" +  ID + "\">");
			if (read == 0) out.println("<b><FONT COLOR=#009900>");
			out.println(subject);
			if (read == 0) out.println("</FONT></b>");
			out.println("</a>");
			out.println("</td>");

			out.println("<td>");
			out.println(type);
			out.println("</td>");
			
			out.println("<td>");
			out.println(timestamp);
			out.println("</td>");
			
			out.println("<td>");
			out.println("<a href=\"inbox.jsp?deleteID=" +  ID + "\">");
			out.println("X");
			out.println("</a>");			
			out.println("</td>");
			
			out.println("</tr>");

		}
%>


</TABLE>
	

 &nbsp <br />
<input type="button" value="Back" onclick="goBack()" />

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