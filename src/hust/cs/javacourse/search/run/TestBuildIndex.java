package hust.cs.javacourse.search.run;

import hust.cs.javacourse.search.index.AbstractDocumentBuilder;
import hust.cs.javacourse.search.index.AbstractIndex;
import hust.cs.javacourse.search.index.AbstractIndexBuilder;
import hust.cs.javacourse.search.index.impl.Posting;
import hust.cs.javacourse.search.util.Config;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * 测试索引构建
 */
public class TestBuildIndex {
    /**
     *  索引构建程序入口
     * @param args : 命令行参数
     */
    public static void main(String[] args){
        List<Integer> list1 = new ArrayList<Integer>();
        list1.add(1);
        list1.add(2);
        Posting pos1 = new Posting(1,1, list1);
        Posting pos2 = new Posting(1,2,list1);
        Posting pos3 = new Posting(1,3,list1);

        List<Posting> postingList = new ArrayList<Posting>();
        postingList.add(pos1);
        postingList.add(pos2);
        postingList.add(pos3);
        postingList.remove(0);
        for (Posting temp: postingList){
            System.out.println(temp.getDocId()+" "+temp.getFreq());
        }
        postingList.remove(new Posting(1,2,list1));
        System.out.println("------------");
        for (Posting temp: postingList){
            System.out.println(temp.getDocId()+" "+temp.getFreq());
        }

    }
}
