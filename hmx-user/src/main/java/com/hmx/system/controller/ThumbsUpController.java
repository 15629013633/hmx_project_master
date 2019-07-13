package com.hmx.system.controller;

import com.hmx.system.dto.ThumbsUpDto;
import com.hmx.system.entity.ThumbsUp;
import com.hmx.system.service.ThumbsUpService;
import com.hmx.utils.result.Config;
import com.hmx.utils.result.ResultBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 点赞
 * Created by Administrator on 2019/6/27.
 */
@RestController
@RequestMapping("/thumbsUp")
public class ThumbsUpController {

    @Autowired
    private ThumbsUpService thumbsUpService;

    /**
     * 增加评论
     * @param thumbsUpDto
     * @return
     */
    @PostMapping("/add")
    public ResultBean add(ThumbsUpDto thumbsUpDto, HttpServletRequest request){

        ResultBean resultBean = new ResultBean();
        boolean flag=true;
        if(StringUtils.isEmpty(thumbsUpDto.getUserPhone())){
            resultBean.setCode(Config.FAIL_FIELD_EMPTY).setContent("用户手机号不能为空");
            flag=false;
        }
        if(null == thumbsUpDto.getContentId() || thumbsUpDto.getContentId() == 0){
            resultBean.setCode(Config.FAIL_FIELD_EMPTY).setContent("评论内容Id不能为空");
            flag=false;
        }
        if(flag){
            flag = thumbsUpService.insert(thumbsUpDto);
            if(!flag){
                resultBean.setCode(Config.FAIL_CODE).setContent("点赞失败");
            }else{
                resultBean.setCode(Config.SUCCESS_CODE).setContent("点赞成功");
            }
        }else {
            resultBean.setCode(Config.FAIL_CODE).setContent("点赞失败");
        }
        return resultBean;
    }

    /**
     * 增加评论
     * @param thumbsUpDto
     * @return
     */
    @PostMapping("/delete")
    public ResultBean delete(ThumbsUpDto thumbsUpDto, HttpServletRequest request){

        ResultBean resultBean = new ResultBean();
        boolean flag=true;
        if(StringUtils.isEmpty(thumbsUpDto.getUserPhone())){
            resultBean.setCode(Config.FAIL_FIELD_EMPTY).setContent("用户手机号不能为空");
            flag=false;
        }
        if(null == thumbsUpDto.getContentId() || thumbsUpDto.getContentId() == 0){
            resultBean.setCode(Config.FAIL_FIELD_EMPTY).setContent("评论内容Id不能为空");
            flag=false;
        }
        if(flag){
            flag = thumbsUpService.deleteByIdArray(thumbsUpDto.getUserPhone(),thumbsUpDto.getContentId());
            if(!flag){
                resultBean.setCode(Config.FAIL_CODE).setContent("删除失败");
            }else{
                resultBean.setCode(Config.SUCCESS_CODE).setContent("删除成功");
            }
        }else {
            resultBean.setCode(Config.FAIL_CODE).setContent("删除失败");
        }
        return resultBean;
    }

    /**
     * 某个用户关于某内容是否点赞
     * @param thumbsUpDto
     * @return
     */
    @GetMapping("/getThumbsByInfo")
    public ResultBean getThumbsByInfo(ThumbsUpDto thumbsUpDto, HttpServletRequest request){
        Map<String, Object> result = new HashMap<>();
        ResultBean resultBean = new ResultBean();
        result.put("thumbsUp",null);
        resultBean.setResult(result);
        boolean flag=true;
        if(StringUtils.isEmpty(thumbsUpDto.getUserPhone())){
            resultBean.setCode(Config.FAIL_FIELD_EMPTY).setContent("用户手机号不能为空");
            flag=false;
        }
        if(null == thumbsUpDto.getContentId() || thumbsUpDto.getContentId() == 0){
            resultBean.setCode(Config.FAIL_FIELD_EMPTY).setContent("内容Id不能为空");
            flag=false;
        }
        if(flag){
            List<ThumbsUp> thumbsUpList = thumbsUpService.selectByuserPhoneAndContentId(thumbsUpDto.getUserPhone(),thumbsUpDto.getContentId());
            if(null != thumbsUpList && thumbsUpList.size() > 0){
                //return new ResultBean().put("thumbsUp", thumbsUpList.get(0)).setCode(Config.SUCCESS_CODE).setContent("查询成功");
                result.put("thumbsUp",thumbsUpList.get(0));
                resultBean.setResult(result).setCode(Config.SUCCESS_CODE);
            }else {
                resultBean.setResult(result).setCode(Config.SUCCESS_CODE);
            }
        }else {
            resultBean.setResult(result).setCode(Config.SUCCESS_CODE);
        }
        return resultBean;
    }

    /**
     * 某个内容点赞数
     * @param thumbsUpDto
     * @return
     */
    @GetMapping("/getThumbsCount")
    public ResultBean getThumbsCount(ThumbsUpDto thumbsUpDto, HttpServletRequest request){
        Map<String, Object> result = new HashMap<>();
        ResultBean resultBean = new ResultBean();
        result.put("count",0);
        resultBean.setResult(result);
        boolean flag=true;
        if(null == thumbsUpDto.getContentId() || thumbsUpDto.getContentId() == 0){
            resultBean.setCode(Config.FAIL_FIELD_EMPTY).setContent("内容Id不能为空");
            flag=false;
        }
        if(flag){
            int count = thumbsUpService.getThumbsCount(thumbsUpDto);
            result.put("count",count);
            resultBean.setResult(result).setCode(Config.SUCCESS_CODE);
        }else {
            resultBean.setResult(result).setCode(Config.SUCCESS_CODE);
        }
        return resultBean;
    }

}
