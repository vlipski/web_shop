package by.it.dao.impl;

import by.it.dao.UserDao;
import by.it.entities.User;

import java.io.Serializable;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

public class UserDaoImpl extends AbstractDao implements UserDao {

    private static volatile UserDao INSTANCE = null;

    private static final String insertSql = "INSERT INTO USER (NAME, LOGIN, PASSWORD) VALUES (?, ?, ?)";
    private static final String selectByLoginSql = "SELECT * FROM USER WHERE LOGIN = ?";
    private static final String selectAllSql = "SELECT * FROM USER";
    private static final String updateSql = "UPDATE USER SET NAME =?, LOGIN = ?, PASSWORD = ?, STATUS = ?, ROLE = ? WHERE ID_USER = ?";
    private static final String deleteSql = "DELETE FROM user WHERE ID_User = ?";


    private PreparedStatement prstInsert;
    private PreparedStatement prstSelectByLogin;
    private PreparedStatement prstSelectAll;
    private PreparedStatement prstUpdate;
    private PreparedStatement prstDelete;


    public static UserDao getInstance() {
        UserDao userDao = INSTANCE;
        if (userDao == null) {
            synchronized (UserDaoImpl.class) {
                userDao = INSTANCE;
                if (userDao == null) {
                    INSTANCE = userDao = new UserDaoImpl();
                }
            }
        }
        return userDao;
    }

    @Override
    public User insert(User user) throws SQLException {
        prstInsert = preStatement(insertSql, Statement.RETURN_GENERATED_KEYS);
        prstInsert.setString(1, user.getName());
        prstInsert.setString(2, user.getLogin());
        prstInsert.setString(3, user.getPassword());
        prstInsert.executeUpdate();
        ResultSet results = prstInsert.getGeneratedKeys();
        if (results.next()) {
            user.setIdUser(results.getLong(1));
        }
        close(results);
        return user;
    }

    @Override
    public User selectByLogin(String login) throws SQLException {
        prstSelectByLogin = preStatement(selectByLoginSql);
        prstSelectByLogin.setString(1, login);
        ResultSet result = prstSelectByLogin.executeQuery();
        if (result.next()) {
            return populatEntity(result);
        }
        close(result);
        return null;
    }

    @Override
    public List<User> selectAll() throws SQLException {
        return selectList(prstSelectAll,selectAllSql);
    }

    @Override
    public User selectById(Serializable id) throws SQLException {
        return null;
    }

    @Override
    public void update(User user) throws SQLException {
        prstUpdate = preStatement(updateSql);
        prstUpdate.setString(1, user.getName());
        prstUpdate.setString(2, user.getLogin());
        prstUpdate.setString(3, user.getPassword());
        prstUpdate.setString(4, user.getStatus());
        prstUpdate.setString(5, user.getRole());
        prstUpdate.setLong(6, user.getIdUser());
        prstUpdate.executeUpdate();
    }

    @Override
    public int delete(Serializable id) throws SQLException {
        prstDelete = preStatement(deleteSql);
        prstDelete.setLong(1, (long) id);
        return prstDelete.executeUpdate();
    }

    @Override
    protected User populatEntity(ResultSet result) throws SQLException {
        User user = new User();
        user.setIdUser(result.getLong(1));
        user.setName(result.getString(2));
        user.setLogin(result.getString(3));
        user.setPassword(result.getString(4));
        user.setStatus(result.getString(5));
        user.setRole(result.getString(6));
        return user;
    }
}

