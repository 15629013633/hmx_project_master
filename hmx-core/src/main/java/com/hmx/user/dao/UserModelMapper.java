package com.hmx.user.dao;

import com.hmx.user.entity.po.UserModel;
import com.hmx.user.entity.po.UserModelExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

public interface UserModelMapper {
    int countByExample(UserModelExample example);

    int deleteByExample(UserModelExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(UserModel record);

    int insertSelective(UserModel record);

    List<UserModel> selectByExample(UserModelExample example);

    UserModel selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") UserModel record, @Param("example") UserModelExample example);

    int updateByExample(@Param("record") UserModel record, @Param("example") UserModelExample example);

    int updateByPrimaryKeySelective(UserModel record);

    int updateByPrimaryKey(UserModel record);

    @Select("select * from sys_user where username = #{username}")
    UserModel findUserByName(String username);

    @Select("select * from sys_user where cell_phone = #{cellPhone}")
    UserModel findUserBycellPhone(String cellPhone);

    @Select("SELECT * FROM user limit #{startIndex},#{pageSize}")
    List<UserModel> findAll(Integer startIndex,Integer pageSize);
}