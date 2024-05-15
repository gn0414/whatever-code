package cn.simon.aspect;

import cn.simon.proxy.User;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class TestAspect {

    public static void main(String[] args) {
        ApplicationContext applicationContext = new ClassPathXmlApplicationContext("/applicationContext6.xml");

        UserService userService = (UserService) applicationContext.getBean("userService");

        userService.login("simon","123456");
    }
}
