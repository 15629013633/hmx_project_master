package com.hmx.managemant;

import com.hmx.aop.Operation;
import com.hmx.base.controller.BaseController;
import com.hmx.data.LoginButtonData;
import com.hmx.user.entity.HmxUser;
import com.hmx.user.entity.UserModel;
import com.hmx.user.service.HmxUserService;
import com.hmx.user.service.LoginService;
import com.hmx.utils.enums.IsVerify;
import com.hmx.utils.http.HttpUtils;
import com.hmx.utils.logger.LogHelper;
import com.hmx.utils.random.RandomHelper;
import com.hmx.utils.result.Config;
import com.hmx.utils.result.LoginUser;
import com.hmx.utils.result.Result;
import com.hmx.utils.result.ResultBean;
import com.hmx.utils.secret.JwtUtil;
import com.hmx.utils.secret.MD5Util;
import com.hmx.utils.sms.SMSHelper;
import com.hmx.utils.sms.SMSSendOut;
import com.hmx.verifylog.entity.HmxVerifylog;
import com.hmx.verifylog.service.HmxVerifylogService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * Created by songjinbao on 2019/4/25.
 */
@Controller
@RequestMapping(value = "/management/loginManage")
public class LoginController extends BaseController {
    @Autowired
    private LoginService loginService;

    @Autowired
    private HmxUserService hmxUserService;
    @Autowired
    private com.hmx.utils.sms.SMSSendOut SMSSendOut;
    @Autowired
    private HmxVerifylogService hmxVerifylogService;
    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private com.hmx.utils.sms.SMSSendOut smsSendOut;

//
//    @RequestMapping("/")
//    public ModelAndView index(HttpServletRequest request){
//        HmxUser userModel = (HmxUser) request.getSession().getAttribute("userInfo");
//        ModelAndView mv = new ModelAndView();
//        mv.addObject("userLogin",userModel);
//        mv.setViewName("/index");
//        return mv;
//    }
//
//
//    @RequestMapping("/home")
//    public ModelAndView home(){
//
//        ModelAndView mv = new ModelAndView();
//        //查询home页统计数据
//        Map<String, Integer> statisticsMap = loginService.loadHomeStatistics();
//        mv.addObject("movieCount",statisticsMap.get("movieCount"));
//        mv.addObject("userCount",statisticsMap.get("userCount"));
//        mv.setViewName("/home");
//        return mv;
//    }
//
//
//
//    @RequestMapping("/login")
//    public ModelAndView login(@RequestParam(defaultValue="false")Boolean error){
//        ModelAndView mv = new ModelAndView();
//        if(error){
//            mv.addObject("isError","账户或密码不正确，请重新输入！");
//        }else {
//            mv.addObject("isError","");
//        }
//        mv.setViewName("/home/login");
//        return mv;
//    }
//
//
//
//    @RequestMapping("/loginButton")
//    @ResponseBody
//    public Result<List<LoginButtonData>> loginButton(HttpServletRequest request){
//        Result<List<LoginButtonData>> result = new Result<List<LoginButtonData>>();
//        HmxUser hmxUser = (HmxUser) request.getSession().getAttribute("userInfo");
//        result.setStatus(10000);
//        result.setData(loginService.loginButton(hmxUser.getId()));
//        return result;
//    }
//
//
//    @RequestMapping(value="/logout", method = RequestMethod.GET)
//    public String logoutPage (HttpServletRequest request, HttpServletResponse response) {
//        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
//        if (auth != null){
//            new SecurityContextLogoutHandler().logout(request, response, auth);
//        }
//        return "redirect:/login?logout";
//    }

    /**
     * 注册用户
     * @param hmxUser
     * @return
     */
    @PostMapping("/add")
    public ResultBean addUser(@ModelAttribute HmxUser hmxUser, String verifyCode, HttpServletRequest request){
        String password = hmxUser.getPassword();
        String userPhone = hmxUser.getUserPhone();
        if(StringUtils.isEmpty(userPhone)){
            return new ResultBean().setCode(Config.FAIL_MOBILE_EMPTY).setContent("手机号不能为空");
        }
        if(StringUtils.isEmpty(password)){
            return new ResultBean().setCode(Config.FAIL_PASSWORD_EMPTY).setContent("密码不能为空");
        }
        if(StringUtils.isEmpty(verifyCode)){
            return new ResultBean().setCode(Config.VERIFY_CODE_EMPTY).setContent("验证码不能为空");
        }
        try {
            HmxUser isHmxUser = hmxUserService.selectUserInfoByUserPhone(userPhone);
            if(isHmxUser != null){
                return new ResultBean().setCode(Config.FAIL_MOBILE_USED).setContent("该手机号已经被注册");
            }
            HmxVerifylog hmxVerifylog = hmxVerifylogService.selectNewVerifylog(userPhone);
            if(hmxVerifylog == null){
                return new ResultBean().setCode(Config.VERIFY_CODE_NOTSEND).setContent("您还没有发送验证码");
            }
            String oldVerifyCode = hmxVerifylog.getVerifyCode();
            if(hmxVerifylog.getIsVerify() == IsVerify.已使用.getState()){
                return new ResultBean().setCode(Config.VERIFY_CODE_OVER).setContent("验证码已被使用");
            }
            if(!verifyCode.equals(oldVerifyCode)){
                return new ResultBean().setCode(Config.VERIFY_CODE_WRONG).setContent("验证码错误");
            }
            hmxUser.setPassword(MD5Util.encode(password));
            Boolean flag = hmxUserService.insert(hmxUser);
            if(!flag){
                return new ResultBean().setCode(Config.FAIL_CODE).setContent("注册用户失败");
            }
            HmxVerifylog hmxVerifylogUpdate = new HmxVerifylog();
            hmxVerifylogUpdate.setVerifyLogId(hmxVerifylog.getVerifyLogId());
            hmxVerifylogUpdate.setIsVerify(IsVerify.已使用.getState());
            hmxVerifylogService.update(hmxVerifylogUpdate);
            return new ResultBean().setCode(Config.SUCCESS_CODE).setContent("注册用户成功");
        } catch (Exception e) {
            LogHelper.logger().error("注册失败", e);
            return new ResultBean().setCode(Config.FAIL_CODE).setContent("注册失败" + e.getMessage());
        }
    }

