package cn.simon.config;


import cn.simon.bean.User;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

@Configuration
@Import(AppConfig2.class)
public class AppConfig1 {

    @Bean
    public User user(){
        return new User();
    }
}
