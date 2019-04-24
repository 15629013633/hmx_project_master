package com.hmx.category.controller;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.hmx.category.service.HmxCategoryService;
import com.hmx.utils.result.Config;
import com.hmx.utils.result.ResultBean;

@RestController
@RequestMapping("/category")
public class CategoryController {

	@Autowired
	private HmxCategoryService hmxCategoryService;
	
	@GetMapping("/all")
	public ResultBean getCategoryAll(){
		List<Map<String,Object>> categoryList = hmxCategoryService.selectCategoryAndContentList();
		if(categoryList == null || categoryList.size() <= 0){
			return new ResultBean().setCode(Config.FAIL_CODE).setContent("没有查找到首页信息");
		}
		return new ResultBean().setCode(Config.SUCCESS_CODE).put("categoryList", categoryList).setContent("查询首页信息成功");
	}
}
