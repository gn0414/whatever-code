# SpringMVC

## 1.SpringMVC由来

由来：基于Spring框架基础之上开发的一个全新的框架 springmvc

作用：SpringMVC可以web开发时的控制器框架,用来替换现有项目中Struts2控制器框架 

![1.png](img%2F1.png)
## 2.SpringMVC引言
SpringMVC典型mvc框架 控制器框架 他是在spring基础上二次开发，用于替换之前的struts2框架
## 3.为什么是SpringMVC
（1）Spring框架的流行程度很高，可以与Spring框架进行无缝整合
（2）SpringMVC运行效率高于struts2运行效率 action创建一个新的Action SpringMVC默认单例处理
（3）注解式开发，开发更加高效灵活
 ## 4.SpringMVC处理基本流程
![2.png](img%2F2.png)
## 5.SpringMVC第一个环境搭建

### 1.web.xml

````xml
<!DOCTYPE web-app PUBLIC
 "-//Sun Microsystems, Inc.//DTD Web Application 2.3//EN"
 "http://java.sun.com/dtd/web-app_2_3.dtd" >

<web-app>
  <display-name>Archetype Created Web Application</display-name>
  <!-- 配置springmvc核心servlet-->
  
  <servlet>
    <servlet-name>springmvc</servlet-name>
    <servlet-class>org.springframework.web.servlet.DispatcherServlet</servlet-class>
    <!-- 配置springmvc配置文件-->
    <init-param>
      <param-name>contextConfigLocation</param-name>
      <param-value>classpath:springmvc.xml</param-value>
    </init-param>
  </servlet>
  
  <servlet-mapping>
    <servlet-name>springmvc</servlet-name>
    <url-pattern>/</url-pattern>
  </servlet-mapping>
</web-app>
配置转发servlet,路径为/即转发所有请求,并且写入自己的配置文件

````

### 2.Springmvc.xml

````xml
<?xml version="1.0" encoding="UTF-8"?>
<beans xmlns="http://www.springframework.org/schema/beans"
       xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
       xmlns:context="http://www.springframework.org/schema/context"
       xmlns:mvc="http://www.springframework.org/schema/mvc"
       xsi:schemaLocation="http://www.springframework.org/schema/beans http://www.springframework.org/schema/beans/spring-beans.xsd http://www.springframework.org/schema/context http://www.springframework.org/schema/context/spring-context.xsd http://www.alibaba.com/schema/stat http://www.alibaba.com/schema/stat.xsd http://www.springframework.org/schema/mvc http://www.springframework.org/schema/mvc/spring-mvc.xsd">
    <!--开启注解扫描 -->
    <context:component-scan base-package="cn.simon.controller"/>

<!--    &lt;!&ndash; 配置处理器映射器&ndash;&gt;-->
<!--    <bean class="org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerMapping"/>-->
<!--    &lt;!&ndash; 配置处理器适配器&ndash;&gt;-->
<!--    <bean class="org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerAdapter"/>-->
    <!-- spring3版本之后已经不建议自己去手动配置处理器映射器和处理器适配器了,而是使用一个schema,它不仅帮我们做了参数类型转换、还做了跳转 响应处理... -->
    <mvc:annotation-driven/>

    <!-- 配置视图解析器-->
    <bean class="org.springframework.web.servlet.view.InternalResourceViewResolver">
        <!-- 注入前缀和后缀 -->
        <property name="prefix" value="/"/>
        <property name="suffix" value=".jsp"/>
    </bean>
</beans>
````

Springmvc的三大组件映射器、适配器、视图解析器

自然都要作为对象,但是我们后面会使用mvc:annotation-driven，而不是配置最简单的适配器和映射器

### 3.Controller

````java
package cn.simon.controller;


import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class HelloController {

    /**
     * @RequestMapping 修饰范围：用在类上 和 方法上
     * 1.用在方法上用来给当前方法加入指定的请求路径
     * 2.用在类上用来给类中所有的方法加入同一的请求路径,在方法访问之前需要加入类上@RequestMapping的路径
     */
    @RequestMapping(value = "/hello")
    public String hello(){
        //1.收集数据
        //2.调用业务方法
        System.out.println("hello springmvc");
        //3.处理响应
        return "index";
    }

    @RequestMapping("save")
    public String save(){
        System.out.println("save method...");
        return "index";
    }
}
````

## 6.Springmvc中的跳转方式

