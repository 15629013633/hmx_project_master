package com.hmx.utils.logger;

import java.lang.reflect.Field;

import java.lang.reflect.InvocationTargetException;

/**
 * @author zrr
 * 获取实体所有的属性
 */
public class GetModelParameter{
	private Object entity;
	
	@SuppressWarnings("unused")
	private GetModelParameter(){}
	
	public GetModelParameter(Object entity){
		this.entity = entity;
	}
	
	public String getParameter() throws IllegalAccessException, IllegalArgumentException, InvocationTargetException{
		
		StringBuffer sb = new StringBuffer();
		
		try{
			Field[] fs = entity.getClass().getDeclaredFields();//获得对象所有属性
			
			for(int i = 0 ; i < fs.length; i++){  
		           Field f = fs[i];  
		           f.setAccessible(true); //设置些属性是可以访问的  
		           Object val = f.get(entity);//得到此属性的值     
		           sb.append("\n").append(f.getName()).append(" : ").append(val);
		    }  
		}catch(Exception e){
			System.err.println(e.getMessage());
		}
		return sb.toString();
	}
	
}
