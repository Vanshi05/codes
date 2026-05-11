import java.io.IOException;

import org.apache.hadoop.io.DoubleWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;

public class AvgMapper extends Mapper<Object, Text, Text, DoubleWritable> {

    private Text word = new Text("avg");

    public void map(Object key, Text value, Context context)
            throws IOException, InterruptedException {

        double num = Double.parseDouble(value.toString());

        context.write(word, new DoubleWritable(num));
    }
}