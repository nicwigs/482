import java.io.IOException;
import java.util.Scanner;
import java.util.HashSet;
import java.io.FileReader;
import java.io.BufferedReader;
import java.net.URI;

import org.apache.hadoop.util.Tool;
import org.apache.hadoop.util.ToolRunner;
import org.apache.hadoop.conf.*;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.Mapper;
import org.apache.hadoop.mapreduce.Reducer;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;
import org.apache.hadoop.fs.FileSystem;

public class WordCount3 extends Configured implements Tool{

  public static class TokenizerMapper
       extends Mapper<Object, Text, Text, IntWritable>{

    private final static IntWritable one = new IntWritable(1);
    private Text word = new Text();
    private HashSet<String> stopwordlist = new HashSet<String>();

    @Override
    protected void setup(Context context)
		throws IOException, InterruptedException {
    	URI[] cacheFiles = context.getCacheFiles();

    	if (cacheFiles != null) {
    	    for(URI f : cacheFiles) {
                BufferedReader br = new BufferedReader(new FileReader(f.toString()));
		String line;

		while ((line = br.readLine())!=null) {
		    stopwordlist.add(line);
		}
    	    }
    	}
    }

    public void map(Object key, Text value, Context context) 
	throws IOException, InterruptedException {

      Scanner s = new Scanner(value.toString());
      while (s.hasNext()) {
	String token =  s.next();
	if (!stopwordlist.contains(token)) {
            word.set(token);
            context.write(word, one);
	}
      }
    }
  }

  public static class IntSumReducer
       extends Reducer<Text,IntWritable,Text,IntWritable> {
    private IntWritable result = new IntWritable();

    public void reduce(Text key, Iterable<IntWritable> values,
                       Context context
                       ) throws IOException, InterruptedException {
      int sum = 0;
      for (IntWritable val : values) {
        sum += val.get();
      }
      result.set(sum);
      context.write(key, result);
    }
  }

  public int run (String[] args) throws Exception {
    Job job = Job.getInstance(getConf(),"word count");
    job.setJarByClass(WordCount3.class);
    job.setMapperClass(TokenizerMapper.class);
    job.setCombinerClass(IntSumReducer.class);
    job.setReducerClass(IntSumReducer.class);
    job.setOutputKeyClass(Text.class);
    job.setOutputValueClass(IntWritable.class);
    FileInputFormat.addInputPath(job, new Path(args[0]));
    FileOutputFormat.setOutputPath(job, new Path(args[1]));
    job.addCacheFile(new Path(args[2]).toUri());
    return job.waitForCompletion(true) ? 0 : 1;
  }

  public static void main(String[] args) throws Exception {
    int exitCode = ToolRunner.run(new WordCount3(), args);
    System.exit(exitCode);
  }
}
