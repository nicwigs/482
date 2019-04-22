data = LOAD 'lastFM-ratings.csv' USING PigStorage(',') AS (userID, artist, num:int);
grp = GROUP data by artist;
tmp = FOREACH grp GENERATE group AS Artist, COUNT($1) AS Users;
tmp2 = ORDER tmp BY Users desc;
result = LIMIT tmp2 10;
dump result;
STORE result INTO 'q2b';