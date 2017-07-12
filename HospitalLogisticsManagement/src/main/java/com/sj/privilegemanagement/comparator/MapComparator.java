package com.sj.privilegemanagement.comparator;

import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import com.sj.common.utils.ObjectUtils;

public class MapComparator implements Comparator<Map<String,Object>> {

	public void MapComparator(){
		mapParam.clear();
	}
	/**
	 * String 存要排序的字段
	 * Boolean true 该字段从大到小，false 从小到大
	 */
	public static Map<String,Boolean> mapParam = new HashMap();
	@Override
	public int compare(Map<String, Object> o1, Map<String, Object> o2) {
//		Class cla = o1.keySet().
		for(Entry<String, Boolean> entry : mapParam.entrySet()){
			if(ObjectUtils.ObjToDouble(o1.get(entry.getKey())).equals(ObjectUtils.ObjToDouble(o2.get(entry.getKey()))))
					continue;
			if((ObjectUtils.ObjToDouble(o1.get(entry.getKey())) > ObjectUtils.ObjToDouble(o2.get(entry.getKey())) && entry.getValue())
				|| (ObjectUtils.ObjToDouble(o1.get(entry.getKey())) < ObjectUtils.ObjToDouble(o2.get(entry.getKey())) && !entry.getValue())){
				return -1;
			}
			return 1;
		}
		return 0;
	}
}
