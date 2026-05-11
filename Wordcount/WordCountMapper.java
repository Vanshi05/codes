import java.io.IOException;
import java.util.StringTokenizer;

import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;

public class WordCountMapper extends Mapper<Object, Text, Text, IntWritable> {

    private final static IntWritable one = new IntWritable(1);
    private Text word = new Text();

    public void map(Object key, Text value, Context context)
            throws IOException, InterruptedException {

        StringTokenizer itr = new StringTokenizer(value.toString());

        while (itr.hasMoreTokens()) {
            word.set(itr.nextToken());
            context.write(word, one);
        }
    }
}

jps
start-dfs.sh
start-yarn.sh

javac -classpath $(hadoop classpath) -d . WordCountMapper.java WordCountReducer.java WordCountDriver.java
jar -cvf wordcount.jar *.class

# Create input directory (if not already there)
hdfs dfs -mkdir -p /user/input

# Upload your text file
hdfs dfs -put sample_text.txt /user/input/

hadoop jar wordcount.jar WordCountDriver /user/input/sample_text.txt /user/output/wordcount_results

hdfs dfs -cat /user/output/wordcount_results/part-r-00000