package com.hmx.system.service.impl;

import com.hmx.category.dao.HmxCategoryContentMapper;
import com.hmx.category.dto.HmxCategoryContentDto;
import com.hmx.images.dao.HmxImagesMapper;
import com.hmx.images.entity.HmxImages;
import com.hmx.system.dao.HmxGovMapper;
import com.hmx.system.dto.HmxGovDto;
import com.hmx.system.entity.HmxGov;
import com.hmx.system.entity.HmxGovExample;
import com.hmx.system.service.HmxGovService;
import com.hmx.utils.result.PageBean;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.util.*;

/**
 * Created by Administrator on 2019/7/10.
 */
@Service
public class HmxGovServiceImpl implements HmxGovService {

    @Autowired
    private HmxGovMapper hmxGovMapper;

    @Autowired
    private HmxCategoryContentMapper hmxCategoryContentMapper;

    @Autowired
    private HmxImagesMapper hmxImagesMapper;

    @Override
    public Boolean insert(HmxGov hmxGov) {
        return hmxGovMapper.insertSelective( hmxGov ) > 0;
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
            HmxGovExample hmxGovExample = new HmxGovExample();
            hmxGovExample.or().andCommentIdIn( idArray );

            int ret = hmxGovMapper.deleteByExample( hmxGovExample );
            return ret > 0;
        }catch( Exception e ){
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public Boolean update(HmxGov comment) {
        return hmxGovMapper.updateByPrimaryKey( comment ) > 0;
    }

    @Override
    public HmxGov info(Integer messageId) {
        return hmxGovMapper.selectByPrimaryKey( messageId );
    }

    @Override
    public PageBean<HmxGov> getPage(PageBean<HmxGov> page, HmxGovDto hmxGovDto) {
        HmxGovExample commentExample = new HmxGovExample();

        commentExample.setOffset(page.getStartOfPage());
        commentExample.setLimit(page.getPageSize());

        HmxGovExample.Criteria where = commentExample.createCriteria();

//        if ( messageDto.getCommentId() != null && messageDto.getCommentId() != 0 ) {
//            where.andCommentIdEqualTo( messageDto.getCommentId() );
//        }

        Integer count = hmxGovMapper.countByExample( commentExample );

        boolean haveData = page.setTotalNum((int)(long)count);

        if(!haveData){
            return page;
        }

        List<HmxGov> data = hmxGovMapper.selectByExample( commentExample );

        page.setPage(data);

        return page;
    }

    @Override
    public List<HmxGov> list(HmxGovDto hmxGovDto) {
        return null;
    }

    @Override
    public Map<String, Object> addComment(HmxGovDto hmxGovDto) {
        Map<String,Object> resultMap = new HashMap<String,Object>();
        return resultMap;
    }

    @Override
    public Map<String, Object> editComment(HmxGovDto hmxGovDto) {
        Map<String,Object> resultMap = new HashMap<String,Object>();
        return resultMap;
    }

    @Override
    @Transactional
    public boolean updateStatus(String ids,String type) {
//        try {
//            String[] idsArr = ids.split(",");
//            for(String id : idsArr){
//                Integer commentId = Integer.valueOf(id);
//                Comment comment = commentMapper.selectByPrimaryKey(commentId);
//                if("shield".equals(type)){
//                    comment.setStatus(0);
//                }else {
//                    comment.setStatus(1);
//                }
//                commentMapper.updateByPrimaryKey(comment);
//            }
//            return true;
//        }catch (Exception e){
//            e.printStackTrace();
//        }
        return false;
    }

    @Override
    @Transactional
    public boolean examination(String ids) {
        try {
            String[] idsArr = ids.split(",");
            if(null != idsArr && idsArr.length > 0){
                for(String id : idsArr){
                    HmxGov hmxGov = hmxGovMapper.selectByPrimaryKey( Integer.valueOf(id) );
                    if(null != hmxGov && hmxGov.getStatus() == 0){
                        HmxCategoryContentDto hmxCategoryContentDto = new HmxCategoryContentDto();
                        hmxCategoryContentDto.setCategoryTitle(hmxGov.getCategoryTitle());
                        hmxCategoryContentDto.setCategoryContent(hmxGov.getCategoryContent());
                        hmxCategoryContentDto.setCreateTime(new Date());
                        int num = hmxCategoryContentMapper.insert(hmxCategoryContentDto);
                        if(num > 0){
                            Integer contentId = hmxCategoryContentDto.getCategoryContentId();
                            HmxImages images = new HmxImages();
                            images.setCategoryContentId(contentId);
                            images.setImageUrl(hmxGov.getContentImages());
                            images.setCreateTime(new Date());
                            hmxImagesMapper.insert(images);
                        }
                        hmxGov.setStatus(1);
                        hmxGovMapper.updateByPrimaryKey(hmxGov);
                    }

                }
            }
            return true;
        }catch (Exception e){
            e.printStackTrace();
            return false;
        }
    }


}
