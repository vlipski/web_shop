package dao;


import by.it.connection.ConnectionManager;
import by.it.dao.UserDao;
import by.it.dao.impl.UserDaoImpl;
import by.it.entities.User;
import org.junit.Test;
import java.sql.Connection;
import java.sql.SQLException;

public class UserDaoTest {

    UserDao userDao = UserDaoImpl.getInstance();
    User user = new User();

    @Test
    public void generalUserTest() throws SQLException {
        user.setName("Виталий Липский");
        user.setLogin("lipski");
        user.setPassword("aaa111");
        Connection connection = ConnectionManager.getConnection(2);
        connection.setAutoCommit(false);
        userDao.insert(user);
        User newUser= userDao.selectByLogin("lipski");
        user.setRole("ADMIN");
        userDao.update(user);
        System.out.println(user.getPassword().equals("aaa11"));



        connection.commit();
    }
}
