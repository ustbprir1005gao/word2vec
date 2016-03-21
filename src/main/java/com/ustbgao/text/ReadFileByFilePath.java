package com.ustbgao.text;

import java.io.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * author gaoqi
 * created At 2015/3/11.
 * 处理搜狗原始数据集工具类
 */
public class ReadFileByFilePath {

    public static  File[] readFileToFileList(String folderPath)throws IOException{

        File file = new File(folderPath);

        return file.listFiles();

    }

    public static void mergeAllTextToSingleText(File[] files){


        Pattern pattern = Pattern.compile("<content>(.{2,})</content>");
        if(files != null){


            FileWriter writer = null;
            for(File file : files){
                InputStreamReader inputStreamReader = null;
                BufferedReader bufReader = null;
                FileInputStream inputStream = null;
                try {

                    String temp = null;
                    File savePath = new File("d:/sougou_result.txt");
                    inputStream = new FileInputStream(file);
                    inputStreamReader = new InputStreamReader(inputStream,"gbk");
                    bufReader = new BufferedReader(inputStreamReader);
                    writer = new FileWriter(savePath, true);
                    String re = null;
                    while((temp = bufReader.readLine()) != null){

                        re = new String(temp.getBytes(), "UTF-8");
                        Matcher matcher = pattern.matcher(re);

                        if(matcher.matches()){

                           writer.write(matcher.group(1)+"\n");
                        }

                    }


                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                }catch(IOException e){
                    e.printStackTrace();
                }finally {
                    if(bufReader != null){
                        try {
                            bufReader.close();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                    if(inputStreamReader != null){
                        try {
                            inputStreamReader.close();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }
            if(writer != null){
                try {
                    writer.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public static void main(String [] args){

        try {
            ReadFileByFilePath.mergeAllTextToSingleText(ReadFileByFilePath.readFileToFileList("D:\\SogouCA"));
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
