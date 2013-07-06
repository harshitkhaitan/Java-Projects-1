package dbAccess;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.Date;
import java.util.Random;

import org.junit.Test;

import quiz.QuizCreateDetails;
import quiz.QuizStats;

public class DBAccessTest {
	DBAccess dbAccess;
	@Test
	public void testUpdateScores() {
		System.out.println("Init the database");
		dbAccess = new DBAccess();
		
		String strArray [] = {"Raj","Vivek", "Harshit" , "Albert" , "Amir" };
		
		Random generator = new Random();
		long timestart =0 ;
		long timestop =0 ;
		long timediff = timestop - timestart; 
		
		for (int i =0; i < 00; i++){
			int roll = generator.nextInt(5) ;
			System.out.println("Name = " + strArray[roll]);
			String userName = strArray[roll];
			String quizName = "India";
			int numOfCorrectAnswers = generator.nextInt(4);
					
			timestart = System.currentTimeMillis();
			roll = generator.nextInt(5)  * 100; 
			try {
				Thread.sleep(roll);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			timestop = System.currentTimeMillis();
			timediff = timestop - timestart; 
			
			dbAccess.updateScores(userName, quizName, numOfCorrectAnswers, timediff);
			System.out.println("diff  " + timediff  );			
		}
	}
	
	
	@Test 
	public  void testQuizCreateDetails() {
		System.out.println("Init the database from testQuizCreateDetails");
		dbAccess = new DBAccess();
		QuizCreateDetails quizdetails = dbAccess.getQuizCreateDetails("India");
		System.out.println("" + quizdetails);
		
	}
	
	@Test 
	public  void testQuizUpdateState() {
		System.out.println("Init the database testQuizUpdateState");
		dbAccess = new DBAccess();
		QuizStats quizS = new QuizStats();
		quizS.setQuizName("M1000");
		quizS.setUserName("Raj");
		
		Random rn = new Random();
		int n = 5 - 0 + 1;
		int i = rn.nextInt() % n;
		int randomNum =   i;
		if (randomNum < -1 ){
			randomNum = randomNum * -1; 
		}

		quizS.setScore(randomNum);
		Date date = new Date();
		
		quizS.setTakenDate(date);
		
		i = rn.nextInt() % 100000;
		if (i < 0) {
			i = i * -1 ;
		}
		
		quizS.setTimeToComplete(i);
		
		dbAccess.insertStatsToDB(quizS);
		
	}
	
	@Test 
	public  void testgetUserDetails() {
		System.out.println("Init the database");
		dbAccess = new DBAccess();
		
		ArrayList<QuizStats> quizStatsforRaj = dbAccess.getStatsForUser("Raj", "5");
		
		for (QuizStats iter : quizStatsforRaj){
			System.out.println("details = " + iter.toString());
		}
		
		quizStatsforRaj = dbAccess.getStatsForUser("%%","5");
		
		for (QuizStats iter : quizStatsforRaj){
			System.out.println("allUsers  = " + iter.toString());
		}
		
	}
	
	@Test 
	public  void testgetAllDetails() {
		System.out.println("Init the testgetAllDetails");
		dbAccess = new DBAccess();
		ArrayList<QuizCreateDetails> quizDetails = dbAccess.getAllQuizDetails();
		
		for (QuizCreateDetails iter : quizDetails) {
			System.out.println("" + iter) ;
		}
		
	}
}
