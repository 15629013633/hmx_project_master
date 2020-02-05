package com.hmx.system.service.impl;

import com.hmx.system.dao.SysManageMapper;
import com.hmx.system.dto.SysManageDto;
import com.hmx.system.entity.SysManage;
import com.hmx.system.entity.SysManageExample;
import com.hmx.system.service.SysManageService;
import com.hmx.utils.result.PageBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class SysManageServiceImpl implements SysManageService {
    @Autowired
    private SysManageMapper sysManageMapper;

    @Override
    public int insert(SysManage sysManage) {
        try {
            return sysManageMapper.insert(sysManage) ;
        }catch (Exception e){
            e.printStackTrace();
        }

        return 0;
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
//            SysManageExample sysManageExample = new SysManageExample();
//            tagtabExample.or().andTagtabIdIn( idArray );
//
//            int ret = tagtabMapper.deleteByExample( tagtabExample );
            return true;
        }catch( Exception e ){
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public Boolean update(SysManage sysManage) {
        try {
            return sysManageMapper.updateByPrimaryKeySelective( sysManage ) > 0;
        }catch (Exception e){
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public SysManage info(Integer id) {
        return sysManageMapper.selectByPrimaryKey( id );
    }

    @Override
    public PageBean<SysManage> getPage(PageBean<SysManage> page, SysManageDto sysManageDto) {
//        TagtabExample tagtabExample = new TagtabExample();
//
//        tagtabExample.setOffset(page.getStartOfPage());
//        tagtabExample.setLimit(page.getPageSize());
//
//        TagtabExample.Criteria where = tagtabExample.createCriteria();
//
//        if ( tagtabDto.getTagId() != null && tagtabDto.getTagId() != 0 ) {
//            where.andTagtabIdEqualTo( tagtabDto.getTagId() );
//        }
//
//        Integer count = tagtabMapper.countByExample( tagtabExample );
//
//        boolean haveData = page.setTotalNum((int)(long)count);
//
//        if(!haveData){
//            return page;
//        }
//
//        List<Tagtab> data = tagtabMapper.selectByExample( tagtabExample );
//        page.setPage(data);

        return page;
    }

    @Override
    public List<SysManage> list(SysManageDto tagtabDto) {
        SysManageExample example = new SysManageExample();
        return  sysManageMapper.selectByExample(example);
    }

}
