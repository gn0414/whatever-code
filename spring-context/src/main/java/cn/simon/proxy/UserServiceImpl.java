package cn.simon.proxy;

public class UserServiceImpl implements UserService{


    @Override
    @Log
    public void register(User user) {
        System.out.println("userimpl.register");
    }

    @Override
    public boolean login(String name, String password) {
        System.out.println("userimpl.login");
        return true;
    }
}
