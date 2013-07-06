package quiz;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

import quiz.QuizQuestion.QuestionTypes;

public class QuizQuestionTest {

	public QuizQuestion quizQuestionVar;
	
	 
	@Test
	public void test1() {
		QuestionTypes questionType;
		questionType = QuestionTypes.QR;
		String quizName = "India";
		ArrayList<String> questions= new ArrayList<String>();
		String Question = "What is the capital of India";
		questions.add(Question);
		
		ArrayList<String> answers = new ArrayList<String>();
		String Answer = "New Delhi";
		answers.add(Answer);
		
		ArrayList<String> multiplechoices = new ArrayList<String>();
		String imgURL = "";
		
		QuizQuestion quizQuestionVar = new  QuizQuestionQR(quizName , 0 , questions, answers, multiplechoices, imgURL );
		
		
		
		
		
		
		assertEquals(true, quizQuestionVar.addQuestionToQuiz());
		
	}
	
	
	@Test
	public void test2() {
		QuestionTypes questionType;
		questionType = QuestionTypes.FIB;
		String quizName = "India";
		
		ArrayList<String> questions= new ArrayList<String>();
		String Question = "India  is officially called ";
		questions.add(Question);
		Question = "  ";
		questions.add(Question);
		
		ArrayList<String> answers = new ArrayList<String>();
		String Answer ="Republic of India" ;
		answers.add(Answer);
		ArrayList<String> multiplechoices = new ArrayList<String>();
		String imgURL = "";
		
		QuizQuestion quizQuestionVar = new  QuizQuestionFIB( quizName , 1 , questions, answers, multiplechoices, imgURL);
		assertEquals(true, quizQuestionVar.addQuestionToQuiz());
		
		System.out.println("Question = " + quizQuestionVar.toString() );
		
		
		
		
		
		
		
	}
	
	
	@Test
	public void test3() {
		QuestionTypes questionType;
		questionType = QuestionTypes.MC;
		String quizName = "India";
		
		ArrayList<String> questions= new ArrayList<String>();
		String Question = "How many States are there in India ";
		questions.add(Question);
		
		ArrayList<String> answers = new ArrayList<String>();
		String Answer = "28";
		answers.add(Answer);
		
		
		ArrayList<String> multiplechoices = new ArrayList<String>();
		String MC = "25";
		multiplechoices.add(MC);
		MC = "26";multiplechoices.add(MC);
		MC = "28";multiplechoices.add(MC);
		MC = "30";multiplechoices.add(MC);
		
		String imgURL = "";
		
		QuizQuestion quizQuestionVar = new  QuizQuestionMC( quizName , 2 , questions, answers, multiplechoices, imgURL);
		
		assertEquals(true, quizQuestionVar.addQuestionToQuiz());
		System.out.println("Question = " + quizQuestionVar.toString() );
	
		
		
	}
	
	
	@Test
	public void test4() {
		QuestionTypes questionType;
		questionType = QuestionTypes.PR;
		String quizName = "India";
		
		ArrayList<String> questions= new ArrayList<String>();
		String Question = "What country's flag is this ? ";
		questions.add(Question);
		
		ArrayList<String> answers = new ArrayList<String>();
		String Answer = "India";
		answers.add(Answer);
		
		ArrayList<String> multiplechoices = new ArrayList<String>();
		String imageUrl = "http://en.wikipedia.org/wiki/File:Flag_of_India.svg";
		
		
		QuizQuestion quizQuestionVar = new  QuizQuestionPR( quizName , 3 , questions, answers, multiplechoices, imageUrl);
		
		
		
		
		
		
		
		assertEquals(true, quizQuestionVar.addQuestionToQuiz());
		System.out.println("Question = " + quizQuestionVar.toString() );
		
	}
	
	
}
