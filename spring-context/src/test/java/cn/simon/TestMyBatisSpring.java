package cn.simon;

import cn.simon.dao.UserDAO;
import cn.simon.entity.User;
import cn.simon.service.UserService;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class TestMyBatisSpring {

    @Test
    public void test1(){
        ApplicationContext ctx = new ClassPathXmlApplicationContext("/applicationContext8.xml");
        UserDAO userDAO = (UserDAO) ctx.getBean("userDAO");

        User user = new User();
        user.setName("xiaojr");
        user.setPassword("123456");

        userDAO.save(user);

    }

    @Test
    public void test2(){
        ApplicationContext ctx = new ClassPathXmlApplicationContext("/applicationContext8.xml");

        UserService userService = (UserService) ctx.getBean("userService");

        User user = new User();
        user.setName("xiaowb");
        user.setPassword("66666");



        userService.register(user);
    }
}
