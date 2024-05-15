package cn.simon.config;


import cn.simon.life.Product;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;

@Configuration
public class AppConfig2 {


    @Bean
    public Product product(){
        return new Product();
    }
}
