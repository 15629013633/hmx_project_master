package com.hmx.category.controller;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
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
}
