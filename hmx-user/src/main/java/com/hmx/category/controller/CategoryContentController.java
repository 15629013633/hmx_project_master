package com.hmx.category.controller;

import java.io.*;
import java.util.*;

import com.hmx.aop.Operation;
import com.hmx.base.controller.BaseController;
import com.hmx.system.entity.SearchModel;
import com.hmx.user.entity.HmxUser;
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

import javax.servlet.http.HttpSession;

@RestController
@RequestMapping("/categoryContent")
public class CategoryContentController extends BaseController {

	//window下
	//private static final String txtFileDir = "E:\\fileTest\\txtFile";
	//linux下--测试环境
//	private static final String txtFileDir = "/home/back/txtFile";
	//linux下--生产环境
	private static final String txtFileDir = "/home/hmx/txtFile";

	@Autowired
	private HmxCategoryContentService hmxCategoryContentService;

	/**
	 * 查询内容详情
	 * @param categoryContentId
	 * @return
     */
	@GetMapping("/getContentById")
	@Operation("获取内容详情")
	public ResultBean getCategoryContentById(Integer categoryContentId){
//		HmxUser hmxUser = new HmxUser();
//		hmxUser.setUserName("songjinbao");
//		hmxUser.setUserPhone("13076949806");
//		setAccount(hmxUser);
		if(categoryContentId == null){
			return new ResultBean().setCode(Config.FAIL_FIELD_EMPTY).setContent("内容编号不能为空");
		}
		Map<String,Object> resultMap = hmxCategoryContentService.selectContentInfoByContentId(categoryContentId,"pc");
		if(resultMap == null){
			return new ResultBean().setCode(Config.FAIL_CODE).put("content", resultMap).setContent("没有查找到内容详情信息");
		}
		return new ResultBean().setCode(Config.SUCCESS_CODE).put("content", resultMap).setContent("获取内容详情成功");
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
		page = hmxCategoryContentService.selectCategoryContentTableByPc(page, hmxCategoryContentDto,"pc");
		List<Map<String,Object>> list = page.getPage();
		if(list == null || list.size() <= 0){
			if(page.getPageNum() == 1){
				return new ResultBean().put("content", page).setCode(Config.SUCCESS_CODE).setContent("暂无数据");
   	    	}
   	    	else{
   	    		return new ResultBean().setCode(Config.SUCCESS_CODE).put("content", page).setContent("没有更多数据了");
   	    	}
		}
		return new ResultBean().put("content", page).setCode(Config.SUCCESS_CODE).setContent("查询内容列表成功");
	}
	/**
	 * 查询排行榜信息
	 * @return
	 */
	@GetMapping("/rankingContent")
	public ResultBean categoryContentRankingList(){
//		if(categoryId == null){
//			return new ResultBean().setCode(Config.FAIL_FIELD_EMPTY).setContent("分类编号不能为空");
//		}
		List<Map<String,Object>> list = hmxCategoryContentService.selectRankingListByCategoryId();
		if(list == null){
			return new ResultBean().setCode(Config.SUCCESS_CODE).put("rankingContent", list).setContent("没有查找到排行信息");
		}
		return new ResultBean().setCode(Config.SUCCESS_CODE).put("rankingContent", list).setContent("获取排行信息成功");
	}

