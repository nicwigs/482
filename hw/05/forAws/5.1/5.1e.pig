data = LOAD 'transcript.txt' USING PigStorage(','); 
grp1 = GROUP data BY $1;
tmp = FOREACH grp1 GENERATE group, 'all', AVG($1.$2); 
grp2 = GROUP data all;
tmp2 = FOREACH grp2 GENERATE $0, AVG($1.$2);
tmp3 = JOIN tmp BY $1, tmp2 BY $0;
tmp4 = FILTER tmp3 BY $2 > $4;
result = FOREACH tmp4 GENERATE $0;
dump result;