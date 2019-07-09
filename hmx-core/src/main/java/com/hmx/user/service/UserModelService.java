package com.hmx.user.service;

import com.hmx.user.dto.UserModelDto;
import com.hmx.user.entity.po.UserModel;
import com.hmx.utils.result.PageBean;
import com.hmx.utils.result.Result;

import java.util.List;

/**
 * Created by Administrator on 2019/7/8.
 */
public interface UserModelService {
    /**
     * @Method: insert
     * @Description: 添加
     * @param user 要添加的对象
     * @return
     */
    Boolean insert (UserModel user);

    /**
     * @Method: deleteByIdArray
     * @Description: 批量删除
     * @param ids 将要删除的对象主键字符串 例如:1,5,10,12
     * @return true 删除成功  false 删除失败
     */
    Boolean deleteByIdArray(String ids);

    /**
     * @Method: update
     * @Description: 修改
     * @param user 要修改的对象
     * @return true 修改成功  false 修改失败
     */
    Boolean update ( UserModel user );

    /**
     * @Method: info
     * @Description: 根据自增主键查询对象信息
     * @param userId 根据自增对象查询信息
     * @return HmxUser 查询的对象
     */
    UserModel info (Integer userId);

    /**
     * @Method: getPage
     * @Description: 分页查询
     * @param page 分页参数
     * @param userDto 查询条件
     * @return PageBean<HmxUser> 查询到的分页值
     */
    PageBean<UserModel> getPage(PageBean<UserModel> page, UserModelDto userDto);

    /**
     * @Method: list
     * @Description: 查询某个条件下的所有数据
     * @param userDto 查询参数
     * @return List<HmxUser> 符合条件的list集合
     */
    List<UserModel> list(UserModelDto userDto );
    /**
     * 用户登录
     * @param user
     * @return
     */
    UserModel login(UserModel user);
    /**
     * 用户手机号查询用户信息
     * @param userPhone
     * @return
     */
    UserModel selectUserInfoByUserPhone(String userPhone);

    //增加或者修改用户信息
    Result<Object> addOrUpdateUser(UserModelDto userDto);

    /**
     * 修改用户密码
     * @param userPhone
     * @param oldPassword
     * @param newPassword
     * @return
     */
    boolean modifyPas(String userPhone, String oldPassword, String newPassword);

    /**
     * 找回密码
     * @param userPhone
     * @param newPassword
     * @return
     */
    boolean findPas(String userPhone, String newPassword);

    boolean updateUserLever(String ids, Integer userLevel);
}
