package com.hmx.aop;
import java.lang.reflect.Method;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import com.google.gson.Gson;
import com.hmx.annotations.NeedLogin;
import com.hmx.user.entity.HmxUser;
import com.hmx.user.service.HmxUserService;
import com.hmx.utils.enums.LoginType;
import com.hmx.utils.logger.LogHelper;
import com.hmx.utils.result.Config;
import com.hmx.utils.result.LoginUser;
import com.hmx.utils.result.ResultBean;
import com.hmx.utils.secret.AESOperator;
import com.hmx.utils.secret.JwtUtil;

import io.jsonwebtoken.Claims;



/**
 * @author Cookie Aop 切面验证数据
 */
@Component
@Aspect
@Order(1)
public class LoginAOP {
    private static Log logger = LogFactory.getLog(LoginAOP.class);
    
    @SuppressWarnings("rawtypes")
    private static Class returnType = null;
    private LoginType loginType = null;
    
    
    @Autowired
    private JwtUtil jwtUtil;
    @Autowired
    private HmxUserService hmxUserService;
    
    @Autowired
    Gson gson;
    
    // 配置切入点,该方法无方法体,主要为方便同类中其他方法使用此处配置的切入点 ,权限配置切入点
    @Pointcut("@annotation(com.hmx.annotations.NeedLogin)")
    public void aspectNeed() {
    }
    @Around("aspectNeed()")
    public Object aroundManager(ProceedingJoinPoint pj) throws Throwable {
        HttpServletRequest request = SysContent.getRequest();
        HttpServletResponse response = SysContent.getResponse();
        //获取前端传递过来的token值
        String token  = request.getHeader("Authorization");
        //aop注解传入接口访问的默认USER类型
        LoginType type = this.getCookieType(pj);
        
        loginType = type;
        
        if (type == null) {
            LogHelper.logger().debug("方法没有 用户限制 ");
            return pj.proceed();
        }
        try {
        	Claims claims = jwtUtil.parseJWT(token);
        	String subject = AESOperator.getInstance().decrypt( claims.getSubject() );
        	LoginUser loginUser = gson.fromJson(subject, LoginUser.class);
        	//String ip =  HttpUtils.getIp(request) ;
        	if(loginUser.getUserId() == null){
        		return returnError();
        	}else{
        		HmxUser user = hmxUserService.info(loginUser.getUserId());
        		if(user != null){
        			return pj.proceed();
        		}
        	}
        	
         	/*if ( loginUser ==null || loginUser.getUserUserType() ==null || loginUser.getIp()==null || loginUser.getUserId() == null ) {
         		return returnError();
			}else{
        		if (type.equals(LoginType.USER)) {
        			if ("1".equals(loginUser.getUserUserType().toString()) || "2".equals(loginUser.getUserUserType().toString()) || "3".equals(loginUser.getUserUserType().toString())) {
                		return pj.proceed();
    				}else{
    					return returnUserTypeError();
    				}
                } else if( type.equals(LoginType.MANAGER) ) {
                	if ("2".equals(loginUser.getUserUserType().toString()) || "3".equals(loginUser.getUserUserType().toString())) {
                		return pj.proceed();
    				}else{
    					return returnUserTypeError();
    				}
                } else if ( type.equals(LoginType.STEWARD) ) {
                	if ("3".equals(loginUser.getUserUserType().toString())) {
                		return pj.proceed();
    				}else{
    					return returnUserTypeError();
    				}
    			}
        	}*/
            
        } catch (Throwable e) {
            LogHelper.logger().error(e);
            return returnError();
        }
        
        return returnError();
    }
    private LoginType getCookieType(ProceedingJoinPoint pj) {
        // 获取切入的 Method
        MethodSignature joinPointObject = (MethodSignature) pj.getSignature();
        returnType = joinPointObject.getReturnType();
        Method method = joinPointObject.getMethod();
        boolean flag = method.isAnnotationPresent(NeedLogin.class);
        if (flag) {
            NeedLogin annotation = method.getAnnotation(NeedLogin.class);
            return annotation.value();
        }
        return null;
    }
    
    private Object returnError(){
        logger.error(" AOP 用户登录验证错误 ");
        if( returnType == ResultBean.class){
        	return new ResultBean(Config.LOGIN_OUT_TIME, true);
        }
        return null;
    }
    private Object returnUserTypeError(){
        if( returnType == ResultBean.class){
        	return new ResultBean(Config.USERTYPE_ERROR, true);
        }
        return null;
    }
}