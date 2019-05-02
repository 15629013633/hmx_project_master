package com.hmx.system.controller;

import com.hmx.system.dto.CommentDto;
import com.hmx.system.service.CommentService;
import com.hmx.utils.result.Config;
import com.hmx.utils.result.ResultBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

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
     * 增加评论
     * @param commentDto
     * @return
     */
    @PostMapping("/add")
    public ResultBean add(CommentDto commentDto, HttpServletRequest request){

        ResultBean resultBean = new ResultBean();
        boolean flag=true;
        if(StringUtils.isEmpty(commentDto.getContent())){
            resultBean.setCode(Config.FAIL_FIELD_EMPTY).setContent("评论不能为空");
            flag=false;
        }
        if(StringUtils.isEmpty(commentDto.getCategoryContentId())){
            resultBean.setCode(Config.FAIL_FIELD_EMPTY).setContent("评论内容Id不能为空");
            flag=false;
        }
        if(StringUtils.isEmpty(commentDto.getUserId())){
            resultBean.setCode(Config.FAIL_FIELD_EMPTY).setContent("评论人Id不能为空");
            flag=false;
        }
        if(flag){
            Map<String,Object> resultMap = commentService.addComment(commentDto);
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
}
