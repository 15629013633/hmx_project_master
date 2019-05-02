package com.hmx.system.controller;

import com.alibaba.druid.util.StringUtils;
import com.hmx.hotWords.dto.HotWordsDto;
import com.hmx.hotWords.entity.HotWords;
import com.hmx.hotWords.srvice.HotWordsService;
import com.hmx.utils.result.Config;
import com.hmx.utils.result.PageBean;
import com.hmx.utils.result.Result;
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
 * 热词类控制层
 * Created by Administrator on 2019/5/2.
 */
@RestController
@RequestMapping("/hotWords")
public class HotWordsController {

    @Autowired
    private HotWordsService hotWordsService;

    /**
     * 热词添加
     * @param hotWordsDto
     * @return
     */
    @PostMapping("/add")
    public ResultBean add(HotWordsDto hotWordsDto, HttpServletRequest request){

        ResultBean resultBean = new ResultBean();
        boolean flag=true;
        if(StringUtils.isEmpty(hotWordsDto.getTitle())){
            resultBean.setCode(Config.FAIL_FIELD_EMPTY).setContent("内容标题不能为空");
            flag=false;
        }
        if(0 == hotWordsDto.getSort()){
            resultBean.setCode(Config.FAIL_FIELD_EMPTY).setContent("热词排序字段不能为0也不能为空");
            flag=false;
        }
        if(flag){
            Map<String,Object> resultMap = hotWordsService.addHotWord(hotWordsDto);
            flag=Boolean.parseBoolean(resultMap.get("flag").toString());
            if(!flag){
                resultBean.setCode(Config.FAIL_CODE);
            }else{
                resultBean.setCode(Config.SUCCESS_CODE);
            }
            resultBean.setContent(resultMap.get("content").toString());

        }
        return resultBean;
    }

    /**
     * 热词修改
     * @param hotWordsDto
     * @return
     */
    @PostMapping("/edit")
    public ResultBean edit(HotWordsDto hotWordsDto, HttpServletRequest request){

        ResultBean resultBean = new ResultBean();
        boolean flag=true;
        if(StringUtils.isEmpty(hotWordsDto.getTitle())){
            resultBean.setCode(Config.FAIL_FIELD_EMPTY).setContent("内容标题不能为空");
            flag=false;
        }
        if(0 == hotWordsDto.getSort()){
            resultBean.setCode(Config.FAIL_FIELD_EMPTY).setContent("热词排序字段不能为0也不能为空");
            flag=false;
        }
        if(flag){
            Map<String,Object> resultMap = hotWordsService.editHotWord(hotWordsDto);
            flag=Boolean.parseBoolean(resultMap.get("flag").toString());
            if(!flag){
                resultBean.setCode(Config.FAIL_CODE);
            }else{
                resultBean.setCode(Config.SUCCESS_CODE);
            }
            resultBean.setContent(resultMap.get("content").toString());

        }
        return resultBean;
    }

    /**
     * 热词删除
     * @param ids
     * @return
     */
    @PostMapping("/delete")
    public ResultBean delete(String ids){

        ResultBean resultBean = new ResultBean();
        boolean flag=true;
        if(StringUtils.isEmpty(ids)){
            resultBean.setCode(Config.FAIL_FIELD_EMPTY).setContent("热词主键不能为空");
            flag=false;
        }
        if(flag){
            flag = hotWordsService.deleteByIdArray(ids);
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
     * @param hotWordsDto
     * @return
     */
    @GetMapping("/list")
    public ResultBean list(HotWordsDto hotWordsDto, PageBean<HotWords> page, Model model){
        page = hotWordsService.list(page, hotWordsDto);
        List<HotWords> list = page.getPage();
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
