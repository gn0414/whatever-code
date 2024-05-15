package cn.simon.aspect;

import cn.simon.proxy.User;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

public class UserServiceImpl implements UserService, ApplicationContextAware {

   private ApplicationContext ctx;

   @Override
   public void register(User user) {
      System.out.println("UserServiceImpl.register");

   }

   @Override
   public void login(String name, String password) {
      System.out.println("UserServiceImpl.login");
      /**
       * 那么此时我们应该拿到我们的已经创建好的工厂然后获取代理类调用register
       */
      UserService userService = (UserService) ctx.getBean("userService");
      userService.register(new User());
   }

   @Override
   public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
      ctx = applicationContext;
   }
}
