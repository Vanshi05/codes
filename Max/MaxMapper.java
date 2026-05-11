import java.io.IOException;

import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;

public class MaxMapper extends Mapper<Object, Text, Text, IntWritable> {

    private Text word = new Text("max");

    public void map(Object key, Text value, Context context)
            throws IOException, InterruptedException {

        int number = Integer.parseInt(value.toString());

        context.write(word, new IntWritable(number));
    }
}