package by.it.dao;

import by.it.entities.User;

import java.sql.SQLException;
import java.util.List;

public interface UserDao extends DAO<User> {

    User selectByLogin(String login) throws SQLException;

    List<User> selectAll() throws SQLException;
}
