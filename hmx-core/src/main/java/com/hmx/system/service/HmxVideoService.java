package com.hmx.system.service;

import com.hmx.system.dto.HmxVideoDto;
import com.hmx.system.entity.HmxVideo;

import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2019/5/21.
 */
public interface HmxVideoService {
    /**
     * @Method: insert
     * @Description: 添加
     * @param hmxVideo 要添加的对象
     * @return
     */
    Boolean insert ( HmxVideo hmxVideo);



    /**
     * @Method: list
     * @Description: 查询某个条件下的所有数据
     * @param hmxVideoDto 查询参数
     * @return List<HmxFiles> 符合条件的list集合
     */
    List<HmxVideo> list(HmxVideoDto hmxVideoDto );

    /**
     * @Method: update
     * @Description: 修改
     * @param hmxVideo 要修改的对象
     * @return true 修改成功  false 修改失败
     */
    Boolean update ( HmxVideo hmxVideo );
}
