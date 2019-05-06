package com.hmx.utils.oss.upload;

import java.awt.image.BufferedImage;
import java.io.*;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.util.PDFTextStripper;

import javax.imageio.IIOImage;
import javax.imageio.ImageIO;
import javax.imageio.ImageWriter;
import javax.imageio.stream.ImageOutputStream;

/**
 * Created by songjinbao on 2019/5/6.
 */
public class FileUtil {


    public static String pdfToHtml(String sourcePath, String outPath,String fileName,String sourceFileName){
        List<String> imgList = new ArrayList<String>();
        String imageDir = fileName.substring(0, fileName.length() - 4);
        try {

            sourcePath = sourcePath + fileName;
            PDDocument doc = PDDocument.load(sourcePath);
            int pageCount = doc.getPageCount();
            System.out.println("总共多少页？" + pageCount);
            List pages = doc.getDocumentCatalog().getAllPages();
            for(int i=0;i<pages.size();i++){
                PDPage page = (PDPage)pages.get(i);
                BufferedImage image = page.convertToImage();
                Iterator iter = ImageIO.getImageWritersBySuffix("jpg");
                ImageWriter writer = (ImageWriter)iter.next();
                String imgName = File.separator + imageDir + "_img" + File.separator +i+".jpg";
                File folder = new File(outPath + File.separator + imageDir + "_img");  //先创建文件夹
                folder.mkdirs();
                File outFile = new File(outPath + imgName); //再创建文件
                imgList.add(imageDir + "_img" + File.separator +i+".jpg");
                outFile.createNewFile();
                FileOutputStream out = new FileOutputStream(outFile);
                ImageOutputStream outImage = ImageIO.createImageOutputStream(out);
                writer.setOutput(outImage);
                writer.write(new IIOImage(image,null,null));
            }
            doc.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return createPPTHtml(outPath, imgList, sourcePath,imageDir,sourceFileName);
    }
    /**自己创建的html代码
     * 原理上就是，把上一步ppt转的图片
     * 以html的方式呈现出来
     */
    public static String createPPTHtml(String wordPath,List<String> imgList,String tempContextUrl,String imageDir,String sourceFileName){
        StringBuilder sb = new StringBuilder("<!doctype html><html><head><meta charset='utf-8'><title>"+sourceFileName+"</title></head><body><div align=\"center\">");
        if (imgList != null && !imgList.isEmpty()) {
            for (String img : imgList) {
                sb.append("<img src='" + img + "' /><br>");
            }
        }
        sb.append("</div></body></html>");
        try {
            String htmlPath = wordPath + File.separator + imageDir+".html";
            System.out.println("htmlPath:" + htmlPath);
            File file = new File(htmlPath);
            BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(file),"UTF-8"));
            bufferedWriter.write(sb.toString());
            bufferedWriter.close();
            return htmlPath;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return "";
    }

    /**
     * 传入一个.pdf文件
     * @param path
     * @throws Exception
     */
    public static void pdfToTxt(String path,String txtFileDir,String fileName) throws Exception {
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
