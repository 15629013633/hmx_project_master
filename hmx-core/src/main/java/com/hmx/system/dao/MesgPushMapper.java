package com.hmx.system.dao;

import com.hmx.system.entity.MesgPush;
import com.hmx.system.entity.MesgPushExample;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2019/6/11.
 */
public interface MesgPushMapper {
    int countByExample(MesgPushExample example);

    int deleteByExample(MesgPushExample example);

    int deleteByPrimaryKey(Integer configId);

    int insert(MesgPush record);

    int insertSelective(MesgPush record);

    List<MesgPush> selectByExample(MesgPushExample example);

    MesgPush selectByPrimaryKey(Integer commentId);

    int updateByExampleSelective(@Param("record") MesgPush record, @Param("example") MesgPushExample example);

    int updateByExample(@Param("record") MesgPush record, @Param("example") MesgPushExample example);

    int updateByPrimaryKeySelective(MesgPush record);

    int updateByPrimaryKey(MesgPush record);

    Integer count(Map<String, Object> parameter);

    List<Map<String,Object>> list(Map<String, Object> parameter);
}
