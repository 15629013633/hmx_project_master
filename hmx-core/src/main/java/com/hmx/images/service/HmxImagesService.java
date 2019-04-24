package com.hmx.images.service;

import java.util.List;
import com.hmx.images.entity.HmxImages;
import com.hmx.utils.result.PageBean;
import com.hmx.images.dto.HmxImagesDto;
/**
 *  HmxImagesService interface
 *
 */
public interface HmxImagesService {
	
	/**
	 * @Method: insert 
	 * @Description: 添加
	 * @param hmxImages 要添加的对象
	 * @return 
	 */
	Boolean insert ( HmxImages hmxImages);
	
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
	 * @param hmxImages 要修改的对象
	 * @return true 修改成功  false 修改失败
	 */
	Boolean update ( HmxImages hmxImages );
	
	/**
	 * @Method: info 
	 * @Description: 根据自增主键查询对象信息
	 * @param hmxImages 根据自增对象查询信息
	 * @return HmxImages 查询的对象
	 */
	HmxImages info (Integer hmxImagesId);
	
	/**
	 * @Method: getPage 
	 * @Description: 分页查询
	 * @param page 分页参数
	 * @param hmxImagesDto 查询条件
	 * @return PageBean<HmxImages> 查询到的分页值
	 */
	PageBean<HmxImages> getPage(PageBean<HmxImages> page,HmxImagesDto hmxImagesDto);
	
	/**
	 * @Method: list 
	 * @Description: 查询某个条件下的所有数据
	 * @param hmxImagesDto 查询参数
	 * @return List<HmxImages> 符合条件的list集合
	 */
	List<HmxImages> list( HmxImagesDto hmxImagesDto );
	
}