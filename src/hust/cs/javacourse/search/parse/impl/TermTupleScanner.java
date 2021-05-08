package hust.cs.javacourse.search.parse.impl;

import hust.cs.javacourse.search.index.AbstractTermTuple;
import hust.cs.javacourse.search.index.impl.Term;
import hust.cs.javacourse.search.index.impl.TermTuple;
import hust.cs.javacourse.search.parse.AbstractTermTupleScanner;
import hust.cs.javacourse.search.util.Config;
import hust.cs.javacourse.search.util.StringSplitter;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
/**
 * <pre>
 *     一个具体的TermTupleScanner对象就是
 *     一个AbstractTermTupleStream流对象，它利用java.io.BufferedReader去读取文本文件得到一个个三元组TermTuple
 *     其具体子类需要重新实现next方法获得文本文件里的三元组
 * </pre>
 */
public class TermTupleScanner extends AbstractTermTupleScanner {
    /**
     * 记录位置
     */
    private int curPos = 0;

    /**
     * TermTuple的集合
     */
    private List<TermTuple> tuples = new ArrayList<TermTuple>();

    /**
     * 获取Terms
     * @return TermTuples
     */
    public List<TermTuple> getTerms() {
        return tuples;
    }

    /**
     * 构造函数
     * @param input BufferedReader对象
     */
    public TermTupleScanner(BufferedReader input) {
        super(input);
        try {
            String str = input.readLine();
            while (str != null) {
                StringSplitter splitter = new StringSplitter();
                splitter.setSplitRegex(Config.STRING_SPLITTER_REGEX);
                List<String> parts = splitter.splitByRegex(str);
                int i;
                for (i = 0; i < parts.size(); i++) {
                    if (!parts.get(i).equals("")){
                        this.tuples.add(new TermTuple(new Term(parts.get(i).toLowerCase()), this.curPos++));
                    }
                }

                str = input.readLine();
            }

        } catch (IOException e) {
            e.printStackTrace();
        }

    }
    /**
     * 获得下一个三元组
     * @return: 下一个三元组；如果到了流的末尾，返回null
     */
    @Override
    public AbstractTermTuple next() {
        if (this.tuples.size()!=0){
            return tuples.remove(0);
        } else {
            return null;
        }
    }

    @Override
    public void close() {
        super.close();
    }
}
