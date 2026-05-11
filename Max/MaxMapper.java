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

jps
start-dfs.sh
start-yarn.sh

javac -classpath `hadoop classpath` -d . MaxMapper.java MaxReducer.java MaxDriver.java
jar -cvf maxnumber.jar *.class# Create an input directory in HDFS
hdfs dfs -mkdir -p /user/input

# Upload your local file to HDFS
hdfs dfs -put input.txt /user/input/hadoop jar maxnumber.jar MaxDriver /user/input/input.txt /user/output/max_result

# List the output files
hdfs dfs -ls /user/output/max_result

# View the content of the result
hdfs dfs -cat /user/output/max_result/part-r-00000