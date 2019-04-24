package com.hmx.annotations;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import com.hmx.utils.enums.LoginType;


/** 
 * 用户Cookie注解，只能用于方法<br/> 
 * 默认为value = CookieType.USER 
 */  
@Documented
@Target({ElementType.TYPE,ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
public @interface NeedLogin {  
    /** 
     * Session中用户的类型<br/> 
     * 默认 USER 
     *  
     * @return 
     */  
	LoginType value() default LoginType.USER;  
	
}  
