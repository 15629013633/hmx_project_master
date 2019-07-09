package com.hmx.managemant.controller;

import com.hmx.aop.Operation;
import com.hmx.user.dao.HmxUserMapper;
import com.hmx.user.dao.RoleMapper;
import com.hmx.user.dto.HmxUserDto;
import com.hmx.user.dto.UserModelDto;
import com.hmx.user.entity.HmxUser;
import com.hmx.user.entity.po.UserModel;
import com.hmx.user.service.HmxUserService;
import com.hmx.user.service.UserModelService;
import com.hmx.utils.enums.IsVerify;
import com.hmx.utils.random.RandomHelper;
import com.hmx.utils.result.Config;
import com.hmx.utils.result.PageBean;
import com.hmx.utils.result.Result;
import com.hmx.utils.result.ResultBean;
import com.hmx.utils.secret.MD5Util;
import com.hmx.utils.sms.SMSSendOut;
import com.hmx.verifylog.entity.HmxVerifylog;
import com.hmx.verifylog.service.HmxVerifylogService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.data.domain.Pageable;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 系统用户控制层
 * Created by songjinbao on 2019/4/25.
 */
//@Controller
@RestController
@RequestMapping(value = "/management/userContrl")
public class UserModelController {

    private final String defaultPass = "123456";

    @Autowired
    private HmxUserMapper hmxUserMapper;

    @Autowired
    private RoleMapper roleDao;

    @Autowired
    private HmxUserService hmxUserService;

    @Autowired
    private SMSSendOut smsSendOut;

    @Autowired
    private HmxVerifylogService hmxVerifylogService;

    @Autowired
    private UserModelService userModelService;


    /**
     * 系统用户添加
     * @return
     */
    @PostMapping("/add")
    @Operation("系统用户添加")
    public ResultBean add(@ModelAttribute UserModel userModel){
        ResultBean resultBean = new ResultBean();
        boolean flag=true;
        try {
            if(StringUtils.isEmpty(userModel.getUsername())){
                resultBean.setCode(Config.FAIL_FIELD_EMPTY).setContent("用户名不能为空");
                flag=false;
            }

            if(flag){
                userModel.setCreateTime(System.currentTimeMillis());
                //默认密码是123456
                userModel.setPassword(MD5Util.encode(defaultPass));
                flag = userModelService.insert(userModel);
                if(!flag){
                    resultBean.setCode(Config.FAIL_CODE).setContent("添加失败");
                }else{
                    resultBean.setCode(Config.SUCCESS_CODE).setContent("添加成功");
                }
            }
        }catch (Exception e){
            resultBean.setCode(Config.FAIL_CODE).setContent("添加失败");
            e.printStackTrace();
        }

        return resultBean;
    }

    /**
     * 系统用户修改
     * @return
     */
    @PostMapping("/edit")
    @Operation("系统用户修改")
    public ResultBean edit(@ModelAttribute UserModel userModel){
        ResultBean resultBean = new ResultBean();
        boolean flag=true;
        try {
            if(null == userModel.getId() || userModel.getId() == 0){
                resultBean.setCode(Config.FAIL_FIELD_EMPTY).setContent("户名id不能为空");
                flag=false;
            }
//            if(StringUtils.isEmpty(userModel.getUsername())){
//                resultBean.setCode(Config.FAIL_FIELD_EMPTY).setContent("用户名不能为空");
//                flag=false;
//            }

            if(flag){
                userModel.setCreateTime(System.currentTimeMillis());
                //默认密码是123456
                //userModel.setPassword(MD5Util.encode(defaultPass));
                flag = userModelService.update(userModel);
                if(!flag){
                    resultBean.setCode(Config.FAIL_CODE).setContent("修改失败");
                }else{
                    resultBean.setCode(Config.SUCCESS_CODE).setContent("修改成功");
                }
            }
        }catch (Exception e){
            resultBean.setCode(Config.FAIL_CODE).setContent("修改失败");
            e.printStackTrace();
        }

        return resultBean;
    }

