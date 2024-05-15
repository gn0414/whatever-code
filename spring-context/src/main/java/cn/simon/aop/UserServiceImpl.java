package cn.simon.aop;


import org.springframework.stereotype.Component;

@Component
public class UserServiceImpl implements UserService {
    @Override
    public void login() {
        System.out.println("login is success");
    }
}
