package hust.cs.javacourse.search.index.impl;

import hust.cs.javacourse.search.index.AbstractTerm;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

public class Term extends AbstractTerm {

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof Term) {
            return ((Term) obj).content == this.content;
        }
        return false;
    }

    @Override
    public String toString() {
        return "Term{" +
                "content='" + content + '\'' +
                '}';
    }

    @Override
    public String getContent() {
        return this.content;
    }

    @Override
    public void setContent(String content) {
        this.content = content;
    }

    @Override
    public int compareTo(AbstractTerm o) {
        return this.getContent().compareTo(o.getContent());
    }

    @Override
    public void writeObject(ObjectOutputStream out) {
        try {
            out.writeUTF(this.content);
        } catch (Exception err) {
            System.out.println("ERROR: " + err);
        }
    }

    @Override
    public void readObject(ObjectInputStream in) {
        try {
            this.content = in.readUTF();
        } catch (Exception err) {
            System.out.println("ERROR: " + err);
        }
    }
}
