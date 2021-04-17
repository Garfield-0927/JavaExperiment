package hust.cs.javacourse.search.query.impl;

import hust.cs.javacourse.search.index.AbstractPosting;
import hust.cs.javacourse.search.index.AbstractTerm;
import hust.cs.javacourse.search.index.impl.Term;
import hust.cs.javacourse.search.query.AbstractHit;

import java.util.Iterator;
import java.util.List;
import java.util.Set;

public class Sort implements hust.cs.javacourse.search.query.Sort {
    @Override
    public void sort(List<AbstractHit> hits) {

    }

    @Override
    public double score(AbstractHit hit) {
        double score = 0;
        Set<AbstractTerm> keys = hit.getTermPostingMapping().keySet();
        Iterator iter = keys.iterator();
        while (iter.hasNext()) {
            score += hit.getTermPostingMapping().get(iter.next()).getFreq();
        }
        return score;
    }
}