    /**
     * 系统用户修改
     * @return
     */
    @PostMapping("/restPass")
    @Operation("系统用户密码重置")
    public ResultBean restPass(@ModelAttribute UserModel userModel){
        ResultBean resultBean = new ResultBean();
        boolean flag=true;
        try {
            if(null == userModel.getId() || userModel.getId() == 0){
                resultBean.setCode(Config.FAIL_FIELD_EMPTY).setContent("户名id不能为空");
                flag=false;
            }
//            if(StringUtils.isEmpty(userModel.getUsername())){
//                resultBean.setCode(Config.FAIL_FIELD_EMPTY).setContent("用户名不能为空");
//                flag=false;
//            }

            if(flag){
                userModel.setCreateTime(System.currentTimeMillis());
                //默认密码是123456
                userModel.setPassword(MD5Util.encode(defaultPass));
                flag = userModelService.update(userModel);
                if(!flag){
                    resultBean.setCode(Config.FAIL_CODE).setContent("重置失败");
                }else{
                    resultBean.setCode(Config.SUCCESS_CODE).setContent("重置成功");
                }
            }
        }catch (Exception e){
            resultBean.setCode(Config.FAIL_CODE).setContent("重置失败");
            e.printStackTrace();
        }

        return resultBean;
    }

    /**
     * 系统用户删除
     * @return
     */
    @PostMapping("/delete")
    @Operation("系统用户删除")
    public ResultBean delete(String ids){
        ResultBean resultBean = new ResultBean();
        boolean flag=true;
        try {
            if(StringUtils.isEmpty(ids)){
                resultBean.setCode(Config.FAIL_FIELD_EMPTY).setContent("户名ids不能为空");
                flag=false;
            }

            if(flag){
                flag = userModelService.deleteByIdArray(ids);
                if(!flag){
                    resultBean.setCode(Config.FAIL_CODE).setContent("删除失败");
                }else{
                    resultBean.setCode(Config.SUCCESS_CODE).setContent("删除成功");
                }
            }
        }catch (Exception e){
            resultBean.setCode(Config.FAIL_CODE).setContent("删除失败");
            e.printStackTrace();
        }

        return resultBean;
    }

