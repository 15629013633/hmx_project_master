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
