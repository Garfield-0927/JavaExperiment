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
     *1.txt
     * 2.txt
     * 3.txt
     * @param args : 命令行参数
     */
    public static void main(String[] args) {


        AbstractDocumentBuilder documentBuilder = new DocumentBuilder();
        AbstractIndexBuilder indexBuilder = new IndexBuilder(documentBuilder);
        String rootDir = Config.DOC_DIR;
        System.out.println("Start build index ...");
        AbstractIndex index = indexBuilder.buildIndex(rootDir);
        index.optimize();
        System.out.println(index); //控制台打印 index 的内容
        //测试保存到文件
        String indexFile = Config.INDEX_DIR + "index.dat";
        index.save(new File(indexFile)); //索引保存到文件
        //测试从文件读取
        AbstractIndex index2 = new Index(); //创建一个空的 index
        index2.load(new File(indexFile)); //从文件加载对象的内容
        System.out.println("\n-------------------\n");
        System.out.println(index2); //控制台打印 index2 的内容


//        DocumentBuilder docBuilder = new DocumentBuilder();
//        IndexBuilder indexBuilder = new IndexBuilder(docBuilder);
//        String rootDirectory = Config.DOC_DIR;
//        AbstractIndex index = indexBuilder.buildIndex(rootDirectory);
//        index.optimize();
//        System.out.println(index);


    }
}
