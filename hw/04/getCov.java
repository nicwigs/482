import java.io.IOException;
import java.util.StringTokenizer;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.DoubleWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.Mapper;
import org.apache.hadoop.mapreduce.Reducer;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;

public class getCov {

  public static class TokenizerMapper
       extends Mapper<Object, Text, Text, DoubleWritable>{

    String attributes[] = {"preg","plas","pres","skin","insu","mass","pedi","age","class"};
    DoubleWritable val1 = new DoubleWritable(1.0);
    private Text at1 = new Text();


    public void map(Object key, Text value, Context context
                    ) throws IOException, InterruptedException {

      String[] line = value.toString().split(",");

      for (int x=0; x<line.length -2; x++){
        val1.set(Double.parseDouble(line[x]));
        at1.set(attributes[x]);
        context.write(at1, val1 );
      }
    }
  }


  public static void main(String[] args) throws Exception {
    Configuration conf = new Configuration();
    Job job = Job.getInstance(conf, "Covariance");
    job.setJarByClass(getCov.class);
    job.setMapperClass(TokenizerMapper.class);
//    job.setReducerClass(IntSumReducer.class);
//    job.setOutputKeyClass(Text.class);
//    job.setOutputValueClass(IntWritable.class);
    job.setMapOutputKeyClass(Text.class);
    job.setMapOutputValueClass(DoubleWritable.class);
    FileInputFormat.addInputPath(job, new Path(args[0]));
    FileOutputFormat.setOutputPath(job, new Path(args[1]));
    System.exit(job.waitForCompletion(true) ? 0 : 1);
  }
}
