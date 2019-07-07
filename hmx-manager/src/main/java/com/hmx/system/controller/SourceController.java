package com.hmx.system.controller;

import com.hmx.aop.Operation;
import com.hmx.system.dto.SourceModelDto;
import com.hmx.system.entity.SourceModel;
import com.hmx.system.service.SourceModelService;
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
     * 分页
     * 获取所有评论列表
     * @param sourceModelDto
     * @return
     */
    @GetMapping("/sourceListPage")
    @Operation("获取分页来源列表")
    public ResultBean tagListPage(SourceModelDto sourceModelDto, PageBean<SourceModel> page, Model model){
        page = sourceModelService.getPage(page, sourceModelDto);
        List<SourceModel> list = page.getPage();
        if(list == null || list.size() <= 0){
            if(page.getPageNum() == 1){
                return new ResultBean().setCode(Config.CONTENT_NULL).setContent("暂无数据");
            }
            else{
                return new ResultBean().setCode(Config.PAGE_NULL).setContent("没有更多数据了");
            }
        }
        return new ResultBean().put("contentPage", page).setCode(Config.SUCCESS_CODE).setContent("查询来源列表成功");
    }

    /**
     * 不分页
     * 获取所有评论列表
     * @param sourceModelDto
     * @return
     */
    @GetMapping("/sourceList")
    @Operation("获取不分页来源列表")
    public ResultBean tagList(SourceModelDto sourceModelDto, PageBean<SourceModel> page, Model model){
        page.setPageNum(1);
        page.setPageSize(100);
        page = sourceModelService.getPage(page, sourceModelDto);
        List<SourceModel> list = page.getPage();
        if(list == null || list.size() <= 0){
            if(page.getPageNum() == 1){
                return new ResultBean().setCode(Config.CONTENT_NULL).setContent("暂无数据");
            }
            else{
                return new ResultBean().setCode(Config.PAGE_NULL).setContent("没有更多数据了");
            }
        }
        return new ResultBean().put("sourceList", list).setCode(Config.SUCCESS_CODE).setContent("查询来源列表成功");
    }

    /**
     * 增加来源
     * @param sourceModelDto
     * @return
     */
    @PostMapping("/add")
    @Operation("增加来源")
    public ResultBean add(SourceModelDto sourceModelDto, HttpServletRequest request){

        ResultBean resultBean = new ResultBean();
        boolean flag=true;
        if(StringUtils.isEmpty(sourceModelDto.getTitle())){
            resultBean.setCode(Config.FAIL_FIELD_EMPTY).setContent("来源名称不能为空");
            flag=false;
        }
        if(flag){
            sourceModelDto.setCreateTime(System.currentTimeMillis());
            int sourId = sourceModelService.insert(sourceModelDto);
            if(0 != sourId){
                resultBean.setCode(Config.SUCCESS_CODE).put("sourceId",sourId).setContent("添加成功");
            }else {
                resultBean.setCode(Config.FAIL_CODE).put("sourceId",sourId).setContent("添加失败");
            }

        }
        return resultBean;
    }

    /**
     * 修改来源
     * @param sourceModelDto
     * @return
     */
    @PostMapping("/edit")
    @Operation("修改来源")
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
    @Operation("删除来源")
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
            resultBean.setContent("删除成功");

        }
        return resultBean;
    }
}