    /**
     * 获取用户信息
     * @param hmxUser
     * @return
     */
    @GetMapping("/getUserInfo")
    public ResultBean getUserInfo(@ModelAttribute HmxUser hmxUser,HttpServletRequest request){
        if(null == hmxUser.getUserPhone()){
            return new ResultBean().setCode(Config.FAIL_MOBILE_EMPTY).setContent("用户手机号不能为空");
        }
        try {
            HmxUser isHmxUser = hmxUserService.selectUserInfoByUserPhone(hmxUser.getUserPhone());
            if(null == isHmxUser){
                return new ResultBean().setCode(Config.USER_NULL).setContent("通过该手机号没有查到用户信息");
            }
            UserModel userModel = new UserModel();
            BeanUtils.copyProperties(isHmxUser,userModel);
            if(StringUtils.isEmpty(userModel.getHeadPic())){
                //填充默认图片
                userModel.setHeadPic("http://www.sskj.art:8080/images/indexImg/indexHeadPic.png");
            }
            return new ResultBean().setCode(Config.SUCCESS_CODE).setContent("查询到用户").put("user",userModel);
        }catch (Exception e){
            e.printStackTrace();
            return new ResultBean().setCode(Config.FAIL_CODE).setContent("查询失败");
        }
    }

    /**
     * 修改密码
     *  userPhone   手机号
     * oldPassword   旧密码
     * newPassword   新密码
     * verCode    短信验证码
     * @return
     */
    @PostMapping("/modifyPas")
    @Operation("修改密码")
    public ResultBean modifyPas(String userPhone,String oldPassword,String newPassword,String verCode,HttpServletRequest request){
        if(StringUtils.isEmpty(userPhone)){
            return new ResultBean().setCode(Config.FAIL_MOBILE_EMPTY).setContent("用户手机号不能为空");
        }
        if(StringUtils.isEmpty(oldPassword)){
            return new ResultBean().setCode(Config.FAIL_PASSWORD_EMPTY).setContent("旧密码不能为空");
        }
        if(StringUtils.isEmpty(newPassword)){
            return new ResultBean().setCode(Config.FAIL_PASSWORD_EMPTY).setContent("新密码不能为空");
        }
        if(StringUtils.isEmpty(verCode)){
            return new ResultBean().setCode(Config.VERIFY_CODE_EMPTY).setContent("验证码不能为空");
        }
        try {
            HmxVerifylog hmxVerifylog = hmxVerifylogService.selectNewVerifylog(userPhone);
            if(null == hmxVerifylog){
                return new ResultBean().setCode(Config.VERIFY_CODE_NOTSEND).setContent("没有查询到验证码");
            }else{
                if(!verCode.equals(hmxVerifylog.getVerifyCode())){
                    return new ResultBean().setCode(Config.VERIFY_CODE_WRONG).setContent("验证码错误");
                }
            }
            //hmxUser.setPassword(MD5Util.encode(hmxUser.getPassword()));
            boolean flag = hmxUserService.modifyPas(userPhone,oldPassword,newPassword);
            if(flag){
                return new ResultBean().setCode(Config.SUCCESS_CODE).setContent("修改成功");
            }
            return new ResultBean().setCode(Config.FAIL_CODE).setContent("修改失败");
        }catch (Exception e){
            e.printStackTrace();
            return new ResultBean().setCode(Config.FAIL_CODE).setContent("修改失败");
        }
    }

