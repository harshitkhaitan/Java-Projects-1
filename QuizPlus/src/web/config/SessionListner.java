package web.config;

import static org.junit.Assert.assertEquals;

import javax.servlet.annotation.WebListener;
import javax.servlet.http.HttpSession;
import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;

import dbAccess.DBAccess;

import quiz.Quiz;
import quiz.QuizQuestion;
import quiz.QuizQuestion.QuestionTypes;

/**
 * Application Lifecycle Listener implementation class SessionListner
 *
 */
@WebListener
public class SessionListner implements HttpSessionListener {

	//Quiz current_quiz;
	DBAccess dbAccess;
	
	// Current user on session
	String username;
	
	// HACK FIXME: temp holder till Quiz supplies questions
	QuizQuestion [] current_questions;

	/**
	 * Default constructor. 
	 */
	public SessionListner() {
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see HttpSessionListener#sessionCreated(HttpSessionEvent)
	 */
	public void sessionCreated(HttpSessionEvent event) {

        HttpSession session = event.getSession();

		// Setup DB
		dbAccess = new DBAccess();
		session.setAttribute("dbAccess", dbAccess);
	
//		username = "SomeoneSpecial";
//		session.setAttribute("username", username);

	}

	/**
	 * @see HttpSessionListener#sessionDestroyed(HttpSessionEvent)
	 */
	public void sessionDestroyed(HttpSessionEvent arg0) {
		// TODO Auto-generated method stub
	}

}
