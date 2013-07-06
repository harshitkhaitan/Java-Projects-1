package quiz;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;

import org.junit.Test;

import quiz.QuizQuestion.QuestionTypes;

import dbAccess.DBAccess;

public class QuizTest {

	Quiz quizVar;
	Quiz quizGet;
	Quiz quizCheck;
	DBAccess dbAccess;
	@Test
	public void testQuiz() {
		System.out.println("Init the database");
		dbAccess = new DBAccess();
		
		String quizName = "test1";
		String creator = "Raj";
		boolean quiztobeRandomized = true;
		
		QuizCreateDetails quizDetails = new QuizCreateDetails();
		quizDetails.setCreatedBy(creator);
		quizDetails.setQuizName(quizName);
		
		
		quizVar = new Quiz(quizName, creator, dbAccess, quizDetails) ;
		
		
		
		
		
		
		//insert the first question
		String Question = "What is the capital of India";
		String Answer = "New Delhi";
		//quizVar.addQuestion_QR(Question, Answer,0);
		
		System.out.println("Adding Second Question"  );
		Question = "How many States are there in India";
		Answer = "28";
		ArrayList<String> multiplechoices = new ArrayList<String>();
		String MC = "25";multiplechoices.add(MC);
		MC = "26";multiplechoices.add(MC);
		MC = "28";multiplechoices.add(MC);
		MC = "30";multiplechoices.add(MC);
		
		//quizVar.addQuestion_MC(Question, Answer,multiplechoices, 1);
		
		Question = "How many union territories are there in India?";
		Answer = "7";
		multiplechoices.clear();
		MC = "25";multiplechoices.add(MC);
		MC = "7";multiplechoices.add(MC);
		MC = "9";multiplechoices.add(MC);
		MC = "10";multiplechoices.add(MC);
		Collections.shuffle(multiplechoices);
		
		//quizVar.addQuestion_MC(Question, Answer,multiplechoices, 2);
		
		Question = "India is the  ";
		Answer   = "seventh-largest"; 
		String Question1 = "country by area; " ; 
		ArrayList<String> questions= new ArrayList<String>();
		questions.add(Question);
		questions.add(Question1);
		
		//quizVar.addQuestion_FIB(questions, Answer, 3);
		
		Question = "Which country's flag is this ? ";
		Answer = "India";
		String imageUrl = "http://en.wikipedia.org/wiki/File:Flag_of_India.svg";
		//quizVar.addQuestion_PR(Question,Answer,imageUrl,4);
		
		//quizVar.saveQuiz();
		//Get the quiz details
		QuizCreateDetails quizCreateDetails = quizVar.getQuizCreateDetails("India");
		assertEquals ("Raj", quizCreateDetails.getCreatedBy());
		System.out.println("Quiz Details " + quizCreateDetails.toString()  );
		
		
		//Now get the instanced quiz.
		quizGet = new Quiz(quizName,  dbAccess);
		
		 ArrayList<QuizQuestion> questionsinQuiz = quizGet.getQuestionsinQuiz();
		
		Iterator <QuizQuestion> question_itr = questionsinQuiz.iterator();
		
		System.out.println("All the questions for the Quiz");
		while(question_itr.hasNext()){
			QuizQuestion this_question = question_itr.next();
			if (this_question.getQuestionType() == QuestionTypes.PR ){
				this_question.setResponse("India");
			}
			if (this_question.getQuestionType() == QuestionTypes.FIB ){
				this_question.setResponse("BLAH");
			}
			System.out.println("Question = " + this_question.toString());
		}
		
		
		long timestart =  System.currentTimeMillis();
		
		long timestop =  System.currentTimeMillis();
		
		long timeTaken = 20002 ;
		//quizGet.checkResponses("Raj",timeTaken);
		
		ArrayList<String> quizNames =   dbAccess.getQuizNames();
		
		System.out.println("quizNames =" + quizNames);
		
		/*
		questionsinQuiz = quizGet.getQuestionsinQuiz();
		question_itr = questionsinQuiz.iterator();
		System.out.println("All the questions for the Quiz");
		while(question_itr.hasNext()){
			QuizQuestion this_question = question_itr.next();
			System.out.println("Question = " + this_question.toString());
		}
		*/
		
	}

}
