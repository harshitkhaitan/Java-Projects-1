USE c_cs108_hkhaitan;

DROP TABLE IF EXISTS Messages;
 -- remove table if it already exists and start from scratch

CREATE TABLE Messages (
	fromUser CHAR(64) NOT NULL ,
	toUser CHAR(64) NOT NULL ,
	message TEXT NOT NULL,
	Mtype TINYINT NOT NULL DEFAULT 0,
	MRead TINYINT NOT NULL DEFAULT 0,
 	Mtime TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP 
);

ALTER TABLE Messages ADD id SERIAL;
ALTER TABLE Messages ADD PRIMARY KEY (id);
ALTER TABLE Messages ADD quizName CHAR(64);
ALTER TABLE Messages ADD myScore INT DEFAULT 0 ;

INSERT INTO Messages (`fromUser`, `toUser`, `message`, `Mtype`) VALUES ('harshit1', 'harshit2', '1234', '0');

