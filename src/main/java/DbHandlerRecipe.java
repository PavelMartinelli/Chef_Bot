import org.sqlite.JDBC;

import java.sql.*;
import java.util.*;

public class  DbHandlerRecipe {

    private static String CONNECTION_PATH;
    private static Connection connection;

    /////Синглтон
    private DbHandlerRecipe() {
    }

    private static class SingletonHolder {
        public static final DbHandlerRecipe HOLDER_INSTANCE = new DbHandlerRecipe();
    }
    public static DbHandlerRecipe getInstance() {
        return SingletonHolder.HOLDER_INSTANCE;
    }
    ////Синглтон

    private DbHandlerRecipe(String connection_path) throws SQLException {
        DriverManager.registerDriver(new JDBC());
        CONNECTION_PATH = connection_path;
        connection = DriverManager.getConnection(CONNECTION_PATH);
    }

    public Map<Integer, Recipe> getALLRecipes() throws SQLException {
        try (Statement statement = connection.createStatement()) {
            Map<Integer, Recipe> recipesDictonary = new HashMap<Integer, Recipe>();

            ResultSet resultSet = statement.executeQuery("SELECT id, title, description, " +
                    "url_photo, ingredients FROM products");

            while (resultSet.next()) {
                ArrayList<String> ingredients_from_bd =
                    new ArrayList<String>(Arrays.asList(resultSet.getString("title").split(",")));

                recipesDictonary.put(resultSet.getInt("id"),
                        new Recipe(resultSet.getInt("id"),
                                resultSet.getString("title"),
                                resultSet.getString("description"),
                                resultSet.getString("url_photo"),
                                ingredients_from_bd));
            }

            return recipesDictonary;

        } catch (SQLException e) {
            e.printStackTrace();
            // Если произошла ошибка - возвращаем пустой словарь
            return Collections.emptyMap();
        }
    }

    public void addRecipe(Recipe recipe) throws SQLException {
        String ingredients_to_bd = String.join(",", recipe.getIngredients());

        try (PreparedStatement statement = connection.prepareStatement(
                "INSERT INTO Products(`id`, `title`, `description`, `url_photo`,`ingredients` ) " +
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
                "DELETE FROM Products WHERE id = ?")) {
            statement.setObject(1, id);

            statement.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

}
