package com.hmx.movie.service;

import java.util.List;
import com.hmx.movie.entity.HmxMovie;
import com.hmx.utils.result.PageBean;
import com.hmx.movie.dto.HmxMovieDto;
/**
 *  HmxMovieService interface
 *
 */
public interface HmxMovieService {
	
	/**
	 * @Method: insert 
	 * @Description: 添加
	 * @param hmxMovie 要添加的对象
	 * @return 
	 */
	Boolean insert ( HmxMovie hmxMovie);
	
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
	 * @param hmxMovie 要修改的对象
	 * @return true 修改成功  false 修改失败
	 */
	Boolean update ( HmxMovie hmxMovie );
	
	/**
	 * @Method: info 
	 * @Description: 根据自增主键查询对象信息
	 * @param hmxMovie 根据自增对象查询信息
	 * @return HmxMovie 查询的对象
	 */
	HmxMovie info (Integer hmxMovieId);
	
	/**
	 * @Method: getPage 
	 * @Description: 分页查询
	 * @param page 分页参数
	 * @param hmxMovieDto 查询条件
	 * @return PageBean<HmxMovie> 查询到的分页值
	 */
	PageBean<HmxMovie> getPage(PageBean<HmxMovie> page,HmxMovieDto hmxMovieDto);
	
	/**
	 * @Method: list 
	 * @Description: 查询某个条件下的所有数据
	 * @param hmxMovieDto 查询参数
	 * @return List<HmxMovie> 符合条件的list集合
	 */
	List<HmxMovie> list( HmxMovieDto hmxMovieDto );
	
}