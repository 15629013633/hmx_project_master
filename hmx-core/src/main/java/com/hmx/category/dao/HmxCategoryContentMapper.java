package com.hmx.category.dao;


import com.hmx.category.entity.HmxCategoryContent;
import com.hmx.category.entity.HmxCategoryContentExample;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

/**
 * Dao interface
 */
@Mapper
public interface HmxCategoryContentMapper{

    int countByExample(HmxCategoryContentExample example);

    int deleteByExample(HmxCategoryContentExample example);

    int deleteByPrimaryKey(Integer configId);

    int insert(HmxCategoryContent record);

    int insertSelective(HmxCategoryContent record);

    List<HmxCategoryContent> selectByExample(HmxCategoryContentExample example);

    List<HmxCategoryContent> selectNewestByExample(HmxCategoryContentExample example);

    HmxCategoryContent selectByPrimaryKey(Integer hmxCategoryContentId);

    int updateByExampleSelective(@Param("record") HmxCategoryContent record, @Param("example") HmxCategoryContentExample example);

    int updateByExample(@Param("record") HmxCategoryContent record, @Param("example") HmxCategoryContentExample example);

    int updateByPrimaryKeySelective(HmxCategoryContent record);

    int updateByPrimaryKey(HmxCategoryContent record);
    /**
     * 查询内容详情
     * @param categoryContentId
     * @return
     */
    HmxCategoryContent selectCategoryContentById(Integer categoryContentId);
    /**
     * 内容列表查询
     * @param parameter
     * @return
     */
    List<Map<String,Object>> selectCategoryContentTable(Map<String,Object> parameter);
    int countCategoryContentTable(Map<String,Object> parameter);
    /**
     * 内容信息详情查询
     * @param categoryContentId
     * @return
     */
    Map<String,Object> selectContentInfoByContentId(Integer categoryContentId);
    /**
     * PC内容列表查询
     * @param parameter
     * @return
     */
    List<Map<String,Object>> selectCategoryContentTableByPc(Map<String,Object> parameter);
    int countCategoryContentTableByPc(Map<String,Object> parameter);
    /**
     * 查看排行榜信息
     * @return
     */
    List<Map<String,Object>> selectRankingListByCategoryId();

    List<Map<String,Object>> queryByContentFlow(String contentFlow);

    Integer countContentTable(Map<String, Object> parameter);

    List<Map<String,Object>> selectContentTable(Map<String, Object> parameter);

    List<Map<String,Object>> maxBromUnm(Map<String, Object> parameter);
    String maxSort(Map<String, Object> parameter);

    void setContentTop(Integer contentId);

    Integer getContentTop(Integer categoryId);

    void removeContentTop(int contentTopId);

    void upAndDown(Map<String, Object> parameter);

    void publishNew(Integer contentId);

    void unPublishNew(Integer valueOf);

    /**
     * 查询某个分类下比某个sort更大的数据中的最小排序的数据记录
     * @param parameter
     * @return
     */
    HmxCategoryContent selectMinSort(Map<String, Object> parameter);

    /**
     * 查询某个分类下比某个sort更小的数据中的最大排序的数据记录
     * @param parameter
     * @return
     */
    HmxCategoryContent selectMaxSort(Map<String, Object> parameter);
}