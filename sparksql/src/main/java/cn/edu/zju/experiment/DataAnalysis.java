package cn.edu.zju.experiment;

import org.apache.spark.api.java.JavaRDD;
import org.apache.spark.sql.Dataset;
import org.apache.spark.sql.Row;
import org.apache.spark.sql.SparkSession;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.PrintStream;

/**
 * @author raozihao
 * @date 2019/10/28
 */
public class DataAnalysis {
    public static void main(String[] args) throws FileNotFoundException {
        SparkSession spark = SparkSession.builder()
                .appName("big-data-analysis")
                .master("local[*]")
                .getOrCreate();

        String path = "...";
        JavaRDD<Record> df = spark.read()
                .textFile(path)
                .javaRDD()
                .map(line -> {
                    String[] parts = line.split("\t");
                    Record record = new Record();
                    record.setTs(parts[0]);
                    record.setUid(parts[1]);
                    record.setKeyword(parts[2]);
                    record.setRank(Integer.parseInt(parts[3].trim()));
                    record.setOrder(Integer.parseInt((parts[4].trim())));
                    record.setUrl(parts[5]);
                    return record;
                });

        Dataset<Row> recordDF = spark.createDataFrame(df, Record.class);
        recordDF.createOrReplaceTempView("search");

        Dataset<Row> result = spark.sql(" select count(*) from search where keyword is not null and keyword !=''");
        result.show();
    }
}
