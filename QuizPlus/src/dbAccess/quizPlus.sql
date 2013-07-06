USE c_cs108_jayakarr;

DROP TABLE IF EXISTS QUIZCREATE;
 -- remove table if it already exists and start from scratch

CREATE TABLE QUIZCREATE (
	QuizName CHAR(64),
	CreatedBy CHAR(64),
	CreateTime TIMESTAMP,
	Description TEXT,
	AllowPractice BOOLEAN,
	OneQuestionPerPage BOOLEAN,
	ImmediateCorrection BOOLEAN,
	numberofTimesTaken INT(4),
	QuestionOrderRandomized BIT(1),
	numOfQuestions INT(4)
);

DROP TABLE IF EXISTS QUIZ_QR;
CREATE TABLE QUIZ_QR(
	id INT (5) NOT NULL AUTO_INCREMENT,
	question TEXT ,
	answer0 TEXT,
	answer1 TEXT,
	answer2 TEXT,
	answer3 TEXT,
	answer4 TEXT,
	answer5 TEXT,
	answer6 TEXT,
	answer7 TEXT,
	answer8 TEXT,
	answer9 TEXT,
	PRIMARY KEY(id)
);

DROP TABLE IF EXISTS QUIZ_MC;
CREATE TABLE QUIZ_MC (
	id INT (5) NOT NULL AUTO_INCREMENT,
	question TEXT,
	answer0 TEXT,
	answer1 TEXT,
	answer2 TEXT,
	answer3 TEXT,
	answer4 TEXT,
	answer5 TEXT,
	answer6 TEXT,
	answer7 TEXT,
	answer8 TEXT,
	answer9 TEXT,
	choice0 TEXT,
	choice1 TEXT,
	choice2 TEXT,
	choice3 TEXT,
	choice4 TEXT,
	choice5 TEXT,
	choice6 TEXT,
	choice7 TEXT,
	choice8 TEXT,
	choice9 TEXT,
	PRIMARY KEY(id)
);

DROP TABLE IF EXISTS QUIZ_FIB;
CREATE TABLE QUIZ_FIB (
	id INT (5) NOT NULL AUTO_INCREMENT,
	questionbeforeblank TEXT,
	answer0 TEXT,
	answer1 TEXT,
	answer2 TEXT,
	answer3 TEXT,
	answer4 TEXT,
	answer5 TEXT,
	answer6 TEXT,
	answer7 TEXT,
	answer8 TEXT,
	answer9 TEXT,
	questionafterblank TEXT,
	PRIMARY KEY(id)

);

DROP TABLE IF EXISTS QUIZ_PR;
CREATE TABLE QUIZ_PR (
	id INT (5) NOT NULL AUTO_INCREMENT,
	question TEXT,
	imgurl TEXT,
	answer0 TEXT,
	answer1 TEXT,
	answer2 TEXT,
	answer3 TEXT,
	answer4 TEXT,
	answer5 TEXT,
	answer6 TEXT,
	answer7 TEXT,
	answer8 TEXT,
	answer9 TEXT,
	PRIMARY KEY(id)


);



DROP TABLE IF EXISTS QUIZSTATS;

CREATE TABLE QUIZSTATS (
	QuizName CHAR(64),
	UserName CHAR(64),
	TakenTime TIMESTAMP,
	TotalTimeTakenToSolve BIGINT(20),
	SCORE		INT(3)
);

DROP TABLE IF EXISTS QUIZDETAILS; 
CREATE TABLE QUIZDETAILS(
	QuizName CHAR(64),
	QuestionNumber INT (3),
	QuestionType CHAR(4),
	QuestionID	INT(4)	
);
