package com.hmx.system.service.impl;

import com.hmx.system.dao.TagtabMapper;
import com.hmx.system.dto.TagtabDto;
import com.hmx.system.entity.Tagtab;
import com.hmx.system.entity.TagtabExample;
import com.hmx.system.service.TagtabService;
import com.hmx.utils.result.PageBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by songjinbao on 2019/5/30.
 */
public class TagtabServiceImpl implements TagtabService {

    @Autowired
    private TagtabMapper tagtabMapper;

    @Override
    public Boolean insert(Tagtab tagtab) {
        return tagtabMapper.insert(tagtab) > 0;
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
            TagtabExample tagtabExample = new TagtabExample();
            tagtabExample.or().andTagtabIdIn( idArray );

            int ret = tagtabMapper.deleteByExample( tagtabExample );
            return ret > 0;
        }catch( Exception e ){
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public Boolean update(Tagtab tagtab) {
        return tagtabMapper.updateByPrimaryKeySelective( tagtab ) > 0;
    }

    @Override
    public Tagtab info(Integer tagtabId) {
        return tagtabMapper.selectByPrimaryKey( tagtabId );
    }

    @Override
    public PageBean<Tagtab> getPage(PageBean<Tagtab> page, TagtabDto tagtabDto) {
        TagtabExample tagtabExample = new TagtabExample();

        tagtabExample.setOffset(page.getStartOfPage());
        tagtabExample.setLimit(page.getPageSize());

        TagtabExample.Criteria where = tagtabExample.createCriteria();

        if ( tagtabDto.getTagId() != null && tagtabDto.getTagId() != 0 ) {
            where.andTagtabIdEqualTo( tagtabDto.getTagId() );
        }

        Integer count = tagtabMapper.countByExample( tagtabExample );

        boolean haveData = page.setTotalNum((int)(long)count);

        if(!haveData){
            return page;
        }

        List<Tagtab> data = tagtabMapper.selectByExample( tagtabExample );
        page.setPage(data);

        return page;
    }

    @Override
    public List<Tagtab> list(TagtabDto tagtabDto) {
       return  null;
    }
}
