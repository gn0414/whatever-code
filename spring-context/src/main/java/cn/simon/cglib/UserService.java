package cn.simon.cglib;

import cn.simon.basic.User;

public class UserService {

    public void login(String username,String password){
        System.out.println("UserService.login");
    }

    public void register(User user){
        System.out.println("UserService.register");
    }
}
