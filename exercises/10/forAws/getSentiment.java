import java.io.IOException;
import java.util.*;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.util.Tool;
import org.apache.hadoop.util.ToolRunner;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.Mapper;
import org.apache.hadoop.mapreduce.Reducer;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;
import org.apache.hadoop.conf.*;
import org.apache.hadoop.mapreduce.lib.input.KeyValueTextInputFormat;
import org.apache.hadoop.io.*;

public class getSentiment extends Configured implements Tool {

	public  static class  myMapper extends Mapper
		<Text, Text, Text, IntWritable> {

	   private Text word = new Text();
	   private IntWritable sentiment = new IntWritable();

	   public void map(Text key, Text value, Context c) 
		throws IOException, InterruptedException {

		sentiment.set(Integer.parseInt(key.toString()));
		StringTokenizer itr = new StringTokenizer(value.toString());
		while (itr.hasMoreTokens()) {
		   word.set(itr.nextToken());
		   c.write(word, sentiment);
		}
	   }
	}

	public static class myReducer extends Reducer<Text, IntWritable, Text, Text> {
	    private Text result = new Text();

	    public void reduce(Text key, Iterable<IntWritable> values, Context c) 		
		throws IOException, InterruptedException {
		   int numPos = 0, numNeg = 0;
		   String sentimentVal;
		   double ratioVal;
	
		   Configuration conf =  c.getConfiguration();
		   int threshold = Integer.parseInt(conf.get("threshold"));
		   double ratio = Double.parseDouble(conf.get("ratio"));

		   for (IntWritable val : values) {
			if (val.get() == 1) {
			    numPos += 1;
			}
			else {
			    numNeg += 1;
			}
		   }
		   if (numPos > numNeg) {
		   	ratioVal = numPos*1.0/(numNeg+0.000001);
			sentimentVal = new String("positive ("+ratioVal+")");
		   }
		   else if (numNeg > numPos) {
		   	ratioVal = numNeg*1.0/(numPos+0.000001);
			sentimentVal = new String("negative ("+ratioVal+")");
		   }
		   else {
			ratioVal = 1.0;
			sentimentVal = new String("neutral ("+ratioVal+")");
		   }	
 
		   if ((numPos + numNeg > 100) && (ratioVal > ratio)) {
		      result.set(sentimentVal);
		      c.write(key, result);
		   }
	    }
	}
	public int run(String[] args)  throws Exception {
		Job job = Job.getInstance(getConf(),"Word sentiment");
		job.setJarByClass(getSentiment.class);
		job.setInputFormatClass(KeyValueTextInputFormat.class);
		job.setMapperClass(myMapper.class);
		job.setReducerClass(myReducer.class);
		job.setMapOutputKeyClass(Text.class);
		job.setMapOutputValueClass(IntWritable.class);
		job.setOutputKeyClass(Text.class);
		job.setOutputValueClass(Text.class);
		FileInputFormat.addInputPath(job, new Path(args[0]));
		FileOutputFormat.setOutputPath(job, new Path(args[1]));
		return job.waitForCompletion(true)? 0: 1;
	}

 	public static void main(String [] args) throws Exception {
		int exitCode = ToolRunner.run(new getSentiment(), args);
		System.exit(exitCode);
	}
}
