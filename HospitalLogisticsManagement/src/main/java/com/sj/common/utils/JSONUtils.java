package com.sj.common.utils;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import net.sf.json.util.JSONStringer;
import net.sf.json.util.JSONTokener;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.logging.Log;
 
/**
 * 
 * @Auther  kay             Email:1132892414@qq.com
 * @Date    2015年12月21日   下午8:56:02
 * @File    JSONUtils.java
 * @Description:
 */
public class JSONUtils {
    /***
     * 将List对象序列化为JSON文本
     */
    public static <T> String toJSONString(List<T> list) {
        JSONArray jsonArray = JSONArray.fromObject(list);
 
        return jsonArray.toString();
    }
 
    /***
     * 将对象序列化为JSON文本
     * 
     * @param object
     * @return
     */
    public static String toJSONString(Object object) {
        JSONArray jsonArray = JSONArray.fromObject(object);
 
        return jsonArray.toString();
    }
 
    /***
     * 将JSON对象数组序列化为JSON文本
     * 
     * @param jsonArray
     * @return
     */
    public static String toJSONString(JSONArray jsonArray) {
        return jsonArray.toString();
    }
 
    /***
     * 将JSON对象序列化为JSON文本
     * 
     * @param jsonObject
     * @return
     */
    public static String toJSONString(JSONObject jsonObject) {
        return jsonObject.toString();
    }
 
    /***
     * 将对象转换为List对象
     * 
     * @param object
     * @return
     */
    @SuppressWarnings({ "rawtypes", "unchecked" })
    public static List toArrayList(Object object) {
        List arrayList = new ArrayList();
 
        JSONArray jsonArray = JSONArray.fromObject(object);
 
        Iterator it = jsonArray.iterator();
        while (it.hasNext()) {
            JSONObject jsonObject = (JSONObject) it.next();
 
            Iterator keys = jsonObject.keys();
            while (keys.hasNext()) {
                Object key = keys.next();
                Object value = jsonObject.get(key);
                arrayList.add(value);
            }
        }
 
        return arrayList;
    }
 
    /***
     * 将对象转换为Collection对象
     * 
     * @param object
     * @return
     */
    @SuppressWarnings("rawtypes")
    public static Collection toCollection(Object object) {
        JSONArray jsonArray = JSONArray.fromObject(object);
 
        return JSONArray.toCollection(jsonArray);
    }
 
    /***
     * 将对象转换为JSON对象数组
     * 
     * @param object
     * @return
     */
    public static JSONArray toJSONArray(Object object) {
        return JSONArray.fromObject(object);
    }
 
    /***
     * 将对象转换为JSON对象
     * 
     * @param object
     * @return
     */
    public static JSONObject toJSONObject(Object object) {
        return JSONObject.fromObject(object);
    }
 
    /***
     * 将对象转换为HashMap
     * 
     * @param object
     * @return
     */
    @SuppressWarnings("rawtypes")
    public static HashMap toHashMap(Object object) {
        HashMap<String, Object> data = new HashMap<String, Object>();
        JSONObject jsonObject = JSONUtils.toJSONObject(object);
        Iterator it = jsonObject.keys();
        while (it.hasNext()) {
            String key = String.valueOf(it.next());
            Object value = jsonObject.get(key);
            data.put(key, value);
        }
 
        return data;
    }
 
    /***
     * 将对象转换为List
     * 
     * @param object
     * @return
     */
    // 返回非实体类型(Map)的List
    @SuppressWarnings("rawtypes")
    public static List<Map<String, Object>> toList(Object object) {
        List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
        JSONArray jsonArray = JSONArray.fromObject(object);
        for (Object obj : jsonArray) {
            JSONObject jsonObject = (JSONObject) obj;
            Map<String, Object> map = new HashMap<String, Object>();
            Iterator it = jsonObject.keys();
            while (it.hasNext()) {
                String key = (String) it.next();
                Object value = jsonObject.get(key);
                map.put((String) key, value);
            }
            list.add(map);
        }
        return list;
    }
 
    /***
     * 将JSON对象数组转换为传入类型的List
     * 
     * @param
     * @param jsonArray
     * @param objectClass
     * @return
     */
    @SuppressWarnings({ "unchecked", "deprecation" })
    public static <T> List<T> toList(JSONArray jsonArray, Class<T> objectClass) {
        return JSONArray.toList(jsonArray, objectClass);
    }
 
    /***
     * 将对象转换为传入类型的List
     * 
     * @param
     * @param jsonArray
     * @param objectClass
     * @return
     */
    @SuppressWarnings({ "unchecked", "deprecation" })
    public static <T> List<T> toList(Object object, Class<T> objectClass) {
        JSONArray jsonArray = JSONArray.fromObject(object);
 
        return JSONArray.toList(jsonArray, objectClass);
    }
 
    /***
     * 将JSON对象转换为传入类型的对象
     * 
     * @param
     * @param jsonObject
     * @param beanClass
     * @return
     */
    @SuppressWarnings("unchecked")
    public static <T> T toBean(JSONObject jsonObject, Class<T> beanClass) {
        return (T) JSONObject.toBean(jsonObject, beanClass);
    }
 
