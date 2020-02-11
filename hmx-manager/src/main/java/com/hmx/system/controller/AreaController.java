package com.hmx.system.controller;

import com.hmx.aop.Operation;
import com.hmx.redis.RedisUtil;
import com.hmx.system.entity.AreaModel;
import com.hmx.system.service.AreaService;
import com.hmx.utils.result.Config;
import com.hmx.utils.result.PageBean;
import com.hmx.utils.result.ResultBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.concurrent.TimeUnit;

@RestController
@RequestMapping("/area")
public class AreaController {

    @Resource
    private AreaService areaService;

    @Autowired
    private RedisUtil redisUtil;

    @Autowired
    private RedisTemplate redisTemplate;
    @RequestMapping(value = "/getById")
    public ResultBean hello(Integer areaId){

//
//        //查询缓存中是否存在
//        boolean hasKey = redisUtil.exists(areaId+"");
//
//
//
//        redisTemplate.opsForValue().set("你好啊","你好啊!!!");
//        ValueOperations vo = redisTemplate.opsForValue();
//        vo.set("所有的博客","aaaaa");
//
//        ValueOperations operations = redisTemplate.opsForValue();
//        Object result = operations.get("所有的博客");


//        return new Result(200,"获取成功",result);

        boolean key1 = redisUtil.exists("所有的博客");
        if(key1){
            Object object = redisUtil.get("所有的博客");
            System.out.println(object.toString());
        }

        AreaModel areaModel = null;
        //查询缓存中是否存在
        boolean hasKey = redisUtil.exists(areaId+"");

        if(hasKey){
            //获取缓存
            Object object =  redisUtil.get(areaId+"");
            areaModel = (AreaModel)object;
            System.out.println("从缓存获取的数据");
//            str = object.toString();
        }else{
            //从数据库中获取信息
            System.out.println("从缓存获取的数据");

            try {
                areaModel = areaService.getObjectById(areaId);
            } catch (Exception e) {
                e.printStackTrace();
            }
//            str = areaModel.getAreaId()+"";
            //数据插入缓存（set中的参数含义：key值，user对象，缓存存在时间10（long类型），时间单位）
            redisUtil.set(areaId+"",areaModel,10L,TimeUnit.MINUTES);
            System.out.println("数据插入缓存" + areaId);
        }
        return new ResultBean().put("content", areaModel).setCode(Config.SUCCESS_CODE).setContent("查询成功");
    }

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
