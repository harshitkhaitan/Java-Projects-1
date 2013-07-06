package web.config;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.Scanner;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import dbAccess.DBAccess;

import quiz.Quiz;
import quiz.QuizCreateDetails;
import quiz.QuizQuestion;
import quiz.QuizQuestion.QuestionTypes;

/**
 * Servlet implementation class QuizServlet
 */
@WebServlet("/QuizServlet")
public class QuizServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private static final boolean DEBUG = false;

	Quiz current_quiz;	

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public QuizServlet() {
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

		HttpSession session = request.getSession();

		String username = (String) session.getAttribute("userName");
		System.out.printf("\n\nRunning with username=%s\n", username);

		response.setContentType("text/html");
		PrintWriter out = response.getWriter();

		//current_quiz = (Quiz) session.getAttribute("current_quiz");

		out.println(returnHeader(current_quiz));

		DBAccess dbAccess = (DBAccess) session.getAttribute("dbAccess");
		session.setAttribute("current_quiz", current_quiz);

		if (request.getParameter("task").equals("SubmitNewQuizQuestions")) {
			System.out.println("Running task=SubmitNewQuizQuestions");
			// Save new quiz
			create_quiz(request, out, dbAccess);
			
			// All done, hop to summary page
			
			//http://localhost:8080/QuizPlus/quiz_summary.jsp?quiz_name=Q_V2
			//	request.
			RequestDispatcher rd = request.getRequestDispatcher(String.format("quiz_summary.jsp?quiz_name=%s", request.getParameter("new_quiz_name")));
			rd.forward(request, response);
				
		} else if (request.getParameter("task").equals("TakeQuiz") || request.getParameter("task").equals("PracticeQuiz")) {
			System.out.println("Running task=TakeQuiz|PracticeQuiz");
			// Begin new quiz

			//String quiz_name = (String) request.getParameter("QuizTableSubmit");
			String quiz_name = request.getParameter("quiz_name");
			current_quiz = new Quiz (quiz_name, dbAccess);

			if(request.getParameter("task").equals("PracticeQuiz")) {
				current_quiz.in_practice_mode = true;
			} else {
				current_quiz.in_practice_mode = false;
			}
			current_quiz.score2.setUserName(username);


			session.setAttribute("current_quiz", current_quiz);

			if(current_quiz.details.one_question_per_page) {
				out.println(returnQuizForm(current_quiz, 0));	
			} else {
				out.println(returnQuizForm(current_quiz, -1));
			}
		} else if (request.getParameter("task").equals("GradeQuestions")) {
			System.out.println("Running task=GradeQuestions");
			// Continue current quiz
			current_quiz = (Quiz) session.getAttribute("current_quiz");
			gradeQuiz(  request,  out,  dbAccess,  response); 
		} else {
			out.println("ERROR: Malformed POST:" + request.getQueryString());
		}

		out.println(returnFooter());
	}

	String returnQuizForm (Quiz q, int question_num) {
		return returnQuizFormAndGrade(q,question_num, false);
	}

	String returnQuizFormGraded (Quiz q, int question_num) {
		return returnQuizFormAndGrade(q,question_num, true);
	}


	String returnQuizFormAndGrade (Quiz q, int question_num, boolean show_grade) {
		System.out.printf("Checking question %d\n", question_num);
		String rvNav = "";
		String submit_str = " action=QuizServlet?task=GradeQuestions method=post ";

		if(question_num == -1) {
			rvNav += jq(""
					+"<p>"
					+"<input type=|submit| name=|submitButton| value=|All Done| >"
					+"</p>"
					);
		} else {
			rvNav += jq(""
					+"<p>"
					+"<input type=|submit| name=|submitButton| value=|All Done| >"
					+"<input type=|submit| name=|submitButton| value=|Next| >"
					+"</p>"
					);

			if(show_grade) {
				if(q.getQuestionsinQuiz().get(question_num).isGraded()) {
					if(q.getQuestionsinQuiz().get(question_num).getScore() > 0) {
						rvNav += jq("<p><img src=|images/right_answer.png|></p>");
					} else {
						rvNav += jq("<p><img src=|images/wrong_answer.png|></p>");
					}
				} else {
					rvNav += jq("<p>Unknown Grade!</p>");
				}

			}

		}

		long quiz_start_time;

		if(q == null) {
			quiz_start_time = -1;
		} else {
			quiz_start_time = q.clientTime;
		}


		String rv = "";
		if(DEBUG)
			rv += String.format("Called for Quiz %s (%d)<br>\n", q.getQuizName(), question_num);
		rv += jq("<form id=|question_form| "+submit_str+">"
				+"<input type=|hidden| name=|quiz_start_time| id=|quiz_start_time| value=|"+quiz_start_time+"| />"
				+"<input type=|submit| name=|submitButton| value=|->| style=|display: none| >");

		{	
			if(question_num == -1) {
				Iterator <QuizQuestion> question_itr = current_quiz.getQuestionsinQuiz().iterator();
				while(question_itr.hasNext()){
					QuizQuestion this_question = question_itr.next();
					
					rv += jq("<div class=|putclear quizdiv|>");
					rv += jq(String.format("<h3>Question #%d</h3>", this_question.getQuestionNumber()));
					rv += returnQuestionForm(this_question);
					rv += jq("</div>");
				}
			} else {
				QuizQuestion this_question = current_quiz.getQuestionsinQuiz().get(question_num);
				rv += returnQuestionForm(this_question);
			}
		}
		rv += rvNav;
		rv += "</form>\n";

		return rv;
	}

	String returnQuestionForm (QuizQuestion q) {
		return q.toHtmlForm();
	}

	String returnHeader (Quiz q) {
		long quiz_start_time;

		if(q == null) {
			quiz_start_time = -1;
		} else {
			quiz_start_time = q.clientTime;
		}

		return jq(



				"<html>" +
						"<head>" +
						"<title>Insert title here</title>" +
						"<body>" +
						"<script src=|jquery.js|></script>" +
						"<script type=|text/javascript| src=|quizTimeKeeper.js|></script>" +
						"<link rel=|stylesheet| type=|text/css| href=|quiz_default.css| />" +

						

				// Borrowed recipe to eat Enter key
				"<script type=|text/javascript|>" +
				"function stopRKey(evt) {" +
				"  var evt = (evt) ? evt : ((event) ? event : null);" +
				"  var node = (evt.target) ? evt.target : ((evt.srcElement) ? evt.srcElement : null);" +
				"  if ((evt.keyCode == 13) && (node.type==|text|))  {return false;}" +
				"}" +
				"document.onkeypress = stopRKey;" +
				"</script>" +


						"<div class=|putclear quizdiv quizclockdiv|><h3>Time Keeper</h3><label id=|clock_txt|></label></div>"
						//+"<div id=|txt2| style=|display : none;|>"
						//+"-1"
						//+"</div>"
				);
	}

	String returnFooter () {
		return jq(
				"\n" +
						"" +

				"<script type=|text/javascript|>" +
				"$(window).ready( function() {"+
				"startQuizTimer();"+
				"});"+
				"</script>"+

				"</body>" +
				"</html>" +
				"</head>" +
				"");
	}


	void turnInQuestion (HttpServletRequest request, QuizQuestion q) {
		System.out.println("Begin turnInQuestion");

		if(true) {
			String resp_field = q.jspPrefix() + "_Answer";
			String answer = request.getParameter(resp_field);
			System.out.printf("Received response %s=%s\n", resp_field, answer);
			q.setResponse(answer);
		}
	}

	void turnInEntireQuiz (HttpServletRequest request) {
		System.out.println("Begin turnInEntireQuiz");
		// Fill in responses
		// FIXME VIVEK: This causes a DB access!
		// ArrayList<QuizQuestion> getQuestionsinQuiz() = current_quiz.getQuestionsinQuiz();

		Iterator <QuizQuestion> question_itr = current_quiz.getQuestionsinQuiz().iterator();
		while(question_itr.hasNext()){
			QuizQuestion this_question = question_itr.next();
			turnInQuestion(request, this_question);
			System.out.println("+");
		}
	}




	public static String jq (String s) {
		return s.replace('|', '"');
	}


	void create_quiz( HttpServletRequest request, PrintWriter out, DBAccess dbAccess) {
		String quiz_name = request.getParameter("new_quiz_name");
		String creator = (String) request.getSession().getAttribute("userName");

		String idx_ordinal_map_asString = request.getParameter("idx_ordinal_map_asString");
		String idx_type_map_asString 	= request.getParameter("idx_type_map_asString");

		String description = request.getParameter("description");

		boolean allow_practice 			= (request.getParameter("allow_practice") != null);
		boolean one_question_per_page 	= (request.getParameter("one_question_per_page") != null);
		boolean immediate_correct 		= (request.getParameter("immediate_correct") != null);
		boolean randomize_order 		= (request.getParameter("randomize_order") != null);

		//System.out.println("allow_practice = "+allow_practice);
		QuizCreateDetails details = new QuizCreateDetails(				
				quiz_name,
				creator,
				0,
				randomize_order,
				allow_practice, 
				one_question_per_page, 
				immediate_correct, 
				description
				);
		current_quiz = new Quiz(quiz_name, creator, dbAccess, details);

		System.out.printf("\nQuiz Details=" + details);
		System.out.printf("\nReceived ordinal map=%s\n", idx_ordinal_map_asString);
		System.out.printf("\nReceived type map=%s\n", idx_type_map_asString);

		String [] idx_ordinal_map = idx_ordinal_map_asString.split(",");
		String [] idx_type_map = idx_type_map_asString.split(",");

		for(int i = 0; i < idx_ordinal_map.length; i++) {
			if(!idx_ordinal_map[i].equals("-1")) {
				System.out.printf("[i]->[%s][%s]\n", i, idx_type_map[i], idx_ordinal_map[i]);
				String tag = String.format("Question_Div_%s", i);

				int num = Integer.parseInt(idx_ordinal_map[i]);

				if(idx_type_map[i].equals("QR")) {
				    
					current_quiz.addQuestion_QR (
							request.getParameter(tag+"_Question"),
							textareaToArrayList (request.getParameter(tag+"_Answer")),
							num
							);

				} else if (idx_type_map[i].equals("MC")) {

					int choice_count = Integer.parseInt(request.getParameter(tag+"_MC_ChoiceCount"));

					ArrayList <String> multiplechoices = new ArrayList <String> (choice_count);
					for(int j = 0; j < choice_count; j++) {
						String this_choice = request.getParameter(tag+ "_MC_idx" +j+ "_ChoiceText");
						multiplechoices.add(this_choice);
						System.out.printf("Adding Choice %d:%s\n", j, this_choice);
					}

					current_quiz.addQuestion_MC (
							request.getParameter(tag+"_Question"),
							request.getParameter(tag+"_Answer"),
							multiplechoices,
							num
							);
				} else if (idx_type_map[i].equals("FIB")) {
					ArrayList <String> question_strings = new ArrayList <String> ();
					question_strings.add(request.getParameter(tag+"_Question_0"));
					question_strings.add(request.getParameter(tag+"_Question_1"));

					current_quiz.addQuestion_FIB (
							question_strings,
							textareaToArrayList (request.getParameter(tag+"_Answer")),
							num
							);
				} else if (idx_type_map[i].equals("PR")) {
					current_quiz.addQuestion_PR (						
							request.getParameter(tag+"_Question"),
							textareaToArrayList (request.getParameter(tag+"_Answer")),
							request.getParameter(tag+"_URL"),
							num
							);
				}
			}
		}
		current_quiz.saveQuiz();
	}

	void gradeQuiz( HttpServletRequest request, PrintWriter out, DBAccess dbAccess, HttpServletResponse response) throws ServletException, IOException { 

		// Cleanup default button state if Enter is hit
		String submitButton = request.getParameter("submitButton");
		long clientTime = 0;
		try {
			clientTime = Long.parseLong(request.getParameter("quiz_start_time"));
		} catch (NumberFormatException e) {
			System.out.println("Num == " + request.getParameter("quiz_start_time"));
			e.printStackTrace();
		}
		current_quiz.clientTime = clientTime;

		if(submitButton == null) {
			submitButton = "All Done";
		}
		
		QuizQuestion current_question = current_quiz.getQuestionsinQuiz().get(current_quiz.current_question);	

		
		// Last question of quiz, its all done!
		if(	(current_quiz.current_question == (current_quiz.getQuestionsinQuiz().size()-1)) // Last Question
			&&
			!(current_quiz.details.immediate_correct && !current_question.isGraded())	// Give immediate feedback
		) {
			submitButton = "All Done";
		}
		
		//System.out.println("Submit="+request.getParameter("submitButton"));

		if(submitButton.equals("All Done")){
			// Submit entire quiz
			if(current_quiz.details.one_question_per_page && !current_quiz.details.immediate_correct) {
				turnInQuestion(request, current_question);
			} else {
				System.out.println("Turning in answers for entire quiz!");
				turnInEntireQuiz(request);
			}

			// All questions are in, grade them now and display
			current_quiz.gradeQuiz();
			if(!current_quiz.in_practice_mode) {
				System.out.println(current_quiz.score2);
				current_quiz.submitScore();
			} else {
				System.out.println("PRACTICE MODE:");
				System.out.println(current_quiz.score2);
			}
			request.getRequestDispatcher("quizTaker_score_summary.jsp").forward(request, response);
		} else {
			// Navigate quiz

			// Save form results to question tab
			turnInQuestion(request, current_question);

			if(current_quiz.details.immediate_correct && !current_question.isGraded()) {
				// Question cannot be modified, grade now
				current_question.checkResponses();
				out.println(returnQuizFormGraded(current_quiz, current_quiz.current_question));
			} else {
				current_question.checkResponses();

				// Next question
				current_quiz.current_question += 1;
				out.println(returnQuizForm(current_quiz, current_quiz.current_question));
			}
		}
	}
	
	//request.getParameter(tag+"_Answer")
	private ArrayList <String> textareaToArrayList (String text) {
		ArrayList <String> rv = new ArrayList<String>();
	    Scanner s = new Scanner(text);
	    while(s.hasNextLine()) {
	    	rv.add(s.nextLine());
	    }
	    return rv;
	}
	
	
}

