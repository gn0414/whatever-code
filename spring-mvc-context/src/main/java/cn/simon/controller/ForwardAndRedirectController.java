package cn.simon.controller;


import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("forwardAndRedirect")
public class ForwardAndRedirectController {
    /**
     * 测试forward跳转到页面
     * 默认：controller跳转到jsp页面局势forward跳转
     */
    @RequestMapping("test")
    public String test(){
        System.out.println("test");
        return "index";
    }
    /**
     * 测试redirect跳转到页面
     * 默认：controller跳转到jsp页面局势redirect跳转
     * 使用语法 return "redirect:/index.jsp"
     * redirect后面填写视图的全名
     * 注意：redirect跳转不会经过视图解析器
     */
    @RequestMapping("test1")
    public String test1(){
        System.out.println("test1");
//        return "redirect:https://www.baidu.com";
        return "redirect:/index.jsp";
    }

    /**
     * 测试forward跳转到相同(不同)controller的不同方法
     * forward:跳转指定Controller和方法的RequestMapping路径
     * @return
     */
    @RequestMapping("test2")
    public String test2(){
        System.out.println("test2");
        return "forward:/forwardAndRedirect/test";
    }


    /**
     * 测试redirect跳转到相同(不同)controller的不同方法
     * forward:跳转指定Controller和方法的RequestMapping路径
     * @return
     * 这个%5B是[
     * 这个%5D是]
     * http://localhost:38080/spring_mvc_context/param/test4?maps%5Baaa%5D=ximeng&maps%5Bbbb%5D=simon
     */
    @RequestMapping("test3")
    public String test3(){
        System.out.println("test3");
        return "redirect:/forwardAndRedirect/test";
    }
}
