# environment variables needed
export JAVA_HOME=/usr/lib/jvm/java-1.8.0-openjdk.x86_64
export PATH=${JAVA_HOME}/bin:${PATH}:.
export HADOOP_CLASSPATH=${JAVA_HOME}/lib/tools.jar

#copy input to HDFS
hadoop fs -copyFromLocal train.data .

# compile
hadoop com.sun.tools.javac.Main getSentiment.java

#(4b) create jar
jar cvf sent.jar getSentiment*.class

#(4c) run Hadoop
# Takes advantage of toolrunner for command line arguments.
# reduces = number of reducers used
# threshold is threshold to get threshold for important words, looks not implemented though.
# ratio is ratio of class1/class2 in which we only store if above this ratio. 
hadoop jar sent.jar getSentiment -D mapreduce.job.reduces=4 -D threshold=10 -D ratio=-1 train.data output

# view results
hadoop fs -ls output

#(h) download results
hadoop fs -getmerge ./output ./result.txt


