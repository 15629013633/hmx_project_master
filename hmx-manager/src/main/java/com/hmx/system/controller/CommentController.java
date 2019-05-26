package com.hmx.system.controller;

import com.alibaba.druid.util.StringUtils;
import com.hmx.system.dto.CommentDto;
import com.hmx.system.entity.Comment;
import com.hmx.system.entity.CommentModel;
import com.hmx.system.service.CommentService;
import com.hmx.utils.result.Config;
import com.hmx.utils.result.PageBean;
import com.hmx.utils.result.ResultBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * 评论控制层
 * Created by Administrator on 2019/5/2.
 */
@RestController
@RequestMapping("/comment")
public class CommentController {

    @Autowired
    private CommentService commentService;

    /**
     * 获取所有评论列表
     * @param commentDto
     * @return
     */
    @GetMapping("/AllList")
    public ResultBean list(CommentDto commentDto, PageBean<CommentModel> page, Model model){
        page = commentService.getPage(page, commentDto);
        List<CommentModel> list = page.getPage();
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
     * 屏蔽评论
     * @param ids
     * @return
     */
    @PostMapping("/shieldCommon")
    public ResultBean shieldCommon(String ids){

        ResultBean resultBean = new ResultBean();
        boolean flag=true;
        if(StringUtils.isEmpty(ids)){
            resultBean.setCode(Config.FAIL_FIELD_EMPTY).setContent("评论主键不能为空");
            flag=false;
        }
        if(flag){
            flag = commentService.updateStatus(ids,"shield");
            if(!flag){
                resultBean.setCode(Config.FAIL_CODE).setContent("屏蔽失败");
                return resultBean;
            }else{
                resultBean.setCode(Config.SUCCESS_CODE).setContent("屏蔽成功");
                return resultBean;
            }
        }
        return resultBean;
    }

    /**
     * 打开评论
     * @param ids
     * @return
     */
    @PostMapping("/openCommon")
    public ResultBean openCommon(String ids){

        ResultBean resultBean = new ResultBean();
        boolean flag=true;
        if(StringUtils.isEmpty(ids)){
            resultBean.setCode(Config.FAIL_FIELD_EMPTY).setContent("评论主键不能为空");
            flag=false;
        }
        if(flag){
            flag = commentService.updateStatus(ids,"open");
            if(!flag){
                resultBean.setCode(Config.FAIL_CODE).setContent("开放评论失败");
                return resultBean;
            }else{
                resultBean.setCode(Config.SUCCESS_CODE).setContent("开放评论成功");
                return resultBean;
            }
        }
        return resultBean;
    }
}
