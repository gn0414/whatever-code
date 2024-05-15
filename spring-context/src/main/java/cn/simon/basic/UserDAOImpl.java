package cn.simon.basic;

public class UserDAOImpl implements UserDAO{
    @Override
    public void insert(User user) {
        System.out.println("insert user");
    }

    @Override
    public User query() {
        System.out.println("query user");
        return new User();
    }
}
