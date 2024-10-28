import org.sqlite.JDBC;

import java.sql.*;
import java.util.*;

public class  DbHandlerRecipe  {

    private static final String CONNECTION_PATH = "jdbc:sqlite:E:\\ООП\\Chef_Bot\\src\\main\\DB\\Recipes.db";
    private static Connection connection;

    /////Синглтон
    private static class SingletonHolder{
        public static final DbHandlerRecipe HOLDER_INSTANCE;
        static {
            try {
                HOLDER_INSTANCE = new DbHandlerRecipe();
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
    }
    public static DbHandlerRecipe getInstance() {
        return SingletonHolder.HOLDER_INSTANCE;
    }
    ////Синглтон

    private DbHandlerRecipe() throws SQLException {
        DriverManager.registerDriver(new JDBC());
        connection = DriverManager.getConnection(CONNECTION_PATH);
    }

    public Map<Integer, Recipe> getALLRecipes() {
        try (Statement statement = connection.createStatement()) {
            Map<Integer, Recipe> recipesDictonary = new HashMap<>();

            ResultSet resultSet = statement.executeQuery("SELECT id, title, description, " +
                    "url_photo, ingredients FROM Recipes");

            while (resultSet.next()) {
                ArrayList<String> ingredients_from_bd =
                        new ArrayList<>(Arrays.asList(resultSet.getString("title").split(",")));

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

    public void addRecipe(Recipe recipe)  {
        String ingredients_to_bd = String.join(",", recipe.getIngredients());

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
                "DELETE FROM Recipes WHERE id = ?")) {
            statement.setObject(1, id);

            statement.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

}
