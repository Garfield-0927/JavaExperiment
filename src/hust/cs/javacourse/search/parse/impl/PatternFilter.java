package hust.cs.javacourse.search.parse.impl;

import hust.cs.javacourse.search.index.AbstractTermTuple;
import hust.cs.javacourse.search.parse.AbstractTermTupleFilter;
import hust.cs.javacourse.search.parse.AbstractTermTupleStream;
import hust.cs.javacourse.search.util.Config;

public class PatternFilter extends AbstractTermTupleFilter {

    public PatternFilter(AbstractTermTupleStream input) {
        super(input);
    }

    @Override
    public AbstractTermTuple next() {
        AbstractTermTuple tuple = input.next();
        if (tuple == null) return null;
        while (!tuple.term.getContent().matches(Config.TERM_FILTER_PATTERN)) {
            tuple = input.next();
            if (tuple == null) return null;
        }
        return tuple;
    }
}
