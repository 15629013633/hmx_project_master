package com.hmx.system.controller;

import com.hmx.aop.Operation;
import com.hmx.category.service.HmxCategoryContentService;
import com.hmx.system.dto.HmxGovDto;
import com.hmx.system.entity.HmxGov;
import com.hmx.system.service.HmxGovService;
import com.hmx.utils.result.Config;
import com.hmx.utils.result.PageBean;
import com.hmx.utils.result.ResultBean;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * Created by Administrator on 2019/7/10.
 */
@RestController
@RequestMapping("/hmxgov")
public class HmxGovController {

    @Autowired
    private HmxGovService hmxGovService;

    @Autowired
    private HmxCategoryContentService hmxCategoryContentService;

    /**
     * 获取所有评论列表
     * @param commentDto
     * @return
     */
    @GetMapping("/AllList")
    @Operation("获取第三方内容列表")
    public ResultBean list(HmxGovDto commentDto, PageBean<HmxGov> page, Model model){
        page = hmxGovService.getPage(page, commentDto);
        List<HmxGov> list = page.getPage();
        if(list == null || list.size() <= 0){
            if(page.getPageNum() == 1){
                return new ResultBean().setCode(Config.CONTENT_NULL).setContent("暂无数据");
            }
            else{
                return new ResultBean().setCode(Config.PAGE_NULL).setContent("没有更多数据了");
            }
        }
        return new ResultBean().put("contentPage", page).setCode(Config.SUCCESS_CODE).setContent("查询消息列表成功");
    }

    /**
     * 增加标签
     * @param ids  标签id，以逗号分隔
     * @return
     */
    @PostMapping("/delete")
    @Operation("删除第三方内容")
    public ResultBean delete(String ids, HttpServletRequest request){

        ResultBean resultBean = new ResultBean();
        boolean flag=true;
        if(StringUtils.isEmpty(ids)){
            resultBean.setCode(Config.FAIL_FIELD_EMPTY).setContent("等级ids不能为空");
            flag=false;
        }
        if(flag){
            flag = hmxGovService.deleteByIdArray(ids);
            if(!flag){
                resultBean.setCode(Config.FAIL_CODE).setContent("删除失败");
            }else{
                resultBean.setCode(Config.SUCCESS_CODE).setContent("删除成功");
            }

        }
        return resultBean;
    }

    /**
     * 第三方内容审核
     * @param ids  标签id，以逗号分隔
     * @return
     */
    @PostMapping("/examination")
    @Operation("第三方内容审核")
    public ResultBean examination(String ids, HttpServletRequest request){

        ResultBean resultBean = new ResultBean();
        boolean flag=true;
        if(StringUtils.isEmpty(ids)){
            resultBean.setCode(Config.FAIL_FIELD_EMPTY).setContent("第三方内容ids不能为空");
            flag=false;
        }
        if(flag){
            flag = hmxGovService.examination(ids);
            if(!flag){
                resultBean.setCode(Config.FAIL_CODE).setContent("第三方内容审核失败");
            }else{
                resultBean.setCode(Config.SUCCESS_CODE).setContent("第三方内容审核成功");
            }

        }
        return resultBean;
    }

    /**
     * 获取用户信息
     * @param hmxGovDto
     * @return
     */
    @GetMapping("/getGovInfo")
    public ResultBean getGovInfo(@ModelAttribute HmxGovDto hmxGovDto, HttpServletRequest request){
        if(null == hmxGovDto.getId() || 0 == hmxGovDto.getId()){
            return new ResultBean().setCode(Config.FAIL_MOBILE_EMPTY).setContent("内容id不能为空");
        }
        try {
            HmxGov hmxGov = hmxGovService.info(hmxGovDto.getId());
            if(null == hmxGov){
                return new ResultBean().setCode(Config.USER_NULL).setContent("没有查询到信息");
            }
            return new ResultBean().setCode(Config.SUCCESS_CODE).setContent("查询成功").put("user",hmxGov);
        }catch (Exception e){
            e.printStackTrace();
            return new ResultBean().setCode(Config.FAIL_CODE).setContent("查询失败");
        }
    }

}