    /***
     * 将将对象转换为传入类型的对象
     * 
     * @param
     * @param object
     * @param beanClass
     * @return
     */
    @SuppressWarnings("unchecked")
    public static <T> T toBean(Object object, Class<T> beanClass) {
        JSONObject jsonObject = JSONObject.fromObject(object);
 
        return (T) JSONObject.toBean(jsonObject, beanClass);
    }
 
    /***
     * 将JSON文本反序列化为主从关系的实体
     * 
     * @param 泛型T 代表主实体类型
     * @param 泛型D 代表从实体类型
     * @param jsonString JSON文本
     * @param mainClass 主实体类型
     * @param detailName 从实体类在主实体类中的属性名称
     * @param detailClass 从实体类型
     * @return
     */
    public static <T, D> T toBean(String jsonString, Class<T> mainClass, String detailName,
            Class<D> detailClass) {
        JSONObject jsonObject = JSONObject.fromObject(jsonString);
        JSONArray jsonArray = (JSONArray) jsonObject.get(detailName);
 
        T mainEntity = JSONUtils.toBean(jsonObject, mainClass);
        List<D> detailList = JSONUtils.toList(jsonArray, detailClass);
 
        try {
            BeanUtils.setProperty(mainEntity, detailName, detailList);
        } catch (Exception ex) {
            throw new RuntimeException("主从关系JSON反序列化实体失败！");
        }
 
        return mainEntity;
    }
 
    /***
     * 将JSON文本反序列化为主从关系的实体
     * 
     * @param 泛型T 代表主实体类型
     * @param 泛型D1 代表从实体类型
     * @param 泛型D2 代表从实体类型
     * @param jsonString JSON文本
     * @param mainClass 主实体类型
     * @param detailName1 从实体类在主实体类中的属性
     * @param detailClass1 从实体类型
     * @param detailName2 从实体类在主实体类中的属性
     * @param detailClass2 从实体类型
     * @return
     */
    public static <T, D1, D2> T toBean(String jsonString, Class<T> mainClass, String detailName1,
            Class<D1> detailClass1, String detailName2, Class<D2> detailClass2) {
        JSONObject jsonObject = JSONObject.fromObject(jsonString);
        JSONArray jsonArray1 = (JSONArray) jsonObject.get(detailName1);
        JSONArray jsonArray2 = (JSONArray) jsonObject.get(detailName2);
 
        T mainEntity = JSONUtils.toBean(jsonObject, mainClass);
        List<D1> detailList1 = JSONUtils.toList(jsonArray1, detailClass1);
        List<D2> detailList2 = JSONUtils.toList(jsonArray2, detailClass2);
 
        try {
            BeanUtils.setProperty(mainEntity, detailName1, detailList1);
            BeanUtils.setProperty(mainEntity, detailName2, detailList2);
        } catch (Exception ex) {
            throw new RuntimeException("主从关系JSON反序列化实体失败！");
        }
 
        return mainEntity;
    }
 
    /***
     * 将JSON文本反序列化为主从关系的实体
     * 
     * @param 泛型T 代表主实体类型
     * @param 泛型D1 代表从实体类型
     * @param 泛型D2 代表从实体类型
     * @param jsonString JSON文本
     * @param mainClass 主实体类型
     * @param detailName1 从实体类在主实体类中的属性
     * @param detailClass1 从实体类型
     * @param detailName2 从实体类在主实体类中的属性
     * @param detailClass2 从实体类型
     * @param detailName3 从实体类在主实体类中的属性
     * @param detailClass3 从实体类型
     * @return
     */
    public static <T, D1, D2, D3> T toBean(String jsonString, Class<T> mainClass,
            String detailName1, Class<D1> detailClass1, String detailName2, Class<D2> detailClass2,
            String detailName3, Class<D3> detailClass3) {
        JSONObject jsonObject = JSONObject.fromObject(jsonString);
        JSONArray jsonArray1 = (JSONArray) jsonObject.get(detailName1);
        JSONArray jsonArray2 = (JSONArray) jsonObject.get(detailName2);
        JSONArray jsonArray3 = (JSONArray) jsonObject.get(detailName3);
 
        T mainEntity = JSONUtils.toBean(jsonObject, mainClass);
        List<D1> detailList1 = JSONUtils.toList(jsonArray1, detailClass1);
        List<D2> detailList2 = JSONUtils.toList(jsonArray2, detailClass2);
        List<D3> detailList3 = JSONUtils.toList(jsonArray3, detailClass3);
 
        try {
            BeanUtils.setProperty(mainEntity, detailName1, detailList1);
            BeanUtils.setProperty(mainEntity, detailName2, detailList2);
            BeanUtils.setProperty(mainEntity, detailName3, detailList3);
        } catch (Exception ex) {
            throw new RuntimeException("主从关系JSON反序列化实体失败！");
        }
 
        return mainEntity;
    }
 
