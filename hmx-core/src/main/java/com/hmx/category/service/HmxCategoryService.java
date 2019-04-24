package com.hmx.category.service;

import java.util.List;
import java.util.Map;

import com.hmx.category.entity.HmxCategory;
import com.hmx.utils.result.PageBean;
import com.hmx.category.dto.HmxCategoryDto;

import javax.servlet.http.HttpServletRequest;

/**
 *  HmxCategoryService interface
 *
 */
public interface HmxCategoryService {
	
	/**
	 * @Method: insert 
	 * @Description: 添加
	 * @param hmxCategory 要添加的对象
	 * @return 
	 */
	Boolean insert ( HmxCategory hmxCategory);
	
	/**
	 * @Method: deleteByIdArray 
	 * @Description: 批量删除
	 * @param ids 将要删除的对象主键字符串 例如:1,5,10,12
	 * @return true 删除成功  false 删除失败
	 */
	Boolean deleteByIdArray(String ids);
	
	/**
	 * @Method: update 
	 * @Description: 修改
	 * @param hmxCategory 要修改的对象
	 * @return true 修改成功  false 修改失败
	 */
	Boolean update ( HmxCategory hmxCategory );
	
	/**
	 * @Method: info 
	 * @Description: 根据自增主键查询对象信息
	 * @param hmxCategory 根据自增对象查询信息
	 * @return HmxCategory 查询的对象
	 */
	HmxCategory info (Integer hmxCategoryId);
	
	/**
	 * @Method: getPage 
	 * @Description: 分页查询
	 * @param page 分页参数
	 * @param hmxCategoryDto 查询条件
	 * @return PageBean<HmxCategory> 查询到的分页值
	 */
	PageBean<HmxCategory> getPage(PageBean<HmxCategory> page,HmxCategoryDto hmxCategoryDto);
	
	/**
	 * @Method: list 
	 * @Description: 查询某个条件下的所有数据
	 * @param hmxCategoryDto 查询参数
	 * @return List<HmxCategory> 符合条件的list集合
	 */
	List<HmxCategory> list( HmxCategoryDto hmxCategoryDto );
	
	/**
     * 获取首页分类以及内容信息
     * @return
     */
    List<Map<String,Object>> selectCategoryAndContentList();
    
    /**
     * 分类添加
     * @param hmxCategoryDto
     * @return
     */
    Map<String,Object> categoryAdd(HmxCategoryDto hmxCategoryDto, HttpServletRequest request);
    
    /**
     * 分类在首页显示模块排序
     * @return
     */
    int selectCategoryMaxSort();
    /**
     * 分类编辑更新
     * @param hmxCategoryDto
     * @return
     */
    Map<String,Object> categoryUpdate(HmxCategoryDto hmxCategoryDto);
    /**
     * 查询分类名是否重复
     * @return
     */
    boolean selectIsCategoryName(HmxCategoryDto hmxCategoryDto);
    /**
     * 分类列表
     * @param parameter
     */
    PageBean<Map<String,Object>> selectCategoryTable(PageBean<Map<String,Object>> page,HmxCategoryDto hmxCategoryDto);
}