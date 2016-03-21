package com.ustbgao.text.util;

import org.ansj.domain.Term;
import org.ansj.splitWord.Analysis;
import org.ansj.splitWord.analysis.BaseAnalysis;
import org.ansj.splitWord.analysis.ToAnalysis;
import org.nlpcn.commons.lang.util.IOUtil;

import java.io.*;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * author gaoqi
 * created At 2015/3/17.
 * <p/>
 * 根据提供的语料库，进行分词
 */
public class SplitWordUtil {

    public static void splitWordByLine(String filePath, String encodingType) throws IOException {

        String temp = null;
        BufferedReader reader = null;
        FileWriter writer = null;
        try {
            reader = IOUtil.getReader(filePath, encodingType);
            ToAnalysis.parse("test 123 孙");
            writer = new FileWriter("d:/resultbig.txt");
            long start = System.currentTimeMillis();
            int allCount = 0;
            int termcnt = 0;
            Set<String> set = new HashSet<String>();
            String content = null;
            while ((temp = reader.readLine()) != null) {

                content = temp.trim();
                if (content.length() > 0) {
                    allCount += content.length();
                    List<Term> result = ToAnalysis.parse(content);
                    for (Term term : result) {
                        String item = term.getName().trim();
                        if (item.length() > 0) {
                            termcnt++;
                            writer.write(item.trim() + " ");
                            set.add(item);
                        }
                    }
                    writer.write("\n");
                }
            }
        long end = System.currentTimeMillis();
        System.out.println("共" + termcnt + "个term，" + set.size() + "个不同的词，共 "
                + allCount + " 个字符，每秒处理了:" + (allCount * 1000.0 / (end - start)));
        } catch (IOException e)
        {
            e.printStackTrace();
        } finally{
            if (null != reader) {
                try {
                    reader.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (null != writer) {
                writer.close();
            }
        }

}

    public static void splitWord(String filePath, String encodingType) throws IOException {

        BufferedReader reader = null;
        try {
            reader = IOUtil.getReader(filePath, encodingType);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        Analysis na = new BaseAnalysis(reader);

        long start = System.currentTimeMillis();
        int allCount = 0;
        Term term = null;
        while ((term = na.next()) != null) {
            if (term.getOffe() % 10000 == 0) {
                System.out.println(term.getOffe() + "\t" + term.getName());
            }
            allCount += term.getName().length();

            if (allCount > 30000000) {
                break;
            }
        }
        long end = System.currentTimeMillis();
        System.out.println(end - start);
        System.out.println("共 " + allCount + " 个字符，每秒处理了:" + (allCount * 1000.0 / (end - start)));
    }

    public static void main(String[] args) {

        System.out.println(ToAnalysis.parse("我今天去球店，买了个乒乓球拍"));
        FileWriter writer = null;
        try {
            writer = new FileWriter(new File("d:/split_word.txt"));
        } catch (IOException e) {
            e.printStackTrace();
        } finally {

        }

        try {
            SplitWordUtil.splitWordByLine("d:/sougou_result.txt","utf-8");
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
