import java.io.IOException;

import org.apache.hadoop.io.DoubleWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Reducer;

public class AvgReducer extends Reducer<Text, DoubleWritable, Text, DoubleWritable> {

    public void reduce(Text key, Iterable<DoubleWritable> values, Context context)
            throws IOException, InterruptedException {

        double sum = 0;
        int count = 0;

        for (DoubleWritable val : values) {
            sum += val.get();
            count++;
        }

        context.write(key, new DoubleWritable(sum / count));
    }
}


# Check Hadoop daemons
jps

# Start HDFS
start-dfs.sh

# Start YARN
start-yarn.sh

# Verify services
jps


# Compile Java files
# Include your Driver class as well
javac -classpath `hadoop classpath` -d . AvgMapper.java AvgReducer.java AvgDriver.java

# Create JAR file
jar -cvf average.jar *.class

# Create input directory in HDFS
hdfs dfs -mkdir -p /user/input

# Upload input file to HDFS
# Replace numbers.txt with your actual file
hdfs dfs -put numbers.txt /user/input

# OPTIONAL: Verify uploaded file
hdfs dfs -ls /user/input

# Run the MapReduce job
hadoop jar average.jar AvgDriver /user/input/numbers.txt /user/output/avg_result

# View the final average result
hdfs dfs -cat /user/output/avg_result/part-r-00000