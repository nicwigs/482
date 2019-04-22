DROP TABLE IF EXISTS Users;
CREATE TABLE IF NOT EXISTS Users (
	UserID STRING,
	Gender char(1),
	Age INT,
	Country STRING)
ROW FORMAT DELIMITED
FIELDS TERMINATED BY ','
STORED AS TEXTFILE;

load data inpath '/user/hadoop/lastFM-users.csv'
overwrite into table Users;

DROP TABLE IF EXISTS Streaming;
CREATE TABLE IF NOT EXISTS Streaming (
	UserID STRING,
	Artist STRING,
	NumPlays INT)
ROW FORMAT DELIMITED
FIELDS TERMINATED BY ','
STORED AS TEXTFILE;

load data inpath '/user/hadoop/lastFM-ratings.csv'
overwrite into table Streaming;


