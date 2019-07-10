package com.hmx.system.dao;

import com.hmx.system.entity.HmxGov;
import com.hmx.system.entity.HmxGovExample;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2019/7/10.
 */
public interface HmxGovMapper {
    int countByExample(HmxGovExample example);

    int deleteByExample(HmxGovExample example);

    int deleteByPrimaryKey(Integer configId);

    int insert(HmxGov record);

    int insertSelective(HmxGov record);

    List<HmxGov> selectByExample(HmxGovExample example);

    HmxGov selectByPrimaryKey(Integer commentId);

    int updateByExampleSelective(@Param("record") HmxGov record, @Param("example") HmxGovExample example);

    int updateByExample(@Param("record") HmxGov record, @Param("example") HmxGovExample example);

    int updateByPrimaryKeySelective(HmxGov record);

    int updateByPrimaryKey(HmxGov record);

    Integer count(Map<String, Object> parameter);

    List<Map<String,Object>> list(Map<String, Object> parameter);
}
