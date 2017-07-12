package com.sj.common.utils;

/**
 * object 对象转换类！
 * @author Administrator
 *
 */
public class ObjectUtils {

	public static Integer ObjToInteger(Object obj){
		return Integer.valueOf(obj.toString());
	}
	public static Double ObjToDouble(Object obj){
		return Double.valueOf(obj.toString());
	}
	public static <T> T objToT(Object obj,T t){
		
		return t;
	}
	
}
