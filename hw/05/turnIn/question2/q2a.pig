data = LOAD 'lastFM-ratings.csv' USING PigStorage(',') AS (userID, artist, num:int);
tmp = FILTER data BY artist == 'the beatles';
result = FOREACH tmp GENERATE userID;
DUMP result;
STORE result INTO 'q2a';