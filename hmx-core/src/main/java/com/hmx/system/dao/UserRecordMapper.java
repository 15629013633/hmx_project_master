package com.hmx.system.dao;

import com.hmx.system.entity.UserRecord;
import com.hmx.system.entity.UserRecordExample;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2019/6/12.
 */
@Mapper
public interface UserRecordMapper {
    int countByExample(UserRecordExample example);

    int deleteByExample(UserRecordExample example);

    int deleteByPrimaryKey(Integer configId);

    int insert(UserRecord record);

    int insertSelective(UserRecord record);

    List<UserRecord> selectByExample(UserRecordExample example);

    UserRecord selectByPrimaryKey(Integer commentId);

    int updateByExampleSelective(@Param("record") UserRecord record, @Param("example") UserRecordExample example);

    int updateByExample(@Param("record") UserRecord record, @Param("example") UserRecordExample example);

    int updateByPrimaryKeySelective(UserRecord record);

    int updateByPrimaryKey(UserRecord record);

    Integer count(Map<String, Object> parameter);

    List<Map<String,Object>> list(Map<String, Object> parameter);

    Integer countContentTable(Map<String, Object> parameter);

    List<UserRecord> selectContentTable(Map<String, Object> parameter);
}
