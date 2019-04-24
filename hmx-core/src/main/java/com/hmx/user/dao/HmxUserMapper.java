package com.hmx.user.dao;


import com.hmx.user.entity.HmxUser;
import com.hmx.user.entity.HmxUserExample;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

/**
 * Dao interface
 */
public interface HmxUserMapper
{

    int countByExample(HmxUserExample example);

    int deleteByExample(HmxUserExample example);

    int deleteByPrimaryKey(Integer configId);

    int insert(HmxUser record);

    int insertSelective(HmxUser record);

    List<HmxUser> selectByExample(HmxUserExample example);

    HmxUser selectByPrimaryKey(Integer hmxUserId);

    int updateByExampleSelective(@Param("record") HmxUser record, @Param("example") HmxUserExample example);

    int updateByExample(@Param("record") HmxUser record, @Param("example") HmxUserExample example);

    int updateByPrimaryKeySelective(HmxUser record);

    int updateByPrimaryKey(HmxUser record);

    /**
     * 用户手机号查询用户信息
     *
     * @param userPhone
     * @return
     */
    HmxUser selectUserInfoByUserPhone(String userPhone);
    
    /**
     * @Author: 肖映彤
     * @Description: 分页查询全部用户信息
     * @Date: 23:36 2019-3-14
     */
    List<HmxUser> findAll(Map<String,Object> data);

    @Select("select * from hmx_user where user_name = #{userName}")
    HmxUser findUserByName(String userName);

    @Select("select * from hmx_user where user_phone = #{phone}")
    HmxUser findUserBycellPhone(String phone);
}