USE c_cs108_hkhaitan;

DROP TABLE IF EXISTS friends;
 -- remove table if it already exists and start from scratch

CREATE TABLE friends (
	username1 CHAR(64),
	username2 CHAR(64),
   	pending TINYINT DEFAULT 0 
);

INSERT INTO friends (`username1`, `username2`, `pending`) VALUES ('harshit1', 'harshit2', '0');
INSERT INTO friends (`username1`, `username2`) VALUES ('harshit2', 'harshit1');
