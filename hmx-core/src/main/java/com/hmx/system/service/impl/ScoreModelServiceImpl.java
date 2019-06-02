package com.hmx.system.service.impl;

import com.hmx.system.dao.ScoreModelMapper;
import com.hmx.system.dto.ScoreModelDto;
import com.hmx.system.entity.ScoreModel;
import com.hmx.system.entity.ScoreModelExample;
import com.hmx.system.service.ScoreModelService;
import com.hmx.utils.result.PageBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2019/6/2.
 */
@Service
public class ScoreModelServiceImpl implements ScoreModelService {

    @Autowired
    private ScoreModelMapper scoreModelMapper;


    @Override
    public Boolean insert(ScoreModel scoreModel) {
        return scoreModelMapper.insert(scoreModel) > 0;
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
            ScoreModelExample scoreModelExample = new ScoreModelExample();
            scoreModelExample.or().andScoreIdIn( idArray );

            int ret = scoreModelMapper.deleteByExample( scoreModelExample );
            return ret > 0;
        }catch( Exception e ){
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public Boolean update(ScoreModel scoreModel) {
        return scoreModelMapper.updateByPrimaryKeySelective( scoreModel ) > 0;
    }

    @Override
    public ScoreModel info(Integer scoreId) {
        return scoreModelMapper.selectByPrimaryKey( scoreId );
    }

    @Override
    public PageBean<ScoreModel> getPage(PageBean<ScoreModel> page, ScoreModelDto scoreModelDto) {
        ScoreModelExample scoreModelExample = new ScoreModelExample();

        scoreModelExample.setOffset(page.getStartOfPage());
        scoreModelExample.setLimit(page.getPageSize());

        ScoreModelExample.Criteria where = scoreModelExample.createCriteria();

        if ( scoreModelDto.getScoreId() != null && scoreModelDto.getScoreId() != 0 ) {
            where.andScoreIdEqualTo( scoreModelDto.getScoreId() );
        }

        if(!StringUtils.isEmpty(scoreModelDto.getUserPhone())){
            where.andUserPhoneEqualTo(scoreModelDto.getUserPhone());
        }

        if(!StringUtils.isEmpty(scoreModelDto.getContentId())){
            where.andContentIdEqualTo(scoreModelDto.getContentId());
        }

        Integer count = scoreModelMapper.countByExample( scoreModelExample );

        boolean haveData = page.setTotalNum((int)(long)count);

        if(!haveData){
            return page;
        }

        List<ScoreModel> data = scoreModelMapper.selectByExample( scoreModelExample );
        page.setPage(data);

        return page;
    }

    @Override
    public List<ScoreModel> list(ScoreModelDto scoreModelDto) {
        return  null;
    }
}
