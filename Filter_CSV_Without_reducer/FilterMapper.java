import java.io.IOException;

import org.apache.hadoop.io.NullWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;

public class FilterMapper extends Mapper<Object, Text, NullWritable, Text> {

    public void map(Object key, Text value, Context context)
            throws IOException, InterruptedException {

        String[] fields = value.toString().split(",");

        if (fields[2].equals("HR")) {
            context.write(NullWritable.get(), value);
        }
    }
}



# Check Java + Hadoop daemons running
jps

# Start HDFS
start-dfs.sh

# Start YARN
start-yarn.sh

# Verify all Hadoop services are running
jps

# Compile Java files
javac -classpath `hadoop classpath` -d . FilterMapper.java FilterDriver.java


# Create JAR file
jar -cvf filterdata.jar *.class

# Create input directory in HDFS
hdfs dfs -mkdir -p /user/input

# Upload dataset file to HDFS
# Replace employees.csv with your actual dataset filename
hdfs dfs -put employees.csv /user/input

# OPTIONAL: Check if file uploaded successfully
hdfs dfs -ls /user/input


# Run the MapReduce job
hadoop jar filterdata.jar FilterDriver /user/input/employees.csv /user/output/filter_result

# View output directory
hdfs dfs -ls /user/output/filter_result

# Display filtered records (only HR department rows)
hdfs dfs -cat /user/output/filter_result/part-m-00000