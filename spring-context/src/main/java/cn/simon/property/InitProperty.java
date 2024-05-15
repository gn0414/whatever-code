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
