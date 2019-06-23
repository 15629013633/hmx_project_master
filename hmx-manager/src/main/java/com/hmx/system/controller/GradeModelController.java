package com.hmx.system.controller;

import com.hmx.system.dto.GradeModelDto;
import com.hmx.system.entity.GradeModel;
import com.hmx.system.entity.Tagtab;
import com.hmx.system.service.GradeModelService;
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
 * 用户级别
 * Created by Administrator on 2019/6/20.
 */

@RestController
@RequestMapping("/gradeModel")
public class GradeModelController {
    @Autowired
    private GradeModelService gradeModelService;

    /**
     * 分页
     * 获取所有评论列表
     * @param gradeModelDto
     * @return
     */
    @GetMapping("/listPage")
    public ResultBean tagListPage(GradeModelDto gradeModelDto, PageBean<GradeModel> page, Model model){
        page = gradeModelService.getPage(page, gradeModelDto);
        List<GradeModel> list = page.getPage();
        if(list == null || list.size() <= 0){
            if(page.getPageNum() == 1){
                return new ResultBean().setCode(Config.CONTENT_NULL).setContent("暂无数据");
            }
            else{
                return new ResultBean().setCode(Config.PAGE_NULL).setContent("没有更多数据了");
            }
        }
        return new ResultBean().put("contentPage", page).setCode(Config.SUCCESS_CODE).setContent("查询列表成功");
    }

    /**
     * 不分页
     * 获取所有评论列表
     * @param gradeModelDto
     * @return
     */
    @GetMapping("/gradeList")
    public ResultBean tagList(GradeModelDto gradeModelDto, PageBean<GradeModel> page, Model model){
        page.setPageNum(1);
        page.setPageSize(100);
        page = gradeModelService.getPage(page, gradeModelDto);
        List<GradeModel> list = page.getPage();
        if(list == null || list.size() <= 0){
            if(page.getPageNum() == 1){
                return new ResultBean().setCode(Config.CONTENT_NULL).setContent("暂无数据");
            }
            else{
                return new ResultBean().setCode(Config.PAGE_NULL).setContent("没有更多数据了");
            }
        }
        return new ResultBean().put("gradeList", list).setCode(Config.SUCCESS_CODE).setContent("查询标签列表成功");
    }


    /**
     * 增加标签
     * @param gradeModelDto
     * @return
     */
    @PostMapping("/add")
    public ResultBean add(GradeModelDto gradeModelDto, HttpServletRequest request){

        ResultBean resultBean = new ResultBean();
        boolean flag=true;
        if(StringUtils.isEmpty(gradeModelDto.getTitle())){
            resultBean.setCode(Config.FAIL_FIELD_EMPTY).setContent("等级名称不能为空");
            flag=false;
        }
        if(flag){
            //gradeModelDto.setUserLevel(0);
            int gradeId = gradeModelService.insert(gradeModelDto);

            if(0 != gradeId){
                resultBean.setCode(Config.SUCCESS_CODE).put("gradeId",gradeId).setContent("添加成功");
            }else {
                resultBean.setCode(Config.FAIL_CODE).put("gradeId",gradeId).setContent("添加失败");
            }

        }
        return resultBean;
    }

    /**
     * 修改标签
     * @param gradeModelDto
     * @return
     */
    @PostMapping("/edit")
    public ResultBean edit(GradeModelDto gradeModelDto, HttpServletRequest request){

        ResultBean resultBean = new ResultBean();
        boolean flag=true;
        if(StringUtils.isEmpty(gradeModelDto.getTitle())){
            resultBean.setCode(Config.FAIL_FIELD_EMPTY).setContent("名称不能为空");
            flag=false;
        }
        if(null == gradeModelDto.getGradeId() || 0 == gradeModelDto.getGradeId()){
            resultBean.setCode(Config.FAIL_FIELD_EMPTY).setContent("id不能为空");
            flag=false;
        }
        if(flag){
            flag = gradeModelService.update(gradeModelDto);
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
    public ResultBean delete(String ids, HttpServletRequest request){

        ResultBean resultBean = new ResultBean();
        boolean flag=true;
        if(StringUtils.isEmpty(ids)){
            resultBean.setCode(Config.FAIL_FIELD_EMPTY).setContent("等级ids不能为空");
            flag=false;
        }
        if(flag){
            flag = gradeModelService.deleteByIdArray(ids);
            if(!flag){
                resultBean.setCode(Config.FAIL_CODE).setContent("删除失败");
            }else{
                resultBean.setCode(Config.SUCCESS_CODE).setContent("删除成功");
            }

        }
        return resultBean;
    }
}
