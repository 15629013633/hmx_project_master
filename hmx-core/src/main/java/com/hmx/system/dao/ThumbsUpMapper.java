package com.hmx.system.dao;

import com.hmx.system.entity.ThumbsUp;
import com.hmx.system.entity.ThumbsUpExample;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2019/6/27.
 */
public interface ThumbsUpMapper {
    int countByExample(ThumbsUpExample example);

    int deleteByExample(ThumbsUpExample example);

    int deleteByPrimaryKey(Integer configId);

    int insert(ThumbsUp record);

    int insertSelective(ThumbsUp record);

    List<ThumbsUp> selectByExample(ThumbsUpExample example);

    ThumbsUp selectByPrimaryKey(Integer commentId);

    int updateByExampleSelective(@Param("record") ThumbsUp record, @Param("example") ThumbsUpExample example);

    int updateByExample(@Param("record") ThumbsUp record, @Param("example") ThumbsUpExample example);

    int updateByPrimaryKeySelective(ThumbsUp record);

    int updateByPrimaryKey(ThumbsUp record);

    Integer count(Map<String, Object> parameter);

    List<Map<String,Object>> list(Map<String, Object> parameter);
}
