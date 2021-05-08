/**
 * author: garfield
 * made on 2021/4/22
 */
package hust.cs.javacourse.search.run;

import hust.cs.javacourse.search.index.AbstractDocumentBuilder;
import hust.cs.javacourse.search.index.AbstractIndex;
import hust.cs.javacourse.search.index.AbstractIndexBuilder;
import hust.cs.javacourse.search.index.impl.DocumentBuilder;
import hust.cs.javacourse.search.index.impl.Index;
import hust.cs.javacourse.search.index.impl.IndexBuilder;
import hust.cs.javacourse.search.util.Config;

import java.io.*;

/**
 * 测试索引构建
 */
public class TestBuildIndex {
    /**
     * 索引构建程序入口
     * 1.txt
     * 2.txt
     * 3.txt
     * @param args : 命令行参数
     */
    public static void main(String[] args) {


        AbstractDocumentBuilder documentBuilder = new DocumentBuilder();
        AbstractIndexBuilder indexBuilder = new IndexBuilder(documentBuilder);
        String rootDir = Config.DOC_DIR;

        AbstractIndex index = indexBuilder.buildIndex(rootDir);
        System.out.println("Start build index ...");
        System.out.println("Index Detail:");
        index.docIdToDocPathMapping.forEach((key,val)->{
            System.out.println(key + "------>" + val);
        });
        index.termToPostingListMapping.forEach((key,val)->{
            System.out.println(key + "------>" + val);
        });
//        测试保存到文件
        String indexFile = Config.INDEX_DIR + "index.dat";
        index.save(new File(indexFile)); //索引保存到文件
        //测试从文件读取
        AbstractIndex index2 = new Index(); //创建一个空的 index
        index2.load(new File(indexFile)); //从文件加载对象的内容
        System.out.println("\n-------------------\n");
        System.out.println("Loading index from index.dat");
        index.docIdToDocPathMapping.forEach((key,val)->{
            System.out.println(key + "------>" + val);
        });
        index.termToPostingListMapping.forEach((key,val)->{
            System.out.println(key + "------>" + val);
        });




    }
}
