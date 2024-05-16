package cn.simon.controller;


import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class HelloController {

    /**
     * @RequestMapping 修饰范围：用在类上 和 方法上
     * 1.用在方法上用来给当前方法加入指定的请求路径
     * 2.用在类上用来给类中所有的方法加入同一的请求路径,在方法访问之前需要加入类上@RequestMapping的路径
     */
    @RequestMapping(value = "/hello")
    public String hello(){
        //1.收集数据
        //2.调用业务方法
//        System.out.println("hello springmvc");
        System.out.println("==========2==========");
        //3.处理响应
        return "index";
    }

    @RequestMapping("save")
    public String save(){
        System.out.println("save method...");
        return "index";
    }


}
