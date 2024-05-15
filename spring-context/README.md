# 1.Spring
## 1.什么是Spring

``Spring是一个轻量级的JavaEE解决方案，整合众多优秀的设计模式。``
* 轻量级

``对于运行环境是没有额外要求的、代码移植性高``

* JavaEE的解决方案

![1.png](img%2F1.png)

* 整合设计模式

  ```
  1.工厂
  2.代理
  3.模板
  4.策略
  ```

  

## 2.设计模式

1.广义概念

面向对象设计中，解决特定问题的经典代码

2.狭义概念

GOF4人帮定义的23中设计模式：工厂、适配器、装饰器、门面、代理、模板...

## 3.工厂设计模式

### 1.什么是工厂设计模式

````
概念：通过工厂类，创建对象
	User user = new User();
	UserDao userDAO = new UserDAOImpl();
好处：解耦合
耦合：指定代码间的强关联关系，一方的改变会影响到另一方
问题：不利于代码维护
````

### 2.通用简单工厂的设计

```java
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
```

## 4.第一个Spring程序

### 1.软件版本

* 添加pom依赖

````
 		<dependency>
            <groupId>org.springframework</groupId>
            <artifactId>spring-context</artifactId>
            <version>5.1.4.RELEASE</version>
        </dependency>
````

### 2.环境搭建

* Spring的配置文件

````
1.配置文件的放置位置：任意位置 没有硬性要求

2.配置文件的命名：	没有硬件要求 建议：applicationContext.xml

思考：日后应用Spring框架时，需要进行配置文件路径的设置。
````

### 3.Spring的核心API

* ApplicationContext

````
作用：Spring提供的ApplicationContext这个工厂，用于对象的创建
好处：解耦合
````

* ApplicationContext接口类型

````
接口：屏蔽实现的差异
非web环境：CLassPathXmlApplicationContext（main junit）
web环境：XmlWebApplicationContext
````

![2.png](img%2F2.png)

* 重量级资源

````
ApplicationContext工厂的对象占用大量内存
不会频繁的创建对象：一个应用只会创建一个工厂对象
那我们的所有线程都会来访问这个工厂，那么是不是会有线程安全问题
自然我们的ApplicationContext工厂：一定是线程安全的（多线程并发访问）
````

### 4.程序开发

````
1.创建类型
2.配置文件配置 applicationContext.xml
3.通过工厂类,获得对象
ApplicationContext
		|- CLassPathXmlApplicationContext
code: 
ApplicationContext ctx = new ClassPathXmlApplicationContext("/applicationContext.xml");
Person person = (Person) ctx.getBean("person");
System.out.println(person);
````

常见api：

````java
ApplicationContext ctx = new ClassPathXmlApplicationContext("/applicationContext.xml");
Person person = (Person) ctx.getBean("person");
Person person = ctx.getBean("person",Person.class);
//如果要使用这个方法,则该类只能有一个对象
Person person = ctx.getBean(Person.class);
//如果要使用这个方法,则该类只能有一个对象
String[] names = ctx.getBeanDefinitionNames();
Arrays.stream(names).forEach(System.out::println);

String[] beanNamesForType = ctx.getBeanNamesForType(Person.class);
Arrays.stream(beanNamesForType).forEach(System.out::println);
//用于判断是否包含该对象的BeanDefinition
boolean person = ctx.containsBeanDefinition("person");
System.out.println(person);
//用于判断是否包含该对象
boolean person1 = ctx.containsBean("person");
````

配置文件中需要注意的细节

````markdown
1.只配置class属性
<bean class="com.simon.basic.Person"/>
上述配置有没有id值 com.simon.basic.Person#0
应用场景：如果这个bean只需要使用一次，那么就可以省略id值
		如果这个baen会使用多次，或者被其他bean引用则必须要设置id值

2.name属性
<bean class="com.simon.basic.Person"/>
<bean id="" class=""
<bean name="" class=""
id和name的区别
1.name可以定义多个,id只能有一个值
2.XML的id属性的值，命名要求:必须要以字母开头，字母 数字 下划线 连线符(已经可以用其他特殊字符了)
  name属性的值，命名没有要求
  name属性会应用在特殊命名的场景下：/person
  
  XMl发展到了今天：ID属性的限制不存在 
3.代码区别，别名无法应用于containsBeanDefinition
````

### 5.Spring工厂的底层实现原理（简易版）

Spring的工厂可以加载私有构造的对象，但是必须是无参。

![3.png](img%2F3.png)

### 6.思考

````
问题：未来的开发过程中，是不是所有的对象，都会交给Spring工厂来创建？
回答：理论上是的，但是实体对象不会交给Spring创建，它是由持久层框架进行创建。
````

## 5.Spring5.x与日志框架的整合

````
Spring与日志框架进行整合，日志框架就可以在控制台中，输出Spring框架运行过程中的一些重要消息。
好处：便于了解Spring框架的运行过程，利于程序的调试
````

Spring如何整合日志框架

````
Spring1.2.3早期于commons-logging.jar
Spring5.x默认整合的日志框架 logback log4j2

Spring5.x整合log4j
1.引入log4j的jar包
2.引入log4.properties配置文件
### 配置根
log4j.rootLogger = debug,console

### 日志输出到控制台显示
log4j.appender.console=org.apache.log4j.ConsoleAppender
log4j.appender.console.Target=System.out
log4j.appender.console.layout=org.apache.log4j.PatternLayout
log4j.appender.console.layout.ConversionPattern=%d{yyyy-MM-dd HH:mm:ss} %-5p %c{1}:%L - %m%n 
````

## 6.注入

### 1.什么是注入

````
通过Spring工厂及其配置文件，为所创建对象的成员变量赋值
````

### 2.为什么需要注入

````
通过编码的方式，为成员变量赋值，存在耦合
````

````java
	@Test
    public void test6(){
        ApplicationContext ctx = new ClassPathXmlApplicationContext("/applicationContext.xml");
        Person person = (Person)ctx.getBean("person");

//        person.setId("1");
//        person.setName("simon");
        //耦合,我们要使用注入

        System.out.println(person);

    }
````

### 3.如何进行注入

1.类提供setget方法

2.编写配置文件

### 4.注入原理分析

![4.png](img%2F4.png)

### 5.Set注入详解

````
针对于不同类型的成员变量，在<property>标签如何注入
````

![5.png](img%2F5.png)

#### 1.JDK内置类型

````xml
<?xml version="1.0" encoding="UTF-8"?>
<beans xmlns="http://www.springframework.org/schema/beans"
       xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
       xsi:schemaLocation="http://www.springframework.org/schema/beans http://www.springframework.org/schema/beans/spring-beans.xsd">
    <bean id="person" class="cn.simon.basic.Person">
        <property name="id" value="1"/>
        <property name="name" value="simon"/>
        <property name="emails">
            <list>
                <value>simon666</value>
                <value>simon777</value>
                <value>simon888</value>
            </list>
        </property>

        <property name="tels">
            <set>
                <value>116666</value>
                <value>117777</value>
                <value>118888</value>
                <value>119999</value>
            </set>
        </property>

        <property name="address">
            <list>
                <value>116666</value>
                <value>117777</value>
                <value>118888</value>
                <value>119999</value>
            </list>
        </property>

        <property name="qqs">
            <map>
                <entry>
                    <key>
                        <value>1</value>
                    </key>
                    <value>1122</value>
                </entry>
                <entry>
                    <key>
                        <value>2</value>
                    </key>
                    <value>2233</value>
                </entry>
                <entry>
                    <key>
                        <value>3</value>
                    </key>
                    <value>3344</value>
                </entry>
            </map>
        </property>

        <property name="p">
            <props>
                <prop key="1">666</prop>
                <prop key="2">777</prop>
                <prop key="3">888</prop>
            </props>
        </property>
    </bean>
<!--    <bean id="person1" class="cn.simon.basic.Person"/>-->
</beans>
````

#### 2.引用数据类型

````java
		<!--        <property name="user">-->
		<!--            <bean class="cn.simon.basic.User"/>-->
		<!--        </property>-->

        <property name="user" ref="user"/>
````

####  3.命名空间简化

````java
    <bean id="person" class="cn.simon.basic.Person" p:id="6" name="person2" p:user-ref="user"/>
````

看的懂就行

### 6.构造注入

````java
 	<bean id="conUser" class="cn.simon.basic.User">
        <constructor-arg name="id" value="1"/>
        <constructor-arg name="name" value="simon"/>
    </bean>   
````

## 7.反转控制与依赖注入

````markdown
控制：对于对象的控制权
反转控制：把对于对象赋值的控制权，从代码中反转（转移）到Spring工厂和配置文件中完成
好处：解耦合
底层实现：工厂设计模式
````

## 8.依赖注入

````java
注入：通过Spring的工厂及配置文件为对象的成员变量赋值
依赖注入：当一个类需要另一个类时，就意味着依赖，一旦出现依赖，就可以把另一个类作为本类的成员变量，最终通过Spring配置文件进行注入（赋值）
好处：解耦合
````

