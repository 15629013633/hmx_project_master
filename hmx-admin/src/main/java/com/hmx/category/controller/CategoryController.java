package com.hmx.category.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.hmx.utils.result.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.alibaba.druid.util.StringUtils;
import com.hmx.category.dto.HmxCategoryDto;
import com.hmx.category.entity.HmxCategory;
import com.hmx.category.service.HmxCategoryService;
import com.hmx.utils.enums.DataState;
import com.hmx.utils.enums.IsClose;
import com.hmx.utils.result.Config;
import com.hmx.utils.result.PageBean;
import com.hmx.utils.result.ResultBean;

import org.springframework.ui.Model;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/category")
public class CategoryController {
	
	@Autowired
	private HmxCategoryService hmxCategoryService;


	@RequestMapping("/init")
	public ModelAndView init() {
		List<HmxCategory> hmxCategoryList = hmxCategoryService.list(new HmxCategoryDto());
		ModelAndView modelAndView = new ModelAndView();
		modelAndView.setViewName("/category/list");
		modelAndView.addObject("category",hmxCategoryList);
		return modelAndView;
	}

	@RequestMapping("/initAdd")
	public ModelAndView initAdd(HttpServletRequest request, @RequestParam(value = "id", required = false) Integer categoryId) {
		ModelAndView modelAndView = new ModelAndView();
		modelAndView.addObject("category",categoryId == null ? new HmxCategory() : hmxCategoryService.info(categoryId));
		modelAndView.setViewName("/category/eidt");
		return modelAndView;
	}

	/**
	 * 首页分类添加
	 * @param hmxcategoryDto
	 * @return
	 */
	@PostMapping("/add")
	public Result<Object> categoryAdd(HmxCategoryDto hmxcategoryDto, HttpServletRequest request){
		Result<Object> result = new Result<>();
		boolean flag=true;
		if(StringUtils.isEmpty(hmxcategoryDto.getCategoryName())){
			result.setStatus(Config.FAIL_FIELD_EMPTY);
			result.setMsg("分类名称不能为空");
			flag=false;
		}
		if(flag){
			Map<String,Object> resultMap = hmxCategoryService.categoryAdd(hmxcategoryDto,request);
			flag=Boolean.parseBoolean(resultMap.get("flag").toString());
			if(!flag){
				result.setStatus(Config.FAIL_CODE);
			}else{
				result.setStatus(Config.SUCCESS_CODE);
			}
		}
		return result;
	}
	/**
	 * 首页分类信息查找
	 * @return
	 */
	@GetMapping("/{id}")
	public String categoryInfo(@PathVariable(name="id",required=true) Integer categoryId,Model model){
		ResultBean resultBean = new ResultBean();
		HmxCategory hmxCategory = hmxCategoryService.info(categoryId);
		if(hmxCategory == null){
			resultBean.setCode(Config.FAIL_CODE);
			resultBean.setContent("查询分类信息失败");
		}else{
			resultBean.setCode(Config.SUCCESS_CODE);
			resultBean.setContent("查询分类信息成功");
		}
		resultBean.put("hmxCategory", hmxCategory);
		model.addAttribute("resultBean", resultBean);
		return "hello";
	}
	/**
	 * 分类信息更新
	 * @param model
	 * @return
	 */
	@PostMapping("/edit")
	public Result<Object> categoryUpdate(HmxCategoryDto hmxcategoryDto,Model model){
		Result<Object> result = new Result<>();
		boolean flag=true;
		if(hmxcategoryDto.getCategoryId() == null){
			result.setStatus(Config.FAIL_FIELD_EMPTY);
			result.setMsg("分类编号不能为空");
			flag=false;
		}
		if(flag){
			Map<String,Object> resultMap = hmxCategoryService.categoryUpdate(hmxcategoryDto);
			flag=Boolean.parseBoolean(resultMap.get("flag").toString());
			if(!flag){
				result.setStatus(Config.FAIL_CODE);
			}else{
				result.setStatus(Config.SUCCESS_CODE);
			}
		}
		return result;
	}
	/**
	 * 分类列表信息
	 * @param hmxcategoryDto
	 * @param page
	 * @param model
	 * @return
	 */
	@GetMapping("/categoryTable")
	public Map<String,Object> categoryTable(HmxCategoryDto hmxcategoryDto,PageBean<Map<String,Object>> page,Model model){
		Map<String,Object> map = new HashMap<>();
		ResultBean resultBean = new ResultBean();
		page = hmxCategoryService.selectCategoryTable(page, hmxcategoryDto);
		List<Map<String,Object>> list = page.getPage();
		if(list == null || list.size() <= 0){
			if(page.getPageNum() == 1){
				resultBean.setCode(Config.CONTENT_NULL).setContent("暂无数据");
   	    	}
   	    	else{
   	    		resultBean.setCode(Config.PAGE_NULL).setContent("没有更多数据了");
   	    	}
		}else{
			resultBean.setCode(Config.SUCCESS_CODE).setContent("查询分类列表成功");
		}
		map.put("rows", page.getPage());
		map.put("total", page.getTotalNum());
		return map;
	}
	/**
	 * 查询所有分类信息列表
	 * @param hmxCategoryDto
	 * @param model
	 * @return
	 */
	@GetMapping("/categoryAll")
	public String categoryAll(HmxCategoryDto hmxCategoryDto,Model model){
		ResultBean resultBean = new ResultBean();
		hmxCategoryDto.setState(DataState.正常.getState());
		hmxCategoryDto.setIsClose(IsClose.开放.getState());
		List<HmxCategory> hmxCategoryList = hmxCategoryService.list(hmxCategoryDto);
		if(hmxCategoryList == null || hmxCategoryList.size() <= 0){
			resultBean.setCode(Config.FAIL_CODE).setContent("没有查找到分类信息");
		}else{
			resultBean.setCode(Config.SUCCESS_CODE).setContent("没有查找到分类信息");
		}
		resultBean.put("hmxCategoryList", hmxCategoryList);
		model.addAttribute("resultBean", resultBean);
		return "hello";
	}
}
