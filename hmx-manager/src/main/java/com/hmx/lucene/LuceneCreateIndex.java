package com.hmx.lucene;

import java.awt.image.BufferedImage;
import java.io.*;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import com.hmx.utils.result.Config;
import com.hmx.utils.result.ResultBean;
import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.document.DateTools;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.SimpleFSDirectory;
import org.apache.lucene.util.Version;
import org.apache.pdfbox.pdfparser.PDFParser;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.util.PDFTextStripper;
import org.apache.poi.hwpf.extractor.WordExtractor;
import org.apache.poi.xwpf.extractor.XWPFWordExtractor;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.imageio.IIOImage;
import javax.imageio.ImageIO;
import javax.imageio.ImageWriter;
import javax.imageio.stream.ImageOutputStream;

/**
 * Created by Administrator on 2019/4/27.
 */
@RestController
@RequestMapping("/lucene1/createIndex")
public class LuceneCreateIndex {

    private static final String fileName = "bigfile_1.pdf";
    private static final String pdfDir = "E:\\fileTest\\pdfFile\\";
    private static final String htmlDir = "E:\\fileTest\\htmlFile";

    private static final String txtPdfDir = "E:\\fileTest\\pdfFile\\";
    private static final String txtFileDir = "E:\\fileTest\\txtfile\\";

    /**
     * @throws IOException
     */
    @GetMapping(value = "/pdfToHtml")
    public ResultBean createHtml() throws IOException {
        ResultBean resultBean = new ResultBean();
        pdfToHtml(pdfDir,htmlDir,fileName);
        return resultBean;
    }

