package com.hmx.system.service.impl;

import com.hmx.system.dao.GradeModelMapper;
import com.hmx.system.dto.GradeModelDto;
import com.hmx.system.entity.GradeModel;
import com.hmx.system.entity.GradeModelExample;
import com.hmx.system.service.GradeModelService;
import com.hmx.utils.result.PageBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2019/6/20.
 */
@Service
public class GradeModelServiceImpl implements GradeModelService {

    @Autowired
    private GradeModelMapper gradeModelMapper;

    @Override
    public int insert(GradeModel gradeModel) {
        gradeModelMapper.insert(gradeModel) ;
        return gradeModel.getGradeId();
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
            GradeModelExample tagtabExample = new GradeModelExample();
            tagtabExample.or().andGradeIdIn(idArray);

            int ret = gradeModelMapper.deleteByExample( tagtabExample );
            return ret > 0;
        }catch( Exception e ){
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public Boolean update(GradeModel gradeModel) {
        return gradeModelMapper.updateByPrimaryKeySelective( gradeModel ) > 0;
    }

    @Override
    public GradeModel info(Integer gradeId) {
        return gradeModelMapper.selectByPrimaryKey( gradeId );
    }

    @Override
    public PageBean<GradeModel> getPage(PageBean<GradeModel> page, GradeModelDto gradeModelDto) {
        GradeModelExample tagtabExample = new GradeModelExample();

        tagtabExample.setOffset(page.getStartOfPage());
        tagtabExample.setLimit(page.getPageSize());

        GradeModelExample.Criteria where = tagtabExample.createCriteria();

        if ( gradeModelDto.getGradeId() != null && gradeModelDto.getGradeId() != 0 ) {
            where.andGradeIdEqualTo( gradeModelDto.getGradeId() );
        }

        Integer count = gradeModelMapper.countByExample( tagtabExample );

        boolean haveData = page.setTotalNum((int)(long)count);

        if(!haveData){
            return page;
        }

        List<GradeModel> data = gradeModelMapper.selectByExample( tagtabExample );
        page.setPage(data);

        return page;
    }

    @Override
    public List<GradeModel> list(GradeModelDto gradeModelDto) {
        return  null;
    }
}
