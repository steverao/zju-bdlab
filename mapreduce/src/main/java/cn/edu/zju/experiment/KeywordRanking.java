package cn.edu.zju.experiment;

import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapred.*;

import java.io.IOException;
import java.util.*;


/**
 * @author raozihao
 * @date 2019/10/31
 */
public class KeywordRanking {

    private static final int topLength = 10;

    public static class Map extends MapReduceBase implements Mapper<LongWritable, Text, Text, Text> {


        @Override
        public void map(LongWritable key, Text value, OutputCollector<Text, Text> outputCollector, Reporter reporter) throws IOException {
            String line = value.toString();
            String[] split = line.split("\t");
            //The keyword position in original utf8 file
            String keyword = split[2];
            //Divide the keyword into different partition and prevent from JVM heap overflow
            String partition = String.valueOf(Math.abs(keyword.hashCode() % 10));
            outputCollector.collect(new Text(partition), new Text(keyword));
        }
    }

    public static class Reduce extends MapReduceBase implements Reducer<Text, Text, Text, Text> {

        @Override
        public void reduce(Text key, Iterator<Text> values, OutputCollector<Text, Text> outputCollector, Reporter reporter) throws IOException {

            class Record {
                String keyword;
                int num;
            }

            List<String> list = new ArrayList<>();
            for (Iterator<Text> it = values; it.hasNext(); ) {
                Text v = it.next();
                list.add(v.toString());
            }
            PriorityQueue<Record> queue = new PriorityQueue<>(topLength, Comparator.comparingInt((Record r) -> r.num));
            list.sort(Comparator.naturalOrder());

            Record first;
            Record Record = new Record();
            int size = list.size();
            for (int i = 0; i < size; i++) {
                if (i == 0) {
                    Record.num = 1;
                    Record.keyword = list.get(i);
                } else {
                    //Keyword was ordered in list
                    if (list.get(i).equals(list.get(i - 1))) {
                        Record.num++;
                    } else {
                        if (queue.size() == topLength) {
                            first = queue.peek();
                            if (first.num < Record.num) {
                                queue.remove(first);
                                queue.add(Record);
                            }
                        } else {
                            queue.add(Record);
                        }
                        if (i < size - 1) {
                            Record = new Record();
                            Record.num = 1;
                            Record.keyword = list.get(i + 1);
                        }
                    }
                    //In order to prevent from losing the last record
                    if (i == size - 1) {
                        if (queue.size() == topLength) {
                            first = queue.peek();
                            if (first.num < Record.num) {
                                queue.remove(first);
                                queue.add(Record);
                            }
                        } else {
                            queue.add(Record);
                        }
                    }
                }
            }

            //Construct return result
            while (queue.size() != 0) {
                Record = queue.poll();
                outputCollector.collect(key, new Text(Record.keyword + " : " + Record.num));
            }

        }
    }

    public static void main(String[] args) throws IOException {
        JobConf conf = new JobConf(KeywordRanking.class);
        conf.setJobName("keyword-ranking");

        conf.setOutputKeyClass(Text.class);
        conf.setOutputValueClass(Text.class);

        conf.setMapperClass(Map.class);
        conf.setReducerClass(Reduce.class);
        conf.setInputFormat(TextInputFormat.class);
        conf.setOutputFormat(TextOutputFormat.class);
        FileInputFormat.setInputPaths(conf, new Path(args[0]));
        FileOutputFormat.setOutputPath(conf, new Path(args[1]));
        JobClient.runJob(conf);
    }
}
