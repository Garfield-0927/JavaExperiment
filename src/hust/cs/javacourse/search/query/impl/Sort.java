package hust.cs.javacourse.search.query.impl;

import hust.cs.javacourse.search.query.AbstractHit;

import java.util.List;

public class Sort implements hust.cs.javacourse.search.query.Sort {
    @Override
    public void sort(List<AbstractHit> hits) {

    }

    @Override
    public double score(AbstractHit hit) {
        return 0;
    }
}
