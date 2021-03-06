package com.hmx.category.controller;

import com.hmx.aop.Operation;
import com.hmx.category.dto.HmxCategoryContentDto;
import com.hmx.category.dto.HmxCategoryDto;
import com.hmx.category.entity.HmxCategory;
import com.hmx.category.entity.HmxCategoryContent;
import com.hmx.category.entity.HmxCategoryContentTrans;
import com.hmx.category.service.HmxCategoryContentService;
import com.hmx.category.service.HmxCategoryService;
import com.hmx.files.dto.HmxFilesDto;
import com.hmx.images.dto.HmxImagesDto;
import com.hmx.model.TransModel;
import com.hmx.movie.dto.HmxMovieDto;
import com.hmx.utils.result.Config;
import com.hmx.utils.result.PageBean;
import com.hmx.utils.result.Result;
import com.hmx.utils.result.ResultBean;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.queryParser.QueryParser;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.ScoreDoc;
import org.apache.lucene.search.TopDocs;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.SimpleFSDirectory;
import org.apache.lucene.util.Version;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by songjinbao on 2019/4/25.
 */
@RestController
@RequestMapping("/category/categoryContent")
public class CategoryContentController {
    @Autowired
    private HmxCategoryContentService hmxCategoryContentService;

    @Autowired
    private HmxCategoryService hmxCategoryService;


    /**
     * 分类内容添加
     * 会添加的内容包含：文本信息，内容基本信息，
     *            文件：pdf文件信息，视频信息，图片信息
     * @return
     */
    @PostMapping(value = "/add",consumes = "application/json")
    @Operation("内容添加")
    public ResultBean categoryAdd(@RequestBody TransModel transModel){
        ResultBean resultBean = new ResultBean();
        resultBean.put("content", "");
        boolean flag=true;
        try {
            HmxCategoryContentDto hmxCategoryContentDto = transModel.getContent();
            List<HmxMovieDto> hmxMovieDtoList = transModel.getMovieList();
            List<HmxImagesDto> hmxImagesDtoList = transModel.getImagesList();
            List<HmxFilesDto> hmxFilesDtoList = transModel.getFilesList();
            if(StringUtils.isEmpty(hmxCategoryContentDto.getCategoryTitle())){
                resultBean.setCode(Config.FAIL_FIELD_EMPTY).setContent("内容标题不能为空");
                flag=false;
            }
//            if(StringUtils.isEmpty(hmxCategoryContentDto.getCategoryContent())){
//                resultBean.setCode(Config.FAIL_FIELD_EMPTY).setContent("内容不能为空");
//                flag=false;
//            }
            if(hmxCategoryContentDto.getCategoryId() == null){
                resultBean.setCode(Config.FAIL_FIELD_EMPTY).setContent("关联分类不能为空");
                flag=false;
            }
//            if(0 == hmxCategoryContentDto.getMode()){
//                resultBean.setCode(Config.FAIL_FIELD_EMPTY).setContent("内容必须有一种展现方式");
//                flag=false;
//            }
            if(StringUtils.isEmpty(hmxCategoryContentDto.getContentFlow())){
                resultBean.setCode(Config.FAIL_FIELD_EMPTY).setContent("内容流水号不能为空");
                flag=false;
            }
//            printValues(hmxMovieDtoList,hmxImagesDtoList,hmxFilesDtoList,"add");
            if(flag){
                Map<String,Object> resultMap = hmxCategoryContentService.categoryContentAdd(hmxCategoryContentDto,hmxMovieDtoList,hmxImagesDtoList,hmxFilesDtoList);
                flag=Boolean.parseBoolean(resultMap.get("flag").toString());
                if(!flag){
                    resultBean.setCode(Config.FAIL_CODE);
                }else{
                    resultBean.setCode(Config.SUCCESS_CODE);
                }
                resultBean.setContent(resultMap.get("content").toString());
            }
        }catch (Exception e){
            resultBean.setCode(Config.FAIL_CODE);
            resultBean.setContent("操作失败");
            e.printStackTrace();
        }

        return resultBean;
    }

