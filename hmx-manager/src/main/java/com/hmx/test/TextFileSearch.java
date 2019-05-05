package com.hmx.test;

import com.alibaba.druid.util.StringUtils;

import java.io.Closeable;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.LineNumberReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 从txt文件中搜索关键字
 * Created by Administrator on 2019/5/4.
 */
public class TextFileSearch {

    private static final String txtFileDir = "E:\\fileTest\\txtfile";

    public static void main(String[] args) {

        TextFileSearch search = new TextFileSearch();
        File file = new File(txtFileDir);
        List<Map<String,String>> mapList = new ArrayList<>();
        if(null != file){
            String[] filelist = file.list();
            if(null != filelist && filelist.length > 0){
                for(String fileName : filelist){
                    String result = search.SearchKeyword(new File(txtFileDir + File.separator + fileName), "习惯上");
                    if(!StringUtils.isEmpty(result)){
                        Map<String,String> map = new HashMap<>();
                        map.put(fileName,result);
                        mapList.add(map);
                    }
                }
            }
        }
        if(mapList.size() > 0){
            for(Map<String,String> map : mapList){
                for(Map.Entry<String,String> entry : map.entrySet()){
                    System.out.println("文件名：" + entry.getKey() + ",查到的文件内容：" + entry.getValue());
                }
            }
        }
        //获取E:\fileTest\txtfile目录下的文件列表
        //search.SearchKeyword(new File("E:\\fileTest\\pdfToTxt\\大文件测试.txt"), "京人民");
    }

    public String SearchKeyword(File file,String keyword) {
        String result = "";
        //参数校验
        verifyParam(file, keyword);

        //行读取
        LineNumberReader lineReader = null;
        try {
            lineReader = new LineNumberReader(new FileReader(file));
            String readLine = null;
            String lastLine = "";//关键字的前一句
            String currnLine = "";//关键字的当前句
            String nextLine = "";//关键字的下一句
            int lineNum = 0;
            while((readLine =lineReader.readLine()) != null){
                //判断每一行中,出现关键词的次数
                int index = 0;
                int next = 0;
                int times = 0;//出现的次数
                //判断次数
                while((index = readLine.indexOf(keyword,next)) != -1) {
                    next = index + keyword.length();
                    times++;
                    lineNum = lineReader.getLineNumber();
                }
                if(times > 0) {
                   System.out.println("第"+ lineReader.getLineNumber() +"行" + "出现 "+keyword+" 次数: "+times);
                   // System.out.println("lastLine是：" + lastLine);
                    currnLine = readLine;
                    //System.out.println("内容是：" + lastLine + readLine);
                }else if(lineNum == 0) {
                    lastLine = readLine;
                }
                if((lineNum + 1) == lineReader.getLineNumber() && (!StringUtils.isEmpty(currnLine))){
                    nextLine = readLine;
                    System.out.println("完整内容是：" + lastLine + currnLine + nextLine);
                    lineNum = 0;
                    //只返回第一次出现的内容
                    result = lastLine + currnLine + nextLine;
                    return result;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            //关闭流
            close(lineReader);
        }
        return result;
    }

    /**
     * 参数校验
     *
     * <br>
     * Date: 2014年11月5日
     */
    private void verifyParam(File file, String keyword) {
        //对参数进行校验证
        if(file == null ){
            throw new NullPointerException("the file is null");
        }
        if(keyword == null || keyword.trim().equals("")){
            throw new NullPointerException("the keyword is null or \"\" ");
        }

        if(!file.exists()) {
            throw new RuntimeException("the file is not exists");
        }
        //非目录
        if(file.isDirectory()){
            throw new RuntimeException("the file is a directory,not a file");
        }

        //可读取
        if(!file.canRead()) {
            throw new RuntimeException("the file can't read");
        }
    }

    /**
     * 关闭流
     * <br>
     * Date: 2014年11月5日
     */
    private void close(Closeable able){
        if(able != null){
            try {
                able.close();
            } catch (IOException e) {
                e.printStackTrace();
                able = null;
            }
        }
    }
}
