package com.hmx.images.dao;


import java.util.List;
import org.apache.ibatis.annotations.Param;

import com.hmx.images.entity.HmxImages;
import com.hmx.images.entity.HmxImagesExample;

/**
 * Dao interface
 */
public interface HmxImagesMapper{

    int countByExample(HmxImagesExample example);

    int deleteByExample(HmxImagesExample example);

    int deleteByPrimaryKey(Integer configId);

    int insert(HmxImages record);

    int insertSelective(HmxImages record);

    List<HmxImages> selectByExample(HmxImagesExample example);

    HmxImages selectByPrimaryKey(Integer hmxImagesId);

    int updateByExampleSelective(@Param("record") HmxImages record, @Param("example") HmxImagesExample example);

    int updateByExample(@Param("record") HmxImages record, @Param("example") HmxImagesExample example);

    int updateByPrimaryKeySelective(HmxImages record);

    int updateByPrimaryKey(HmxImages record);

}