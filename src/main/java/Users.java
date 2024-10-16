import java.util.HashMap;
import java.util.Map;

public class Users {
    private Map<Long, User> usersDictonary = new HashMap<Long, User>();

    Users(Map<Long, User> usersDictonary) {
        this.usersDictonary = usersDictonary;
    }

    public User getUser(Long userId) {
        return usersDictonary.get(userId);
    }
    public void dellUser(Long userId) {
        usersDictonary.remove(userId);

        //TO DO: Сделать удаление пользователя из базы данных
    }

    public void addUser(User user) {
        usersDictonary.put(user.getId(), user);

        //TO DO: Сделать запись пользователй в базу данных
    }

    //TO DO: Сделать загрузку с базы данных списка пользоватенлей в Словарь при запуске бота
}
