package by.it.services.impl;


import by.it.dao.UserDao;
import by.it.dao.impl.UserDaoImpl;
import by.it.entities.User;
import by.it.services.ServiceException;
import by.it.services.UserService;
import org.apache.log4j.Logger;

import java.sql.SQLException;

public class UserServiceImpl extends AbstractServices implements UserService {

    private static final Logger log = Logger.getLogger(BasketServiceImpl.class);
    private static volatile UserService INSTANCE = null;
    private UserDao userDao = UserDaoImpl.getInstance();


    public static UserService getInstance() {
        UserService userService = INSTANCE;
        if (userService == null) {
            synchronized (UserServiceImpl.class) {
                userService = INSTANCE;
                if (userService == null) {
                    INSTANCE = userService = new UserServiceImpl();
                }
            }
        }
        return userService;
    }

    @Override
    public User createUser(String name, String login, String password) {
        User user = new User(name, login, password);
        try {
            startTransaction(4);
            if (userDao.selectByLogin(login) == null) {
                user = userDao.insert(user);
            } else {
                throw new ServiceException("registration.userWithThisLoginExists");
            }
            commit(4);
            return user;
        } catch (SQLException e) {
            rollback(4);
            log.error(e);
            throw new ServiceException("Error create User");
        }
    }

    @Override
    public User authorization(String login, String password) {
        try {
            startTransaction(2);
            User user = userDao.selectByLogin(login);
            if (user != null && password.equals(user.getPassword())) {
                commit(2);
                return user;
            }
            rollback(2);
            user = null;
            return user;
        } catch (SQLException e) {
            log.error(e);
            throw new ServiceException("Error getting User by login" + login);
        }
    }
}
