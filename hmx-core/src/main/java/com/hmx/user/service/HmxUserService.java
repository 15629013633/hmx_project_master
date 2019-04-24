package com.hmx.user.service;

import java.util.List;
import com.hmx.user.entity.HmxUser;
import com.hmx.utils.result.PageBean;
import com.hmx.user.dto.HmxUserDto;
import com.hmx.utils.result.Result;

/**
 *  HmxUserService interface
 *
 */
public interface HmxUserService {
	
	/**
	 * @Method: insert 
	 * @Description: 添加
	 * @param hmxUser 要添加的对象
	 * @return 
	 */
	Boolean insert ( HmxUser hmxUser);
	
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
	 * @param hmxUser 要修改的对象
	 * @return true 修改成功  false 修改失败
	 */
	Boolean update ( HmxUser hmxUser );
	
	/**
	 * @Method: info 
	 * @Description: 根据自增主键查询对象信息
	 * @param hmxUser 根据自增对象查询信息
	 * @return HmxUser 查询的对象
	 */
	HmxUser info (Integer hmxUserId);
	
	/**
	 * @Method: getPage 
	 * @Description: 分页查询
	 * @param page 分页参数
	 * @param hmxUserDto 查询条件
	 * @return PageBean<HmxUser> 查询到的分页值
	 */
	PageBean<HmxUser> getPage(PageBean<HmxUser> page,HmxUserDto hmxUserDto);
	
	/**
	 * @Method: list 
	 * @Description: 查询某个条件下的所有数据
	 * @param hmxUserDto 查询参数
	 * @return List<HmxUser> 符合条件的list集合
	 */
	List<HmxUser> list( HmxUserDto hmxUserDto );
	/**
	 * 用户登录
	 * @param hmxUser
	 * @return
	 */
	HmxUser login(HmxUser hmxUser);
	/**
     * 用户手机号查询用户信息
     * @param userPhone
     * @return
     */
    HmxUser selectUserInfoByUserPhone(String userPhone);

	//增加或者修改用户信息
	Result<Object> addOrUpdateUser(HmxUserDto hmxUserDto);
	
}