    /**
     * 分类内容编辑
     * @return
     */
    @PostMapping(value = "/edit",consumes = "application/json")
    @Operation("内容修改")
    public ResultBean categoryUpdate(@RequestBody TransModel transModel){
        ResultBean resultBean = new ResultBean();
        resultBean.put("content", "");
        boolean flag=true;
        HmxCategoryContentDto hmxCategoryContentDto = transModel.getContent();
        List<HmxMovieDto> hmxMovieDtoList = transModel.getMovieList();
        List<HmxImagesDto> hmxImagesDtoList = transModel.getImagesList();
        List<HmxFilesDto> hmxFilesDtoList = transModel.getFilesList();
        if(flag){
            Map<String,Object> resultMap = null;
            resultMap = hmxCategoryContentService.categoryContentUpdate(hmxCategoryContentDto,hmxMovieDtoList,hmxImagesDtoList,hmxFilesDtoList);

            flag=Boolean.parseBoolean(resultMap.get("flag").toString());
            if(!flag){
                resultBean.setCode(Config.FAIL_CODE).setContent("编辑失败");
                //result.setStatus(20000);
                //result.setMsg("失败");
                return resultBean;
            }else{
                resultBean.setCode(Config.SUCCESS_CODE).setContent("编辑成功");
                return resultBean;
            }
        }
        return resultBean;
    }

    /**
     * 分类内容删除
     * @return
     */
    @PostMapping(value = "/deleteContent")
    @Operation("内容删除")
    public ResultBean delete(String ids){
        ResultBean resultBean = new ResultBean();
        resultBean.put("content", "");
        boolean flag=true;
        if(StringUtils.isEmpty(ids)){
            resultBean.setCode(Config.FAIL_FIELD_EMPTY).setContent("内容主键不能为空");
            flag=false;
        }
        if(flag){
            flag = hmxCategoryContentService.deleteByIdArray(ids);
            if(!flag){
                resultBean.setCode(Config.FAIL_CODE).setContent("删除失败");
                return resultBean;
            }else{
                resultBean.setCode(Config.SUCCESS_CODE).setContent("删除成功");
                return resultBean;
            }
        }
        return resultBean;
    }
    /**
     * 内容详情查询
     * @param categoryContentId
     * @param model
     * @return
     */
    @GetMapping("/getCategoryContentById")
    @Operation("内容详情查询")
    public ResultBean categoryContentInfo(Integer categoryContentId, Model model){
        ResultBean resultBean = new ResultBean();
        resultBean.put("content", "");
        boolean flag=true;
        if(categoryContentId == null){
            resultBean.setCode(Config.FAIL_FIELD_EMPTY).setContent("内容编号不能为空");
            flag=false;
        }
        if(flag){
            HmxCategoryContentTrans resultMap = hmxCategoryContentService.selectCategoryContentById(categoryContentId);
            if(resultMap == null){
                resultBean.setCode(Config.FAIL_CODE).setContent("查询内容详情失败");
            }else{
                resultBean.setCode(Config.SUCCESS_CODE).setContent("查询内容详情成功");
            }
            resultBean.put("content", resultMap);
        }
        model.addAttribute("resultBean", resultBean);
        return resultBean;
    }

    /**
     * 内容列表
     * @param hmxCategoryContentDto
     * @param page
     * @param model
     * @return
     */
    @GetMapping("/categoryContentTable")
    @Operation("获取内容列表")
    public ResultBean categoryContentTable(HmxCategoryContentDto hmxCategoryContentDto, PageBean<Map<String,Object>> page, Model model){
//        Map<String,Object> map = new HashMap<>();
        ResultBean resultBean = new ResultBean();
        resultBean.put("content","");
        page = hmxCategoryContentService.selectCategoryContentTable(page, hmxCategoryContentDto);
        List<Map<String,Object>> list = page.getPage();
        if(list == null || list.size() <= 0){
            if(page.getPageNum() == 1){
                resultBean.setCode(Config.CONTENT_NULL).setContent("暂无数据");
            }
            else{
                resultBean.setCode(Config.PAGE_NULL).setContent("没有更多数据了");
            }
        }else{
            resultBean.setCode(Config.SUCCESS_CODE).setContent("查询内容列表成功");
        }
        return new ResultBean().put("content", page);
    }

