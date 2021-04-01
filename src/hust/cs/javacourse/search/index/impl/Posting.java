package hust.cs.javacourse.search.index.impl;

import hust.cs.javacourse.search.index.AbstractPosting;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class Posting extends AbstractPosting {
    @Override
    public boolean equals(Object obj) {
        if (obj instanceof  Posting){
            return ((Posting) obj).docId == this.docId
                    && ((Posting) obj).freq==this.freq
                    && ((Posting) obj).positions==this.positions;
        }
        return false;
    }

    @Override
    public int getDocId() {
        return this.docId;
    }

    @Override
    public void setDocId(int docId) {
        this.docId = docId;
    }

    @Override
    public int getFreq() {
        return this.freq;
    }

    @Override
    public void setFreq(int freq) {
        if (freq > 0){
            this.freq = freq;
        }
    }

    @Override
    public List<Integer> getPositions() {
        return this.positions;
    }

    @Override
    public void setPositions(List<Integer> positions) {
        this.positions = positions;
    }

    @Override
    public int compareTo(AbstractPosting o) {
        return this.getDocId() - o.getDocId();
    }

    @Override
    public void sort() {
        this.positions.sort(Comparator.naturalOrder());
    }

    @Override
    public void writeObject(ObjectOutputStream out) {
        try {
            out.writeInt(this.docId);
            out.writeInt(this.freq);
            out.writeObject(this.positions);
        } catch (Exception err){
            System.out.println("ERROR: " + err);
        }
    }

    @Override
    public void readObject(ObjectInputStream in) {
        try {
            this.docId = in.readInt();
            this.freq  = in.readInt();
            this.positions = (ArrayList<Integer>)in.readObject();
        } catch (Exception err){
            System.out.println("ERROR: " + err);
        }
    }

    @Override
    public String toString() {
        return "Posting{" +
                "docId=" + docId +
                ", freq=" + freq +
                ", positions=" + positions +
                '}';
    }
}
