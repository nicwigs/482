userData = LOAD 'lastFM-users.csv' USING PigStorage(',') AS (userID,gender,age,country);
tmp = FOREACH userData GENERATE userID, gender, ((gender == 'm') ? 1 : 0) AS male;
tmp2 = FOREACH tmp GENERATE userID, male, ((gender == 'f') ? 1 : 0) AS female;

data = LOAD 'lastFM-ratings.csv' USING PigStorage(',') AS (userID, artist, num:int);
tmp3 = FILTER data BY artist == 'the beatles';
tmp4 = FOREACH tmp3 GENERATE userID;

tmp5 = JOIN tmp4 by $0, tmp2 by $0;
tmp6 = FOREACH tmp5 GENERATE male, female;

grp1 = GROUP tmp6 all;
result = FOREACH grp1 GENERATE SUM($1.male), SUM($1.female);

DUMP result;
STORE result INTO 'q2d';
