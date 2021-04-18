package hust.cs.javacourse.search.run;

import hust.cs.javacourse.search.index.AbstractPosting;
import hust.cs.javacourse.search.index.AbstractPostingList;
import hust.cs.javacourse.search.index.impl.Posting;
import hust.cs.javacourse.search.index.impl.PostingList;

import java.util.ArrayList;
import java.util.List;

public class test {
    public static void main(String[] args) {
        List<Integer> list = new ArrayList<Integer>();
        list.add(1);
        list.add(2);
        list.add(3);
        list.add(4);
        AbstractPostingList postingList = new PostingList();
        AbstractPosting posting1 = new Posting(1,4,list);
        postingList.add(posting1);
        System.out.println(postingList.toString());
        List<Integer> list2 = new ArrayList<Integer>();
        list2.add(4);
        list2.add(1);
        list2.add(3);
        list2.add(2);
        AbstractPosting posting2 = new Posting(1,4,list2);
        postingList.add(posting2);
        System.out.println(postingList.toString());

    }
}
