package User;

import DataBaseHandlers.DbHandlerUser;
import java.util.HashMap;
import java.util.Map;

public class Users {
    private Map<Long, User> usersDictonary = new HashMap<Long, User>();
    private final DbHandlerUser dbHandlerUser = DbHandlerUser.getInstance();

    Users(Map<Long, User> usersDictonary) { // Конструктор без БД
        this.usersDictonary = usersDictonary;
    }
    Users(){ // Конструктор через БД
        this.usersDictonary = dbHandlerUser.getALL();
    }

    public User getUser(Long userId) {
        return usersDictonary.get(userId);
    }

    public void deleteUser(Long userId) {
        usersDictonary.remove(userId);

        dbHandlerUser.delete(userId);
    }

    public void addUser(User user) {
        usersDictonary.put(user.getId(), user);

        dbHandlerUser.add(user);
    }
}
