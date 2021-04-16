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
import java.util.ArrayList;

// TODO
public class IndexSearcher extends AbstractIndexSearcher {
    public IndexSearcher() {
    }

    @Override
    public void open(String indexFile) {
        try {
            this.index.load(new File(indexFile));
        } catch (Exception err) {
            System.out.println(err);
        }
    }

    @Override
    public AbstractHit[] search(AbstractTerm queryTerm, Sort sorter) {
        AbstractPostingList plist = this.index.search(queryTerm);
        AbstractHit[] hits = new AbstractHit[plist.size()];
        for (int i = 0; i < plist.size(); i++) {
            AbstractPosting posting = plist.get(i);
            hits[i] = new Hit(posting.getDocId(), this.index.getDocName(posting.getDocId()));       // 这里传文件名即可，不然会找不到文件，详情参见fileUtil中的read方法
        }
        return hits;
    }

    @Override
    public AbstractHit[] search(AbstractTerm queryTerm1, AbstractTerm queryTerm2, Sort sorter, LogicalCombination combine) {
        AbstractPostingList plist1 = this.index.search(queryTerm1);
        AbstractPostingList plist2 = this.index.search(queryTerm2);
        System.out.println(plist1);
        System.out.println(plist2);
        ArrayList<AbstractHit> hits = new ArrayList<>();
        switch (combine) {
            case OR:
                if (plist1 != null) {
                    for (int i = 0; i < plist1.size(); i++) {
                        AbstractPosting posting = plist1.get(i);
                        hits.add(new Hit(posting.getDocId(), this.index.getDocName(posting.getDocId())));     // 这里传文件名即可，不然会找不到文件，详情参见fileUtil中的read方法
                    }
                }
                if (plist2 != null) {
                    for (int i = 0; i < plist2.size(); i++) {
                        AbstractPosting posting = plist2.get(i);
                        hits.removeIf(item -> item.getDocId() == posting.getDocId());
                        hits.add(new Hit(posting.getDocId(), this.index.getDocName(posting.getDocId())));
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
                                hits.add(new Hit(posting.getDocId(), this.index.getDocName(posting.getDocId())));
                            }
                        }
                    }
                }
                break;
        }

        return hits.toArray(new AbstractHit[hits.size()]);
    }
}
