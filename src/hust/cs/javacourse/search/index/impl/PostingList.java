package hust.cs.javacourse.search.index.impl;

import hust.cs.javacourse.search.index.AbstractPosting;
import hust.cs.javacourse.search.index.AbstractPostingList;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class PostingList extends AbstractPostingList {
    @Override
    public String toString() {
        return "PostingList{" +
                "list=" + list +
                '}';
    }

    @Override
    public void add(AbstractPosting posting) {
        this.list.add(posting);
    }

    @Override
    public void add(List<AbstractPosting> postings) {
        this.list.addAll(postings);
    }

    @Override
    public AbstractPosting get(int index) {
        return list.get(index);
    }

    @Override
    public int indexOf(AbstractPosting posting) {
        return list.indexOf(posting);
    }

    @Override
    public int indexOf(int docId) {
        for (int i = 0; i < this.list.size(); i++) {
            if (this.list.get(i).getDocId() == docId) {
                return i;
            }
        }
        return -1;
    }

    @Override
    public boolean contains(AbstractPosting posting) {
        return this.list.contains(posting);
    }

    @Override
    public void remove(int index) {
        if (index >= 0 && index < this.list.size()) {
            this.list.remove(index);
        }
    }

    @Override
    public void remove(AbstractPosting posting) {
        this.list.remove(posting);
    }

    @Override
    public int size() {
        return this.list.size();
    }

    @Override
    public void clear() {
        this.list.clear();
    }

    @Override
    public boolean isEmpty() {
        return this.list.isEmpty();
    }

    @Override
    public void sort() {
        this.list.sort(Comparator.naturalOrder());
    }

    @Override
    public void writeObject(ObjectOutputStream out) {
        try {
            out.writeObject(this.list);
        } catch (Exception err) {
            System.out.println("ERROR: " + err);
        }
    }

    @Override
    public void readObject(ObjectInputStream in) {
        try {
            this.list = (ArrayList<AbstractPosting>)in.readObject();
        } catch (Exception err) {
            System.out.println("ERROR: " + err);
        }
    }
}
