package dbAccess;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.PreparedStatement;
import java.sql.Statement;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import javax.swing.table.AbstractTableModel;

import quiz.QuizCreateDetails;
import quiz.QuizQuestion;
import quiz.QuizQuestion.QuestionTypes;
import quiz.QuizQuestionFIB;
import quiz.QuizQuestionMC;
import quiz.QuizQuestionPR;
import quiz.QuizQuestionQR;
import quiz.QuizStats;

/**
 * DataBase connection for all quizzes.
 * 
 * @author jayakarr
 * 
 */

public class DBAccess extends AbstractTableModel {

	private boolean DEBUG = false;

	private static final long serialVersionUID = 1L;
	private static String account = "ccs108vigupta";
	private static String password = "eephohsh";
	private static String server = "mysql-user.stanford.edu";
	private static String database = "c_cs108_vigupta";

	private static Connection conn;

	private Object syncConnObject;

	/**
	 * constructor
	 */
	public DBAccess() {

		syncConnObject = new Object();
		try {
			Class.forName("com.mysql.jdbc.Driver");
			conn = DriverManager.getConnection("jdbc:mysql://" + server,
					account, password);
			Statement stmt = conn.createStatement();
			stmt.executeQuery("USE " + database);
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		if (DEBUG)
			System.out.println("Established connection with the  database ");

	}

	@Override
	public int getColumnCount() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int getRowCount() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public Object getValueAt(int arg0, int arg1) {
		// TODO Auto-generated method stub
		return null;
	}

	/**
	 * Creates a quiz and adds it to the QUIZCREATE Table in the database.
	 * 
	 * @param quizName
	 * @param creator
	 * @param date1
	 * @param questionOrderRandom
	 * @param numOfQuestions
	 */
	public void quizCreate(String quizName, String creator, Date date1,
			boolean questionOrderRandom, int numOfQuestions,
			QuizCreateDetails details) {

		DateAndTime dateTime = new DateAndTime(date1);
		String createDate = dateTime.dateForMySQL();
		String questionOrderString = "";
		if (questionOrderRandom)
			questionOrderString = "b'1'";
		else
			questionOrderString = "b'0'";

		String sql2;
		sql2 = "INSERT INTO `c_cs108_vigupta`.`QUIZCREATE`";
		sql2 = sql2
				+ "(`QuizName`, `CreatedBy`, `CreateTime`, `numberofTimesTaken`, `QuestionOrderRandomized`, `numOfQuestions`, "
				+ "`AllowPractice`, `OneQuestionPerPage`, `ImmediateCorrection`, `Description` )";
		sql2 = sql2
				+ " VALUES ('"
				+ quizName
				+ "', '"
				+ creator
				+ "', '"
				+ createDate
				+ "', '0' ,  "
				+ questionOrderString
				+ ", '"
				+ numOfQuestions
				+ "' ,"
				+ String.format(" b'%s' , b'%s' , b'%s' , '%s'",
						details.allow_practice ? "1" : "0",
								details.one_question_per_page ? "1" : "0",
										details.immediate_correct ? "1" : "0",
												details.desciption) + " ); ";
		// INSERT INTO `c_cs108_vigupta`.`QUIZCREATE` (`QuizName`, `CreatedBy`,
		// `CreateTime`, `numberofTimesTaken`) VALUES ('ert', 'ert',
		// CURRENT_TIMESTAMP, '66');
		if (DEBUG || true)
			System.out.println("sql = " + sql2);

		PreparedStatement stmt;

		int count = 0;

		try {
			stmt = conn.prepareStatement(sql2);
			count = stmt.executeUpdate();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		if (DEBUG)
			System.out.println("Added a new Quiz =" + count);

		return;

	}

	/**
	 * Given a quizName returns the following in the object returns all the
	 * information about the quiz. refer to QuizCreateDetails.java for more
	 * information.
	 * 
	 * @param quizName
	 * @return QuizCreateDetails object
	 */

	public QuizCreateDetails getQuizCreateDetails(String quizName) {

		String sql2;
		sql2 = "SELECT *  FROM `QUIZCREATE` WHERE `QuizName` LIKE  '";
		sql2 = sql2 + quizName + "'";
		if (DEBUG)
			System.out.println("sql = " + sql2);
		// SELECT * FROM `QUIZCREATE` WHERE `QuizName` LIKE 'INDIA'

		PreparedStatement stmt;
		ResultSet rs = null;

		try {
			stmt = conn.prepareStatement(sql2);
			rs = stmt.executeQuery();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		// put the result set into a quizCreateDetails variable.
		QuizCreateDetails quizCreateDetailsVar;
		quizCreateDetailsVar = new QuizCreateDetails();

		quizCreateDetailsVar.setQuizName(quizName);
		try {
			String charArray;
			if (rs.next()) {
				charArray = rs.getString("CreatedBy");
				quizCreateDetailsVar.setCreatedBy(charArray);

				charArray = rs.getString("Description");
				quizCreateDetailsVar.desciption = charArray;

				Date date = null;
				date = rs.getTimestamp("CreateTime");

				DateAndTime dateTime = new DateAndTime(date);
				String createDate = dateTime.getDate();
				quizCreateDetailsVar.setCreateDate(createDate);

				String createTime = dateTime.getTime();
				quizCreateDetailsVar.setCreateTime(createTime);

				boolean questionOrder = rs
						.getBoolean("QuestionOrderRandomized");
				quizCreateDetailsVar.setQuestionOrderRandom(questionOrder);

				quizCreateDetailsVar.allow_practice = rs
						.getBoolean("AllowPractice");
				quizCreateDetailsVar.one_question_per_page = rs
						.getBoolean("OneQuestionPerPage");
				quizCreateDetailsVar.immediate_correct = rs
						.getBoolean("ImmediateCorrection");
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		sql2 = "";
		sql2 = "SELECT *  FROM `QUIZSTATS` WHERE `QuizName` LIKE  '";
		sql2 = sql2 + quizName + "'";
		sql2 = sql2 + "ORDER BY   `SCORE` DESC ,  `TotalTimeTakenToSolve`  ";
		if (DEBUG)
			System.out.println("sql = " + sql2);

		try {
			stmt = conn.prepareStatement(sql2);
			rs = stmt.executeQuery();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		int numOfTimesTaken = 0;
		try {

			while (rs.next()) {
				numOfTimesTaken++;
				QuizStats quizStatsVar = new QuizStats();
				quizStatsVar.setQuizName(quizName);
				quizStatsVar.setUserName(rs.getString("UserName"));

				Date date = null;
				date = rs.getTimestamp("TakenTime");

				quizStatsVar.setTakenDate(date);

				quizStatsVar.setScore(rs.getInt("Score"));
				quizStatsVar.setTimeToComplete(rs
						.getLong("TotalTimeTakenToSolve"));

				quizCreateDetailsVar.setStatsForQuiz(quizStatsVar);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		quizCreateDetailsVar.setNumberOfTimesTaken(numOfTimesTaken);

		sql2 = "";
		sql2 = "SELECT AVG (SCORE)  FROM `QUIZSTATS` WHERE `QuizName` LIKE '";
		sql2 = sql2 + quizName + "'";
		if (DEBUG)
			System.out.println("sql to get the average" + sql2);
		try {
			stmt = conn.prepareStatement(sql2);
			rs = stmt.executeQuery();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		try {
			while (rs.next()) {
				quizCreateDetailsVar.setAvg(rs.getFloat(1));
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		sql2 = "";
		sql2 = "SELECT STDDEV(SCORE)  FROM `QUIZSTATS` WHERE `QuizName` LIKE '";
		sql2 = sql2 + quizName + "'";
		if (DEBUG)
			System.out.println("sql to get the standard deviation " + sql2);
		try {
			stmt = conn.prepareStatement(sql2);
			rs = stmt.executeQuery();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		try {
			while (rs.next()) {
				quizCreateDetailsVar.setStd(rs.getFloat(1));
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return quizCreateDetailsVar;
	}

	/**
	 * inserts the question into the DataBase returns an integer for successful
	 * insertion.
	 * 
	 * @param quizQuestionVar
	 * @return int
	 */
	public int insertQuestionToDB(QuizQuestion quizQuestionVar) {

		// first get the last ID from the table where this question needs to go.
		if (quizQuestionVar.getQuestionType() == QuestionTypes.QR) {
			return insertQRQuestion(quizQuestionVar);
		}
		if (quizQuestionVar.getQuestionType() == QuestionTypes.MC) {
			return insertMCQuestion(quizQuestionVar);
		}
		if (quizQuestionVar.getQuestionType() == QuestionTypes.FIB) {
			return insertFIBQuestion(quizQuestionVar);
		}
		if (quizQuestionVar.getQuestionType() == QuestionTypes.PR) {
			return insertPRQuestion(quizQuestionVar);
		}
		return 0;

	}

	/**
	 * insert a Picture Response Question returns an integer for successful
	 * insertion.
	 * 
	 * @param quizQuestionVar
	 * @return
	 */
	private int insertPRQuestion(QuizQuestion quizQuestionVar) {
		if (DEBUG)
			System.out.println("in insertPRQuestion ");
		PreparedStatement stmt;
		ResultSet rs = null;
		String sql2;
		// Insert the new question into QUIZ_FIB table
		// Get the question string out
		ArrayList<String> questions = quizQuestionVar.getQuestionString();
		String question = questions.get(0);
		// Get the answer string out
		ArrayList<String> answers = quizQuestionVar.getCorrectAnswer();
		
		String imgURL = quizQuestionVar.getImageUrl();
		// Prepare the statement
		sql2 = "";
		sql2 = sql2
				+ "INSERT INTO `c_cs108_vigupta`.`QUIZ_PR` ( `question` ,	`imgurl` ,`answer0`, `answer1`, `answer2`, `answer3`, `answer4`, `answer5`, `answer6`, `answer7`, `answer8`, `answer9`)	VALUES ('";
		sql2 = sql2 + question + "', '";
		sql2 = sql2 + imgURL + "'";
		for (int i = 0; i < answers.size(); i++) {
			sql2 = sql2 + ", '" + answers.get(i) + "'";
		}
		for (int i = answers.size(); i < 10; i++) {
			sql2 = sql2 + ",NULL";
		}
		
		sql2 = sql2 + ");";
		
		if (DEBUG)
			System.out.println("sql to insert question = " + sql2);
		int count = 0;
		try {
			stmt = conn.prepareStatement(sql2);
			count = stmt.executeUpdate();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		if (DEBUG)
			System.out.println("Added a new Question to PR  =" + count);

		// Now to get the index of the question
		sql2 = "";
		sql2 = sql2 + "SELECT * FROM `QUIZ_PR` WHERE `question` LIKE '";
		sql2 = sql2 + question + "' AND `answer0` LIKE '";
		sql2 = sql2 + answers.get(0) + "'";
		if (DEBUG)
			System.out.println("sql = " + sql2);
		try {
			stmt = conn.prepareStatement(sql2);
			rs = stmt.executeQuery();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		int id = 0;
		try {
			while (rs.next()) {
				id = rs.getInt("id");
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		if (DEBUG)
			System.out.println("id for new question = " + id);

		// insert the Question in the QUIZDETAILS table
		// get the QuizName
		String quizName = quizQuestionVar.getQuizName();
		// get the questionNum
		int questionNum = quizQuestionVar.getQuestionNumber();
		sql2 = "";
		sql2 = sql2
				+ "INSERT INTO `c_cs108_vigupta`.`QUIZDETAILS` (`QuizName`, `QuestionNumber`, `QuestionType`, `QuestionID`) VALUES ('";
		sql2 = sql2 + quizName + "', '";
		sql2 = sql2 + questionNum + "', '";
		sql2 = sql2 + "PR" + "', '";
		sql2 = sql2 + id + "' );";
		if (DEBUG)
			System.out.println("sql = " + sql2);

		try {
			stmt = conn.prepareStatement(sql2);
			count = count + stmt.executeUpdate();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		if (DEBUG)
			System.out
					.println("Added a new Question to QUIZDETAILS  =" + count);

		return count;
	}

	private int insertFIBQuestion(QuizQuestion quizQuestionVar) {
		if (DEBUG)
			System.out.println("in insertFIBQuestion ");
		PreparedStatement stmt;
		ResultSet rs = null;
		String sql2;
		// Insert the new question into QUIZ_FIB table
		// Get the question string out
		ArrayList<String> questions = quizQuestionVar.getQuestionString();
		String questionbeforeblank = questions.get(0);
		String questionafterblank = questions.get(1);
		// Get the answer string out
		ArrayList<String> answers = quizQuestionVar.getCorrectAnswer();
		
		sql2 = "";
		sql2 = sql2 
				+" INSERT INTO `c_cs108_vigupta`.`QUIZ_FIB` ( `questionbeforeblank`, `answer0`, `answer1`, `answer2`, `answer3`, `answer4`, `answer5`, `answer6`, " ; 
		sql2 = sql2 + "`answer7`, `answer8`, `answer9`, `questionafterblank`) VALUES ('";
		sql2 = sql2 + questionbeforeblank + "'";
		
		for (int i = 0; i < answers.size(); i++) {
			sql2 = sql2 + ", '" + answers.get(i) + "'";
		}
		for (int i = answers.size(); i < 10; i++) {
			sql2 = sql2 + ",NULL";
		}
		sql2 = sql2  + ",'";
		 
		sql2 = sql2 + questionafterblank + "' );";
		if (DEBUG)
			System.out.println("sql to insert question = " + sql2);
		int count = 0;
		try {
			stmt = conn.prepareStatement(sql2);
			count = stmt.executeUpdate();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		if (DEBUG)
			System.out.println("Added a new Question to FIB  =" + count);
	
		sql2 = "";
		sql2 = sql2
				+ "SELECT * FROM `QUIZ_FIB` WHERE `questionbeforeblank` LIKE '";
		sql2 = sql2 + questionbeforeblank + "' AND `answer0` LIKE '";
		sql2 = sql2 + answers.get(0) + "' AND `questionafterblank` LIKE '";
		sql2 = sql2 + questionafterblank + "'";
		if (DEBUG)
			System.out.println("sql = " + sql2);
		try {
			stmt = conn.prepareStatement(sql2);
			rs = stmt.executeQuery();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		int id = 0;
		try {
			while (rs.next()) {
				id = rs.getInt("id");
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		if (DEBUG)
			System.out.println("id for new question = " + id);
		// insert the Question in the QUIZDETAILS table
		// get the QuizName
		String quizName = quizQuestionVar.getQuizName();
		// get the questionNum
		int questionNum = quizQuestionVar.getQuestionNumber();
		sql2 = "";
		sql2 = sql2
				+ "INSERT INTO `c_cs108_vigupta`.`QUIZDETAILS` (`QuizName`, `QuestionNumber`, `QuestionType`, `QuestionID`) VALUES ('";
		sql2 = sql2 + quizName + "', '";
		sql2 = sql2 + questionNum + "', '";
		sql2 = sql2 + "FIB" + "', '";
		sql2 = sql2 + id + "' );";
		if (DEBUG)
			System.out.println("sql = " + sql2);

		try {
			stmt = conn.prepareStatement(sql2);
			count = count + stmt.executeUpdate();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		if (DEBUG)
			System.out
					.println("Added a new Question to QUIZDETAILS  =" + count);

		return count;
    }

	private int insertMCQuestion(QuizQuestion quizQuestionVar) {
		if (DEBUG)
			System.out.println("in insertMCQuestion ");
		PreparedStatement stmt;
		ResultSet rs = null;
		String sql2;
		// Insert the new question into QUIZ_QR table
		// Get the question string out
		ArrayList<String> questions = quizQuestionVar.getQuestionString();
		String question = questions.get(0);
		// Get the answer string out
		ArrayList<String> answers = quizQuestionVar.getCorrectAnswer();
		
		// Get the multiChioice out
		ArrayList<String> multiChoice = quizQuestionVar.getMultipleChoice();

		sql2 = "";
		sql2 = sql2
				+ "INSERT INTO `c_cs108_vigupta`.`QUIZ_MC` ( `question`," ;
		for (int i =0 ; i < 10 ; i++){
			sql2 = sql2 + String.format("`answer%d`,", i);
		}
		sql2 = sql2 + " `choice0`, `choice1`, `choice2`, `choice3`, `choice4`, `choice5`,";
		sql2 = sql2 + " `choice6`, `choice7`, `choice8`, `choice9`) VALUES ('";
		sql2 = sql2 + question + "'";
		for (int i = 0; i < answers.size(); i++) {
			sql2 = sql2 + ", '" + answers.get(i) + "'";
		}
		for (int i = answers.size(); i < 10; i++) {
			sql2 = sql2 + ",NULL";
		}
		
		for (int i = 0; i < multiChoice.size(); i++) {
			sql2 = sql2 + ", '" + multiChoice.get(i) + "'";
		}
		for (int i = multiChoice.size(); i < 10; i++) {
			sql2 = sql2 + ",NULL";
		}
		sql2 = sql2 + ");";
		if (DEBUG)
			System.out.println("sql to insert question = " + sql2);
		int count = 0;
		try {
			stmt = conn.prepareStatement(sql2);
			count = stmt.executeUpdate();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		if (DEBUG)
			System.out.println("Added a new Question to QR  =" + count);
		// Now to get the index of the question
		sql2 = "";
		sql2 = sql2 + "SELECT * FROM `QUIZ_MC` WHERE `question` LIKE '";
		sql2 = sql2 + question + "' AND `answer0` LIKE '";
		sql2 = sql2 + answers.get(0) + "'";

		// SELECT * FROM `QUIZ_MC` WHERE `question` LIKE 'How many States are
		// there in India' AND `answer` LIKE '28'
		// Get the ID for the new Question
		if (DEBUG)
			System.out.println("sql = " + sql2);
		try {
			stmt = conn.prepareStatement(sql2);
			rs = stmt.executeQuery();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		int id = 0;
		try {
			while (rs.next()) {
				id = rs.getInt("id");
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		if (DEBUG)
			System.out.println("id for new question = " + id);
		// insert the Question in the QUIZDETAILS table
		// get the QuizName
		String quizName = quizQuestionVar.getQuizName();
		// get the questionNum
		int questionNum = quizQuestionVar.getQuestionNumber();
		sql2 = "";
		sql2 = sql2
				+ "INSERT INTO `c_cs108_vigupta`.`QUIZDETAILS` (`QuizName`, `QuestionNumber`, `QuestionType`, `QuestionID`) VALUES ('";
		sql2 = sql2 + quizName + "', '";
		sql2 = sql2 + questionNum + "', '";
		sql2 = sql2 + "MC" + "', '";
		sql2 = sql2 + id + "' );";
		if (DEBUG)
			System.out.println("sql = " + sql2);

		try {
			stmt = conn.prepareStatement(sql2);
			count = count + stmt.executeUpdate();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		if (DEBUG)
			System.out
					.println("Added a new Question to QUIZDETAILS  =" + count);

		return count;
	}

	private int insertQRQuestion(QuizQuestion quizQuestionVar) {
		if (DEBUG)
			System.out.println("in insertQRQuestion ");
		PreparedStatement stmt;
		ResultSet rs = null;
		String sql2;
		// Insert the new question into QUIZ_QR table
		// Get the question string out
		ArrayList<String> questions = quizQuestionVar.getQuestionString();
		String question = questions.get(0);
		// Get the answer string out
		ArrayList<String> answers = quizQuestionVar.getCorrectAnswer();
		
		// Prepare the statement
		sql2 = "";
		sql2 = sql2 
				+" INSERT INTO `c_cs108_vigupta`.`QUIZ_QR` ( `question`, `answer0`, `answer1`, `answer2`, `answer3`, `answer4`, `answer5`, `answer6`, " ; 
		sql2 = sql2 + "`answer7`, `answer8`, `answer9`) VALUES ('";
		
		sql2 = sql2 + question + "'";
		for (int i = 0; i < answers.size(); i++) {
			sql2 = sql2 + ", '" + answers.get(i) + "'";
		}
		for (int i = answers.size(); i < 10; i++) {
			sql2 = sql2 + ",NULL";
		}
		
		sql2 = sql2 + ");";
		if (DEBUG)
			System.out.println("sql to insert question = " + sql2);
		int count = 0;
		try {
			stmt = conn.prepareStatement(sql2);
			count = stmt.executeUpdate();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		if (DEBUG)
			System.out.println("Added a new Question to QR  =" + count);

		// Now to get the index number of the auto generated statement

		sql2 = "";

		sql2 = sql2 + "SELECT *  FROM `QUIZ_QR` WHERE `question` LIKE '";
		sql2 = sql2 + question + "' AND `answer0` LIKE '";
		sql2 = sql2 + answers.get(0) + "'";
		if (DEBUG)
			System.out.println("sql to get the index number  " + sql2);

		// SELECT * FROM `QUIZ_QR` WHERE `question` LIKE 'What is the capital of
		// India' AND `answer` LIKE 'New Delhi'

		// Get the ID for the new Question
		if (DEBUG)
			System.out.println("sql = " + sql2);

		try {
			stmt = conn.prepareStatement(sql2);
			rs = stmt.executeQuery();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		int id = 0;
		try {
			while (rs.next()) {
				id = rs.getInt("id");
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		if (DEBUG)
			System.out.println("id for new question = " + id);

		// insert the Question in the QUIZDETAILS table
		// get the QuizName
		String quizName = quizQuestionVar.getQuizName();
		// get the questionNum
		int questionNum = quizQuestionVar.getQuestionNumber();
		sql2 = "";
		sql2 = sql2
				+ "INSERT INTO `c_cs108_vigupta`.`QUIZDETAILS` (`QuizName`, `QuestionNumber`, `QuestionType`, `QuestionID`) VALUES ('";
		sql2 = sql2 + quizName + "', '";
		sql2 = sql2 + questionNum + "', '";
		sql2 = sql2 + "QR" + "', '";
		sql2 = sql2 + id + "' );";
		if (DEBUG)
			System.out.println("sql = " + sql2);

		try {
			stmt = conn.prepareStatement(sql2);
			count = count + stmt.executeUpdate();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		if (DEBUG)
			System.out
					.println("Added a new Question to QUIZDETAILS  =" + count);

		return count;
	}

	public ArrayList<QuizQuestion> getQuestions(String quizName) {

		// Search the QuizDetails to get the quiz.
		if (DEBUG)
			System.out.println("in getQuestions  ");
		PreparedStatement stmt;
		ResultSet rs = null;
		String sql2;
		sql2 = "SELECT * FROM `QUIZDETAILS` WHERE `QuizName` LIKE '";
		sql2 = sql2 + quizName + "'";
		if (DEBUG)
			System.out.println("sql to get questions  = " + sql2);
		// SELECT * FROM `QUIZDETAILS` WHERE `QuizName` LIKE 'India'
		try {
			stmt = conn.prepareStatement(sql2);
			rs = stmt.executeQuery();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		int questionNumber;
		String questionType;
		int questionIndexInType;

		ArrayList<QuizQuestion> questionsReturned = new ArrayList<QuizQuestion>();

		try {
			while (rs.next()) {
				QuizQuestion questionRecreate;

				if(rs.getString("QuestionType").equals("QR")) {
					questionRecreate = new QuizQuestionQR();
				} else if (rs.getString("QuestionType").equals("MC")) {
					questionRecreate = new QuizQuestionMC();
				} else if (rs.getString("QuestionType").equals("FIB")) {
					questionRecreate = new QuizQuestionFIB();
				} else if (rs.getString("QuestionType").equals("PR")) {
					questionRecreate = new QuizQuestionPR();
				} else {
					questionRecreate = null;
				}

				questionNumber = rs.getInt("QuestionNumber");

				questionType = rs.getString("QuestionType");
				questionIndexInType = rs.getInt("QuestionID");
				if (DEBUG)
					System.out.println("questionNumber = " + questionNumber
							+ "questionType = " + questionType
							+ "questionIndexInType = " + questionIndexInType);

				if (questionType.equals(QuestionTypes.FIB.toString())) {
					questionRecreate = recreateFIBQuestion(questionIndexInType);
					questionRecreate.setQuestionNumber(questionNumber);
					questionRecreate.setQuizName(quizName);
					if (DEBUG)
						System.out.println("FIB question = "
								+ questionRecreate.toString());
				}
				if (questionType.equals(QuestionTypes.MC.toString())) {
					questionRecreate = recreateMCQuestion(questionIndexInType);
					questionRecreate.setQuestionNumber(questionNumber);
					questionRecreate.setQuizName(quizName);
					if (DEBUG)
						System.out.println("MC question = "
								+ questionRecreate.toString());
				}
				if (questionType.equals(QuestionTypes.PR.toString())) {
					questionRecreate = recreatePRQuestion(questionIndexInType);
					questionRecreate.setQuestionNumber(questionNumber);
					questionRecreate.setQuizName(quizName);
					if (DEBUG)
						System.out.println("PR question = "
								+ questionRecreate.toString());
				}

				if (questionType.equals(QuestionTypes.QR.toString())) {
					questionRecreate = recreateQRQuestion(questionIndexInType);
					questionRecreate.setQuestionNumber(questionNumber);
					questionRecreate.setQuizName(quizName);
					if(DEBUG)
						System.out.println("QR question = "
								+ questionRecreate.toString());
				}

				questionsReturned.add(questionRecreate);

			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return questionsReturned;
	}

	private QuizQuestionQR recreateQRQuestion(int questionIndexInType) {
		QuizQuestionQR questionToBeReturned = new QuizQuestionQR();
		if (DEBUG)
			System.out.println("in recreate QR question  ");
		PreparedStatement stmt;
		ResultSet rs = null;
		String sql2;
		sql2 = "SELECT *  FROM `QUIZ_QR` WHERE `id` =";
		sql2 = sql2 + questionIndexInType;
		if (DEBUG)
			System.out.println("QR question at index = " + questionIndexInType
					+ " and can be gotten by the following query = " + sql2);
		try {
			stmt = conn.prepareStatement(sql2);
			rs = stmt.executeQuery();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		try {
			while (rs.next()) {
				String question = rs.getString("question");
				questionToBeReturned.setQuestionString(question, 0);
                for (int i =0; i < 10; i++) {
					String iter =  String.format("answer%d", i);
					String answer = rs.getString(iter);
					if (answer != null ){
						questionToBeReturned.setCorrectAnswer(answer, i);
					}
				}

				questionToBeReturned.setQuestionType(QuestionTypes.QR);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return questionToBeReturned;
	}

	private QuizQuestion recreatePRQuestion(int questionIndexInType) {
		QuizQuestionPR questionToBeReturned = new QuizQuestionPR();
		if (DEBUG)
			System.out.println("in recreate PR question  ");
		PreparedStatement stmt;
		ResultSet rs = null;
		String sql2;
		sql2 = "SELECT *  FROM `QUIZ_PR` WHERE `id` =";
		sql2 = sql2 + questionIndexInType;
		if (DEBUG)
			System.out.println("PR question at index = " + questionIndexInType
					+ " and can be gotten by the following query = " + sql2);
		try {
			stmt = conn.prepareStatement(sql2);
			rs = stmt.executeQuery();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		try {
			while (rs.next()) {
				String question = rs.getString("question");
				questionToBeReturned.setQuestionString(question, 0);
				for (int i =0; i < 10; i++) {
					String iter =  String.format("answer%d", i);
					String answer = rs.getString(iter);
					if (answer != null ){
						questionToBeReturned.setCorrectAnswer(answer, i);
					}
				}
                questionToBeReturned.setQuestionType(QuestionTypes.PR);
				String imgurl = rs.getString("imgurl");
				questionToBeReturned.setImageUrl(imgurl);

			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return questionToBeReturned;
	}

	private QuizQuestionMC recreateMCQuestion(int questionIndexInType) {
		QuizQuestionMC questionToBeReturned = new QuizQuestionMC();
		if (DEBUG)
			System.out.println("in recreate MC question  ");
		PreparedStatement stmt;
		ResultSet rs = null;
		String sql2;
		sql2 = "SELECT *  FROM `QUIZ_MC` WHERE `id` =";
		sql2 = sql2 + questionIndexInType;
		if (DEBUG)
			System.out.println("MC question at index = " + questionIndexInType
					+ " and can be gotten by the following query = " + sql2);
		try {
			stmt = conn.prepareStatement(sql2);
			rs = stmt.executeQuery();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		try {
			while (rs.next()) {
				String question = rs.getString("question");
				questionToBeReturned.setQuestionString(question, 0);

				for (int i =0; i < 10; i++) {
					String iter =  String.format("answer%d", i);
					String answer = rs.getString(iter);
					if (answer != null ){
						questionToBeReturned.setCorrectAnswer(answer, i);
					}
					iter =  String.format("choice%d", i);
					String choice = rs.getString(iter);
					if (choice != null ){
						questionToBeReturned.setMultipleChoice(choice, i);
					}
				}
				questionToBeReturned.setQuestionType(QuestionTypes.MC);

			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return questionToBeReturned;
	}

	private QuizQuestionFIB recreateFIBQuestion(int questionIndexInType) {
		QuizQuestionFIB questionToBeReturned = new QuizQuestionFIB();
		if (DEBUG)
			System.out.println("in recreate FIB question  ");
		PreparedStatement stmt;
		ResultSet rs = null;
		String sql2;
		sql2 = "SELECT *  FROM `QUIZ_FIB` WHERE `id` =";
		sql2 = sql2 + questionIndexInType;
		if (DEBUG)
			System.out.println("FIB question at index = " + questionIndexInType
					+ " and can be gotten by the following query = " + sql2);
		try {
			stmt = conn.prepareStatement(sql2);
			rs = stmt.executeQuery();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		try {
			while (rs.next()) {
				String questionbeforeblank = rs
						.getString("questionbeforeblank");
				questionToBeReturned.setQuestionString(questionbeforeblank, 0);
				String questionafterblank = rs.getString("questionafterblank");
				questionToBeReturned.setQuestionString(questionafterblank, 1);
	
				for (int i =0; i < 10; i++) {
					String iter =  String.format("answer%d", i);
					String answer = rs.getString(iter);
					if (answer != null ){
						questionToBeReturned.setCorrectAnswer(answer, i);
					}
				}
				
				questionToBeReturned.setQuestionType(QuestionTypes.FIB);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return questionToBeReturned;
	}

	public boolean getQuestionOrder(String quizName) {
		if (DEBUG)
			System.out.println("in recreate PR question  ");
		PreparedStatement stmt;
		ResultSet rs = null;
		String sql2;

		sql2 = "SELECT *  FROM `QUIZCREATE` WHERE `QuizName` LIKE '";
		sql2 = sql2 + quizName + "'";
		if (DEBUG)
			System.out.println("sql to generate the question Order = " + sql2);
		try {
			stmt = conn.prepareStatement(sql2);
			rs = stmt.executeQuery();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		boolean questionOrder = false;
		try {
			while (rs.next()) {
				questionOrder = rs.getBoolean("QuestionOrderRandomized");

			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return questionOrder;
	}

	public void updateScores(String userName, String quizName,
			int numOfCorrectAnswers, long timeTakenToSolve) {
		Date date = new Date();
		DateAndTime dateTime = new DateAndTime(date);
		String createDate = dateTime.dateForMySQL();

		if (DEBUG)
			System.out.println("Take Date = " + createDate);
		PreparedStatement stmt;

		String sql2;
		sql2 = "INSERT INTO `c_cs108_vigupta`.`QUIZSTATS` (`QuizName` ,`UserName` ,`TakenTime` , `TotalTimeTakenToSolve` ,`SCORE`)VALUES ('";
		sql2 = sql2 + quizName + "', '" + userName + "', '" + createDate
				+ "', '" + timeTakenToSolve + "', '" + numOfCorrectAnswers
				+ "' );";
		if (DEBUG)
			System.out.println("sql to update scores =" + sql2);

		int count = 0;

		try {
			stmt = conn.prepareStatement(sql2);
			count = stmt.executeUpdate();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		if (DEBUG)
			System.out.println("updated a new record  =" + count);

		// Need to update the number of times the test was taken
		sql2 = "";
		sql2 = "SELECT * FROM `QUIZCREATE` WHERE `QuizName` LIKE '";
		sql2 = sql2 + quizName + "'";
		if (DEBUG)
			System.out
			.println("sql to get the Quiz detail for a particular quiz  = "
					+ sql2);
		ResultSet rs = null;
		try {
			stmt = conn.prepareStatement(sql2);
			rs = stmt.executeQuery();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		int numOfTimesTaken = 0;
		try {
			while (rs.next()) {
				numOfTimesTaken = rs.getInt("numberofTimesTaken");
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		numOfTimesTaken++;

		// Add the updated the number of times the test was taken
		String updateQuery = "UPDATE QUIZCREATE  SET numberofTimesTaken  = ? "
				+ " WHERE QuizName = ? ";
		PreparedStatement ps;
		try {
			ps = conn.prepareStatement(updateQuery);
			ps.setInt(1, numOfTimesTaken);
			ps.setString(2, quizName);
			ps.executeUpdate();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		// Update achievements -High Score 
		ArrayList<QuizStats> best_nolater_than_submit = getBestStatsForQuizNoLaterThan(userName, dateTime);
		if(best_nolater_than_submit.size() > 0) {
			if(best_nolater_than_submit.get(0).getUserName().equals(userName)) {
				System.out.println("HIGH SCORE for " + userName);
			}
		}
		
		return;
	}

	public ArrayList<String> getQuizByPopularity(String limit) {
		String filter = String.format("");
		String sort = String.format("ORDER BY `numberofTimesTaken` DESC, `QuizName` DESC %s", limit);
		return getQuizNames(filter, sort);
	}

	public ArrayList<String> getQuizByCreateTime(String limit) {
		String filter = String.format("");
		String sort = String.format("ORDER BY `CreateTime` DESC, `QuizName` DESC %s", limit);
		return getQuizNames(filter, sort);
	}


	public ArrayList<String> getQuizByUserNames(String username, String limit) {
		String filter = String.format("WHERE `CreatedBy` LIKE  '%s' ", username);
		String sort = String.format("ORDER BY `QuizName` DESC %s ", limit);
		return getQuizNames(filter, sort);
	}

	public ArrayList<String> getQuizNames() {
		return getQuizNames("", "ORDER BY `QuizName` DESC");
	}

	public ArrayList<String> getQuizNames(String filter, String sort) {

		PreparedStatement stmt;
		ResultSet rs = null;
		String sql2;
		sql2 = "SELECT * FROM `QUIZCREATE`";

		sql2 += " " + filter;
		sql2 += " " + sort;
		sql2 += ";";

		if (DEBUG)
			System.out.println("sql to get the quizzes = " + sql2);

		ArrayList<String> quizNames = new ArrayList<String>();

		try {
			stmt = conn.prepareStatement(sql2);
			rs = stmt.executeQuery();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		try {
			while (rs.next()) {
				String quizName = rs.getString("QuizName");
				quizNames.add(quizName);

				if (DEBUG)
					System.out.printf("Matched quiz %s\n", quizName);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return quizNames;

	}

	public void insertStatsToDB(QuizStats quizS) {
		boolean DEBUG = true;

		if (DEBUG)
			System.out.println("in insertStatsToDB ");
		PreparedStatement stmt;
		ResultSet rs = null;
		DateAndTime dateTime = new DateAndTime(
				quizS.getTakenDateInDateFormant());
		String createDate = dateTime.dateForMySQL();

		String sql2;
		sql2 = "INSERT INTO `c_cs108_vigupta`.`QUIZSTATS` (`QuizName`, `UserName`, `TakenTime`, `TotalTimeTakenToSolve`, `SCORE`) VALUES ('";
		sql2 = sql2 + quizS.getQuizName() + "', '";
		sql2 = sql2 + quizS.getUserName() + "', '";
		sql2 = sql2 + createDate + "', '";
		sql2 = sql2 + quizS.getTimeToComplete() + "', '";
		sql2 = sql2 + quizS.getScore() + "');";
		if (DEBUG)
			System.out.println("sql to insert question = " + sql2);

		synchronized (syncConnObject) {
			try {
				stmt = conn.prepareStatement(sql2);
				// HACK FIXME RAJ CONFIRM
				stmt.executeUpdate();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		}

		sql2 = "";
		sql2 = "SELECT * FROM `QUIZCREATE` WHERE `QuizName` LIKE '" + quizS.getQuizName()  + "'" ;
		if (DEBUG)
			System.out.println("sql to update the number of times taken  = " + sql2);
		synchronized (syncConnObject) {
			try {
				stmt = conn.prepareStatement(sql2);
				rs = stmt.executeQuery();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

		int numOfTimesTaken =0;
		synchronized (syncConnObject) {
			try {
				while (rs.next()){
					numOfTimesTaken = rs.getInt("numberofTimesTaken");
				}
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

		numOfTimesTaken = numOfTimesTaken + 1; 

		String updateQuery = "UPDATE QUIZCREATE  SET numberofTimesTaken  = ? "
				+ " WHERE QuizName = ? ";
		PreparedStatement ps;

		synchronized (syncConnObject) {
			try {
				ps = conn.prepareStatement(updateQuery);
				ps.setInt(1, numOfTimesTaken);
				ps.setString(2, quizS.getQuizName());
				ps.executeUpdate();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		// Update achievements -High Score
		quizS.setHighScore(false);
		ArrayList<QuizStats> best_nolater_than_submit = getBestStatsForQuizNoLaterThan(quizS.getQuizName(), dateTime);
		if(best_nolater_than_submit.size() > 0) {
			QuizStats qs = best_nolater_than_submit.get(0); 
			
			DateAndTime qs_DateAndTime = new DateAndTime(qs.getTakenDateInDateFormant());
			boolean timestamp_match = qs_DateAndTime.dateForMySQL().equals(dateTime.dateForMySQL()); 
					
			if(DEBUG) 
				System.out.printf("Comparing %s to %s\n", qs_DateAndTime.dateForMySQL(), dateTime.dateForMySQL());
				
			if(qs.getUserName().equals(quizS.getUserName()) && timestamp_match && (quizS.getScore() > 0)) {
				if(DEBUG)
					System.out.println("HIGH SCORE for " + quizS.getUserName());
				quizS.setHighScore(true);
			}
		}



	}

	public ArrayList<QuizStats> getBestStatsForQuizForUser(String quizName,
			String userName) {
		return getStats(String.format(
				"WHERE (`UserName` LIKE  '%s') AND (`QuizName` LIKE  '%s')",
				userName, quizName),
				"ORDER BY `SCORE` DESC ,  `TotalTimeTakenToSolve`, `TakenTime`");
	}

	public ArrayList<QuizStats> getLatestStatsForQuizForUser(String quizName,
			String userName) {
		return getStats(String.format(
				"WHERE (`UserName` LIKE  '%s') AND (`QuizName` LIKE  '%s')",
				userName, quizName),
				"ORDER BY  `TakenTime` DESC, `SCORE` DESC ,  `TotalTimeTakenToSolve`");
	}

	public ArrayList<QuizStats> getBestStatsForQuizForDay(String quizName) {
		return getStats(String.format(
				"WHERE `QuizName` LIKE  '%s'", quizName) +
				// Leave at 15 minutes as per handout
				"AND (`TakenTime` > ( NOW() - INTERVAL 15 MINUTE))",
				"ORDER BY  `SCORE` DESC ,  `TotalTimeTakenToSolve` , `TakenTime`");
	}

	public ArrayList<QuizStats> getBestStatsForQuizNoLaterThan(String quizName, DateAndTime dateTime) {		
		return getStats(String.format(
				"WHERE `QuizName` LIKE  '%s'", quizName) +
				String.format("AND (`TakenTime` <= '%s')", dateTime.dateForMySQL()),
				"ORDER BY  `SCORE` DESC ,  `TotalTimeTakenToSolve`, `TakenTime`");
	}	
	
	public ArrayList<QuizStats> getBestStatsForQuiz(String quizName) {
		return getStats(String.format(
				"WHERE `QuizName` LIKE  '%s'", quizName),
				"ORDER BY `SCORE` DESC ,  `TotalTimeTakenToSolve`, `TakenTime`");
	}

	public ArrayList<QuizStats> getLatestStatsForQuiz(String quizName) {
		return getStats(String.format(
				"WHERE `QuizName` LIKE  '%s'", quizName),
				"ORDER BY  `TakenTime` DESC, `SCORE` DESC ,  `TotalTimeTakenToSolve`");
	}

	public ArrayList<QuizStats> getStatsForUser(String userName, String limit) {
		return getStats(String.format(
				"WHERE `UserName` LIKE  '%s'", userName),
				String.format("ORDER BY  `TakenTime` DESC, `SCORE` DESC ,  `TotalTimeTakenToSolve` %s", limit));
	}

	public ArrayList<QuizStats> getStats(String filter) {
		return getStats(filter,
				"ORDER BY   `SCORE` DESC ,  `TotalTimeTakenToSolve`");
	}

	public ArrayList<QuizStats> getStats(String filter, String sort) {
		boolean DEBUG = true;
		
		if (DEBUG)
			System.out.println("Get the stats for the user");

		String sql2;
		sql2 = "";
		sql2 = "SELECT *  FROM `QUIZSTATS` ";
		sql2 += String.format(" %s ", filter);
		sql2 += String.format(" %s ", sort);
		sql2 += ";";

		if (DEBUG)
			System.out.println("sql = " + sql2);

		PreparedStatement stmt;
		ResultSet rs = null;

		try {
			stmt = conn.prepareStatement(sql2);
			rs = stmt.executeQuery();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		ArrayList<QuizStats> userStats = new ArrayList<QuizStats>();

		try {
			while (rs.next()) {
				QuizStats userStat = new QuizStats();

				userStat.setUserName(rs.getString("UserName"));
				userStat.setQuizName(rs.getString("QuizName"));
				userStat.setScore(rs.getInt("Score"));
				userStat.setTimeToComplete(rs.getLong("TotalTimeTakenToSolve"));
				Date date = null;
				date = rs.getTimestamp("TakenTime");
				userStat.setTakenDate(date);
				userStats.add(userStat);

			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return userStats;

	}

	public ArrayList<QuizCreateDetails> getAllQuizDetails() {

		ArrayList<QuizCreateDetails> quizDetails = new ArrayList<QuizCreateDetails>();

		String sql2 = "";
		sql2 = "SELECT * FROM `QUIZCREATE` ";

		if (DEBUG)
			System.out.println("sql = " + sql2);

		PreparedStatement stmt;
		ResultSet rs = null;

		try {
			stmt = conn.prepareStatement(sql2);
			rs = stmt.executeQuery();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		try {
			while (rs.next()) {
				String quizName = rs.getString("QuizName");
				QuizCreateDetails singleQuizDetails = getQuizCreateDetails(quizName);
				quizDetails.add(singleQuizDetails);

			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return quizDetails;

	}	public int highestScoreByUserByQuiz(String username, String quiz_name) {
		ArrayList<QuizStats> user_stats = getBestStatsForQuizForUser(quiz_name, username);
		int rv = 0;

		if(user_stats.size() > 0) {
			rv = user_stats.get(0).getScore();
		}
		
		if(DEBUG)
			System.out.printf("Returning HS=%d for user=%s on quiz=%s\n", rv, username, quiz_name);

		return rv;	}	public int totalQuizCreated(String username) {		ArrayList<String> user_quiz_creations = getQuizByUserNames(username, "");		return user_quiz_creations.size();	}	public int totalQuizTaken(String username) {		ArrayList<QuizStats> user_stats = getStatsForUser(username, "");		return user_stats.size();	}	public int totalPracticeQuizTaken(String username) {		// TODO Auto-generated method stub		return 0;	}	public boolean highestScoreInATest(String username) {		// TODO Auto-generated method stub		return false;	}
}
