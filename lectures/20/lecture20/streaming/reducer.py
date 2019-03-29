#!/usr/bin/python
import sys

prev_key = '999999'
count = 0
for myline in sys.stdin: 
	myline = myline.strip() 
	words = myline.split('\t') 
	if prev_key != words[0]:
		if count > 0:
			avg = sum*1.0/count;
			print "%s\t%s" % (prev_key, avg)
		sum = 0
		count = 0

	prev_key = words[0]
	if words[1] != "?":
		sum = sum + int(words[1])
		count = count + 1

if count > 0:
	avg = sum*1.0/count;
	print "%s\t%s" % (prev_key, avg)
		
