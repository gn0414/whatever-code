package cn.simon.dynamic;

import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;

public class Arround implements MethodInterceptor {

    /**
     * invoke方法的作用：额外功能书写在invoke
     *                  额外功能: 原始方法之前
     *                          原始方法之后
     *                          原始方法之前和之后
     * 确定：原始方法如何执行
     * 参数 methodInvocation 额外功能所增加给的那个原始方法
     *                  login
     *                  register
     *                  methodInvocation.proceed(); 这个就是执行
     *
     * 返回值: 代表原始方法执行后的返回值
     * @param methodInvocation
     * @return
     * @throws Throwable
     */
    @Override
    public Object invoke(MethodInvocation methodInvocation) throws Throwable {
        System.out.println("--------before--------");
        Object ret = methodInvocation.proceed();
        System.out.println("--------after--------");
//        return ret;
        return false;
    }
}
