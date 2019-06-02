package com.hmx.system.appcontroller;

import com.hmx.system.dto.ScoreModelDto;
import com.hmx.system.entity.ScoreModel;
import com.hmx.system.service.ScoreModelService;
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
 * Created by Administrator on 2019/6/2.
 */
@RestController
@RequestMapping("/score")
public class ScoreController {

    @Autowired
    private ScoreModelService scoreModelService;

    /**
     * 分页
     * 获取评分
     * @param scoreModelDto
     * @return
     */
    @GetMapping("/tagListPage")
    public ResultBean tagListPage(ScoreModelDto scoreModelDto, PageBean<ScoreModel> page, Model model){
        page = scoreModelService.getPage(page, scoreModelDto);
        List<ScoreModel> list = page.getPage();
        if(list == null || list.size() <= 0){
            if(page.getPageNum() == 1){
                return new ResultBean().setCode(Config.CONTENT_NULL).setContent("暂无数据");
            }
            else{
                return new ResultBean().setCode(Config.PAGE_NULL).setContent("没有更多数据了");
            }
        }
        return new ResultBean().put("contentPage", page).setCode(Config.SUCCESS_CODE).setContent("查询评分列表成功");
    }

    /**
     * 分页
     * 获取评分
     * @param scoreModelDto
     * @return
     */
    @GetMapping("/getScoreByUserPhone")
    public ResultBean getScoreByUserPhone(ScoreModelDto scoreModelDto, PageBean<ScoreModel> page, Model model){
        ResultBean resultBean = new ResultBean();
        if(StringUtils.isEmpty(scoreModelDto.getUserPhone())){
            resultBean.setCode(Config.FAIL_FIELD_EMPTY).setContent("用户id不能为空");
            return resultBean;
        }
        page = scoreModelService.getPage(page, scoreModelDto);
        List<ScoreModel> list = page.getPage();
        if(list == null || list.size() <= 0){
            if(page.getPageNum() == 1){
                return new ResultBean().setCode(Config.CONTENT_NULL).setContent("暂无数据");
            }
            else{
                return new ResultBean().setCode(Config.PAGE_NULL).setContent("没有更多数据了");
            }
        }
        return new ResultBean().put("scoreList", list).setCode(Config.SUCCESS_CODE).setContent("查询评分成功");
    }

    /**
     * 评分
     * @param scoreModelDto
     * @return
     */
    @PostMapping("/add")
    public ResultBean add(ScoreModelDto scoreModelDto, HttpServletRequest request){

        ResultBean resultBean = new ResultBean();
        boolean flag=true;
        if(StringUtils.isEmpty(scoreModelDto.getContentId())){
            resultBean.setCode(Config.FAIL_FIELD_EMPTY).setContent("内容id不能为空");
            flag=false;
        }
        if(StringUtils.isEmpty(scoreModelDto.getUserPhone())){
            resultBean.setCode(Config.FAIL_FIELD_EMPTY).setContent("用户id不能为空");
            flag=false;
        }
        if(flag){
            scoreModelDto.setCreateTime(System.currentTimeMillis());
            flag = scoreModelService.insert(scoreModelDto);
            if(!flag){
                resultBean.setCode(Config.FAIL_CODE);
            }else{
                resultBean.setCode(Config.SUCCESS_CODE);
            }
            resultBean.setContent("添加成功");

        }
        return resultBean;
    }



    /**
     * 修改评分
     * @param scoreModelDto
     * @return
     */
//    @PostMapping("/edit")
//    public ResultBean edit(ScoreModelDto scoreModelDto, HttpServletRequest request){
//
//
//        return null;
//    }

    /**
     * 删除评分
     * @param ids  标签id，以逗号分隔
     * @return
     */
//    @PostMapping("/delete")
//    public ResultBean delete(String ids, HttpServletRequest request){
//
//        ResultBean resultBean = new ResultBean();
//        boolean flag=true;
//        if(StringUtils.isEmpty(ids)){
//            resultBean.setCode(Config.FAIL_FIELD_EMPTY).setContent("标签ids不能为空");
//            flag=false;
//        }
//        if(flag){
//            flag = scoreModelService.deleteByIdArray(ids);
//            if(!flag){
//                resultBean.setCode(Config.FAIL_CODE);
//            }else{
//                resultBean.setCode(Config.SUCCESS_CODE);
//            }
//            resultBean.setContent("删除成功");
//
//        }
//        return resultBean;
//    }
}
