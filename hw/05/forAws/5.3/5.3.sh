wget http://www.cse.msu.edu/~cse482/lastFM.tar

tar -xvf lastFM.tar

cd lastFM

hadoop fs -copyFromLocal lastFM-ratings.csv .
hadoop fs -copyFromLocal lastFM-users.csv .

cd .. 

# -u: server
# -n: user
# default password is blank
#-f runs file we want 
beeline -u "jdbc:hive2://localhost:10000/default" -n hadoop -f question3a.sql
beeline -u "jdbc:hive2://localhost:10000/default" -n hadoop -f question3b.sql
beeline -u "jdbc:hive2://localhost:10000/default" -n hadoop -f question3c.sql
beeline -u "jdbc:hive2://localhost:10000/default" -n hadoop -f question3d.sql


hadoop fs -getmerge /user/hive/warehouse/question3b ./question3b.txt
hadoop fs -getmerge /user/hive/warehouse/question3c ./question3c.txt
hadoop fs -getmerge /user/hive/warehouse/question3d ./question3d.txt
