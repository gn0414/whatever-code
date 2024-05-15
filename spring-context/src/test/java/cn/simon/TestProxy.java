package cn.simon;

import cn.simon.proxy.User;
import cn.simon.proxy.UserService;
import cn.simon.proxy.UserServiceProxy;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class TestProxy {

    @Test
    public void test1(){
        UserService userService = new UserServiceProxy();
        userService.login("simon","123");
        userService.register(new User());
    }

    @Test
    public void test2(){
        ApplicationContext ctx = new ClassPathXmlApplicationContext("/applicationContext4.xml");
        UserService userService = (UserService) ctx.getBean("userService");
        userService.register(new User());
        userService.login("simon","123");
    }
}
