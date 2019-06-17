package com.hmx.category.service.impl;

import java.util.List;
import java.util.Map;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;

import com.hmx.category.dao.HmxCategoryContentMapper;
import com.hmx.category.entity.HmxCategoryContent;
import com.hmx.category.entity.HmxCategoryContentExample;
import com.hmx.images.dao.HmxImagesMapper;
import com.hmx.images.dto.HmxImagesDto;
import com.hmx.images.entity.HmxImages;
import com.hmx.images.entity.HmxImagesExample;
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

	@Autowired
	private HmxImagesMapper hmxImagesMapper;
	
	
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
     * 获取首页一级分类及其下面的最新的10条内容
     * @return
     */
    public List<Map<String,Object>> selectCategoryAndContentList(){
		//获取一级分类
		Map<String,Object> parameter = new HashMap<String,Object>();
		parameter.put("parentId",0);
		List<Map<String,Object>> categoryList = hmxCategoryMapper.selectCategoryTable(parameter);
		List<Map<String,Object>> topCategoryList = new ArrayList<>();
		//按sort为  1，2，7，4，3，9，8， 5，6排序
		if(null != categoryList && categoryList.size() > 0){
			sortCategory(categoryList,topCategoryList);
		}

		//获取二级分类
		if(null != topCategoryList && topCategoryList.size() > 0){
			//获取二级分类及其下的内容
			for(Map<String,Object> map : topCategoryList){
				map.put("contentList",null);
				Integer topCategoryId = Integer.valueOf(map.get("categoryId")+"");
				Map<String,Object> subparameter = new HashMap<String,Object>();
				subparameter.put("parentId",topCategoryId);
				List<Map<String,Object>> subCategoryList = hmxCategoryMapper.selectCategoryTable(subparameter);
				if(null != subCategoryList && subCategoryList.size() > 0){
					List<Integer> subCategoryIdList = new ArrayList<>();
					for (Map<String,Object> objectMap : subCategoryList){
						subCategoryIdList.add(Integer.valueOf(objectMap.get("categoryId")+""));
					}
					HmxCategoryContentExample example = new HmxCategoryContentExample();
					//这里要改成and连接
					example.or().andCategoryIdIn(subCategoryIdList);
					example.setOrderByClause("create_time");
					example.setOffset(0);
					example.setLimit(10);
					List<HmxCategoryContent> contenList = hmxCategoryContentMapper.selectByExample(example);
					if(null != contenList && contenList.size() > 0){
						for(HmxCategoryContent content : contenList){
							content.setCategoryContent("");
							//获取对应的图片
							HmxImagesExample hmxImagesExample = new HmxImagesExample();
							HmxImagesExample.Criteria where = hmxImagesExample.createCriteria();
							where.andCategoryContentIdEqualTo(content.getCategoryContentId());
							List<HmxImages> imagesList = hmxImagesMapper.selectByExample(hmxImagesExample);
							if(null != imagesList && imagesList.size() > 0){
								for(HmxImages images : imagesList){
									if(!StringUtils.isEmpty(images.getImageUrl())){
										content.setContentImages(images.getImageUrl());
										break;
									}
									if(!StringUtils.isEmpty(images.getTransImage())){
										content.setContentImages(images.getTransImage());
										break;
									}
									if(!StringUtils.isEmpty(images.getVerticalImage())){
										content.setContentImages(images.getVerticalImage());
										break;
									}
								}
							}
						}
					}
					map.put("contentList",contenList);
				}
			}
		}
    	return topCategoryList;
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
			//添加一级分类不能重名
			if(null == hmxCategoryDto.getParentId() || 0 == hmxCategoryDto.getParentId()){
				HmxCategoryDto categoryDto = new HmxCategoryDto();
				categoryDto.setCategoryName(hmxCategoryDto.getCategoryName());
				categoryDto.setState(DataState.正常.getState());
				if(selectIsCategoryName(categoryDto)){
					resultMap.put("content", "分类名已经被占用了");
					return resultMap;
				}
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
			//更新了一级分类的类型，二级分类类型也得跟着变更
//			if(null == hmxCategoryDto.getParentId() || 0 == hmxCategoryDto.getParentId()){
//				HmxCategoryExample hmxCategoryExample = new HmxCategoryExample();
//
//				Criteria where = hmxCategoryExample.createCriteria();
//				where.andParentIdEqualTo(hmxCategoryDto.getCategoryId());
//				List<HmxCategory> categoryList = hmxCategoryMapper.selectByExample(hmxCategoryExample);
//				if(null != categoryList && categoryList.size() > 0){
//					for(HmxCategory category : categoryList){
//						category.setCategoryType(hmxCategoryDto.getCategoryType());
//						hmxCategoryMapper.updateByPrimaryKeySelective(category);
//					}
//				}
//			}
    		resultMap.put("flag", true);
    		resultMap.put("content", "更新分类信息成功");
    		return resultMap;
		} catch (Exception e) {
			e.printStackTrace();
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

	@Override
	public List<Map<String,Object>> allTopCategory(HmxCategoryDto hmxCategoryDto) {
		Map<String,Object> parameter = new HashMap<String,Object>();
		parameter.put("parentId",0);
		List<Map<String,Object>> categoryList = hmxCategoryMapper.selectCategoryTable(parameter);
//		List<Map<String,Object>> topCategoryList = new ArrayList<>();
//		sortCategory(categoryList,topCategoryList);
//		return topCategoryList;
		return categoryList;
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

	/**
	 * 分类排序
	 * @param categoryList
	 * @param topCategoryList
     */
	public void sortCategory(List<Map<String,Object>> categoryList,List<Map<String,Object>> topCategoryList){
		for(int i = 0; i < 9; i++){
			for(int j = 0; j < categoryList.size(); j++){
				Map<String,Object> map = categoryList.get(j);
				Integer sort = Integer.valueOf(map.get("sort")+"");
				if(i == 0 && sort == 1){
					//map.put("sort",1);
					topCategoryList.add(map);
					break;
				}else if(i == 1 && sort == 11){//剧目资料，换成，音乐影视
					//map.put("sort",2);
					topCategoryList.add(map);
					break;
				}else if(i == 2 && sort == 7){
					//map.put("sort",3);
					topCategoryList.add(map);
					break;
				}else if(i == 3 && sort == 4){
					//map.put("sort",4);
					topCategoryList.add(map);
					break;
				}else if(i == 4 && sort == 3){
					//map.put("sort",5);
					topCategoryList.add(map);
					break;
				}else if(i == 5 && sort == 9){
					//map.put("sort",6);
					topCategoryList.add(map);
					break;
				}else if(i == 6 && sort == 8){
					//map.put("sort",7);
					topCategoryList.add(map);
					break;
				}else if(i == 7 && sort == 5){
					//map.put("sort",8);
					topCategoryList.add(map);
					break;
				}else if(i == 8 && sort == 6){
					//map.put("sort",9);
					topCategoryList.add(map);
					break;
				}
			}
		}
		for(int i = 0; i < topCategoryList.size(); i++){
			topCategoryList.get(i).put("sort",i+1);
		}
	}

}
 
 