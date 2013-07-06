package web.config;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import dbAccess.DBAccess;

/**
 * Servlet implementation class MessageSendingServlet
 */
@WebServlet("/MessageSendingServlet")
public class MessageSendingServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public MessageSendingServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doPost( request,  response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		DataBaseConnection db = (DataBaseConnection) request.getServletContext().getAttribute("Database");
		DBAccess dbAccess = (DBAccess) request.getSession().getAttribute("dbAccess");
		HttpSession session = request.getSession();
		String fromUser = (String) session.getAttribute("userName");
		String toUser = request.getParameter("ToUser");
		String message = request.getParameter("message");
		String typeSring = (String) request.getParameter("messageType");
		String quiz_name = (String) request.getParameter("quizname");
		int bestScore = dbAccess.highestScoreByUserByQuiz(fromUser,quiz_name);
        int type = Integer.parseInt(typeSring);
		
		boolean success = db.SendMessage(fromUser, toUser, message, type, quiz_name, bestScore);
		if (success){
			if(type == 0){
				RequestDispatcher dispatch = request.getRequestDispatcher("MessageSent.jsp?ToUser=" + toUser + "&Type=Message");
				dispatch.forward(request, response);				
			}else if(type == 1){
				RequestDispatcher dispatch = request.getRequestDispatcher("MessageSent.jsp?ToUser=" + toUser + "&Type=Request");
				dispatch.forward(request, response);
			}else if(type == 2){
				RequestDispatcher dispatch = request.getRequestDispatcher("MessageSent.jsp?ToUser=" + toUser + "&Type=Challenge");
				dispatch.forward(request, response);
			}
		}else{
			RequestDispatcher dispatch = request.getRequestDispatcher("BadRequest.jsp");
			dispatch.forward(request, response);			
		}	}

}
