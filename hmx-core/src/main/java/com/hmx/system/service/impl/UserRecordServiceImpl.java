package com.hmx.system.service.impl;

import com.hmx.system.dao.UserRecordMapper;
import com.hmx.system.dto.UserRecordDto;
import com.hmx.system.entity.UserRecord;
import com.hmx.system.entity.UserRecordExample;
import com.hmx.system.service.UserRecordService;
import com.hmx.utils.result.PageBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.*;

/**
 * Created by Administrator on 2019/6/12.
 */
@Service
public class UserRecordServiceImpl implements UserRecordService {
    @Autowired
    private UserRecordMapper userRecordMapper;

    @Override
    public Boolean insert(UserRecord userRecord) {
        return userRecordMapper.insertSelective( userRecord ) > 0;
    }

    @Override
    public Boolean deleteByIdArray(String ids) {

        return false;
    }

    @Override
    public Boolean update(UserRecord userRecord) {
        return true;
    }

    @Override
    public UserRecord info(Integer messageId) {
        return userRecordMapper.selectByPrimaryKey( messageId );
    }

    //type 1查询已读消息，2查询未读消息，0查询所有消息
    @Override
    public PageBean<UserRecord> getPage(PageBean<UserRecord> page, UserRecordDto userRecordDto,Integer typpe) {
        Map<String,Object> parameter = new HashMap<String,Object>();
        parameter.put("offset", page.getStartOfPage());
        parameter.put("limit", page.getPageSize());

//        UserRecordExample userRecordExample1 = new UserRecordExample();
//
//        userRecordExample.setOffset(page.getStartOfPage());
//        userRecordExample.setLimit(page.getPageSize());
//
//        UserRecordExample.Criteria where = userRecordExample.createCriteria();

        if ( userRecordDto.getContentId() != null && userRecordDto.getContentId() != 0 ) {
            parameter.put("contentId",userRecordDto.getContentId());
        }

        if (!StringUtils.isEmpty(userRecordDto.getUserId())) {
            parameter.put("userId",userRecordDto.getUserId());
        }

        Integer count = userRecordMapper.countContentTable( parameter );

        boolean haveData = page.setTotalNum((int)(long)count);

        if(!haveData){
            return page;
        }

        List<UserRecord> data = userRecordMapper.selectContentTable( parameter );

        page.setPage(data);

        return page;
    }

    @Override
    public List<UserRecord> list(UserRecordDto userRecordDto) {
        return null;
    }






}
