package hust.cs.javacourse.search.index.impl;

import hust.cs.javacourse.search.index.AbstractTermTuple;

public class TermTuple extends AbstractTermTuple {
    /**
     * 缺省构造函数
     */
    public TermTuple() {
    }



    

    public TermTuple(Term term, int pos) {
        this.term = term;
        this.curPos = pos;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof  TermTuple){
            return ((TermTuple) obj).freq == this.freq
                    && ((TermTuple) obj).curPos == this.curPos
                    && ((TermTuple) obj).term == this.term;
        }
        return false;
    }

    @Override
    public String toString() {
        return "TermTuple{" +
                "term=" + term +
                ", freq=" + freq +
                ", curPos=" + curPos +
                '}';
    }
}
