package com.hmx.category.service.impl;

import java.util.*;

import com.hmx.base.entity.ContentModel;
import com.hmx.base.entity.RcmbModel;
import com.hmx.base.entity.RowsModel;
import com.hmx.category.dao.HmxCategoryMapper;
import com.hmx.category.dto.HmxCategoryDto;
import com.hmx.category.entity.*;
import com.hmx.category.service.HmxCategoryService;
import com.hmx.files.dao.HmxFilesMapper;
import com.hmx.files.dto.HmxFilesDto;
import com.hmx.files.entity.HmxFiles;
import com.hmx.files.entity.HmxFilesExample;
import com.hmx.hotWords.dao.HotWordsMapper;
import com.hmx.hotWords.dto.HotWordsDto;
import com.hmx.hotWords.entity.HotWords;
import com.hmx.hotWords.entity.HotWordsExample;
import com.hmx.images.dao.HmxImagesMapper;
import com.hmx.images.dto.HmxImagesDto;
import com.hmx.images.entity.HmxImages;
import com.hmx.images.entity.HmxImagesExample;
import com.hmx.movie.dao.HmxMovieMapper;
import com.hmx.movie.dto.HmxMovieDto;
import com.hmx.movie.entity.HmxMovie;
import com.hmx.movie.entity.HmxMovieExample;
import com.hmx.system.entity.SearchModel;
import com.hmx.system.entity.Tagtab;
import com.hmx.system.service.TagtabService;
import com.hmx.utils.common.CommonUtils;
import com.hmx.utils.common.TimeUtils;
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
import com.hmx.category.dto.HmxCategoryContentDto;
import com.hmx.category.entity.HmxCategoryContentExample.Criteria;
import com.hmx.category.dao.HmxCategoryContentMapper;
/**
 * Service implementation class
 *
 */
 @Service
 public class HmxCategoryContentServiceImpl implements HmxCategoryContentService{

	private static final Integer INDEX = 45;  //关键字前后几个字符被显示
	private static final Integer COUNTINDEX = 100;  //最多显示多少个字
	private static final Integer MAXCOUNT = 30;   //预留多少个字的保留空间

 	@Autowired
	private HmxCategoryContentMapper hmxCategoryContentMapper;

	@Autowired
	private HmxCategoryMapper hmxCategoryMapper;

 	@Autowired
	private InitVodClients initVodClients;
	@Autowired
	private HmxMovieMapper hmxMovieMapper;

	@Autowired
	private HmxFilesMapper hmxFilesMapper;

	@Autowired
	private HmxImagesMapper hmxImagesMapper;

	@Autowired
	private HmxCategoryService hmxCategoryService;

	@Autowired
	private TagtabService tagtabService;

	@Autowired
	private HotWordsMapper hotWordsMapper;
	
	
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

			//1删除图片
			HmxImagesExample hmxImagesExample = new HmxImagesExample();
			hmxImagesExample.or().andCategoryContentIdIn(idArray);
			hmxImagesMapper.deleteByExample(hmxImagesExample);
			//2删除文件
			HmxFilesExample hmxFilesExample = new HmxFilesExample();
			hmxFilesExample.or().andCategoryContentIdIn(idArray);
			hmxFilesMapper.deleteByExample(hmxFilesExample);
			//3删除视频
//			HmxMovieExample hmxMovieExample = new HmxMovieExample();
//			hmxMovieExample.or().andCategoryContentIdIn(idArray);
//			hmxMovieMapper.deleteByExample(hmxMovieExample);

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
//  		if ( hmxCategoryContentDto.getContentType() != null && hmxCategoryContentDto.getContentType() != 0 ) {
//			where.andContentTypeEqualTo( hmxCategoryContentDto.getContentType() );
//		}
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
		if(hmxCategoryContentDto.getMode() != null && hmxCategoryContentDto.getMode() > 0){
			where.andModeEqualTo(hmxCategoryContentDto.getMode());
		}
		
		if( hmxCategoryContentDto.getLimit() != null ){
			hmxCategoryContentExample.setLimit( hmxCategoryContentDto.getLimit() );
		}
//		if( !StringUtils.isEmpty( hmxCategoryContentDto.getOrderByClause() ) ){
//			hmxCategoryContentExample.setOrderByClause( hmxCategoryContentDto.getOrderByClause() );
//		}
		hmxCategoryContentExample.setOrderByClause( " sort,create_time " );
		return hmxCategoryContentMapper.selectByExample(hmxCategoryContentExample);
	}
	/**
	 * 内容详情添加
	 * @param hmxCategoryContentDto
	 * @return
	 */
	@Transactional
	public Map<String,Object> categoryContentAdd(HmxCategoryContentDto hmxCategoryContentDto, List<HmxMovieDto> hmxMovieDtoList,
												 List<HmxImagesDto> hmxImagesDtoList, List<HmxFilesDto> hmxFilesDtoList){
		Map<String,Object> resultMap = new HashMap<String,Object>();
		resultMap.put("flag", false);
		try {
			Date date = new Date();
			hmxCategoryContentDto.setCreateTime(date);
			hmxCategoryContentDto.setNewTime(date);
			hmxCategoryContentDto.setState(0);
			hmxCategoryContentDto.setVersion(0);
			//获取分类下排序最大值
			Map<String,Object> parameter = new HashMap<String,Object>();
			parameter.put("categoryId", hmxCategoryContentDto.getCategoryId());
			String maxSort = hmxCategoryContentMapper.maxSort(parameter);
			int max_Sort = 1;
			if(StringUtils.isEmpty(maxSort)){
				max_Sort = 1;
			}else{
				max_Sort = Integer.valueOf(maxSort) + 1;
			}
			hmxCategoryContentDto.setSort(max_Sort);
			if(!insert(hmxCategoryContentDto)){
				resultMap.put("content", "添加内容失败");
	    		return resultMap;
			}
			int categoryId = hmxCategoryContentDto.getCategoryContentId();
			//1将视频信息维护到movie表
//			for(HmxMovieDto hmxMovieDto : hmxMovieDtoList){
//				hmxMovieDto.setCreateTime(date);
//				hmxMovieDto.setCategoryContentId(categoryId);
//				hmxMovieMapper.insert(hmxMovieDto);
//			}
			//2将图片信息维护到images表
			for(HmxImagesDto hmxImagesDto : hmxImagesDtoList){
				hmxImagesDto.setCreateTime(date);
				hmxImagesDto.setCategoryContentId(categoryId);
				hmxImagesMapper.insert(hmxImagesDto);
			}
			//3将pdf文件信息维护进files表
			for(HmxFilesDto filesDto : hmxFilesDtoList){
				filesDto.setCreateTime(date);
				filesDto.setCategoryContentId(categoryId);
				hmxFilesMapper.insert(filesDto);
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
	public Map<String,Object> categoryContentUpdate(HmxCategoryContentDto hmxCategoryContentDto, List<HmxMovieDto> hmxMovieDtoList, List<HmxImagesDto> hmxImagesDtoList,List<HmxFilesDto> hmxFilesDtoList){
		Map<String,Object> resultMap = new HashMap<String,Object>();
		resultMap.put("flag", false);
		try {
			hmxCategoryContentDto.setNewTime(new Date());
			hmxCategoryContentDto.setState(0);
			hmxCategoryContentDto.setIsPublish(0);//修改的内容发布状态要重新置为未发布
			if(!update(hmxCategoryContentDto)){
				resultMap.put("content", "编辑内容失败");
	    		return resultMap;
			}
			Integer categoryId = hmxCategoryContentDto.getCategoryContentId();
			//1更新视频信息
//			for(HmxMovieDto hmxMovieDto : hmxMovieDtoList){
//				HmxMovie hmxMovie = hmxMovieMapper.selectByPrimaryKey(hmxMovieDto.getMovieId());
//				if(null == hmxMovie){
//					hmxMovieDto.setCreateTime(new Date());
//					hmxMovieDto.setCategoryContentId(categoryId);
//					hmxMovieMapper.insert(hmxMovieDto);
//				}else {
//					hmxMovieMapper.updateByPrimaryKeySelective(hmxMovieDto);
//				}
//
//			}
			//2更新图片信息
			for(HmxImagesDto hmxImagesDto : hmxImagesDtoList){
				HmxImages images = hmxImagesMapper.selectByPrimaryKey(hmxImagesDto.getImageId());
				if(images == null ){
					hmxImagesDto.setCreateTime(new Date());
					hmxImagesDto.setCategoryContentId(categoryId);
					hmxImagesMapper.insert(hmxImagesDto);
				}else {
					hmxImagesMapper.updateByPrimaryKeySelective(hmxImagesDto);
				}

			}
			//3更新文件信息
			for(HmxFilesDto filesDto : hmxFilesDtoList){
				HmxFiles files = hmxFilesMapper.selectByPrimaryKey(filesDto.getFileId());
				if(null == files){
					filesDto.setCreateTime(new Date());
					filesDto.setCategoryContentId(categoryId);
					hmxFilesMapper.insert(filesDto);
				}else {
					hmxFilesMapper.updateByPrimaryKeySelective(filesDto);
				}
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
    public HmxCategoryContentTrans selectCategoryContentById(Integer categoryContentId){
		HmxCategoryContent hmxCategoryContent = hmxCategoryContentMapper.selectCategoryContentById(categoryContentId);
		HmxCategoryContentTrans hmxCategoryContentTrans = new HmxCategoryContentTrans();
		hmxCategoryContentTrans.setCreateTime(hmxCategoryContent.getCreateTime());
		hmxCategoryContentTrans.setCategoryContentId(hmxCategoryContent.getCategoryContentId());
		hmxCategoryContentTrans.setCategoryContent(hmxCategoryContent.getCategoryContent());
		hmxCategoryContentTrans.setBrowseNum(hmxCategoryContent.getBrowseNum());
		hmxCategoryContentTrans.setCategoryId(hmxCategoryContent.getCategoryId());
		hmxCategoryContentTrans.setCategoryTitle(hmxCategoryContent.getCategoryTitle());
		hmxCategoryContentTrans.setContentImages(hmxCategoryContent.getContentImages());
		hmxCategoryContentTrans.setContentType(hmxCategoryContent.getContentType());
		hmxCategoryContentTrans.setMusicId(hmxCategoryContent.getMusicId());
		hmxCategoryContentTrans.setNewTime(hmxCategoryContent.getNewTime());
		hmxCategoryContentTrans.setState(hmxCategoryContent.getState());
		hmxCategoryContentTrans.setVersion(hmxCategoryContent.getVersion());
		hmxCategoryContentTrans.setMovieId(hmxCategoryContent.getMovieId()+"");
		hmxCategoryContentTrans.setContentDesc(hmxCategoryContent.getContentDesc());
		hmxCategoryContentTrans.setSubTitle(hmxCategoryContent.getSubTitle());
		hmxCategoryContentTrans.setMode(hmxCategoryContent.getMode());
		hmxCategoryContentTrans.setContentFlow(hmxCategoryContent.getContentFlow());
		hmxCategoryContentTrans.setSort(hmxCategoryContent.getSort());
		hmxCategoryContentTrans.setIsShowHomePage(hmxCategoryContent.getIsShowHomePage());
		hmxCategoryContentTrans.setIsPublish(hmxCategoryContent.getIsPublish());
		hmxCategoryContentTrans.setSourceId(hmxCategoryContent.getSourceId());
		hmxCategoryContentTrans.setSourceTitle(hmxCategoryContent.getSourceTitle());
		hmxCategoryContentTrans.setUserLevel(hmxCategoryContent.getUserLevel());

		//获取分类信息
		HmxCategory hmxCategory = hmxCategoryMapper.selectByPrimaryKey(hmxCategoryContent.getCategoryId());

		hmxCategoryContentTrans.setCategoryName(hmxCategory.getCategoryName());
		hmxCategoryContentTrans.setCategoryParentId(hmxCategory.getParentId());
//		String tagName = "";
//		//获取标签信息
//		if(!StringUtils.isEmpty(hmxCategoryContent.getTagId())){
//			String[] tagIdsArr = hmxCategoryContent.getTagId().split(",");
//			for(String tagId : tagIdsArr){
//				if(CommonUtils.isInteger(tagId)){
//					Tagtab tagtab = tagtabService.info(Integer.valueOf(tagId));
//					if(null != tagtab){
//						tagName += tagtab.getTagName() + ",";
//					}
//				}
//
//			}
//		}
//		if(tagName.endsWith(",")){
//			tagName = tagName.substring(0,tagName.length() - 1);
//		}
//		hmxCategoryContentTrans.setTagId(hmxCategoryContent.getTagId());
//		hmxCategoryContentTrans.setTagName(tagName);
    	//查询视频信息
//		String movieIds = "";
//		HmxMovieExample hmxMovieExample = new HmxMovieExample();
//		//hmxMovieExample.or().andCategoryContentIdIn(idArray);
//		hmxMovieExample.or().andCategoryContentIdEqualTo(categoryContentId+"");
//		hmxMovieExample.setOrderByClause("serie");
//		List<HmxMovie> hmxMovieList = hmxMovieMapper.selectByExample(hmxMovieExample);
//		if(hmxMovieList != null && hmxMovieList.size() > 0){
//			hmxCategoryContentTrans.setMovieList(hmxMovieList);
//			for(HmxMovie movie : hmxMovieList){
//				movieIds += movie.getVideoId()+",";
//			}
//		}

//		if(!StringUtils.isEmpty(movieIds) && movieIds.endsWith(movieIds)){
//			movieIds = movieIds.substring(0,(movieIds.length() - 1));
//		}
//		hmxCategoryContentTrans.setMovieId(movieIds);
		//查询内容下的pdf的url
		String fileUrl = "";
		HmxFilesExample hmxFilesExample = new HmxFilesExample();
		hmxFilesExample.or().andCategoryContentIdEqualTo(categoryContentId);
		List<HmxFiles> hmxFilesList = hmxFilesMapper.selectByExample(hmxFilesExample);
		if(null != hmxFilesList && hmxFilesList.size() > 0){
			hmxCategoryContentTrans.setFilesList(hmxFilesList);
			fileUrl = hmxFilesList.get(0).getFileUrl();
		}
		hmxCategoryContentTrans.setFileUrl(fileUrl);
		//查询视频下面的图片信息
		String imageUrl = "";
//		String transImage = "";   //横图
//		String verticalImage = "";   //竖图
		HmxImagesExample hmxImagesExample = new HmxImagesExample();
		hmxImagesExample.or().andCategoryContentIdEqualTo(categoryContentId);
		List<HmxImages> hmxImagesList = hmxImagesMapper.selectByExample(hmxImagesExample);
		if(null != hmxImagesList && hmxImagesList.size() > 0){
			hmxCategoryContentTrans.setImagesList(hmxImagesList);
		}
//		hmxCategoryContentTrans.setContentImages(imageUrl);
//		hmxCategoryContentTrans.setVerticalImage(verticalImage);
//		hmxCategoryContentTrans.setTransImage(transImage);
		return hmxCategoryContentTrans;
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
		if(hmxCategoryContentDto.getContentType() != null){
			parameter.put("contentType", hmxCategoryContentDto.getContentType());
		}
		if(null != hmxCategoryContentDto.getMode() && hmxCategoryContentDto.getMode() != 0){
			parameter.put("mode", hmxCategoryContentDto.getMode());
		}
		if(!StringUtils.isEmpty(hmxCategoryContentDto.getSubTitle())){
			parameter.put("subTitle", hmxCategoryContentDto.getSubTitle());
		}
		if(!StringUtils.isEmpty(hmxCategoryContentDto.getCategoryContent())){
			parameter.put("title", hmxCategoryContentDto.getCategoryContent());
		}
		if(!StringUtils.isEmpty(hmxCategoryContentDto.getIsPublish())){
			parameter.put("isPublish", hmxCategoryContentDto.getIsPublish());
		}
    	Integer count = hmxCategoryContentMapper.countCategoryContentTable(parameter);
	    Boolean haveData = page.setTotalNum((int)(long)count);
	    if(!haveData){
			return page;
		}
	    List<Map<String,Object>> data = hmxCategoryContentMapper.selectCategoryContentTable(parameter);
		//获取imageUrl
		if(null != data && data.size() > 0){
			for(Map<String,Object> map : data){
				if(StringUtils.isEmpty(map.get("contentImages")+"") || "null".equals(map.get("contentImages")+"")){
					//获取图片在images表
					HmxImagesExample imagesExample = new HmxImagesExample();
					imagesExample.or().andCategoryContentIdEqualTo(Integer.valueOf(map.get("categoryContentId")+""));
					List<HmxImages> imagesList = hmxImagesMapper.selectByExample(imagesExample);
					if(null != imagesList && imagesList.size() > 0){
						for(HmxImages images : imagesList){
							if(!StringUtils.isEmpty(images.getImageUrl())){
								map.put("contentImages",images.getImageUrl());
								break;
							}
						}
					}
				}
			}
		}
	    page.setPage(data);
    	return page;
    }
    /**
     * 内容信息详情查询
     * 更新内容浏览量+1
     * @param categoryContentId
     * @return
     */
    public Map<String,Object> selectContentInfoByContentId(Integer categoryContentId,String type){
    	Map<String,Object> resultMap = hmxCategoryContentMapper.selectContentInfoByContentId(categoryContentId);
    	resultMap.put("videoUrl", null);
		resultMap.put("tagName",null);
    	if(resultMap != null){
    		HmxCategoryContent hmxCategoryContent = new HmxCategoryContent();
    		hmxCategoryContent.setCategoryContentId(categoryContentId);
    		hmxCategoryContent.setBrowseNum(Integer.parseInt(resultMap.get("browseNum").toString()));
    		update(hmxCategoryContent);
    		//获取视频链接
//    		if(resultMap.get("videoList") != null){
//    			List<Map<String,Object>> videoList = (List<Map<String,Object>>)resultMap.get("videoList");
//    			if(videoList != null && videoList.size()>0){
//    				for(Map<String,Object> video:videoList){
//    					video.put("videoUrl", null);
//    					if(video.get("videoId") != null){
//    						Map<String,Object> resultUrl = initVodClients.getUrl(video.get("videoId").toString());
//    						boolean flag = Boolean.parseBoolean(resultUrl.get("flag").toString());
//    						if(flag){
//    							video.put("videoUrl", resultUrl.get("url"));
//    						}
//    					}
//    				}
//    			}
//    		}
			//获取内容的标签
			List<Map<String,Object>> data = new ArrayList<Map<String,Object>>();
			data.add(resultMap);
			getTagInfo(data);
//			String tagName = "";
//			//获取标签信息
//			if(!StringUtils.isEmpty(resultMap.get("tagId")+"")){
//				String[] tagIdsArr = resultMap.get("tagId").toString().split(",");
//				for(String tagId : tagIdsArr){
//					if(CommonUtils.isInteger(tagId)){
//						Tagtab tagtab = tagtabService.info(Integer.valueOf(tagId));
//						if(null != tagtab){
//							tagName += tagtab.getTagName() + ",";
//						}
//					}
//
//				}
//			}
//			if(tagName.endsWith(",")){
//				tagName = tagName.substring(0,tagName.length() - 1);
//			}
//			resultMap.put("tagName",tagName);
    	}
		//查询内容下的视频信息
//		String movieIds = "";
//		HmxMovieExample hmxMovieExample = new HmxMovieExample();
//		//hmxMovieExample.or().andCategoryContentIdIn(idArray);
//		hmxMovieExample.or().andCategoryContentIdEqualTo(categoryContentId+"");
//		hmxMovieExample.setOrderByClause("serie");
//		List<HmxMovie> hmxMovieList = hmxMovieMapper.selectByExample(hmxMovieExample);
//		resultMap.put("videoList",hmxMovieList);
//
//		if(hmxMovieList != null && hmxMovieList.size() > 0){
//			for(HmxMovie movie : hmxMovieList){
//				movieIds += movie.getVideoId()+",";
//			}
//		}
//
//		if(!StringUtils.isEmpty(movieIds) && movieIds.endsWith(movieIds)){
//			movieIds = movieIds.substring(0,(movieIds.length() - 1));
//		}
//		resultMap.put("videoId",movieIds);
		//查询内容下的pdf的url
		String fileUrl = "";
		HmxFilesExample hmxFilesExample = new HmxFilesExample();
		hmxFilesExample.or().andCategoryContentIdEqualTo(categoryContentId);
		List<HmxFiles> hmxFilesList = hmxFilesMapper.selectByExample(hmxFilesExample);
//		List<HmxFiles> filesList = new ArrayList<>();
//		if(null != hmxFilesList && hmxFilesList.size() > 0){
//			for(HmxFiles file : hmxFilesList){
//				if("app".equals(type)){
//					if(file.getFileUrl().endsWith("epub")){
//						filesList.add(file);
//					}
//				}else if("pc".equals(type)){
//					if(file.getFileUrl().endsWith("html")){
//						//自己测试用start
//						file.setFileUrl(file.getFileUrl().replace("http://www.sskj.art:8080","http://120.79.169.165:80"));
//						//自己测试用end
//
//						filesList.add(file);
//					}
//				}
//			}
//		}
//		resultMap.put("filesList",filesList);
		if(null != hmxFilesList && hmxFilesList.size() > 0){
			fileUrl = hmxFilesList.get(0).getFileUrl();
			//自己测试用start
			//fileUrl = fileUrl.replace("http://www.sskj.art:8080","http://120.79.169.165:80");
			//自己测试用end
		}
		HmxImagesExample hmxImagesExample = new HmxImagesExample();
		hmxImagesExample.or().andCategoryContentIdEqualTo(categoryContentId);
		List<HmxImages> hmxImagesList = hmxImagesMapper.selectByExample(hmxImagesExample);
//		resultMap.put("imagesList",hmxImagesList);
		resultMap.put("fileUrl",fileUrl);
		//add at 20190608  临时为前端给这个字段加上内容
		if(null != hmxImagesList && hmxImagesList.size() > 0){
			for(HmxImages images : hmxImagesList){
				if(!StringUtils.isEmpty(images.getImageUrl())){
					resultMap.put("contentImages",images.getImageUrl());
					break;
				}
			}
		}
    	return resultMap;
    }
    /**
     * Pc内容列表查询
     * @return
     */
    public PageBean<Map<String,Object>> selectCategoryContentTableByPc(PageBean<Map<String,Object>> page,HmxCategoryContentDto hmxCategoryContentDto,String type){
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
		if(hmxCategoryContentDto.getMode() != null && hmxCategoryContentDto.getMode() > 0){
			parameter.put("mode", hmxCategoryContentDto.getMode());
		}
		if(!StringUtils.isEmpty(hmxCategoryContentDto.getSubTitle())){
			parameter.put("subTitle", hmxCategoryContentDto.getSubTitle());
		}
		if(null != hmxCategoryContentDto.getContentType() && 0 != hmxCategoryContentDto.getContentType()){
			parameter.put("contentType", hmxCategoryContentDto.getContentType());
		}
		if(!StringUtils.isEmpty(hmxCategoryContentDto.getCategoryContent())){
			parameter.put("title", hmxCategoryContentDto.getCategoryContent());
		}
		parameter.put("isPublish", 1);
    	Integer count = hmxCategoryContentMapper.countCategoryContentTableByPc(parameter);
	    Boolean haveData = page.setTotalNum((int)(long)count);
	    if(!haveData){
			return page;
		}
	    List<Map<String,Object>> data = hmxCategoryContentMapper.selectCategoryContentTableByPc(parameter);
		if(null != data && data.size() > 0){
			//获取内容的附属信息，图片，视频
			try {
				getContentImagesAndVideo(data,type);
			}catch (Exception e){
				e.printStackTrace();
			}

			//获取标签
			try {
				getTagInfo(data);
			}catch (Exception e){
				e.printStackTrace();
			}

		}

	    page.setPage(data);
    	return page;
    }

	public void getTagInfo(List<Map<String,Object>> data){
		for(Map<String,Object> map : data){
			map.put("tagName","");
			String tagIds = map.get("tagId")+"";
			String tagName = "";
			//获取标签信息
			if(!StringUtils.isEmpty(tagIds)){
				String[] tagIdsArr = tagIds.split(",");
				for(String tagId : tagIdsArr){
					if(CommonUtils.isInteger(tagId)){
						Tagtab tagtab = tagtabService.info(Integer.valueOf(tagId));
						if(null != tagtab){
							tagName += tagtab.getTagName() + ",";
						}
					}

				}
			}
			if(tagName.endsWith(",")){
				tagName = tagName.substring(0,tagName.length() - 1);
			}
			map.put("tagName",tagName);
		}
	}

	public void getContentImagesAndVideo(List<Map<String,Object>> data,String type){
		for(Map<String,Object> map : data){

			String contentId = map.get("categoryContentId")+"";
			HmxImagesExample hmxImagesExample = new HmxImagesExample();
			hmxImagesExample.or().andCategoryContentIdEqualTo(Integer.valueOf(contentId));
			//获取图片信息
			List<HmxImages> hmxImagesList = hmxImagesMapper.selectByExample(hmxImagesExample);
			if("app".equals(type)){
				map.put("imagesList",hmxImagesList);
			}
			if("pc".equals(type) && null != hmxImagesList && hmxImagesList.size() > 0){
				for(HmxImages images : hmxImagesList){
					if(!StringUtils.isEmpty(images.getImageUrl())){
						map.put("contentImages",images.getImageUrl());
						break;
					}
					if(!StringUtils.isEmpty(images.getTransImage())){
						map.put("contentImages",images.getTransImage());
						break;
					}
					if(!StringUtils.isEmpty(images.getVerticalImage())){
						map.put("contentImages",images.getVerticalImage());
						break;
					}
				}
			}

			//获取视频信息
//			HmxMovieExample hmxMovieExample = new HmxMovieExample();
//			hmxMovieExample.or().andCategoryContentIdEqualTo(contentId+"");
//			hmxMovieExample.setOrderByClause("serie");
//			List<HmxMovie> hmxMovieList = hmxMovieMapper.selectByExample(hmxMovieExample);
//			map.put("videoList",hmxMovieList);
		}
	}
    
    /**
     * 查看排行榜信息
     * @return
     */
    public List<Map<String,Object>> selectRankingListByCategoryId(){
		List<Map<String,Object>> list = hmxCategoryContentMapper.selectRankingListByCategoryId();
    	return list;
    }

	@Override
	public PageBean<Map<String, Object>> search(PageBean<Map<String, Object>> page, String contentValue) {
		Map<String,Object> parameter = new HashMap<String,Object>();
//		parameter.put("offset", page.getStartOfPage());
		parameter.put("limit", page.getPageSize()*2);
		parameter.put("state", DataState.正常.getState());
		parameter.put("title", contentValue);
		//统计搜索关键字次数
		try {
			HotWordsExample hotWordsExample = new HotWordsExample();
			HotWordsExample.Criteria where = hotWordsExample.createCriteria();
			where.andTitleEqualTo(contentValue);
			List<HotWords> data = hotWordsMapper.selectByExample(hotWordsExample);
			if(null == data || data.size() == 0){
				//增加到数据库
				HotWords record = new HotWords();
				record.setFrequency(1);
				record.setTitle(contentValue);
				record.setType(1);
				record.setCreateDate(new Date());
				record.setSort(0);
				hotWordsMapper.insert(record);
			}else {
				HotWords words = data.get(0);
				words.setFrequency(words.getFrequency() + 1);
				hotWordsMapper.updateByPrimaryKey(words);
			}
		}catch (Exception e){
			e.printStackTrace();
		}
		Integer count = hmxCategoryContentMapper.countCategoryContentTable(parameter);
		Boolean haveData = page.setTotalNum((int)(long)count);
		if(!haveData){
			return page;
		}
		List<Map<String,Object>> data = hmxCategoryContentMapper.selectCategoryContentTable(parameter);
		//如果是从内容中查找的则只显示关键字的前后15个文字
		//如果是标题查找到的则将内容的开头30个字展示出来
		if(null != data && data.size() > 0){
			//查找标签内容
			try {
				getTagInfo(data);
			}catch (Exception e){
				e.printStackTrace();
			}
			for(Map<String,Object> map : data){
				try {
					String content = CommonUtils.clearNotChinese(map.get("categoryContent")+"");
					if(!StringUtils.isEmpty(content) && content.length() > ((INDEX * 2) + contentValue.length() + MAXCOUNT)){
						Integer num = content.indexOf(contentValue);
						if( num != -1){//内容中存在关键字
							Integer startIndex = num - INDEX;
							content = content.substring(startIndex < 0 ? 0 : startIndex,(num + contentValue.length() + INDEX));
							map.put("categoryContent",content);
						}else {//内容中不存在关键字
							map.put("categoryContent",content.substring(0,COUNTINDEX));
						}
					}
				}catch (Exception e){
					e.printStackTrace();
				}
			}
		}
		page.setPage(data);
		return page;
	}


	@Override
	public PageBean<Map<String, Object>> seniorSearch(PageBean<Map<String, Object>> page, SearchModel searchModel) {
		Map<String,Object> parameter = new HashMap<String,Object>();
		//高级搜索不支持分页
//		parameter.put("offset", page.getStartOfPage());
//		parameter.put("limit", page.getPageSize());
		parameter.put("state", DataState.正常.getState());
		if(!StringUtils.isEmpty(searchModel.getCategoryTitle())){
			parameter.put("categoryTitle", searchModel.getCategoryTitle());
			//统计搜索关键字次数
			try {
				HotWordsExample hotWordsExample = new HotWordsExample();
				HotWordsExample.Criteria where = hotWordsExample.createCriteria();
				where.andTitleEqualTo(searchModel.getCategoryTitle());
				List<HotWords> data = hotWordsMapper.selectByExample(hotWordsExample);
				if(null == data){
					//增加到数据库
					HotWords record = new HotWords();
					record.setFrequency(1);
					record.setTitle(searchModel.getCategoryTitle());
					record.setType(1);
					record.setCreateDate(new Date());
					record.setSort(0);
					hotWordsMapper.insert(record);
				}else {
					HotWords words = data.get(0);
					words.setFrequency(words.getFrequency() + 1);
					hotWordsMapper.updateByPrimaryKey(words);
				}
			}catch (Exception e){
				e.printStackTrace();
			}
		}

		//根据二级分类id查找
		if(null != searchModel.getCategoryId() && 0 != searchModel.getCategoryId()){
			parameter.put("categoryId", searchModel.getCategoryId());
		}else if(null != searchModel.getParentCategoryId() && 0 != searchModel.getParentCategoryId()){//根据一级分类查找
			HmxCategoryDto hmxCategoryDto = new HmxCategoryDto();
			hmxCategoryDto.setParentId(searchModel.getParentCategoryId());
			List<HmxCategory> categoryList = hmxCategoryService.list(hmxCategoryDto);
			List<Integer> categoryIdsList = new ArrayList<>();
			for(int i = 0; i < categoryList.size(); i++){
				categoryIdsList.add(categoryList.get(i).getCategoryId());
			}
			parameter.put("parentCategoryId", categoryIdsList);
		}


		if(null != searchModel.getDateTime()){
			Integer dateTime = searchModel.getDateTime();
			Date start = TimeUtils.getNeedTime(0,0,0,0);
			Date end = TimeUtils.getNeedTime(23,59,59,0);
			if(1 == dateTime){
				parameter.put("beginDate",start);
				parameter.put("endDate",end);
			}
			if(2 == dateTime){
				parameter.put("beginDate",TimeUtils.getPastWeak());
				parameter.put("endDate",start);
			}
			if(3 == dateTime){
				parameter.put("beginDate",TimeUtils.getPastMonth());
				parameter.put("endDate", start);
			}
			if(4 == dateTime){
				parameter.put("beginDate",TimeUtils.getPastYear());
				parameter.put("endDate",start);
			}
		}

		if(null != searchModel.getDefinition()){
			Integer definition = searchModel.getDefinition();
			if(1 == definition){
				parameter.put("definition","FD");
			}
			if(2 == definition){
				parameter.put("definition","LD");
			}
			if(3 == definition){
				parameter.put("definition","SD");
			}
			if(4 == definition){
				parameter.put("definition","HD");
			}

		}

		if(null != searchModel.getDuration()){
			Integer duration = searchModel.getDuration();
			if(1 == duration){
				parameter.put("minDuration",60*60+"");
			}
			if(2 == duration){
				parameter.put("minDuration",30*60+"");
				parameter.put("maxDuration",60*60+"");
			}
			if(3 == duration){
				parameter.put("minDuration",10*60+"");
				parameter.put("maxDuration",30*60+"");
			}
			if(4 == duration){
				parameter.put("minDuration","0");
				parameter.put("maxDuration",10*60+"");
			}
		}

		Integer count = hmxCategoryContentMapper.countContentTable(parameter);
		Boolean haveData = page.setTotalNum((int)(long)count);
		if(!haveData){
			return page;
		}
		List<Map<String,Object>> data = hmxCategoryContentMapper.selectContentTable(parameter);
		page.setPage(data);
		return page;
	}

	@Override
	public List<Map<String, Object>> queryByContentFlow(String contentFlow) {
		return hmxCategoryContentMapper.queryByContentFlow(contentFlow);
	}

	@Override
	public Map<String, Object> queryContentById(Integer categoryContentId) {
		Map<String,Object> resultMap = hmxCategoryContentMapper.selectContentInfoByContentId(categoryContentId);
		return resultMap;
	}



	@Override
	public List<RcmbModel> getHomeInfo() {
		List<RcmbModel> rcmbModelList = new ArrayList<>();
		//1获取轮播图
		packWheel(rcmbModelList);
		//2获取一级分类信息
		List<HmxCategory> categoryList = packCategory(rcmbModelList);

		//3获取一级分类下的内容信息
		if(null != categoryList && categoryList.size() > 0){
			packCategoryContent(rcmbModelList,categoryList);
		}
		return rcmbModelList;
	}

	@Override
	public List<HmxCategoryContent> selectNewest(HmxCategoryContentDto hmxCategoryContentDto) {
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
		if(hmxCategoryContentDto.getMode() != null && hmxCategoryContentDto.getMode() > 0){
			where.andModeEqualTo(hmxCategoryContentDto.getMode());
		}

		if( hmxCategoryContentDto.getLimit() != null ){
			hmxCategoryContentExample.setLimit( hmxCategoryContentDto.getLimit() );
		}
		hmxCategoryContentExample.setOrderByClause( " create_time " );
		return hmxCategoryContentMapper.selectNewestByExample(hmxCategoryContentExample);
	}

	@Override
	public List<Map<String,Object>> getContentRankNum(Integer categoryType) {
		List<Map<String,Object>> hmxCategoryContentList = new ArrayList<>();
		Map<String,Object> parameter = new HashMap<String,Object>();
		if(null != categoryType && categoryType != 0){
			parameter.put("categoryType", categoryType);
			List<Map<String,Object>> tempList = hmxCategoryContentMapper.maxBromUnm(parameter);
			if(null != tempList && tempList.size() > 0){
				hmxCategoryContentList.add(tempList.get(0));
			}
		}else {
			//获取分类有多少种
			Map<String,Object> categoryParameter = new HashMap<String,Object>();
			List<Map<String,Object>> categoryList = hmxCategoryMapper.selectCategoryTypes(categoryParameter);
			if(null != categoryList && categoryList.size() > 0){
				for(Map<String,Object> map : categoryList){
					Integer cateType = Integer.valueOf(map.get("categoryType")+"");
					//获取内每个类型内容的最高浏览量
					Map<String,Object> temparameter = new HashMap<String,Object>();
					temparameter.put("categoryType",cateType);
					List<Map<String,Object>> tempList = hmxCategoryContentMapper.maxBromUnm(temparameter);
					if(null != tempList && tempList.size() > 0){
						hmxCategoryContentList.add(tempList.get(0));
					}
				}
			}
		}

		return hmxCategoryContentList;
	}

	@Override
	@Transactional
	public Map<String, Object> setTop(Integer contentId,Integer categoryId) {
		Map<String,Object> resultMap = new HashMap<String,Object>();
		resultMap.put("flag", false);
		try {
			//查询分类下是否有置顶的
			Integer contentTopId = hmxCategoryContentMapper.getContentTop(categoryId);
			if(null != contentTopId && contentTopId != 0){
				hmxCategoryContentMapper.removeContentTop(contentTopId);
			}
			hmxCategoryContentMapper.setContentTop(contentId);
			resultMap.put("flag",true);
		}catch (Exception e){
			e.printStackTrace();
		}
		return resultMap;
	}

	@Override
	@Transactional
	public Map<String, Object> upAndDown(Integer contentId, Integer categoryId, Integer sort, Integer type) {
		Map<String,Object> resultMap = new HashMap<String,Object>();
		resultMap.put("flag", false);
		try {
			Map<String,Object> parameter = new HashMap<String,Object>();
			Map<String,Object> parameter0 = new HashMap<String,Object>();
			Map<String,Object> parameter1 = new HashMap<String,Object>();
			int upContentId = 0;
			int upSort = 0;
			int downContentId = 0;
			int downSort = 0;
			parameter.put("sort",sort);
			parameter.put("categoryId",categoryId);

			if(1 == type){//上移

				HmxCategoryContent content = hmxCategoryContentMapper.selectMinSort(parameter);
				if(null != content){
					upContentId = contentId;
					downSort = content.getSort();
					downContentId = content.getCategoryContentId();
					upSort = sort;
				}else{
					resultMap.put("flag",true);
					return resultMap;
				}

			}
			if(2 == type){//下移
				HmxCategoryContent content = hmxCategoryContentMapper.selectMaxSort(parameter);
				if(null != content){
					upContentId = contentId;
					downSort = content.getSort();
					downContentId = content.getCategoryContentId();
					upSort = sort;
				}else {
					resultMap.put("flag",true);
					return resultMap;
				}

			}

			parameter0.put("contentId", upContentId);
			parameter0.put("sort", downSort);
			parameter1.put("contentId", downContentId);
			parameter1.put("sort", upSort);
			hmxCategoryContentMapper.upAndDown(parameter0);
			hmxCategoryContentMapper.upAndDown(parameter1);
			resultMap.put("flag",true);
		}catch (Exception e){
			e.printStackTrace();
		}
		return resultMap;
	}

	@Override
	@Transactional
	public Map<String, Object> publish(String ids) {
		Map<String,Object> resultMap = new HashMap<String,Object>();
		resultMap.put("flag", false);
		try {
			String[] idsArr = ids.split(",");
			for(String id : idsArr){
				hmxCategoryContentMapper.publishNew(Integer.valueOf(id));
			}
			resultMap.put("flag",true);
		}catch (Exception e){
			e.printStackTrace();
		}
		return resultMap;
	}

	@Override
	@Transactional
	public Map<String, Object> unPublish(String ids) {
		Map<String,Object> resultMap = new HashMap<String,Object>();
		resultMap.put("flag", false);
		try {
			String[] idsArr = ids.split(",");
			for(String id : idsArr){
				hmxCategoryContentMapper.unPublishNew(Integer.valueOf(id));
			}
			resultMap.put("flag",true);
		}catch (Exception e){
			e.printStackTrace();
		}
		return resultMap;
	}

	/**
	 * 轮播图组装
	 * @param rcmbModelList
	 */
	public void packWheel(List<RcmbModel> rcmbModelList){
		HmxCategoryContentDto hmxCategoryContentDto = new HmxCategoryContentDto();
		hmxCategoryContentDto.setMode(2);
		List<HmxCategoryContent> wheelPlantList = list(hmxCategoryContentDto);
		if(null == wheelPlantList || wheelPlantList.size() == 0){
			return;
		}
		RcmbModel rcmbModel = new RcmbModel();
		rcmbModelList.add(rcmbModel);
		rcmbModel.setMode(1);
		rcmbModel.setHasMore(false);
		rcmbModel.setTitle("轮播图");
		List<RowsModel> rowsModelList = new ArrayList<>();
		rcmbModel.setRows(rowsModelList);
		RowsModel rowsModel = new RowsModel();
		rowsModelList.add(rowsModel);
		rowsModel.setRow(1);
		rowsModel.setLine(1);
		rowsModel.setImageType("1");
		List<ContentModel> contentModelList = new ArrayList<>();
		rowsModel.setItems(contentModelList);
		if(null != wheelPlantList && wheelPlantList.size() > 0){
			for(HmxCategoryContent content : wheelPlantList){
				ContentModel contentModel = new ContentModel();
				contentModel.setTitle(content.getCategoryTitle());
				contentModel.setSubTitle(content.getSubTitle());
				contentModel.setContentId(content.getCategoryContentId());
				contentModel.setContent("");
				contentModel.setCreateTime(content.getCreateTime());
				contentModel.setDesc(content.getContentDesc());
				contentModel.setContentType(content.getContentType());
				contentModel.setUserLevel(content.getUserLevel());
				//获取内容的图片
				String imageUrl = "";
				String transImage = "";   //横图
				String verticalImage = "";   //竖图
				HmxImagesExample hmxImagesExample = new HmxImagesExample();
				hmxImagesExample.or().andCategoryContentIdEqualTo(content.getCategoryContentId());
				List<HmxImages> hmxImagesList = hmxImagesMapper.selectByExample(hmxImagesExample);
				if(null != hmxImagesList && hmxImagesList.size() > 0){
					for(HmxImages images : hmxImagesList){
						if(!StringUtils.isEmpty(images.getImageUrl())){
							imageUrl= images.getImageUrl();
						}
						if(!StringUtils.isEmpty(images.getVerticalImage())){
							verticalImage = images.getVerticalImage();
						}
						if(StringUtils.isEmpty(images.getTransImage())){
							transImage = images.getTransImage();
						}
					}
				}
				contentModel.setImageUrl(imageUrl);
				contentModel.setVerticalImage(verticalImage);
				contentModel.setTransImage(transImage);

				contentModelList.add(contentModel);
			}
		}
	}

	/**
	 * 组装一级分类
	 * @param rcmbModelList
     */
	public List<HmxCategory> packCategory(List<RcmbModel> rcmbModelList){
		HmxCategoryDto hmxCategoryDto = new HmxCategoryDto();
		hmxCategoryDto.setParentId(0);
		List<HmxCategory> categoryList = hmxCategoryService.list(hmxCategoryDto);
		if(null == categoryList || categoryList.size() == 0){
			return null;
		}

		RcmbModel rcmbModel = new RcmbModel();
		rcmbModelList.add(rcmbModel);
		rcmbModel.setMode(2);
		rcmbModel.setHasMore(false);
		rcmbModel.setTitle("一级分类");
		List<RowsModel> rowsModelList = new ArrayList<>();
		rcmbModel.setRows(rowsModelList);
		RowsModel rowsModel = new RowsModel();
		rowsModelList.add(rowsModel);
		rowsModel.setRow(2);
		rowsModel.setLine(6);
		rowsModel.setImageType("2");
		rowsModel.setItems(categoryList);
		return categoryList;
	}

	/**
	 * 组装分类内容
	 * @param rcmbModelList
	 * @param categoryList
     */
	public void packCategoryContent(List<RcmbModel> rcmbModelList,List<HmxCategory> categoryList){
		List<ContentModel> contentModel5List = new ArrayList<>();
		int num = 0;
		for(HmxCategory category : categoryList){
			RcmbModel rcmbModel = new RcmbModel();
			rcmbModelList.add(rcmbModel);
			rcmbModel.setMode(3);
			rcmbModel.setShowTitle(1);
			rcmbModel.setHasMore(false);
			List<RowsModel> rowsModelList = new ArrayList<>();
			rcmbModel.setRows(rowsModelList);
			RowsModel rowsModel = new RowsModel();
			rowsModelList.add(rowsModel);
			rowsModel.setRow(2);
			rowsModel.setLine(3);
			rowsModel.setImageType("1");
			List<ContentModel> contentModelList = new ArrayList<>();
			rowsModel.setItems(contentModelList);
			String categoryName = category.getCategoryName();
			rcmbModel.setTitle(categoryName);
			if("名家功臣".equals(categoryName)){
				rcmbModel.setMode(4);
			}
			if("图片资料".equals(categoryName)){
				rcmbModel.setMode(5);
				num = 0;
			}
			//获取一级分类的二级分类
			HmxCategoryDto hmxCategoryDto = new HmxCategoryDto();
			hmxCategoryDto.setParentId(category.getCategoryId());
			List<HmxCategory> hmxCategoryList = hmxCategoryService.list(hmxCategoryDto);
			List<Integer> subCategoryIdList = new ArrayList<>();
			if(null != hmxCategoryList && hmxCategoryList.size() > 0){
				for(HmxCategory hmxCategory : hmxCategoryList){
					subCategoryIdList.add(hmxCategory.getCategoryId());
				}
			}
			if(subCategoryIdList.size() == 0){
				continue;
			}
			HmxCategoryContentExample hmxCategoryContentExample = new HmxCategoryContentExample();
			hmxCategoryContentExample.or().andCategoryIdIn(subCategoryIdList);
			hmxCategoryContentExample.setOrderByClause(" sort,create_time ");
			hmxCategoryContentExample.setLimit(10);
			hmxCategoryContentExample.setOffset(0);
			List<HmxCategoryContent> contentList = hmxCategoryContentMapper.selectByExample(hmxCategoryContentExample);
			if(null != contentList && contentList.size() > 0){
				for(HmxCategoryContent content : contentList){
					ContentModel contentModel = new ContentModel();
					contentModel.setTitle(content.getCategoryTitle());
					contentModel.setSubTitle(content.getSubTitle());
					contentModel.setContentId(content.getCategoryContentId());
					contentModel.setContent("");
					contentModel.setCreateTime(content.getCreateTime());
					contentModel.setDesc(content.getContentDesc());
					contentModel.setContentType(content.getContentType());
					contentModel.setUserLevel(content.getUserLevel());
					//获取内容的图片
					String imageUrl = "";
					String transImage = "";   //横图
					String verticalImage = "";   //竖图
					HmxImagesExample hmxImagesExample = new HmxImagesExample();
					hmxImagesExample.or().andCategoryContentIdEqualTo(content.getCategoryContentId());
					List<HmxImages> hmxImagesList = hmxImagesMapper.selectByExample(hmxImagesExample);
					if(null != hmxImagesList && hmxImagesList.size() > 0){
						for(HmxImages images : hmxImagesList){
							if(!StringUtils.isEmpty(images.getImageUrl())){
								imageUrl= images.getImageUrl();
							}
							if(!StringUtils.isEmpty(images.getVerticalImage())){
								verticalImage = images.getVerticalImage();
							}
							if(StringUtils.isEmpty(images.getTransImage())){
								transImage = images.getTransImage();
							}
						}
					}
					contentModel.setImageUrl(imageUrl);
					contentModel.setVerticalImage(verticalImage);
					contentModel.setTransImage(transImage);

					//==========为加一个大横图start======
					if(num == 0 && rcmbModel.getMode() == 5){
						contentModel5List.add(contentModel);
					}else {
						contentModelList.add(contentModel);
					}
					//==========为加一个大横图end======
					num++;
				}
			}

			//==========为加一个大横图start======
			if(rcmbModel.getMode() == 5){
				RowsModel rowMap0 = new RowsModel();
				rowMap0.setItems(contentModel5List);
				rowMap0.setLine(1);
				rowMap0.setRow(1);
				rowMap0.setImageType("1");
				rowsModelList.add(0,rowMap0);
			}

			//==========为加一个大横图end======
		}
	}



//	private static Date getNeedTime(int hour,int minute,int second,int addDay,int ...args){
//		Calendar calendar = Calendar.getInstance();
//		if(addDay != 0){
//			calendar.add(Calendar.DATE,addDay);
//		}
//		calendar.set(Calendar.HOUR_OF_DAY,hour);
//		calendar.set(Calendar.MINUTE,minute);
//		calendar.set(Calendar.SECOND,second);
//		if(args.length==1){
//			calendar.set(Calendar.MILLISECOND,args[0]);
//		}
//		return calendar.getTime();
//	}

}
 
 