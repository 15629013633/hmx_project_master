package com.hmx.system.controller;

import com.alibaba.druid.util.StringUtils;
import com.hmx.aop.Operation;
import com.hmx.images.dto.HmxImagesDto;
import com.hmx.images.entity.HmxImages;
import com.hmx.images.service.HmxImagesService;
import com.hmx.utils.result.Config;
import com.hmx.utils.result.PageBean;
import com.hmx.utils.result.ResultBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;

/**
 * Created by songjinbao on 2019/6/19.
 */
@RestController
@RequestMapping("/manageImages")
public class ImagesController {

    @Autowired
    private HmxImagesService hmxImagesService;

    /**
     * 热词添加
     * @param hmxImagesDto
     * @return
     */
    @PostMapping("/add")
    @Operation("图片添加")
    public ResultBean add(HmxImagesDto hmxImagesDto, HttpServletRequest request){

        ResultBean resultBean = new ResultBean();
        boolean flag=true;
        if(StringUtils.isEmpty(hmxImagesDto.getTitle())){
            resultBean.setCode(Config.FAIL_FIELD_EMPTY).setContent("标题不能为空");
            flag=false;
        }
        if(flag){
            flag = hmxImagesService.insert(hmxImagesDto);
            if(!flag){
                resultBean.setCode(Config.FAIL_CODE).setContent("添加失败");
            }else{
                resultBean.setCode(Config.SUCCESS_CODE).setContent("添加成功");
            }


        }
        return resultBean;
    }

    /**
     * 热词添加
     * @param hmxImagesDto
     * @return
     */
    @PostMapping("/edit")
    @Operation("图片修改")
    public ResultBean edit(HmxImagesDto hmxImagesDto, HttpServletRequest request){

        ResultBean resultBean = new ResultBean();
        boolean flag=true;
        if(StringUtils.isEmpty(hmxImagesDto.getTitle())){
            resultBean.setCode(Config.FAIL_FIELD_EMPTY).setContent("标题不能为空");
            flag=false;
        }
        if(flag){
            flag = hmxImagesService.update(hmxImagesDto);
            if(!flag){
                resultBean.setCode(Config.FAIL_CODE).setContent("修改失败");
            }else{
                resultBean.setCode(Config.SUCCESS_CODE).setContent("修改成功");
            }


        }
        return resultBean;
    }

    /**
     * 热词删除
     * @param ids
     * @return
     */
    @PostMapping("/delete")
    @Operation("图片删除")
    public ResultBean delete(String ids){

        ResultBean resultBean = new ResultBean();
        boolean flag=true;
        if(StringUtils.isEmpty(ids)){
            resultBean.setCode(Config.FAIL_FIELD_EMPTY).setContent("主键不能为空");
            flag=false;
        }
        if(flag){
            flag = hmxImagesService.deleteByIdArray(ids);
            if(!flag){
                resultBean.setCode(Config.FAIL_CODE).setContent("删除失败");
                return resultBean;
            }else{
                resultBean.setCode(Config.SUCCESS_CODE).setContent("删除成功");
                return resultBean;
            }
        }
        return resultBean;
    }

    /**
     * 热词列表
     * @param hmxImagesDto
     * @return
     */
    @GetMapping("/list")
    public ResultBean list(HmxImagesDto hmxImagesDto, PageBean<HmxImages> page, Model model){
        page = hmxImagesService.getPage(page,hmxImagesDto,"manage");
        List<HmxImages> list = page.getPage();
        if(list == null || list.size() <= 0){
            if(page.getPageNum() == 1){
                return new ResultBean().setCode(Config.CONTENT_NULL).setContent("暂无数据");
            }
            else{
                return new ResultBean().setCode(Config.PAGE_NULL).setContent("没有更多数据了");
            }
        }
        return new ResultBean().put("contentPage", page).setCode(Config.SUCCESS_CODE).setContent("查询内容列表成功");
    }
}
