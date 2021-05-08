package hust.cs.javacourse.search.index.impl;

import hust.cs.javacourse.search.index.AbstractTermTuple;
/**
 * <pre>
 *      一个TermTuple对象为三元组(单词，出现频率，出现的当前位置).
 *      当解析一个文档时，每解析到一个单词，应该产生一个三元组，其中freq始终为1(因为单词出现了一次).
 * </pre>
 *
 *
 */
public class TermTuple extends AbstractTermTuple {
    /**
     * 缺省构造函数
     */
    public TermTuple() {
    }


    /**
     * constructor
     * @param term  Term
     * @param pos   position
     */
    public TermTuple(Term term, int pos) {
        this.term = term;
        this.curPos = pos;
    }

    /**
     * 比较obj是否等于实例
     * @param obj ：要比较的另外一个三元组
     * @return
     */
    @Override
    public boolean equals(Object obj) {
        if (obj instanceof  TermTuple){
            return ((TermTuple) obj).curPos == this.curPos && ((TermTuple) obj).term.getContent().equals(this.term.getContent());
        }
        return false;
    }

    /**
     * 获取字符串表示
     * @return
     */
    @Override
    public String toString() {
        return "TermTuple{" +
                "term=" + term +
                ", freq=" + freq +
                ", curPos=" + curPos +
                '}';
    }
}