	/**
	 * 搜索
	 * @param contentValue
	 * @param page
	 * @param model
	 * @return
	 */
	@GetMapping("/search")
	public ResultBean search(String contentValue, PageBean<Map<String,Object>> page, Model model){
		int pageNum = page.getPageNum();
		if(StringUtils.isEmpty(contentValue)){
			return new ResultBean().setCode(Config.FAIL_FIELD_EMPTY).setContent("搜索内容字段不能为空");
		}
		Map<String,Object> map = new HashMap<>();
		//从文本和标题中查
		page = hmxCategoryContentService.search(page, contentValue);
		//从pdf生成的txt中查询关键字
		try {
			slectStr(contentValue,page);
		}catch (Exception e){
			e.printStackTrace();
		}

		List<Map<String,Object>> list = page.getPage();
		List<Map<String,Object>> resultList = new ArrayList<Map<String,Object>>();
		Set<String> set = new HashSet();

		if(null != list && list.size() > 0){
			for(Map<String,Object> map1 : list){
				String categoryContentId = map1.get("categoryContentId")+"";
				if(!StringUtils.isEmpty(categoryContentId)){
					Map<String,Object> resultMap = hmxCategoryContentService.selectContentInfoByContentId(Integer.valueOf(categoryContentId),"pc");
					resultMap.put("categoryContent",map1.get("categoryContent"));
					if(set.add(resultMap.get("categoryContentId")+"")){//去重
						resultList.add(resultMap);
					}
				}
			}
		}
		if(null != page && page.getPage() != null){
//			page.getPage().clear();
//			page.setPage(resultList);
//			page.setPageNum(1);
//			page.setPageSize(10);
//			page.setTotalNum(resultList.size());

			page.getPage().clear();
			int startIndex = (pageNum - 1) * page.getPageSize();
			int endIndex = startIndex + page.getPageSize();

			if(endIndex >= resultList.size()){
				endIndex = resultList.size();
			}

			if(startIndex >= endIndex){
				page.setPage(new ArrayList<Map<String,Object>>());
			}else {
				page.setPage(resultList.subList(startIndex,endIndex));
			}
		}
		return new ResultBean().put("contentPage", page).setCode(Config.SUCCESS_CODE).setContent("查询内容成功");
	}



	/**
	 * 高级搜索
	 * @param searchModel
	 * @param page
	 * @param model
	 * @return
	 */
	@GetMapping("/seniorSearch")
	public ResultBean seniorSearch(SearchModel searchModel, PageBean<Map<String,Object>> page, Model model){
		int pageNum = page.getPageNum();
		Map<String,Object> map = new HashMap<>();
		if((null == searchModel.getCategoryId() || 0 == searchModel.getCategoryId())
				&& (null == searchModel.getParentCategoryId() || 0 == searchModel.getParentCategoryId())){
			return new ResultBean().setCode(Config.FAIL_FIELD_EMPTY).setContent("分类id不能为空");
		}
		//从文本和标题中查  实际上目前只从标题中查了
		page = hmxCategoryContentService.seniorSearch(page, searchModel);
		//从pdf中查询关键字
		try {
			if(!StringUtils.isEmpty(searchModel.getCategoryTitle())){
				slectStr(searchModel.getCategoryTitle(),page);
			}
		}catch (Exception e){
			e.printStackTrace();
		}

		List<Map<String,Object>> list = page.getPage();
		List<Map<String,Object>> resultList = new ArrayList<Map<String,Object>>();

		if(null != list && list.size() > 0){
			for(Map<String,Object> map1 : list){
				String categoryContentId = map1.get("categoryContentId")+"";
				if(!StringUtils.isEmpty(categoryContentId)){
					Map<String,Object> resultMap = hmxCategoryContentService.selectContentInfoByContentId(Integer.valueOf(categoryContentId),"pc");
					resultList.add(resultMap);
				}
			}
		}
		if(null != page && page.getPage() != null){
			System.out.println("查询之后pageNum：----" + page.getPageNum());
			page.getPage().clear();
			System.out.println("清理之后pageNum：----" + pageNum);
			int startIndex = (pageNum - 1) * page.getPageSize();
			int endIndex = startIndex + page.getPageSize();
			if(endIndex >= resultList.size()){
				endIndex = resultList.size();
			}
			if(startIndex >= endIndex){
				page.setPage(new ArrayList<Map<String,Object>>());
			}else {
				page.setPage(resultList.subList(startIndex,endIndex));
			}

		}
		return new ResultBean().put("contentPage", page).setCode(Config.SUCCESS_CODE).setContent("查询内容成功");
	}

