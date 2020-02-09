package com.hmx.system.service.impl;

import com.hmx.system.base.BaseServieImpl;
import com.hmx.system.dao.AreaMapper;
import com.hmx.system.entity.AreaModel;
import com.hmx.system.service.AreaService;
import com.hmx.utils.result.PageBean;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;

@Service
public class AreaServiceImpl extends BaseServieImpl<AreaModel> implements AreaService{
//public class AreaServiceImpl implements AreaService {

    @Resource
    private AreaMapper areaMapper;

    public PageBean<AreaModel> getPage(PageBean<AreaModel> page, AreaModel areaModel) {
        try {
            HashMap <String,Object> params=new HashMap<String,Object>();
            page.setLimitAndOffset(params);
            params.put("orderByClause"," city asc ");
            if (!StringUtils.isEmpty(areaModel.getProvice())){
                params.put("provice",areaModel.getProvice());
            }
//            Integer count = areaMapper.count( params);
            Integer count = baseMapper.count( params);

            boolean haveData = page.setTotalNum((int)(long)count);

            if(!haveData){
                return page;
            }
//            List<AreaModel> data = areaMapper.selectPage( params );
            List<AreaModel> data = baseMapper.selectPage( params );
            page.setPage(data);
        }catch (Exception e){
            e.printStackTrace();
        }

        return page;
    }


}
