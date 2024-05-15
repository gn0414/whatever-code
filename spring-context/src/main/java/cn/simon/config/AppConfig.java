package cn.simon.config;


import cn.simon.bean.User;
import cn.simon.bean.UserBean;
import cn.simon.dao.UserDAO;
import cn.simon.dao.UserDAOImpl;
import cn.simon.factorybean.ConnectionFactoryBean;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.*;
import org.springframework.stereotype.Service;

import java.sql.Connection;
import java.sql.DriverManager;

@Configuration
//@ComponentScan(basePackages = "cn.simon", excludeFilters = {@ComponentScan.Filter(type = FilterType.ANNOTATION, value = Service.class)})
@ComponentScan(basePackages = "cn.simon",useDefaultFilters = false ,includeFilters = {@ComponentScan.Filter(type = FilterType.ASPECTJ, pattern = "cn.simon.bean..*")})
@PropertySource("classpath:init.properties")
public class AppConfig {

//    @Value("${id}")
//    private Integer id;
//
//    @Value("${user}")
//    private String name;
//
//    /**
//     * 简单对象
//     * @return
//     */
//    @Bean
//    public UserBean userBean(){
//        return new UserBean();
//    }
//    /**
//     * 如果创建复杂对象
//     */
//
//    @Bean
//    public Connection conn(){
//        Connection conn = null;
//        try {
//            Class.forName("com.mysql.jdbc.Driver");
//            conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/mydb","root","zxmi520..");
//        }catch (Exception e){
//
//        }
//        return conn;
//    }
//
//    @Bean
//    public Connection conn1(){
//        Connection conn = null;
//        try {
//            ConnectionFactoryBean factoryBean = new ConnectionFactoryBean();
//            factoryBean.setDriverClassName("com.mysql.jdbc.Driver");
//            factoryBean.setUrl("jdbc:mysql://localhost:3306/mydb");
//            factoryBean.setUsername("root");
//            factoryBean.setPassword("zxmi520..");
//            conn = factoryBean.getObject();
//        }catch (Exception e){
//
//        }
//        return conn;
//    }
//
////    @Bean
////    public UserDAO userDAO(){
////        return new UserDAOImpl();
////    }
////
////    @Bean
////    public UserService userService(){
////        UserServiceImpl userService = new UserServiceImpl();
////        userService.setUserDAO(userDAO());
////        return userService;
////    }
//
//    @Bean
//    public User user(){
//        User user = new User();
//        user.setId(id);
//        user.setName(name);
//        return user;
//    }
}
