package cn.simon;

import cn.simon.aop.UserService;
import cn.simon.bean.User;
import cn.simon.config.AppConfig3;
import cn.simon.config.AppConfig5;
import cn.simon.four.Account;
import cn.simon.property.InitProperty;
import cn.simon.scope.Custom;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class TestAnnotation {

    @Test
    public void test1(){
        ApplicationContext ctx = new ClassPathXmlApplicationContext("applicationContext9.xml");

        User user = (User) ctx.getBean("u");

        System.out.println(user);
    }

    @Test
    public void test2(){
        ApplicationContext ctx = new ClassPathXmlApplicationContext("applicationContext9.xml");
        Custom custom = (Custom) ctx.getBean("custom");
        Custom custom1 = (Custom) ctx.getBean("custom");
        System.out.println(custom);
        System.out.println(custom1);
    }

    @Test
    public void test3(){
        ApplicationContext ctx = new ClassPathXmlApplicationContext("applicationContext9.xml");
        ctx.getBean("account");
    }

    @Test
    public void test4(){
        ClassPathXmlApplicationContext ctx = new ClassPathXmlApplicationContext("applicationContext9.xml");
        ctx.close();
    }

    @Test
    public void test5(){
        ClassPathXmlApplicationContext ctx = new ClassPathXmlApplicationContext("applicationContext9.xml");
        InitProperty initProperty = (InitProperty) ctx.getBean("initProperty");

        System.out.println(initProperty.getSex());
        System.out.println(initProperty.getUsername());
        System.out.println(initProperty.getXiaomao());
    }

    @Test
    public void test6(){
        AnnotationConfigApplicationContext atx = new AnnotationConfigApplicationContext("cn.simon.config");

    }

    @Test
    public void test7(){
        AnnotationConfigApplicationContext atx = new AnnotationConfigApplicationContext(AppConfig3.class);
        Account account = (Account) atx.getBean("account");
        System.out.println(account.getName());
        System.out.println(account.getId());
    }

    @Test
    public void test8(){
        AnnotationConfigApplicationContext atx = new AnnotationConfigApplicationContext(AppConfig5.class);
        UserService userService = (UserService) atx.getBean("userServiceImpl");
        userService.login();
    }
}
