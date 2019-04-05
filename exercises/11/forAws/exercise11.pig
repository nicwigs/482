data = LOAD 'wiki_edit.txt' USING PigStorage(' ') AS (revID, article, ts, username);

describe data;

edits = FOREACH data GENERATE article, username;
uniq_edits = DISTINCT edits;
articles = GROUP uniq_edits BY article;
counts = FOREACH articles GENERATE group AS article, COUNT(uniq_edits.username) AS numUsers; 
results = FILTER counts BY (int) numUsers > 15;
dump results;
STORE results INTO 'output';
