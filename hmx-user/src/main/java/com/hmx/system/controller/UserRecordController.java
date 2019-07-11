package com.hmx.system.controller;

import com.hmx.system.dto.UserRecordDto;
import com.hmx.system.entity.UserRecord;
import com.hmx.system.service.UserRecordService;
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
 * 用户阅读系统消息记录控制层
 * Created by Administrator on 2019/6/12.
 */
@RestController
@RequestMapping("/userRecord")
public class UserRecordController {

    @Autowired
    private UserRecordService userRecordService;

    /**
     * 增加评论
     * @param userRecordDto
     * @return
     */
    @PostMapping("/add")
    public ResultBean add(UserRecordDto userRecordDto, HttpServletRequest request){

        ResultBean resultBean = new ResultBean();
        boolean flag=userRecordService.insert(userRecordDto);
        if(!flag){
            resultBean.setCode(Config.FAIL_CODE).setContent("添加阅读消息失败");
        }
        resultBean.setCode(Config.SUCCESS_CODE).setContent("添加阅读消息成功");
        return resultBean;
    }

    /**
     * 获取某个内容下的所有评论
     * type 1查询已读消息，2查询未读消息，0查询所有消息
     * @param userRecordDto
     * @return
     */
    @GetMapping("/userRecordList")
    public ResultBean list(UserRecordDto userRecordDto, PageBean<UserRecord> page, Model model,Integer type){
        page = userRecordService.getPage(page, userRecordDto,type);
        List<UserRecord> list = page.getPage();
        if(list == null || list.size() <= 0){
            if(page.getPageNum() == 1){
                return new ResultBean().setCode(Config.CONTENT_NULL).put("contentPage", page).setContent("暂无数据");
            }
            else{
                return new ResultBean().setCode(Config.PAGE_NULL).put("contentPage", page).setContent("没有更多数据了");
            }
        }
        return new ResultBean().put("contentPage", page).setCode(Config.SUCCESS_CODE).setContent("查询消息列表成功");
    }
}
