package com.hmx.system.controller;

import com.hmx.system.dto.TagtabDto;
import com.hmx.system.service.TagtabService;
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
 * 标签控制层
 * Created by Administrator on 2019/5/30.
 */
@RestController
@RequestMapping("/tag")
public class TagController {
    @Autowired
    private TagtabService tagtabService;

    /**
     * 增加标签
     * @param tagtabDto
     * @return
     */
    @PostMapping("/add")
    public ResultBean add(TagtabDto tagtabDto, HttpServletRequest request){

        ResultBean resultBean = new ResultBean();
        boolean flag=true;
        if(StringUtils.isEmpty(tagtabDto.getTagName())){
            resultBean.setCode(Config.FAIL_FIELD_EMPTY).setContent("标签名称不能为空");
            flag=false;
        }
        if(flag){
            tagtabDto.setCreateTime(System.currentTimeMillis());
            flag = tagtabService.insert(tagtabDto);
            if(!flag){
                resultBean.setCode(Config.FAIL_CODE);
            }else{
                resultBean.setCode(Config.SUCCESS_CODE);
            }
            resultBean.setContent("添加成功");

        }
        return resultBean;
    }

    /**
     * 修改标签
     * @param tagtabDto
     * @return
     */
    @PostMapping("/edit")
    public ResultBean edit(TagtabDto tagtabDto, HttpServletRequest request){

        ResultBean resultBean = new ResultBean();
        boolean flag=true;
        if(StringUtils.isEmpty(tagtabDto.getTagName())){
            resultBean.setCode(Config.FAIL_FIELD_EMPTY).setContent("标签名称不能为空");
            flag=false;
        }
        if(null == tagtabDto.getTagId() || 0 == tagtabDto.getTagId()){
            resultBean.setCode(Config.FAIL_FIELD_EMPTY).setContent("标签id不能为空");
            flag=false;
        }
        if(flag){
            flag = tagtabService.update(tagtabDto);
            if(!flag){
                resultBean.setCode(Config.FAIL_CODE);
            }else{
                resultBean.setCode(Config.SUCCESS_CODE);
            }
            resultBean.setContent("修改成功");

        }
        return resultBean;
    }

    /**
     * 增加标签
     * @param ids  标签id，以逗号分隔
     * @return
     */
    @PostMapping("/delete")
    public ResultBean delete(String ids, HttpServletRequest request){

        ResultBean resultBean = new ResultBean();
        boolean flag=true;
        if(StringUtils.isEmpty(ids)){
            resultBean.setCode(Config.FAIL_FIELD_EMPTY).setContent("标签ids不能为空");
            flag=false;
        }
        if(flag){
            flag = tagtabService.deleteByIdArray(ids);
            if(!flag){
                resultBean.setCode(Config.FAIL_CODE);
            }else{
                resultBean.setCode(Config.SUCCESS_CODE);
            }
            resultBean.setContent("删除成功");

        }
        return resultBean;
    }
}
