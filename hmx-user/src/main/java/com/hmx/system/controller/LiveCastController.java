package com.hmx.system.controller;

import com.hmx.system.entity.LiveRequest;
import com.hmx.utils.result.Config;
import com.hmx.utils.result.ResultBean;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;

/**
 * 直播控制层
 * Created by songjinbao on 2019/7/17.
 */
@RestController
@RequestMapping("/liveCast")
public class LiveCastController {

    public ResultBean liveList(){

        LiveRequest liveRequest = new LiveRequest();
        liveRequest.setVersion("2016-11-01");
        liveRequest.setAccessKeyId("");
        liveRequest.setSignature("");
        liveRequest.setSignatureMethod("HMAC-SHA1");
        liveRequest.setTimestamp((new Date()) + "");
        liveRequest.setSignatureVersion("1.0");
        liveRequest.setSignatureNonce(System.currentTimeMillis()+"");
        liveRequest.setResourceOwnerAccount("");
        liveRequest.setFormat("json");
        return new ResultBean().put("liveList", liveRequest).setCode(Config.SUCCESS_CODE).setContent("查询直播列表成功");
    }
}