    /***
     * 将JSON文本反序列化为主从关系的实体
     * 
     * @param 主实体类型
     * @param jsonString JSON文本
     * @param mainClass 主实体类型
     * @param detailClass 存放了多个从实体在主实体中属性名称和类型
     * @return
     */
    @SuppressWarnings("rawtypes")
    public static <T> T toBean(String jsonString, Class<T> mainClass,
            HashMap<String, Class> detailClass) {
        JSONObject jsonObject = JSONObject.fromObject(jsonString);
        T mainEntity = JSONUtils.toBean(jsonObject, mainClass);
        for (Object key : detailClass.keySet()) {
            try {
                Class value = (Class) detailClass.get(key);
                BeanUtils.setProperty(mainEntity, key.toString(), value);
            } catch (Exception ex) {
                throw new RuntimeException("主从关系JSON反序列化实体失败！");
            }
        }
        return mainEntity;
    }
    
    private final static String regex = "\"([^\\\" ]+?)\":";
    
    /**
     * 一个方法解析多层json数据  json + 正则 + 递归
     * @see {@link java.util.regex.Matcher}, {@link java.util.regex.Pattern}
     * @param jsonStr
     * @return {@link java.util.Map} or {@link java.util.List} or {@link java.lang.String}
     */
    public static Object jsonParse(final String jsonStr) {
        if (jsonStr == null) throw new NullPointerException("JsonString shouldn't be null");
        try {
            if (isJsonObject(jsonStr)) {
                final Pattern pattern = Pattern.compile(regex);
                final Matcher matcher = pattern.matcher(jsonStr);
                final Map<String, Object> map = new HashMap<String, Object>();
                final JSONObject jsonObject = JSONUtils.toJSONObject(jsonStr);
                try {
                    for (; matcher.find(); ) {
                        String groupName = matcher.group(1);
                        Object obj = jsonObject.opt(groupName);
                        //Log.e(groupName, obj+"");
                        if (isJsonObject(obj+"") || isJsonArray(obj+"")) {
                            matcher.region(matcher.end() + (obj+"").replace("\\", "").length(), matcher.regionEnd());
                            map.put(groupName, jsonParse(obj+""));
                        } else {
                            map.put(groupName, obj+"");
                        }
                    }
                } catch (Exception e) {
                    // TODO: handle exception
//                    Log.e("object---error", e.getMessage()+"--"+e.getLocalizedMessage());
                }
                 
                return map;
            } else if (isJsonArray(jsonStr)) {
                List<Object> list = new ArrayList<Object>();
                try {
                    JSONArray jsonArray = JSONArray.fromObject(jsonStr);
                    for (int i = 0; i < jsonArray.size(); i++) {
                        Object object = jsonArray.opt(i);
                        list.add(jsonParse(object+""));
                    }
                } catch (Exception e) {
                    // TODO: handle exception
//                    Log.e("array---error", e.getMessage()+"--"+e.getLocalizedMessage());
                }
                return list;
            } 
        } catch (Exception e) {
            // TODO: handle exception
//            Log.e("RegexUtil--regexJson", e.getMessage()+"");
        }
        return jsonStr;
    }
     
    /**
     * To determine whether a string is JsonObject {@link org.json.JSONObject}
     * @param jsonStr {@link java.lang.String}
     * @return boolean
     */
    private static boolean isJsonObject(final String jsonStr) {
        if (jsonStr == null) return false;
        return Pattern.matches("^\\{.*\\}$", jsonStr.trim());
    }
     
    /**
     * To determine whether a string is JsonArray {@link org.json.JSONArray};
     * @param jsonStr {@link java.lang.String}
     * @return boolean
     */
    private static boolean isJsonArray(final String jsonStr) {
        if (jsonStr == null) return false;
        return Pattern.matches("^\\[.*\\]$", jsonStr.trim());
    }
     
    /**
     * 将对象分装为json字符串 (json + 递归)
     * @param obj 参数应为{@link java.util.Map} 或者 {@link java.util.List}
     * @return
     */
    @SuppressWarnings("unchecked")
    public static Object jsonEnclose(Object obj) {
        try {
            if (obj instanceof Map) {   //如果是Map则转换为JsonObject
                Map<String, Object> map = (Map<String, Object>)obj;
                Iterator<Entry<String, Object>> iterator = map.entrySet().iterator();
                JSONStringer jsonStringer = new JSONStringer();
                while (iterator.hasNext()) {
                    Entry<String, Object> entry = iterator.next();
                    jsonStringer.key(entry.getKey()).value(jsonEnclose(entry.getValue()));
                }
                JSONObject jsonObject = JSONObject.fromObject(jsonStringer);
                return jsonObject;
            } else if (obj instanceof List) {  //如果是List则转换为JsonArray
                List<Object> list = (List<Object>)obj;
                JSONStringer jsonStringer = new JSONStringer();
                for (int i = 0; i < list.size(); i++) {
                    jsonStringer.value(jsonEnclose(list.get(i)));
                }
                JSONArray jsonArray = JSONArray.fromObject(jsonStringer);
                return jsonArray;
            } else {
                return obj;
            }
        } catch (Exception e) {
            // TODO: handle exception
//            LogUtil.e("jsonUtil--Enclose", e.getMessage());
            return e.getMessage();
        }
    }
}