package com.sj.common.utils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.beanutils.BeanMap;

import com.sj.privilegemanagement.entity.Proposer;
/**
 * 2017年4月24日 08:35:30 lwj
 * @author Administrator
 * map  object  转化
 */
public class MapOfObject {
	public static Object mapToObject(Map<String, Object> map, Class<?> beanClass) throws Exception {    
        if (map == null)  
            return null;  
        Object obj = beanClass.newInstance();  
        org.apache.commons.beanutils.BeanUtils.populate(obj, map);  
        return obj;  
    }    
      
    public static Map<?, ?> objectToMap(Object obj) {  
        if(obj == null)  
            return null;  
        BeanMap beanMap = new org.apache.commons.beanutils.BeanMap(obj);
        Map map = new HashMap<String, Object>();
        map.putAll(beanMap);
        map.put("enable", true);
        return map;  
    }

	public static <T> List<T> mapToObject(List<Map<String, Object>> maps,
			Class<T> beanClass){
		List<T> objects = new ArrayList<T>();
		for (Map<String, Object> map : maps) {
			try {
				objects.add((T)mapToObject(map, beanClass));
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return objects;
	}    
      
}
