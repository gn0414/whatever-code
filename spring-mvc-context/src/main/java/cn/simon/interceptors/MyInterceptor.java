package cn.simon.interceptors;


import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


public class MyInterceptor implements HandlerInterceptor {

    //1.请求经过拦截器会优先进入拦截器中preHandler方法执行preHandler方法中内容
    //2.如果preHandler返回true代表请求放行  如果返回值为false 中断请求
    //3.如果preHandler返回true就会进入控制器的方法
    //4.当控制器中的方法结束之后，就会返回到postHandler方法
    //5.posthandler执行完成之后会响应请求，在响应请求完成之后会执行afterCompletion
    @Override
    //参数1 参数2 分别是请求和响应对象 参数3：当前请求的控制器对应方法对象
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        System.out.println("==========1==========");
        return true;
    }


    //参数1 参数2 分别是请求和响应对象 参数3：当前请求的控制器对应方法对象 参数4 模型和视图（就是jsp）
    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
        System.out.println("==========3==========");
    }


    //参数1 参数2 分别是请求和响应对象 参数3：当前请求的控制器对应方法对象 参数4 请求过程中出现的异常对象
    //注意：无论正确还是失败都会执行
    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        System.out.println("==========4==========");
    }


}
