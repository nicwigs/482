export JAVA_HOME=/usr/lib/jvm/java-1.8.0-openjdk.x86_64
export PATH=${JAVA_HOME}/bin:${PATH}:.
export HADOOP_CLASSPATH=${JAVA_HOME}/lib/tools.jar

# Number 3
hadoop com.sun.tools.javac.Main getConv.java
jar cvf gc.jar getConv*.class
hadoop fs -copyFromLocal diabetes.csv .
hadoop jar gc.jar getConv diabetes.csv output_hw3
hadoop fs -getmerge ./output_hw3 ./result_hw3.txt

#Number 4
hadoop com.sun.tools.javac.Main favArtist.java
jar cvf fa.jar favArtist*.class
hadoop fs -copyFromLocal lastFM.csv .
hadoop jar fa.jar favArtist lastFM.csv output_hw4
hadoop fs -getmerge ./output_hw4 ./result_hw4.txt


