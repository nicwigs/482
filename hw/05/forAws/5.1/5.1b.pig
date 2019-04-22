data = LOAD 'transcript.txt' USING PigStorage(',') AS (Sname, Course, GPA:float);
grp = GROUP data BY Course;
tmp = FOREACH grp GENERATE group AS Course, AVG(data.$2) AS avgGPA;
result = ORDER tmp BY avgGPA desc;
dump result;