package DataBaseHandlers;

import User.*;
import org.sqlite.JDBC;

import java.sql.*;
import java.util.*;

public class DbHandlerUser {
    private static final String CONNECTION_PATH = "jdbc:sqlite:E:\\ООП\\Chef_Bot\\src\\main\\DB\\Recipes.db";
    private static Connection connection;

    /////Синглтон
    private static class SingletonHolder{
        public static final DbHandlerUser HOLDER_INSTANCE;
        static {
            try {
                HOLDER_INSTANCE = new DbHandlerUser();
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
    }
    public static DbHandlerUser getInstance() {
        return DbHandlerUser.SingletonHolder.HOLDER_INSTANCE;
    }
    ////Синглтон

    private DbHandlerUser() throws SQLException {
        DriverManager.registerDriver(new JDBC());
        connection = DriverManager.getConnection(CONNECTION_PATH);
    }

    public Map<Long, User> getALL() {
        try (Statement statement = connection.createStatement()) {
            Map<Long, User> recipesDictonary = new HashMap<>();

            ResultSet resultSet = statement.executeQuery("SELECT id, userName, password FROM Users");

            while (resultSet.next()) {
                Long id = resultSet.getLong(1);
                String userName = resultSet.getString(2);
                String password = resultSet.getString(3);

                User user = new User(id, userName, password);
                recipesDictonary.put(id, user);
            }

            return recipesDictonary;

        } catch (SQLException e) {
            e.printStackTrace();
            // Если произошла ошибка - возвращаем пустой словарь
            return Collections.emptyMap();
        }
    }

    public void add(User user)  {

        try (PreparedStatement statement = connection.prepareStatement(
                "INSERT INTO Users(`id`, `userName`, `password`) " +
                        "VALUES(?, ?, ?)")) {
            statement.setObject(1, user.getId());
            statement.setObject(2, user.getUserName());
            statement.setObject(3, user.getPassword());

            statement.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void delete(Long id) {
        try (PreparedStatement statement = connection.prepareStatement(
                "DELETE FROM Users WHERE id = ?")) {
            statement.setObject(1, id);

            statement.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
