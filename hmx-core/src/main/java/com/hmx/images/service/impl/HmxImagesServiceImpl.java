package com.hmx.images.service.impl;

import java.util.List;
import java.util.ArrayList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import com.hmx.images.dao.HmxImagesMapper;
import com.hmx.images.entity.HmxImages;
import com.hmx.images.entity.HmxImagesExample;
import com.hmx.images.entity.HmxImagesExample.Criteria;
import com.hmx.images.service.HmxImagesService;
import com.hmx.images.dto.HmxImagesDto;
import com.hmx.utils.result.PageBean;


/**
 * Service implementation class
 *
 */
 @Service
 public class HmxImagesServiceImpl implements HmxImagesService{
 
 	@Autowired
	private HmxImagesMapper hmxImagesMapper;
	
	
	/**
	 * @Method: insert 
	 * @Description: 添加
	 * @param hmxImages 要添加的对象
	 * @return 
	 */
	@Override
	public Boolean insert( HmxImages hmxImages ) {
		return hmxImagesMapper.insertSelective( hmxImages ) > 0;
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
			HmxImagesExample hmxImagesExample = new HmxImagesExample();
			hmxImagesExample.or().andImageIdIn( idArray );
			
			int ret = hmxImagesMapper.deleteByExample( hmxImagesExample );
			return ret > 0;
		}catch( Exception e ){
			e.printStackTrace();
		}
		return false;
	}
	
	/**
	 * @Method: update 
	 * @Description: 修改
	 * @param hmxImages 要修改的对象
	 * @return true 修改成功  false 修改失败
	 */
	@Override
	public Boolean update(HmxImages hmxImages) {
		return hmxImagesMapper.updateByPrimaryKeySelective( hmxImages ) > 0;
	}
	
	
	/**
	 * @Method: info 
	 * @Description: 根据自增主键查询对象信息
	 * @param hmxImages 根据自增对象查询信息
	 * @return HmxImages 查询的对象
	 */
	public HmxImages info (Integer hmxImagesId) {
		return hmxImagesMapper.selectByPrimaryKey( hmxImagesId );
	}
	
	/**
	 * @Method: getPage 
	 * @Description: 分页查询
	 * @param page 分页参数
	 * @param hmxImagesDto 查询条件
	 * @return PageBean<HmxImages> 查询到的分页值
	 */
	public PageBean<HmxImages> getPage(PageBean<HmxImages> page,HmxImagesDto hmxImagesDto) {
		
		HmxImagesExample hmxImagesExample = new HmxImagesExample();
		
		hmxImagesExample.setOffset(page.getStartOfPage());
		hmxImagesExample.setLimit(page.getPageSize());
		
		Criteria where = hmxImagesExample.createCriteria();
		
  		if ( hmxImagesDto.getImageId() != null && hmxImagesDto.getImageId() != 0 ) {
			where.andImageIdEqualTo( hmxImagesDto.getImageId() );
		}
  		if ( StringUtils.isEmpty( hmxImagesDto.getImageUrl() ) ) {
			where.andImageUrlEqualTo( hmxImagesDto.getImageUrl() );
		}
  		if ( hmxImagesDto.getCreateTime() != null ) {
  			where.andCreateTimeEqualTo( hmxImagesDto.getCreateTime() );
  		}
  		if ( hmxImagesDto.getNewTime() != null ) {
  			where.andNewTimeEqualTo( hmxImagesDto.getNewTime() );
  		}
  		if ( hmxImagesDto.getState() != null && hmxImagesDto.getState() != 0 ) {
			where.andStateEqualTo( hmxImagesDto.getState() );
		}
  		if ( hmxImagesDto.getVersion() != null && hmxImagesDto.getVersion() != 0 ) {
			where.andVersionEqualTo( hmxImagesDto.getVersion() );
		}
  		if ( hmxImagesDto.getCreateid() != null && hmxImagesDto.getCreateid() != 0 ) {
			where.andCreateidEqualTo( hmxImagesDto.getCreateid() );
		}
		
		Integer count = hmxImagesMapper.countByExample( hmxImagesExample );
			
		boolean haveData = page.setTotalNum((int)(long)count);
			
		if(!haveData){
			return page;
		}
			
		List<HmxImages> data = hmxImagesMapper.selectByExample( hmxImagesExample );
		
		page.setPage(data);
		
		return page;
	}
	
	
	/**
	 * @Method: list 
	 * @Description: 查询某个条件下的所有数据
	 * @param hmxImagesDto 查询参数
	 * @return List<HmxImages> 符合条件的list集合
	 */
	public List<HmxImages> list( HmxImagesDto hmxImagesDto ) {
	
		HmxImagesExample hmxImagesExample = new HmxImagesExample();
		
		Criteria where = hmxImagesExample.createCriteria();
		
  		if ( hmxImagesDto.getImageId() != null && hmxImagesDto.getImageId() != 0 ) {
			where.andImageIdEqualTo( hmxImagesDto.getImageId() );
		}
  		if ( !StringUtils.isEmpty( hmxImagesDto.getImageUrl() ) ) {
			where.andImageUrlEqualTo( hmxImagesDto.getImageUrl() );
		}
  		if ( hmxImagesDto.getCreateTime() != null ) {
  			where.andCreateTimeEqualTo( hmxImagesDto.getCreateTime() );
  		}
  		if ( hmxImagesDto.getNewTime() != null ) {
  			where.andNewTimeEqualTo( hmxImagesDto.getNewTime() );
  		}
  		if ( hmxImagesDto.getState() != null && hmxImagesDto.getState() != 0 ) {
			where.andStateEqualTo( hmxImagesDto.getState() );
		}
  		if ( hmxImagesDto.getVersion() != null && hmxImagesDto.getVersion() != 0 ) {
			where.andVersionEqualTo( hmxImagesDto.getVersion() );
		}
  		if ( hmxImagesDto.getCreateid() != null && hmxImagesDto.getCreateid() != 0 ) {
			where.andCreateidEqualTo( hmxImagesDto.getCreateid() );
		}
		
		if( hmxImagesDto.getLimit() != null ){
			hmxImagesExample.setLimit( hmxImagesDto.getLimit() );
		}
		if( !StringUtils.isEmpty( hmxImagesDto.getOrderByClause() ) ){
			hmxImagesExample.setOrderByClause( hmxImagesDto.getOrderByClause() );
		}
		return hmxImagesMapper.selectByExample(hmxImagesExample);
	}
	
}
 
 