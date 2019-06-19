package com.hmx.files.service.impl;

import com.hmx.files.dao.HmxFilesMapper;
import com.hmx.files.dto.HmxFilesDto;
import com.hmx.files.entity.HmxFiles;
import com.hmx.files.entity.HmxFilesExample;
import com.hmx.files.service.HmxFilesService;
import com.hmx.utils.oss.upload.UploadUtil;
import com.hmx.utils.result.PageBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2019/4/26.
 */
@Service
@Transactional
public class HmxFilesServiceImpl implements HmxFilesService{

    @Autowired
    private HmxFilesMapper hmxFilesMapper;
    @Override
    public Boolean insert(HmxFiles hmxFiles) {
        return hmxFilesMapper.insertSelective( hmxFiles ) > 0;
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
            HmxFilesExample hmxFilesExample = new HmxFilesExample();
            hmxFilesExample.or().andFileIdIn( idArray );

            int ret = hmxFilesMapper.deleteByExample( hmxFilesExample );
            return ret > 0;
        }catch( Exception e ){
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public Boolean update(HmxFiles hmxFiles) {
        return hmxFilesMapper.updateByPrimaryKeySelective( hmxFiles ) > 0;
    }

    @Override
    public HmxFiles info(Integer hmxFilesId) {
        return hmxFilesMapper.selectByPrimaryKey( hmxFilesId );
    }

    @Override
    public PageBean<HmxFiles> getPage(PageBean<HmxFiles> page, HmxFilesDto hmxFilesDto) {
        HmxFilesExample hmxFilesExample = new HmxFilesExample();

        hmxFilesExample.setOffset(page.getStartOfPage());
        hmxFilesExample.setLimit(page.getPageSize());

        HmxFilesExample.Criteria where = hmxFilesExample.createCriteria();

        if ( hmxFilesDto.getFileId() != null && hmxFilesDto.getFileId() != 0 ) {
            where.andFileIdEqualTo( hmxFilesDto.getFileId() );
        }
        if ( StringUtils.isEmpty( hmxFilesDto.getFileUrl() ) ) {
            where.andFileUrlEqualTo( hmxFilesDto.getFileUrl() );
        }
        if ( hmxFilesDto.getCreateTime() != null ) {
            where.andCreateTimeEqualTo( hmxFilesDto.getCreateTime() );
        }
        if ( hmxFilesDto.getState() != null && hmxFilesDto.getState() != 0 ) {
            where.andStateEqualTo( hmxFilesDto.getState() );
        }
        if ( hmxFilesDto.getVersion() != null && hmxFilesDto.getVersion() != 0 ) {
            where.andVersionEqualTo( hmxFilesDto.getVersion() );
        }
        if ( hmxFilesDto.getCreateid() != null && hmxFilesDto.getCreateid() != 0 ) {
            where.andCreateidEqualTo( hmxFilesDto.getCreateid() );
        }

        Integer count = hmxFilesMapper.countByExample( hmxFilesExample );

        boolean haveData = page.setTotalNum((int)(long)count);

        if(!haveData){
            return page;
        }

        List<HmxFiles> data = hmxFilesMapper.selectByExample( hmxFilesExample );

        page.setPage(data);

        return page;
    }

    @Override
    public List<HmxFiles> list(HmxFilesDto HmxFilesDto) {
        return null;
    }

    @Override
    @Transactional
    public boolean deleteByFileUrl(String fileUrl) {
        boolean flag = false;
        if(fileUrl.endsWith("html")){
            try {
                flag = UploadUtil.deleteByFileUrl(fileUrl);
            }catch (Exception e){
                flag = false;
                e.printStackTrace();
            }

        }else {//删除epub
            flag = true;
        }

        if(true){
            HmxFilesExample hmxFilesExample = new HmxFilesExample();
            HmxFilesExample.Criteria where = hmxFilesExample.createCriteria();
            where.andFileUrlEqualTo( fileUrl );
            hmxFilesMapper.deleteByExample(hmxFilesExample);
            flag = true;
        }
        return  flag;
    }
}
