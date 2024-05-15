package cn.simon.life;

import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

@Component
public class ProductBean {

    @PostConstruct
    public void myInit(){
        System.out.println("my init");
    }

    @PreDestroy
    public void myDes(){
        System.out.println("my des");
    }
}
