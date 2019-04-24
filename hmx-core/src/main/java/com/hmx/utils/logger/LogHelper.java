package com.hmx.utils.logger;

import org.apache.log4j.Logger;
import java.io.PrintWriter;
import java.io.StringWriter;


/**
 * @Description 日志记录类
 * @version V0.0.1
 * @date 2016-09-08
 */
public class LogHelper {
    /**
     * 获取最原始被调用的堆栈信息
     * @return
     */
    private static StackTraceElement findCaller() {
        // 获取堆栈信息
        StackTraceElement[] callStack = Thread.currentThread().getStackTrace();
        if(null == callStack){
        	return null;
        }
        // 最原始被调用的堆栈信息
        StackTraceElement caller = null;
        // 日志类名称
        String logClassName = LogHelper.class.getName();
        // 循环遍历到日志类标识
        boolean isEachLogClass = false;
 
        // 遍历堆栈信息，获取出最原始被调用的方法信息
        for (StackTraceElement strackTraceEle : callStack) {
            // 遍历到日志类
            if(logClassName.equals(strackTraceEle.getClassName())) {
                isEachLogClass = true;
            }
            // 下一个非日志类的堆栈，就是最原始被调用的方法
            if(isEachLogClass) {
                if(!logClassName.equals(strackTraceEle.getClassName())) {
                    isEachLogClass = false;
                    caller = strackTraceEle;
                    break;
                }
            }
        }
        return caller;
    }
 
    /**
     * 自动匹配请求类名，生成logger对象，此处 logger name 值为 [className].[methodName]() Line: [fileLine]
     * @return    
     * @author yzChen
     * @date 2016年10月13日 下午11:50:59 
     */
    public static Logger logger() {
        StackTraceElement caller = findCaller();//最原始被调用的堆栈对象
        if(caller == null){
        	return Logger.getLogger(LogHelper.class);
        }else{
        	return Logger.getLogger(caller.getClassName() + "." + caller.getMethodName() + "() Line: " + caller.getLineNumber());
        }
    }
    
}