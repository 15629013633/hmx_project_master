package com.hmx.user.controller;


import java.util.Date;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.hmx.annotations.NeedLogin;
import com.hmx.user.entity.HmxUser;
import com.hmx.user.service.HmxUserService;
import com.hmx.utils.enums.IsVerify;
import com.hmx.utils.http.HttpUtils;
import com.hmx.utils.logger.LogHelper;
import com.hmx.utils.random.RandomHelper;
import com.hmx.utils.result.Config;
import com.hmx.utils.result.LoginUser;
import com.hmx.utils.result.ResultBean;
import com.hmx.utils.secret.JwtUtil;
import com.hmx.utils.secret.MD5Util;
import com.hmx.utils.sms.SMSSendOut;
import com.hmx.verifylog.entity.HmxVerifylog;
import com.hmx.verifylog.service.HmxVerifylogService;


@RestController
@RequestMapping("/user")
public class UserController {

	@Autowired
	private HmxUserService hmxUserService;
	@Autowired
	private SMSSendOut SMSSendOut;
	@Autowired
	private HmxVerifylogService hmxVerifylogService;
	@Autowired
	private JwtUtil jwtUtil;
	
	@GetMapping("/{id}")
	@NeedLogin
	public ResultBean getUserInfo(@PathVariable Integer id){
		HmxUser user = hmxUserService.info(id);
		return new ResultBean().put("user", user);
	}
	/**
	 * 注册用户
	 * @param hmxUser
	 * @return
	 */
	@PostMapping("/add")
	public ResultBean addUser(@ModelAttribute HmxUser hmxUser,String verifyCode,HttpServletRequest request){
		String password = hmxUser.getPassword();
		String userPhone = hmxUser.getUserPhone();
		if(StringUtils.isEmpty(userPhone)){
			return new ResultBean().setCode(Config.FAIL_FIELD_EMPTY).setContent("手机号不能为空");
		}
		if(StringUtils.isEmpty(password)){
			return new ResultBean().setCode(Config.FAIL_FIELD_EMPTY).setContent("密码不能为空");
		}
		if(StringUtils.isEmpty(verifyCode)){
			return new ResultBean().setCode(Config.FAIL_FIELD_EMPTY).setContent("验证码不能为空");
		}
		try {
			HmxUser isHmxUser = hmxUserService.selectUserInfoByUserPhone(userPhone);
			if(isHmxUser != null){
				return new ResultBean().setCode(Config.FAIL_CODE).setContent("该手机号已经被注册");
			}
			HmxVerifylog hmxVerifylog = hmxVerifylogService.selectNewVerifylog(userPhone);
			if(hmxVerifylog == null){
				return new ResultBean().setCode(Config.FAIL_CODE).setContent("您还没有发送验证码");
			}
			String oldVerifyCode = hmxVerifylog.getVerifyCode();
			if(hmxVerifylog.getIsVerify() == IsVerify.已使用.getState()){
				return new ResultBean().setCode(Config.FAIL_CODE).setContent("验证码已被使用");
			}
			if(!verifyCode.equals(oldVerifyCode)){
				return new ResultBean().setCode(Config.FAIL_CODE).setContent("验证码错误");
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
	 * 发送验证码
	 * @param hmxUser
	 * @return
	 */
	@PostMapping("/send")
	public ResultBean smsSend(@ModelAttribute HmxUser hmxUser){
		String userPhone = hmxUser.getUserPhone();
		if(StringUtils.isEmpty(userPhone)){
			return new ResultBean().setCode(Config.FAIL_CODE).setContent("手机号不能为空");
		}
		String code = RandomHelper.getRandomNum(6);
		try {
			HmxUser isHmxUser = hmxUserService.selectUserInfoByUserPhone(userPhone);
			if(isHmxUser != null){
				return new ResultBean().setCode(Config.FAIL_CODE).setContent("该手机号已经被注册");
			}
			boolean flag = SMSSendOut.SMSSending(userPhone, code);
			if(!flag){
				return new ResultBean().setCode(Config.FAIL_CODE).setContent("发送验证码失败");
			}
			HmxVerifylog hmxVerifylog = new HmxVerifylog();
			hmxVerifylog.setAddTime(new Date());
			hmxVerifylog.setVerifyCode(code);
			hmxVerifylog.setVerifyObject(userPhone);
			hmxVerifylog.setVerifyType(0);
			flag = hmxVerifylogService.insert(hmxVerifylog);
			return new ResultBean().setCode(Config.SUCCESS_CODE).setContent("发送验证码成功");
		} catch (Exception e) {
			LogHelper.logger().error("操作失败", e);
			return new ResultBean().setCode(Config.FAIL_CODE).setContent("发送验证码失败" + e.getMessage());
		}
	}
	/**
	 * 用户登录
	 * @param hmxUser
	 * @param request
	 * @return
	 */
	@PostMapping("/login")
	public ResultBean login(@ModelAttribute HmxUser hmxUser,HttpServletRequest request){
		try {
			String password = hmxUser.getPassword();
			String userPhone = hmxUser.getUserPhone();
			if(StringUtils.isEmpty(userPhone)){
				return new ResultBean().setCode(Config.FAIL_FIELD_EMPTY).setContent("用户账号不能为空");
			}
			if(StringUtils.isEmpty(password)){
				return new ResultBean().setCode(Config.FAIL_FIELD_EMPTY).setContent("用户密码不能为空");
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
			return new ResultBean().setCode(Config.SUCCESS_CODE).setContent("登录成功").put("token", token);
		} catch (Exception e) {
			LogHelper.logger().error("登录失败", e);
			return new ResultBean().setCode(Config.FAIL_CODE).setContent("登录失败" + e.getMessage());
		}
	}
}
