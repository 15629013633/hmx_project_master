package com.hmx.system.controller;

import com.hmx.system.entity.LiveRequest;
import com.hmx.utils.result.Config;
import com.hmx.utils.result.ResultBean;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 直播控制层
 * Created by songjinbao on 2019/7/17.
 */
@RestController
@RequestMapping("/liveCast")
public class LiveCastController {

    public ResultBean liveList(){

        LiveRequest liveRequest = new LiveRequest();
        liveRequest.setVersion("");
        liveRequest.setAccessKeyId("");
        liveRequest.setSignature("");
        liveRequest.setSignatureMethod("");
        liveRequest.setTimestamp("");
        liveRequest.setSignatureVersion("");
        liveRequest.setSignatureNonce("");
        liveRequest.setResourceOwnerAccount("");
        liveRequest.setFormat("");
        return new ResultBean().put("liveList", liveRequest).setCode(Config.SUCCESS_CODE).setContent("查询直播列表成功");
    }
}
