package com.hmx.user.controller;


import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.URL;
import java.net.URLConnection;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import com.hmx.aop.Operation;
import com.hmx.base.controller.BaseController;
import com.hmx.user.dto.HmxUserDto;
import com.hmx.user.entity.UserModel;
import com.hmx.utils.sms.SMSHelper;
import com.hmx.verifylog.dto.HmxVerifylogDto;
import org.springframework.beans.BeanUtils;
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

/**
 * 用户注册成功必须是有手机号的
 *
 */
@RestController
@RequestMapping("/user")
public class UserController extends BaseController {

	@Autowired
	private HmxUserService hmxUserService;
	@Autowired
	private SMSSendOut SMSSendOut;
	@Autowired
	private HmxVerifylogService hmxVerifylogService;
	@Autowired
	private JwtUtil jwtUtil;

	@Autowired
	private SMSSendOut smsSendOut;
	
//	@GetMapping("/getById")
//	@NeedLogin
//	public ResultBean getUserInfo(String id){
//		HmxUser user = hmxUserService.info(Integer.valueOf(id));
//		return resultBean.put("user", user);
//	}


	/**
	 * 注册用户
	 * @param hmxUser
	 * @return
	 */
	@PostMapping("/register")
	public ResultBean addUser(@ModelAttribute HmxUser hmxUser,String verifyCode,HttpServletRequest request){
		String password = hmxUser.getPassword();
		String userPhone = hmxUser.getUserPhone();
		ResultBean resultBean = new ResultBean();
		resultBean.put("content","");
		if(StringUtils.isEmpty(userPhone)){
			return resultBean.setCode(Config.FAIL_MOBILE_EMPTY).setContent("手机号不能为空");
		}
		if(StringUtils.isEmpty(password)){
			return resultBean.setCode(Config.FAIL_PASSWORD_EMPTY).setContent("密码不能为空");
		}
		if(StringUtils.isEmpty(verifyCode)){
			return resultBean.setCode(Config.VERIFY_CODE_EMPTY).setContent("验证码不能为空");
		}
		try {
			HmxUser isHmxUser = hmxUserService.selectUserInfoByUserPhone(userPhone);
			if(isHmxUser != null){
				return resultBean.setCode(Config.FAIL_MOBILE_USED).setContent("该手机号已经被注册");
			}
			HmxVerifylog hmxVerifylog = hmxVerifylogService.selectNewVerifylog(userPhone);
			if(hmxVerifylog == null){
				return resultBean.setCode(Config.VERIFY_CODE_NOTSEND).setContent("您还没有发送验证码");
			}
			String oldVerifyCode = hmxVerifylog.getVerifyCode();
			if(hmxVerifylog.getIsVerify() == IsVerify.已使用.getState()){
				return resultBean.setCode(Config.VERIFY_CODE_OVER).setContent("验证码已被使用");
			}
			if(!verifyCode.equals(oldVerifyCode)){
				return resultBean.setCode(Config.VERIFY_CODE_WRONG).setContent("验证码错误");
			}
			hmxUser.setPassword(MD5Util.encode(password));
			Boolean flag = hmxUserService.insert(hmxUser);
			if(!flag){
				return resultBean.setCode(Config.FAIL_CODE).setContent("注册用户失败");
			}
			HmxVerifylog hmxVerifylogUpdate = new HmxVerifylog();
			hmxVerifylogUpdate.setVerifyLogId(hmxVerifylog.getVerifyLogId());
			hmxVerifylogUpdate.setIsVerify(IsVerify.已使用.getState());
			hmxVerifylogService.update(hmxVerifylogUpdate);
			UserModel userModel = new UserModel();
			BeanUtils.copyProperties(hmxUser, userModel);
			return resultBean.setCode(Config.SUCCESS_CODE).setContent("注册用户成功").put("content",userModel);
		} catch (Exception e) {
			LogHelper.logger().error("注册失败", e);
			return resultBean.setCode(Config.FAIL_CODE).setContent("注册失败" + e.getMessage());
		}
	}

