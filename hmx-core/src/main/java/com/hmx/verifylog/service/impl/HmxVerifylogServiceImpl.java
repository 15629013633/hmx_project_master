package com.hmx.verifylog.service.impl;

import java.util.List;
import java.util.ArrayList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;


import com.hmx.verifylog.service.HmxVerifylogService;
import com.hmx.verifylog.entity.HmxVerifylog;
import com.hmx.verifylog.dto.HmxVerifylogDto;
import com.hmx.verifylog.entity.HmxVerifylogExample;
import com.hmx.verifylog.entity.HmxVerifylogExample.Criteria;
import com.hmx.utils.result.PageBean;
import com.hmx.verifylog.dao.HmxVerifylogMapper;
/**
 * Service implementation class
 *
 */
 @Service
 public class HmxVerifylogServiceImpl implements HmxVerifylogService{
 
 	@Autowired
	private HmxVerifylogMapper hmxVerifylogMapper;
	
	
	/**
	 * @Method: insert 
	 * @Description: 添加
	 * @param hmxVerifylog 要添加的对象
	 * @return 
	 */
	@Override
	public Boolean insert( HmxVerifylog hmxVerifylog ) {
		return hmxVerifylogMapper.insertSelective( hmxVerifylog ) > 0;
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
			HmxVerifylogExample hmxVerifylogExample = new HmxVerifylogExample();
			hmxVerifylogExample.or().andVerifyLogIdIn( idArray );
			
			int ret = hmxVerifylogMapper.deleteByExample( hmxVerifylogExample );
			return ret > 0;
		}catch( Exception e ){
			e.printStackTrace();
		}
		return false;
	}
	
	/**
	 * @Method: update 
	 * @Description: 修改
	 * @param hmxVerifylog 要修改的对象
	 * @return true 修改成功  false 修改失败
	 */
	@Override
	public Boolean update(HmxVerifylog hmxVerifylog) {
		return hmxVerifylogMapper.updateByPrimaryKeySelective( hmxVerifylog ) > 0;
	}
	
	
	/**
	 * @Method: info 
	 * @Description: 根据自增主键查询对象信息
	 * @param hmxVerifylog 根据自增对象查询信息
	 * @return HmxVerifylog 查询的对象
	 */
	public HmxVerifylog info (Integer hmxVerifylogId) {
		return hmxVerifylogMapper.selectByPrimaryKey( hmxVerifylogId );
	}
	
	/**
	 * @Method: getPage 
	 * @Description: 分页查询
	 * @param page 分页参数
	 * @param hmxVerifylogDto 查询条件
	 * @return PageBean<HmxVerifylog> 查询到的分页值
	 */
	public PageBean<HmxVerifylog> getPage(PageBean<HmxVerifylog> page,HmxVerifylogDto hmxVerifylogDto) {
		
		HmxVerifylogExample hmxVerifylogExample = new HmxVerifylogExample();
		
		hmxVerifylogExample.setOffset(page.getStartOfPage());
		hmxVerifylogExample.setLimit(page.getPageSize());
		
		Criteria where = hmxVerifylogExample.createCriteria();
		
  		if ( hmxVerifylogDto.getVerifyLogId() != null && hmxVerifylogDto.getVerifyLogId() != 0 ) {
			where.andVerifyLogIdEqualTo( hmxVerifylogDto.getVerifyLogId() );
		}
  		if ( hmxVerifylogDto.getVerifyType() != null && hmxVerifylogDto.getVerifyType() != 0 ) {
			where.andVerifyTypeEqualTo( hmxVerifylogDto.getVerifyType() );
		}
  		if ( StringUtils.isEmpty( hmxVerifylogDto.getVerifyCode() ) ) {
			where.andVerifyCodeEqualTo( hmxVerifylogDto.getVerifyCode() );
		}
  		if ( StringUtils.isEmpty( hmxVerifylogDto.getVerifyObject() ) ) {
			where.andVerifyObjectEqualTo( hmxVerifylogDto.getVerifyObject() );
		}
  		if ( hmxVerifylogDto.getAddTime() != null ) {
  			where.andAddTimeEqualTo( hmxVerifylogDto.getAddTime() );
  		}
  		if ( hmxVerifylogDto.getIsVerify() != null && hmxVerifylogDto.getIsVerify() != 0 ) {
			where.andIsVerifyEqualTo( hmxVerifylogDto.getIsVerify() );
		}
		
		Integer count = hmxVerifylogMapper.countByExample( hmxVerifylogExample );
			
		boolean haveData = page.setTotalNum((int)(long)count);
			
		if(!haveData){
			return page;
		}
			
		List<HmxVerifylog> data = hmxVerifylogMapper.selectByExample( hmxVerifylogExample );
		
		page.setPage(data);
		
		return page;
	}
	
	
	/**
	 * @Method: list 
	 * @Description: 查询某个条件下的所有数据
	 * @param hmxVerifylogDto 查询参数
	 * @return List<HmxVerifylog> 符合条件的list集合
	 */
	public List<HmxVerifylog> list( HmxVerifylogDto hmxVerifylogDto ) {
	
		HmxVerifylogExample hmxVerifylogExample = new HmxVerifylogExample();
		
		Criteria where = hmxVerifylogExample.createCriteria();
		
  		if ( hmxVerifylogDto.getVerifyLogId() != null && hmxVerifylogDto.getVerifyLogId() != 0 ) {
			where.andVerifyLogIdEqualTo( hmxVerifylogDto.getVerifyLogId() );
		}
  		if ( hmxVerifylogDto.getVerifyType() != null && hmxVerifylogDto.getVerifyType() != 0 ) {
			where.andVerifyTypeEqualTo( hmxVerifylogDto.getVerifyType() );
		}
  		if ( !StringUtils.isEmpty( hmxVerifylogDto.getVerifyCode() ) ) {
			where.andVerifyCodeEqualTo( hmxVerifylogDto.getVerifyCode() );
		}
  		if ( !StringUtils.isEmpty( hmxVerifylogDto.getVerifyObject() ) ) {
			where.andVerifyObjectEqualTo( hmxVerifylogDto.getVerifyObject() );
		}
  		if ( hmxVerifylogDto.getAddTime() != null ) {
  			where.andAddTimeEqualTo( hmxVerifylogDto.getAddTime() );
  		}
  		if ( hmxVerifylogDto.getIsVerify() != null && hmxVerifylogDto.getIsVerify() != 0 ) {
			where.andIsVerifyEqualTo( hmxVerifylogDto.getIsVerify() );
		}
		
		if( hmxVerifylogDto.getLimit() != null ){
			hmxVerifylogExample.setLimit( hmxVerifylogDto.getLimit() );
		}
		if( !StringUtils.isEmpty( hmxVerifylogDto.getOrderByClause() ) ){
			hmxVerifylogExample.setOrderByClause( hmxVerifylogDto.getOrderByClause() );
		}
		return hmxVerifylogMapper.selectByExample(hmxVerifylogExample);
	}
	/**
     * 查询用户最新一条验证码记录
     * @param verifyObject
     * @return
     */
    public HmxVerifylog selectNewVerifylog(String verifyObject){
    	return hmxVerifylogMapper.selectNewVerifylog(verifyObject);
    }
}
 
 