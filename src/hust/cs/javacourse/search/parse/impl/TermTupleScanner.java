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

public class TermTupleScanner extends AbstractTermTupleScanner {

    private int curPos = 0;
    private List<TermTuple> tuples = new ArrayList<TermTuple>();

    public List<TermTuple> getTerms() {
        return tuples;
    }

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