## 9.简单对象和复杂对象

![6](img%2F6.png)

### 1.FactoryBean

#### 1.FactoryBean接口获取复杂对象

````java
package cn.simon.factorybean;

import org.springframework.beans.factory.FactoryBean;

import java.sql.Connection;
import java.sql.DriverManager;

public class ConnectionFactoryBean implements FactoryBean<Connection> {
    @Override
    public Connection getObject() throws Exception {
        Class.forName("com.mysql.jdbc.Driver");
        return DriverManager.getConnection("jdbc:mysql://localhost:3306/mydb","root","zxmi520..");
    }

    @Override
    public Class<?> getObjectType() {
        return Connection.class;
    }

    @Override
    public boolean isSingleton() {
        return false;
    }
}

````

但是我们配置文件配置就要这样配置

````xml
<bean id="conn" class="cn.simon.factorybean.ConnectionFactoryBean"/>
````

我们可以很明显的看出来，我们使用过这个ConnectionFactoryBean获得Connection复杂对象

细节

1.如果就想获得FactoryBean类型的对象 ctx.getBean("&conn")

2.isSingleton方法

返回true就只会创建一个复杂对象

返回false每一次都会创建新的对象

问题：根据这个对象的特点，决定是返回(SqlSessionFactory)true还是false(Connection)（能否共用）

3.注入优化

````java
package cn.simon.factorybean;

import org.springframework.beans.factory.FactoryBean;

import java.sql.Connection;
import java.sql.DriverManager;

public class ConnectionFactoryBean implements FactoryBean<Connection> {

    private String driverClassName;

    private String username;

    private String password;

    private String url;

    public void setDriverClassName(String driverClassName) {
        this.driverClassName = driverClassName;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getDriverClassName() {
        return driverClassName;
    }

    public String getPassword() {
        return password;
    }

    public String getUrl() {
        return url;
    }

    public String getUsername() {
        return username;
    }

    @Override
    public Connection getObject() throws Exception {
        Class.forName(driverClassName);
        return DriverManager.getConnection(url,username,password);
    }

    @Override
    public Class<?> getObjectType() {
        return Connection.class;
    }

    @Override
    public boolean isSingleton() {
        return false;
    }
}
````

````xml
<bean id="conn" class="cn.simon.factorybean.ConnectionFactoryBean">
        <property name="driverClassName" value="com.mysql.jdbc.Driver"/>
        <property name="url" value="jdbc:mysql://localhost:3306/mydb"/>
        <property name="username" value="root"/>
        <property name="password" value="zxmi520.."/>
</bean>
````

解耦合

#### 2.FactoryBean的实现原理[简易原理]

````markwork
接口回调
1.为什么Spring规定FactoryBean接口实现并且getObject（）
2.ctx.getBean（”conn“）获得是复杂对象 Connection而没有获得ConncetionFactoryBean（&）


Spring内部运行流程
1.通过conn获得ConnectionFactoryBean类的对象，进而通过instanceof判断是FactoryBean接口的实现类
2.Spring按照规定getObject（）获得conn对象
3.返回conn
````

### 2.实例工厂

````
1.避免Spring框架的侵入
implements FactoryBean
2.整合遗留系统
````

配置文件

````xml
<bean id="connFactory" class="cn.simon.factorybean.ConnectionFactory"/>
<bean id= "conn" factory-bean="connFactory" factory-method="getConnection"/>
````

### 3.静态工厂

````xml
<bean id="bean" class="cn.simon.factorybean.StaticConnectionFactory" factory-method="getConnection"/>
````

## 10.Spring工厂控制创建对象的次数

### 1.简单对象的创建次数

````xml
<bean id="account" scope="singleton" class="cn.simon.scope.Account"/>
````

singleton:只会创建一次简单对象（默认值）

prototype:每次调用都会创建一个新的对象

### 2.复杂对象的创建次数

FactoryBean里面的isSingleton方法来控制

若是实例工厂和静态工厂还是通过scope属性进行对象创建次数控制

### 3.为什么要控制对象的创建次数？

````markdown
好处：节省不必要的内存浪费
````

什么样的对象只创建一次就行了？

````markdown
1.SqlSessionFactory
2.DAO
3.Service
````

什么样的对象每一次都要创建新的？

````
1.Connection
2.SqlSession | Session
3.Structs2 Action
````

## 11.对象的生命周期

### 1.什么是对象的生命周期

````
指的是一个对象创建、存活、消亡的一个完整过程
````

### 2.为什么要学习对象的生命周期

````
由Spring负责对象的创建、存活、销毁、了解生命周期，有利于我们使用好Spring为我们创建的对象
````

### 3.生命周期的3个阶段

#### 1.创建阶段

````
Spring工厂合适创建对象
````

scope="singleton"

````
Spring工厂创建的同时，对象也创建

注意：我们不希望工厂创建的同时，将单例对象创建了，懒加载即可 lazy-init="true"
````

scope="prototype"

````
Spring工厂会在获取对象的同时,创建对象
ctx.getBean("")
````

````xml
<!--    <bean id="product" class="cn.simon.life.Product"/>-->
<!--    <bean id="product" scope="prototype" class="cn.simon.life.Product"/>-->
        <bean id="product" class="cn.simon.life.Product" lazy-init="true"/>
````

#### 2.初始化阶段

````
Spring工厂在创建完对象后,调用对象的初始化方法，完成对应的初始化操作

1.初始化方法提供：程序员根据需求，提供初始化方法，最终完成初始化操作
2.初始化方法调用：Spring工厂进行调用
````

* initializingBean接口

````java
public class Product implements InitializingBean {

    public Product(){
        System.out.println("product no-args");
    }


    @Override
    public void afterPropertiesSet() throws Exception {
        System.out.println("product.afterPropertiesSet");
    }
}
````

* 对象中提供一个普通的方法并配置文件

````java
package cn.simon.life;

import org.springframework.beans.factory.InitializingBean;

public class Product implements InitializingBean {

    public Product(){
        System.out.println("product no-args");
    }


    @Override
    public void afterPropertiesSet() throws Exception {
        System.out.println("product.afterPropertiesSet");
    }


    public void myInit(){
        System.out.println("product.myInit");
    }
}

   <bean id="product" class="cn.simon.life.Product" init-method="myInit"/>
````

细节

1.如果一个对象即实现了InitializingBean同时又提供的普通的初始化方法 顺序

````markdown
1.InitializaingBean
2.普通初始化方法
````

2.如果我们的Product里面有属性，有注入的需求，那是先注入呢还是调初始化方法呢？

答案先注入后初始化afterPropertiesSet这个方法名说的很明显了

````java
package cn.simon.life;

import org.springframework.beans.factory.InitializingBean;

public class Product implements InitializingBean {


    private String name;

    public void setName(String name) {
        System.out.println("product.setName");
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public Product(){
        System.out.println("product.no-args");
    }


    @Override
    public void afterPropertiesSet() throws Exception {
        System.out.println("product.afterPropertiesSet");
    }


    public void myInit(){
        System.out.println("product.myInit");
    }
}


product.no-args
product.setName
product.afterPropertiesSet
product.myInit

````

3.什么叫做初始化操作

````
资源的初始化：数据库 IO 网络
实际用的确实很少
````

#### 3.销毁阶段

````
1.Spring销毁对象前,会调用对象的销毁方法，完成销毁操作

Spring什么时候销毁所创建的对象
ctx.close();即容器关闭
销毁方法：程序员根据自己的需求，定义销毁方法，完成销毁操作
	调用：Spring工厂完成调用
````

* 实现DisposableBean接口

* 定义一个普通的销毁方法并定义配置文件

​	和初始化一样我就不写了

* 细节分析

1.销毁方法的操作只适用于singleton

2.什么叫做销毁操作

````
主要指的是 资源的释放操作
用的也很少
````

## 12.配置文件参数化

````
把Spring配置文件中需要经常修改的字符串信息，转移到一个更小的配置文件中

1.Spring的配置文件中存在需要经常修改的字符串？
存在 以数据库连接相关的参数 代表
2.经常变化字符串，在Spring的配置文件中，直接修改
不利于项目维护（修改）
````

### 1.配置文件参数的开发步骤

提供一个小的properties

填写kv

````properties
jdbc.driverClassName=com.mysql.jdbc.Driver
jdbc.url=jdbc:mysql://localhost:3306/mydb
jdbc.username=root
jdbc.password=zxmi520..
````

然后去编写xml,引入context

````xml
<?xml version="1.0" encoding="UTF-8"?>
<beans xmlns="http://www.springframework.org/schema/beans"
       xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance" xmlns:p="http://www.springframework.org/schema/p"
       xmlns:context="http://www.springframework.org/schema/context"
       xsi:schemaLocation="http://www.springframework.org/schema/beans http://www.springframework.org/schema/beans/spring-beans.xsd http://www.springframework.org/schema/context http://www.springframework.org/schema/context/spring-context.xsd">

    <context:property-placeholder location="classpath:/db.properties"/>

