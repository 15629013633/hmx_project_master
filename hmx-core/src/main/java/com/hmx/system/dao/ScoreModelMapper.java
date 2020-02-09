package com.hmx.system.dao;

import com.hmx.system.entity.ScoreModel;
import com.hmx.system.entity.ScoreModelExample;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2019/6/2.
 */
@Mapper
public interface ScoreModelMapper {
    int countByExample(ScoreModelExample example);

    int deleteByExample(ScoreModelExample example);

    int deleteByPrimaryKey(Integer configId);

    int insert(ScoreModel record);

    int insertSelective(ScoreModel record);

    List<ScoreModel> selectByExample(ScoreModelExample example);

    ScoreModel selectByPrimaryKey(Integer commentId);

    int updateByExampleSelective(@Param("record") ScoreModel record, @Param("example") ScoreModelExample example);

    int updateByExample(@Param("record") ScoreModel record, @Param("example") ScoreModelExample example);

    int updateByPrimaryKeySelective(ScoreModel record);

    int updateByPrimaryKey(ScoreModel record);

    Integer count(Map<String, Object> parameter);

    List<Map<String,Object>> list(Map<String, Object> parameter);
}
