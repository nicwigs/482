/*
 * Myscript.pig
 * = These are C++ style comment lines
 */
log = LOAD 'wiki_edit.txt' USING PigStorage(' ') 
	AS (revid,article,revdate,username);
lmt = LIMIT log 4;   	-- only show 4 tuples
DUMP lmt;		-- display the results
-- End of program
