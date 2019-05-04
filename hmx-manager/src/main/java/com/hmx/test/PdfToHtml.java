package com.hmx.test;


import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics2D;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.List;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import javax.imageio.IIOImage;
import javax.imageio.ImageIO;
import javax.imageio.ImageWriter;
import javax.imageio.stream.ImageOutputStream;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;

/**
 * 参见  https://blog.51cto.com/13479739/2285594
 * Created by Administrator on 2019/5/4.
 */
public class PdfToHtml {

    private static final String fileName = "bigfile_1.pdf";
    private static final String pdfDir = "E:\\fileTest\\pdfFile\\";
    private static final String htmlDir = "E:\\fileTest\\htmlFile";

    public static void main(String[] args) {
        //传入PDF地址
        //PdfToImage("E:\\Xshell\\40000003017146.pdf");

        pdfToHtml(pdfDir,htmlDir,fileName);
    }

    private static void pdfToHtml(String sourcePath, String outPath,String fileName){
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
        createPPTHtml(outPath, imgList, sourcePath,imageDir);
    }
    /**自己创建的html代码
     * 原理上就是，把上一步ppt转的图片
     * 以html的方式呈现出来
     */
    public static void createPPTHtml(String wordPath,List<String> imgList,String tempContextUrl,String imageDir){
        StringBuilder sb = new StringBuilder("<!doctype html><html><head><meta charset='utf-8'><title>无标题文档</title></head><body><div align=\"center\">");
        if (imgList != null && !imgList.isEmpty()) {
            for (String img : imgList) {
                sb.append("<img src='" + img + "' /><br>");
            }
        }
        sb.append("</div></body></html>");
        try {
            File file = new File(wordPath + File.separator + imageDir+".html");
            BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(file),"UTF-8"));
            bufferedWriter.write(sb.toString());
            bufferedWriter.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