	public void slectStr(String contentValue,PageBean<Map<String,Object>> page) throws Exception{
		File file = new File(txtFileDir);
		List<Map<String,String>> mapList = new ArrayList<>();
		if(null != file){
			String[] filelist = file.list();
			if(null != filelist && filelist.length > 0){
				for(String fileName : filelist){
					String result = searchKeyword(new File(txtFileDir + File.separator + fileName), contentValue);
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
					String fileName = entry.getKey();
					String content = entry.getValue();
					System.out.println("文件名：" + fileName + ",查到的文件内容：" + content);
					try {
						//String[] strArr = fileName.split("_");
						String contentFlow = fileName.substring(0,fileName.length()-4);
						List<Map<String,Object>> contentList = hmxCategoryContentService.queryByContentFlow(contentFlow);
						if(null != contentList && contentList.size() > 0){
							Map<String,Object> contentMap = contentList.get(0);
							contentMap.put("categoryContent",content);
							if(null == page.getPage() || page.getPage().size() < 1){
								List<Map<String,Object>> list = new ArrayList<Map<String,Object>>();
								list.add(contentMap);
								page.setPage(list);
							}else{
								page.getPage().add(contentMap);
							}
							page.setTotalNum(page.getTotalNum()+1);
						}
					}catch (Exception e){
						e.printStackTrace();
					}
				}
			}
		}
	}

	public String searchKeyword(File file,String keyword) {
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

//	public void slectStr1(String contentValue,PageBean<Map<String,Object>> page) throws Exception{
//		if(page == null ){
//			page = new PageBean<Map<String,Object>>();
//		}
//		// 保存索引文件的地方
//		String indexDirectory = "/home/back/targetIndex";
//		//String indexDirectory = "https://zhonghuazhu.oss-cn-hangzhou.aliyuncs.com/test/files/targetIndex";
//		// 创建Directory对象 ，也就是分词器对象
//		Directory directory = new SimpleFSDirectory(new File(indexDirectory));
//		// 创建 IndexSearcher对象，相比IndexWriter对象，这个参数就要提供一个索引的目录就行了
//		IndexSearcher indexSearch = new IndexSearcher(directory);
//		// 创建QueryParser对象,
//		// 第1个参数表示Lucene的版本,
//		// 第2个表示搜索Field的字段,
//		// 第3个表示搜索使用分词器
//		QueryParser queryParser = new QueryParser(Version.LUCENE_30,
//				"contents", new StandardAnalyzer(Version.LUCENE_30));
//		// 生成Query对象
//		Query query = queryParser.parse(contentValue);
//		// 搜索结果 TopDocs里面有scoreDocs[]数组，里面保存着索引值
//		TopDocs hits = indexSearch.search(query, 10);
//		// hits.totalHits表示一共搜到多少个
//		System.out.println("找到了" + hits.totalHits + "个");
//		// 循环hits.scoreDocs数据，并使用indexSearch.doc方法把Document还原，再拿出对应的字段的值
//		for (int i = 0; i < hits.scoreDocs.length; i++) {
//			ScoreDoc sdoc = hits.scoreDocs[i];
//			Document doc = indexSearch.doc(sdoc.doc);
//			System.out.println(doc.get("filename"));
//			String filename = doc.get("filename");
//			if(!StringUtils.isEmpty(filename)){
//				try {
//					String[] strArr = filename.split("_");
//					String contentId = strArr[3];
//					contentId = contentId.substring(0,contentId.length()-4);
//					Map<String,Object> map = hmxCategoryContentService.queryContentById(Integer.valueOf(contentId));
//					if(null == page.getPage() || page.getPage().size() < 1){
//						List<Map<String,Object>> list = new ArrayList<Map<String,Object>>();
//						list.add(map);
//						page.setPage(list);
//					}else{
//						page.getPage().add(map);
//					}
//					page.setTotalNum(page.getTotalNum()+1);
//				}catch (Exception e){
//					e.printStackTrace();
//				}
//			}
//		}
//		indexSearch.close();
//		//=============测试end=======
//	}

	/**
	 * 内容相关推荐，根据分类来推荐
	 * @param hmxCategoryContentDto
	 * @param page
	 * @param model
	 * @return
	 */
	@GetMapping("/recommend")
	public ResultBean recommend(HmxCategoryContentDto hmxCategoryContentDto,PageBean<Map<String,Object>> page,Model model){
		page = hmxCategoryContentService.selectCategoryContentTableByPc(page, hmxCategoryContentDto,"pc");
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

}
