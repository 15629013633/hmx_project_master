package com.hmx.lucene;
import java.io.*;
import java.util.Date;

import com.hmx.utils.result.ResultBean;
import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.document.DateTools;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.queryParser.ParseException;
import org.apache.lucene.queryParser.QueryParser;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.ScoreDoc;
import org.apache.lucene.search.TopDocs;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.SimpleFSDirectory;
import org.apache.lucene.util.Version;
import org.apache.pdfbox.pdfparser.PDFParser;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.util.PDFTextStripper;
import org.apache.poi.hwpf.extractor.WordExtractor;
import org.apache.poi.xwpf.extractor.XWPFWordExtractor;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by Administrator on 2019/4/27.
 */
@RestController
@RequestMapping("/content/search")
public class LuceneSearch {
    public ResultBean search(String str) throws IOException, ParseException {
        ResultBean resultBean = new ResultBean();
        //创建索引
        createIndex();
        // 保存索引文件的地方
        String indexDirectory = "test"+File.separator+"files"+File.separator+"default"+File.separator;
        //String indexDirectory = "https://zhonghuazhu.oss-cn-hangzhou.aliyuncs.com/test/files/targetIndex";
        // 创建Directory对象 ，也就是分词器对象
        Directory directory = new SimpleFSDirectory(new File(indexDirectory));
        // 创建 IndexSearcher对象，相比IndexWriter对象，这个参数就要提供一个索引的目录就行了
        IndexSearcher indexSearch = new IndexSearcher(directory);
        // 创建QueryParser对象,
        // 第1个参数表示Lucene的版本,
        // 第2个表示搜索Field的字段,
        // 第3个表示搜索使用分词器
        QueryParser queryParser = new QueryParser(Version.LUCENE_30,
                "contents", new StandardAnalyzer(Version.LUCENE_30));
        // 生成Query对象
        Query query = queryParser.parse(str);
        // 搜索结果 TopDocs里面有scoreDocs[]数组，里面保存着索引值
        TopDocs hits = indexSearch.search(query, 10);
        // hits.totalHits表示一共搜到多少个
        System.out.println("找到了" + hits.totalHits + "个");
        // 循环hits.scoreDocs数据，并使用indexSearch.doc方法把Document还原，再拿出对应的字段的值
        for (int i = 0; i < hits.scoreDocs.length; i++) {
            ScoreDoc sdoc = hits.scoreDocs[i];
            Document doc = indexSearch.doc(sdoc.doc);
            System.out.println(doc.get("filename"));
        }
        indexSearch.close();
        return resultBean;
    }

    public void createIndex() throws IOException {
        // 保存文件的路径
        //String dataDirectory = "E:\\fileTest\\sourcefile";
        String dataDirectory = "test"+File.separator+"files"+File.separator+"default"+File.separator;
        //String dataDirectory = "https://zhonghuazhu.oss-cn-hangzhou.aliyuncs.com/test/files/default";
        // 保存Lucene索引文件的路径
        String indexDirectory = "test"+File.separator+"files"+File.separator+"targetIndex"+File.separator;
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

    }
}
