package hust.cs.javacourse.search.query.impl;

import hust.cs.javacourse.search.index.AbstractIndex;
import hust.cs.javacourse.search.index.AbstractPosting;
import hust.cs.javacourse.search.index.AbstractPostingList;
import hust.cs.javacourse.search.index.AbstractTerm;
import hust.cs.javacourse.search.index.impl.Index;
import hust.cs.javacourse.search.index.impl.Posting;
import hust.cs.javacourse.search.query.AbstractHit;
import hust.cs.javacourse.search.query.AbstractIndexSearcher;
import hust.cs.javacourse.search.query.Sort;
import hust.cs.javacourse.search.util.Config;

import java.io.File;
import java.lang.reflect.Array;
import java.util.*;

public class IndexSearcher extends AbstractIndexSearcher {
    public IndexSearcher() {
    }
    /**
     * 从指定索引文件打开索引，加载到index对象里. 一定要先打开索引，才能执行search方法
     * @param indexFile ：指定索引文件
     */
    @Override
    public void open(String indexFile) {
        try {
            this.index.load(new File(indexFile));
        } catch (Exception err) {
            err.printStackTrace();
        }
    }
    /**
     * 根据单个检索词进行搜索
     * @param queryTerm ：检索词
     * @param sorter ：排序器
     * @return ：命中结果数组
     */
    @Override
    public AbstractHit[] search(AbstractTerm queryTerm, Sort sorter) {
        AbstractPostingList plist = this.index.search(queryTerm);
        if (plist != null) {
            Map<AbstractTerm, AbstractPosting> termPostingMapping = new HashMap<>();
            AbstractHit[] hits = new AbstractHit[plist.size()];
            for (int i = 0; i < plist.size(); i++) {
                AbstractPosting posting = plist.get(i);
                termPostingMapping.put(queryTerm, posting);
                hits[i] = new Hit(posting.getDocId(), this.index.getDocName(posting.getDocId()), termPostingMapping);       // 这里传文件名即可，不然会找不到文件，详情参见fileUtil中的read方法
                hits[i].setScore(sorter.score(hits[i]));
                termPostingMapping.clear();
            }
            sorter.sort(Arrays.asList(hits));
            return hits;
        } else {
            return null;
        }

    }
    /**
     *
     * 根据二个检索词进行搜索
     * @param queryTerm1 ：第1个检索词
     * @param queryTerm2 ：第2个检索词
     * @param sorter ：    排序器
     * @param combine ：   多个检索词的逻辑组合方式
     * @return ：命中结果数组
     */
    @Override
    public AbstractHit[] search(AbstractTerm queryTerm1, AbstractTerm queryTerm2, Sort sorter, LogicalCombination combine) {
        AbstractPostingList plist1 = this.index.search(queryTerm1);
        AbstractPostingList plist2 = this.index.search(queryTerm2);
        Map<AbstractTerm, AbstractPosting> termPostingMapping = new HashMap<>();
        ArrayList<AbstractHit> hits = new ArrayList<>();
        switch (combine) {
            case OR:
                if (plist1 != null) {
                    for (int i = 0; i < plist1.size(); i++) {
                        AbstractPosting posting = plist1.get(i);
                        termPostingMapping.put(queryTerm1, posting);
                        hits.add(new Hit(posting.getDocId(), this.index.getDocName(posting.getDocId()), termPostingMapping));     // 这里传文件名即可，不然会找不到文件，详情参见fileUtil中的read方法
                        hits.get(i).setScore(sorter.score(hits.get(i)));
                        termPostingMapping.clear();
                    }
                }
                if (plist2 != null) {
                    for (int i = 0; i < plist2.size(); i++) {
                        int flag = 0;
                        AbstractPosting posting = plist2.get(i);
                        for (int j = 0; j < hits.size(); j++) {
                            AbstractHit item = hits.get(j);
                            if (item.getDocId() == posting.getDocId()) {
                                item.getTermPostingMapping().put(queryTerm2, posting);
                                item.setScore(sorter.score(item));
                                flag = 1;
                            }
                        }
                        if (flag == 0) {
                            termPostingMapping.put(queryTerm2, posting);
                            hits.add(new Hit(posting.getDocId(), this.index.getDocName(posting.getDocId()), termPostingMapping));
                            hits.get(hits.size()-1).setScore(sorter.score(hits.get(hits.size()-1)));
                            termPostingMapping.clear();
                        }
                    }
                }
                break;
            case AND:
                if (plist1 != null && plist2 != null) {
                    for (int i = 0; i < plist1.size(); i++) {
                        AbstractPosting posting = plist1.get(i);
                        for (int j = 0; j < plist2.size(); j++) {
                            AbstractPosting posting1 = plist2.get(j);
                            if (posting.getDocId() == posting1.getDocId()) {
                                termPostingMapping.put(queryTerm1, posting);
                                termPostingMapping.put(queryTerm2, posting1);
                                hits.add(new Hit(posting.getDocId(), this.index.getDocName(posting.getDocId()), termPostingMapping));
                                hits.get(i).setScore(sorter.score(hits.get(i)));
                                termPostingMapping.clear();
                            }
                        }
                    }
                }
                break;
        }

        sorter.sort(hits);
        return hits.toArray(new AbstractHit[hits.size()]);
    }
}
