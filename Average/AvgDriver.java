import java.io.IOException;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.Path;

import org.apache.hadoop.io.DoubleWritable;
import org.apache.hadoop.io.Text;

import org.apache.hadoop.mapreduce.Job;

import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;

public class AvgDriver {

    public static void main(String[] args)
            throws IOException, ClassNotFoundException, InterruptedException {

        // Create configuration object
        Configuration conf = new Configuration();

        // Create job
        Job job = Job.getInstance(conf, "Average Calculator");

        // Set Driver class
        job.setJarByClass(AvgDriver.class);

        // Set Mapper and Reducer classes
        job.setMapperClass(AvgMapper.class);
        job.setReducerClass(AvgReducer.class);

        // Set output key and value types
        job.setOutputKeyClass(Text.class);
        job.setOutputValueClass(DoubleWritable.class);

        // Set input and output paths
        FileInputFormat.addInputPath(job, new Path(args[0]));
        FileOutputFormat.setOutputPath(job, new Path(args[1]));

        // Exit based on job success
        System.exit(job.waitForCompletion(true) ? 0 : 1);
    }
}