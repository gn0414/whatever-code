package com.simon.annotation;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class Test {
    public static void main(String[] args) throws Exception {
        //这里讲解自定义注解的解析过程
        Class<?> initDemoClass = Class.forName("com.simon.annotation.InitDemo");

        Method[] methods = initDemoClass.getDeclaredMethods();
        for (Method method : methods) {
            boolean isInitMethod = method.isAnnotationPresent(InitMethod.class);
            System.out.println("method name : "+method.getName() + " is signed by InitMethod: "+isInitMethod);
            if (isInitMethod){
                method.invoke(initDemoClass.getConstructor().newInstance());
            }
        }

    }
}
