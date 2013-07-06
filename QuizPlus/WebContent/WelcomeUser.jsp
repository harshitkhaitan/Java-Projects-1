<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
    
<%@ page import="web.config.*"%>
<%@ page import="dbAccess.*"%>
<%@ page import="quiz.*"%>
<%@ page import="java.util.ArrayList"%>
<%@ page import="java.util.Iterator"%>


<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<link rel="stylesheet" type="text/css" href="quiz_default.css" />
<title>Welcome <%= request.getSession().getAttribute("userName") %></title>
</head>

<body>
<%
	String user_name = (String) session.getAttribute("userName");
	DataBaseConnection db = (DataBaseConnection) request.getServletContext().getAttribute("Database");
	DBAccess dbAccess = (DBAccess) session.getAttribute("dbAccess");
	ArrayList<Message> messages = db.getMyinbox(user_name,-1);
	int new_messages=0;
	int new_friend_request=0;
	int new_challenge=0;
	int new_stuff=0;
	for(Message msg: messages){
		if(msg.getRead()==0){
			new_stuff++;
			if(msg.getType()==0) new_messages++;
			if(msg.getType()==1) new_friend_request++;
			if(msg.getType()==2) new_challenge++;
		}
	}	
%>

<div id="main">
	<div class="organize" >
		<div class="putleft" >
			<b>Welcome <%= request.getSession().getAttribute("userName") %></b>
		</div>
		<div class="endpanel"></div>		
		<div class="putleft" >
		<br /><a href="inbox.jsp">Inbox (<% out.println(new_messages + " New Message, " + new_friend_request + " New Friend Request, " + new_challenge + " New Challenge "); %>)</a>
		<br /><a href="friendList.jsp">Manage Friends</a>
		<br /><a href="SendMessage.jsp">Send a Message</a>
		<br /><a href="SendChallenge.jsp">Send a Challenge</a>
		<br /><a href="SendFriendRequest.jsp">Send a Friend Request</a>		
		</div>
		
		<div class="endpanel"></div>
		
	</div>
		
	<div class="organize" >
		
		<div class="putleft" >
		<h3>FriendZone</h3>
			<%
				ArrayList<Friend> friends = db.getFriends(user_name);
			
				if (friends != null) {
			
					ArrayList<String> friend_creations = new ArrayList<String>();
					ArrayList<QuizStats> friend_quiz_stats = new ArrayList<QuizStats>();
			
					for (Friend friend : friends) {
						String friend_name = friend.getUsername();
						friend_creations.addAll(dbAccess.getQuizByUserNames(friend_name, "LIMIT 1"));
						friend_quiz_stats.addAll(dbAccess.getStatsForUser(friend_name, "LIMIT 1"));
					}
					out.println("<p>Recent quizzes created</p>");
					out.println(Quiz.toHtmlTable(friend_creations, "", dbAccess));
					
					out.println("<p>Recent quizzes taken</p>");
					out.println(QuizStats.toHtmlTable(friend_quiz_stats, ""));
				
				} else {
					out.println("<p>No friends found</p>");
				}
				
			%>
		</div>
		
		<div class="putleft" >
		<h3>UserActivity</h3>
		
			<p> Achievements </p>
			<%
				ArrayList<Achievement> achievments = db.getUserAchievements(user_name, dbAccess);
				out.println(Achievement.toHtmlTable(achievments, ""));
			%>
			
			<p> Quiz Creations</p>
			<%
				ArrayList<String> user_quiz_names = dbAccess.getQuizByUserNames(user_name, "LIMIT 5");
				out.println(Quiz.toHtmlTable(user_quiz_names, "", dbAccess));
			%>

			<p> Recent Quiz Attempts</p>
			<%
				ArrayList<QuizStats> quiz_stats = dbAccess.getStatsForUser(user_name, "LIMIT 5");
				out.println(QuizStats.toHtmlTable(quiz_stats, ""));
			%>
			
			
		</div>
		
		<div class="putleft" >
		<h3>SiteActivity</h3>
		<p> Popular Quizzes</p>
			<%
				// FIXME VIVEK: move method to Quiz
					ArrayList<String> popular_quiz_names = dbAccess.getQuizByPopularity("LIMIT 5");
					out.println(Quiz.toHtmlTable(popular_quiz_names, "", dbAccess));
			%>
			
			<p> Recent Quiz Creations</p>
			<%
				// FIXME VIVEK: move method to Quiz
					ArrayList<String> new_quiz_names = dbAccess.getQuizByCreateTime("LIMIT 5");
					out.println(Quiz.toHtmlTable(new_quiz_names, "", dbAccess));
			%>
		</div>
		
		<div class="endpanel"></div>
	</div>
</div>


</body>
</html>