    <bean id="conn" class="cn.simon.factorybean.ConnectionFactoryBean">
        <property name="driverClassName" value="${jdbc.driverClassName}"/>
        <property name="url" value="${jdbc.url}"/>
        <property name="username" value="${jdbc.username}"/>
        <property name="password" value="${jdbc.password}"/>
    </bean>

</beans>
````

也就是<context:property-placeholder location="classpath:/db.properties"/>

然后用占位符替换即可

## 13.自定义类型转换器

### 1.什么是类型转换器

````
作用：Spring通过类型转化器把配置文件那种字符串类型的数据,转换成了成员变量对应类型的数据,进而注入
````

### 2.自定义类型转换器

原因：当Spring内部没有提供特定类型的转换器时,而程序员在应用过程中还需要使用,则需要程序员自己自定义转换器

### 3.实现步骤

1.实现converter接口

````java
package cn.simon.converter;

import org.springframework.core.convert.converter.Converter;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class MyDateConverter implements Converter<String, Date> {

    private String format;

    public void setFormat(String format) {
        this.format = format;
    }

    public String getFormat() {
        return format;
    }

    /**
     *
     * @param source 代表我们配置文件中日期字符串
     * @return Spring对转化好的值给对象的Date属性直接赋值
     */
    @Override
    public Date convert(String source) {
        SimpleDateFormat sdf = new SimpleDateFormat(format);
        Date date = null;
        try {
            date = sdf.parse(source);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return date;
    }
}
````

2.Spring配置文件进行注册该对象

3.注册类型转换器

````java
<?xml version="1.0" encoding="UTF-8"?>
<beans xmlns="http://www.springframework.org/schema/beans"
       xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance" xmlns:p="http://www.springframework.org/schema/p"
       xmlns:context="http://www.springframework.org/schema/context"
       xsi:schemaLocation="http://www.springframework.org/schema/beans http://www.springframework.org/schema/beans/spring-beans.xsd http://www.springframework.org/schema/context http://www.springframework.org/schema/context/spring-context.xsd">

    <context:property-placeholder location="classpath:/db.properties"/>

    <bean id="person" class="cn.simon.converter.Person">
        <property name="name" value="simon"/>
        <property name="birthday" value="2024-04-01"/>

    </bean>

    <bean id="myDateConverter" class="cn.simon.converter.MyDateConverter">
        <property name="format" value="yyyy-MM-dd"/>
    </bean>

    <!-- 用于注册类型转换器 -->
    <bean id="conversionService" class="org.springframework.context.support.ConversionServiceFactoryBean">
        <property name="converters">
            <set>
                <ref bean="myDateConverter"/>
            </set>
        </property>
    </bean>

</beans>
````

细节:

1.ConversionServiceFactoryBean 定义bean的id只能为conversionService

2.Spring框架内置日期类型的转换器

日期格式:2020/05/01

## 14.后置处理Bean

````markdown
BeanPostProcessor作用：对Spring工厂所创建的对象,进行再加工。
AOP底层实现

注意：BeanPostProccessor接口
	xxxx(){

	}
````

![7](img%2F7.png)

~~~
程序员实现BeanPostProcessor规定接口的方法

postProcessBeforeInitiallization
作用：Spring创建完对象,并进行注入后，可以运行Before方法进行加工
获得Spring创建好的对象：通过方法的参数
最终通过返回值交给Spring框架

postProcessAfterInitiallization
作用：Spring初始化对象后，可以运行After方法进行加工
获得Spring创建好的对象：通过方法的参数
最终通过返回值交给Spring框架

实战中：我们很少处理Spring的初始化操作：没必要区分Before After 只需要实现其中的一个After方法即可
注意：我们只实现After但是Before操作还是要return这个Bean
~~~

### 1.BeanPostProcessor的开发步骤

1.类实现BeanPostProccessor接口

````java
package cn.simon.beanpost;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanPostProcessor;

public class MyBeanPostProcessor implements BeanPostProcessor {
    @Override
    public Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException {
        return BeanPostProcessor.super.postProcessBeforeInitialization(bean, beanName);
    }

    @Override
    public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
        if (bean instanceof Category){
            Category category = (Category) bean;
            category.setName("xiaowb");
            return category;
        }
        return bean;
    }
}

````

2.Spring的配置文件中进行配置

````xml	
<?xml version="1.0" encoding="UTF-8"?>
<beans xmlns="http://www.springframework.org/schema/beans"
       xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance" xmlns:p="http://www.springframework.org/schema/p"
       xmlns:context="http://www.springframework.org/schema/context"
       xsi:schemaLocation="http://www.springframework.org/schema/beans http://www.springframework.org/schema/beans/spring-beans.xsd http://www.springframework.org/schema/context http://www.springframework.org/schema/context/spring-context.xsd">

    <context:property-placeholder location="classpath:/db.properties"/>

    <bean id="c" class="cn.simon.beanpost.Category">
        <property name="name" value="xiaojr"/>
        <property name="id" value="1"/>
    </bean>

    <bean id="myBeanPostProcessor" class="cn.simon.beanpost.MyBeanPostProcessor"/>

</beans>
````

3.BeanPostProccssor细节

* BeanPostProccssor会对所有创建的对象进行加工。

## 15 AOP编程

### 1.静态代理设计模式

#### 1.为什么需要代理设计模式

##### 1.问题

* 在JavaEE分层开发中,那个层次对我们最为重要的

````
DAO --> Service --> Controller

JavaEE分层开发中，最为重要的是Service层
````

* Service层中包含了哪些代码？

````
Service层中= 核心功能（业务运算、DAO调用） + 额外功能（不属于业务、可有可无、代码量很小） 
````



![8](img%2F8.png)

额外功能：不属于核心业务、可有可无、代码量少

额外功能书写在Service层中好不好？

````
Service层的调用者的角度（Controller）：需要在Service层书写额外功能
								  软件设计者：Service层不需要额外功能
````

#### 2.代理设计模式

##### 1.概念

````
通过代理类,对原始类增加额外功能。
好处：利于原始类的维护。
````

##### 2.名词解释

````
1.目标类 原始类
	指的是 业务类（核心功能 ---》 业务运算 DAO运算）
2.目标方法，原始方法
	目标类（原始类）中的方法 就是目标方法（原始方法）
3.额外功能（附加功能）
	日志，事务，性能
````

##### 3.代理开发的核心要素

````
代理类 = 目标类 + 额外功能 + （实现相同接口）相同方法
````

##### 4.静态代理编码

````java
package cn.simon.proxy;

public class UserServiceProxy implements UserService{

    private UserServiceImpl userService = new UserServiceImpl();
    @Override
    public void register(User user) {
        System.out.println("---log---");
        userService.register(user);
    }

    @Override
    public boolean login(String name, String password) {
        System.out.println("---log---");
        return userService.login(name,password);
    }
}


package cn.simon.proxy;

public class UserServiceImpl implements UserService{


    @Override
    public void register(User user) {
        System.out.println("userimpl.register");
    }

    @Override
    public boolean login(String name, String password) {
        System.out.println("userimpl.login");
        return true;
    }
}


package cn.simon.proxy;

public interface UserService {

    void register(User user);

    boolean login(String name,String password);
}
````

静态代理：为每一个原始类,都有一个代理类

##### 5.静态代理存在的问题

````
1.静态类文件数量过多,不利于项目管理
2.额外功能维护性差
````

### 2.Spring动态代理开发

#### 1.Spring动态代理的概念

````
概念：通过代理类为原始类增加额外功能
好处：利于原始类的维护
````

#### 2.Spring动态代理的开发步骤

1.创建原始类

````java
package cn.simon.proxy;

public class UserServiceImpl implements UserService{


    @Override
    public void register(User user) {
        System.out.println("userimpl.register");
    }

    @Override
    public boolean login(String name, String password) {
        System.out.println("userimpl.login");
        return true;
    }
}

<bean id="userService" class="cn.simon.proxy.UserServiceImpl"/>
````

2.额外功能

​	MethodBeforeAdvie接口

````
额外的功能书写在接口的实现中
````

````java
package cn.simon.dynamic;

import org.springframework.aop.MethodBeforeAdvice;

import java.lang.reflect.Method;

public class Before implements MethodBeforeAdvice {
    /**
     * 需要将运行在原始方法执行之前运行的额外功能，书写在before方法中
     * @param method
     * @param objects
     * @param o
     * @throws Throwable
     */
    @Override
    public void before(Method method, Object[] objects, Object o) throws Throwable {
        System.out.println("----method before advice----");
    }
}
<bean id="before" class="cn.simon.dynamic.Before"/>
````

3.定义切入点

````
我们的额外功能加入的地方就是切入点
目的：由程序员根据自己的需要，决定额外功能加入给哪个原始方法
register
login

简单的测试：所有方法都作为切入点，并且加入额外的功能
````

````xml
   <aop:config>
		<!--        所有的方法都作为切入点，都加入额外功能-->
        <aop:pointcut id="pc" expression="execution(* *(..))"/>
    </aop:config>
````

4.组装（2 3整合）

````xml
<aop:config>
		<!--        所有的方法都作为切入点，都加入额外功能-->
        <aop:pointcut id="pc" expression="execution(* *(..))"/>

