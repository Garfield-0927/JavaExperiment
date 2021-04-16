package hust.cs.javacourse.search.query.impl;

import hust.cs.javacourse.search.index.AbstractIndex;
import hust.cs.javacourse.search.index.AbstractTerm;
import hust.cs.javacourse.search.index.impl.Index;
import hust.cs.javacourse.search.query.AbstractHit;
import hust.cs.javacourse.search.query.AbstractIndexSearcher;
import hust.cs.javacourse.search.query.Sort;

import java.io.File;

// TODO
public class IndexSearcher extends AbstractIndexSearcher {
    public IndexSearcher() {
    }

    @Override
    public void open(String indexFile) {
        try {
            this.index.load(new File(indexFile));
        } catch (Exception err){
            System.out.println(err);
        }
    }

    @Override
    public AbstractHit[] search(AbstractTerm queryTerm, Sort sorter) {
        return new AbstractHit[0];
    }

    @Override
    public AbstractHit[] search(AbstractTerm queryTerm1, AbstractTerm queryTerm2, Sort sorter, LogicalCombination combine) {
        return new AbstractHit[0];
    }
}
