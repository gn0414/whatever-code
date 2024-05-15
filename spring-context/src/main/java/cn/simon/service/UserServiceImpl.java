package cn.simon.service;

import cn.simon.dao.UserDAO;
import cn.simon.entity.User;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;


@Transactional
public class UserServiceImpl implements UserService{

    private UserDAO userDAO;

    public void setUserDAO(UserDAO userDAO) {
        this.userDAO = userDAO;
    }

    public UserDAO getUserDAO() {
        return userDAO;
    }

    @Override
    public void register(User user) {
        userDAO.save(user);
    }
}