    public void printValues(List<HmxMovieDto> hmxMovieDtoList, List<HmxImagesDto> hmxImagesDtoList,List<HmxFilesDto> hmxFilesDtoList,String type){
        for(HmxMovieDto hmxMovieDto : hmxMovieDtoList){
            System.out.print(type+ "视频信息title:" + hmxMovieDto.getMovieName()+",videoId:" + hmxMovieDto.getVideoId()+",serie=" + hmxMovieDto.getSerie());
        }
        for(HmxImagesDto hmxImagesDto : hmxImagesDtoList){
            System.out.print(type+ "图片信息：" + hmxImagesDto.getImageUrl());
        }
        for(HmxFilesDto hmxFilesDto : hmxFilesDtoList){
            System.out.print(type+ "文件信息：" + hmxFilesDto.getFileUrl());
        }
    }

    /**
     * 内容列表
     * @param contentValue
     * @param page
     * @param model
     * @return
     */
    @GetMapping("/search")
    @Operation("内容搜索")
    public Map<String,Object> search(String contentValue, PageBean<Map<String,Object>> page, Model model){

        Map<String,Object> map = new HashMap<>();
        ResultBean resultBean = new ResultBean();
        //从文本和标题中查  实际上目前只从标题中查了
        page = hmxCategoryContentService.search(page, contentValue);
        //从pdf中查询关键字
        try {
            slectStr(contentValue,page);
        }catch (Exception e){
            e.printStackTrace();
        }

        List<Map<String,Object>> list = page.getPage();
        if(list == null || list.size() <= 0){
            if(page.getPageNum() == 1){
                resultBean.setCode(Config.CONTENT_NULL).setContent("暂无数据");
            }
            else{
                resultBean.setCode(Config.PAGE_NULL).setContent("没有更多数据了");
            }
        }else{
            resultBean.setCode(Config.SUCCESS_CODE).setContent("查询内容列表成功");
        }
        map.put("rows", page.getPage());
        map.put("total", page.getTotalNum());
        return map;
    }

    public void slectStr(String contentValue,PageBean<Map<String,Object>> page) throws Exception{
        if(page == null ){
            page = new PageBean<Map<String,Object>>();
        }
        // 保存索引文件的地方
        String indexDirectory = "/home/back/targetIndex";
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
        Query query = queryParser.parse(contentValue);
        // 搜索结果 TopDocs里面有scoreDocs[]数组，里面保存着索引值
        TopDocs hits = indexSearch.search(query, 10);
        // hits.totalHits表示一共搜到多少个
        System.out.println("找到了" + hits.totalHits + "个");
        // 循环hits.scoreDocs数据，并使用indexSearch.doc方法把Document还原，再拿出对应的字段的值
        for (int i = 0; i < hits.scoreDocs.length; i++) {
            ScoreDoc sdoc = hits.scoreDocs[i];
            Document doc = indexSearch.doc(sdoc.doc);
            System.out.println(doc.get("filename"));
            String filename = doc.get("filename");
            if(!StringUtils.isEmpty(filename)){
                try {
                    String[] strArr = filename.split("_");
                    String contentId = strArr[3];
                    contentId = contentId.substring(0,contentId.length()-4);
                    Map<String,Object> map = hmxCategoryContentService.queryContentById(Integer.valueOf(contentId));
                    if(null == page.getPage() || page.getPage().size() < 1){
                        List<Map<String,Object>> list = new ArrayList<Map<String,Object>>();
                        list.add(map);
                        page.setPage(list);
                    }
                    page.getPage().add(map);
                    page.setTotalNum(page.getTotalNum()+1);
                }catch (Exception e){
                    e.printStackTrace();
                }
            }
        }
        indexSearch.close();
        //=============测试end=======
    }

    /**
     * 获取每种类型的内容最多浏览量的数据
     * @param categoryType
     * @param model
     * @return
     */
    @GetMapping("/getContentRankNum")
    @Operation("获取内容浏览量")
    public ResultBean getContentRankNum(Integer categoryType, Model model){
        ResultBean resultBean = new ResultBean();
        List<Map<String,Object>> mapList = hmxCategoryContentService.getContentRankNum(categoryType);
        resultBean.put("content",mapList);
        if(null != mapList && mapList.size() > 0){
            resultBean.setCode(Config.SUCCESS_CODE).setContent("获取到内容浏览量");
        }else {
            resultBean.setCode(Config.SUCCESS_CODE).setContent("未获取到内容浏览量");
        }
        return resultBean;
    }

