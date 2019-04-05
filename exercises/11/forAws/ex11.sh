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

# upload to hdfs
hadoop fs -copyFromLocal wiki_edit.txt .
hadoop fs -copyFromLocal nolog.conf .

# no log file, run in local mode
pig -x local -4 nolog.conf exercise11.pig

# since in local mode, output not stored on hdfs, hence no -getmerge
cat output/* > result.txt
