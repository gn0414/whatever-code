package cn.simon.basic;

public class UserServiceImplNew implements UserService{
    @Override
    public void login(String name, String password) {
        System.out.println("userServiceImplNew-login");
    }

    @Override
    public void register(User user) {
        System.out.println("userServiceImplNew-register");
    }
}