	/**
	 * 修改用户信息
	 * @param hmxUser
	 * @return
	 */
	@PostMapping("/edit")
	public ResultBean editUser(@ModelAttribute HmxUser hmxUser,HttpServletRequest request){
		ResultBean resultBean = new ResultBean();
		resultBean.put("content","");
		if(null == hmxUser.getId() || 0 == hmxUser.getId()){
			return resultBean.setCode(Config.FAIL_FIELD_EMPTY).setContent("用户id不能为空");
		}
		try {
			//hmxUser.setPassword(MD5Util.encode(hmxUser.getPassword()));
			boolean flag = hmxUserService.update(hmxUser);
			if(flag){
				UserModel userModel = new UserModel();
				BeanUtils.copyProperties(hmxUser, userModel);
				return resultBean.setCode(Config.SUCCESS_CODE).setContent("修改成功").put("content",userModel);
			}
			return resultBean.setCode(Config.FAIL_CODE).setContent("修改失败");
		}catch (Exception e){
			e.printStackTrace();
			return resultBean.setCode(Config.FAIL_CODE).setContent("修改失败");
		}
	}

	/**
	 * 获取用户信息
	 * @param hmxUser
	 * @return
	 */
	@GetMapping("/getUserInfo")
	public ResultBean getUserInfo(@ModelAttribute HmxUser hmxUser,HttpServletRequest request){
		ResultBean resultBean = new ResultBean();
		resultBean.put("content","");
		if(null == hmxUser.getUserPhone()){
			return resultBean.setCode(Config.FAIL_MOBILE_EMPTY).setContent("用户手机号不能为空");
		}
		try {
			HmxUser isHmxUser = hmxUserService.selectUserInfoByUserPhone(hmxUser.getUserPhone());
			if(null == isHmxUser){
				return resultBean.setCode(Config.USER_NULL).setContent("通过该手机号没有查到用户信息");
			}
			UserModel userModel = new UserModel();
			BeanUtils.copyProperties(isHmxUser,userModel);
//			if(StringUtils.isEmpty(userModel.getHeadPic())){
//				//填充默认图片
//				userModel.setHeadPic("http://www.sskj.art:8080/images/indexImg/indexHeadPic.png");
//			}
			return resultBean.setCode(Config.SUCCESS_CODE).setContent("查询到用户").put("content",userModel);
		}catch (Exception e){
			e.printStackTrace();
			return resultBean.setCode(Config.FAIL_CODE).setContent("查询失败");
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
	public ResultBean modifyPas(String userPhone,String oldPassword,String newPassword,String verCode,HttpServletRequest request){
		ResultBean resultBean = new ResultBean();
		resultBean.put("content","");
		if(StringUtils.isEmpty(userPhone)){
			return resultBean.setCode(Config.FAIL_MOBILE_EMPTY).setContent("用户手机号不能为空");
		}
		if(StringUtils.isEmpty(oldPassword)){
			return resultBean.setCode(Config.FAIL_PASSWORD_EMPTY).setContent("旧密码不能为空");
		}
		if(StringUtils.isEmpty(newPassword)){
			return resultBean.setCode(Config.FAIL_PASSWORD_EMPTY).setContent("新密码不能为空");
		}
		if(StringUtils.isEmpty(verCode)){
			return resultBean.setCode(Config.VERIFY_CODE_EMPTY).setContent("验证码不能为空");
		}
		try {
			HmxVerifylog hmxVerifylog = hmxVerifylogService.selectNewVerifylog(userPhone);
			if(null == hmxVerifylog){
				return resultBean.setCode(Config.VERIFY_CODE_NOTSEND).setContent("没有查询到验证码");
			}else{
				if(!verCode.equals(hmxVerifylog.getVerifyCode())){
					return resultBean.setCode(Config.VERIFY_CODE_WRONG).setContent("验证码错误");
				}
			}
			//hmxUser.setPassword(MD5Util.encode(hmxUser.getPassword()));
			boolean flag = hmxUserService.modifyPas(userPhone,oldPassword,newPassword);
			if(flag){
				return resultBean.setCode(Config.SUCCESS_CODE).setContent("修改成功");
			}
			return resultBean.setCode(Config.FAIL_CODE).setContent("修改失败");
		}catch (Exception e){
			e.printStackTrace();
			return resultBean.setCode(Config.FAIL_CODE).setContent("修改失败");
		}
	}

	/**
	 * 找回密码
	 *  userPhone   手机号
	 * newPassword   新密码
	 * verCode    短信验证码
	 * @return
	 */
	@PostMapping("/findPas")
	public ResultBean findPas(String userPhone,String newPassword,String verCode,HttpServletRequest request){
		ResultBean resultBean = new ResultBean();
		resultBean.put("content","");
		if(StringUtils.isEmpty(userPhone)){
			return resultBean.setCode(Config.FAIL_MOBILE_EMPTY).setContent("用户手机号不能为空");
		}
		if(StringUtils.isEmpty(newPassword)){
			return resultBean.setCode(Config.FAIL_PASSWORD_EMPTY).setContent("新密码不能为空");
		}
		if(StringUtils.isEmpty(verCode)){
			return resultBean.setCode(Config.VERIFY_CODE_EMPTY).setContent("验证码不能为空");
		}
		try {
			HmxVerifylog hmxVerifylog = hmxVerifylogService.selectNewVerifylog(userPhone);
			if(null == hmxVerifylog){
				return resultBean.setCode(Config.FAIL_CODE).setContent("没有查询到验证码");
			}else{
				if(!verCode.equals(hmxVerifylog.getVerifyCode())){
					return resultBean.setCode(Config.FAIL_CODE).setContent("验证码错误");
				}
			}
			HmxUser hmxUser  = hmxUserService.selectUserInfoByUserPhone(userPhone);
			if(null == hmxUser){
				return resultBean.setCode(Config.USER_NULL).setContent("没有查询到该手机号注册记录");
			}
			boolean flag = hmxUserService.findPas(userPhone,newPassword);
			if(flag){
				UserModel userModel = new UserModel();
				BeanUtils.copyProperties(hmxUser, userModel);
				return resultBean.setCode(Config.SUCCESS_CODE).setContent("修改成功").put("content",userModel);
			}
			return resultBean.setCode(Config.FAIL_CODE).setContent("修改失败");
		}catch (Exception e){
			e.printStackTrace();
			return resultBean.setCode(Config.FAIL_CODE).setContent("修改失败");
		}
	}



	/**
	 * 发送验证码
	 * @param hmxUser
	 * @return
	 */
	@PostMapping("/sendCode")
	public ResultBean smsSend(@ModelAttribute HmxUser hmxUser){
		ResultBean resultBean = new ResultBean();
		resultBean.put("content","");
		String userPhone = hmxUser.getUserPhone();
		if(StringUtils.isEmpty(userPhone)){
			return resultBean.setCode(Config.FAIL_MOBILE_EMPTY).setContent("手机号不能为空");
		}
		String code = RandomHelper.getRandomNum(6);
		try {
			//-----delete at 20190602  验证码接口就是只用来发送验证码，不敢别的
//			HmxUser isHmxUser = hmxUserService.selectUserInfoByUserPhone(userPhone);
//			if(isHmxUser != null){
//				return resultBean.setCode(Config.FAIL_CODE).setContent("该手机号已经被注册");
//			}
//			Boolean isSend = smsSendOut.SMSSending(userPhone,code);
			boolean flag = SMSHelper.sendSms(userPhone,code);

			if(!flag){
				return resultBean.setCode(Config.FAIL_CODE).setContent("发送验证码失败");
			}
			if(flag){
				HmxVerifylog hmxVerifylog = new HmxVerifylog();
				hmxVerifylog.setAddTime(new Date());
				hmxVerifylog.setVerifyCode(code);
				hmxVerifylog.setVerifyObject(userPhone);
				hmxVerifylog.setVerifyType(0);
				hmxVerifylogService.insert(hmxVerifylog);
				return resultBean.setCode(Config.SUCCESS_CODE).setContent("发送验证码成功");
			}
			return resultBean.setCode(Config.FAIL_CODE).setContent("发送验证码失败");
		} catch (Exception e) {
			LogHelper.logger().error("操作失败", e);
			return resultBean.setCode(Config.FAIL_CODE).setContent("发送验证码失败" + e.getMessage());
		}
	}

	/**
	 * 模拟发送验证码
	 * @param hmxUser
	 * @return
	 */
	@GetMapping("/sendCodeTest")
	public ResultBean sendCodeTest(@ModelAttribute HmxUser hmxUser){
		ResultBean resultBean = new ResultBean();
		resultBean.put("content","");
		String userPhone = hmxUser.getUserPhone();
		if(StringUtils.isEmpty(userPhone)){
			return resultBean.setCode(Config.FAIL_MOBILE_EMPTY).setContent("手机号不能为空");
		}
		String code = RandomHelper.getRandomNum(6);
		try {
			if(true){
				HmxVerifylog hmxVerifylog = new HmxVerifylog();
				hmxVerifylog.setAddTime(new Date());
				hmxVerifylog.setVerifyCode(code);
				hmxVerifylog.setVerifyObject(userPhone);
				hmxVerifylog.setVerifyType(0);
				hmxVerifylogService.insert(hmxVerifylog);
				return resultBean.setCode(Config.SUCCESS_CODE).setContent("发送验证码成功").put("content",code);
			}
			return resultBean.setCode(Config.FAIL_CODE).setContent("发送验证码失败");
		} catch (Exception e) {
			LogHelper.logger().error("操作失败", e);
			return resultBean.setCode(Config.FAIL_CODE).setContent("发送验证码失败" + e.getMessage());
		}
	}

	/**
	 * 用户名密码登录
	 * @param hmxUser
	 * @param request
	 * @return
	 */
	@PostMapping("/login")
	@Operation("测试log,登录")
	public ResultBean login(@ModelAttribute HmxUser hmxUser,HttpServletRequest request){
		ResultBean resultBean = new ResultBean();
		resultBean.put("content","");
		try {

			String password = hmxUser.getPassword();
			String userPhone = hmxUser.getUserPhone();
			if(StringUtils.isEmpty(userPhone)){
				return resultBean.setCode(Config.FAIL_MOBILE_EMPTY).setContent("用户手机号不能为空");
			}
			if(StringUtils.isEmpty(password)){
				return resultBean.setCode(Config.FAIL_PASSWORD_EMPTY).setContent("用户密码不能为空");
			}
			HmxUser user = hmxUserService.login(hmxUser);
			if(user == null){
				return resultBean.setCode(Config.FAIL_CODE).setContent("登录账号或密码错误");
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
			setAccount(user);
			return resultBean.setCode(Config.SUCCESS_CODE).setContent("登录成功").put("content",userModel);
		} catch (Exception e) {
			LogHelper.logger().error("登录失败", e);
			return resultBean.setCode(Config.FAIL_CODE).setContent("登录失败" + e.getMessage());
		}
	}

	/**
	 * 验证码登录  检查如果未注册则注册，注册了就直接返回用户信息
	 * @param hmxVerifylogDto
	 * @param request
	 * @return
	 */
	@PostMapping("/loginByCode")
	public ResultBean loginByCode(HmxVerifylogDto hmxVerifylogDto, HttpServletRequest request){
		ResultBean resultBean = new ResultBean();
		resultBean.put("content","");
		try {
			String userPhone = hmxVerifylogDto.getVerifyObject();
			String verifyCode = hmxVerifylogDto.getVerifyCode();
			if(StringUtils.isEmpty(verifyCode)){
				return resultBean.setCode(Config.VERIFY_CODE_EMPTY).setContent("验证码不能为空");
			}
			if(StringUtils.isEmpty(userPhone)){
				return resultBean.setCode(Config.FAIL_MOBILE_EMPTY).setContent("手机号码不能为空");
			}
			//检验验证码是否正确
			//List<HmxVerifylog> list = hmxVerifylogService.list(hmxVerifylogDto);
			String ip = HttpUtils.getIp(request);
			LoginUser simpleUser = new LoginUser();
			simpleUser.setIp(ip);
			//用户类型默认1暂定
			simpleUser.setUserUserType(1);

			HmxVerifylog hmxVerifylog = hmxVerifylogService.selectNewVerifylog(userPhone);
			if(hmxVerifylog == null){
				return resultBean.setCode(Config.VERIFY_CODE_NOTSEND).setContent("您还没有发送验证码");
			}
			String oldVerifyCode = hmxVerifylog.getVerifyCode();
			if(hmxVerifylog.getIsVerify() == IsVerify.已使用.getState()){
				return resultBean.setCode(Config.VERIFY_CODE_OVER).setContent("验证码已被使用");
			}
			if(!verifyCode.equals(oldVerifyCode)){
				return resultBean.setCode(Config.VERIFY_CODE_WRONG).setContent("验证码错误");
			}
			UserModel userModel = new UserModel();
//			if(null != list && list.size() > 0){
				//检查该手机号是否已经注册
				HmxUser hmxUser = hmxUserService.selectUserInfoByUserPhone(hmxVerifylogDto.getVerifyObject());
				if(null == hmxUser){
					//注册用户  用户名默认为手机号   密码默认手机号后六位
					HmxUser registerUser = new HmxUser();
					registerUser.setUserName(userPhone);
					//registerUser.setPassword(MD5Util.encode(userPhone.substring(userPhone.length()-6,userPhone.length())));
					registerUser.setUserPhone(userPhone);
					registerUser.setType(1);
					Boolean flag = hmxUserService.insert(registerUser);
					if(!flag){
						return resultBean.setCode(Config.FAIL_CODE).setContent("后台自动注册失败");
					}

					simpleUser.setUserId(registerUser.getId());

					String token = jwtUtil.createJWT(simpleUser);
					BeanUtils.copyProperties(registerUser, userModel);
					//return resultBean.setCode(Config.SUCCESS_CODE).setContent("登录成功").put("content",userModel);
				}else {
					//String ip = HttpUtils.getIp(request);
					//LoginUser simpleUser = new LoginUser();
//					simpleUser.setIp(ip);
					simpleUser.setUserId(hmxUser.getId());
					String token = jwtUtil.createJWT(simpleUser);

					BeanUtils.copyProperties(hmxUser, userModel);
					//return resultBean.setCode(Config.SUCCESS_CODE).setContent("登录成功").put("content",userModel);
				}
			HmxVerifylog hmxVerifylogUpdate = new HmxVerifylog();
			hmxVerifylogUpdate.setVerifyLogId(hmxVerifylog.getVerifyLogId());
			hmxVerifylogUpdate.setIsVerify(IsVerify.已使用.getState());
			hmxVerifylogService.update(hmxVerifylogUpdate);
			return resultBean.setCode(Config.SUCCESS_CODE).setContent("登录成功").put("content",userModel);
				//return resultBean.setCode(Config.SUCCESS_CODE).setContent("登录成功").put("mobile", hmxVerifylogDto.getVerifyObject());
//			}else {
//				return resultBean.setCode(Config.FAIL_CODE).setContent("验证码验证失败");
//			}
		} catch (Exception e) {
			LogHelper.logger().error("登录失败", e);
			return resultBean.setCode(Config.FAIL_CODE).setContent("登录失败" + e.getMessage());
		}
	}

	/**
	 * 第三方登录
	 * @param hmxUserDto
	 * @param request
	 * @return
	 */
	@PostMapping("/loginByThirdLoginId")
	public ResultBean loginByThirdLoginId(HmxUserDto hmxUserDto, HttpServletRequest request){
		ResultBean resultBean = new ResultBean();
		resultBean.put("content","");
		try {
			String ip = HttpUtils.getIp(request);
			LoginUser simpleUser = new LoginUser();
			simpleUser.setIp(ip);
			String wxId = hmxUserDto.getWxId();
			String qqId = hmxUserDto.getQqId();
			if(StringUtils.isEmpty(wxId) && StringUtils.isEmpty(qqId)){
				return resultBean.setCode(Config.FAIL_THID_EMPTY).setContent("第三方id不能为空");
			}
			List<HmxUser> list = hmxUserService.list(hmxUserDto);
			if(list == null || list.size() == 0){
				return resultBean.setCode(Config.USER_NULL).setContent("登录失败,没有找到该用户第三方注册记录");
			}
			HmxUser user = list.get(0);
			simpleUser.setUserId(user.getId());

			String token = jwtUtil.createJWT(simpleUser);
			UserModel userModel = new UserModel();
			BeanUtils.copyProperties(user, userModel);
			return resultBean.setCode(Config.SUCCESS_CODE).setContent("登录成功").put("content",userModel);

		} catch (Exception e) {
			LogHelper.logger().error("登录失败", e);
			return resultBean.setCode(Config.FAIL_CODE).setContent("登录失败" + e.getMessage());
		}
	}

	/**
	 * 第三方注册
	 * @param hmxUser
	 * @return
	 */
	@PostMapping("/thirdRegister")
	public ResultBean thirdRegister(@ModelAttribute HmxUser hmxUser,String verifyCode,HttpServletRequest request){
		ResultBean resultBean = new ResultBean();
		resultBean.put("content","");
		//String password = hmxUser.getPassword();
		String userPhone = hmxUser.getUserPhone();
		String wxId = hmxUser.getWxId();
		String qqId = hmxUser.getQqId();
		if(StringUtils.isEmpty(userPhone)){
			return resultBean.setCode(Config.FAIL_MOBILE_EMPTY).setContent("手机号不能为空");
		}
		if(StringUtils.isEmpty(verifyCode)){
			return resultBean.setCode(Config.VERIFY_CODE_EMPTY).setContent("验证码不能为空");
		}
		if(StringUtils.isEmpty(wxId) && StringUtils.isEmpty(qqId)){
			return resultBean.setCode(Config.FAIL_THID_EMPTY).setContent("第三方id不能为空");
		}
		try {
			//之前该手机号用户已经通过非第三方注册方式完成注册
			HmxVerifylog hmxVerifylog = hmxVerifylogService.selectNewVerifylog(userPhone);
			HmxUser isHmxUser = hmxUserService.selectUserInfoByUserPhone(userPhone);
			if(isHmxUser != null){
					//能查到但是没有第三方id，则更新用户信息，将第三方id更新进去，以保证第三方登录成功
					hmxUser.setPassword(null);
				if(!StringUtils.isEmpty(wxId)){
					isHmxUser.setWxId(wxId);
				}
				if(!StringUtils.isEmpty(qqId)){
					isHmxUser.setQqId(qqId);
				}
					boolean flag = hmxUserService.update(isHmxUser);
					HmxVerifylog hmxVerifylogUpdate = new HmxVerifylog();
					hmxVerifylogUpdate.setVerifyLogId(hmxVerifylog.getVerifyLogId());
					hmxVerifylogUpdate.setIsVerify(IsVerify.已使用.getState());
					hmxVerifylogService.update(hmxVerifylogUpdate);
					if(!flag){
						return resultBean.setCode(Config.FAIL_CODE).setContent("注册失败");
					}else {
						UserModel userModel = new UserModel();
						BeanUtils.copyProperties(isHmxUser, userModel);
						return resultBean.setCode(Config.SUCCESS_CODE).setContent("注册用户成功").put("content",userModel);
					}
			}
			else {//用户不存在，走注册流程
				//HmxVerifylog hmxVerifylog = hmxVerifylogService.selectNewVerifylog(userPhone);
				if(hmxVerifylog == null){
					return resultBean.setCode(Config.VERIFY_CODE_NOTSEND).setContent("您还没有发送验证码");
				}
				String oldVerifyCode = hmxVerifylog.getVerifyCode();
				if(hmxVerifylog.getIsVerify() == IsVerify.已使用.getState()){
					return resultBean.setCode(Config.VERIFY_CODE_OVER).setContent("验证码已被使用");
				}
				if(!verifyCode.equals(oldVerifyCode)){
					return resultBean.setCode(Config.VERIFY_CODE_WRONG).setContent("验证码错误");
				}
				//hmxUser.setPassword(MD5Util.encode(password));
				hmxUser.setUserName(StringUtils.isEmpty(wxId) ? qqId : wxId);
				Boolean flag = hmxUserService.insert(hmxUser);
				HmxVerifylog hmxVerifylogUpdate = new HmxVerifylog();
				hmxVerifylogUpdate.setVerifyLogId(hmxVerifylog.getVerifyLogId());
				hmxVerifylogUpdate.setIsVerify(IsVerify.已使用.getState());
				hmxVerifylogService.update(hmxVerifylogUpdate);
				if(!flag){
					return resultBean.setCode(Config.FAIL_CODE).setContent("注册用户失败");
				}
				UserModel userModel = new UserModel();
				BeanUtils.copyProperties(hmxUser, userModel);
				return resultBean.setCode(Config.SUCCESS_CODE).setContent("注册用户成功").put("content",userModel);
			}
		} catch (Exception e) {
			LogHelper.logger().error("注册失败", e);
			return resultBean.setCode(Config.FAIL_CODE).setContent("注册失败" + e.getMessage());
		}
	}



	public static void main(String[] arg0){
		//SMSHelper.sendSms("13076949806","1111");
		String usrPhone = "130176949806";
		System.out.println(MD5Util.encode("222222"));
	}


}
