package com.hmx.category.dao;


import com.hmx.category.entity.HmxCategory;
import com.hmx.category.entity.HmxCategoryExample;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

/**
 * Dao interface
 */
public interface HmxCategoryMapper{

    int countByExample(HmxCategoryExample example);

    int deleteByExample(HmxCategoryExample example);

    int deleteByPrimaryKey(Integer configId);

    int insert(HmxCategory record);

    int insertSelective(HmxCategory record);

    List<HmxCategory> selectByExample(HmxCategoryExample example);

    HmxCategory selectByPrimaryKey(Integer hmxCategoryId);

    int updateByExampleSelective(@Param("record") HmxCategory record, @Param("example") HmxCategoryExample example);

    int updateByExample(@Param("record") HmxCategory record, @Param("example") HmxCategoryExample example);

    int updateByPrimaryKeySelective(HmxCategory record);

    int updateByPrimaryKey(HmxCategory record);
    /**
     * 获取首页分类以及内容信息
     * @return
     */
    List<Map<String,Object>> selectCategoryAndContentList();
    /**
     * 分类在首页显示模块排序
     * @return
     */
    int selectCategoryMaxSort();
    /**
     * 查询分类名是否重复
     * @return
     */
    int selectIsCategoryName(Map<String,Object> parameter);
    /**
     * 分类列表
     * @param parameter
     */
    List<Map<String,Object>> selectCategoryTable(Map<String,Object> parameter);
    int countCategoryTable(Map<String,Object> parameter);
}