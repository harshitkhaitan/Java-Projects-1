USE c_cs108_hkhaitan;

DROP TABLE IF EXISTS userAccounts;
 -- remove table if it already exists and start from scratch

CREATE TABLE userAccounts (
	username CHAR(64),
    password CHAR(255),
    salt CHAR(64)
);

ALTER TABLE userAccounts ADDUNIQUE ( `username` );
INSERT INTO userAccounts (`username`, `password`, `salt`) VALUES ('harshit', '1234', '1234');
