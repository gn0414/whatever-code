package cn.simon.factory;

import cn.simon.proxy.User;

public interface UserService {

   void login(String name,String password);

   void register(User user);
}
