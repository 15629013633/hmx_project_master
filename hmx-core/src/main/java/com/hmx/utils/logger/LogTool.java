package com.hmx.utils.logger;

import java.util.List;
import java.util.Map;



/**
 * 日志工具类
 */
public class LogTool {
	
	@SuppressWarnings("rawtypes")
	public static String getParameter(Object parameter){
		
		String parameterStr = null;
		
		if(parameter instanceof Map){
			StringBuffer sbuffer = new StringBuffer();
			Map parameterMap = (Map)parameter;
			for(Object key:parameterMap.keySet()){
				sbuffer.append("\n").append(key).append(" : ").append(parameterMap.get(key));
			}
			parameterStr = sbuffer.toString();
		}else if(parameter instanceof List){
			StringBuffer sbuffer = new StringBuffer();
			for(Object obj: (List)parameter){
				String str = getParameter(obj);
				sbuffer.append("\n").append(str);
			}
			parameterStr = sbuffer.toString();
		}else if(parameter instanceof Object[]){
			Object[] array = (Object[])parameter;
			StringBuffer sbuffer = new StringBuffer();
			for(Object o:array){
				sbuffer.append("\n").append(o);
			}
			parameterStr = sbuffer.toString();
		}else if(parameter instanceof String){
			parameterStr = parameter.toString();
		}else{
			GetModelParameter getModel = new GetModelParameter(parameter);
			try {
				parameterStr = getModel.getParameter();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		
		return parameterStr;
	}
	
}
