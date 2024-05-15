package cn.simon.aspect;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;

@Aspect
/**
 * 1.额外功能
 *          MyArround implements MethodInterceptor
 * 2.切入点
 */
public class MyAspect {



    @Around(value = "myPointCut()")
    public Object arround(ProceedingJoinPoint joinPoint) throws Throwable {
        System.out.println("----aspect log----");
        Object ret = joinPoint.proceed();
        System.out.println("----aspect log----");
        return ret;
    }

    @Pointcut("execution(* *(..))")
    public void myPointCut(){}

}