		<!--        组装：目的把切入点 与 额外功能进行整合-->
        <aop:advisor advice-ref="before" pointcut-ref="pc"/>
    </aop:config>
````

5.调用

````java
获得Spring工厂创建的动态代理对象,并且进行调用
注意：
	1.Spring工厂通过原始对象的id值获得的是代理对象(没有为什么,王八的屁股，规定)
	2.获得代理对象后,可以通过声明接口类型进行对象的存储
ctx.getBean("userService")
    
 @Test
    public void test2(){
        ApplicationContext ctx = new ClassPathXmlApplicationContext("/applicationContext4.xml");
        UserService userService = (UserService) ctx.getBean("userService");
        userService.register(new User());
        userService.login("simon","123");
    }
````

#### 3.动态代理细节分析

1.Spring创建的代理类在哪呢？

````markdown
Spring框架在运行时,通过动态字节码技术,在JVM创建的,运行在JVM内部，等程序结束后，会和JVM一起消失。

什么叫动态字节码技术？
通过第三方动态字节码框架,在JVM中创建对应类的字节码，进而创建对象,当虚拟机结束,动态字节码跟着消失。
Java运行一个类
	JVM运行这个类的字节码 ---> Object
结论：动态代理不需要定义类文件,都是JVM运行过程中动态创建的,所以不会造成静态代理,类文件数量过多,影响项目管理的问题。
````

![9](img%2F9.png)

2.动态代理编程简化代理的开发

````
在额外功能不改变的前提下，创建其他目标类的代理对象时，只需要指定原始对象即可。
````

3.动态代理额外功能的维护性大大增强

我们的很多原始类都有这个Before，我们如果想替换这个功能,就只需要创建一个新的Before而不需要改之前的代码，然后在配置文件进行修改即可。

#### 4.动态代理详解

##### 1.额外功能的详解

````java
MethodBeforeAdvice接口作用：额外功能运行在原始方法执行之前,进行额外功能操作。
package cn.simon.dynamic;

import org.springframework.aop.MethodBeforeAdvice;

import java.lang.reflect.Method;

public class Before implements MethodBeforeAdvice {
    /**
     * 需要将运行在原始方法执行之前运行的额外功能，书写在before方法中
     * @param method
     * @param objects
     * @param o
     * @throws Throwable
     */
    @Override
    public void before(Method method, Object[] objects, Object o) throws Throwable {
        System.out.println("----method before advice----");
    }
}
before方法的3个参数在实战中,该如何使用。
按需使用即可。
````

我们的上面的参数分别代表着什么呢?

method:额外功能新增加给的那个原始方法（login or register）。

objects:额外功能所增加给的那个原始方法的参数。

o:额外功能所增加给的那个原始对象。

##### 2.MethodInterceptor（方法拦截器）

MethodBeforeAdvice -> 原始方法执行之前

MethodInteceptor ->原始方法之前和之后

````java
MethodInterceptor接口作用：额外功能运行在原始方法执行之前以及之后,进行额外功能操作。
package cn.simon.dynamic;

import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;

public class Arround implements MethodInterceptor {

    /**
     * invoke方法的作用：额外功能书写在invoke
     *                  额外功能: 原始方法之前
     *                          原始方法之后
     *                          原始方法之前和之后
     * 确定：原始方法如何执行
     * 参数 methodInvocation 额外功能所增加给的那个原始方法
     *                  login
     *                  register
     *                  methodInvocation.proceed(); 这个就是执行
     *
     * 返回值: 代表原始方法执行后的返回值
     * @param methodInvocation
     * @return
     * @throws Throwable
     */
    @Override
    public Object invoke(MethodInvocation methodInvocation) throws Throwable {
        System.out.println("--------before--------");
        Object ret = methodInvocation.proceed();
        System.out.println("--------after--------");
        return ret;
    }
}

 	<aop:config>
        <aop:pointcut id="pc1" expression="execution(* *(..))"/>
        <aop:advisor advice-ref="arround" pointcut-ref="pc1"/>
    </aop:config>

````

MethodInterceptor对原始方法的返回值的影响。

```java
如果使用下列代码就会返回原始方法的返回值
		Object ret = methodInvocation.proceed();
        System.out.println("--------after--------");
        return ret;
当然我们可以自定义，直接返回我们的任何值。
        System.out.println("--------before--------");
        Object ret = methodInvocation.proceed();
        System.out.println("--------after--------");
//        return ret;
        return false;
```

##### 3.切入点详解

```` xml
切入点决定了额外功能加入位置（方法）

<aop:pointcut id = "pc" expression="execution(* *(..))"
execution(* *(..))匹配了所有方法,我们接下来就要进行对切入点表达式的学习了
1.executuion() 切入点函数
2.* *(..) 切入点表达式  
````

1.方法切入点表达式

````markword
* *(..) 为什么可以代表所有的方法呢
定义一个方法
public void add(int i,int j)
*(修饰符返回值) *(方法名) (参数)
对修饰符返回值没要求 对方法名没要求 对参数没要求这就是所有方法了
````

* 定义login方法作为切入点

````markdown
* login(..)

* register(..)
````

* 定义login方法且login方法有两个字符串类型的参数作为切入点

````markdown
* login(String,String)
#非java.lang包中的类型,必须要写全限定名
* register(cn.simon.proxy.User)
#只需要第一个参数是String,后面无所谓
login(String,..)
````

* 上面所讲解的方法切入点表达式并不精准

我们想要精准到某一个方法就需要 包 类 方法（参数）

![10](img%2F10.png)

````markdown
修饰符 返回值 		包.类.方法（参数）
	*				cn.simon.proxy.UserServiceImpl.login(..)
````

##### 4.类切入点

````markdown
类切入点,自然这个类的所有方法都会加上对应的额外功能
````

* 语法1

````markdown
# 为类中的所有方法加入了额外功能
* cn.simon.proxy.UserServiceImpl.*(..)
````

* 语法2

````markdown
#不考虑包的存在
# 为类中的所有方法加入了额外功能
* *..UserServiceImpl.*(..)
````

我们的*在我们的包目录就只能代表一层包，所以我们写成两个点代表多级

##### 5.包切入点

```markdown
包切入点,自然这个包下面所有的类的所有方法都会加上对应的额外功能
```

* 语法1

````markdown
# 必须在proxy的包下面而不能是子包
* cn.simon.proxy.*.*(..)
# 这样就是当前包及其子包
* cn.simon.proxy..*.*(..)
````

##### 6.切入点函数

````
切入点函数：用于执行切入点表达式
````

1.execution

````markdown
最为重要的切入点函数,功能最全
执行 方法切入点表达式，类切入点标识，包切入点表达式
弊端：execution执行切入点表达式,书写麻烦

注意：其他切入点函数,只是简化execution书写复杂度，功能上完全一致
````

2.args

````
作用：主要用于函数（方法）参数的匹配
切入点：方法参数必须的是2个字符串类型的参数
execution(* *(String,String))
args(String,String)
````

3.within

```
作用：主要用于类、包切入点表达式的匹配

切入点：UserServiceImpl这个类
execution(* *..UserServiceImpl.*(..))
within(*..UserServiceImpl)
切入点:cn.simon.proxy这个包及其子包
execution(* cn.simon.proxy..*(..))
within(cn.simon.proxy..*)
```

4.@annotation

```java
作用：为具有特殊注解的方法加入额外功能

package cn.simon.proxy;


import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface Log {
}


 <aop:config>
        <aop:pointcut id="log" expression="@annotation(cn.simon.proxy.Log)"/>
        <aop:advisor advice-ref="arround" pointcut-ref="log"/>
</aop:config>

```

5.切入点函数的逻辑运算

````
指的是 整合多个切入点函数一起配合工作,进而完成更加复杂的需求
````

and与操作

````markdown
案例: login 同时 参数2个字符串
execution(* login(String,String))
execution(* login(..)) and args(String,String)
注意：与操作不能用于同种类型的切入点函数(不可能有这种逻辑)
````

or或操作

````
案例 login 或者 register
register方法和login方法作为切入点
execution(* login(..)) or execution(* register(..))
````

### 3.AOP编程

#### 1.AOP概念

````
AOP(Aspect Oriented Programing) 面向切面编程 = Spring动态代理开发
以切面为基本单位的程序开发,通过切面间的彼此协同,相互调用,完成程序的构建。
切面 = 切入点 + 额外功能

OOP(Object Oriented Programing) 面向对象编程 Java
以对象为基本单位的程序开发,通过对象间的彼此协同,相互调用,完成程序的构建。

POP(Producer Oriented Programing) 面向过程（方法、函数）编程 C
以过程为基本单位的程序开发,通过过程间的彼此协同，相互调用，完成程序的构建。
````

````
AOP的概念：
	本质就是Spring的动态代理开发,通过代理类为原始类增加额外功能
	好处：利于原始类的维护
注意：有了AOP编程不可能取代OOP，OOP编程有意补充。
````

#### 2.AOP编程的开发步骤

````
1.原始对象
2.额外功能（MethodInterceptor）
3.切入点
4.组装切面（额外功能+切入点）
````

#### 3.切面的名词解释

````
切面 = 切入点 + 额外功能

几何学
	面 = 点 + 相同的性质
````

#### 4.AOP的底层实现原理

##### 1.核心问题

````
1.AOP如何创建动态代理类（动态字节码技术）
2.Spring工厂如何加工创建代理对象
	通过原始对象的id值,获得的是代理对象
````

##### 2.动态代理类的创建

1.JDK的动态代理

InvocationHandler

​	作用：用于书写额外功能,额外功能运行原始方法执行前后,前后抛出异常

​	参数：Proxy忽略掉 代表的是代理对象

​				Method原始方法

​				args原始方法的参数

​	Object：原始方法的返回值

​	Object invoke(Object proxy,Method method,Object[] args){

​	}

和MethodInterceptor一样

类加载器的作用 CLassLoader

1.通过类加载器把对应类的字节码文件加载到JVM

2.通过类加载器创建类的CLass对象，进而创建这个类的对象

如何获得类加载器：每一个类的.class文件自动分配与之对应的ClassLoader

动态代理类没有.class文件,虚拟机不会为其分配ClassLoader,可是我又需要,那就借用一个ClassLoader

所以我们的Proxy.newProxyInstance()的第一个参数就是我们需要借用的类加载器完成类CLass对象的创建。

编码

````java
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

````

2.Cglib的动态代理

如果原始类没有实现任何接口,我想为这个原始对象创建代理类

那我们如何让我们的代理类和原始类的方法如何相同呢,没错就是继承。

代理类

````java
public class UserServiceProxy extends UserServiceImpl{
	login(){
		额外功能
		super.login()
	}
}
````

````
CGlib创建动态代理的原理：父子继承关系创建代理对象,原始类作为父类,代理类作为子类,这样既可以保证2者方法一致，同时还可以在代理类提供新的实现（额外功能+原始方法）
````

````java
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
````

##### 3.Spring工厂如何加工原始对象

其实相当简单,动态代理+BeanPostProccessor来完成对原始对象的加工

UserService接口

````java
package cn.simon.factory;

import cn.simon.proxy.User;

public interface UserService {

