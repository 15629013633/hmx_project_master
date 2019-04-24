package com.hmx.verifylog.service;

import java.util.List;
import com.hmx.verifylog.entity.HmxVerifylog;
import com.hmx.utils.result.PageBean;
import com.hmx.verifylog.dto.HmxVerifylogDto;
/**
 *  HmxVerifylogService interface
 *
 */
public interface HmxVerifylogService {
	
	/**
	 * @Method: insert 
	 * @Description: 添加
	 * @param hmxVerifylog 要添加的对象
	 * @return 
	 */
	Boolean insert ( HmxVerifylog hmxVerifylog);
	
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
	 * @param hmxVerifylog 要修改的对象
	 * @return true 修改成功  false 修改失败
	 */
	Boolean update ( HmxVerifylog hmxVerifylog );
	
	/**
	 * @Method: info 
	 * @Description: 根据自增主键查询对象信息
	 * @param hmxVerifylog 根据自增对象查询信息
	 * @return HmxVerifylog 查询的对象
	 */
	HmxVerifylog info (Integer hmxVerifylogId);
	
	/**
	 * @Method: getPage 
	 * @Description: 分页查询
	 * @param page 分页参数
	 * @param hmxVerifylogDto 查询条件
	 * @return PageBean<HmxVerifylog> 查询到的分页值
	 */
	PageBean<HmxVerifylog> getPage(PageBean<HmxVerifylog> page,HmxVerifylogDto hmxVerifylogDto);
	
	/**
	 * @Method: list 
	 * @Description: 查询某个条件下的所有数据
	 * @param hmxVerifylogDto 查询参数
	 * @return List<HmxVerifylog> 符合条件的list集合
	 */
	List<HmxVerifylog> list( HmxVerifylogDto hmxVerifylogDto );
	
	/**
     * 查询用户最新一条验证码记录
     * @param verifyObject
     * @return
     */
    HmxVerifylog selectNewVerifylog(String verifyObject);
}