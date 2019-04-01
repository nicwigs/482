import java.io.IOException;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.Mapper;
import org.apache.hadoop.mapreduce.Reducer;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;

public class favArtist {

  public static class myMapper
       extends Mapper<Object, Text, IntWritable, Text>{

    private Text vals = new Text();
    private IntWritable k = new IntWritable();

    public void map(Object key, Text value, Context context
                    ) throws IOException, InterruptedException {

      String[] line = value.toString().split(",");

      k.set(Integer.parseInt(line[0]));
      vals.set(line[1]+","+line[2]);
      context.write(k, vals );

    }
  }

public static class myReducer
     extends Reducer<IntWritable,Text,IntWritable,Text> {

  private Text result = new Text();

  public void reduce(IntWritable key, Iterable<Text> values,
                     Context context
                     ) throws IOException, InterruptedException {
    
    int maxListen = 0;
    String artist = new String();
    String fav = new String();
    int listen = 0;

    for (Text val : values) {
      String[] row = val.toString().split(",");
      artist = row[0];
      listen = Integer.parseInt(row[1]);

      if (listen > maxListen){
        maxListen = listen;
        fav = artist;
      }
    }

    result.set(fav);
    context.write(key, result);
  }
}


  public static void main(String[] args) throws Exception {
    Configuration conf = new Configuration();
    Job job = Job.getInstance(conf, "Favorite Artist");
    job.setJarByClass(favArtist.class);
    job.setMapperClass(myMapper.class);
    job.setReducerClass(myReducer.class);
    job.setOutputKeyClass(IntWritable.class);
    job.setOutputValueClass(Text.class);
    job.setMapOutputKeyClass(IntWritable.class);
    job.setMapOutputValueClass(Text.class);
    FileInputFormat.addInputPath(job, new Path(args[0]));
    FileOutputFormat.setOutputPath(job, new Path(args[1]));
    System.exit(job.waitForCompletion(true) ? 0 : 1);
  }
}
