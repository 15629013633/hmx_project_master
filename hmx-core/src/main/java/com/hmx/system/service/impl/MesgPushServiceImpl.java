package com.hmx.system.service.impl;

import com.hmx.system.dao.MesgPushMapper;
import com.hmx.system.dto.MesgPushDto;
import com.hmx.system.entity.MesgPush;
import com.hmx.system.entity.MesgPushExample;
import com.hmx.system.service.MesgPushService;
import com.hmx.utils.result.PageBean;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.util.*;

/**
 * Created by Administrator on 2019/6/11.
 */
@Service
public class MesgPushServiceImpl implements MesgPushService {

    @Autowired
    private MesgPushMapper mesgPushMapper;

    @Override
    public Boolean insert(MesgPush mesgPush) {
        return mesgPushMapper.insertSelective( mesgPush ) > 0;
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
            MesgPushExample mesgPushExample = new MesgPushExample();
            mesgPushExample.or().andContentIdIn( idArray );

            int ret = mesgPushMapper.deleteByExample( mesgPushExample );
            return ret > 0;
        }catch( Exception e ){
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public Boolean update(MesgPush mesgPush) {
        return mesgPushMapper.updateByPrimaryKeySelective( mesgPush ) > 0;
    }

    @Override
    public MesgPush info(Integer contentId) {
        return mesgPushMapper.selectByPrimaryKey( contentId );
    }

    @Override
    public PageBean<MesgPush> getPage(PageBean<MesgPush> page, MesgPushDto mesgPushDto) {
        MesgPushExample mesgPushExample = new MesgPushExample();

        mesgPushExample.setOffset(page.getStartOfPage());
        mesgPushExample.setLimit(page.getPageSize());

        MesgPushExample.Criteria where = mesgPushExample.createCriteria();

        if ( mesgPushDto.getContentId() != null && mesgPushDto.getContentId() != 0 ) {
            where.andContentIdEqualTo( mesgPushDto.getContentId() );
        }

        if(mesgPushDto.getStatus() != null && mesgPushDto.getStatus() != 0){
            where.andStatusEqualTo(mesgPushDto.getStatus());
        }
        if(mesgPushDto.getContentTpye() != null && mesgPushDto.getContentTpye() != 0){
            where.andContentTpyeEqualTo(mesgPushDto.getContentTpye());
        }

        Integer count = mesgPushMapper.countByExample( mesgPushExample );

        boolean haveData = page.setTotalNum((int)(long)count);

        if(!haveData){
            return page;
        }

        List<MesgPush> data = mesgPushMapper.selectByExample( mesgPushExample );

        page.setPage(data);

        return page;
    }

    @Override
    public List<MesgPush> list(MesgPushDto mesgPushDto) {
        return null;
    }

    @Override
    public Map<String, Object> addComment(MesgPushDto mesgPushDto) {
        Map<String,Object> resultMap = new HashMap<String,Object>();
        resultMap.put("flag", false);
        try {
            mesgPushDto.setCreateTime(System.currentTimeMillis());
            if(!insert(mesgPushDto)){
                resultMap.put("content", "添加推送消息失败");
                return resultMap;
            }else {
                resultMap.put("flag", true);
                resultMap.put("content", "添加推送消息成功");
                return resultMap;
            }
        }catch (Exception e){
            e.printStackTrace();
            resultMap.put("content", "添加推送消息失败");
            return resultMap;
        }
    }

    @Override
    public Map<String, Object> editComment(MesgPushDto mesgPushDto) {
        Map<String,Object> resultMap = new HashMap<String,Object>();
        resultMap.put("flag", false);
        try {
            if(!update(mesgPushDto)){
                resultMap.put("content", "修改推送消息失败");
                return resultMap;
            }else {
                resultMap.put("flag", true);
                resultMap.put("content", "修改推送消息成功");
                return resultMap;
            }
        }catch (Exception e){
            e.printStackTrace();
            resultMap.put("content", "修改推送消息失败");
            return resultMap;
        }
    }

    @Override
    @Transactional
    public boolean updateStatus(String ids,String type) {
        try {
//            String[] idsArr = ids.split(",");
//            for(String id : idsArr){
//                Integer msgId = Integer.valueOf(id);
//                MesgPush mesgPush = mesgPushMapper.selectByPrimaryKey(msgId);
//                if("shield".equals(type)){
//                    mesgPush.setStatus(0);
//                }else {
//                    comment.setStatus(1);
//                }
//                commentMapper.updateByPrimaryKey(comment);
//            }
            return true;
        }catch (Exception e){
            e.printStackTrace();
        }
        return false;
    }

//    @Override
//    public PageBean<Comment> list(PageBean<Comment> page, CommentDto commentDto,Integer type) {
//        MessageExample messageExample = new MessageExample();
//        messageExample.setLimit(page.getPageSize());
//        messageExample.setOffset(page.getStartOfPage());
//        MessageExample.Criteria where = messageExample.createCriteria();
//        //
//        if(1 == type){//查询接收到的评论列表
//            messageExample.or().andTargeUserIdEqualTo(messageDto.getTargeUserId());
//        }else if(2 == type){//查询发送的评论列表
//            messageExample.or().andFromUserIdEqualTo(messageDto.getFromUserId());
//        }else if(3 == type){//查询自己未读评论
//            where.andTargeUserIdEqualTo(messageDto.getTargeUserId());
//            where.andStatusEqualTo(0);
//        } else if(4 == type){
//
//        } else {
//            return page;
//        }
//
//
//
//        Integer count = messageMappper.countByExample(messageExample);
//        Boolean haveData = page.setTotalNum((int)(long)count);
//        if(!haveData){
//            return page;
//        }
//        List<Message> data = messageMappper.selectByExample(messageExample);
//        page.setPage(data);
//        return page;
//    }
}
