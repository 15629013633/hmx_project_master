package com.hmx.system.service.impl;

import com.hmx.system.dao.CommentMapper;
import com.hmx.system.dto.CommentDto;
import com.hmx.system.entity.Comment;
import com.hmx.system.entity.CommentExample;
import com.hmx.system.service.CommentService;
import com.hmx.utils.result.PageBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.util.*;

/**
 * Created by Administrator on 2019/5/2.
 */
@Service
public class CommentServiceImpl implements CommentService{

    @Autowired
    private CommentMapper commentMapper;

    @Override
    public Boolean insert(Comment message) {
        return commentMapper.insertSelective( message ) > 0;
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
            CommentExample messageExample = new CommentExample();
            messageExample.or().andCommentIdIn( idArray );

            int ret = commentMapper.deleteByExample( messageExample );
            return ret > 0;
        }catch( Exception e ){
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public Boolean update(Comment comment) {
        return commentMapper.updateByPrimaryKeySelective( comment ) > 0;
    }

    @Override
    public Comment info(Integer messageId) {
        return commentMapper.selectByPrimaryKey( messageId );
    }

    @Override
    public PageBean<Comment> getPage(PageBean<Comment> page, CommentDto messageDto) {
        CommentExample commentExample = new CommentExample();

        commentExample.setOffset(page.getStartOfPage());
        commentExample.setLimit(page.getPageSize());

        CommentExample.Criteria where = commentExample.createCriteria();

        if ( messageDto.getCommentId() != null && messageDto.getCommentId() != 0 ) {
            where.andCommentIdEqualTo( messageDto.getCommentId() );
        }

        if(!StringUtils.isEmpty(messageDto.getUserId())){
            where.andUserIdEqualTo(messageDto.getUserId());
        }

        if(messageDto.getCategoryContentId() != null && messageDto.getCategoryContentId() != 0){
            where.andCategoryContentIdEqualTo(messageDto.getCategoryContentId());
        }

        Integer count = commentMapper.countByExample( commentExample );

        boolean haveData = page.setTotalNum((int)(long)count);

        if(!haveData){
            return page;
        }

        List<Comment> data = commentMapper.selectByExample( commentExample );

        page.setPage(data);

        return page;
    }

    @Override
    public List<Comment> list(CommentDto commentDto) {
        return null;
    }

    @Override
    public Map<String, Object> addComment(CommentDto commentDto) {
        Map<String,Object> resultMap = new HashMap<String,Object>();
        resultMap.put("flag", false);
        try {
            commentDto.setCreateDate(new Date());
            if(!insert(commentDto)){
                resultMap.put("content", "添加评论失败");
                return resultMap;
            }else {
                resultMap.put("flag", true);
                resultMap.put("content", "添加评论成功");
                return resultMap;
            }
        }catch (Exception e){
            e.printStackTrace();
            resultMap.put("content", "添加评论失败");
            return resultMap;
        }
    }

    @Override
    public Map<String, Object> editComment(CommentDto commentDto) {
        Map<String,Object> resultMap = new HashMap<String,Object>();
        resultMap.put("flag", false);
        try {
            if(!update(commentDto)){
                resultMap.put("content", "修改评论失败");
                return resultMap;
            }else {
                resultMap.put("flag", true);
                resultMap.put("content", "修改评论成功");
                return resultMap;
            }
        }catch (Exception e){
            e.printStackTrace();
            resultMap.put("content", "修改评论失败");
            return resultMap;
        }
    }

    @Override
    @Transactional
    public boolean updateStatus(String ids,String type) {
        try {
            String[] idsArr = ids.split(",");
            for(String id : idsArr){
                Integer commentId = Integer.valueOf(id);
                Comment comment = commentMapper.selectByPrimaryKey(commentId);
                if("shield".equals(type)){
                    comment.setStatus(0);
                }else {
                    comment.setStatus(1);
                }
                commentMapper.updateByPrimaryKey(comment);
            }
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
