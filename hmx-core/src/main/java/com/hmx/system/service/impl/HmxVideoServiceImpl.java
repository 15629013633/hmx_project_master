package com.hmx.system.service.impl;

import com.hmx.system.dao.HmxVideoMapper;
import com.hmx.system.dto.HmxVideoDto;
import com.hmx.system.entity.HmxVideo;
import com.hmx.system.entity.HmxVideoExample;
import com.hmx.system.service.HmxVideoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.List;

/**
 * Created by Administrator on 2019/5/21.
 */
@Service
public class HmxVideoServiceImpl implements HmxVideoService {

    @Autowired
    private HmxVideoMapper hmxVideoMapper;

    @Override
    public Boolean insert(HmxVideo hmxVideo) {
        return hmxVideoMapper.insert(hmxVideo)>0;
    }

    @Override
    public List<HmxVideo> list(HmxVideoDto hmxVideoDto) {
        HmxVideoExample videoExample = new HmxVideoExample();
        HmxVideoExample.Criteria where = videoExample.createCriteria();
        if(!StringUtils.isEmpty(hmxVideoDto.getVideoId())){
            where.andVideoIdEqualTo(hmxVideoDto.getVideoId());
        }
        if(!StringUtils.isEmpty(hmxVideoDto.getJobId())){
            where.andJobIdEqualTo(hmxVideoDto.getJobId());
        }
        if(!StringUtils.isEmpty(hmxVideoDto.getDefinition())){
            where.andDefinitionEqualTo(hmxVideoDto.getDefinition());
        }

        List<HmxVideo> hmxVideoList = hmxVideoMapper.selectByExample(videoExample);
        return hmxVideoList;
    }

    @Override
    public Boolean update(HmxVideo hmxVideo) {
        return hmxVideoMapper.updateByPrimaryKeySelective( hmxVideo ) > 0;
    }
}
