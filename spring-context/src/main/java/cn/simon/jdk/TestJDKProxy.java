package cn.simon.jdk;

import cn.simon.proxy.UserService;
import cn.simon.proxy.UserServiceImpl;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

public class TestJDKProxy {

    public static void main(String[] args) {
        UserService userService = new UserServiceImpl();

        /**
         * JDK创建动态代理
         */

        InvocationHandler handler = new InvocationHandler() {
            @Override
            public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
                System.out.println("------proxy log------");
                Object ret = method.invoke(userService,args);
                System.out.println("------proxy log------");
                return ret;
            }
        };

        UserService userServiceProxy = (UserService) Proxy.newProxyInstance(TestJDKProxy.class.getClassLoader(), userService.getClass().getInterfaces(), handler);

        userServiceProxy.login("simon","123456");

    }
}
