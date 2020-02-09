package com.hmx.files.dao;

import com.hmx.files.entity.HmxFiles;
import com.hmx.files.entity.HmxFilesExample;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 文件
 * Created by Administrator on 2019/4/26.
 */
@Mapper
public interface HmxFilesMapper {
    int countByExample(HmxFilesExample example);

    int deleteByExample(HmxFilesExample example);

    int deleteByPrimaryKey(Integer configId);

    int insert(HmxFiles record);

    int insertSelective(HmxFiles record);

    List<HmxFiles> selectByExample(HmxFilesExample example);

    HmxFiles selectByPrimaryKey(Integer hmxFilesId);

    int updateByExampleSelective(@Param("record") HmxFiles record, @Param("example") HmxFilesExample example);

    int updateByExample(@Param("record") HmxFiles record, @Param("example") HmxFilesExample example);

    int updateByPrimaryKeySelective(HmxFiles record);

    int updateByPrimaryKey(HmxFiles record);
}
