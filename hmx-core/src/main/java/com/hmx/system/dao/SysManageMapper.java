package com.hmx.system.dao;

import com.hmx.system.entity.SysManage;
import com.hmx.system.entity.SysManageExample;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

@Mapper
public interface SysManageMapper {
    int countByExample(SysManageExample example);

    int deleteByExample(SysManageExample example);

    int deleteByPrimaryKey(Integer configId);

    int insert(SysManage record);

    int insertSelective(SysManage record);

    List<SysManage> selectByExample(SysManageExample example);

    SysManage selectByPrimaryKey(Integer commentId);

    int updateByExampleSelective(@Param("record") SysManage record, @Param("example") SysManageExample example);

    int updateByExample(@Param("record") SysManage record, @Param("example") SysManageExample example);

    int updateByPrimaryKeySelective(SysManage record);

    int updateByPrimaryKey(SysManage record);

    Integer count(Map<String, Object> parameter);

    List<Map<String,Object>> list(Map<String, Object> parameter);
}
