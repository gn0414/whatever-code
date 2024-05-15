package cn.simon.basic;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class BeanFactory {
    //用接口来解耦合
    private static Properties env = new Properties();

    static {
        //获得IO的输入流
        //第二部 文件内容封装 Properties集合中

        try(InputStream inputStream = BeanFactory.class.getResourceAsStream("/applicationContext.properties")) {
            env.load(inputStream);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    /**
     * 对象的创建方式
     * 1.直接调用构造方法来构建对象
     * 2.通过反射来创建对象 解耦合
     * Class clazz = Class.forName("cn.simon.basic.UserServiceImpl")
     *
     * @return
     */
    public static Object getBean(String beanName){
        Object object = null;
        try {
             Class clazz = Class.forName(env.getProperty(beanName));
             object = clazz.newInstance();
        } catch (ClassNotFoundException | InstantiationException | IllegalAccessException e) {
            throw new RuntimeException(e);
        }
        return object;
    }
}
