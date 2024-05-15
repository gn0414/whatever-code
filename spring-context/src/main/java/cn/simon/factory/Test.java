package cn.simon.factory;

import cn.simon.proxy.User;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class Test {

    public static void main(String[] args) {
        ApplicationContext context = new ClassPathXmlApplicationContext("applicationContext5.xml");
        UserService userService = (UserService) context.getBean("userService");

        userService.register(new User());
    }
}
