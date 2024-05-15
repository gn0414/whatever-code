package cn.simon;

import cn.simon.bean.UserBean;
import cn.simon.config.AppConfig;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import java.sql.Connection;

public class TestConfiguration {

    @Test
    public void test1(){
//        ApplicationContext ctx = new AnnotationConfigApplicationContext(AppConfig.class);
        ApplicationContext ctx = new AnnotationConfigApplicationContext("cn.simon");
    }

    @Test
    public void test2(){
        ApplicationContext ctx = new AnnotationConfigApplicationContext(AppConfig.class);
//        UserBean userBean = (UserBean) ctx.getBean("userBean");
//
//
//        Connection conn = (Connection) ctx.getBean("conn");
//        Connection conn1 = (Connection) ctx.getBean("conn1");
//        System.out.println(userBean);
//        System.out.println(conn);
//        System.out.println(conn1);
    }
}
