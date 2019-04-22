std = LOAD 'student.txt' USING PigStorage(',') AS (Name, Status, Dept);
cs = FILTER std BY Dept == 'cse';
data = LOAD 'transcript.txt' USING PigStorage(',')
       AS (Sname, Course, GPA:float);
tmp = JOIN cs by Name, data by Sname;
tmp2 = FOREACH tmp GENERATE Sname, Course;
result = DISTINCT tmp2;
dump result;