package com.hmx.hotWords.srvice.impl;

import com.hmx.hotWords.dao.HotWordsMapper;
import com.hmx.hotWords.dto.HotWordsDto;
import com.hmx.hotWords.entity.HotWords;
import com.hmx.hotWords.entity.HotWordsExample;
import com.hmx.hotWords.srvice.HotWordsService;
import com.hmx.utils.enums.DataState;
import com.hmx.utils.result.PageBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.*;

/**
 * Created by Administrator on 2019/5/2.
 */
@Service
public class HotWordsServiceImpl implements HotWordsService{

    @Autowired
    private HotWordsMapper hotWordsMapper;

    @Override
    public Boolean insert(HotWords hotWords) {
        return hotWordsMapper.insertSelective( hotWords ) > 0;
    }

    @Override
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
            HotWordsExample hotWordsExample = new HotWordsExample();
            hotWordsExample.or().andHotWordIdIn( idArray );

            int ret = hotWordsMapper.deleteByExample( hotWordsExample );
            return ret > 0;
        }catch( Exception e ){
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public Boolean update(HotWords hotWords) {
        return hotWordsMapper.updateByPrimaryKeySelective( hotWords ) > 0;
    }

    @Override
    public HotWords info(Integer hmxFilesId) {
        return hotWordsMapper.selectByPrimaryKey( hmxFilesId );
    }

    @Override
    public PageBean<HotWords> getPage(PageBean<HotWords> page, HotWordsDto hotWordsDto) {
        HotWordsExample hotWordsExample = new HotWordsExample();

        hotWordsExample.setOffset(page.getStartOfPage());
        hotWordsExample.setLimit(page.getPageSize());

        HotWordsExample.Criteria where = hotWordsExample.createCriteria();

        if ( hotWordsDto.getHotWordId() != null && hotWordsDto.getHotWordId() != 0 ) {
            where.andHotWordIdEqualTo( hotWordsDto.getHotWordId() );
        }

        Integer count = hotWordsMapper.countByExample( hotWordsExample );

        boolean haveData = page.setTotalNum((int)(long)count);

        if(!haveData){
            return page;
        }

        List<HotWords> data = hotWordsMapper.selectByExample( hotWordsExample );

        page.setPage(data);

        return page;
    }

    @Override
    public List<HotWords> list(HotWordsDto hotWordsDto) {
        return null;
    }

    @Override
    public Map<String, Object> addHotWord(HotWordsDto hotWordsDto) {
        Map<String,Object> resultMap = new HashMap<String,Object>();
        resultMap.put("flag", false);
        try {
            hotWordsDto.setCreateDate(new Date());
            if(!insert(hotWordsDto)){
                resultMap.put("content", "添加热词失败");
                return resultMap;
            }else {
                resultMap.put("flag", true);
                resultMap.put("content", "添加热词成功");
                return resultMap;
            }
        }catch (Exception e){
            e.printStackTrace();
            resultMap.put("content", "添加热词失败");
            return resultMap;
        }
    }

    @Override
    public Map<String, Object> editHotWord(HotWordsDto hotWordsDto) {
        Map<String,Object> resultMap = new HashMap<String,Object>();
        resultMap.put("flag", false);
        try {
            if(!update(hotWordsDto)){
                resultMap.put("content", "修改热词失败");
                return resultMap;
            }else {
                resultMap.put("flag", true);
                resultMap.put("content", "修改热词成功");
                return resultMap;
            }
        }catch (Exception e){
            e.printStackTrace();
            resultMap.put("content", "修改热词失败");
            return resultMap;
        }
    }

    @Override
    public PageBean<HotWords> list(PageBean<HotWords> page, HotWordsDto hotWordsDto) {
        HotWordsExample hotWordsExample = new HotWordsExample();
        if(!StringUtils.isEmpty(hotWordsDto.getTitle())){
            hotWordsExample.or().andTitleLikeTo(hotWordsDto.getTitle());
        }
        hotWordsExample.setLimit(page.getPageSize());
        hotWordsExample.setOffset(page.getStartOfPage());
        hotWordsExample.setOrderByClause("sort");


        Integer count = hotWordsMapper.countByExample(hotWordsExample);
        Boolean haveData = page.setTotalNum((int)(long)count);
        if(!haveData){
            return page;
        }
        List<HotWords> data = hotWordsMapper.selectByExample(hotWordsExample);
        page.setPage(data);
        return page;
    }
}
