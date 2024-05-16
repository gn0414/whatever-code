package cn.simon.handlerexception;

import org.springframework.web.servlet.HandlerExceptionResolver;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class GlobalExceptionResolver implements HandlerExceptionResolver {
    /**
     * 用来处理发生异常时方法
     * @param httpServletRequest 请求
     * @param httpServletResponse 响应
     * @param o 方法对象
     * @param e 异常对象
     * @return 出现异常时展示视图和数据
     */

    @Override
    public ModelAndView resolveException(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, Exception e) {
        ModelAndView modelAndView = new ModelAndView();
        //modelandview有很智能的一点就是我们的错误信息如果使用forward就会采用放入request作用域,但是我们如果是重定向就会放在url上
        modelAndView.setViewName("redirect:/error.jsp");
        modelAndView.addObject("msg",e.getMessage());
        return modelAndView;
    }
}
