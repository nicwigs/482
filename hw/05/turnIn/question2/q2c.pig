data = LOAD 'lastFM-ratings.csv' USING PigStorage(',') AS (userID, artist, num:int);
grp = GROUP data by artist;
tmp = FOREACH grp GENERATE group AS Artist, SUM($1.num) AS Plays;
tmp2 = ORDER tmp BY Plays desc;
result = LIMIT tmp2 10;
dump result;
STORE result INTO 'q2c';