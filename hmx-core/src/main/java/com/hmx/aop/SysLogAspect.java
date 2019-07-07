package com.hmx.aop;

/**
 * https://blog.csdn.net/xfr140309/article/details/86636553
 * Created by songjinbao on 2019/7/5.
 */

import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.Date;
import java.util.Objects;

import com.hmx.log.entity.SysLog;
import com.hmx.log.servie.ISysLogService;
import com.hmx.user.entity.HmxUser;
import org.apache.commons.logging.Log;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.LocalVariableTableParameterNameDiscoverer;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;


/**
 * 系统日志：切面处理类
 */
@Aspect
@Component
public class SysLogAspect {
    private final static Logger log = org.slf4j.LoggerFactory.getLogger(SysLogAspect.class);

    @Autowired
    private ISysLogService sysLogService;

    //表示匹配带有自定义注解的方法
    @Pointcut("@annotation(com.hmx.aop.Operation)")
    public void pointcut() {}

    @Around("pointcut()")
    public Object around(ProceedingJoinPoint point) {
        Object result =null;
        long beginTime = System.currentTimeMillis();

        try {
            log.info("我在目标方法之前执行！");
            result = point.proceed();
            long endTime = System.currentTimeMillis();
            insertLog(point,endTime-beginTime);
        } catch (Throwable e) {
            // TODO Auto-generated catch block
        }
        return result;
    }

    private void insertLog(ProceedingJoinPoint point ,long time) {
        try {
            MethodSignature signature = (MethodSignature)point.getSignature();
            Method method = signature.getMethod();
            SysLog sys_log = new SysLog();

            Operation userAction = method.getAnnotation(Operation.class);
            if (userAction != null) {
                // 注解上的描述
                System.out.println("desc=" + userAction.value());
                sys_log.setOperation(userAction.value());
            }

            // 请求的类名
            String className = point.getTarget().getClass().getName();
            // 请求的方法名
            String methodName = signature.getName();
            sys_log.setMethod(methodName);
            // 请求的方法参数值
            String args = Arrays.toString(point.getArgs());
            if(!StringUtils.isEmpty(args) && args.length() > 300){
                args = args.substring(0,300);
            }
            sys_log.setParams(args);

            //从session中获取当前登陆人id
            //Long useride = (Long)SecurityUtils.getSubject().getSession().getAttribute("userid");
            HmxUser user = (HmxUser)((ServletRequestAttributes) Objects.requireNonNull(RequestContextHolder.getRequestAttributes())).getRequest().getSession().getAttribute("account");
            if(null == user){
                System.out.println("未从session中获取到用户，desc=" + userAction.value() + ",methodName=" + methodName);
                return;
            }else {
                String userid = user.getUserPhone();//应该从session中获取当前登录人的id，这里简单模拟下

                System.out.println("userid=" + userid);
                sys_log.setUserId(userid);
                sys_log.setUsername(user.getUserName());


                sys_log.setCreateDate(System.currentTimeMillis());

                log.info("当前登陆人：{},类名:{},方法名:{},参数：{},执行时间：{}",userid, className, methodName, args, time);
                sysLogService.insert(sys_log);
            }

        }catch (Exception e){
           e.printStackTrace();
        }
    }
}
