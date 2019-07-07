package com.hmx.system.controller;

import com.hmx.aop.Operation;
import com.hmx.system.dto.MesgPushDto;
import com.hmx.system.entity.MesgPush;
import com.hmx.system.service.MesgPushService;
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
 * X消息推送控制层
 * Created by songjinbao on 2019/6/12.
 */
@RestController
@RequestMapping("/mesgPush")
public class MesgPushController {

    @Autowired
    private MesgPushService mesgPushService;



    /**
     * 分页
     * 获取所有消息推送列表
     * @param mesgPushDto
     * @return
     */
    @GetMapping("/tagListPage")
    @Operation("获取推送消息列表")
    public ResultBean tagListPage(MesgPushDto mesgPushDto, PageBean<MesgPush> page, Model model){
        page = mesgPushService.getPage(page, mesgPushDto);
        List<MesgPush> list = page.getPage();
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
     * 不分页  给pc端
     * 获取所有评论列表
     * @param tagtabDto
     * @return
     */
//    @GetMapping("/tagList")
//    public ResultBean tagList(TagtabDto tagtabDto, PageBean<Tagtab> page, Model model){
//        page.setPageNum(1);
//        page.setPageSize(100);
//        page = tagtabService.getPage(page, tagtabDto);
//        List<Tagtab> list = page.getPage();
//        if(list == null || list.size() <= 0){
//            if(page.getPageNum() == 1){
//                return new ResultBean().setCode(Config.CONTENT_NULL).setContent("暂无数据");
//            }
//            else{
//                return new ResultBean().setCode(Config.PAGE_NULL).setContent("没有更多数据了");
//            }
//        }
//        return new ResultBean().put("tagList", list).setCode(Config.SUCCESS_CODE).setContent("查询标签列表成功");
//    }

    /**
     * 增加标签
     * @param mesgPushDto
     * @return
     */
    @PostMapping("/add")
    @Operation("添加推送消息")
    public ResultBean add(MesgPushDto mesgPushDto, HttpServletRequest request){

        ResultBean resultBean = new ResultBean();
        boolean flag=true;

        if(flag){
            mesgPushDto.setCreateTime(System.currentTimeMillis());
            flag = mesgPushService.insert(mesgPushDto);
            if(!flag){
                resultBean.setCode(Config.FAIL_CODE).setContent("添加失败");
            }else{
                resultBean.setCode(Config.SUCCESS_CODE).setContent("添加成功");
            }

        }
        return resultBean;
    }

    /**
     * 修改标签
     * @param mesgPushDto
     * @return
     */
    @PostMapping("/edit")
    @Operation("修改推送消息")
    public ResultBean edit(MesgPushDto mesgPushDto, HttpServletRequest request){

        ResultBean resultBean = new ResultBean();
        boolean flag=true;
        if(null == mesgPushDto.getContentId() || 0 == mesgPushDto.getContentId()){
            resultBean.setCode(Config.FAIL_FIELD_EMPTY).setContent("内容id不能为空");
            flag=false;
        }
        if(flag){
            flag = mesgPushService.update(mesgPushDto);
            if(!flag){
                resultBean.setCode(Config.FAIL_CODE).setContent("修改失败");
            }else{
                resultBean.setCode(Config.SUCCESS_CODE).setContent("修改成功");
            }

        }
        return resultBean;
    }

    /**
     * 增加标签
     * @param ids  标签id，以逗号分隔
     * @return
     */
    @PostMapping("/delete")
    @Operation("删除推送消息")
    public ResultBean delete(String ids, HttpServletRequest request){

        ResultBean resultBean = new ResultBean();
        boolean flag=true;
        if(StringUtils.isEmpty(ids)){
            resultBean.setCode(Config.FAIL_FIELD_EMPTY).setContent("内容ids不能为空");
            flag=false;
        }
        if(flag){
            flag = mesgPushService.deleteByIdArray(ids);
            if(!flag){
                resultBean.setCode(Config.FAIL_CODE).setContent("删除失败");
            }else{
                resultBean.setCode(Config.SUCCESS_CODE).setContent("删除成功");
            }

        }
        return resultBean;
    }

    /**
     * 推送内容
     * @param ids  标签id，以逗号分隔
     * @return
     */
    @PostMapping("/jpush")
    @Operation("推送消息")
    public ResultBean jpush(String ids, HttpServletRequest request){

        ResultBean resultBean = new ResultBean();
        boolean flag=true;
        if(StringUtils.isEmpty(ids)){
            resultBean.setCode(Config.FAIL_FIELD_EMPTY).setContent("内容ids不能为空");
            flag=false;
        }
        if(flag){
            flag = mesgPushService.jpush(ids);
            if(!flag){
                resultBean.setCode(Config.FAIL_CODE).setContent("推送失败");
            }else{
                resultBean.setCode(Config.SUCCESS_CODE).setContent("推送成功");
            }

        }
        return resultBean;
    }


}
