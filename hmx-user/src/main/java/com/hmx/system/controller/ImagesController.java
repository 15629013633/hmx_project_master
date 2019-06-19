package com.hmx.system.controller;

import com.hmx.images.dto.HmxImagesDto;
import com.hmx.images.entity.HmxImages;
import com.hmx.images.service.HmxImagesService;
import com.hmx.utils.result.Config;
import com.hmx.utils.result.PageBean;
import com.hmx.utils.result.ResultBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * Created by songjinbao on 2019/6/19.
 */
@RestController
@RequestMapping("/userImages")
public class ImagesController {
    @Autowired
    private HmxImagesService hmxImagesService;

    /**
     * 热词列表
     * @param hmxImagesDto
     * @return
     */
    @GetMapping("/list")
    public ResultBean list(HmxImagesDto hmxImagesDto, PageBean<HmxImages> page, Model model){
        page = hmxImagesService.getPage(page,hmxImagesDto,"pc");
        List<HmxImages> list = page.getPage();
        if(list == null || list.size() <= 0){
            if(page.getPageNum() == 1){
                return new ResultBean().setCode(Config.CONTENT_NULL).put("contentPage", page).setContent("暂无数据");
            }
            else{
                return new ResultBean().setCode(Config.PAGE_NULL).put("contentPage", page).setContent("没有更多数据了");
            }
        }
        return new ResultBean().put("contentPage", page).setCode(Config.SUCCESS_CODE).setContent("查询内容列表成功");
    }
}
