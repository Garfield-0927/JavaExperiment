package hust.cs.javacourse.search.run;

import hust.cs.javacourse.search.index.AbstractTerm;
import hust.cs.javacourse.search.index.impl.Term;
import hust.cs.javacourse.search.parse.AbstractTermTupleStream;
import hust.cs.javacourse.search.query.AbstractHit;
import hust.cs.javacourse.search.query.AbstractIndexSearcher;
import hust.cs.javacourse.search.query.impl.IndexSearcher;
import hust.cs.javacourse.search.query.impl.Sort;
import hust.cs.javacourse.search.util.Config;

import javax.swing.plaf.nimbus.AbstractRegionPainter;
import java.util.Locale;

/**
 * 测试搜索
 */
public class TestSearchIndex {
    /**
     * 搜索程序入口
     *
     * @param args ：命令行参数
     */
    public static void main(String[] args) {
        AbstractIndexSearcher searcher = new IndexSearcher();
        searcher.open(Config.INDEX_DIR + "index.dat");
        AbstractTerm queryTerm1 = new Term("Activity".toLowerCase());
        AbstractTerm queryTerm2 = new Term("coronavirus".toLowerCase());

        AbstractHit[] hits = searcher.search(queryTerm1, queryTerm2, new Sort(), AbstractIndexSearcher.LogicalCombination.OR);
        System.out.println("======================================");
        if (hits.length != 0) {
            for (AbstractHit hit : hits) {
                System.out.println(hit.getContent());
                System.out.println("======================================");
            }
        } else {
            System.out.println("Not Found");
        }

    }
}
