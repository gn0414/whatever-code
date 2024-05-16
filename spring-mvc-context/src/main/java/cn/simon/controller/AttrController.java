package cn.simon.controller;


import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

@Controller
@RequestMapping("attr")
public class AttrController {

    /**
     * 使用forward跳转页面数据传递
     * 1.传递零散类型数据
     * 2.传递对象类型数据
     * 3.传递集合类型数据
     * 建议是使用Model,我也不知道为啥（可能是解Servlet的藕？）
     */

    @RequestMapping("test")
    public String test(Model model,HttpServletRequest request, HttpServletResponse response){

        String name = "simon";
//        request.setAttribute("name",name);
        model.addAttribute("name",name);
        return "attr";
    }

    /**
     * 使用redirect跳转页面地址栏数据传递
     * 1.传递零散类型数据
     * 2.传递对象类型数据
     * 3.传递集合类型数据
     */
    @RequestMapping("test1")
    public String test1(Model model,HttpServletRequest request, HttpServletResponse response) throws UnsupportedEncodingException {
        String name = "西蒙";
        return "redirect:/attr.jsp?name="+ URLEncoder.encode(name,"UTF-8");
    }
    /**
     * 使用redirect跳转页面Session数据传递
     * 1.传递零散类型数据
     * 2.传递对象类型数据
     * 3.传递集合类型数据
     */
    @RequestMapping("test2")
    public String test2(Model model,HttpServletRequest request, HttpServletResponse response) throws UnsupportedEncodingException {
        String name = "西蒙";
        request.getSession().setAttribute("user",name);
        return "redirect:/attr.jsp";
    }
}