    /**
     * 获取所有系统用户
     * @param userDto
     * @return
     */
    @GetMapping("/AllList")
    @Operation("获取所有系统用户")
    public ResultBean list(UserModelDto userDto, PageBean<UserModel> page, Model model){
        page = userModelService.getPage(page, userDto);
        List<UserModel> list = page.getPage();
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
     * 获取用户信息
     * @param userModel
     * @return
     */
    @GetMapping("/getUserInfo")
    @Operation("获取系统用户信息")
    public ResultBean getUserInfo(@ModelAttribute UserModel userModel,HttpServletRequest request){
        if(null == userModel.getId() || userModel.getId() == 0){
            return new ResultBean().setCode(Config.FAIL_MOBILE_EMPTY).setContent("用户id不能为空");
        }
        try {
            UserModel model = userModelService.info(userModel.getId());
            if(null == model){
                return new ResultBean().setCode(Config.USER_NULL).setContent("通过该手机号没有查到用户信息");
            }
            return new ResultBean().setCode(Config.SUCCESS_CODE).setContent("查询到用户").put("user",model);
        }catch (Exception e){
            e.printStackTrace();
            return new ResultBean().setCode(Config.FAIL_CODE).setContent("查询失败");
        }
    }





//    /**
//     *@Author: shi
//     *@Description: 用户管理初始化
//     *@param:
//     *@return
//     *@Date: 17:09 2018/7/20
//     */
//    @RequestMapping(value = "/init")
//    public ModelAndView init(){
//        ModelAndView mv = new ModelAndView();
//        mv.setViewName("/system/user/list");
//        return mv;
//    }
//
//    /**
//     *@Author: shi
//     *@Description: 用户管理编辑
//     *@param: request
//     *@return
//     *@Date: 17:09 2018/7/20
//     */
//    @RequestMapping(value = "/eidt")
//    public ModelAndView eidt(HttpServletRequest request, Integer id){
//        ModelAndView mv = new ModelAndView();
//        HmxUser userModelLogin = (HmxUser) request.getSession().getAttribute("userInfo");
//        mv.addObject("roleModel",roleDao.findAll());
//        mv.addObject("user",id == null ? new HmxUser() : hmxUserMapper.selectByPrimaryKey(id));
//        mv.setViewName("/system/user/eidt");
//        return mv;
//    }
//
//    /**
//     *@Author: shi
//     *@Description: 条件获取所有用户
//     *@param: userModel pageable request
//     *@return
//     *@Date: 17:10 2018/7/20
//     */
//    @RequestMapping(value = "/getLists")
//    @ResponseBody
//    public Map<String, Object> getLists(HmxUser userModel, Pageable pageable, HttpServletRequest request) {
//        Map<String, Object> result = new HashMap<>();
//        HmxUser userModelLogin = (HmxUser) request.getSession().getAttribute("userInfo");
//        Map<String,Object> map = new HashMap<>();
//        map.put("currPage",pageable.getPageNumber());
//        map.put("pageSize",pageable.getPageSize());
//        String username=userModel.getUserName();
//        String cellPhone=userModel.getUserPhone();
//        if(username != null && !"".equals(username)){
//            map.put("userName",username);
//        }else if(cellPhone != null && !"".equals(cellPhone)){
//            map.put("userPhone",cellPhone);
//        }
//        List<HmxUser> modelPage = hmxUserMapper.findAll(map);
//        result.put("rows", modelPage);
//        result.put("total", modelPage.size());
//        return result;
//    }
//
//    /**
//     *@Author: shi
//     *@Description: 用户禁用/启用
//     *@param: userId valid
//     *@return
//     *@Date: 17:11 2018/7/20
//     */
//    @RequestMapping(value = "/isValid")
//    @ResponseBody
//    public Result<Object> isValid(Integer userId, Boolean isValid){
//        Result<Object> result = new Result<>();
//        result.setStatus(10000);
////        userDao.updateById(isValid,userId);
//        return result;
//    }
//
//
//    @RequestMapping(value = "/addOrUpdateUser")
//    @ResponseBody
//    public Result<Object> addOrUpdateUser(HmxUserDto hmxUserDto){
//        return hmxUserService.addOrUpdateUser(hmxUserDto);
//    }
//
//
//    @RequestMapping(value = "/updateUser")
//    @ResponseBody
//    public Result<Object> updateUser(HmxUser hmxUser,@RequestParam(required = false) String verifyCode){
//        Result<Object> result = new Result<>();
//        if(hmxUser.getUserPhone().equals("")){
//            hmxUser.setUserPhone(null);
//        }
//        if(verifyCode != null && !verifyCode.equals("")){
//            HmxVerifylog hmxVerifylog = hmxVerifylogService.selectNewVerifylog(hmxUser.getUserPhone());
//            if(hmxVerifylog == null){
//                result.setMsg("您还没有发送验证码");
//                result.setStatus(20000);
//                return result;
//            }
//            String oldVerifyCode = hmxVerifylog.getVerifyCode();
//            if(hmxVerifylog.getIsVerify() == IsVerify.已使用.getState()){
//                result.setMsg("验证码已被使用");
//                result.setStatus(20000);
//                return result;
//            }
//            if(!verifyCode.equals(oldVerifyCode)){
//                result.setMsg("验证码错误");
//                result.setStatus(20000);
//                return result;
//            }
//        }
//        Boolean flag = hmxUserService.update(hmxUser);
//        if(flag){
//            result.setMsg("success");
//            result.setStatus(10000);
//            return result;
//        }else {
//            result.setMsg("更新失败");
//            result.setStatus(20000);
//            return result;
//        }
//    }
//
//
//    @RequestMapping(value = "/getValidation")
//    @ResponseBody
//    public Result<Object> getValidation(HttpServletRequest request , String cellPhone){
//        HmxUser userModelLogin = (HmxUser) request.getSession().getAttribute("userInfo");
//        String code = RandomHelper.getRandomNum(6);
//        Boolean isSend = smsSendOut.SMSSending(cellPhone,code);
//        HmxVerifylog hmxVerifylog = new HmxVerifylog();
//        hmxVerifylog.setAddTime(new Date());
//        hmxVerifylog.setVerifyCode(code);
//        hmxVerifylog.setVerifyObject(cellPhone);
//        hmxVerifylog.setVerifyType(0);
//        Boolean flag = hmxVerifylogService.insert(hmxVerifylog);
//        Result<Object> result = new Result<>();
//        if(isSend && flag){
//            result.setStatus(10000);
//            result.setMsg("success");
//        }else {
//            result.setStatus(20000);
//            result.setMsg("失败");
//        }
//        return result;
//    }
//
//    /**
//     *@Author: shi
//     *@Description:  修改密码
//     *@param: userData
//     *@return
//     *@Date: 10:44 2018/7/25
//     */
////    @RequestMapping(value = "/modifyPassword")
////    @ResponseBody
////    public Result<Object> modifyPassword(HttpServletRequest request , Integer userId, Boolean isReset, String newPassword){
////        UserModel userModelLogin = (UserModel) request.getSession().getAttribute("userInfo");
////        logger.info(userModelLogin.getUsername()+"---修改密码："+userId+"________"+newPassword);
////        return userService.modifyPassword(userId,isReset,newPassword);
////    }
}
