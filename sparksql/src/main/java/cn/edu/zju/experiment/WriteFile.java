package cn.edu.zju.experiment;

import java.io.*;

/**
 * @author raozihao
 * @date 2019/10/29
 */
public class WriteFile {
    public static void main(String[] args) {
        String path = "/Users/raozihao/Desktop/bigdata-experiment/bigdata-experiment-result";
        File file = new File(path );

        String str = "这些都将写入文件中";
        byte[] b = str.getBytes();
        OutputStream out = null;
        try {
            out = new FileOutputStream(file);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        try {
            out.write(b);
            out.close();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }
}
