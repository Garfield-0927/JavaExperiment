package hust.cs.javacourse.search.index.impl;

import hust.cs.javacourse.search.index.*;

import java.io.*;
import java.util.*;

/**
 * <pre>
 * Index是内存中的倒排索引对象的类.
 *      一个倒排索引对象包含了一个文档集合的倒排索引.
 *      内存中的倒排索引结构为HashMap，key为Term对象，value为对应的PostingList对象.
 *      另外在AbstractIndex里还定义了从docId和docPath之间的映射关系.
 *      必须实现下面接口:
 *          FileSerializable：可序列化到文件或从文件反序列化.
 * </pre>
 */
public class Index extends AbstractIndex implements FileSerializable {


    /**
     * 返回索引的字符串表示
     *
     * @return 索引的字符串表示
     */
    @Override
    public String toString() {
        termToPostingListMapping.forEach((key,value)->{
            System.out.println(key.toString()+": "+value.toString());
        });
        docIdToDocPathMapping.forEach((key,value)->{
            System.out.println(key.toString()+": "+value.toString());
        });
        return "Index{\n" +
                "docIdToDocPath={\n" + this.docIdToDocPathMapping.toString() +
                "}, \n" +
                "termToPostingList={\n" + this.termToPostingListMapping.toString() +
                "}";
    }

    /**
     * 添加文档到索引，更新索引内部的HashMap
     *
     * @param document ：文档的AbstractDocument子类型表示
     */
    @Override
    public void addDocument(AbstractDocument document) {
        this.docIdToDocPathMapping.put(document.getDocId(), document.getDocPath());
        Map<AbstractTerm, Posting> map = new TreeMap<AbstractTerm, Posting>();
        for (AbstractTermTuple tuple : document.getTuples()) {
            if (!map.containsKey(tuple.term)) {
                List<Integer> pos = new ArrayList<Integer>();
                pos.add(tuple.curPos);
                Posting posting = new Posting(document.getDocId(), 1, pos);
                map.put(tuple.term, posting);
            } else {
                Posting p = map.get(tuple.term);
                p.setFreq(p.getFreq() + 1);
                List<Integer> pos = p.getPositions();
                pos.add(tuple.curPos);
                p.setPositions(pos);
            }
        }

        map.forEach((key, val) -> {
            if (this.termToPostingListMapping.containsKey(key)) {
                this.termToPostingListMapping.get(key).add(val);
            } else {
                AbstractPostingList pl = new PostingList();
                pl.add(val);
                this.termToPostingListMapping.put(key, pl);
            }
        });
    }

    /**
     * <pre>
     * 从索引文件里加载已经构建好的索引.内部调用FileSerializable接口方法readObject即可
     * @param file ：索引文件
     * </pre>
     */
    @Override
    public void load(File file) {
        try {
            ObjectInputStream in = new ObjectInputStream(new FileInputStream(file));
            this.readObject(in);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * <pre>
     * 将在内存里构建好的索引写入到文件. 内部调用FileSerializable接口方法writeObject即可
     * @param file ：写入的目标索引文件
     * </pre>
     */
    @Override
    public void save(File file) {
        try {
            ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(file));
            this.writeObject(out);
        } catch (IOException err) {
            err.printStackTrace();
        }
    }

    /**
     * 返回指定单词的PostingList
     *
     * @param term : 指定的单词
     * @return ：指定单词的PostingList;如果索引字典没有该单词，则返回null
     */
    @Override
    public AbstractPostingList search(AbstractTerm term) {

        return termToPostingListMapping.getOrDefault(term, null);
    }

    /**
     * 返回索引的字典.字典为索引里所有单词的并集
     *
     * @return ：索引中Term列表
     */
    @Override
    public Set<AbstractTerm> getDictionary() {
        return termToPostingListMapping.keySet();
    }

    /**
     * <pre>
     * 对索引进行优化，包括：
     *      对索引里每个单词的PostingList按docId从小到大排序
     *      同时对每个Posting里的positions从小到大排序
     * 在内存中把索引构建完后执行该方法
     * </pre>
     */
    @Override
    public void optimize() {
        for (AbstractPostingList list : this.termToPostingListMapping.values()) {
            list.sort();
            for (int i = 0; i < list.size(); i++) {
                list.get(i).sort();
            }
        }
    }

    /**
     * 根据docId获得对应文档的完全路径名
     *
     * @param docId ：文档id
     * @return : 对应文档的完全路径名
     */
    @Override
    public String getDocName(int docId) {
        return this.docIdToDocPathMapping.getOrDefault(docId, null);
    }

    /**
     * 写到二进制文件
     *
     * @param out :输出流对象
     */
    @Override
    public void writeObject(ObjectOutputStream out) {
        try {
            out.writeObject(this.termToPostingListMapping);
            out.writeObject(this.docIdToDocPathMapping);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 从二进制文件读
     *
     * @param in ：输入流对象
     */
    @Override
    public void readObject(ObjectInputStream in) {
        try {
            this.termToPostingListMapping = (Map<AbstractTerm, AbstractPostingList>) in.readObject();
            this.docIdToDocPathMapping = (Map<Integer, String>) in.readObject();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
}
