package cn.simon;

import cn.simon.basic.*;
import cn.simon.beanpost.Category;
import cn.simon.converter.MyDateConverter;
import cn.simon.factorybean.ConnectionFactoryBean;
import cn.simon.scope.Account;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.context.support.ConversionServiceFactoryBean;
import org.springframework.core.convert.ConversionService;
import org.springframework.core.convert.support.ConversionServiceFactory;

import java.sql.Connection;
import java.util.Arrays;

/**
 * @author Simon
 */
public class TestSpring {

    /**
     * 用于测试工厂类进行解耦合的操作
     */
    @Test
    public void test1(){
        //UserServiceImplNew
        //UserService userService = new UserServiceImplNew();
        //这就是耦合


//        UserService userService = new UserServiceImpl();

        UserService userService = (UserService) BeanFactory.getBean("userService");

        userService.login("simon","123");

        User user = new User();

        userService.register(user);
    }

    /**
     * 用于测试Spring的第一个程序
     */
    @Test
    public void test2(){
        ApplicationContext ctx = new ClassPathXmlApplicationContext("/applicationContext.xml");

        Person person = (Person) ctx.getBean("person");

        System.out.println(person);
    }

    @Test
    public void test3(){
        ApplicationContext ctx = new ClassPathXmlApplicationContext("/applicationContext.xml");

        Person person = ctx.getBean("person",Person.class);

        System.out.println(person);
    }

    @Test
    public void test4(){

        ApplicationContext ctx = new ClassPathXmlApplicationContext("/applicationContext.xml");
        //如果要使用这个方法,则该类只能有一个对象
        Person person = ctx.getBean(Person.class);
        System.out.println(person);
    }

    @Test
    public void test5(){

        ApplicationContext ctx = new ClassPathXmlApplicationContext("/applicationContext.xml");
        //如果要使用这个方法,则该类只能有一个对象
        String[] names = ctx.getBeanDefinitionNames();
        Arrays.stream(names).forEach(System.out::println);

        String[] beanNamesForType = ctx.getBeanNamesForType(Person.class);
        Arrays.stream(beanNamesForType).forEach(System.out::println);

        //用于判断是否包含该对象的BeanDefinition
        boolean person = ctx.containsBeanDefinition("person");
        System.out.println(person);
        ////用于判断是否包含该对象
        boolean person1 = ctx.containsBean("person");
    }


    @Test
    public void test6(){
        ApplicationContext ctx = new ClassPathXmlApplicationContext("/applicationContext.xml");
        Person person = (Person)ctx.getBean("person");

//        person.setId("1");
//        person.setName("simon");
        //耦合,我们要使用注入

        System.out.println(person);

    }

    @Test
    public void test7(){
        ApplicationContext ctx = new ClassPathXmlApplicationContext("/applicationContext.xml");
        User user = (User)ctx.getBean("conUser");
        System.out.println(user);
    }

    @Test
    public void test8(){
        ApplicationContext ctx = new ClassPathXmlApplicationContext("/applicationContext.xml");
        Connection conn = (Connection)ctx.getBean("conn");
        Connection conn1 = (Connection)ctx.getBean("conn");
        System.out.println(conn);
        System.out.println(conn1);
    }

    @Test
    public void test9(){
        ApplicationContext ctx = new ClassPathXmlApplicationContext("/applicationContext.xml");
        ConnectionFactoryBean connectionFactoryBean = (ConnectionFactoryBean) ctx.getBean("&conn");
        System.out.println(connectionFactoryBean);
    }

    @Test
    public void test10(){
        ApplicationContext ctx = new ClassPathXmlApplicationContext("/applicationContext.xml");
        Account account = (Account) ctx.getBean("account");
        Account account1 = (Account) ctx.getBean("account");
        System.out.println(account);
        System.out.println(account1);
    }

    @Test
    public void test11(){
        ApplicationContext ctx = new ClassPathXmlApplicationContext("/applicationContext.xml");
    }

    //测试配置文件参数化
    @Test
    public void test12(){
        ApplicationContext ctx = new ClassPathXmlApplicationContext("/applicationContext1.xml");
        Connection conn = (Connection)ctx.getBean("conn");
        System.out.println(conn);
    }

    @Test
    public void test13(){
        ApplicationContext ctx = new ClassPathXmlApplicationContext("/applicationContext2.xml");

        ConversionService converter = (ConversionService) ctx.getBean("conversionService");
        cn.simon.converter.Person person = (cn.simon.converter.Person)ctx.getBean("person");
        System.out.println(person);
    }

    @Test
    public void test14(){
        ApplicationContext ctx = new ClassPathXmlApplicationContext("/applicationContext3.xml");
        Category category = (Category) ctx.getBean("c");
        System.out.println(category.getName());
    }
}
