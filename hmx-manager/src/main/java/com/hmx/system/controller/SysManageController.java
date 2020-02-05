package com.hmx.system.controller;

import com.hmx.aop.Operation;
import com.hmx.system.dto.SysManageDto;
import com.hmx.system.entity.SysManage;
import com.hmx.system.service.SysManageService;
import com.hmx.utils.result.Config;
import com.hmx.utils.result.ResultBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * 系统内容管理控制层
 */
@RestController
@RequestMapping(value = "/management/sysManage")
public class SysManageController {
    @Autowired
    private SysManageService sysManageService;

    /**
     * 增加系统信息
     * @param sysManageDto
     * @return
     */
    @PostMapping("/add")
    @Operation("增加系统信息")
    public ResultBean add(SysManageDto sysManageDto, HttpServletRequest request){

        ResultBean resultBean = new ResultBean();
        if(StringUtils.isEmpty(sysManageDto.getHomeTitle())){
            return resultBean.setCode(Config.FAIL_CODE).setContent("添加失败,系统标题不能为空").put("content","添加失败,系统标题不能为空");
        }
        if(StringUtils.isEmpty(sysManageDto.getHomePic())){
            return resultBean.setCode(Config.FAIL_CODE).setContent("添加失败,系统图标不能为空").put("content","添加失败,系统图标不能为空");
        }
        int id = sysManageService.insert(sysManageDto);

        if(0 != id){
            resultBean.setCode(Config.SUCCESS_CODE).setContent("添加成功").put("content",sysManageDto);
        }else {
            resultBean.setCode(Config.FAIL_CODE).setContent("添加失败").put("content","");
        }
        return resultBean;
    }

    /**
     * 修改系统信息
     * @param sysManageDto
     * @return
     */
    @PostMapping("/edit")
    @Operation("修改系统信息")
    public ResultBean edit(SysManageDto sysManageDto, HttpServletRequest request){

        ResultBean resultBean = new ResultBean();
        resultBean.put("content","");
        if(StringUtils.isEmpty(sysManageDto.getHomeTitle())){
            return resultBean.setCode(Config.FAIL_CODE).setContent("添加失败,系统标题不能为空").put("content","添加失败,系统标题不能为空");
        }
        if(StringUtils.isEmpty(sysManageDto.getHomePic())){
            return resultBean.setCode(Config.FAIL_CODE).setContent("添加失败,系统图标不能为空").put("content","添加失败,系统图标不能为空");
        }
        if(null == sysManageDto.getSysId() || 0 == sysManageDto.getSysId()){
           return resultBean.setCode(Config.FAIL_FIELD_EMPTY).setContent("系统信息id不能为空");
        }
        boolean flag = sysManageService.update(sysManageDto);
        if(flag){
            resultBean.setCode(Config.SUCCESS_CODE).setContent("修改成功").put("content",sysManageDto);
        }else{
            resultBean.setCode(Config.FAIL_CODE).setContent("修改失败");
        }
        return resultBean;
    }

    /**
     * 修改系统信息
     * @param sysManageDto
     * @return
     */
    @GetMapping("/getSysManageInfo")
    @Operation("修改系统信息")
    public ResultBean getSysManageInfo(SysManageDto sysManageDto, HttpServletRequest request){

        ResultBean resultBean = new ResultBean();
        resultBean.put("content","");
        List<SysManage> list = sysManageService.list(sysManageDto);
        if(null != list && list.size() > 0){
            resultBean.setCode(Config.SUCCESS_CODE).setContent("获取成功").put("content",list.get(0));
        }else{
            resultBean.setCode(Config.FAIL_CODE).setContent("获取失败");
        }
        return resultBean;
    }
}
