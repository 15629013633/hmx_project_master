package com.hmx.system.dao;

import com.hmx.system.entity.GradeModel;
import com.hmx.system.entity.GradeModelExample;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2019/6/20.
 */
public interface GradeModelMapper {
    int countByExample(GradeModelExample example);

    int deleteByExample(GradeModelExample example);

    int deleteByPrimaryKey(Integer configId);

    int insert(GradeModel record);

    int insertSelective(GradeModel record);

    List<GradeModel> selectByExample(GradeModelExample example);

    GradeModel selectByPrimaryKey(Integer commentId);

    int updateByExampleSelective(@Param("record") GradeModel record, @Param("example") GradeModelExample example);

    int updateByExample(@Param("record") GradeModel record, @Param("example") GradeModelExample example);

    int updateByPrimaryKeySelective(GradeModel record);

    int updateByPrimaryKey(GradeModel record);

    Integer count(Map<String, Object> parameter);

    List<Map<String,Object>> list(Map<String, Object> parameter);
}
