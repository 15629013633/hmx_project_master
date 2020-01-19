package com.hmx.category.controller;

import com.hmx.aop.Operation;
import com.hmx.category.dto.HmxCategoryDto;
import com.hmx.category.entity.HmxCategory;
import com.hmx.category.service.HmxCategoryService;
import com.hmx.utils.enums.DataState;
import com.hmx.utils.enums.IsClose;
import com.hmx.utils.result.Config;
import com.hmx.utils.result.PageBean;
import com.hmx.utils.result.Result;
import com.hmx.utils.result.ResultBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import com.alibaba.druid.util.StringUtils;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by songjinbao on 2019/4/25.
 */
@RestController
@RequestMapping("/category/controller")
public class CategoryController {
    @Autowired
    private HmxCategoryService hmxCategoryService;


//    @RequestMapping("/init")
//    public ModelAndView init() {
//        List<HmxCategory> hmxCategoryList = hmxCategoryService.list(new HmxCategoryDto());
//        ModelAndView modelAndView = new ModelAndView();
//        modelAndView.setViewName("/category/list");
//        modelAndView.addObject("category",hmxCategoryList);
//        return modelAndView;
//    }
//
//    @RequestMapping("/initAdd")
//    public ModelAndView initAdd(HttpServletRequest request, @RequestParam(value = "id", required = false) Integer categoryId) {
//        ModelAndView modelAndView = new ModelAndView();
//        modelAndView.addObject("category",categoryId == null ? new HmxCategory() : hmxCategoryService.info(categoryId));
//        modelAndView.setViewName("/category/eidt");
//        return modelAndView;
//    }

    /**
     * 首页分类添加
     * @param hmxcategoryDto
     * @return
     */
    @PostMapping("/add")
    @Operation("分类添加")
    public ResultBean categoryAdd(HmxCategoryDto hmxcategoryDto, HttpServletRequest request){
        ResultBean resultBean = new ResultBean();
        resultBean.put("content", "");
        boolean flag=true;
        if(StringUtils.isEmpty(hmxcategoryDto.getCategoryName())){
            resultBean.setCode(Config.FAIL_FIELD_EMPTY);
            resultBean.setContent("分类名称不能为空");
            flag=false;
        }
        if(flag){
            Map<String,Object> resultMap = hmxCategoryService.categoryAdd(hmxcategoryDto,request);
            flag=Boolean.parseBoolean(resultMap.get("flag").toString());
            if(!flag){
                resultBean.setCode(Config.FAIL_CODE);
                resultBean.setContent(resultMap.get("content")+"");
                //result.setStatus(Config.FAIL_CODE);
            }else{
                resultBean.setCode(Config.SUCCESS_CODE);
                resultBean.setContent("操作成功");
               // result.setStatus(Config.SUCCESS_CODE);
            }
        }
        return resultBean;
    }
    /**
     * 首页分类信息查找
     * @return
     */
    @GetMapping("/getCategoryByid")
    @Operation("分类详情查询")
    //public String categoryInfo(@PathVariable(name="id",required=true) Integer categoryId, Model model){
    public ResultBean categoryInfo(Integer categoryId, Model model){
        ResultBean resultBean = new ResultBean();
        HmxCategory hmxCategory = hmxCategoryService.info(categoryId);
        if(hmxCategory == null){
            resultBean.setCode(Config.FAIL_CODE);
            resultBean.setContent("查询分类信息失败");
        }else{
            resultBean.setCode(Config.SUCCESS_CODE);
            resultBean.setContent("查询分类信息成功");
        }
        resultBean.put("content", hmxCategory);
        model.addAttribute("resultBean", resultBean);
        return resultBean;
    }
    /**
     * 分类信息更新
     * @param model
     * @return
     */
    @PostMapping("/edit")
    @Operation("分类更新")
    public ResultBean categoryUpdate(HmxCategoryDto hmxcategoryDto,Model model){
        ResultBean resultBean = new ResultBean();
        resultBean.put("content", "");
        boolean flag=true;
        if(hmxcategoryDto.getCategoryId() == null){
            resultBean.setCode(Config.FAIL_FIELD_EMPTY);
            resultBean.setContent("分类编号不能为空");
            flag=false;
        }
        if(flag){
            Map<String,Object> resultMap = hmxCategoryService.categoryUpdate(hmxcategoryDto);
            flag=Boolean.parseBoolean(resultMap.get("flag").toString());
            if(!flag){
                resultBean.setCode(Config.FAIL_CODE);
                resultBean.setContent(resultMap.get("content")+"");
            }else{
                resultBean.setCode(Config.SUCCESS_CODE);
                resultBean.setContent("操作成功");
            }
        }
        return resultBean;
    }

