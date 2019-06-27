package com.hmx.system.service.impl;

import com.hmx.system.dao.ThumbsUpMapper;
import com.hmx.system.dto.ThumbsUpDto;
import com.hmx.system.entity.ThumbsUp;
import com.hmx.system.entity.ThumbsUpExample;
import com.hmx.system.service.ThumbsUpService;
import com.hmx.utils.result.PageBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2019/6/27.
 */
@Service
public class ThumbsUpServiceImpl implements ThumbsUpService {

    @Autowired
    private ThumbsUpMapper thumbsUpMapper;
    
    @Override
    public Boolean insert(ThumbsUp thumbsUp) {
        List<ThumbsUp> upList = selectByuserPhoneAndContentId(thumbsUp.getUserPhone(),thumbsUp.getContentId());
        if(null != upList && upList.size() > 0){
            return true;
        }
        return thumbsUpMapper.insert(thumbsUp) > 0;
    }

    @Override
    public Boolean deleteByIdArray(String userPhone,Integer contentId) {


//        String[] arrayStr = null ;
        try{
            List<Integer> idArray = new ArrayList<Integer>();
            List<ThumbsUp> thumbsUpList = selectByuserPhoneAndContentId(userPhone,contentId);
            if(null != thumbsUpList && thumbsUpList.size() > 0){
                for(ThumbsUp thumbsUp : thumbsUpList){
                    idArray.add(thumbsUp.getThumbsId());
                }
            }
            ThumbsUpExample upExample = new ThumbsUpExample();
            upExample.or().andThumbsIdIn(idArray);

            int ret = thumbsUpMapper.deleteByExample( upExample );
            return ret > 0;
        }catch( Exception e ){
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public Boolean update(ThumbsUp thumbsUp) {
        return thumbsUpMapper.updateByPrimaryKeySelective( thumbsUp ) > 0;
    }

    @Override
    public ThumbsUp info(Integer thumbsId) {
        return thumbsUpMapper.selectByPrimaryKey( thumbsId );
    }

    @Override
    public List<ThumbsUp> selectByuserPhoneAndContentId(String userPhone,Integer contentId) {
        ThumbsUpExample upExample = new ThumbsUpExample();
        ThumbsUpExample.Criteria where = upExample.createCriteria();
        where.andUserPhoneEqualTo(userPhone);
        where.andContentIdEqualTo(contentId);
        return thumbsUpMapper.selectByExample( upExample );
    }

    @Override
    public int getThumbsCount(ThumbsUpDto thumbsUpDto) {
        ThumbsUpExample example = new ThumbsUpExample();

        ThumbsUpExample.Criteria where = example.createCriteria();
        where.andContentIdEqualTo(thumbsUpDto.getContentId());
        return thumbsUpMapper.countByExample(example);
    }

    @Override
    public PageBean<ThumbsUp> getPage(PageBean<ThumbsUp> page, ThumbsUpDto thumbsUpDto) {
//        ScoreModelExample scoreModelExample = new ScoreModelExample();
//
//        scoreModelExample.setOffset(page.getStartOfPage());
//        scoreModelExample.setLimit(page.getPageSize());
//
//        ScoreModelExample.Criteria where = scoreModelExample.createCriteria();
//
//        if ( scoreModelDto.getScoreId() != null && scoreModelDto.getScoreId() != 0 ) {
//            where.andScoreIdEqualTo( scoreModelDto.getScoreId() );
//        }
//
//        if(!StringUtils.isEmpty(scoreModelDto.getUserPhone())){
//            where.andUserPhoneEqualTo(scoreModelDto.getUserPhone());
//        }
//
//        if(!StringUtils.isEmpty(scoreModelDto.getContentId())){
//            where.andContentIdEqualTo(scoreModelDto.getContentId());
//        }
//
//        Integer count = thumbsUpMapper.countByExample( scoreModelExample );
//
//        boolean haveData = page.setTotalNum((int)(long)count);
//
//        if(!haveData){
//            return page;
//        }
//
//        List<ScoreModel> data = thumbsUpMapper.selectByExample( scoreModelExample );
//        page.setPage(data);

        return page;
    }

    @Override
    public List<ThumbsUp> list(ThumbsUpDto thumbsUpDto) {
        return  null;
    }
}