   void login(String name,String password);

   void register(User user);
}



//实现类
package cn.simon.factory;

import cn.simon.proxy.User;

public class UserServiceImpl implements UserService {

   @Override
   public void register(User user) {
      System.out.println("UserServiceImpl.register");
   }

   @Override
   public void login(String name, String password) {
      System.out.println("UserServiceImpl.login");
   }
}

//BeanPostProccessor
package cn.simon.factory;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanPostProcessor;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

public class ProxyBeanPostProcessor implements BeanPostProcessor {
    @Override
    public Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException {
        return BeanPostProcessor.super.postProcessBeforeInitialization(bean, beanName);
    }

    @Override
    public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
        InvocationHandler invocationHandler = new InvocationHandler() {
            @Override
            public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
                System.out.println("-----new log-----");
                return method.invoke(bean, args);
            }
        };
        return  Proxy.newProxyInstance(ProxyBeanPostProcessor.class.getClassLoader(), bean.getClass().getInterfaces(), invocationHandler);
    }
}


//测试Spring动态代理
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

````

#### 5.基于注解的AOP编程的开发步骤

1.原始对象

2.额外功能

3.切入点

4.组装切面

我们都知道,面对对象编程是否是可以将切面也作为一个对象呢？没错

````java
package cn.simon.aspect;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;

@Aspect
/**
 * 1.额外功能
 *          MyArround implements MethodInterceptor
 * 2.切入点
 */
public class MyAspect {

    @Around("execution(* login(..))")
    public Object arround(ProceedingJoinPoint joinPoint) throws Throwable {
        System.out.println("----aspect log----");
        Object ret = joinPoint.proceed();
        System.out.println("----aspect log----");
        return ret;
    }

}

很简单,我们之前是额外功能靠实现MethodInterceptor来进行实现的
现在我们只需要通过使用@Arround注解方法,并且我们的参数为ProceedingJoinPoint即可
然后在@Arround的内容里面写入我们的切入点表达式即可,我们的切面算是定义完了，然后作为对象即可
在xml里面定义我们的切面对象并且开启自动切面
<bean id="userService" class="cn.simon.aspect.UserServiceImpl"/>

<bean id="arround" class="cn.simon.aspect.MyAspect"/>

    <!--告知Spring基于注解进行AOP开发-->
<aop:aspectj-autoproxy/>
````

需要注意的一些细节

切入点可以复用

````java
 @Around(value = "myPointCut()")
    public Object arround(ProceedingJoinPoint joinPoint) throws Throwable {
        System.out.println("----aspect log----");
        Object ret = joinPoint.proceed();
        System.out.println("----aspect log----");
        return ret;
    }

    @Pointcut("execution(* login(..))")
    public void myPointCut(){}
````

#### 6.动态代理的创建方式

````markword
AOP底层实现 2种代理创建方式
1.JDK 通过实现接口 做新的实现类的方式	 创建代理对象
2.Cglib 通过继承父类	做新的子类	创建代理对象
那我们Spring的动态代理是使用哪一种方式呢？

默认情况 AOP编程	底层应用JDK动态代理创建方式
如果我想切换为Cglib呢？
		1.基于注解AOP开发
		<aop:aspectj-autoproxy proxy-target-class="true"/>
		2.传统的AOP开发
		<aop:config proxy-target-class="true">
        	<aop:pointcut id="pc1" expression="execution(* login(..)) and args(String,String)"/>
        	<aop:advisor advice-ref="arround" pointcut-ref="pc1"/>
    	</aop:config>
````

#### 7.AOP开发中的一个坑

````
坑：在同一个业务类中,进行业务方法间的相互调用,只有最外层的方法,才是加入了额外功能
````



````java
public class UserServiceImpl implements UserService {

   @Override
   public void register(User user) {
      System.out.println("UserServiceImpl.register");
   }

   @Override
   public void login(String name, String password) {
      System.out.println("UserServiceImpl.login");
      this.register(new User());
   }
}
````

这个问题相当好理解,因为我们这里使用的this的注册方法调用的是原始对象的注册方法,所以是没有AOP的额外功能的,

可是此时我的真正的设计目的：代理对象的register方法 --->额外功能+原始功能

````java
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

````

这里就是一个非常好的 ApplicationContextAware的实现,我们需要工厂就实现这个接口。

## 16.持久层整合

#### 1.Spring框架为什么要与持久层技术进行整合

````
1.JavaEE开发需要持久层进行数据库的访问控制
2.JDBC Hibernate MyBatis进行持久开发过程存在大量的代码冗余
3.Spring基于模板设计模式对于上述的持久层技术进行了封装
````

#### 2.Spring可以与哪些持久层技术进行整合

````
1.JDBC
	|- JDBCTemplate
2.Hibernate(JPA)
	|- HibernateTemplate
3.MyBatis
	|-SqlSessionFactoryBean MapperScannerConfigure
````

## 17.Spring与MyBatis整合

### 1.Mybatis开发步骤回顾

````markdown
1.实体
2.实体别名		配置繁琐
3.表
4.创建DAO接口
5.实现Mapper文件
6.注册Mapper文件	配置繁琐
7.MybatisAPI调用	 代码冗余
````

### 2.Mybatis开发存在的问题

````
配置繁琐 代码冗余 2 6 7
````

###  3.Spring与Mybatis整合

![11](img%2F11.png)

![12](img%2F12.png)

### 4.Spring和Mybatis整合的开发编码

* 配置文件(ApplicationContext.xml) 进行相关配置

````

````

* 编码

````markdown
# 实战根据需求 写的代码
1.实体
2.表
3.创建DAO接口
4.实现Mapper文件
````

### 5.编码实战

1.导入依赖

```xml
 		<dependency>
            <groupId>org.springframework</groupId>
            <artifactId>spring-jdbc</artifactId>
            <version>5.1.14.RELEASE</version>
        </dependency>

        <dependency>
            <groupId>org.mybatis</groupId>
            <artifactId>mybatis-spring</artifactId>
            <version>2.0.2</version>
        </dependency>

        <dependency>
            <groupId>com.alibaba</groupId>
            <artifactId>druid</artifactId>
            <version>1.1.18</version>
        </dependency>
```

2.配置文件(ApplicationContext.xml)

