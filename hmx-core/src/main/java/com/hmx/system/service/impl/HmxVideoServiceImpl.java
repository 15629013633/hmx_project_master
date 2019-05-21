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
        if(!StringUtils.isEmpty(hmxVideoDto.getVideoId())){
            videoExample.or().andVideoIdEqualTo(hmxVideoDto.getVideoId());
        }
        if(!StringUtils.isEmpty(hmxVideoDto.getJobId())){
            videoExample.or().andJobIdEqualTo(hmxVideoDto.getJobId());
        }
        List<HmxVideo> hmxVideoList = hmxVideoMapper.selectByExample(videoExample);
        return hmxVideoList;
    }
}