    /**
     * 置顶接口
     * @return
     */
    @PostMapping(value = "/setTop")
    @Operation("内容置顶")
    public ResultBean setTop(Integer contentId,Integer categoryId){
        ResultBean resultBean = new ResultBean();
        resultBean.put("content", "");
        boolean flag=true;
        if(0 == contentId ){
            resultBean.setCode(Config.FAIL_CODE).setContent("置顶失败，内容id不能为空");
            flag = false;
        }
        if(0 == categoryId ){
            resultBean.setCode(Config.FAIL_CODE).setContent("置顶失败，分类id不能为空");
            flag = false;
        }
        if(flag){
            Map<String,Object> resultMap = null;
            resultMap = hmxCategoryContentService.setTop(contentId,categoryId);

            flag=Boolean.parseBoolean(resultMap.get("flag").toString());
            if(!flag){
                resultBean.setCode(Config.FAIL_CODE).setContent("置顶设置失败");
                return resultBean;
            }else{
                resultBean.setCode(Config.SUCCESS_CODE).setContent("置顶设置成功");
                return resultBean;
            }
        }
        return resultBean;
    }

    /**
     * 上移下移接口
     * type 1 为上移
     * type 2 为下移
     * @return
     */
    @PostMapping(value = "/upAndDown")
    @Operation("内容修改")
    public ResultBean upAndDown(Integer contentId,Integer categoryId,Integer sort,Integer type){
        ResultBean resultBean = new ResultBean();
        resultBean.put("content", "");
        boolean flag=true;
        if(contentId == 0 || categoryId == 0 || sort == 0 || type == 0){
            resultBean.setCode(Config.FAIL_CODE).setContent("参数为空校验不通过");
            flag = false;
        }
        if(flag){
            Map<String,Object> resultMap = hmxCategoryContentService.upAndDown(contentId,categoryId,sort,type);

            flag=Boolean.parseBoolean(resultMap.get("flag").toString());
            if(!flag){
                resultBean.setCode(Config.FAIL_CODE).setContent("排序失败");
                return resultBean;
            }else{
                resultBean.setCode(Config.SUCCESS_CODE).setContent("排序成功");
                return resultBean;
            }
        }
        return resultBean;
    }

    /**
     * 内容上架
     * @return
     */
    @PostMapping(value = "/publish")
    @Operation("内容置顶")
    public ResultBean publish(String ids){
        ResultBean resultBean = new ResultBean();
        resultBean.put("content", "");
        boolean flag=true;
        if(StringUtils.isEmpty(ids) ){
            resultBean.setCode(Config.FAIL_CODE).setContent("内容id不能为空");
            flag = false;
        }
        if(flag){
            Map<String,Object> resultMap = null;
            resultMap = hmxCategoryContentService.publish(ids);

            flag=Boolean.parseBoolean(resultMap.get("flag").toString());
            if(!flag){
                resultBean.setCode(Config.FAIL_CODE).setContent("上架失败");
                return resultBean;
            }else{
                resultBean.setCode(Config.SUCCESS_CODE).setContent("上架成功");
                return resultBean;
            }
        }
        return resultBean;
    }

    /**
     * 内容下架接口
     * @return
     */
    @PostMapping(value = "/unPublish")
    @Operation("内容下架")
    public ResultBean unPublish(String ids){
        ResultBean resultBean = new ResultBean();
        resultBean.put("content", "");
        boolean flag=true;
        if(StringUtils.isEmpty(ids) ){
            resultBean.setCode(Config.FAIL_CODE).setContent("内容id不能为空");
            flag = false;
        }
        if(flag){
            Map<String,Object> resultMap = null;
            resultMap = hmxCategoryContentService.unPublish(ids);

            flag=Boolean.parseBoolean(resultMap.get("flag").toString());
            if(!flag){
                resultBean.setCode(Config.FAIL_CODE).setContent("下架失败");
                return resultBean;
            }else{
                resultBean.setCode(Config.SUCCESS_CODE).setContent("下架成功");
                return resultBean;
            }
        }
        return resultBean;
    }

    public static  void  main(String[] arg0){
        String contentId = "34.pdf";
        contentId = contentId.substring(0,contentId.length()-4);
        System.out.print("contentId=" + contentId);
    }
}
