package com.hmx.system.controller;

import com.hmx.aop.Operation;
import com.hmx.hotWords.dto.HotWordsDto;
import com.hmx.hotWords.entity.HotWords;
import com.hmx.hotWords.srvice.HotWordsService;
import com.hmx.utils.result.Config;
import com.hmx.utils.result.PageBean;
import com.hmx.utils.result.ResultBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * Created by Administrator on 2019/5/2.
 */
@RestController
@RequestMapping("/hotWords")
public class HotWordsController {

    @Autowired
    private HotWordsService hotWordsService;

    /**
     * 热词列表
     * @param hotWordsDto
     * @return
     */
    @GetMapping("/list")
    public ResultBean list(HotWordsDto hotWordsDto, PageBean<HotWords> page, Model model){
        hotWordsDto.setType(0);
        page = hotWordsService.list(page, hotWordsDto);
        List<HotWords> list = page.getPage();
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

    /**
     * 热词列表
     * @param hotWordsDto
     * @return
     */
    @GetMapping("/rankList")
    public ResultBean rankList(HotWordsDto hotWordsDto, PageBean<HotWords> page, Model model){
        //hotWordsDto.setType(0);
        page = hotWordsService.rankList(page, hotWordsDto);
        List<HotWords> list = page.getPage();
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
