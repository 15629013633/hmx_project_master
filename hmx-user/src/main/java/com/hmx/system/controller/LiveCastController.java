package com.hmx.system.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.hmx.live.entity.LiveModel;
import com.hmx.system.entity.LiveRequest;
import com.hmx.system.test.ALiYunSignUtils;
import com.hmx.utils.result.Config;
import com.hmx.utils.result.PageBean;
import com.hmx.utils.result.ResultBean;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;
import java.util.List;

/**
 * 直播控制层
 * Created by songjinbao on 2019/7/17.
 */
@RestController
@RequestMapping("/liveCast")
public class LiveCastController {

//    public ResultBean liveList(){
//
//        LiveRequest liveRequest = new LiveRequest();
//        liveRequest.setVersion("2016-11-01");
//        liveRequest.setAccessKeyId("");
//        liveRequest.setSignature("");
//        liveRequest.setSignatureMethod("HMAC-SHA1");
//        liveRequest.setTimestamp((new Date()) + "");
//        liveRequest.setSignatureVersion("1.0");
//        liveRequest.setSignatureNonce(System.currentTimeMillis()+"");
//        liveRequest.setResourceOwnerAccount("");
//        liveRequest.setFormat("json");
//        return new ResultBean().put("liveList", liveRequest).setCode(Config.SUCCESS_CODE).setContent("查询直播列表成功");
//    }

    /**
     * 获取直播列表
     * @return
     */
    @GetMapping(value="/liveList")
    public ResultBean liveList(){
        try {
            LiveModel liveResult =  ALiYunSignUtils.liveList();

            return new ResultBean().put("liveContent", liveResult).setCode(Config.SUCCESS_CODE).setContent("查询消息列表成功");
        }catch (Exception e){
            e.printStackTrace();
        }
        return new ResultBean().put("liveContent", "").setCode(Config.SUCCESS_CODE).setContent("查询消息列表成功");
    }

}
