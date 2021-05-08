/**
 * author: garfield
 * made on 2021/4/22
 */
package hust.cs.javacourse.search.run;

import hust.cs.javacourse.search.index.*;
import hust.cs.javacourse.search.index.impl.DocumentBuilder;
import hust.cs.javacourse.search.index.impl.IndexBuilder;
import hust.cs.javacourse.search.index.impl.Term;
import hust.cs.javacourse.search.query.AbstractHit;
import hust.cs.javacourse.search.query.AbstractIndexSearcher;
import hust.cs.javacourse.search.query.impl.IndexSearcher;
import hust.cs.javacourse.search.query.impl.SimpleSorter;
import hust.cs.javacourse.search.util.Config;
import hust.cs.javacourse.search.util.StopWords;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;

public class application {

    public static void main(String[] args) {
        AbstractIndexBuilder indexBuilder = null;
        AbstractIndexSearcher indexSearcher = null;
        int opt = 1;        //  用户选项
        while (opt != 0) {
            System.out.println("*******************************************");
            System.out.println("*        Welcome To Search Engine         *");
            System.out.println("*     Designed By Garfield, 2021/4/22     *");
            System.out.println("*  1. Save Index From Existing Articles   *");
            System.out.println("*  2. Load Index From Existing Files      *");
            System.out.println("*  3. Search Specific Articles            *");
            System.out.println("*  0. Exit                                *");
            System.out.println("*******************************************");
            System.out.println("Please Input Your Option: ");
            Scanner scanner = new Scanner(System.in);
            opt = scanner.nextInt();
            switch (opt) {
                case 1:
                    if (indexBuilder == null) {
                        AbstractDocumentBuilder documentBuilder = new DocumentBuilder();
                        indexBuilder = new IndexBuilder(documentBuilder);
                        String rootDir = Config.DOC_DIR;        // 文章所在根目录
                        System.out.println("Start saving index...");
                        indexBuilder.buildIndex(rootDir);
                        System.out.println("Index save finished!");
                    } else {
                        System.out.println("Index already exists!");
                    }
                    break;
                case 2:
                    if (indexSearcher == null) {
                        System.out.println("Start load index...");
                        String indexFile = Config.INDEX_DIR + "index.dat";
                        File file = new File(indexFile);
                        if (file.exists()) {
                            indexSearcher = new IndexSearcher();
                            indexSearcher.open(indexFile);
                            System.out.println("Index load finished!");
                        } else {
                            System.out.println("Please build index and save it to file first!");
                        }
                    } else {
                        System.out.println("Index already exists!");
                    }
                    break;
                case 3:
                    if (indexSearcher == null) {
                        System.out.println("Please Build Index First");
                    } else {
                        System.out.println("1. search articles with one word");
                        System.out.println("2. search articles with two words(AND)");
                        System.out.println("3. search articles with two words(OR)");

                        int opt2;
                        SimpleSorter sorter = new SimpleSorter();
                        Scanner scanner1 = new Scanner(System.in);
                        opt2 = scanner1.nextInt();

                        AbstractHit[] resHits = null;
                        AbstractTerm queryTerm1 = null;
                        AbstractTerm queryTerm2 = null;
                        Scanner scanner2 = new Scanner(System.in);
                        if (opt2 == 1) {
                            System.out.print("Input The Word: ");
                            queryTerm1 = new Term(scanner2.next());
                            if (isIllegalWord(queryTerm1)) {
                                System.out.println("Words Illegal");
                            } else {
                                resHits = indexSearcher.search(queryTerm1, sorter);
                                showRes(resHits);
                            }


                        } else if (opt2 == 2) {
                            System.out.print("Input The Two Words(Aword Bword): ");
                            String buf = scanner2.nextLine();
                            queryTerm1 = new Term(buf.split(" ")[0]);
                            queryTerm2 = new Term(buf.split(" ")[1]);
                            if (isIllegalWord(queryTerm1) || isIllegalWord(queryTerm2)) {
                                System.out.println("Words Illegal");
                            } else {
                                resHits = indexSearcher.search(queryTerm1, queryTerm2, sorter, AbstractIndexSearcher.LogicalCombination.AND);
                                showRes(resHits);
                            }

                        } else if (opt2 == 3) {
                            System.out.print("Input The Two Words(Aword Bword): ");
                            String buf = scanner2.nextLine();
                            queryTerm1 = new Term(buf.split(" ")[0]);
                            queryTerm2 = new Term(buf.split(" ")[1]);
                            if (isIllegalWord(queryTerm1) || isIllegalWord(queryTerm2)) {
                                System.out.println("Words Illegal");
                            } else {
                                resHits = indexSearcher.search(queryTerm1, queryTerm2, sorter, AbstractIndexSearcher.LogicalCombination.OR);
                                showRes(resHits);
                            }
                        } else {
                            System.out.println("Input Error!");
                        }
                    }
                    break;
                case 0:
                    break;
                default:
                    System.out.println("Input Error!");
                    break;
            }

        }


    }


    /**
     * 用来展示结果
     *
     * @param resHits 命中的集合
     */
    public static void showRes(AbstractHit[] resHits) {
        if (resHits != null) {
            for (AbstractHit hit : resHits) {
                System.out.println("======================================================Split Line======================================================");
                System.out.println("docId: " + hit.getDocId());
                System.out.println("docPath: " + hit.getDocPath());
                System.out.println("docScore: " + hit.getScore());
                System.out.println("docContent: " + hit.getContent());
            }
            System.out.println("======================================================Split Line======================================================");
        } else {
            System.out.println("Not Found!");
        }
    }


    /**
     * 判断该term是否非法
     *
     * @param term 输入的term
     * @return 返回该term是否非法
     */
    public static boolean isIllegalWord(AbstractTerm term) {
        ArrayList<String> stopWords = new ArrayList<>(Arrays.asList(StopWords.STOP_WORDS));
        return stopWords.contains(term.getContent())
                || term.getContent().length() > Config.TERM_FILTER_MAXLENGTH
                || term.getContent().length() < Config.TERM_FILTER_MINLENGTH
                || !term.getContent().matches(Config.TERM_FILTER_PATTERN);
    }
}