    /**
     * 分类信息更新
     * @param model
     * @param categoryIds  分类主件id，以逗号分隔  2,3,4
     * @return
     */
    @PostMapping("/delete")
    @Operation("分类删除")
    public ResultBean categoryDelete(String categoryIds,Model model){
        ResultBean resultBean = new ResultBean();
        resultBean.put("content", "");
        boolean flag=true;
        if(StringUtils.isEmpty(categoryIds)){
            resultBean.setCode(Config.FAIL_FIELD_EMPTY);
            resultBean.setContent("分类编号不能为空");
            flag=false;
        }
        if(flag){
            Map<String,Object> resultMap = hmxCategoryService.categoryDelete(categoryIds);
            flag=Boolean.parseBoolean(resultMap.get("flag").toString());
            if(!flag){
                resultBean.setCode(Config.FAIL_CODE);
                resultBean.setContent(resultMap.get("content")+"");
            }else{
                resultBean.setCode(Config.SUCCESS_CODE);
                resultBean.setContent("操作成功");
            }
        }
        return resultBean;
    }
    /**
     * 分类列表信息
     * parentId 为0则查询所有一级分类    不为0则查某个一级分类下的所有二级分类
     * @param hmxcategoryDto
     * @param page
     * @param model
     * @return
     */
    @GetMapping("/categoryTable")
    @Operation("获取分类信息列表")
    public ResultBean categoryTable(HmxCategoryDto hmxcategoryDto, PageBean<Map<String,Object>> page, Model model){
//        Map<String,Object> map = new HashMap<>();
        ResultBean resultBean = new ResultBean();
        page = hmxCategoryService.selectCategoryTable(page, hmxcategoryDto);
        List<Map<String,Object>> list = page.getPage();
        if(list == null || list.size() <= 0){
            if(page.getPageNum() == 1){
                resultBean.setCode(Config.CONTENT_NULL).setContent("暂无数据");
            }
            else{
                resultBean.setCode(Config.PAGE_NULL).setContent("没有更多数据了");
            }
        }else{
            resultBean.setCode(Config.SUCCESS_CODE).setContent("查询分类列表成功");
        }
        return resultBean.put("content", page);
    }
    /**
     * 查询所有分类信息列表
     * parentId 为0则查询所有一级分类    不为0则查某个一级分类下的所有二级分类
     * @param hmxCategoryDto
     * @param model
     * @return
     */
    @GetMapping("/categoryAll")
    @Operation("获取所有分类")
    public ResultBean categoryAll(HmxCategoryDto hmxCategoryDto,Model model){
        ResultBean resultBean = new ResultBean();
        hmxCategoryDto.setState(DataState.正常.getState());
        hmxCategoryDto.setIsClose(IsClose.开放.getState());
        List<HmxCategory> hmxCategoryList = hmxCategoryService.list(hmxCategoryDto);
        if(hmxCategoryList == null || hmxCategoryList.size() <= 0){
            resultBean.setCode(Config.FAIL_CODE).setContent("没有查找到分类信息");
        }else{
            resultBean.setCode(Config.SUCCESS_CODE).setContent("没有查找到分类信息");
        }
        resultBean.put("hmxCategoryList", hmxCategoryList);
        model.addAttribute("resultBean", resultBean);
        return resultBean;
    }
}
