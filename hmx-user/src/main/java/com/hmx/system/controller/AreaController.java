package com.hmx.system.controller;

import com.hmx.aop.Operation;
import com.hmx.system.entity.AreaModel;
import com.hmx.system.service.AreaService;
import com.hmx.utils.result.Config;
import com.hmx.utils.result.PageBean;
import com.hmx.utils.result.ResultBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.List;

@RestController
@RequestMapping("/userArea")
public class AreaController {

    @Resource
    private AreaService areaService;

    /**
     * 获取所有地区
     * @param areaModel
     * @return
     */
    @GetMapping("/AllList")
    @Operation("获取所有地区")
    public ResultBean list(AreaModel areaModel, PageBean<AreaModel> page, Model model){
        page = areaService.getPage(page, areaModel);
        List<AreaModel> list = page.getPage();
        if(list == null || list.size() <= 0){
            if(page.getPageNum() == 1){
                return new ResultBean().setCode(Config.CONTENT_NULL).setContent("暂无数据");
            }
            else{
                return new ResultBean().setCode(Config.PAGE_NULL).setContent("没有更多数据了");
            }
        }
        return new ResultBean().put("content", page).setCode(Config.SUCCESS_CODE).setContent("查询消息列表成功");
    }

    /**
     * 增加地区信息
     * @param areaModel
     * @return
     */
    @PostMapping("/add")
    @Operation("增加地区信息")
    public ResultBean add(AreaModel areaModel, HttpServletRequest request) {

        ResultBean resultBean = new ResultBean();

        try {
            areaService.insert(areaModel);
            resultBean.setCode(Config.SUCCESS_CODE).setContent("添加成功").put("content",areaModel);
        } catch (Exception e) {
            resultBean.setCode(Config.FAIL_CODE).setContent("添加失败").put("content","");
            e.printStackTrace();
        }

        return resultBean;
    }

    /**
     * 修改地区信息
     * @param areaModel
     * @return
     */
    @PostMapping("/edit")
    @Operation("修改地区信息")
    public ResultBean edit(AreaModel areaModel, HttpServletRequest request) {

        ResultBean resultBean = new ResultBean();

        try {
            areaService.edit(areaModel);
            resultBean.setCode(Config.SUCCESS_CODE).setContent("修改成功").put("content",areaModel);
        } catch (Exception e) {
            resultBean.setCode(Config.FAIL_CODE).setContent("修改失败").put("content","");
            e.printStackTrace();
        }

        return resultBean;
    }

    /**
     * 修改地区信息
     * @param areaModel
     * @return
     */
    @PostMapping("/delete")
    @Operation("删除地区信息")
    public ResultBean delete(AreaModel areaModel, HttpServletRequest request) {

        ResultBean resultBean = new ResultBean();
        resultBean.put("content","");
        try {
            areaService.delete(areaModel.getAreaId());
            resultBean.setCode(Config.SUCCESS_CODE).setContent("删除成功");
        } catch (Exception e) {
            resultBean.setCode(Config.FAIL_CODE).setContent("删除失败");
            e.printStackTrace();
        }

        return resultBean;
    }


}