```xml
<?xml version="1.0" encoding="UTF-8"?>
<beans xmlns="http://www.springframework.org/schema/beans"
       xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance" xmlns:tx="http://www.springframework.org/schema/tx"
       xsi:schemaLocation="http://www.springframework.org/schema/beans http://www.springframework.org/schema/beans/spring-beans.xsd http://www.springframework.org/schema/context http://www.springframework.org/schema/context/spring-context.xsd http://www.springframework.org/schema/aop https://www.springframework.org/schema/aop/spring-aop.xsd http://www.springframework.org/schema/tx https://www.springframework.org/schema/tx/spring-tx.xsd">
    

    <bean id="dataSource" class="com.alibaba.druid.pool.DruidDataSource">
        <property name="driverClassName" value="com.mysql.jdbc.Driver"/>
        <property name="url" value="jdbc:mysql://localhost:3306/mydb"/>
        <property name="username" value="root"/>
        <property name="password" value="zxmi520.."/>
    </bean>
    
    <bean id="sqlSessionFactoryBean" class="org.mybatis.spring.SqlSessionFactoryBean">
        <property name="dataSource" ref="dataSource"/>
        <property name="typeAliasesPackage" value="cn.simon.entity"/>
        <property name="mapperLocations">
            <list>
                <value>classpath:/mapper/*Mapper.xml</value>
            </list>
        </property>
    </bean>

    <bean id="scanner" class="org.mybatis.spring.mapper.MapperScannerConfigurer">
        <property name="sqlSessionFactoryBeanName" value="sqlSessionFactoryBean"/>
        <property name="basePackage" value="cn.simon.dao"/>
    </bean>
    
</beans>
```

3.编码

### 6.Spring与Mybatis整合细节

* 问题：Spring与Mybatis整合后,为什么DAO不提供事务,但是数据能够插入数据库中？

````markdown
Connection --> tx
Mybatis(Connection)

本质上控制连接对象（Connection） ---> 连接池（DataSource）
1.Mybatis提供的连接池对象 ---》创建Connection
Connection.setAutoCommit(false)手工控制了事务,操作完成后，手动提交
2.Druit作为连接池  ---》创建Connection
Connection.setAutoCommit(true)true默认值 保持自动控制事务，一条sql 自动提交

注意：未来实战中，还会手工控制事务（多条sql一起成功，一起失败），后续Spring通过事务控制解决了这个问题。
````

## 18.Spring的事务处理

### 1.什么是事务？

````
保证业务操作完整性的一种数据库机制
事务的4特点：A C I D
1.A 原子性
2.C 一致性
3.I 隔离性
4.D 持久性
````

### 2.如何控制事务

````
JDBC:
	Connection.setAutoCommit(false);
	Connection.commit();
	Connection.rollback();
MyBatis
	MyBatis自动开启事务
	sqlSession(Connection).commit();
	sqlSession(Connection).rollback();
结论：控制事务的底层,都是Connection对象完成的。
````

### 3.Spring控制事务的开发

````
Spring是通过AOP的方式进行事务开发
````

1.原始对象

````
public class xxxServiceImpl{
	private xxxDAO xxxDAO
	set get
}
1.原始对象 ---> 原始方法 --->核心功能（业务处理+DAO调用）
2.DAO作为Service的成员变量，依赖注入的方式进行赋值
````

2.额外功能

````
org.springframework.jdbc.datasource.DataSourceTransactionManager
注入Connection --》 注入连接池
1.MethodInterceptor
public Object invoke(MethodInvocation invocation)
	try{
		Connection.setAutoCommit(false);
		Object ret = invocation.proceed();
		Connection.commit();
	}catch(Exception e){
		Connection.rollback();
	}
	return ret;
````

3.切入点

````
@Transactional
事务的额外功能加入给哪些业务方法

1.类上：类中的所有方法都会加入事务
2.方法上：这个方法会加入事务
````

4.组装切面

````
1.切入点
2.额外功能

<tx:annotation-driven transaction-manager=""/>
````

### 4.Spring控制事务的编码

引入pom依赖

````xml
		<dependency>
            <groupId>org.springframework</groupId>
            <artifactId>spring-tx</artifactId>
            <version>5.1.14.RELEASE</version>
        </dependency>
````

编写xml文件

````xml
	<bean id="userService" class="cn.simon.service.UserServiceImpl">
        <property name="userDAO" ref="userDAO"/>
    </bean>
	# 给userService注入userDAo

    <bean id="dataSourceTransactionManager" class="org.springframework.jdbc.datasource.DataSourceTransactionManager">
        <property name="dataSource" ref="dataSource"/>
    </bean>
	# 注入数据源事务处理器（获取连接池对象管理连接 通过AOP 对方法开启事务）

	# 将Transaction注解的切入点和额外功能(数据源事务处理器)绑定起来
    <tx:annotation-driven transaction-manager="dataSourceTransactionManager"/>
````

### 5.Spring中的事务属性（Transcation Attribute）

#### 1.什么是事务属性

````
属性：描述物体特征的一系列值

事务属性：描述事务特征的一系列值
1.隔离属性
2.传播属性
3.只读属性
4.超时属性
5.异常属性
````

#### 2.如何添加事务属性

````
Transactional(isloation=,propagation=,readOnly=,timeout=,rollbackFor=,noRollbackFor=)
````

#### 3.隔离属性

* 隔离属性的概念

````
概念：他描述了事务解决并发问题的特征
1.什么是并发
	多个事务（属性）对同一数据进行了访问操作。
	同一时间：0.0000几秒,微小的前后
2.并发会产生哪些问题
	1.脏读
	2.不可重复度
	3.幻影读
3.并发问题如何解决
	通过隔离属性解决，隔离属性中设置不同的值，解决并发处理过程中的问题。
````

* 事务并发产生的问题

  * 脏读

    ```
    一个事务，读取了另一个事务中没有提交的数据。会在本事务中产生数据不一致的问题。
    解决方案 @Transactional(isolation=Isolation.READ_COMMITTED)
    去读已提交的数据
    ```
    
  * 不可重复读
  
    ````
    一个事务,多次读取相同的数据,但是读取结果不一样。会在本事务中产生数据不一致的问题
    注意： 1 不是脏读 2 一个事务中
    解决方案 @Transactional(isolation=Isolation.REPEATABLE_READ)
    数据库会在底层加一把行数
    ````
  
  * 幻影读
  
    ````
    一个查询中，多次对整表进行查询统计,但是结果不一样，会在本事务中产生数据不一致的问题
    解决方案 @Transactional(isolation=Isolation.SERIALIZABLE)
    数据库会在底层加一把表数
    ````

并发安全

````
SERIALIZABLE > REPEATABLE_READ READ_COMMITTED > READ_UNCOMMITTED
````

运行效率自然相反

#### 4.数据库对于隔离属性的支持

![13](img%2F13.png)

````
Oracle如何解决不可重复读呢
采用的是多版本比对的方式,解决不可重复读的问题
````

#### 5.默认的隔离属性

````
ISOLATION_DEFAULT:会调用不同数据库所设置的默认隔离属性

MySQL:REPEATABLE_READ
Oracle:READ_COMMITTED
````

#### 6.隔离属性在实战中的建议

````
推荐使用Spring指定的ISOLATION_DEFAULT
未来中的实战中,并发访问情况 很低

如果真遇到并发问题,乐观锁
	Hibernate(JPA) Version
	MyBatis 通过拦截器自定义开发
````

#### 7.传播属性

传播属性的概念

````
概念：他描述了事务解决嵌套问题的特征
什么叫做事务的嵌套：他指的是一个大的事务中，包含了多个小的事务。
TXA -------------
	TXB -------------
	TXB -------------
	
	TXC -------------
	TXC -------------
TXA -------------
````

````
service调用service情况下,有可能出现事务嵌套
````

问题：当我们的TXC事务失败,就会回滚TXA，TXC但是TXB已经提交

传播属性的值及其用法

![14、](img%2F14.png)

REQUIRED 适合于增删改

SUPPORTS适合于查

* 默认的传播属性是

````
REQUIRED是传播属性的默认值
````

* 推荐传播属性的使用方式

````
增删改 方法：直接使用默认值REQUIRED
查询	操作：显示指定传播属性的值为SUPPORTS
````

REQUIRES_NEW适合日志记录方法中，即我们要完成自己的日志记录而不在意外部的逻辑是否执行正确

#### 8.只读属性（readOnly）

````
针对于只进行查询操作的业务方法,可以加入只读属性,提供运行效率
默认值：false
````

#### 9.超时属性

````
指定了事务等待的最长时间
1.为什么事务进行等待
	当前事务访问数据时,有可能访问的数据被别的事务进行加锁的处理,那么此时本事务就必须进行等待
	等待时间 秒
	如何应用呢
	timeout属性即timeout=2,也就是最多睡2s
超时属性的默认值： -1 最终由对于的数据库来指定
````

#### 10.异常属性

````
Spring事务处理过程中
默认 对于RuntimeException及其子类 采用的是回滚的策略
默认 对于Exception及其子类 采用的是提交的策略

rollbackFor = Exception
noRollbackFor = RuntimeException
这样对于Exception及其子类就是回滚
对于RuntimeException就是提交

建议：在实战中使用RuntimeException及其子类并且事务异常属性的默认值
````

#### 11.事务属性常见配置总结

![15](img%2F15.png)

