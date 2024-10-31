/*package DataBaseHandlers;

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

            }

            return recipesDictonary;

        } catch (SQLException e) {
            e.printStackTrace();
            // Если произошла ошибка - возвращаем пустой словарь
            return Collections.emptyMap();
        }
    }

    public void addRecipe(Recipe recipe)  {

        try (PreparedStatement statement = connection.prepareStatement(
                "INSERT INTO Recipes(`id`, `title`, `description`, `url_photo`,`ingredients` ) " +
                        "VALUES(?, ?, ?, ?, ?)")) {
            statement.setObject(1, recipe.getId());
            statement.setObject(2, recipe.getTitle());
            statement.setObject(3, recipe.getDescription());
            statement.setObject(4, recipe.getUrl_photo());
            statement.setObject(5, ingredients_to_bd);

            statement.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void deleteRecipe(int id) {
        try (PreparedStatement statement = connection.prepareStatement(
                "DELETE FROM Users WHERE id = ?")) {
            statement.setObject(1, id);

            statement.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}*/
