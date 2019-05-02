package com.hmx.system.controller;

import com.hmx.system.dto.MessageDto;
import com.hmx.system.entity.Message;
import com.hmx.system.service.MessageService;
import com.hmx.utils.result.Config;
import com.hmx.utils.result.PageBean;
import com.hmx.utils.result.ResultBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;

/**
 * 消息控制层
 * Created by Administrator on 2019/5/2.
 */
@RestController
@RequestMapping("/message")
public class MessageController {

    @Autowired
    private MessageService messageService;

    /**
     * 发送消息
     * @param messageDto
     * @return
     */
    @PostMapping("/add")
    public ResultBean add(MessageDto messageDto, HttpServletRequest request){

        ResultBean resultBean = new ResultBean();
        boolean flag=true;
        if(StringUtils.isEmpty(messageDto.getContent())){
            resultBean.setCode(Config.FAIL_FIELD_EMPTY).setContent("消息不能为空");
            flag=false;
        }
        if(StringUtils.isEmpty(messageDto.getFromUserId())){
            resultBean.setCode(Config.FAIL_FIELD_EMPTY).setContent("消息发送人Id不能为空");
            flag=false;
        }
        if(StringUtils.isEmpty(messageDto.getTargeUserId())){
            resultBean.setCode(Config.FAIL_FIELD_EMPTY).setContent("消息接收人Id不能为空");
            flag=false;
        }
        if(flag){
            Map<String,Object> resultMap = messageService.addMessage(messageDto);
            flag=Boolean.parseBoolean(resultMap.get("flag").toString());
            if(!flag){
                resultBean.setCode(Config.FAIL_CODE);
            }else{
                resultBean.setCode(Config.SUCCESS_CODE);
            }
            resultBean.setContent(resultMap.get("content").toString());

        }
        return resultBean;
    }

    /**
     * 读了消息就要更新消息状态
     * @param messageDto
     * @return
     */
    @PostMapping("/updateMesStatus")
    public ResultBean updateMessageStatus(MessageDto messageDto, HttpServletRequest request){

        ResultBean resultBean = new ResultBean();
        boolean flag=true;
        messageDto.setStatus(1);//设置为已读
        if(StringUtils.isEmpty(messageDto.getMessageId())){
            resultBean.setCode(Config.FAIL_FIELD_EMPTY).setContent("消息Id不能为空");
            flag=false;
        }
        if(flag){
            flag=messageService.update(messageDto);
            if(!flag){
                resultBean.setCode(Config.FAIL_CODE);
                resultBean.setContent("状态更新失败");
            }else{
                resultBean.setCode(Config.SUCCESS_CODE);
                resultBean.setContent("状态更新成功");
            }


        }
        return resultBean;
    }

    /**
     * 消息没有修改
     * @param messageDto
     * @return
     */
    @PostMapping("/edit")
    public ResultBean edit(MessageDto messageDto, HttpServletRequest request){

        ResultBean resultBean = new ResultBean();
//        boolean flag=true;
//        if(0 == messageDto.getHotWordId()){
//            resultBean.setCode(Config.FAIL_FIELD_EMPTY).setContent("热词id不能为空");
//            flag=false;
//        }
//        if(StringUtils.isEmpty(hotWordsDto.getTitle())){
//            resultBean.setCode(Config.FAIL_FIELD_EMPTY).setContent("热词标题不能为空");
//            flag=false;
//        }
//        if(0 == hotWordsDto.getSort()){
//            resultBean.setCode(Config.FAIL_FIELD_EMPTY).setContent("热词排序字段不能为0也不能为空");
//            flag=false;
//        }
//        if(flag){
//            Map<String,Object> resultMap = messageService.editHotWord(messageDto);
//            flag=Boolean.parseBoolean(resultMap.get("flag").toString());
//            if(!flag){
//                resultBean.setCode(Config.FAIL_CODE);
//            }else{
//                resultBean.setCode(Config.SUCCESS_CODE);
//            }
//            resultBean.setContent(resultMap.get("content").toString());
//
//        }
        return resultBean;
    }

    /**
     * 消息不能删除
     * @param ids
     * @return
     */
    @PostMapping("/delete")
    public ResultBean delete(String ids){

        ResultBean resultBean = new ResultBean();
//        boolean flag=true;
//        if(StringUtils.isEmpty(ids)){
//            resultBean.setCode(Config.FAIL_FIELD_EMPTY).setContent("热词主键不能为空");
//            flag=false;
//        }
//        if(flag){
//            flag = messageService.deleteByIdArray(ids);
//            if(!flag){
//                resultBean.setCode(Config.FAIL_CODE).setContent("删除失败");
//                return resultBean;
//            }else{
//                resultBean.setCode(Config.SUCCESS_CODE).setContent("删除成功");
//                return resultBean;
//            }
//        }
        return resultBean;
    }

    /**
     * type :
     *    1 查询接收到的消息列表    targeUserId 值传自己的用户id
     *    2 查询发送给别人的消息列表   fromUserId传自己的用户id
     * @param messageDto
     * @return
     */
    @GetMapping("/AllList")
    public ResultBean list(MessageDto messageDto, PageBean<Message> page, Model model,Integer type){
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

    /**
     * 查询自己未读消息
     *   type必须为3
     * @param messageDto
     * @return
     */
    @GetMapping("/unreadList")
    public ResultBean unreadList(MessageDto messageDto, PageBean<Message> page, Model model){
        page = messageService.list(page, messageDto,3);
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
