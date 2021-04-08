package hust.cs.javacourse.search.run;

import hust.cs.javacourse.search.index.AbstractDocumentBuilder;
import hust.cs.javacourse.search.index.AbstractIndex;
import hust.cs.javacourse.search.index.AbstractIndexBuilder;
import hust.cs.javacourse.search.index.impl.Posting;
import hust.cs.javacourse.search.util.Config;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

/**
 * 测试索引构建
 */
public class TestBuildIndex {
    /**
     *  索引构建程序入口
     * @param args : 命令行参数
     */
    public static void main(String[] args){
        Map<Integer, String> doc = new TreeMap<>();
        doc.put(1,"abc");
        doc.put(2,"bbb");
        doc.put(3,"ccc");
        System.out.println(doc.toString());

    }
}
