package com.hmx.user.controller;


import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.URL;
import java.net.URLConnection;
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
//			boolean flag = SMSSendOut.SMSSending(userPhone, code);
//			if(!flag){
//				return new ResultBean().setCode(Config.FAIL_CODE).setContent("发送验证码失败");
//			}
//			HmxVerifylog hmxVerifylog = new HmxVerifylog();
//			hmxVerifylog.setAddTime(new Date());
//			hmxVerifylog.setVerifyCode(code);
//			hmxVerifylog.setVerifyObject(userPhone);
//			hmxVerifylog.setVerifyType(0);
//			flag = hmxVerifylogService.insert(hmxVerifylog);
			boolean flag = sendSms(userPhone,code);
			if(!flag){
				return new ResultBean().setCode(Config.FAIL_CODE).setContent("发送验证码失败");
			}
			if(flag){
				return new ResultBean().setCode(Config.SUCCESS_CODE).setContent("发送验证码成功");
			}
			return new ResultBean().setCode(Config.FAIL_CODE).setContent("发送验证码失败");
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

	public static void main(String[] arg0){
		sendSms("13076949806","1111");
	}

	public static Boolean sendSms(String mobile, String code) {
		Boolean flag=true;
		try {
			StringBuffer sb=new StringBuffer();
			sb.append("action=send&");
			sb.append("userid=&");
			sb.append("account=8T00134&");
			sb.append("password=8T0013443&");
			sb.append("mobile="+mobile+"&");
			sb.append("content=【黄梅戏资源库】您的验证码是"+code+"，请在一分钟内进行验证!如非本人操作，请忽略本短信"+"&");
			sb.append("sendTime=&");
			sb.append("extno=&");
			String xmlStr=sendPost("https://dx.ipyy.net/sms.aspx", sb.toString());
//			String Success=parseXml(xmlStr);//短信发送成功的标记为 Success
//			if ("Success".equals(Success)) {
//				flag=true;
//			}else {
//				flag=false;
//			}

			System.out.println(xmlStr);
		} catch (Exception e) {
			e.printStackTrace();
			flag=false;
		}
		return flag;
	}

	/**
	 * 向指定 URL 发送POST方法的请求
	 *
	 * @param url
	 *            发送请求的 URL
	 * @param param
	 *            请求参数，请求参数应该是 name1=value1&name2=value2 的形式。
	 * @return 所代表远程资源的响应结果
	 */
	public static String sendPost(String url, String param) {
		PrintWriter out = null;
		BufferedReader in = null;
		String result = "";
		try {
			URL realUrl = new URL(url);
			// 打开和URL之间的连接
			URLConnection conn = realUrl.openConnection();
			// 设置通用的请求属性
			conn.setRequestProperty("accept", "*/*");
			conn.setRequestProperty("connection", "Keep-Alive");
			conn.setRequestProperty("user-agent", "Mozilla/4.0 (compatible; MSIE 6.0; WinDo1ws NT 5.1;SV1)");
			// 发送POST请求必须设置如下两行
			conn.setDoOutput(true);
			conn.setDoInput(true);
			// 获取URLConnection对象对应的输出流
			out = new PrintWriter(conn.getOutputStream());
			// 发送请求参数
			out.print(param);
			// flush输出流的缓冲
			out.flush();
			// 定义BufferedReader输入流来读取URL的响应
			in = new BufferedReader(new InputStreamReader(conn.getInputStream()));
			String line;
			while ((line = in.readLine()) != null) {
				result += line;
			}
		} catch (Exception e) {
			System.out.println("发送 POST 请求出现异常！" + e);
			e.printStackTrace();
		}
		// 使用finally块来关闭输出流、输入流
		finally {
			try {
				if (out != null) {
					out.close();
				}
				if (in != null) {
					in.close();
				}
			} catch (IOException ex) {
				ex.printStackTrace();
			}
		}
		return result;
	}
}
