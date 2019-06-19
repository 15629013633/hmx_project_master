package com.hmx.system.controller;

import com.hmx.images.dto.HmxImagesDto;
import com.hmx.images.entity.HmxImages;
import com.hmx.images.service.HmxImagesService;
import com.hmx.utils.result.Config;
import com.hmx.utils.result.PageBean;
import com.hmx.utils.result.ResultBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by songjinbao on 2019/6/17.
 */
@RestController
@RequestMapping("/hmxImage")
public class HmxImagesController {

    @Autowired
    private HmxImagesService hmxImagesService;

    /**
     * 获取系统中最新的N张图片
     * @param hmxImagesDto
     * @return
     */
    @GetMapping("/getImagesList")
    public ResultBean list(HmxImagesDto hmxImagesDto, PageBean<HmxImages> page, Model model){
        page = hmxImagesService.getPage(page, hmxImagesDto,"manage");
        List<HmxImages> list = page.getPage();
        if(list == null || list.size() <= 0){
            if(page.getPageNum() == 1){
                return new ResultBean().setCode(Config.CONTENT_NULL).put("contentPage", page).setContent("暂无数据");
            }
            else{
                return new ResultBean().setCode(Config.PAGE_NULL).put("contentPage", page).setContent("没有更多数据了");
            }
        }
        List<HmxImages> resultList = new ArrayList<>();
        for(int i = 0; i < list.size(); i++){
            if(resultList.size() == 5){
                break;
            }
            HmxImages images = list.get(i);
            if(!StringUtils.isEmpty(images.getImageUrl())){
                resultList.add(images);
                continue;
            }
            images.setImageUrl(StringUtils.isEmpty(images.getTransImage()) ? images.getVerticalImage() : images.getTransImage());
            resultList.add(images);
//            if(!StringUtils.isEmpty(images.getTransImage())){
//                resultList.add(images);
//                continue;
//            }
//            if(!StringUtils.isEmpty(images.getVerticalImage())){
//                resultList.add(images);
//                continue;
//            }
        }
        page.setPage(resultList);
        return new ResultBean().put("contentPage", page).setCode(Config.SUCCESS_CODE).setContent("查询消息列表成功");
    }
}
