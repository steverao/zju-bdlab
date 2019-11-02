package cn.edu.zju;

import org.apache.avro.generic.GenericData;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.PriorityQueue;

/**
 * @author raozihao
 * @date 2019/10/31
 */
public class KeywordRankingTest {

    class Record {

        String keyword;
        int num;

        Record(String keyword, int num) {
            this.num = num;
            this.keyword = keyword;
        }
    }

    @Test
    public void sortTest() {
        List<String> list = new ArrayList<>();
        list.add("bdb");
        list.add("a");
        list.add("bc");
        list.sort(Comparator.naturalOrder());
        System.out.println(list);
    }

    @Test
    public void priorityQueueTest() {
        PriorityQueue<Record> queue = new PriorityQueue<>(5, Comparator.comparingInt((Record r) -> r.num));
        Record Record = new Record("a", 10);
        queue.add(Record);
        Record.num = 100;
        Record.keyword = "b";
        queue.add(Record);
        int cnt = 0;
        while (queue.size() != 0) {
            Record = queue.poll();
            System.out.println("#" + cnt++ + " : keyword=" + Record.keyword + " cnt=" + Record.num);
        }
    }

    @Test
    public void hashCodeTest() {
        String str = "42034q34q";
        System.out.println(str.hashCode());
    }


}
