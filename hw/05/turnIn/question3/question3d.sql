DROP TABLE IF EXISTS question3d;
CREATE TABLE question3d
ROW FORMAT DELIMITED
FIELDS TERMINATED BY '\t'
LINES TERMINATED BY '\n'
STORED AS TEXTFILE 
AS
	SELECT Country AS country, COUNT(*) AS numUsers
	FROM Streaming s JOIN Users u ON (s.UserID = u.UserID)
	WHERE Artist = 'the beatles'
	GROUP BY Country;
	