package com.hmx.system.dao;

import com.hmx.system.entity.SourceModel;
import com.hmx.system.entity.SourceModelExample;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2019/5/30.
 */
@Mapper
public interface SourceModelMapper {
    int countByExample(SourceModelExample example);

    int deleteByExample(SourceModelExample example);

    int deleteByPrimaryKey(Integer configId);

    int insert(SourceModel sourceModel);

    int insertSelective(SourceModel record);

    List<SourceModel> selectByExample(SourceModelExample example);

    SourceModel selectByPrimaryKey(Integer commentId);

    int updateByExampleSelective(@Param("record") SourceModel record, @Param("example") SourceModelExample example);

    int updateByExample(@Param("record") SourceModel record, @Param("example") SourceModelExample example);

    int updateByPrimaryKeySelective(SourceModel record);

    int updateByPrimaryKey(SourceModel record);

    Integer count(Map<String, Object> parameter);

    List<Map<String,Object>> list(Map<String, Object> parameter);
}
