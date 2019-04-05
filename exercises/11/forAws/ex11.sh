#(1) get data 
wget http://www.cse.msu.edu/~cse482/exercise11.tar
# unarchive
tar -xvf exercise11.tar
cd exercise11

# launch pig
# turn off pig messages
#pig -x local -4 nolog.conf
#exec exercise11.pig

cp ../exercise11.pig .
hadoop fs -copyFromLocal wiki_edit.txt .
hadoop fs -copyFromLocal nolog.conf .

pig -x local -4 nolog.conf exercise11.pig

cat output/* > result.txt
