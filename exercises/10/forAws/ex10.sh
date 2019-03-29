
#(2) get data 
wget http://www.cse.msu.edu/~cse482/lecture19.tar
#(2a) unarchive
tar -xvf lecture19.tar
cd lecture19
# (2b) env.sh contains the following for environment var
# environment variables needed
export JAVA_HOME=/usr/lib/jvm/java-1.8.0-openjdk.x86_64
export PATH=${JAVA_HOME}/bin:${PATH}:.
export HADOOP_CLASSPATH=${JAVA_HOME}/lib/tools.jar

#(2c) copy input to HDFS
hadoop fs -copyFromLocal sentiment.data .

# (3) replace old getSentiment.java with new file
# Only change is in reducer outpt - ratio
rm getSentiment.java #remove old
mv ../getSentiment.java . # bring new file into directory

#(4a) compile
hadoop com.sun.tools.javac.Main getSentiment.java

#(4b) create jar
jar cvf sent.jar getSentiment*.class

#(4c) run Hadoop
# Takes advantage of toolrunner for command line arguments.
# reduces = number of reducers used
# threshold is threshold to get threshold for important words, looks not implemented though.
# ratio is ratio of class1/class2 in which we only store if above this ratio. 
hadoop jar sent.jar getSentiment -D mapreduce.job.reduces=4 -D threshold=800 -D ratio=10 sentiment.data output

# view results
hadoop fs -ls output

#(h) download results
hadoop fs -getmerge ./output ./result.txt