1.原始Servlet技术中跳转方式?

(1)forward跳转：请求转发

​				  特点：服务器内部跳转,跳转之后地址栏不变,一次跳转,跳转时可以使用request作用域传递数据

(2)redirect跳转：请求重定向

​				  特点：客户端跳转,跳转之后地址栏改变	多次跳转	跳转过程不能使用request作用域传递数据

2.SpringMVC中跳转方式

a.Controller	-------->	JSP页面跳转

forword: 默认就是forward跳转到页面 具体语法return 

redirect：使用语法 return "redirect:/index.jsp", redirect后面填写视图的全名,redirect跳转不会经过视图解析器

b.Controller    -------->	Controller之间跳转(相同,不同控制器)

forword: 要写上关键字forward:/controller/method(同样也是全路径)

redirect：要写上关键字redirect:/controller/method

具体代码演示如下

````java
package cn.simon.controller;


import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("forwardAndRedirect")
public class ForwardAndRedirectController {
    /**
     * 测试forward跳转到页面
     * 默认：controller跳转到jsp页面局势forward跳转
     */
    @RequestMapping("test")
    public String test(){
        System.out.println("test");
        return "index";
    }
    /**
     * 测试redirect跳转到页面
     * 默认：controller跳转到jsp页面局势redirect跳转
     * 使用语法 return "redirect:/index.jsp"
     * redirect后面填写视图的全名
     * 注意：redirect跳转不会经过视图解析器
     */
    @RequestMapping("test1")
    public String test1(){
        System.out.println("test1");
//        return "redirect:https://www.baidu.com";
        return "redirect:/index.jsp";
    }

    /**
     * 测试forward跳转到相同(不同)controller的不同方法
     * forward:跳转指定Controller和方法的RequestMapping路径
     * @return
     */
    @RequestMapping("test2")
    public String test2(){
        System.out.println("test2");
        return "forward:/forwardAndRedirect/test";
    }


    /**
     * 测试redirect跳转到相同(不同)controller的不同方法
     * forward:跳转指定Controller和方法的RequestMapping路径
     * @return
     */
    @RequestMapping("test3")
    public String test3(){
        System.out.println("test3");
        return "redirect:/forwardAndRedirect/test";
    }
}

````

## 7.Springmvc中参数接收
![3.png](img%2F3.png)

代码演示

````java
package cn.simon.controller;


import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("forwardAndRedirect")
public class ForwardAndRedirectController {
    /**
     * 测试forward跳转到页面
     * 默认：controller跳转到jsp页面局势forward跳转
     */
    @RequestMapping("test")
    public String test(){
        System.out.println("test");
        return "index";
    }
    /**
     * 测试redirect跳转到页面
     * 默认：controller跳转到jsp页面局势redirect跳转
     * 使用语法 return "redirect:/index.jsp"
     * redirect后面填写视图的全名
     * 注意：redirect跳转不会经过视图解析器
     */
    @RequestMapping("test1")
    public String test1(){
        System.out.println("test1");
//        return "redirect:https://www.baidu.com";
        return "redirect:/index.jsp";
    }

    /**
     * 测试forward跳转到相同(不同)controller的不同方法
     * forward:跳转指定Controller和方法的RequestMapping路径
     * @return
     */
    @RequestMapping("test2")
    public String test2(){
        System.out.println("test2");
        return "forward:/forwardAndRedirect/test";
    }


    /**
     * 测试redirect跳转到相同(不同)controller的不同方法
     * forward:跳转指定Controller和方法的RequestMapping路径
     * @return
     * 这个%5B是[
     * 这个%5D是]
     * http://localhost:38080/spring_mvc_context/param/test4?maps%5Baaa%5D=ximeng&maps%5Bbbb%5D=simon
     */
    @RequestMapping("test3")
    public String test3(){
        System.out.println("test3");
        return "redirect:/forwardAndRedirect/test";
    }
}

````

### 1.Springmvc在接受请求参数时解决中文乱码的问题

````xml 
1.GET方式请求出现乱码
tomcat8.x版本之前 默认使用server.xml中URLEncoding="iso-8859-1" 编码不是

在你的tomcat里面server.xml
   <Connector port="38080" protocol="HTTP/1.1"
               connectionTimeout="20000"
               redirectPort="8443"
               maxParameterCount="1000" URIEncoding="UTF-8"
               />
