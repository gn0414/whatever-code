package cn.simon.controller;


import cn.simon.entity.CollectionParam;
import cn.simon.entity.User;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Date;

@Controller
@RequestMapping("param")
public class ParamController {


    /**
     * 测试零散类型的参数接收
     * 注意参数列表一定要和形参名一样！
     * @return
     * http://localhost:38080/spring_mvc_context/param/test?name=xiaochen&id=1&date=2022/11/20%2012:54:12&price=24.22
     */
    @RequestMapping("test")
    public String test(String name, Integer id, Date date,Double price){
        System.out.println(name);
        System.out.println(id);
        System.out.println(date);
        System.out.println(price);
        return "index";
    }

    /**
     *测试对象类型的参数接受
     * 直接将要接收对象作为控制器方法参数声明
     * 注意：springmvc封装对象时直接根据传递参数key与对象中属性名一致自动封装对象
     * 若既有对象又有name则都赋值
     * @param user
     * @return
     * http://localhost:38080/spring_mvc_context/param/test1?name=xiaochen&id=1&bir=2022/11/20%2012:54:12&age=12
     */
    @RequestMapping("test1")
    public String test1(User user,String name){
        System.out.println(user);
        System.out.println(name);
        return "index";
    }

    /**
     * 测试数组类型的参数接受
     * url: http://localhost:38080/spring_mvc_context/param/test2?qqs=111&qqs=222&qqs=333
     * form:
     *      name="qqs" value="看书"
     *      name="qqs" value="卖报"
     *      name="qqs" value="吃饭"
     * 注意：保证请求多个参数key和数组名相同就行
     * @return
     */
    @RequestMapping("test2")
    public String test2(String[] qqs){
        for (String qq : qqs) {
            System.out.println(qq);
        }
        return "index";
    }
    /**
     * 测试集合list类型的参数接受
     * 如果要接受集合类型的参数就必须放入要对象里面才可以 推荐放入vo对象中接受集合
     * http://localhost:38080/spring_mvc_context/param/test3?lists=simon&lists=simon1&lists=simon2
     */
    @RequestMapping("test3")
    public String test3(CollectionParam collect){
        collect.getLists().forEach(System.out::println);
        return "index";
    }
    /**
     * 测试集合map类型的参数接受
     *
     *
     */
    @RequestMapping("test4")
    public String test4(CollectionParam collect){
        collect.getMaps().forEach((k,v) -> {
            System.out.println(k+"-"+v);
        });
        return "index";
    }
}
