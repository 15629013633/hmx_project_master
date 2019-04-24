

package com.hmx.verifylog.dao;


import com.hmx.verifylog.entity.HmxVerifylog;
import com.hmx.verifylog.entity.HmxVerifylogExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

/**
 * Dao interface
 */
public interface HmxVerifylogMapper{

    int countByExample(HmxVerifylogExample example);

    int deleteByExample(HmxVerifylogExample example);

    int deleteByPrimaryKey(Integer configId);

    int insert(HmxVerifylog record);

    int insertSelective(HmxVerifylog record);

    List<HmxVerifylog> selectByExample(HmxVerifylogExample example);

    HmxVerifylog selectByPrimaryKey(Integer hmxVerifylogId);

    int updateByExampleSelective(@Param("record") HmxVerifylog record, @Param("example") HmxVerifylogExample example);

    int updateByExample(@Param("record") HmxVerifylog record, @Param("example") HmxVerifylogExample example);

    int updateByPrimaryKeySelective(HmxVerifylog record);

    int updateByPrimaryKey(HmxVerifylog record);

    /**
     * 查询用户最新一条验证码记录
     * @param verifyObject
     * @return
     */
    HmxVerifylog selectNewVerifylog(String verifyObject);
}