写上 URIEncoding="UTF-8"
2.POST方式请求出现乱码
说明：在springmvc中默认没有对post方式请求没有任何编码处理,所以直接接受post方法请求会出现中文乱码
解决方案：
	a.自定义filter 对request和response对象编码 utf-8
	b.使用springmvc提供好编码filter CharacterEncodingFilter 
````

a.自定义过滤器

````java
package cn.simon.filter;

import javax.servlet.*;
import java.io.IOException;

public class UTF8EncodingFilter implements Filter {
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        servletRequest.setCharacterEncoding("UTF-8");
        servletResponse.setCharacterEncoding("UTF-8");
        filterChain.doFilter(servletRequest,servletResponse);
    }

    @Override
    public void destroy() {

    }
}
````

````xml
<!DOCTYPE web-app PUBLIC
 "-//Sun Microsystems, Inc.//DTD Web Application 2.3//EN"
 "http://java.sun.com/dtd/web-app_2_3.dtd" >

<web-app>

  <display-name>Archetype Created Web Application</display-name>
  <!-- 配置springmvc核心servlet-->

  <filter>
    <filter-name>charset</filter-name>
    <filter-class>cn.simon.filter.UTF8EncodingFilter</filter-class>
  </filter>

  <filter-mapping>
    <filter-name>charset</filter-name>
    <url-pattern>/*</url-pattern>
  </filter-mapping>

  <servlet>
    <servlet-name>springmvc</servlet-name>
    <servlet-class>org.springframework.web.servlet.DispatcherServlet</servlet-class>
    <!-- 配置springmvc配置文件-->
    <init-param>
      <param-name>contextConfigLocation</param-name>
      <param-value>classpath:springmvc.xml</param-value>
    </init-param>
  </servlet>
  
  <servlet-mapping>
    <servlet-name>springmvc</servlet-name>
    <url-pattern>/</url-pattern>
  </servlet-mapping>
</web-app>
````

b.springmvc提供的过滤器

````xml
 <filter>
    <filter-name>charset</filter-name>
    <filter-class>org.springframework.web.filter.CharacterEncodingFilter</filter-class>
    <init-param>
      <param-name>encoding</param-name>
      <param-value>UTF-8</param-value>
    </init-param>
  </filter>

  <filter-mapping>
    <filter-name>charset</filter-name>
    <url-pattern>/*</url-pattern>
  </filter-mapping>
````

## 8.Springmvc中的数据传递机制

````
1.数据传递机制
	数据怎么存储？	数据页面如何获取？	在页面中获取的数据如何展示？
	
Servlet：	request、session、application（数据存储）
			 EL表达式（数据在页面如何获取）
			 EL+JSTL标签	展示
			 
Struts2： 	request、session、application（数据存储）
			 EL表达式（数据在页面如何获取）
			 EL+JSTL标签	展示		
			 
SpringMVC： 	request、session、application（数据存储）
			 EL表达式（数据在页面如何获取）
			 EL+JSTL标签	展示
			 
其实就是一样的
2.存数据具体使用哪种作用域:
	跳转方式: forward:一次请求	request作用域 | Model作用域 
			 redirect：多次请求 session作用域 application作用域[不推荐] ？地址栏传递数据
3.如何在springmvc控制器中获取request对象和response对象
public String findAll(HttpServletRequest request,HttpServletResponse response)
注意：直接将request	response对象作为控制器方法参数声明即可获取。
````

数据传递代码

````java
package cn.simon.controller;


import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

@Controller
@RequestMapping("attr")
public class AttrController {

    /**
     * 使用forward跳转页面数据传递
     * 1.传递零散类型数据
     * 2.传递对象类型数据
     * 3.传递集合类型数据
     * 建议是使用Model,我也不知道为啥（可能是解Servlet的藕？）
     */

    @RequestMapping("test")
    public String test(Model model,HttpServletRequest request, HttpServletResponse response){

        String name = "simon";
//        request.setAttribute("name",name);
        model.addAttribute("name",name);
        return "attr";
    }

    /**
     * 使用redirect跳转页面地址栏数据传递
     * 1.传递零散类型数据
     * 2.传递对象类型数据
     * 3.传递集合类型数据
     */
    @RequestMapping("test1")
    public String test1(Model model,HttpServletRequest request, HttpServletResponse response) throws UnsupportedEncodingException {
        String name = "西蒙";
        return "redirect:/attr.jsp?name="+ URLEncoder.encode(name,"UTF-8");
    }
    /**
     * 使用redirect跳转页面Session数据传递
     * 1.传递零散类型数据
     * 2.传递对象类型数据
     * 3.传递集合类型数据
     */
    @RequestMapping("test2")
    public String test2(Model model,HttpServletRequest request, HttpServletResponse response) throws UnsupportedEncodingException {
        String name = "西蒙";
        request.getSession().setAttribute("user",name);
        return "redirect:/attr.jsp";
    }
}
````

