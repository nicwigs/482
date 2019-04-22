wget http://www.cse.msu.edu/~cse482/exercise12.tar

tar -xvf exercise12.tar

hadoop fs -mkdir grade
hadoop fs -mkdir major

hadoop fs -copyFromLocal grade.txt grade
hadoop fs -copyFromLocal major.txt major

# -u: server
# -n: user
# default password is blank
#-f runs file we want 
beeline -u "jdbc:hive2://localhost:10000/default" -n hadoop -f exercise12.sql

# NOTE if ever errors "-getmerge" not command -its from UTF-8 and being translated wrong

hadoop fs -getmerge /user/hive/warehouse/transcript ./transcript.txt
