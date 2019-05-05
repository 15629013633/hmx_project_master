package com.hmx.test;
import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.net.MalformedURLException;
import java.net.URL;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.util.PDFTextStripper;

/**
 * Created by Administrator on 2019/5/4.
 */
public class PdfToTxt {
    private static final String pdfDir = "E:\\fileTest\\pdfFile\\";
    private static final String txtFileDir = "E:\\fileTest\\txtfile\\";
    public static void main(String[] args) {
        try {
            //获取pdf文件
//            String fileName = "bigfile_1.pdf";
//            readPdf(pdfDir,fileName);

            readPdf(pdfDir,"diskaal_aal74_12_72.pdf");
            readPdf(pdfDir,"diskaal_aal94_08_34.pdf");
            readPdf(pdfDir,"diskabl_abl32_07_33.pdf");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 传入一个.pdf文件
     * @param path
     * @throws Exception
     */
    public static void readPdf(String path,String fileName) throws Exception {
        // 是否排序
        boolean sort = false;
        // pdf文件名
        String pdfFilePath = path + fileName;
        // 输入文本文件名称
        String textFile = null;
        // 编码方式
        String encoding = "UTF-8";
        // 开始提取页数
        int startPage = 1;
        // 结束提取页数
        int endPage = Integer.MAX_VALUE;
        // 文件输入流，生成文本文件
        Writer output = null;
        // 内存中存储的PDF Document
        PDDocument document = null;
        try {
            try {
                // 首先当作一个URL来装载文件，如果得到异常再从本地文件系统//去装载文件
                URL url = new URL(pdfFilePath);
                //注意参数已不是以前版本中的URL.而是File。
                document = PDDocument.load(pdfFilePath);
                // 获取PDF的文件名
               // String fileName2 = url.getFile();
                // 以原来PDF的名称来命名新产生的txt文件
                if (fileName.length() > 4) {
                    File outputFile = new File(fileName.substring(0, fileName.length() - 4)+ ".txt");
                    textFile =txtFileDir+outputFile.getName();
                }
            } catch (MalformedURLException e) {
                // 如果作为URL装载得到异常则从文件系统装载
                //注意参数已不是以前版本中的URL.而是File。
                document = PDDocument.load(pdfFilePath);
                if (pdfFilePath.length() > 4) {
                    File outputFile = new File(fileName.substring(0, fileName.length() - 4)+ ".txt");
                    textFile =txtFileDir+outputFile.getName();
                }
            }
            // 文件输入流，写入文件倒textFile
            output = new OutputStreamWriter(new FileOutputStream(textFile),encoding);
            // PDFTextStripper来提取文本
            PDFTextStripper stripper = null;
            stripper = new PDFTextStripper();
            // 设置是否排序
            stripper.setSortByPosition(sort);
            // 设置起始页
            stripper.setStartPage(startPage);
            // 设置结束页
            stripper.setEndPage(endPage);
            // 调用PDFTextStripper的writeText提取并输出文本
            stripper.writeText(document, output);

            System.out.println(textFile + " 输出成功！");
        } finally {
            if (output != null) {
                // 关闭输出流
                output.close();
            }
            if (document != null) {
                // 关闭PDF Document
                document.close();
            }
        }
    }

}