数据传递视图

````xml
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" isELIgnored="false" %>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html"; charset="utf-8">
    <title></title>
</head>
<body>
<h2>测试数据</h2>
<h3>request的结果 : ${requestScope.name}</h3>

<h3>获取地址栏的数据:${param.name}</h3>

<h3>获取session的数据:${sessionScope.user}</h3>
</body>
</html>
````

## 9.Springmvc的静态资源拦截问题

### 1.出现原因

我们的DispacherServlet配置时,url-pattern配置为"/",因为会导致项目中所有/开头请求,均被作为控制器请求处理,这样会导致项目中的静态资源(css,js,img)拦截。

解决方案:

1.url-pattern / 导致静态资源拦截,我们配置为 * .action, * .do ,* .haha

使用这种方式日后访问路径结尾必须加入指定后缀

2.url-pattern依然使用/，在Springmvc的配置文件中加入如下的配置

````xml
<mvc:default-servlet-handler/>
````

各种html,css,js,img等静态资源先去找其静态资源,找不到再交给控制器(但是我发现如果相同的路径会先去找控制器，找不到才找静态资源)

## 10.文件上传

定义：指的是用户将自己本地计算机中文件通过网络的形式上传到系统所在服务器上过程，这个过程称之为文件上传

如何开发文件上传呢？

（1）在系统开发一个可以进行文件上传页面，包含form表单 在表单中开发一个可以选择本地计算机文件入口

<form>
    	<input type="file" name=""/>
    	<input type="submit" name="提交"/>
</form>

（2）form表单 method

a.form表单提交方式必须为post

b.form enctype application/x-www-form-urlencoded（文本）| multipart/form-data(二进制)（调整为这个）

（3）开发Controller 在控制器方法中使用MultipartFile 进行文件的接受

（4）在springmvc配置文件加入文件上传解析器配置

​	CommonsMultipartResolver（要求文件上传解析器必须存在id，并且一定要叫做multipartResolver）（一点不能错）

（5）引入文件上传相关依赖

commons-fileupload

### 1.限制文件上传大小

````xml
<bean id="multipartResolver" class="org.springframework.web.multipart.commons.CommonsMultipartResolver">
        <property name="maxUploadSize" value="20971520"/>
    </bean>
````

最大上传2m

## 11.文件下载

定义：用户将服务器中文件下载到自己本地计算机中过程称之为文件下载

1.开放文件下载

a.定义系统中哪些文件需要用户下载

b.将需要下载文件放入指定下载目录中

c.开发一个页面提供一个文件下载链接

d.开发下载controller

jsp

````xml
<a href="${pageContext.request.contextPath}/file/download?fileName=aa.txt">aa.txt</a>
````

代码

````java
@RequestMapping("download")
    public void download(String fileName, HttpServletRequest request, HttpServletResponse response) throws IOException {

        System.out.println("下载文件名称"+fileName);


        String realPath = request.getSession().getServletContext().getRealPath("/download");

        //通过文件输入流加载文件
        FileInputStream is = new FileInputStream(new File(realPath, fileName));
        //设置响应类型
        response.setContentType("text/plain;charset=UTF-8");
        //获取响应输出流
        ServletOutputStream os = response.getOutputStream();
        //附件下载
        response.setHeader("content-disposition","attachment;fileName="+fileName);
//        int len;
//        //处理下载流
//        byte[] b = new byte[1024];
//
//        while (true){
//            len = is.read(b);
//            if (len == -1)break;
//            os.write(b,0,len);
//        }
//
//        os.close();
//        is.close();

        IOUtils.copy(is,os);
        IOUtils.closeQuietly(is);
        IOUtils.closeQuietly(os);
    }
````

## 12.Springmvc的拦截器

1.拦截器 Interceptor 拦截 中断

类似于javaweb中讲过Filter(只能拦截控制器的路径)

