USE c_cs108_hkhaitan;

DROP TABLE IF EXISTS Achievements;
 -- remove table if it already exists and start from scratch

CREATE TABLE Achievements (
	username CHAR(64) NOT NULL ,
	achievement CHAR(64) NOT NULL ,
	achievement_type INT NOT NULL DEFAULT 0,
 	Mtime TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP 
);

ALTER TABLE Achievements ADD id SERIAL;
ALTER TABLE Achievements ADD PRIMARY KEY (id);

INSERT INTO Achievements (`username`, `achievement`) VALUES ('harshit1', 'bogus');

