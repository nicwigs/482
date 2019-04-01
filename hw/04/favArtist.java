import java.io.IOException;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.DoubleWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.Mapper;
import org.apache.hadoop.mapreduce.Reducer;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;

public class favArtist {

  public static class myMapper
       extends Mapper<Object, Text, Text, Text>{

    private Text vals = new Text();
    private Text k = new Text();

    public void map(Object key, Text value, Context context
                    ) throws IOException, InterruptedException {

      String[] line = value.toString().split(",");

      k.set(line[0]);
      vals.set(line[1]+","+line[2]);
      context.write(k, vals );

    }
  }


  public static void main(String[] args) throws Exception {
    Configuration conf = new Configuration();
    Job job = Job.getInstance(conf, "Favorite Artist");
    job.setJarByClass(favArtist.class);
    job.setMapperClass(myMapper.class);
    //job.setReducerClass(myReducer.class);
    //job.setOutputKeyClass(Text.class);
    //job.setOutputValueClass(DoubleWritable.class);
    job.setMapOutputKeyClass(Text.class);
    job.setMapOutputValueClass(Text.class);
    FileInputFormat.addInputPath(job, new Path(args[0]));
    FileOutputFormat.setOutputPath(job, new Path(args[1]));
    System.exit(job.waitForCompletion(true) ? 0 : 1);
  }
}
