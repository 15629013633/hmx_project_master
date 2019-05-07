package com.hmx.category.service.impl;

import java.util.List;
import java.util.Map;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;

import com.hmx.category.dao.HmxCategoryContentMapper;
import com.hmx.category.entity.HmxCategoryContent;
import com.hmx.category.entity.HmxCategoryContentExample;
import com.hmx.user.entity.HmxUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import com.hmx.category.service.HmxCategoryService;
import com.hmx.utils.enums.DataState;
import com.hmx.utils.result.PageBean;
import com.hmx.category.entity.HmxCategory;
import com.hmx.category.dto.HmxCategoryDto;
import com.hmx.category.entity.HmxCategoryExample;
import com.hmx.category.entity.HmxCategoryExample.Criteria;
import com.hmx.category.dao.HmxCategoryMapper;

import javax.servlet.http.HttpServletRequest;

/**
 * Service implementation class
 *
 */
 @Service
 public class HmxCategoryServiceImpl implements HmxCategoryService{
 
 	@Autowired
	private HmxCategoryMapper hmxCategoryMapper;

	@Autowired
	private HmxCategoryContentMapper hmxCategoryContentMapper;
	
	
	/**
	 * @Method: insert 
	 * @Description: 添加
	 * @param hmxCategory 要添加的对象
	 * @return 
	 */
	@Override
	public Boolean insert( HmxCategory hmxCategory ) {
		return hmxCategoryMapper.insertSelective( hmxCategory ) > 0;
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
			HmxCategoryExample hmxCategoryExample = new HmxCategoryExample();
			hmxCategoryExample.or().andCategoryIdIn( idArray );
			
			int ret = hmxCategoryMapper.deleteByExample( hmxCategoryExample );
			return ret > 0;
		}catch( Exception e ){
			e.printStackTrace();
		}
		return false;
	}
	
	/**
	 * @Method: update 
	 * @Description: 修改
	 * @param hmxCategory 要修改的对象
	 * @return true 修改成功  false 修改失败
	 */
	@Override
	public Boolean update(HmxCategory hmxCategory) {
		return hmxCategoryMapper.updateByPrimaryKeySelective( hmxCategory ) > 0;
	}
	
	
	/**
	 * @Method: info 
	 * @Description: 根据自增主键查询对象信息
	 * @param hmxCategoryId 根据自增对象查询信息
	 * @return HmxCategory 查询的对象
	 */
	public HmxCategory info (Integer hmxCategoryId) {
		return hmxCategoryMapper.selectByPrimaryKey( hmxCategoryId );
	}
	
	/**
	 * @Method: getPage 
	 * @Description: 分页查询
	 * @param page 分页参数
	 * @param hmxCategoryDto 查询条件
	 * @return PageBean<HmxCategory> 查询到的分页值
	 */
	public PageBean<HmxCategory> getPage(PageBean<HmxCategory> page,HmxCategoryDto hmxCategoryDto) {
		
		HmxCategoryExample hmxCategoryExample = new HmxCategoryExample();
		
		hmxCategoryExample.setOffset(page.getStartOfPage());
		hmxCategoryExample.setLimit(page.getPageSize());
		
		Criteria where = hmxCategoryExample.createCriteria();
		
  		if ( hmxCategoryDto.getCategoryId() != null && hmxCategoryDto.getCategoryId() != 0 ) {
			where.andCategoryIdEqualTo( hmxCategoryDto.getCategoryId() );
		}
  		if ( StringUtils.isEmpty( hmxCategoryDto.getCategoryName() ) ) {
			where.andCategoryNameEqualTo( hmxCategoryDto.getCategoryName() );
		}
  		if ( hmxCategoryDto.getCategoryType() != null && hmxCategoryDto.getCategoryType() != 0 ) {
			where.andCategoryTypeEqualTo( hmxCategoryDto.getCategoryType() );
		}
  		if ( hmxCategoryDto.getSort() != null && hmxCategoryDto.getSort() != 0 ) {
			where.andSortEqualTo( hmxCategoryDto.getSort() );
		}
  		if ( hmxCategoryDto.getIsClose() != null && hmxCategoryDto.getIsClose() != 0 ) {
			where.andIsCloseEqualTo( hmxCategoryDto.getIsClose() );
		}
  		if ( hmxCategoryDto.getCreateTime() != null ) {
  			where.andCreateTimeEqualTo( hmxCategoryDto.getCreateTime() );
  		}
  		if ( hmxCategoryDto.getNewTime() != null ) {
  			where.andNewTimeEqualTo( hmxCategoryDto.getNewTime() );
  		}
  		if ( hmxCategoryDto.getState() != null && hmxCategoryDto.getState() != 0 ) {
			where.andStateEqualTo( hmxCategoryDto.getState() );
		}
  		if ( hmxCategoryDto.getVersion() != null && hmxCategoryDto.getVersion() != 0 ) {
			where.andVersionEqualTo( hmxCategoryDto.getVersion() );
		}
  		if ( hmxCategoryDto.getCreateid() != null && hmxCategoryDto.getCreateid() != 0 ) {
			where.andCreateidEqualTo( hmxCategoryDto.getCreateid() );
		}
		
		Integer count = hmxCategoryMapper.countByExample( hmxCategoryExample );
			
		boolean haveData = page.setTotalNum((int)(long)count);
			
		if(!haveData){
			return page;
		}
			
		List<HmxCategory> data = hmxCategoryMapper.selectByExample( hmxCategoryExample );
		
		page.setPage(data);
		
		return page;
	}
	
	
	/**
	 * @Method: list 
	 * @Description: 查询某个条件下的所有数据
	 * @param hmxCategoryDto 查询参数
	 * @return List<HmxCategory> 符合条件的list集合
	 */
	public List<HmxCategory> list( HmxCategoryDto hmxCategoryDto ) {
	
		HmxCategoryExample hmxCategoryExample = new HmxCategoryExample();
		
		Criteria where = hmxCategoryExample.createCriteria();
		
  		if ( hmxCategoryDto.getCategoryId() != null && hmxCategoryDto.getCategoryId() != 0 ) {
			where.andCategoryIdEqualTo( hmxCategoryDto.getCategoryId() );
		}
  		if ( !StringUtils.isEmpty( hmxCategoryDto.getCategoryName() ) ) {
			where.andCategoryNameEqualTo( hmxCategoryDto.getCategoryName() );
		}
  		if ( hmxCategoryDto.getCategoryType() != null && hmxCategoryDto.getCategoryType() != 0 ) {
			where.andCategoryTypeEqualTo( hmxCategoryDto.getCategoryType() );
		}
  		if ( hmxCategoryDto.getSort() != null && hmxCategoryDto.getSort() != 0 ) {
			where.andSortEqualTo( hmxCategoryDto.getSort() );
		}
  		if ( hmxCategoryDto.getIsClose() != null) {
			where.andIsCloseEqualTo( hmxCategoryDto.getIsClose() );
		}
  		if ( hmxCategoryDto.getCreateTime() != null ) {
  			where.andCreateTimeEqualTo( hmxCategoryDto.getCreateTime() );
  		}
  		if ( hmxCategoryDto.getNewTime() != null ) {
  			where.andNewTimeEqualTo( hmxCategoryDto.getNewTime() );
  		}
  		if ( hmxCategoryDto.getState() != null) {
			where.andStateEqualTo( hmxCategoryDto.getState() );
		}
  		if ( hmxCategoryDto.getVersion() != null && hmxCategoryDto.getVersion() != 0 ) {
			where.andVersionEqualTo( hmxCategoryDto.getVersion() );
		}
  		if ( hmxCategoryDto.getCreateid() != null && hmxCategoryDto.getCreateid() != 0 ) {
			where.andCreateidEqualTo( hmxCategoryDto.getCreateid() );
		}
		if(hmxCategoryDto.getParentId() != null && hmxCategoryDto.getParentId() != 0){//查询某个一级分类下的二级分类
			where.andParentIdEqualTo(hmxCategoryDto.getParentId());
		}else if(hmxCategoryDto.getParentId() != null && hmxCategoryDto.getParentId() == 0){//查询所有一级分类
			where.andParentIdEqualTo(hmxCategoryDto.getParentId());
		}

		if( hmxCategoryDto.getLimit() != null ){
			hmxCategoryExample.setLimit( hmxCategoryDto.getLimit() );
		}
		if( !StringUtils.isEmpty( hmxCategoryDto.getOrderByClause() ) ){
			hmxCategoryExample.setOrderByClause( hmxCategoryDto.getOrderByClause() );
		}
		return hmxCategoryMapper.selectByExample(hmxCategoryExample);
	}
	
	/**
     * 获取首页一级分类以及内容信息
     * @return
     */
    public List<Map<String,Object>> selectCategoryAndContentList(){
		List<Map<String,Object>> list = hmxCategoryMapper.selectCategoryAndContentList();
    	return list;
    }
    /**
     * 分类添加
     * @param hmxCategoryDto
     * @return
     */
    public Map<String,Object> categoryAdd(HmxCategoryDto hmxCategoryDto, HttpServletRequest request){
		//暂时先去掉
		//HmxUser userModelLogin = (HmxUser) request.getSession().getAttribute("userInfo");
    	Map<String,Object> resultMap = new HashMap<String,Object>();
    	resultMap.put("flag", false);
    	try {
    		HmxCategoryDto categoryDto = new HmxCategoryDto();
    		categoryDto.setCategoryName(hmxCategoryDto.getCategoryName());
    		categoryDto.setState(DataState.正常.getState());
    		if(selectIsCategoryName(categoryDto)){
    			resultMap.put("content", "分类名已经被占用了");
    			return resultMap;
    		}
    		if(hmxCategoryDto.getSort() == null ){
    			hmxCategoryDto.setSort(selectCategoryMaxSort()+1);
    		}
    		Date date = new Date();
    		hmxCategoryDto.setCreateTime(date);
    		hmxCategoryDto.setNewTime(date);
			//hmxCategoryDto.setCreateid(userModelLogin.getId());
    		if(!insert(hmxCategoryDto)){
    			resultMap.put("content", "添加分类失败");
    			return resultMap;
    		}
    		resultMap.put("flag", true);
    		resultMap.put("content", "添加分类成功");
    		return resultMap;
		} catch (Exception e) {
			e.printStackTrace();
			resultMap.put("content", "添加分类失败");
			return resultMap;
		}
    }
    
    /**
     * 分类在首页显示模块排序
     * @return
     */
    public int selectCategoryMaxSort(){
    	return hmxCategoryMapper.selectCategoryMaxSort();
    }
    /**
     * 分类编辑更新
     * @param hmxCategoryDto
     * @return
     */
    public Map<String,Object> categoryUpdate(HmxCategoryDto hmxCategoryDto){
    	Map<String,Object> resultMap = new HashMap<String,Object>();
    	resultMap.put("flag", false);
    	try {
    		HmxCategoryDto categoryDto = new HmxCategoryDto();
    		categoryDto.setCategoryName(hmxCategoryDto.getCategoryName());
    		categoryDto.setState(DataState.正常.getState());
    		categoryDto.setCategoryId(hmxCategoryDto.getCategoryId());
    		if(selectIsCategoryName(categoryDto)){
    			resultMap.put("content", "分类名已经被占用了");
    			return resultMap;
    		}
    		Date date = new Date();
    		hmxCategoryDto.setNewTime(date);
    		if(!update(hmxCategoryDto)){
    			resultMap.put("content", "更新分类信息失败");
    			return resultMap;
    		}
    		resultMap.put("flag", true);
    		resultMap.put("content", "更新分类信息成功");
    		return resultMap;
		} catch (Exception e) {
			resultMap.put("content", "更新分类信息失败");
			return resultMap;
		}
    }

	/**
	 * 分类删除
	 * @param categoryIds
	 * @return
     */
	@Override
	public Map<String, Object> categoryDelete(String categoryIds) {
		Map<String,Object> resultMap = new HashMap<String,Object>();
		resultMap.put("flag", false);
		try {
			if(!deleteByIdArray(categoryIds)){
				resultMap.put("content", "删除分类信息失败");
				return resultMap;
			}
			resultMap.put("flag", true);
			resultMap.put("content", "删除分类信息成功");
			return resultMap;
		} catch (Exception e) {
			resultMap.put("content", "删除分类信息失败");
			return resultMap;
		}
	}
    /**
     * 查询分类名是否重复
     * @return
     */
    public boolean selectIsCategoryName(HmxCategoryDto hmxCategoryDto){
    	Map<String,Object> parameter = new HashMap<String,Object>();
    	if(!StringUtils.isEmpty(hmxCategoryDto.getCategoryName())){
    		parameter.put("categoryName", hmxCategoryDto.getCategoryName());
    	}
    	if(hmxCategoryDto.getCategoryId() != null){
    		parameter.put("categoryId", hmxCategoryDto.getCategoryId());
    	}
    	if(hmxCategoryDto.getState() != null){
    		parameter.put("state", hmxCategoryDto.getState());
    	}
    	return hmxCategoryMapper.selectIsCategoryName(parameter)>0;
    }
    /**
     * 分类列表
     */
    public PageBean<Map<String,Object>> selectCategoryTable(PageBean<Map<String,Object>> page,HmxCategoryDto hmxCategoryDto){
    	Map<String,Object> parameter = new HashMap<String,Object>();
    	parameter.put("offset", page.getStartOfPage());
    	parameter.put("limit", page.getPageSize());
    	parameter.put("state", DataState.正常.getState());
    	if(!StringUtils.isEmpty(hmxCategoryDto.getCategoryName())){
    		parameter.put("categoryName", hmxCategoryDto.getCategoryName());
    	}
    	if(hmxCategoryDto.getBeginDate() != null){
    		parameter.put("beginDate", hmxCategoryDto.getBeginDate());
    	}
    	if(hmxCategoryDto.getEndDate() != null){
    		parameter.put("endDate", hmxCategoryDto.getEndDate());
    	}
    	if(hmxCategoryDto.getIsClose() != null){
    		parameter.put("isClose", hmxCategoryDto.getIsClose());
    	}
		if(null != hmxCategoryDto.getParentId() && hmxCategoryDto.getParentId() != 0){//查询所有二级分类
			parameter.put("parentId", hmxCategoryDto.getParentId());
		}else if(null != hmxCategoryDto.getParentId() && hmxCategoryDto.getParentId() == 0){ //查询所有一级分类
			parameter.put("parentId", hmxCategoryDto.getParentId());
		}
    	Integer count = hmxCategoryMapper.countCategoryTable(parameter);
	    Boolean haveData = page.setTotalNum((int)(long)count);
	    if(!haveData){
			return page;
		}
	    List<Map<String,Object>> data = hmxCategoryMapper.selectCategoryTable(parameter);
	    page.setPage(data);
    	return page;
    }

}
 
 