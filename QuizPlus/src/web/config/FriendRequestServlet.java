package web.config;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * Servlet implementation class FriendRequestServlet
 */
@WebServlet("/FriendRequestServlet")
public class FriendRequestServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public FriendRequestServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		DataBaseConnection db = (DataBaseConnection) request.getServletContext().getAttribute("Database");
		HttpSession session = request.getSession();
		String ToUser = (String) session.getAttribute("userName");
		String submitButton = request.getParameter("submitButton");
		String FromUser = request.getParameter("from");
		String ID = request.getParameter("ID");
	
		int id = Integer.parseInt(ID);

		if(submitButton.equals("Accept")){
			db.acceptFriendRequest(FromUser, ToUser);
			db.deleteFriendRequest(ToUser, ID);
			RequestDispatcher dispatch = request.getRequestDispatcher("inbox.jsp?status=accept");
			dispatch.forward(request, response);			
		}else{
			db.deleteFriendRequest(ToUser, ID);
			RequestDispatcher dispatch = request.getRequestDispatcher("inbox.jsp?status=reject");
			dispatch.forward(request, response);			
		}
		
		
//		boolean success = db.SendMessage(fromUser, toUser, message, type);
//		if (success){
//			if(type == 0){
//				RequestDispatcher dispatch = request.getRequestDispatcher("MessageSent.jsp?ToUser=" + toUser + "&Type=Message");
//				dispatch.forward(request, response);				
//			}else if(type == 1){
//				RequestDispatcher dispatch = request.getRequestDispatcher("MessageSent.jsp?ToUser=" + toUser + "&Type=Request");
//				dispatch.forward(request, response);
//			}else if(type == 2){
//				RequestDispatcher dispatch = request.getRequestDispatcher("MessageSent.jsp?ToUser=" + toUser + "&Type=Challenge");
//				dispatch.forward(request, response);
//			}
//		}else{
//			RequestDispatcher dispatch = request.getRequestDispatcher("Unimplemented.jsp");
//			dispatch.forward(request, response);			
//		}	}

	}

}
