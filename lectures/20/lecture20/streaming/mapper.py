#!/usr/bin/python
import sys

for myline in sys.stdin: 
	words = myline.split(',') 
	print '%s\t%s' % (words[1], words[24])


