package hust.cs.javacourse.search.run;

import hust.cs.javacourse.search.parse.AbstractTermTupleStream;
import hust.cs.javacourse.search.parse.impl.LengthFilter;
import hust.cs.javacourse.search.parse.impl.PatternFilter;
import hust.cs.javacourse.search.parse.impl.StopWordsFilter;
import hust.cs.javacourse.search.parse.impl.TermTupleScanner;
import hust.cs.javacourse.search.util.Config;

import java.io.*;

/**
 * 测试索引构建
 */
public class TestBuildIndex {
    /**
     *  索引构建程序入口
     * @param args : 命令行参数
     */
    public static void main(String[] args){

        try {
            BufferedReader reader = new BufferedReader(
                    new InputStreamReader(new FileInputStream(new File(Config.DOC_DIR + "1.txt")))
            );
            AbstractTermTupleStream stream = new StopWordsFilter(new LengthFilter(new PatternFilter(new TermTupleScanner(reader))));
            System.out.println(stream.next());
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }


    }
}
