package com.hmx.system.dao;

import com.hmx.system.entity.HmxVideo;
import com.hmx.system.entity.HmxVideoExample;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2019/5/19.
 */
public interface HmxVideoMapper {
//    int countByExample(HmxVideoExample example);
//
//    int deleteByExample(HmxVideoExample example);
//
//    int deleteByPrimaryKey(Integer configId);

    int insert(HmxVideo record);

//    int insertSelective(HmxVideo record);

    List<HmxVideo> selectByExample(HmxVideoExample example);

//    HmxVideo selectByPrimaryKey(Integer hmxFilesId);
//
//    int updateByExampleSelective(@Param("record") HmxVideo record, @Param("example") HmxVideoExample example);
//
//    int updateByExample(@Param("record") HmxVideo record, @Param("example") HmxVideoExample example);
//
    int updateByPrimaryKeySelective(HmxVideo record);
//
//    int updateByPrimaryKey(HmxVideo record);
//
//    Integer count(Map<String, Object> parameter);
//
//    List<Map<String,Object>> list(Map<String, Object> parameter);
}