#### 12.基于标签的事务配置方式（事务开发的第二种形式）

![16](img%2F16.png)

实战我们会消除上方冗余，使用通配方法名

![17](img%2F17.png)

## 19.MVC框架整合思想

### 1.搭建mvc运行环境

```xml
<project xmlns="http://maven.apache.org/POM/4.0.0" xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
         xsi:schemaLocation="http://maven.apache.org/POM/4.0.0 http://maven.apache.org/maven-v4_0_0.xsd">
    <modelVersion>4.0.0</modelVersion>
    <parent>
        <groupId>cn.simon</groupId>
        <artifactId>spring-review</artifactId>
        <version>1.0-SNAPSHOT</version>
    </parent>

    <artifactId>spring-mvc</artifactId>
    <packaging>war</packaging>
    <name>spring-mvc Maven Webapp</name>
    <url>http://maven.apache.org</url>

    <dependencies>
        <dependency>
            <groupId>junit</groupId>
            <artifactId>junit</artifactId>
            <version>3.8.1</version>
            <scope>test</scope>
        </dependency>

        <dependency>
            <groupId>javax.servlet</groupId>
            <artifactId>javax.servlet-api</artifactId>
            <version>3.1.0</version>
            <scope>provided</scope>
        </dependency>

        <dependency>
            <groupId>javax.servlet</groupId>
            <artifactId>jstl</artifactId>
            <version>1.2</version>
        </dependency>

        <dependency>
            <groupId>javax.servlet.jsp</groupId>
            <artifactId>javax.servlet.jsp-api</artifactId>
            <version>2.3.1</version>
            <scope>provided</scope>
        </dependency>
    </dependencies>
    <build>

    </build>
</project>
```

### 2.为什么整合MVC框架

```markdown
1.MVC框架提供了控制器Controller调用Service
DAO ---> Service ---> Controller
2.请求响应的处理
3.接受请求参数
4.控制程序的运行流程
5.视图解析（JSP JSON Freemarker Thyemeleaf）
```

### 3.Spring可以整合哪些MVC框架

```
1.struts1
2.webwork
3.jsf
4.struts2
5.sprngMVC
```

### 4.Spring整合MVC框架的核心思路

#### 1.准备工厂

````
1.Web开发中如何创建工厂
	ApplicationContext ctx = new WebXmlApplicationContext()
2.如何保证工厂唯一同时被共用
	web request|session|servletContext（application）
	工厂存储在ServletContext这个作用域中,ServletContext.setAttribute("xxxx",ctx);
	唯一：ServletContext对象（全局唯一） --》我们在ServletContext创建的同时让工厂也同时创建
	ServletContxtListener ---》 创建工厂，同样也会只保证调用了一次，保证了唯一性。
3.总结：
	ServletContextListener去执行
	ApplicationContext ctx = new WebXmlApplicationContext()
	存储在ServletContext中给大家共用。
4.Spring帮我们封装了一个ContextLoaderListener
	1.创建工厂
	2.把工厂存在ServletContext中
````

````xml
ContextLoaderListener使用方式
web.xml
<listener>
	<listener-class>org.springframework.web.context.ContextLoaderListener<listener-class>
</listener>

<context-param>
    <param-name>contextConfigLocation</param-name>
    <param-value>classpath:applicationContext.xml</param-value>
</context-param>
````



## 20.注解编程

### 1.什么是注解编程

````
指的是在类或者方法上加入特定的注解,完成特点功能的开发
````

### 2.为什么要讲注解编程

````
1.注解开发方便
	代码简介 开发速度大大提高
2.Spring开发潮流
````

### 3.注解的作用

* 替换xml这种配置形式,简化配置
* 替换接口，实现调用双方的契约性

![18](img%2F18.png)

````
通过注解的方式，在功能调用者和功能提供者之间的约定，因为注解应用更加灵活，所以现在的开发中，更推荐通过注解的形式完成。
````

### 4.Spring注解的发展历程

````
1.Spring2.x开始支持注解编程
目的：提供的这些注解只是为了在某些情况下简化XML配置，作为XML开发的有益补充
2.Spring3.x @Configuration @Bean
目的：彻底替换XML，基于纯注解开发
3.Spring4.x SpringBoot
	提倡使用注解常见开发
````

### 5.Spring注解开发的一个问题

````
Spring基于注解进行配置后，还能否解耦合呢？
在Spring框架应用注解时，如果对注解配置的内容不满意，可以通过SPring配置文件进行覆盖
````

## 21.Spring的基础注解(Spring2.x)

### 1.@Component

* 搭建开发环境

````
<context:component-scan base-package="cn.simon"/>

作用：让Spring框架在设置包及其子包中扫描对应的注解,使其生效
````

* 对象创建相关注解
  * @Component

````
替换<bean>标签，设置了@Component注解后,id就为类名第一位小写
````

````
@Component("u")
我们可以直接在Component里面定义id
````

Component的衍生注解

````
@Repository
@Service
@Controller

注意：本质上这些衍生注解就是Component 
	 作用细节用法都是完全一致
那为什么要提供这些衍生注解呢？
目的：更加准确的表达一个类型的作用

@Repository --->XXXDAO

@Service --->Service

@Controller --->Controller

注意：Spring整合Mybatis开发过程中 不适用@Repository @Component
````

### 2.@Scope

````
作用：控制简单对象创建次数
注意：不添加@Scope Spring提供默认值 singleton
````

### 3.@Lazy

````
作用：延迟创建单实例对象
````

### 4.生命周期方法相关注解

````
1.初始化相关方法
InitializingBean
2.销毁方法
DisposableBean

注意：1.上述2个注解并不是Spring提供的,而是JSR（JavaEE规范）520
	 2.再一次的验证,通过注解实现了接口的契约性
````

### 5.注入相关注解

* 用户自定义类型@Autowired

Autowired的注解的细节：

````
1.Autowired注解基于类型进行注入
2.基于名称注入要配合@Qualifier
3.Autowired注解放置位置
	a.放置在对应成员变量的set方法
	b.放置在成员变量上（通过反射）
4.JavaEE规范中类似功能的注解
	JSR250
	@Resource(name = "xxxxxx") 基于名字注入
	@Autowired（）
	@Qualifier（"xxxxxx"）
	注意：如果在应用Resource注解时，名字没有配对成功，那么他会继续按照类型进行注入
	JSR330
	@Inject 作用与@Autowired完全一致，基于类型进行注入
````

* JDK类型的成员变量@Value

````
设置一个配置标签<context:property-placeholder location=""/>
相当简单就不多说了 
"${}"
````

* @PropertySource注解

我们上面那个配置标签太过于麻烦了,所以我们决定也用注解来解决

````
应用@PropertySource读取配置文件
````

````java
package cn.simon.property;


import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

@Component
@PropertySource("classpath:/init.properties")
public class InitProperty {

    @Value("${user}")
    private String username;

    @Value("${sex}")
    private String sex;

    @Value("${xiaomao}")
    private String xiaomao;


