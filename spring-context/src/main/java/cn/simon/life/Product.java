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
