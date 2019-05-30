package com.hmx.system.dao;

import com.hmx.system.entity.Tagtab;
import com.hmx.system.entity.TagtabExample;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * Created by songjinbao on 2019/5/30.
 */
public interface TagtabMapper {
    int countByExample(TagtabExample example);

    int deleteByExample(TagtabExample example);

    int deleteByPrimaryKey(Integer configId);

    int insert(Tagtab record);

    int insertSelective(Tagtab record);

    List<Tagtab> selectByExample(TagtabExample example);

    Tagtab selectByPrimaryKey(Integer commentId);

    int updateByExampleSelective(@Param("record") Tagtab record, @Param("example") TagtabExample example);

    int updateByExample(@Param("record") Tagtab record, @Param("example") TagtabExample example);

    int updateByPrimaryKeySelective(Tagtab record);

    int updateByPrimaryKey(Tagtab record);

    Integer count(Map<String, Object> parameter);

    List<Map<String,Object>> list(Map<String, Object> parameter);
}
