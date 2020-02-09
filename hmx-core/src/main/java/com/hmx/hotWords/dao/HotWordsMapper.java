package com.hmx.hotWords.dao;

import com.hmx.hotWords.entity.HotWords;
import com.hmx.hotWords.entity.HotWordsExample;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2019/5/2.
 */
@Mapper
public interface HotWordsMapper {
    int countByExample(HotWordsExample example);

    int deleteByExample(HotWordsExample example);

    int deleteByPrimaryKey(Integer configId);

    int insert(HotWords record);

    int insertSelective(HotWords record);

    List<HotWords> selectByExample(HotWordsExample example);

    HotWords selectByPrimaryKey(Integer hmxFilesId);

    int updateByExampleSelective(@Param("record") HotWords record, @Param("example") HotWordsExample example);

    int updateByExample(@Param("record") HotWords record, @Param("example") HotWordsExample example);

    int updateByPrimaryKeySelective(HotWords record);

    int updateByPrimaryKey(HotWords record);

    Integer count(Map<String, Object> parameter);

    List<Map<String,Object>> list(Map<String, Object> parameter);
}
