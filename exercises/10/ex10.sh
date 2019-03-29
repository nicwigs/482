
#(a) get data 
wget http://www.cse.msu.edu/~ptan/CSE482/exercises/ex9/ex9.tar
#(b) unarchive
tar -xvf ex9.tar
cd ex9
# (c) env.sh contains the following for environment var
# environment variables needed
export JAVA_HOME=/usr/lib/jvm/java-1.8.0-openjdk.x86_64
export PATH=${JAVA_HOME}/bin:${PATH}:.
export HADOOP_CLASSPATH=${JAVA_HOME}/lib/tools.jar

#(d) compile
hadoop com.sun.tools.javac.Main WordCount.java

#(e) create jar
jar cvf wc.jar *.class

#(f) copy input to HDFS
hadoop fs -copyFromLocal document.txt .

#(g) run Hadoop
hadoop jar wc.jar WordCount document.txt output

# view results
hadoop fs -ls output

#(h) download results
hadoop fs -getmerge ./output ./result.txt


