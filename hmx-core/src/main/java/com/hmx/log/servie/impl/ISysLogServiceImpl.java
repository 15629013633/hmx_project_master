package com.hmx.log.servie.impl;

import com.hmx.log.dao.SysLogMapper;
import com.hmx.log.dto.SysLogDto;
import com.hmx.log.entity.SysLog;
import com.hmx.log.entity.SysLogExample;
import com.hmx.log.servie.ISysLogService;
import com.hmx.utils.result.PageBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2019/7/7.
 */
@Service
public class ISysLogServiceImpl implements ISysLogService {

    @Autowired
    private SysLogMapper sysLogMapper;

    @Override
    public Boolean insert(SysLog sysLog) {
        return sysLogMapper.insert(sysLog) > 0;
    }

    @Override
    public Boolean deleteByIdArray(String ids) {
//        List<Integer> idArray = new ArrayList<Integer>();
//        String[] arrayStr = null ;
//        try{
//            if( ids == null || ids == "" ){
//                return false;
//            }
//
//            if( ids.length() > 0 ){
//                arrayStr = ids.split(",");
//            }
//
//            for(String strid: arrayStr){
//                Integer id = Integer.parseInt(strid);
//                idArray.add(id);
//            }
//            ScoreModelExample scoreModelExample = new ScoreModelExample();
//            scoreModelExample.or().andScoreIdIn( idArray );
//
//            int ret = sysLogMapper.deleteByExample( scoreModelExample );
//            return ret > 0;
//        }catch( Exception e ){
//            e.printStackTrace();
//        }
        return false;
    }

    @Override
    public Boolean update(SysLog sysLog) {
        return sysLogMapper.updateByPrimaryKeySelective(sysLog) > 0;
    }

    @Override
    public SysLog info(Integer scoreId) {
        return sysLogMapper.selectByPrimaryKey( scoreId );
    }

    @Override
    public PageBean<SysLog> getPage(PageBean<SysLog> page, SysLogDto sysLogDto) {
        SysLogExample sysLogExample = new SysLogExample();

        sysLogExample.setOffset(page.getStartOfPage());
        sysLogExample.setLimit(page.getPageSize());

        SysLogExample.Criteria where = sysLogExample.createCriteria();



        if(!StringUtils.isEmpty(sysLogDto.getUserId())){
            where.andContentIdEqualTo(sysLogDto.getUserId());
        }

        Integer count = sysLogMapper.countByExample( sysLogExample );

        boolean haveData = page.setTotalNum((int)(long)count);

        if(!haveData){
            return page;
        }

        List<SysLog> data = sysLogMapper.selectByExample( sysLogExample );
        page.setPage(data);

        return page;
    }

    @Override
    public List<SysLog> list(SysLogDto sysLogDto) {
        return  null;
    }
}
