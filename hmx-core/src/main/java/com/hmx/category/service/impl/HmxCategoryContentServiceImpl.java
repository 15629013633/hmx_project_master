package com.hmx.category.service.impl;

import java.util.List;
import java.util.Map;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;


import com.hmx.category.service.HmxCategoryContentService;
import com.hmx.utils.enums.DataState;
import com.hmx.utils.result.Config;
import com.hmx.utils.result.PageBean;
import com.hmx.utils.result.ResultBean;
import com.hmx.utils.upload.InitVodClients;
import com.hmx.category.entity.HmxCategoryContent;
import com.hmx.category.dto.HmxCategoryContentDto;
import com.hmx.category.entity.HmxCategoryContentExample;
import com.hmx.category.entity.HmxCategoryContentExample.Criteria;
import com.hmx.category.dao.HmxCategoryContentMapper;
/**
 * Service implementation class
 *
 */
 @Service
 public class HmxCategoryContentServiceImpl implements HmxCategoryContentService{
 
 	@Autowired
	private HmxCategoryContentMapper hmxCategoryContentMapper;
 	@Autowired
	private InitVodClients initVodClients;
	
	
	/**
	 * @Method: insert 
	 * @Description: 添加
	 * @param hmxCategoryContent 要添加的对象
	 * @return 
	 */
	@Override
	public Boolean insert( HmxCategoryContent hmxCategoryContent ) {
		return hmxCategoryContentMapper.insertSelective( hmxCategoryContent ) > 0;
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
			HmxCategoryContentExample hmxCategoryContentExample = new HmxCategoryContentExample();
			hmxCategoryContentExample.or().andCategoryContentIdIn( idArray );
			
			int ret = hmxCategoryContentMapper.deleteByExample( hmxCategoryContentExample );
			return ret > 0;
		}catch( Exception e ){
			e.printStackTrace();
		}
		return false;
	}
	
	/**
	 * @Method: update 
	 * @Description: 修改
	 * @param hmxCategoryContent 要修改的对象
	 * @return true 修改成功  false 修改失败
	 */
	@Override
	public Boolean update(HmxCategoryContent hmxCategoryContent) {
		return hmxCategoryContentMapper.updateByPrimaryKeySelective( hmxCategoryContent ) > 0;
	}
	
	
	/**
	 * @Method: info 
	 * @Description: 根据自增主键查询对象信息
	 * @param hmxCategoryContent 根据自增对象查询信息
	 * @return HmxCategoryContent 查询的对象
	 */
	public HmxCategoryContent info (Integer hmxCategoryContentId) {
		return hmxCategoryContentMapper.selectByPrimaryKey( hmxCategoryContentId );
	}
	
	/**
	 * @Method: getPage 
	 * @Description: 分页查询
	 * @param page 分页参数
	 * @param hmxCategoryContentDto 查询条件
	 * @return PageBean<HmxCategoryContent> 查询到的分页值
	 */
	public PageBean<HmxCategoryContent> getPage(PageBean<HmxCategoryContent> page,HmxCategoryContentDto hmxCategoryContentDto) {
		
		HmxCategoryContentExample hmxCategoryContentExample = new HmxCategoryContentExample();
		
		hmxCategoryContentExample.setOffset(page.getStartOfPage());
		hmxCategoryContentExample.setLimit(page.getPageSize());
		
		Criteria where = hmxCategoryContentExample.createCriteria();
		
  		if ( hmxCategoryContentDto.getCategoryContentId() != null && hmxCategoryContentDto.getCategoryContentId() != 0 ) {
			where.andCategoryContentIdEqualTo( hmxCategoryContentDto.getCategoryContentId() );
		}
  		if ( hmxCategoryContentDto.getCategoryId() != null && hmxCategoryContentDto.getCategoryId() != 0 ) {
			where.andCategoryIdEqualTo( hmxCategoryContentDto.getCategoryId() );
		}
  		if ( !StringUtils.isEmpty( hmxCategoryContentDto.getCategoryTitle() ) ) {
			where.andCategoryTitleEqualTo( hmxCategoryContentDto.getCategoryTitle() );
		}
  		if ( !StringUtils.isEmpty( hmxCategoryContentDto.getCategoryContent() ) ) {
			where.andCategoryContentEqualTo( hmxCategoryContentDto.getCategoryContent() );
		}
  		if ( hmxCategoryContentDto.getContentType() != null && hmxCategoryContentDto.getContentType() != 0 ) {
			where.andContentTypeEqualTo( hmxCategoryContentDto.getContentType() );
		}
  		if ( !StringUtils.isEmpty( hmxCategoryContentDto.getContentImages() ) ) {
			where.andContentImagesEqualTo( hmxCategoryContentDto.getContentImages() );
		}
  		if ( hmxCategoryContentDto.getMovieId() != null && hmxCategoryContentDto.getMovieId() != 0 ) {
			where.andMovieIdEqualTo( hmxCategoryContentDto.getMovieId() );
		}
  		if ( hmxCategoryContentDto.getMusicId() != null && hmxCategoryContentDto.getMusicId() != 0 ) {
			where.andMusicIdEqualTo( hmxCategoryContentDto.getMusicId() );
		}
  		if ( hmxCategoryContentDto.getBrowseNum() != null && hmxCategoryContentDto.getBrowseNum() != 0 ) {
			where.andBrowseNumEqualTo( hmxCategoryContentDto.getBrowseNum() );
		}
  		if ( hmxCategoryContentDto.getCreateTime() != null ) {
  			where.andCreateTimeEqualTo( hmxCategoryContentDto.getCreateTime() );
  		}
  		if ( hmxCategoryContentDto.getNewTime() != null ) {
  			where.andNewTimeEqualTo( hmxCategoryContentDto.getNewTime() );
  		}
  		if ( hmxCategoryContentDto.getState() != null && hmxCategoryContentDto.getState() != 0 ) {
			where.andStateEqualTo( hmxCategoryContentDto.getState() );
		}
  		if ( hmxCategoryContentDto.getVersion() != null && hmxCategoryContentDto.getVersion() != 0 ) {
			where.andVersionEqualTo( hmxCategoryContentDto.getVersion() );
		}
  		if ( hmxCategoryContentDto.getCreateid() != null && hmxCategoryContentDto.getCreateid() != 0 ) {
			where.andCreateidEqualTo( hmxCategoryContentDto.getCreateid() );
		}
		
		Integer count = hmxCategoryContentMapper.countByExample( hmxCategoryContentExample );
			
		boolean haveData = page.setTotalNum((int)(long)count);
			
		if(!haveData){
			return page;
		}
			
		List<HmxCategoryContent> data = hmxCategoryContentMapper.selectByExample( hmxCategoryContentExample );
		
		page.setPage(data);
		
		return page;
	}
	
	
	/**
	 * @Method: list 
	 * @Description: 查询某个条件下的所有数据
	 * @param hmxCategoryContentDto 查询参数
	 * @return List<HmxCategoryContent> 符合条件的list集合
	 */
	public List<HmxCategoryContent> list( HmxCategoryContentDto hmxCategoryContentDto ) {
	
		HmxCategoryContentExample hmxCategoryContentExample = new HmxCategoryContentExample();
		
		Criteria where = hmxCategoryContentExample.createCriteria();
		
  		if ( hmxCategoryContentDto.getCategoryContentId() != null && hmxCategoryContentDto.getCategoryContentId() != 0 ) {
			where.andCategoryContentIdEqualTo( hmxCategoryContentDto.getCategoryContentId() );
		}
  		if ( hmxCategoryContentDto.getCategoryId() != null && hmxCategoryContentDto.getCategoryId() != 0 ) {
			where.andCategoryIdEqualTo( hmxCategoryContentDto.getCategoryId() );
		}
  		if ( !StringUtils.isEmpty( hmxCategoryContentDto.getCategoryTitle() ) ) {
			where.andCategoryTitleEqualTo( hmxCategoryContentDto.getCategoryTitle() );
		}
  		if ( !StringUtils.isEmpty( hmxCategoryContentDto.getCategoryContent() ) ) {
			where.andCategoryContentEqualTo( hmxCategoryContentDto.getCategoryContent() );
		}
  		if ( hmxCategoryContentDto.getContentType() != null && hmxCategoryContentDto.getContentType() != 0 ) {
			where.andContentTypeEqualTo( hmxCategoryContentDto.getContentType() );
		}
  		if ( !StringUtils.isEmpty( hmxCategoryContentDto.getContentImages() ) ) {
			where.andContentImagesEqualTo( hmxCategoryContentDto.getContentImages() );
		}
  		if ( hmxCategoryContentDto.getMovieId() != null && hmxCategoryContentDto.getMovieId() != 0 ) {
			where.andMovieIdEqualTo( hmxCategoryContentDto.getMovieId() );
		}
  		if ( hmxCategoryContentDto.getMusicId() != null && hmxCategoryContentDto.getMusicId() != 0 ) {
			where.andMusicIdEqualTo( hmxCategoryContentDto.getMusicId() );
		}
  		if ( hmxCategoryContentDto.getBrowseNum() != null && hmxCategoryContentDto.getBrowseNum() != 0 ) {
			where.andBrowseNumEqualTo( hmxCategoryContentDto.getBrowseNum() );
		}
  		if ( hmxCategoryContentDto.getCreateTime() != null ) {
  			where.andCreateTimeEqualTo( hmxCategoryContentDto.getCreateTime() );
  		}
  		if ( hmxCategoryContentDto.getNewTime() != null ) {
  			where.andNewTimeEqualTo( hmxCategoryContentDto.getNewTime() );
  		}
  		if ( hmxCategoryContentDto.getState() != null && hmxCategoryContentDto.getState() != 0 ) {
			where.andStateEqualTo( hmxCategoryContentDto.getState() );
		}
  		if ( hmxCategoryContentDto.getVersion() != null && hmxCategoryContentDto.getVersion() != 0 ) {
			where.andVersionEqualTo( hmxCategoryContentDto.getVersion() );
		}
  		if ( hmxCategoryContentDto.getCreateid() != null && hmxCategoryContentDto.getCreateid() != 0 ) {
			where.andCreateidEqualTo( hmxCategoryContentDto.getCreateid() );
		}
		
		if( hmxCategoryContentDto.getLimit() != null ){
			hmxCategoryContentExample.setLimit( hmxCategoryContentDto.getLimit() );
		}
		if( !StringUtils.isEmpty( hmxCategoryContentDto.getOrderByClause() ) ){
			hmxCategoryContentExample.setOrderByClause( hmxCategoryContentDto.getOrderByClause() );
		}
		return hmxCategoryContentMapper.selectByExample(hmxCategoryContentExample);
	}
	/**
	 * 内容详情添加
	 * @param hmxCategoryContentDto
	 * @return
	 */
	@Transactional
	public Map<String,Object> categoryContentAdd(HmxCategoryContentDto hmxCategoryContentDto){
		Map<String,Object> resultMap = new HashMap<String,Object>();
		resultMap.put("flag", false);
		try {
			Date date = new Date();
			hmxCategoryContentDto.setCreateTime(date);
			hmxCategoryContentDto.setNewTime(date);
			if(!insert(hmxCategoryContentDto)){
				resultMap.put("content", "添加内容失败");
	    		return resultMap;
			}
			resultMap.put("flag", true);
    		resultMap.put("content", "添加内容成功");
    		return resultMap;
		} catch (Exception e) {
			e.printStackTrace();
			resultMap.put("content", "添加内容失败");
			return resultMap;
		}
	}
	
	/**
	 * 内容详情编辑
	 * @param hmxCategoryContentDto
	 * @return
	 */
	@Transactional
	public Map<String,Object> categoryContentUpdate(HmxCategoryContentDto hmxCategoryContentDto){
		Map<String,Object> resultMap = new HashMap<String,Object>();
		resultMap.put("flag", false);
		try {
			hmxCategoryContentDto.setNewTime(new Date());
			if(!update(hmxCategoryContentDto)){
				resultMap.put("content", "编辑内容失败");
	    		return resultMap;
			}
			resultMap.put("flag", true);
    		resultMap.put("content", "编辑内容成功");
    		return resultMap;
		} catch (Exception e) {
			e.printStackTrace();
			resultMap.put("content", "编辑内容失败");
			return resultMap;
		}
	}
	/**
     * 查询内容详情
     * @param categoryContentId
     * @return
     */
    public HmxCategoryContent selectCategoryContentById(Integer categoryContentId){
    	return hmxCategoryContentMapper.selectCategoryContentById(categoryContentId);
    }
    /**
     * 内容列表查询
     * @return
     */
    public PageBean<Map<String,Object>> selectCategoryContentTable(PageBean<Map<String,Object>> page,HmxCategoryContentDto hmxCategoryContentDto){
    	Map<String,Object> parameter = new HashMap<String,Object>();
    	parameter.put("offset", page.getStartOfPage());
    	parameter.put("limit", page.getPageSize());
    	parameter.put("state", DataState.正常.getState());
    	if(!StringUtils.isEmpty(hmxCategoryContentDto.getCategoryTitle())){
    		parameter.put("categoryTitle", hmxCategoryContentDto.getCategoryTitle());
    	}
    	if(hmxCategoryContentDto.getBeginDate() != null){
    		parameter.put("beginDate", hmxCategoryContentDto.getBeginDate());
    	}
    	if(hmxCategoryContentDto.getEndDate() != null){
    		parameter.put("endDate", hmxCategoryContentDto.getEndDate());
    	}
    	if(hmxCategoryContentDto.getCategoryId() != null){
    		parameter.put("categoryId", hmxCategoryContentDto.getCategoryId());
    	}
    	Integer count = hmxCategoryContentMapper.countCategoryContentTable(parameter);
	    Boolean haveData = page.setTotalNum((int)(long)count);
	    if(!haveData){
			return page;
		}
	    List<Map<String,Object>> data = hmxCategoryContentMapper.selectCategoryContentTable(parameter);
	    page.setPage(data);
    	return page;
    }
    /**
     * 内容信息详情查询
     * 更新内容浏览量+1
     * @param categoryContentId
     * @return
     */
    public Map<String,Object> selectContentInfoByContentId(Integer categoryContentId){
    	Map<String,Object> resultMap = hmxCategoryContentMapper.selectContentInfoByContentId(categoryContentId);
    	resultMap.put("videoUrl", null);
    	if(resultMap != null){
    		HmxCategoryContent hmxCategoryContent = new HmxCategoryContent();
    		hmxCategoryContent.setCategoryContentId(categoryContentId);
    		hmxCategoryContent.setBrowseNum(Integer.parseInt(resultMap.get("browseNum").toString()));
    		update(hmxCategoryContent);
    		if(resultMap.get("videoId") != null){
    			Map<String,Object> resultUrl = initVodClients.getUrl(resultMap.get("videoId").toString());
    			boolean flag = Boolean.parseBoolean(resultUrl.get("flag").toString());
    			if(flag){
    				resultMap.put("videoUrl", resultUrl.get("url"));
    			}
    		}
    	}
    	return resultMap;
    }
    /**
     * Pc内容列表查询
     * @return
     */
    public PageBean<Map<String,Object>> selectCategoryContentTableByPc(PageBean<Map<String,Object>> page,HmxCategoryContentDto hmxCategoryContentDto){
    	Map<String,Object> parameter = new HashMap<String,Object>();
    	parameter.put("offset", page.getStartOfPage());
    	parameter.put("limit", page.getPageSize());
    	parameter.put("state", DataState.正常.getState());
    	if(!StringUtils.isEmpty(hmxCategoryContentDto.getCategoryTitle())){
    		parameter.put("categoryTitle", hmxCategoryContentDto.getCategoryTitle());
    	}
    	if(hmxCategoryContentDto.getBeginDate() != null){
    		parameter.put("beginDate", hmxCategoryContentDto.getBeginDate());
    	}
    	if(hmxCategoryContentDto.getEndDate() != null){
    		parameter.put("endDate", hmxCategoryContentDto.getEndDate());
    	}
    	if(hmxCategoryContentDto.getCategoryId() != null){
    		parameter.put("categoryId", hmxCategoryContentDto.getCategoryId());
    	}
    	Integer count = hmxCategoryContentMapper.countCategoryContentTableByPc(parameter);
	    Boolean haveData = page.setTotalNum((int)(long)count);
	    if(!haveData){
			return page;
		}
	    List<Map<String,Object>> data = hmxCategoryContentMapper.selectCategoryContentTableByPc(parameter);
	    page.setPage(data);
    	return page;
    }
    
    /**
     * 查看排行榜信息
     * @param categoryId
     * @return
     */
    public Map<String,Object>selectRankingListByCategoryId(Integer categoryId){
    	return hmxCategoryContentMapper.selectRankingListByCategoryId(categoryId);
    }
}
 
 