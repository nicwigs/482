DROP TABLE IF EXISTS grade;
CREATE EXTERNAL TABLE IF NOT EXISTS grade (
	name STRING, 
	hw1 INT,
	hw2 INT,
	hw3 INT)
ROW FORMAT DELIMITED
FIELDS TERMINATED BY ','
STORED AS TEXTFILE
LOCATION '/user/hadoop/grade'; -- path to the directory that contains grade.txt.

DROP TABLE IF EXISTS major;
CREATE EXTERNAL TABLE IF NOT EXISTS major (
	name STRING,
	status STRING,
	dept STRING)
ROW FORMAT DELIMITED
FIELDS TERMINATED BY ','
STORED AS TEXTFILE
LOCATION '/user/hadoop/major';

DROP TABLE IF EXISTS transcript;
CREATE TABLE transcript
ROW FORMAT DELIMITED
FIELDS TERMINATED BY ','
LINES TERMINATED BY '\n' AS
	SELECT grade.name AS name, status, dept, hw1+hw2+hw3 as hwgrade
	FROM grade, major
	WHERE grade.name = major.name;

