package cn.edu.zju.experiment;

import java.io.*;
import java.util.PriorityQueue;
import java.util.Queue;
import java.util.Scanner;

/**
 * @author raozihao
 * @date 2019/11/1
 */
public class CombinerResult {

    private static final int topLength = 10;

    static class Record {
        String keyword;
        int num;
    }
    public static void main(String[] args) throws IOException {
        String inputPath="/Users/raozihao/Desktop/bd-lab/inter-result.txt";
        String outputPath="/Users/raozihao/Desktop/bd-lab/final-result.txt";
        InputStream is = new FileInputStream(inputPath);
        OutputStreamWriter osw = new OutputStreamWriter(new FileOutputStream(outputPath));

        Scanner cin = new Scanner(is);

        Queue queue = new PriorityQueue<Record>(topLength, (o1, o2) -> o1.num - o2.num);

        Record node;
        Record nodeTo;

        while (cin.hasNext()){
            String str = cin.nextLine();
            String[] x = str.split(" : ");
            node = new Record();
            node.keyword = x[0];
            node.num = Integer.parseInt(x[1]);
            if (queue.size()==topLength){
                nodeTo = (Record) queue.peek();
                if (nodeTo.num<node.num){
                    queue.remove();
                    queue.add(node);
                }
            }else{
                queue.add(node);
            }
        }

        Record ans[] = new Record[topLength];
        int index = topLength;

        while (!queue.isEmpty()){
            node = (Record) queue.poll();
            ans[--index] = node;
        }
        for (int i=0;i<topLength;i++){
            osw.write(ans[i].keyword+"\t"+ans[i].num+"\r\n");
        }
        osw.close();
        is.close();

    }
}
