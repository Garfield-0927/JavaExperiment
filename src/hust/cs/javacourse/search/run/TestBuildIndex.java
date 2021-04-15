package hust.cs.javacourse.search.run;

import hust.cs.javacourse.search.index.AbstractIndex;
import hust.cs.javacourse.search.index.impl.DocumentBuilder;
import hust.cs.javacourse.search.index.impl.IndexBuilder;
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
            DocumentBuilder docBuilder = new DocumentBuilder();
            IndexBuilder indexBuilder = new IndexBuilder(docBuilder);
            String rootDirectory = Config.DOC_DIR;
            AbstractIndex index = indexBuilder.buildIndex(rootDirectory);
            System.out.println(index);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }


    }
}