    /**
     * @throws IOException
     */
    @GetMapping(value = "/pdfToTxt")
    public ResultBean createTxt() throws Exception {
        ResultBean resultBean = new ResultBean();
        pdfToTxt(txtPdfDir,fileName);
        return resultBean;
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

    /**
     * 传入一个.pdf文件
     * @param path
     * @throws Exception
     */
    public static void pdfToTxt(String path,String fileName) throws Exception {
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

    /**
     * @throws IOException
     */
    @GetMapping(value = "/createIndex")
    public ResultBean createIndex() throws IOException {
        ResultBean resultBean = new ResultBean();
        try {
            String dataDirectory = "/home/back/sourcefile";
            //String dataDirectory = "https://zhonghuazhu.oss-cn-hangzhou.aliyuncs.com/test/files/default";
            // 保存Lucene索引文件的路径
            String indexDirectory = "/home/back/targetIndex";
            // String indexDirectory = "https://zhonghuazhu.oss-cn-hangzhou.aliyuncs.com/test/files/targetIndex";
            // 创建Directory对象 ，也就是分词器对象
            Directory directory = new SimpleFSDirectory(new File(indexDirectory));
            // 创建一个简单的分词器,可以对数据进行分词
            Analyzer analyzer = new StandardAnalyzer(Version.LUCENE_30);

            // 创建索引实例
            // 第1个参数是Directory,
            // 第2个是分词器,
            // 第3个表示是否是创建, true代表覆盖原先数据, 如果为false为在此基础上面修改,
            // 第4个MaxFieldLength表示对每个Field限制建立分词索引的最大数目，
            // 如果是MaxFieldLength.UNLIMITED，表示长度没有限制;
            // 如果是MaxFieldLength.LIMITED则表示有限制，可以通过IndexWriter对象的setMaxFieldLength（int
            // n）进行指定
            IndexWriter indexWriter = new IndexWriter(directory, analyzer, true,
                    IndexWriter.MaxFieldLength.UNLIMITED);
            // 获取所有需要建立索引的文件
            File[] files = new File(dataDirectory).listFiles();

            for (int i = 0; i < files.length; i++) {
                // 文件是第几个
                System.out.println("这是第" + i + "个文件----------------");
                // 文件的完整路径
                System.out.println("完整路径：" + files[i].toString());
                // 获取文件名称
                String fileName = files[i].getName();
                // 获取文件后缀名，将其作为文件类型
                String fileType = fileName.substring(fileName.lastIndexOf(".") + 1,
                        fileName.length()).toLowerCase();
                // 文件名称
                System.out.println("文件名称：" + fileName);
                // 文件类型
                System.out.println("文件类型：" + fileType);

                Document doc = new Document();

                // String fileCode = FileType.getFileType(files[i].toString());
                // 查看各个文件的文件头标记的类型
                // System.out.println("fileCode=" + fileCode);

                InputStream in = new FileInputStream(files[i]);
                InputStreamReader reader = null;

                if (fileType != null && !fileType.equals("")) {

                    if (fileType.equals("doc")) {
                        // 获取doc的word文档
                        WordExtractor wordExtractor = new WordExtractor(in);
                        // 创建Field对象，并放入doc对象中
                        // Field的各个字段含义如下：
                        // 第1个参数是设置field的name，
                        // 第2个参数是value，value值可以是文本（String类型，Reader类型或者是预分享的TokenStream）,
                        // 二进制（byet[]）, 或者是数字（一个 Number类型）
                        // 第3个参数是Field.Store，选择是否存储，如果存储的话在检索的时候可以返回值
                        // 第4个参数是Field.Index，用来设置索引方式
                        doc.add(new Field("contents", wordExtractor.getText(),
                                Field.Store.YES, Field.Index.ANALYZED));
                        // 关闭文档
                        wordExtractor.close();
                        System.out.println("注意：已为文件“" + fileName + "”创建了索引");

                    } else if (fileType.equals("docx")) {
                        // 获取docx的word文档
                        XWPFWordExtractor xwpfWordExtractor = new XWPFWordExtractor(
                                new XWPFDocument(in));
                        // 创建Field对象，并放入doc对象中
                        doc.add(new Field("contents", xwpfWordExtractor.getText(),
                                Field.Store.YES, Field.Index.ANALYZED));
                        // 关闭文档
                        xwpfWordExtractor.close();
                        System.out.println("注意：已为文件“" + fileName + "”创建了索引");

                    } else if (fileType.equals("pdf")) {
                        // 获取pdf文档
                        PDFParser parser = new PDFParser(in);
                        parser.parse();
                        PDDocument pdDocument = parser.getPDDocument();
                        PDFTextStripper stripper = new PDFTextStripper();
                        // 创建Field对象，并放入doc对象中
                        doc.add(new Field("contents", stripper.getText(pdDocument),
                                Field.Store.NO, Field.Index.ANALYZED));
                        // 关闭文档
                        pdDocument.close();
                        System.out.println("注意：已为文件“" + fileName + "”创建了索引");

                    } else if (fileType.equals("txt")) {
                        // 建立一个输入流对象reader
                        reader = new InputStreamReader(in);
                        // 建立一个对象，它把文件内容转成计算机能读懂的语言
                        BufferedReader br = new BufferedReader(reader);
                        String txtFile = "";
                        String line = null;

                        while ((line = br.readLine()) != null) {
                            // 一次读入一行数据
                            txtFile += line;
                        }
                        // 创建Field对象，并放入doc对象中
                        doc.add(new Field("contents", txtFile, Field.Store.NO,
                                Field.Index.ANALYZED));
                        System.out.println("注意：已为文件“" + fileName + "”创建了索引");

                    } else {

                        System.out.println();
                        continue;

                    }

                }
                // 创建文件名的域，并放入doc对象中
                doc.add(new Field("filename", files[i].getName(), Field.Store.YES,
                        Field.Index.NOT_ANALYZED));
                // 创建时间的域，并放入doc对象中
                doc.add(new Field("indexDate", DateTools.dateToString(new Date(),
                        DateTools.Resolution.DAY), Field.Store.YES,
                        Field.Index.NOT_ANALYZED));
                // 写入IndexWriter
                indexWriter.addDocument(doc);
                // 换行
                System.out.println();
            }
            // 查看IndexWriter里面有多少个索引
            System.out.println("numDocs=" + indexWriter.numDocs());
            // 关闭索引
            indexWriter.close();
            resultBean.setCode(Config.SUCCESS_CODE);
        }catch (Exception e){
            resultBean.setCode(Config.FAIL_CODE);
            e.printStackTrace();
        }
        return resultBean;
    }
}
