package com.hmx.system.service.impl;

import com.hmx.system.dao.SourceModelMapper;
import com.hmx.system.dto.SourceModelDto;
import com.hmx.system.entity.SourceModel;
import com.hmx.system.entity.SourceModelExample;
import com.hmx.system.service.SourceModelService;
import com.hmx.utils.result.PageBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2019/5/30.
 */
@Service
public class SourceModelServiceImpl implements SourceModelService {
    @Autowired
    private SourceModelMapper sourceModelMapper;

    @Override
    public int insert(SourceModel sourceModel) {
        int count = sourceModelMapper.insert(sourceModel) ;
        int num = sourceModel.getSourceId();
        return num;
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
            SourceModelExample sourceModelExample = new SourceModelExample();
            sourceModelExample.or().andSourceIdIn( idArray );

            int ret = sourceModelMapper.deleteByExample( sourceModelExample );
            return ret > 0;
        }catch( Exception e ){
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public Boolean update(SourceModel sourceModel) {
        return sourceModelMapper.updateByPrimaryKeySelective( sourceModel ) > 0;
    }

    @Override
    public SourceModel info(Integer sourceId) {
        return sourceModelMapper.selectByPrimaryKey( sourceId );
    }

    @Override
    public PageBean<SourceModel> getPage(PageBean<SourceModel> page, SourceModelDto sourceModelDto) {
        SourceModelExample sourceModelExample = new SourceModelExample();

        sourceModelExample.setOffset(page.getStartOfPage());
        sourceModelExample.setLimit(page.getPageSize());

        SourceModelExample.Criteria where = sourceModelExample.createCriteria();

        if ( sourceModelDto.getSourceId() != null && sourceModelDto.getSourceId() != 0 ) {
            where.andSourceIdEqualTo( sourceModelDto.getSourceId() );
        }

        Integer count = sourceModelMapper.countByExample( sourceModelExample );

        boolean haveData = page.setTotalNum((int)(long)count);

        if(!haveData){
            return page;
        }

        List<SourceModel> data = sourceModelMapper.selectByExample( sourceModelExample );
        page.setPage(data);

        return page;
    }

    @Override
    public List<SourceModel> list(SourceModelDto sourceModelDto) {
        return  null;
    }
}
