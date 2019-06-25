package com.hmx.category.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.hmx.category.dto.HmxCategoryContentDto;
import com.hmx.category.dto.HmxCategoryDto;
import com.hmx.category.entity.HmxCategory;
import com.hmx.category.entity.HmxCategoryContent;
import com.hmx.category.service.HmxCategoryContentService;
import com.hmx.images.dto.HmxImagesDto;
import com.hmx.images.entity.HmxImages;
import com.hmx.images.service.HmxImagesService;
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
	@Autowired
	private HmxCategoryContentService hmxCategoryContentService;
	@Autowired
	private HmxImagesService hmxImagesService;
	
	@GetMapping("/all")
	public ResultBean getCategoryAll(){
		//查询首页一级分类
		List<Map<String,Object>> categoryList = hmxCategoryService.selectCategoryAndContentList();
		if(categoryList == null || categoryList.size() <= 0){
			return new ResultBean().setCode(Config.FAIL_CODE).setContent("没有查找到首页信息");
		}
		//查询首页轮播图
		HmxCategoryContentDto hmxCategoryContentDto = new HmxCategoryContentDto();
		hmxCategoryContentDto.setMode(2);
		List<HmxCategoryContent> wheelPlantList = hmxCategoryContentService.list(hmxCategoryContentDto);
		List<Map<String,Object>> wheelPlantMapList = new ArrayList<>();
		if(null != wheelPlantList && wheelPlantList.size() > 0){
			getImageUrl(wheelPlantList,wheelPlantMapList);
		}
		//获取首页大图
		HmxCategoryContentDto hmxCategoryContentDto2 = new HmxCategoryContentDto();
		hmxCategoryContentDto2.setMode(3);
		List<HmxCategoryContent> homePageList = hmxCategoryContentService.list(hmxCategoryContentDto2);
		List<Map<String,Object>> homePageMapList = new ArrayList<>();
		if(null != homePageList && homePageList.size() > 0){
			getImageUrl(homePageList,homePageMapList);
		}
		return new ResultBean().setCode(Config.SUCCESS_CODE).put("categoryList", categoryList)
				.put("wheelPlantList",wheelPlantMapList).put("homePageList",homePageMapList)
				.setContent("查询首页信息成功");
	}

	public void getImageUrl(List<HmxCategoryContent> homePageList,List<Map<String,Object>> list){
		for(HmxCategoryContent hmxCategoryContent : homePageList){
			Map<String,Object> map = new HashMap<>();
			map.put("categoryContentId",hmxCategoryContent.getCategoryContentId());
			map.put("contentImages","");
			map.put("categoryTitle",hmxCategoryContent.getCategoryTitle());
			map.put("contentType",hmxCategoryContent.getContentType());
			map.put("mode",hmxCategoryContent.getMode());
			map.put("userLevel",hmxCategoryContent.getUserLevel());
			//查询内容的图片
			HmxImagesDto hmxImagesDto = new HmxImagesDto();
			hmxImagesDto.setCategoryContentId(hmxCategoryContent.getCategoryContentId());
			List<HmxImages> imagesList = hmxImagesService.list(hmxImagesDto);
			if(null != imagesList && imagesList.size() > 0){
				HmxImages images = imagesList.get(0);
				map.put("contentImages",images.getImageUrl());
			}
			list.add(map);
		}
	}

	@GetMapping("/allCategory")
	/**
	 * parentId 为0则查询所有一级分类    不为0则查某个一级分类下的所有二级分类
	 */
	public ResultBean allCategory(HmxCategoryDto hmxCategoryDto){
		//查询分类
		List<HmxCategory> categoryList = hmxCategoryService.list(hmxCategoryDto);
		if(categoryList == null || categoryList.size() <= 0){
			return new ResultBean().setCode(Config.FAIL_CODE).put("categoryList", categoryList).setContent("没有查找到分类信息");
		}
		return new ResultBean().setCode(Config.SUCCESS_CODE).put("categoryList", categoryList).setContent("查询首页信息成功");
	}

	@GetMapping("/allTopCategory")
	/**
	 * 获取所有一级分类
	 */
	public ResultBean allTopCategory(HmxCategoryDto hmxCategoryDto){
		//查询分类
		List<Map<String,Object>> categoryList = hmxCategoryService.allTopCategory(hmxCategoryDto);
		if(categoryList == null || categoryList.size() <= 0){
			return new ResultBean().setCode(Config.FAIL_CODE).put("categoryList", categoryList).setContent("没有查找到分类信息");
		}
		return new ResultBean().setCode(Config.SUCCESS_CODE).put("categoryList", categoryList).setContent("查询首页信息成功");
	}

}
