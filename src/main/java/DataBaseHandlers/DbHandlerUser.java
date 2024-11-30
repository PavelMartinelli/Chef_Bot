package DataBaseHandlers;

import User.*;
import org.sqlite.JDBC;

import java.sql.*;
import java.util.*;
import java.util.stream.Collectors;

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
            Map<Long, User> userDictonary = new HashMap<>();

            ResultSet resultSet = statement.executeQuery("SELECT id, userName, password, idFavoritesRecipe FROM Users");

            while (resultSet.next()) {
                Long id = resultSet.getLong("id");
                String userName = resultSet.getString("userName");
                String password = resultSet.getString("password");

                if (resultSet.getString("idFavoritesRecipe") == null){
                    User user = new User(id, userName, password, null);
                    userDictonary.put(id, user);
                }
                else {
                    ArrayList<String> idRecipes_from_bd_str =
                            new ArrayList<>(Arrays.asList(resultSet.getString("idFavoritesRecipe").split(",")));
                    ArrayList<Integer> idRecipes_from_bd_int = new ArrayList<>();

                    for (String str : idRecipes_from_bd_str) {
                        idRecipes_from_bd_int.add(Integer.parseInt(str));
                    }

                    User user = new User(id, userName, password, idRecipes_from_bd_int);
                    userDictonary.put(id, user);
                }

            }

            return userDictonary;

        } catch (SQLException e) {
            e.printStackTrace();
            // Если произошла ошибка - возвращаем пустой словарь
            return Collections.emptyMap();
        }
    }
    public void add(User user)  {


        try (PreparedStatement statement = connection.prepareStatement(
                "INSERT INTO Users(`id`, `userName`, `password`, `idFavoritesRecipe`) " +
                        "VALUES(?, ?, ?, ?)")) {
            statement.setObject(1, user.getId());
            statement.setObject(2, user.getUserName());
            statement.setObject(3, user.getPassword());

            String favoritesRecipeIds = user.getIdFavoritesRecipe().stream()
                    .map(String::valueOf)
                    .collect(Collectors.joining(","));

            statement.setString(4, favoritesRecipeIds);
            statement.execute();

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

    //TO DO Функия обновления избраного у пользователя по его id
    public void updateUserFavorites(Long userId, ArrayList<Integer> newFavoritesRecipeList) {
        try {
            String updatedFavoritesRecipeIds = newFavoritesRecipeList.stream()
                    .map(String::valueOf)
                    .collect(Collectors.joining(","));

            String updateQuery = "UPDATE Users SET idFavoritesRecipe = ? WHERE id = ?";
            try (PreparedStatement updateStatement = connection.prepareStatement(updateQuery)) {
                updateStatement.setString(1, updatedFavoritesRecipeIds);
                updateStatement.setLong(2, userId);
                updateStatement.executeUpdate();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
