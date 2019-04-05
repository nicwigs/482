define wordcount(text) returns counts {
   tokens = foreach $text generate TOKENIZE($0) as terms;
   wordlist = foreach tokens generate FLATTEN(terms) as word, 1 as freq;
   groups = group wordlist by word;
   $counts = foreach groups generate group as word,
		   SUM(wordlist.freq) as freq;
}