    public void setUsername(String username) {
        this.username = username;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public void setXiaomao(String xiaomao) {
        this.xiaomao = xiaomao;
    }

    public String getUsername() {
        return username;
    }

    public String getSex() {
        return sex;
    }

    public String getXiaomao() {
        return xiaomao;
    }
}

````

@Value属性的使用细节

````
1.不能将其使用在静态变量上（如果应用则会注入失败）
2.Value直接+Properties这种方式无法注入集合类型
Spring提供新的配置形式：YAML，YML
````

### 6.注解扫描详解

````xml
<context:component-scan base-package="cn.simon"/>
当前包 及其 子包
````

#### 1.排除方式

````xml
<context:component-scan base-package="cn.simon">
	<context:exclude-filter type="" expression=""/>
	type:assignable:排除特定的类型
		 annotation:排除指定的注解
    	 aspectj:排除切入点表达式（只能用包、类切入点表达式）
    		cn.simon.bean..* （bean包及其子包的所有类）
    		*..User		(所有包的User类)
    	 regex:正则表达式
    	 custom:自定义排除策略（框架的底层开发,SpringBoot就用了很多）
</context:component-scan>

排除策略可以叠加使用
````

#### 2.包含方式（component-scan细化）

````xml
<context:component-scan base-package="cn.simon">
	<context:include-filter type="" expression="" use-default-filters="false"/>
	1.use-default-filters
    作用：让Spring默认的注解扫描方式失效
    2.我来指定扫描哪些注解
    type:assignable:包含特定的类型
		 annotation:包含指定的注解
    	 aspectj:包含切入点表达式（只能用包、类切入点表达式）
    		cn.simon.bean..* （bean包及其子包的所有类）
    		*..User		(所有包的User类)
    	 regex:正则表达式
    	 custom:自定义包含策略
</context:component-scan>
````

### 7.对于注解开发的思考

* 配置互通

````
Spring注解配置 配置文件的配置 互通
即我们在配置文件里面可以ref到我们注解所创建的对象
````

* 什么情况使用注解 什么情况使用配置文件

````
@Component替换 <bean>
1.在程序员开发的类型上,可以加入对应注解,进行对象的创建
2.应用其他非程序员开发的类型时，还是需要使用<bean>进行配置的
SqlSessionFactoryBean	MapperScannerConfigure
````

## 22.Spring的高级注解（Spring3.x）

### 1.配置Bean

````
Spring3.x提供的新注解,用于替换XML配置文件
@Configuration
public class AppConfig{

}
这个@Configuration替换了我们的applicationContext中什么内容呢？
其实就是我们原有applicationContext的xml内容都在配置Bean里面可以完成
````

![19](img%2F19.png)

````
1.创建工厂代码
ApplicationContext ctx = new AnnotationConfigApplicationContext();
2.指定配置文件
	1.指定配置bean的Class
	ApplicationContext ctx = new AnnotationConfigApplicationContext(AppConfig.class);
	2.指定配置bean所在的路径
	ApplicationContext ctx = new AnnotationConfigApplicationContext("cn.simon");
````

* 配置Bean开发的细节分析

````
1.不能集成Log4j（不知道为啥我可以emm）
集成logback xml网上可以自己去找一下
2.本质：也是@Component注解的衍生注解
自然可以应用<context:component-scan>进行扫描
````

### 2.@Bean注解

````
@Bean注解在配置bean中进行使用,等同于XML配置文件中的<bean>标签
````

````
1.简单对象
2.复杂对象
我们使用@Bean注解就要考虑上面两种对象的创建 
````

![20](img%2F20.png)

````java
//代码如下所示
	@Test
    public void test2(){
        ApplicationContext ctx = new AnnotationConfigApplicationContext(AppConfig.class);
        UserBean userBean = (UserBean) ctx.getBean("userBean");


        Connection conn = (Connection) ctx.getBean("conn");
        System.out.println(userBean);
        System.out.println(conn);
    }
````

* @Bean注解创建复杂对象的注意事项

我们之前创建复杂对象是通过FactoryBean来进行实现的,其实使用注解开发也是可以的

````java
@Bean
    public Connection conn1(){
        Connection conn = null;
        try {
            ConnectionFactoryBean factoryBean = new ConnectionFactoryBean();
            factoryBean.setDriverClassName("com.mysql.jdbc.Driver");
            factoryBean.setUrl("jdbc:mysql://localhost:3306/mydb");
            factoryBean.setUsername("root");
            factoryBean.setPassword("zxmi520..");
            conn = factoryBean.getObject();
        }catch (Exception e){

        }
        return conn;
    }
````

* @Bean自定义id值

````
@Bean("xxx")
如果不指定就是方法名，例如上面的conn1
````

* 控制对象的创建次数

````
就是@scope注解就行
@Bean("xxx")
@Scope("prototype")
````

### 3.@Bean注解的注入

* 用户自定义类型

````java
	@Bean
    public UserDAO userDAO(){
        return new UserDAOImpl();
    }

    @Bean
    public UserService userService(UserDAO userDAO){
        UserServiceImpl userService = new UserServiceImpl();
        userService.setUserDAO(userDAO);
        return userService;
    }
作为参数放入这个方法里面即可，容器里面有这个对象就会放进去然后set进去
或者这样
 	@Bean
    public UserDAO userDAO(){
        return new UserDAOImpl();
    }

    @Bean
    public UserService userService(){
        UserServiceImpl userService = new UserServiceImpl();
        userService.setUserDAO(userDAO());
        return userService;
    }
````

* JDK类型的注入

````java
	@Bean
    public User user(){
        User user = new User();
        user.setId(1);
        user.setName("simon");
        return user;
    }
太基础了就不说了
````

JDK类型注入的细节分析

我们这样jdk注入没有解耦，所以我们可以使用之前的PropertySource的注解来读取配置文件来进行注入jdk属性

````java
@Configuration
@PropertySource("classpath:init.properties")
public class AppConfig {

    @Value("${id}")
    private Integer id;

    @Value("${user}")
    private String name;

    @Bean
    public User user(){
        User user = new User();
        user.setId(id);
        user.setName(name);
        return user;
    }
}
````

### 4.@ComponentScan注解

````java
@ComponentScan注解在配置bean中使用，等同于XML配置文件中的<context:component-scan>标签

目的：进行相关注解的扫描（@Component @Value @Autowired）

@Configuration
@ComponentScan(basePackages = "cn.simon")
@PropertySource("classpath:init.properties")
public class AppConfig {
````

#### 1.排除、包含的使用

````java
@ComponentScan(basePackages = "cn.simon", excludeFilters = {@ComponentScan.Filter(type = FilterType.ANNOTATION, value = Service.class)}
排除cn.simon包下面的@Service注解的对象
包含同理
@ComponentScan(basePackages = "cn.simon",useDefaultFilters = false ,includeFilters = {@ComponentScan.Filter(type = FilterType.ASPECTJ, pattern = "cn.simon.bean..*")})
````

### 5.Spring多种创建对象方式的应用场景

![21](img%2F21.png)

基于@Import注解

在@Import里面的值可以直接写入类，在配置文件@Confiuguration里面可以直接注册这个里面写入的bean

### 6.配置的优先级

````
@Component及其衍生注解 < @Bean < 配置文件bean标签
优先级高的配置 覆盖优先级低配置
配置覆盖：id值 保持一致
````

* 这样就可以解决注解配置的解耦合问题

````java
@Configuration
@ImportResource("applicationContext.xml")
public class AppConfig4{
	@Bean
	public UserDAO userDAO(){
		return new UserDAOImpl();
	}
}
````

我们applicationContext.xml里面的userDAO的id属性的bean可以覆盖这个@Bean注解的对象

同时我们在获取容器的时候可以多配置,有同学说,那我也要在AppConfig4里面修改啊,加@ImportResource("applicationContext.xml")注解

这个不简单嘛,我们新建一个AppConfig5，然后在容器的时候写入多配置文件即可

````java
ApplicationContxt ctx = new AnnotationConfigApplicationContext(AppConfig4.class,AppConfig5.class);
````

### 7.整合多个配置信息

![22](img%2F22.png)

按照功能进行拆分

例如Spring和Mybatis进行整合写一个配置Bean

Spring和事务的整合也写一个配置Bean

````
拆分多个配置bean的开发,是一种模块化开发的形式,也体现了面对对象各司其职的设计思想
````

* 多个配置Bean的整合

  * 使用basepackge对多个配置Bean的整合

    ````java
    AnnotationConfigApplicationContext atx = new AnnotationConfigApplicationContext("cn.simon.config");
    ````

    使用@Import注解(即原来的配置文件的import标签)

    ````java
    @Configuration
    @Import(AppConfig2.class)
    public class AppConfig1 {
    
        @Bean
        public User user(){
            return new User();
        }
    }
    AnnotationConfigApplicationContext atx = new AnnotationConfigApplicationContext(AppConfig1.class);
    ````

* 配置Bean与@Component相关注解的整合

  ````java
  @Component
  public class UserDAOImpl implements UserDAO{
  }
  
  @Configuration
  @ComponentScan("")
  public class AppConfig3{
      
      @Autowired
      private UserDAO userDAO;
      
  	@Bean
  	public UserService userService(){
  		UserServiceImpl userService = new UserServiceImpl;
          userService.setUserDAO(userDAO);
  		return userService;
  	}
  }
  ````

  

* 配置Bean与SpringXML配置文件的整合

  ![24](img%2F24.png)

如何实现跨配置的注入？

![23](img%2F23.png)



我们要注入谁,就直接@Autowired即可

### 8.配置Bean的底层实现 

````
Spring在配置bean中加入了@Configuration注解后,底层就会通过Cglib的代理方式,来进行对象的相关配置、处理（例如控制对象创建次数）
````

## 23.四维一体的开发思想

### 1.什么是四维议题

````
Spring开发一个功能的4种形式，虽然开发方式不同,但是最终效果是一样的
1.基于schema
2.基于特定功能注解	[推荐]
3.基于原始<bean>
4.基于@Bean注解	  [推荐]
其实都是基于PropertySourcePlaceHolderConfigurer
````

代码实战:基于@Bean注解开发

````java
package cn.simon.four;

import org.springframework.beans.factory.annotation.Value;

import java.io.Serializable;

@Component
public class Account implements Serializable {

    @Value("${id}")
    private Integer id;

    @Value("${name}")
    private String name;

    public void setId(Integer id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getId() {
        return id;
    }

    public String getName() {
        return name;
    }
}

````

````java
package cn.simon.config;


import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;
import org.springframework.core.io.ClassPathResource;

@Configuration
@ComponentScan(basePackages = "cn.simon.four")
public class AppConfig3 {
    @Bean
    public PropertySourcesPlaceholderConfigurer configurer(){
        PropertySourcesPlaceholderConfigurer configurer = new PropertySourcesPlaceholderConfigurer();
        configurer.setLocation(new ClassPathResource("four.properties"));
        return configurer;
    }
}

````

````
id = 10
name = xiaojr
````



