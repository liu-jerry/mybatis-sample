package org.oyach.mybatis.dataSources.util;

import org.apache.ibatis.io.Resources;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Method;

/**
 * @author liuzhenyuan
 * @version Last modified 15/3/2
 * @since 0.0.1
 */
public abstract class MethodUntil {
    private static Logger logger = LoggerFactory.getLogger(MethodUntil.class);
    /**
     * 根据方法名称，获取方法
     *
     * 该类中方法名必须唯一，也就是不能重载
     *
     * @param clazz
     * @param methodName
     * @return
     */
    public static Method getMethodByName(Class clazz, String methodName){
        if (clazz == null || methodName == null || methodName.trim().equals("")){
            return null;
        }

        Method[] methods = clazz.getDeclaredMethods();
        for (Method method : methods){
            if (method.getName().equals(methodName)){
                return method;
            }
        }
        return null;
    }

    public static Class getClassById(String id) throws ClassNotFoundException{
        int index = id.lastIndexOf(".");
        String className = id.substring(0, index);
        try {
            return Resources.classForName(className);
        } catch (ClassNotFoundException e) {
            throw new ClassNotFoundException("class not found. className=" + className);
        }
    }

    /**
     * 根据id获取方法
     *
     * @param id org.oyahc.dao.StudentMapper.findById
     * @return method
     */
    public static Method getMethodById(String id) throws NoSuchMethodException{
        int index = id.lastIndexOf(".");

        String className = id.substring(0, index);
        String methodName = id.substring(index + 1);

        Class clazz;
        try {
            clazz = Class.forName(className);
        } catch (ClassNotFoundException e) {
            throw new NoSuchMethodException("class not found. className=" + className);
        }

        Method method = getMethodByName(clazz, methodName);
        if (method == null){
            throw new NoSuchMethodException("in class no such method. methodName=" + methodName);
        }
        return method;
    }
}
