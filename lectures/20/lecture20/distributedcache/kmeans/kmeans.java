import java.io.IOException;
import java.lang.InterruptedException;
import java.io.FileReader;
import java.io.BufferedReader;
import java.net.URI;
import java.util.ArrayList;
import java.util.StringTokenizer;

import org.apache.hadoop.util.Tool;
import org.apache.hadoop.util.ToolRunner;
import org.apache.hadoop.conf.*;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.*;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.Mapper;
import org.apache.hadoop.mapreduce.Reducer;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;

public class kmeans extends Configured implements Tool {
    public static class km_mapper extends Mapper<Object, Text, Text, Text> {
	private ArrayList<String> centroids = new ArrayList<String>();
	private static Text cluster = new Text();

	@Override
	protected void setup(Context context) 
		throws IOException, InterruptedException {
	    URI[] cacheFiles = context.getCacheFiles();

	    if (cacheFiles != null) {
		for (URI f: cacheFiles) {
		    BufferedReader br = new BufferedReader(new FileReader(f.toString()));
		    String line;

		    while ((line = br.readLine())!=null) {
			centroids.add(line);
		    }
		}
	    }
	}

	public int assignment(String x) {
	    int id = -1;
            double mindist = Double.MAX_VALUE;
            double dist;

            for (int i=0; i<centroids.size(); i++) {
            	dist = calcDistance(x, centroids.get(i));
            	if (dist < mindist) {
                    mindist = dist;
                    id = i;
            	}
            }
            return id;
    	}

	public static double calcDistance(String x1, String x2) {
            ArrayList<Double> s1 = new ArrayList<Double>();
            ArrayList<Double> s2 = new ArrayList<Double>();

            StringTokenizer st = new StringTokenizer(x1,",");
            while (st.hasMoreTokens()) {
                s1.add(Double.parseDouble(st.nextToken()));
            }
            st = new StringTokenizer(x2,",");
            while (st.hasMoreTokens()) {
                s2.add(Double.parseDouble(st.nextToken()));
            }

            double dist = 0;
            for (int i=0; i<s1.size(); i++) {
            	dist += (s1.get(i) - s2.get(i))*(s1.get(i) - s2.get(i));
            }
            return Math.sqrt(dist);
     	}

	public void map(Object key, Text value, Context context) 
		throws IOException, InterruptedException {
	    int clusterID = assignment(value.toString());
	    cluster.set(centroids.get(clusterID));
	    context.write(cluster, value);
	}
    }

    public static class km_reducer extends Reducer<Text, Text, Text, Text> {
	private Text new_centroid = new Text();
	private Text clusters = new Text();

	public void reduce(Text key, Iterable<Text> values, Context context)
		throws IOException, InterruptedException {
	    ArrayList<Double> new_center = new ArrayList<Double>();
	    int numPoints = 0;
	    String data = new String();
	    String[] attribs = new String[0];

	    for (Text point : values) {
		attribs = point.toString().split(",");
		data = data + "(" + point.toString() + ")";

		if (++numPoints == 1) {
		    for (int i=0; i<attribs.length; i++) {
			new_center.add(Double.parseDouble(attribs[i]));
		    }
		}
		else {
		    for (int i=0; i<attribs.length; i++) {
			double val = new_center.get(i);
			val += Double.parseDouble(attribs[i]);
			new_center.set(i,val);
		    }
		}
	    }
	    String str = new String("(");
	    for (int i=0; i<attribs.length; i++) {
		str = str +  new_center.get(i)*1.0/numPoints + ",";
	    }
	    new_centroid.set(str.substring(0, str.length()-1) + ")");
	    clusters.set(data);
	    context.write(new_centroid, clusters);
	}
    }


    public int run(String [] args) throws Exception {
	Job job = Job.getInstance(getConf(),"k-means");
  	job.setJarByClass(kmeans.class);
	job.setMapperClass(km_mapper.class);
	job.setMapOutputKeyClass(Text.class);
	job.setMapOutputValueClass(Text.class);
	job.setReducerClass(km_reducer.class);
	job.setOutputKeyClass(Text.class);
	job.setOutputValueClass(Text.class);
	FileInputFormat.addInputPath(job, new Path(args[0]));
	FileOutputFormat.setOutputPath(job, new Path(args[1]));
	job.addCacheFile(new Path(args[2]).toUri());
	return job.waitForCompletion(true)? 0: 1;
    }

    public static void main(String[] args) throws Exception {
	int exitCode = ToolRunner.run(new kmeans(), args);
	System.exit(exitCode);
    }
}