2.作用

通过将控制器中的通用代码放在拦截器中执行，减少控制器中代码的冗余

3.拦截器特点

（1）请求到达会经过拦截器，响应回来通用经过拦截器

（2）拦截器只能拦截控制器相关请求，不能拦截jsp，静态资源相关请求

（3）拦截器可以中断请求轨迹 

（4）开发拦截器

1.类  implement HandlerInterceptor

2.配置拦截器

a.注册拦截器对象 bean 

b.配置拦截器拦截请求路径

````java
package cn.simon.interceptors;


import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


public class MyInterceptor implements HandlerInterceptor {

    //1.请求经过拦截器会优先进入拦截器中preHandler方法执行preHandler方法中内容
    //2.如果preHandler返回true代表请求放行  如果返回值为false 中断请求
    //3.如果preHandler返回true就会进入控制器的方法
    //4.当控制器中的方法结束之后，就会返回到postHandler方法
    //5.posthandler执行完成之后会响应请求，在响应请求完成之后会执行afterCompletion
    @Override
    //参数1 参数2 分别是请求和响应对象 参数3：当前请求的控制器对应方法对象
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        System.out.println("==========1==========");
        return true;
    }


    //参数1 参数2 分别是请求和响应对象 参数3：当前请求的控制器对应方法对象 参数4 模型和视图（就是jsp）
    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
        System.out.println("==========3==========");
    }


    //参数1 参数2 分别是请求和响应对象 参数3：当前请求的控制器对应方法对象 参数4 请求过程中出现的异常对象
    //注意：无论正确还是失败都会执行
    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        System.out.println("==========4==========");
    }
}


package cn.simon.controller;


import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class HelloController {

    /**
     * @RequestMapping 修饰范围：用在类上 和 方法上
     * 1.用在方法上用来给当前方法加入指定的请求路径
     * 2.用在类上用来给类中所有的方法加入同一的请求路径,在方法访问之前需要加入类上@RequestMapping的路径
     */
    @RequestMapping(value = "/hello")
    public String hello(){
        //1.收集数据
        //2.调用业务方法
//        System.out.println("hello springmvc");
        System.out.println("==========2==========");
        //3.处理响应
        return "index";
    }

    @RequestMapping("save")
    public String save(){
        System.out.println("save method...");
        return "index";
    }


}

````

````xml
<bean id="myInterceptor" class="cn.simon.interceptors.MyInterceptor"/>
    
    <mvc:interceptors>
        <mvc:interceptor>
            <!-- 拦截哪些路径-->
            <mvc:mapping path="/hello"/>
            <ref bean="myInterceptor"/>
        </mvc:interceptor>
    </mvc:interceptors>
````

## 13.Springmvc的全局异常处理

### 1.Springmvc作为一个控制器主要作用

1.处理请求	接受请求数据	调用业务对象

2.请求响应	跳转对应视图展示数据

### 2.现有控制器开发存在问题

1.在处理用户请求出现运行时异常时直接响应给用户的是一个错误页面，对于用户的使用体验不友好

### 3.全局异常处理机制

作用：用来解决整合系统中任意一个控制器抛出异常时的统一处理入口

### 4.全局异常处理开发

1.类implements HandlerException

2.配置全局异常处理类

<bean id= "" class="xxxExceptionResolver">

````java
package cn.simon.handlerexception;

import org.springframework.web.servlet.HandlerExceptionResolver;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class GlobalExceptionResolver implements HandlerExceptionResolver {
    /**
     * 用来处理发生异常时方法
     * @param httpServletRequest 请求
     * @param httpServletResponse 响应
     * @param o 方法对象
     * @param e 异常对象
     * @return 出现异常时展示视图和数据
     */

    @Override
    public ModelAndView resolveException(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, Exception e) {
        ModelAndView modelAndView = new ModelAndView();
        //modelandview有很智能的一点就是我们的错误信息如果使用forward就会采用放入request作用域,但是我们如果是重定向就会放在url上
        modelAndView.setViewName("redirect:/error.jsp");
        modelAndView.addObject("msg",e.getMessage());
        return modelAndView;
    }
}
````

````java
package cn.simon.controller;


import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class ErrorController {

    @RequestMapping("/error")
    public String error(){
        throw new RuntimeException("出错了");
    }
}

````

````xml
<bean class="cn.simon.handlerexception.GlobalExceptionResolver"/>
````

