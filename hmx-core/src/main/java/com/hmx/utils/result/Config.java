package com.hmx.utils.result;

public class Config {

	
	/**
	 * Java Web Token 加解密token的秘钥
	 */
	public static final String JJWT_SECURITY_KEY="hmx.com";
	
	
	/**
	 * 请求成功的返回码
	 */
	public static final Integer SUCCESS_CODE = 10000;
	
	
	/**
	 * 操作失败
	 */
	public static final Integer FAIL_CODE = 10004;
	
	
	/**
	 * 字段为空的返回码
	 */
	public static final Integer FAIL_FIELD_EMPTY = 10001;
	
	/**
	 * 用户名名错误
	 */
	public static final Integer PASSWORD_ERROR_WRONG = 10002;
	
	/**
	 * 验证码错误
	 */
	public static final Integer VERIFY_CODE_WRONG = 10003;

	/**
	 * 没有发送验证码
	 */
	public static final Integer VERIFY_CODE_NOTSEND = 10030;


	/**
	 * 验证码被使用
	 */
	public static final Integer VERIFY_CODE_OVER = 10031;

	/**
	 * 验证码为空
	 */
	public static final Integer VERIFY_CODE_EMPTY = 10032;

	/**
	 * 手机号不能为空
	 */
	public static final Integer FAIL_MOBILE_EMPTY = 10033;

	/**
	 * 第三方id不能为空
	 */
	public static final Integer FAIL_THID_EMPTY = 10034;

	/**
	 * 密码不能为空
	 */
	public static final Integer FAIL_PASSWORD_EMPTY = 10035;

	/**
	 * 手机号已经被注册
	 */
	public static final Integer FAIL_MOBILE_USED = 10036;
	/**
	 * 操作的数据重复
	 */
	public static final Integer REAPET_ERROR = 10005;
    
    /**
     * 登录超时或者未登录的非法请求
     */
    public static final Integer LOGIN_OUT_TIME = 10006;
    
    /**
     * 文件上传异常
     */
    public static final Integer UPLOAD_ERROR = 10007;
    
    /**
     * 用户类型错误
     */
    public static final Integer USERTYPE_ERROR = 10010;
    
    /**
     * @author lzg
     * 请求成功，分页没有数据(即从第一页开始就没有数据)
     */
    public static final Integer CONTENT_NULL = 10008;
    /**
     * @author lzg
     * 请求成功，分页当前页没有数据(从第二页或第N页开始就没有数据)
     */
    public static final Integer PAGE_NULL = 10009;
    /**
     * 用户没有注册
     */
    public static final Integer USER_NULL = 10011;

	/**
	 * 用户已经注册
	 */
	public static final Integer USER_EXITS = 10100;

	/**
	 * 用户已经注册过微信
	 */
	public static final Integer USER_WX_EXITS = 10101;

	/**
	 * 用户已经注册过qq
	 */
	public static final Integer USER_QQ_EXITS = 10102;
    /**
     * 是否是黑名单
     */
    public static final Integer USER_BLACK = 10012;
    /**
     * 没有权限
     */
    public static final Integer USER_NOT_POWER = 10013;
    /**
     * 用户手机号码没有注册
     */
    public static final Integer USER_PHONE_NOT_REGISTER = 10014;
    
    
    /**
     * 错误码标识 
     * 未知错误
     */
    public static final String UNDEFIND_CODE = "000";
    /**
     * 签约成功
     */
    public static final String WIN_CODE = "999";
    /**
     * 签约计算总价错误
     */
    public static final String CALCULA_CODE = "001";
    /**
     * 签约表生成错误
     */
    public static final String SING_ERROR_CODE = "002";
    
    
}
