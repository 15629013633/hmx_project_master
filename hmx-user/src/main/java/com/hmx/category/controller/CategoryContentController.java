package com.hmx.category.controller;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.hmx.category.dto.HmxCategoryContentDto;
import com.hmx.category.service.HmxCategoryContentService;
import com.hmx.utils.result.Config;
import com.hmx.utils.result.PageBean;
import com.hmx.utils.result.ResultBean;

@RestController
@RequestMapping("/categoryContent")
public class CategoryContentController {
	
	@Autowired
	private HmxCategoryContentService hmxCategoryContentService;

	/**
	 * 查询内容详情
	 * @param categoryContentId
	 * @return
     */
	@GetMapping("/getContentById")
	public ResultBean getCategoryContentById(Integer categoryContentId){
		if(categoryContentId == null){
			return new ResultBean().setCode(Config.FAIL_FIELD_EMPTY).setContent("内容编号不能为空");
		}
		Map<String,Object> resultMap = hmxCategoryContentService.selectContentInfoByContentId(categoryContentId);
		if(resultMap == null){
			return new ResultBean().setCode(Config.FAIL_CODE).setContent("没有查找到内容详情信息");
		}
		return new ResultBean().setCode(Config.SUCCESS_CODE).put("categoryContentInfo", resultMap).setContent("获取内容详情成功");
	}
	
	/**
	 * 内容列表
	 * @param hmxCategoryContentDto
	 * @param page
	 * @param model
	 * @return
	 */
	@GetMapping("/contentTable")
	public ResultBean categoryContentTable(HmxCategoryContentDto hmxCategoryContentDto,PageBean<Map<String,Object>> page,Model model){
		page = hmxCategoryContentService.selectCategoryContentTableByPc(page, hmxCategoryContentDto);
		List<Map<String,Object>> list = page.getPage();
		if(list == null || list.size() <= 0){
			if(page.getPageNum() == 1){
				return new ResultBean().setCode(Config.CONTENT_NULL).setContent("暂无数据");
   	    	}
   	    	else{
   	    		return new ResultBean().setCode(Config.PAGE_NULL).setContent("没有更多数据了");
   	    	}
		}
		return new ResultBean().put("contentPage", page).setCode(Config.SUCCESS_CODE).setContent("查询内容列表成功");
	}
	/**
	 * 查询排行榜信息
	 * @param categoryId
	 * @return
	 */
	@GetMapping("/rankingContent")
	public ResultBean categoryContentRankingList(Integer categoryId){
		if(categoryId == null){
			return new ResultBean().setCode(Config.FAIL_FIELD_EMPTY).setContent("分类编号不能为空");
		}
		Map<String,Object> resultMap = hmxCategoryContentService.selectRankingListByCategoryId(categoryId);
		if(resultMap == null){
			return new ResultBean().setCode(Config.FAIL_CODE).setContent("没有查找到排行信息");
		}
		return new ResultBean().setCode(Config.SUCCESS_CODE).put("rankingContent", resultMap).setContent("获取排行信息成功");
	}

	/**
	 * 内容列表
	 * @param contentValue
	 * @param page
	 * @param model
	 * @return
	 */
	@GetMapping("/search")
	public ResultBean search(String contentValue, PageBean<Map<String,Object>> page, Model model){

		Map<String,Object> map = new HashMap<>();
		//从文本和标题中查  实际上目前只从标题中查了
		page = hmxCategoryContentService.search(page, contentValue);
		//从pdf中查询关键字
		try {
			slectStr(contentValue,page);
		}catch (Exception e){
			e.printStackTrace();
		}

		List<Map<String,Object>> list = page.getPage();
		List<Map<String,Object>> resultList = new ArrayList<Map<String,Object>>();

		if(null != list && list.size() > 0){
			for(Map<String,Object> map1 : list){
				String categoryContentId = map1.get("categoryContentId")+"";
				if(!StringUtils.isEmpty(categoryContentId)){
					Map<String,Object> resultMap = hmxCategoryContentService.selectContentInfoByContentId(Integer.valueOf(categoryContentId));
					resultList.add(resultMap);
				}
			}
		}
		if(null != page && page.getPage() != null){
			page.getPage().clear();
			page.setPage(resultList);
		}

//		map.put("rows", page.getPage());
//		map.put("total", page.getTotalNum());
		return new ResultBean().put("contentPage", page).setCode(Config.SUCCESS_CODE).setContent("查询内容成功");
//		return map;
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
					}else{
						page.getPage().add(map);
					}
					page.setTotalNum(page.getTotalNum()+1);
				}catch (Exception e){
					e.printStackTrace();
				}
			}
		}
		indexSearch.close();
		//=============测试end=======
	}
}
