data = LOAD 'transcript.txt' USING PigStorage(',') AS (Sname, Course, GPA:float);
tmp = FILTER data BY Course == 'cse482';
result = FOREACH tmp GENERATE Sname, GPA;
DUMP result;