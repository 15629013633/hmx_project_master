package com.hmx.system.controller;

import com.hmx.system.dto.MesgPushDto;
import com.hmx.system.dto.UserRecordDto;
import com.hmx.system.entity.MesgPush;
import com.hmx.system.entity.UserRecord;
import com.hmx.system.service.MesgPushService;
import com.hmx.utils.result.Config;
import com.hmx.utils.result.PageBean;
import com.hmx.utils.result.ResultBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.Map;

/**
 * 系统消息控制层
 * Created by Administrator on 2019/6/12.
 */
@RestController
@RequestMapping("/pcmesgPush")
public class MesgPushController {
    @Autowired
    private MesgPushService mesgPushService;

    private Logger logger = LoggerFactory.getLogger(MesgPushController.class);
    /**
     * type 1查询已读消息，2查询未读消息，0查询所有消息
     * 分页
     * 获取所有消息推送列表
     * @param mesgPushDto
     * @return
     */
    @GetMapping("/allList")
    public ResultBean allList(MesgPushDto mesgPushDto, UserRecordDto userRecordDto, PageBean<Map<String,Object>> page, Model model, Integer type){
        if(StringUtils.isEmpty(userRecordDto.getUserId())){
            return new ResultBean().setCode(Config.FAIL_FIELD_EMPTY).put("contentPage", page).setContent("用户id不能为空");
        }
        page = mesgPushService.allListPage(page, mesgPushDto,userRecordDto,type);
        System.out.println("111222222222222222222222222222222");
        logger.info("info,############################");
        logger.debug("info,############################");
        List<Map<String,Object>> list = page.getPage();
//        try {
//            int a = 1;
//            int b = 0;
//            int c = a/b;
//        }catch (Exception e){
//            System.out.println(e.getLocalizedMessage());
////            logger.error(e.getMessage());
//            e.printStackTrace();
//        }

        if(list == null || list.size() <= 0){
            if(page.getPageNum() == 1){
                return new ResultBean().setCode(Config.CONTENT_NULL).put("contentPage", page).setContent("暂无数据");
            }
            else{
                return new ResultBean().setCode(Config.PAGE_NULL).put("contentPage", page).setContent("没有更多数据了");
            }
        }
        return new ResultBean().put("contentPage", page).setCode(Config.SUCCESS_CODE).setContent("查询标签列表成功");
    }
}
