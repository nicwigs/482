std = LOAD 'student.txt' USING PigStorage(','); 
data = LOAD 'transcript.txt' USING PigStorage(','); 
enrollment = JOIN std by $0, data by $0;
tmp = FOREACH enrollment GENERATE $0, $2, $4, $5; 
tmp2 = GROUP tmp BY $2;
result = FOREACH tmp2 GENERATE group, MIN(tmp.$3);
dump result;