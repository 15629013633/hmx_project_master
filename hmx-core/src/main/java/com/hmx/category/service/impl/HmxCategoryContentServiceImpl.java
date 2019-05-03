package com.hmx.category.service.impl;

import java.util.List;
import java.util.Map;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;

import com.hmx.category.entity.HmxCategoryContentTrans;
import com.hmx.files.dao.HmxFilesMapper;
import com.hmx.files.dto.HmxFilesDto;
import com.hmx.files.entity.HmxFiles;
import com.hmx.files.entity.HmxFilesExample;
import com.hmx.images.dao.HmxImagesMapper;
import com.hmx.images.dto.HmxImagesDto;
import com.hmx.images.entity.HmxImages;
import com.hmx.images.entity.HmxImagesExample;
import com.hmx.movie.dao.HmxMovieMapper;
import com.hmx.movie.dto.HmxMovieDto;
import com.hmx.movie.entity.HmxMovie;
import com.hmx.movie.entity.HmxMovieExample;
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
	@Autowired
	private HmxMovieMapper hmxMovieMapper;

	@Autowired
	private HmxFilesMapper hmxFilesMapper;

	@Autowired
	private HmxImagesMapper hmxImagesMapper;
	
	
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
			HmxMovieExample hmxMovieExample = new HmxMovieExample();
			hmxMovieExample.or().andCategoryContentIdIn(idArray);
			hmxMovieMapper.deleteByExample(hmxMovieExample);

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
	public Map<String,Object> categoryContentAdd(HmxCategoryContentDto hmxCategoryContentDto, List<HmxMovieDto> hmxMovieDtoList,
												 List<HmxImagesDto> hmxImagesDtoList, List<HmxFilesDto> hmxFilesDtoList){
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
			int categoryId = hmxCategoryContentDto.getCategoryContentId();
			//1将视频信息维护到movie表
			for(HmxMovieDto hmxMovieDto : hmxMovieDtoList){
				hmxMovieDto.setCreateTime(date);
				hmxMovieDto.setCategoryContentId(categoryId);
				hmxMovieMapper.insert(hmxMovieDto);
			}
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
			if(!update(hmxCategoryContentDto)){
				resultMap.put("content", "编辑内容失败");
	    		return resultMap;
			}
			Integer categoryId = hmxCategoryContentDto.getCategoryContentId();
			//1更新视频信息
			for(HmxMovieDto hmxMovieDto : hmxMovieDtoList){
				HmxMovie hmxMovie = hmxMovieMapper.selectByPrimaryKey(hmxMovieDto.getMovieId());
				if(null == hmxMovie){
					hmxMovieDto.setCreateTime(new Date());
					hmxMovieDto.setCategoryContentId(categoryId);
					hmxMovieMapper.insert(hmxMovieDto);
				}else {
					hmxMovieMapper.updateByPrimaryKeySelective(hmxMovieDto);
				}

			}
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
		hmxCategoryContentTrans.setDesc(hmxCategoryContent.getDesc());
		hmxCategoryContentTrans.setSubTitle(hmxCategoryContent.getSubTitle());
		hmxCategoryContentTrans.setMode(hmxCategoryContent.getMode());
    	//查询视频信息
		String movieIds = "";
		HmxMovieExample hmxMovieExample = new HmxMovieExample();
		//hmxMovieExample.or().andCategoryContentIdIn(idArray);
		hmxMovieExample.or().andCategoryContentIdEqualTo(categoryContentId+"");
		hmxMovieExample.setOrderByClause("serie");
		List<HmxMovie> hmxMovieList = hmxMovieMapper.selectByExample(hmxMovieExample);
		if(hmxMovieList != null && hmxMovieList.size() > 0){
			hmxCategoryContentTrans.setMovieList(hmxMovieList);
			for(HmxMovie movie : hmxMovieList){
				movieIds += movie.getVideoId()+",";
			}
		}

		if(!StringUtils.isEmpty(movieIds) && movieIds.endsWith(movieIds)){
			movieIds = movieIds.substring(0,(movieIds.length() - 1));
		}
		hmxCategoryContentTrans.setMovieId(movieIds);
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
		HmxImagesExample hmxImagesExample = new HmxImagesExample();
		hmxImagesExample.or().andCategoryContentIdEqualTo(categoryContentId);
		List<HmxImages> hmxImagesList = hmxImagesMapper.selectByExample(hmxImagesExample);
		if(null != hmxImagesList && hmxImagesList.size() > 0){
			hmxCategoryContentTrans.setImagesList(hmxImagesList);
			imageUrl= hmxImagesList.get(0).getImageUrl();
		}
		hmxCategoryContentTrans.setContentImages(imageUrl);
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
		if(hmxCategoryContentDto.getMode() != 0){
			parameter.put("mode", hmxCategoryContentDto.getMode());
		}
		if(!StringUtils.isEmpty(hmxCategoryContentDto.getSubTitle())){
			parameter.put("subTitle", hmxCategoryContentDto.getSubTitle());
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
    		//获取视频链接
    		if(resultMap.get("videoList") != null){
    			List<Map<String,Object>> videoList = (List<Map<String,Object>>)resultMap.get("videoList");
    			if(videoList != null && videoList.size()>0){
    				for(Map<String,Object> video:videoList){
    					video.put("videoUrl", null);
    					if(video.get("videoId") != null){
    						Map<String,Object> resultUrl = initVodClients.getUrl(video.get("videoId").toString());
    						boolean flag = Boolean.parseBoolean(resultUrl.get("flag").toString());
    						if(flag){
    							video.put("videoUrl", resultUrl.get("url"));
    						}
    					}
    				}
    			}
    		}
    	}
		//查询内容下的视频信息
		String movieIds = "";
		HmxMovieExample hmxMovieExample = new HmxMovieExample();
		//hmxMovieExample.or().andCategoryContentIdIn(idArray);
		hmxMovieExample.or().andCategoryContentIdEqualTo(categoryContentId+"");
		hmxMovieExample.setOrderByClause("serie");
		List<HmxMovie> hmxMovieList = hmxMovieMapper.selectByExample(hmxMovieExample);
		if(hmxMovieList != null && hmxMovieList.size() > 0){
			for(HmxMovie movie : hmxMovieList){
				movieIds += movie.getVideoId()+",";
			}
		}

		if(!StringUtils.isEmpty(movieIds) && movieIds.endsWith(movieIds)){
			movieIds = movieIds.substring(0,(movieIds.length() - 1));
		}
		resultMap.put("videoId",movieIds);
		//查询内容下的pdf的url
		String fileUrl = "";
		HmxFilesExample hmxFilesExample = new HmxFilesExample();
		hmxFilesExample.or().andCategoryContentIdEqualTo(categoryContentId);
		List<HmxFiles> hmxFilesList = hmxFilesMapper.selectByExample(hmxFilesExample);
		if(null != hmxFilesList && hmxFilesList.size() > 0){
			fileUrl = hmxFilesList.get(0).getFileUrl();
		}
		resultMap.put("fileUrl",fileUrl);
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
		if(hmxCategoryContentDto.getMode() != null && hmxCategoryContentDto.getMode() > 0){
			parameter.put("mode", hmxCategoryContentDto.getMode());
		}
		if(!StringUtils.isEmpty(hmxCategoryContentDto.getSubTitle())){
			parameter.put("subTitle", hmxCategoryContentDto.getSubTitle());
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

	@Override
	public PageBean<Map<String, Object>> search(PageBean<Map<String, Object>> page, String contentValue) {
		Map<String,Object> parameter = new HashMap<String,Object>();
		parameter.put("offset", page.getStartOfPage());
		parameter.put("limit", page.getPageSize());
		parameter.put("state", DataState.正常.getState());
		parameter.put("categoryTitle", contentValue);
		Integer count = hmxCategoryContentMapper.countCategoryContentTable(parameter);
		Boolean haveData = page.setTotalNum((int)(long)count);
		if(!haveData){
			return page;
		}
		List<Map<String,Object>> data = hmxCategoryContentMapper.selectCategoryContentTable(parameter);
		page.setPage(data);
		return page;
	}


	@Override
	public PageBean<Map<String, Object>> seniorSearch(PageBean<Map<String, Object>> page, HmxCategoryContentDto hmxCategoryContentDto) {
		Map<String,Object> parameter = new HashMap<String,Object>();
		parameter.put("offset", page.getStartOfPage());
		parameter.put("limit", page.getPageSize());
		parameter.put("state", DataState.正常.getState());
		if(!StringUtils.isEmpty(hmxCategoryContentDto.getCategoryTitle())){
			parameter.put("categoryTitle", hmxCategoryContentDto.getCategoryTitle());
		}
		parameter.put("categoryId", hmxCategoryContentDto.getCategoryId());

		Integer count = hmxCategoryContentMapper.countCategoryContentTable(parameter);
		Boolean haveData = page.setTotalNum((int)(long)count);
		if(!haveData){
			return page;
		}
		List<Map<String,Object>> data = hmxCategoryContentMapper.selectCategoryContentTable(parameter);
		page.setPage(data);
		return page;
	}

	@Override
	public Map<String, Object> queryContentById(Integer categoryContentId) {
		Map<String,Object> resultMap = hmxCategoryContentMapper.selectContentInfoByContentId(categoryContentId);
		return resultMap;
	}

}
 
 