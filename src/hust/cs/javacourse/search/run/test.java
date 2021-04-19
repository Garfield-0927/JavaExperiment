package hust.cs.javacourse.search.run;

import hust.cs.javacourse.search.index.AbstractDocument;
import hust.cs.javacourse.search.index.AbstractPosting;
import hust.cs.javacourse.search.index.AbstractPostingList;
import hust.cs.javacourse.search.index.impl.Document;
import hust.cs.javacourse.search.index.impl.DocumentBuilder;
import hust.cs.javacourse.search.index.impl.Posting;
import hust.cs.javacourse.search.index.impl.PostingList;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class test {
    public static void main(String[] args) {

        AbstractDocument doc = new DocumentBuilder().build(0, "C:\\Users\\A\\Desktop\\Experiment1Test\\test\\text\\2.txt", new File("C:\\Users\\A\\Desktop\\Experiment1Test\\test\\text\\2.txt"));
        System.out.println(doc.toString());

    }
}
