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
       extends Mapper<Object, Text, Text, Text>{

    String attributes[] = {"preg","plas","pres","skin","insu","mass","pedi","age","class"};
    private Text vals = new Text();
    private Text atts = new Text();


    public void map(Object key, Text value, Context context
                    ) throws IOException, InterruptedException {

      String[] line = value.toString().split(",");

      for (int x=0; x<line.length -2; x++){
        for (int y=x+1; y<line.length-1; y++){
          vals.set(line[x]+","+line[y]);
          atts.set(attributes[x]+","+attributes[y]);
          context.write(atts, vals );
        }
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
    job.setMapOutputValueClass(Text.class);
    FileInputFormat.addInputPath(job, new Path(args[0]));
    FileOutputFormat.setOutputPath(job, new Path(args[1]));
    System.exit(job.waitForCompletion(true) ? 0 : 1);
  }
}
