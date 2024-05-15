package cn.simon.basic;

public class UserServiceImpl implements UserService{

    private UserDAO userDAO = (UserDAO) BeanFactory.getBean("userDao");

    @Override
    public void login(String name, String password) {
        userDAO.query();
    }

    @Override
    public void register(User user) {
        userDAO.insert(user);
    }
}
