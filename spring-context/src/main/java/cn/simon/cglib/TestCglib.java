package cn.simon.cglib;

import org.springframework.cglib.proxy.Enhancer;
import org.springframework.cglib.proxy.MethodInterceptor;
import org.springframework.cglib.proxy.MethodProxy;

import java.lang.reflect.Method;

public class TestCglib {

    public static void main(String[] args) {
        //1.创建原始对象
        UserService userService = new UserService();

        //通过cglib创建动态代理对象
        /*
        *
        * */

        Enhancer enhancer = new Enhancer();
        enhancer.setClassLoader(TestCglib.class.getClassLoader());
        enhancer.setSuperclass(UserService.class);
        MethodInterceptor methodInterceptor = new MethodInterceptor() {
            @Override
            public Object intercept(Object o, Method method, Object[] objects, MethodProxy methodProxy) throws Throwable {
                System.out.println("----cglib----");
                Object ret = method.invoke(userService, objects);
                System.out.println("----cglib----");
                return ret;
            }
        };
        enhancer.setCallback(methodInterceptor);

        UserService userServiceProxy = (UserService) enhancer.create();
        userServiceProxy.login("simon","123456");
    }
}