    /**
     * 找回密码
     *  userPhone   手机号
     * newPassword   新密码
     * verCode    短信验证码
     * @return
     */
    @Operation("找回密码")
    @PostMapping("/findPas")
    public ResultBean findPas(String userPhone,String newPassword,String verCode,HttpServletRequest request){
        if(StringUtils.isEmpty(userPhone)){
            return new ResultBean().setCode(Config.FAIL_MOBILE_EMPTY).setContent("用户手机号不能为空");
        }
        if(StringUtils.isEmpty(newPassword)){
            return new ResultBean().setCode(Config.FAIL_PASSWORD_EMPTY).setContent("新密码不能为空");
        }
        if(StringUtils.isEmpty(verCode)){
            return new ResultBean().setCode(Config.VERIFY_CODE_EMPTY).setContent("验证码不能为空");
        }
        try {
            HmxVerifylog hmxVerifylog = hmxVerifylogService.selectNewVerifylog(userPhone);
            if(null == hmxVerifylog){
                return new ResultBean().setCode(Config.FAIL_CODE).setContent("没有查询到验证码");
            }else{
                if(!verCode.equals(hmxVerifylog.getVerifyCode())){
                    return new ResultBean().setCode(Config.FAIL_CODE).setContent("验证码错误");
                }
            }
            //hmxUser.setPassword(MD5Util.encode(hmxUser.getPassword()));
            boolean flag = hmxUserService.findPas(userPhone,newPassword);
            if(flag){
                return new ResultBean().setCode(Config.SUCCESS_CODE).setContent("修改成功");
            }
            return new ResultBean().setCode(Config.FAIL_CODE).setContent("修改失败");
        }catch (Exception e){
            e.printStackTrace();
            return new ResultBean().setCode(Config.FAIL_CODE).setContent("修改失败");
        }
    }



    /**
     * 发送验证码
     * @param hmxUser
     * @return
     */
    @PostMapping("/send")
    public ResultBean smsSend(@ModelAttribute HmxUser hmxUser){
        String userPhone = hmxUser.getUserPhone();
        if(StringUtils.isEmpty(userPhone)){
            return new ResultBean().setCode(Config.FAIL_MOBILE_EMPTY).setContent("手机号不能为空");
        }
        String code = RandomHelper.getRandomNum(6);
        try {
            //-----delete at 20190602  验证码接口就是只用来发送验证码，不敢别的
//			HmxUser isHmxUser = hmxUserService.selectUserInfoByUserPhone(userPhone);
//			if(isHmxUser != null){
//				return new ResultBean().setCode(Config.FAIL_CODE).setContent("该手机号已经被注册");
//			}
            Boolean isSend = smsSendOut.SMSSending(userPhone,code);
            boolean flag = SMSHelper.sendSms(userPhone,code);

            if(!flag){
                return new ResultBean().setCode(Config.FAIL_CODE).setContent("发送验证码失败");
            }
            if(flag){
                HmxVerifylog hmxVerifylog = new HmxVerifylog();
                hmxVerifylog.setAddTime(new Date());
                hmxVerifylog.setVerifyCode(code);
                hmxVerifylog.setVerifyObject(userPhone);
                hmxVerifylog.setVerifyType(0);
                hmxVerifylogService.insert(hmxVerifylog);
                return new ResultBean().setCode(Config.SUCCESS_CODE).setContent("发送验证码成功");
            }
            return new ResultBean().setCode(Config.FAIL_CODE).setContent("发送验证码失败");
        } catch (Exception e) {
            LogHelper.logger().error("操作失败", e);
            return new ResultBean().setCode(Config.FAIL_CODE).setContent("发送验证码失败" + e.getMessage());
        }
    }
    /**
     * 用户名密码登录
     * @param hmxUser
     * @param request
     * @return
     */
    @PostMapping("/login")
    @Operation("登录")
    public ResultBean login(@ModelAttribute HmxUser hmxUser,HttpServletRequest request){
        try {
            String password = hmxUser.getPassword();
            String userPhone = hmxUser.getUserPhone();
            if(StringUtils.isEmpty(userPhone)){
                return new ResultBean().setCode(Config.FAIL_MOBILE_EMPTY).setContent("用户手机号不能为空");
            }
            if(StringUtils.isEmpty(password)){
                return new ResultBean().setCode(Config.FAIL_PASSWORD_EMPTY).setContent("用户密码不能为空");
            }
            HmxUser user = hmxUserService.login(hmxUser);
            if(user == null){
                return new ResultBean().setCode(Config.FAIL_CODE).setContent("登录账号或密码错误");
            }
            String ip = HttpUtils.getIp(request);
            LoginUser simpleUser = new LoginUser();
            simpleUser.setIp(ip);
            simpleUser.setUserId(user.getId());
            //用户类型默认1暂定
            simpleUser.setUserUserType(1);
            String token = jwtUtil.createJWT(simpleUser);
            UserModel userModel = new UserModel();
            BeanUtils.copyProperties(user, userModel);
            setAccount(hmxUser);
            return new ResultBean().setCode(Config.SUCCESS_CODE).setContent("登录成功").put("token", token).put("user",userModel);
        } catch (Exception e) {
            LogHelper.logger().error("登录失败", e);
            return new ResultBean().setCode(Config.FAIL_CODE).setContent("登录失败" + e.getMessage());
        }
    }
}
