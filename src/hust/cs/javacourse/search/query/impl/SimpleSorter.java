package hust.cs.javacourse.search.query.impl;


import hust.cs.javacourse.search.index.AbstractTerm;
import hust.cs.javacourse.search.query.AbstractHit;

import java.util.*;

public class SimpleSorter implements hust.cs.javacourse.search.query.Sort {
    @Override
    public void sort(List<AbstractHit> hits) {
        Collections.sort(hits, Comparator.reverseOrder());
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
