package com.hmx.system.controller;

import com.hmx.system.dto.SourceModelDto;
import com.hmx.system.service.SourceModelService;
import com.hmx.utils.result.Config;
import com.hmx.utils.result.ResultBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

/**
 * 来源控制层
 * Created by Administrator on 2019/5/30.
 */
@RestController
@RequestMapping("/source")
public class SourceController {

    @Autowired
    private SourceModelService sourceModelService;

    /**
     * 增加来源
     * @param sourceModelDto
     * @return
     */
    @PostMapping("/add")
    public ResultBean add(SourceModelDto sourceModelDto, HttpServletRequest request){

        ResultBean resultBean = new ResultBean();
        boolean flag=true;
        if(StringUtils.isEmpty(sourceModelDto.getTitle())){
            resultBean.setCode(Config.FAIL_FIELD_EMPTY).setContent("来源名称不能为空");
            flag=false;
        }
        if(flag){
            sourceModelDto.setCreateTime(System.currentTimeMillis());
            flag = sourceModelService.insert(sourceModelDto);
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
     * 修改来源
     * @param sourceModelDto
     * @return
     */
    @PostMapping("/edit")
    public ResultBean edit(SourceModelDto sourceModelDto, HttpServletRequest request){

        ResultBean resultBean = new ResultBean();
        boolean flag=true;
        if(StringUtils.isEmpty(sourceModelDto.getTitle())){
            resultBean.setCode(Config.FAIL_FIELD_EMPTY).setContent("来源名称不能为空");
            flag=false;
        }
        if(null == sourceModelDto.getSourceId() || 0 == sourceModelDto.getSourceId()){
            resultBean.setCode(Config.FAIL_FIELD_EMPTY).setContent("来源id不能为空");
            flag=false;
        }
        if(flag){
            flag = sourceModelService.update(sourceModelDto);
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
     * 删除来源
     * @param ids  标签id，以逗号分隔
     * @return
     */
    @PostMapping("/delete")
    public ResultBean delete(String ids, HttpServletRequest request){

        ResultBean resultBean = new ResultBean();
        boolean flag=true;
        if(StringUtils.isEmpty(ids)){
            resultBean.setCode(Config.FAIL_FIELD_EMPTY).setContent("来源ids不能为空");
            flag=false;
        }
        if(flag){
            flag = sourceModelService.deleteByIdArray(ids);
            if(!flag){
                resultBean.setCode(Config.FAIL_CODE);
            }else{
                resultBean.setCode(Config.SUCCESS_CODE);
            }
            resultBean.setContent("修改成功");

        }
        return resultBean;
    }
}
