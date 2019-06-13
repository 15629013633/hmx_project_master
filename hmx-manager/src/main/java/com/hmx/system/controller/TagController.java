package com.hmx.system.controller;

import com.hmx.system.dto.TagtabDto;
import com.hmx.system.entity.Tagtab;
import com.hmx.system.service.TagtabService;
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
import java.util.ArrayList;
import java.util.List;
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
     * 分页
     * 获取所有评论列表
     * @param tagtabDto
     * @return
     */
    @GetMapping("/tagListPage")
    public ResultBean tagListPage(TagtabDto tagtabDto, PageBean<Tagtab> page, Model model){
        page = tagtabService.getPage(page, tagtabDto);
        List<Tagtab> list = page.getPage();
        if(list == null || list.size() <= 0){
            if(page.getPageNum() == 1){
                return new ResultBean().setCode(Config.CONTENT_NULL).setContent("暂无数据");
            }
            else{
                return new ResultBean().setCode(Config.PAGE_NULL).setContent("没有更多数据了");
            }
        }
        return new ResultBean().put("contentPage", page).setCode(Config.SUCCESS_CODE).setContent("查询标签列表成功");
    }

    /**
     * 不分页
     * 获取所有评论列表
     * @param tagtabDto
     * @return
     */
    @GetMapping("/tagList")
    public ResultBean tagList(TagtabDto tagtabDto, PageBean<Tagtab> page, Model model){
        page.setPageNum(1);
        page.setPageSize(100);
        page = tagtabService.getPage(page, tagtabDto);
        List<Tagtab> list = page.getPage();
        if(list == null || list.size() <= 0){
            if(page.getPageNum() == 1){
                return new ResultBean().setCode(Config.CONTENT_NULL).setContent("暂无数据");
            }
            else{
                return new ResultBean().setCode(Config.PAGE_NULL).setContent("没有更多数据了");
            }
        }
        return new ResultBean().put("tagList", list).setCode(Config.SUCCESS_CODE).setContent("查询标签列表成功");
    }

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
            int tagId = tagtabService.insert(tagtabDto);

            if(0 != tagId){
                resultBean.setCode(Config.SUCCESS_CODE).put("tagId",tagId).setContent("添加成功");
            }else {
                resultBean.setCode(Config.FAIL_CODE).put("tagId",tagId).setContent("添加失败");
            }

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
