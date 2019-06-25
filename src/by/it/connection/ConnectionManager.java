package by.it.connection;

import java.sql.Connection;

/**
 * Class ConnectionManager
 *
 * Created by yslabko on 07/01/2017.
 */
public class ConnectionManager {
    private static ThreadLocal<Connection> tl = new ThreadLocal<>();

    public static Connection getConnection(int level) throws ConnectionException {
        try {
            if (tl.get() == null) {
                tl.set(DataSource.getInstance().getConnection(level));
            }
            return tl.get();
        } catch (Exception e) {
            throw new ConnectionException("Ошибка получения соединения " +  e.getMessage());
        }
    }
}
