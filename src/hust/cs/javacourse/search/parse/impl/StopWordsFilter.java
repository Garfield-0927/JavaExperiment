package hust.cs.javacourse.search.parse.impl;

import hust.cs.javacourse.search.index.AbstractTermTuple;
import hust.cs.javacourse.search.parse.AbstractTermTupleFilter;
import hust.cs.javacourse.search.parse.AbstractTermTupleStream;
import hust.cs.javacourse.search.util.StopWords;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class StopWordsFilter extends AbstractTermTupleFilter {

    private List<String> stopWord;

    /**
     * 构造函数
     *
     * @param input : 输入流
     */
    public StopWordsFilter(AbstractTermTupleStream input) {
        super(input);
        this.stopWord = new ArrayList<String>(Arrays.asList(StopWords.STOP_WORDS));
    }

    /**
     * 获得下一个三元组
     * 过滤掉停等词
     *
     * @return: 下一个三元组；如果到了流的末尾，返回null
     */
    @Override
    public AbstractTermTuple next() {
        AbstractTermTuple stopWordFilter = input.next();
        if (stopWordFilter == null) return null;
        while (stopWord.contains(stopWordFilter.term.getContent())) {
            stopWordFilter = input.next();
            if (stopWordFilter == null) return null;
        }
        return stopWordFilter;
    }

}
