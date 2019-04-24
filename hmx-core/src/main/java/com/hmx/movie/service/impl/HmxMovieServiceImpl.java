package com.hmx.movie.service.impl;

import java.util.List;
import java.util.ArrayList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;


import com.hmx.movie.service.HmxMovieService;
import com.hmx.utils.result.PageBean;
import com.hmx.movie.entity.HmxMovie;
import com.hmx.movie.dto.HmxMovieDto;
import com.hmx.movie.entity.HmxMovieExample;
import com.hmx.movie.entity.HmxMovieExample.Criteria;
import com.hmx.movie.dao.HmxMovieMapper;
/**
 * Service implementation class
 *
 */
 @Service
 public class HmxMovieServiceImpl implements HmxMovieService{
 
 	@Autowired
	private HmxMovieMapper hmxMovieMapper;
	
	
	/**
	 * @Method: insert 
	 * @Description: 添加
	 * @param hmxMovie 要添加的对象
	 * @return 
	 */
	@Override
	public Boolean insert( HmxMovie hmxMovie ) {
		return hmxMovieMapper.insertSelective( hmxMovie ) > 0;
	}
		
	/**
	 * @Method: deleteByIdArray 
	 * @Description: 批量删除
	 * @param ids 将要删除的对象主键字符串 例如:1,5,10,12
	 * @return true 删除成功  false 删除失败
	 */
	 @Override
	 @Transactional
	 public Boolean deleteByIdArray(String ids) {
	 	List<Integer> idArray = new ArrayList<Integer>();
		String[] arrayStr = null ;
		try{
			if( ids == null || ids == "" ){
				return false;
			}
			
			if( ids.length() > 0 ){
				arrayStr = ids.split(",");
			}
			
			for(String strid: arrayStr){
				Integer id = Integer.parseInt(strid);
				idArray.add(id);
			}
			HmxMovieExample hmxMovieExample = new HmxMovieExample();
			hmxMovieExample.or().andMovieIdIn( idArray );
			
			int ret = hmxMovieMapper.deleteByExample( hmxMovieExample );
			return ret > 0;
		}catch( Exception e ){
			e.printStackTrace();
		}
		return false;
	}
	
	/**
	 * @Method: update 
	 * @Description: 修改
	 * @param hmxMovie 要修改的对象
	 * @return true 修改成功  false 修改失败
	 */
	@Override
	public Boolean update(HmxMovie hmxMovie) {
		return hmxMovieMapper.updateByPrimaryKeySelective( hmxMovie ) > 0;
	}
	
	
	/**
	 * @Method: info 
	 * @Description: 根据自增主键查询对象信息
	 * @param hmxMovie 根据自增对象查询信息
	 * @return HmxMovie 查询的对象
	 */
	public HmxMovie info (Integer hmxMovieId) {
		return hmxMovieMapper.selectByPrimaryKey( hmxMovieId );
	}
	
	/**
	 * @Method: getPage 
	 * @Description: 分页查询
	 * @param page 分页参数
	 * @param hmxMovieDto 查询条件
	 * @return PageBean<HmxMovie> 查询到的分页值
	 */
	public PageBean<HmxMovie> getPage(PageBean<HmxMovie> page,HmxMovieDto hmxMovieDto) {
		
		HmxMovieExample hmxMovieExample = new HmxMovieExample();
		
		hmxMovieExample.setOffset(page.getStartOfPage());
		hmxMovieExample.setLimit(page.getPageSize());
		
		Criteria where = hmxMovieExample.createCriteria();
		
  		if ( hmxMovieDto.getMovieId() != null && hmxMovieDto.getMovieId() != 0 ) {
			where.andMovieIdEqualTo( hmxMovieDto.getMovieId() );
		}
  		if ( !StringUtils.isEmpty( hmxMovieDto.getMovieName() ) ) {
			where.andMovieNameEqualTo( hmxMovieDto.getMovieName() );
		}
  		if ( !StringUtils.isEmpty( hmxMovieDto.getRatio() ) ) {
			where.andRatioEqualTo( hmxMovieDto.getRatio() );
		}
  		if ( !StringUtils.isEmpty( hmxMovieDto.getDuration() ) ) {
			where.andDurationEqualTo( hmxMovieDto.getDuration() );
		}
  		if ( !StringUtils.isEmpty( hmxMovieDto.getMovieUrl() ) ) {
			where.andMovieUrlEqualTo( hmxMovieDto.getMovieUrl() );
		}
  		if ( hmxMovieDto.getCreateTime() != null ) {
  			where.andCreateTimeEqualTo( hmxMovieDto.getCreateTime() );
  		}
  		if ( hmxMovieDto.getNewTime() != null ) {
  			where.andNewTimeEqualTo( hmxMovieDto.getNewTime() );
  		}
  		if ( hmxMovieDto.getState() != null ) {
			where.andStateEqualTo( hmxMovieDto.getState() );
		}
  		if ( hmxMovieDto.getVersion() != null && hmxMovieDto.getVersion() != 0 ) {
			where.andVersionEqualTo( hmxMovieDto.getVersion() );
		}
  		if ( hmxMovieDto.getCreateid() != null && hmxMovieDto.getCreateid() != 0 ) {
			where.andCreateidEqualTo( hmxMovieDto.getCreateid() );
		}
		if ( !StringUtils.isEmpty( hmxMovieDto.getVideoId() ) ) {
			where.andVideoIdEqualTo(hmxMovieDto.getVideoId());
		}
		if ( hmxMovieDto.getVideoStatus() != null && hmxMovieDto.getVideoStatus() != 0 ) {
			where.andVideoStatusEqualTo(hmxMovieDto.getVideoStatus());
		}
		
		Integer count = hmxMovieMapper.countByExample( hmxMovieExample );
			
		boolean haveData = page.setTotalNum((int)(long)count);
			
		if(!haveData){
			return page;
		}
			
		List<HmxMovie> data = hmxMovieMapper.selectByExample( hmxMovieExample );
		
		page.setPage(data);
		
		return page;
	}
	
	
	/**
	 * @Method: list 
	 * @Description: 查询某个条件下的所有数据
	 * @param hmxMovieDto 查询参数
	 * @return List<HmxMovie> 符合条件的list集合
	 */
	public List<HmxMovie> list( HmxMovieDto hmxMovieDto ) {
	
		HmxMovieExample hmxMovieExample = new HmxMovieExample();
		
		Criteria where = hmxMovieExample.createCriteria();
		
  		if ( hmxMovieDto.getMovieId() != null && hmxMovieDto.getMovieId() != 0 ) {
			where.andMovieIdEqualTo( hmxMovieDto.getMovieId() );
		}
  		if ( !StringUtils.isEmpty( hmxMovieDto.getMovieName() ) ) {
			where.andMovieNameEqualTo( hmxMovieDto.getMovieName() );
		}
  		if ( !StringUtils.isEmpty( hmxMovieDto.getRatio() ) ) {
			where.andRatioEqualTo( hmxMovieDto.getRatio() );
		}
  		if ( !StringUtils.isEmpty( hmxMovieDto.getDuration() ) ) {
			where.andDurationEqualTo( hmxMovieDto.getDuration() );
		}
  		if ( !StringUtils.isEmpty( hmxMovieDto.getMovieUrl() ) ) {
			where.andMovieUrlEqualTo( hmxMovieDto.getMovieUrl() );
		}
  		if ( hmxMovieDto.getCreateTime() != null ) {
  			where.andCreateTimeEqualTo( hmxMovieDto.getCreateTime() );
  		}
  		if ( hmxMovieDto.getNewTime() != null ) {
  			where.andNewTimeEqualTo( hmxMovieDto.getNewTime() );
  		}
  		if ( hmxMovieDto.getState() != null && hmxMovieDto.getState() != 0 ) {
			where.andStateEqualTo( hmxMovieDto.getState() );
		}
  		if ( hmxMovieDto.getVersion() != null && hmxMovieDto.getVersion() != 0 ) {
			where.andVersionEqualTo( hmxMovieDto.getVersion() );
		}
  		if ( hmxMovieDto.getCreateid() != null && hmxMovieDto.getCreateid() != 0 ) {
			where.andCreateidEqualTo( hmxMovieDto.getCreateid() );
		}
		
		if( hmxMovieDto.getLimit() != null ){
			hmxMovieExample.setLimit( hmxMovieDto.getLimit() );
		}
		if( !StringUtils.isEmpty( hmxMovieDto.getOrderByClause() ) ){
			hmxMovieExample.setOrderByClause( hmxMovieDto.getOrderByClause() );
		}
		return hmxMovieMapper.selectByExample(hmxMovieExample);
	}
	
}
 
 