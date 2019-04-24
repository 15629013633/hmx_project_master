package com.hmx.management.controller.user;


import com.hmx.user.dao.HmxUserMapper;
import com.hmx.user.dao.RoleMapper;
import com.hmx.user.dto.HmxUserDto;
import com.hmx.user.entity.HmxUser;
import com.hmx.user.entity.po.UserModel;
import com.hmx.user.service.HmxUserService;
import com.hmx.utils.enums.IsVerify;
import com.hmx.utils.random.RandomHelper;
import com.hmx.utils.result.Config;
import com.hmx.utils.result.Result;
import com.hmx.utils.result.ResultBean;
import com.hmx.utils.sms.SMSSendOut;
import com.hmx.verifylog.entity.HmxVerifylog;
import com.hmx.verifylog.service.HmxVerifylogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.util.*;


@Controller
@RequestMapping(value = "/system/userManage")
public class UserModelController {
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

    
    /**
     *@Author: shi
     *@Description: 用户管理初始化
     *@param:
     *@return 
     *@Date: 17:09 2018/7/20
     */
    @RequestMapping(value = "/init")
    public ModelAndView init(){
        ModelAndView mv = new ModelAndView();
        mv.setViewName("/system/user/list");
        return mv;
    }

    /**
     *@Author: shi
     *@Description: 用户管理编辑
     *@param: request
     *@return
     *@Date: 17:09 2018/7/20
     */
    @RequestMapping(value = "/eidt")
    public ModelAndView eidt(HttpServletRequest request, Integer id){
        ModelAndView mv = new ModelAndView();
        HmxUser userModelLogin = (HmxUser) request.getSession().getAttribute("userInfo");
        mv.addObject("roleModel",roleDao.findAll());
        mv.addObject("user",id == null ? new HmxUser() : hmxUserMapper.selectByPrimaryKey(id));
        mv.setViewName("/system/user/eidt");
        return mv;
    }

    /**
     *@Author: shi
     *@Description: 条件获取所有用户
     *@param: userModel pageable request
     *@return 
     *@Date: 17:10 2018/7/20
     */
    @RequestMapping(value = "/getLists")
    @ResponseBody
    public Map<String, Object> getLists(HmxUser userModel, Pageable pageable, HttpServletRequest request) {
        Map<String, Object> result = new HashMap<>();
        HmxUser userModelLogin = (HmxUser) request.getSession().getAttribute("userInfo");
        Map<String,Object> map = new HashMap<>();
        map.put("currPage",pageable.getPageNumber());
        map.put("pageSize",pageable.getPageSize());
        String username=userModel.getUserName();
        String cellPhone=userModel.getUserPhone();
        if(username != null && !"".equals(username)){
            map.put("userName",username);
        }else if(cellPhone != null && !"".equals(cellPhone)){
            map.put("userPhone",cellPhone);
        }
        List<HmxUser> modelPage = hmxUserMapper.findAll(map);
        result.put("rows", modelPage);
        result.put("total", modelPage.size());
        return result;
    }

    /**
     *@Author: shi
     *@Description: 用户禁用/启用
     *@param: userId valid
     *@return 
     *@Date: 17:11 2018/7/20
     */
    @RequestMapping(value = "/isValid")
    @ResponseBody
    public Result<Object> isValid(Integer userId, Boolean isValid){
        Result<Object> result = new Result<>();
        result.setStatus(10000);
//        userDao.updateById(isValid,userId);
        return result;
    }


    @RequestMapping(value = "/addOrUpdateUser")
    @ResponseBody
    public Result<Object> addOrUpdateUser(HmxUserDto hmxUserDto){
        return hmxUserService.addOrUpdateUser(hmxUserDto);
    }


    @RequestMapping(value = "/updateUser")
    @ResponseBody
    public Result<Object> updateUser(HmxUser hmxUser,@RequestParam(required = false) String verifyCode){
        Result<Object> result = new Result<>();
        if(hmxUser.getUserPhone().equals("")){
            hmxUser.setUserPhone(null);
        }
        if(verifyCode != null && !verifyCode.equals("")){
            HmxVerifylog hmxVerifylog = hmxVerifylogService.selectNewVerifylog(hmxUser.getUserPhone());
            if(hmxVerifylog == null){
                result.setMsg("您还没有发送验证码");
                result.setStatus(20000);
                return result;
            }
            String oldVerifyCode = hmxVerifylog.getVerifyCode();
            if(hmxVerifylog.getIsVerify() == IsVerify.已使用.getState()){
                result.setMsg("验证码已被使用");
                result.setStatus(20000);
                return result;
            }
            if(!verifyCode.equals(oldVerifyCode)){
                result.setMsg("验证码错误");
                result.setStatus(20000);
                return result;
            }
        }
        Boolean flag = hmxUserService.update(hmxUser);
        if(flag){
            result.setMsg("success");
            result.setStatus(10000);
            return result;
        }else {
            result.setMsg("更新失败");
            result.setStatus(20000);
            return result;
        }
    }


    @RequestMapping(value = "/getValidation")
    @ResponseBody
    public Result<Object> getValidation(HttpServletRequest request , String cellPhone){
        HmxUser userModelLogin = (HmxUser) request.getSession().getAttribute("userInfo");
        String code = RandomHelper.getRandomNum(6);
        Boolean isSend = smsSendOut.SMSSending(cellPhone,code);
        HmxVerifylog hmxVerifylog = new HmxVerifylog();
        hmxVerifylog.setAddTime(new Date());
        hmxVerifylog.setVerifyCode(code);
        hmxVerifylog.setVerifyObject(cellPhone);
        hmxVerifylog.setVerifyType(0);
        Boolean flag = hmxVerifylogService.insert(hmxVerifylog);
        Result<Object> result = new Result<>();
        if(isSend && flag){
            result.setStatus(10000);
            result.setMsg("success");
        }else {
            result.setStatus(20000);
            result.setMsg("失败");
        }
        return result;
    }

    /**
     *@Author: shi
     *@Description:  修改密码
     *@param: userData
     *@return
     *@Date: 10:44 2018/7/25
     */
//    @RequestMapping(value = "/modifyPassword")
//    @ResponseBody
//    public Result<Object> modifyPassword(HttpServletRequest request , Integer userId, Boolean isReset, String newPassword){
//        UserModel userModelLogin = (UserModel) request.getSession().getAttribute("userInfo");
//        logger.info(userModelLogin.getUsername()+"---修改密码："+userId+"________"+newPassword);
//        return userService.modifyPassword(userId,isReset,newPassword);
//    }

}
