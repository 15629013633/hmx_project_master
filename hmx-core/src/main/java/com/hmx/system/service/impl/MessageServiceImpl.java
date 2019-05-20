package com.hmx.system.service.impl;

import com.hmx.system.dao.MessageMappper;
import com.hmx.system.dto.MessageDto;
import com.hmx.system.entity.Message;
import com.hmx.system.entity.MessageExample;
import com.hmx.system.service.MessageService;
import com.hmx.utils.result.PageBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

/**
 * Created by Administrator on 2019/5/2.
 */
@Service
public class MessageServiceImpl implements MessageService {

    @Autowired
    private MessageMappper messageMappper;

    @Override
    public Boolean insert(Message message) {
        return messageMappper.insertSelective( message ) > 0;
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
            MessageExample messageExample = new MessageExample();
            messageExample.or().andMessageIdIn( idArray );

            int ret = messageMappper.deleteByExample( messageExample );
            return ret > 0;
        }catch( Exception e ){
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public Boolean update(Message message) {
        return messageMappper.updateByPrimaryKeySelective( message ) > 0;
    }

    @Override
    public Message info(Integer messageId) {
        return messageMappper.selectByPrimaryKey( messageId );
    }

    @Override
    public PageBean<Message> getPage(PageBean<Message> page, MessageDto messageDto) {
        MessageExample messageExample = new MessageExample();

        messageExample.setOffset(page.getStartOfPage());
        messageExample.setLimit(page.getPageSize());

        MessageExample.Criteria where = messageExample.createCriteria();

        if ( messageDto.getMessageId() != null && messageDto.getMessageId() != 0 ) {
            where.andMessageIdEqualTo( messageDto.getMessageId() );
        }

        Integer count = messageMappper.countByExample( messageExample );

        boolean haveData = page.setTotalNum((int)(long)count);

        if(!haveData){
            return page;
        }

        List<Message> data = messageMappper.selectByExample( messageExample );

        page.setPage(data);

        return page;
    }

    @Override
    public List<Message> list(MessageDto messageDto) {
        return null;
    }

    @Override
    public Map<String, Object> addMessage(MessageDto messageDto) {
        Map<String,Object> resultMap = new HashMap<String,Object>();
        resultMap.put("flag", false);
        try {
            messageDto.setCreateDate(new Date());
            if(!insert(messageDto)){
                resultMap.put("content", "添加消息失败");
                return resultMap;
            }else {
                resultMap.put("flag", true);
                resultMap.put("content", "添加消息成功");
                return resultMap;
            }
        }catch (Exception e){
            e.printStackTrace();
            resultMap.put("content", "添加消息失败");
            return resultMap;
        }
    }

    @Override
    public Map<String, Object> editHotWord(MessageDto messageDto) {
        Map<String,Object> resultMap = new HashMap<String,Object>();
        resultMap.put("flag", false);
        try {
            if(!update(messageDto)){
                resultMap.put("content", "修改消息失败");
                return resultMap;
            }else {
                resultMap.put("flag", true);
                resultMap.put("content", "修改消息成功");
                return resultMap;
            }
        }catch (Exception e){
            e.printStackTrace();
            resultMap.put("content", "修改消息失败");
            return resultMap;
        }
    }

    @Override
    public PageBean<Message> list(PageBean<Message> page, MessageDto messageDto,Integer type) {
        MessageExample messageExample = new MessageExample();
        messageExample.setLimit(page.getPageSize());
        messageExample.setOffset(page.getStartOfPage());
        MessageExample.Criteria where = messageExample.createCriteria();
        //
        if(1 == type){//查询接收到的消息列表
            messageExample.or().andTargeUserIdEqualTo(messageDto.getTargeUserId());
        }else if(2 == type){//查询发送的消息列表
            messageExample.or().andFromUserIdEqualTo(messageDto.getFromUserId());
        }else if(3 == type){//查询自己未读消息
            where.andTargeUserIdEqualTo(messageDto.getTargeUserId());
            where.andStatusEqualTo(0);
        } else if(0 == type){//查询已读和未读的消息列表
            messageExample.or().andTargeUserIdEqualTo(messageDto.getTargeUserId());
        } else {
            return page;
        }



        Integer count = messageMappper.countByExample(messageExample);
        Boolean haveData = page.setTotalNum((int)(long)count);
        if(!haveData){
            return page;
        }
        List<Message> data = messageMappper.selectByExample(messageExample);
        page.setPage(data);
        return page;
    }
}
