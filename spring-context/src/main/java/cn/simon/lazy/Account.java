package cn.simon.lazy;


import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

@Component
@Lazy
public class Account {

    public Account(){
        System.out.println("Account.account");
    }
}
