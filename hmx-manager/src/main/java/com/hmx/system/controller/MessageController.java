package com.hmx.system.controller;

import com.hmx.aop.Operation;
import com.hmx.system.dto.MessageDto;
import com.hmx.system.entity.Message;
import com.hmx.system.service.MessageService;
import com.hmx.utils.result.Config;
import com.hmx.utils.result.PageBean;
import com.hmx.utils.result.ResultBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * 消息实体类
 * Created by Administrator on 2019/5/2.
 */
@RestController
@RequestMapping("/message")
public class MessageController {
    @Autowired
    private MessageService messageService;

    /**
     * type :
     *    1 查询某个人接收到的消息列表    targeUserId 值传用户id
     *    2 查询某个人发送给别人的消息列表   fromUserId传用户id
     *    3 查询某个人收到的未读消息   targeUserId 值传用户id
     *    4 查询系统中所有的消息
     * @param messageDto
     * @return
     */
    @GetMapping("/AllList")
    @Operation("获取消息列表")
    public ResultBean list(MessageDto messageDto, PageBean<Message> page, Model model, Integer type){
        if(0 == type){
            type = 4;
        }
        page = messageService.list(page, messageDto,type);
        List<Message> list = page.getPage();
        if(list == null || list.size() <= 0){
            if(page.getPageNum() == 1){
                return new ResultBean().setCode(Config.CONTENT_NULL).setContent("暂无数据");
            }
            else{
                return new ResultBean().setCode(Config.PAGE_NULL).setContent("没有更多数据了");
            }
        }
        return new ResultBean().put("contentPage", page).setCode(Config.SUCCESS_CODE).setContent("查询消息列表成功");
    }
}
