/*
 * Class: ReflectionUtils
 * Description:反射工具类

 */
package com.ttd.util;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;


/**
 * @author 
 * 
 */
public class ReflectionUtils {
    /**
     * 循环向上转型, 获取对象的 DeclaredField
     * 
     * @param object
     * @param fieldName
     * @return
     */
    public static Field getDeclaredField(Class<?> clazz, String fieldName) {

        for (Class<?> superClass = clazz; superClass != Object.class; superClass = superClass
                .getSuperclass()) {
            try {
                return superClass.getDeclaredField(fieldName);
            } catch (NoSuchFieldException e) {
                // Field 不在当前类定义, 继续向上转型
            }
        }
        return null;
    }
/**
 * addby Gaohuanquan 
 * 根据字段名填充对象；
 * @param object
 * @param name
 * @param value
 * @return
 * @throws NoSuchFieldException
 * @throws SecurityException
 * @throws NoSuchMethodException
 * @throws IllegalAccessException
 * @throws IllegalArgumentException
 * @throws InvocationTargetException
 */
    public static Object setField(Object object, String name, Object value)
            throws NoSuchFieldException, SecurityException,
            NoSuchMethodException, IllegalAccessException,
            IllegalArgumentException, InvocationTargetException {
        Class<?> classType = object.getClass();
        // set方法的名字
        Field field = classType.getDeclaredField(name);
        String firstLetter = name.substring(0, 1).toUpperCase(); // 将属性的首字母转换为大写
        String setMethodName = "set" + firstLetter + name.substring(1);
        // 获取方法对象
        Method setMethod = classType.getMethod(setMethodName,
                new Class[] { field.getType() });// 注意set方法需要传入参数类型

        // 调用set方法将这个值复制到新的对象中去
        setMethod.invoke(object, new Object[] { value });
        return object;
    }
    /**
     * add By Gaohuanquan 获取对象某个字段值
     * @param object
     * @param name
     * @return
     * @throws NoSuchFieldException
     * @throws SecurityException
     * @throws NoSuchMethodException
     * @throws IllegalAccessException
     * @throws IllegalArgumentException
     * @throws InvocationTargetException
     */
    public static Object getField(Object object, String name)
            throws NoSuchFieldException, SecurityException,
            NoSuchMethodException, IllegalAccessException,
            IllegalArgumentException, InvocationTargetException {
        Class<?> classType = object.getClass();   
        String firstLetter = name.substring(0, 1).toUpperCase(); // 将属性的首字母转换为大写    
        String getMethodName = "get" + firstLetter + name.substring(1);
        // 获取方法对象
        Method getMethod = classType.getMethod(getMethodName, new Class[]{});
      //调用get方法获取旧的对象的值
        Object value = getMethod.invoke(object, new Object[]{});
        return value;
    }
    
    
 
}
