package cn.simon.aspect;

import cn.simon.proxy.User;

public interface UserService {

   void login(String name,String password);

   void register(User user);
}
