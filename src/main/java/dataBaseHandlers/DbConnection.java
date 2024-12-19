package dataBaseHandlers;

import org.sqlite.JDBC;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DbConnection {
    private static final String CONNECTION_PATH = "jdbc:sqlite:DB/Recipes.db";
    private static Connection connection;

    ///Синглтон
    private static class SingletonHolder{
        public static final DbConnection HOLDER_INSTANCE;
        static {
            try {
                HOLDER_INSTANCE = new DbConnection();
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
    }
    public static DbConnection getInstance() {
        return DbConnection.SingletonHolder.HOLDER_INSTANCE;
    }

    private DbConnection() throws SQLException {
        DriverManager.registerDriver(new JDBC());
        connection = DriverManager.getConnection(CONNECTION_PATH);
    }

    public Connection getConnection(){
        return connection;
    }

}
