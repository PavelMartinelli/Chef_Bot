import java.util.HashMap;
import java.util.Map;

public class Users {
    private Map<Long,UserState> usersDictonary = new HashMap<Long,UserState>();

    Users(Map<Long,UserState> usersDictonary) {
        this.usersDictonary = usersDictonary;
    }

    public void addUser(UserState user) {
        usersDictonary.put(user.getId(), user);

        //TO DO: Сделать запись пользователй в базу данных
    }

    //TO DO: Сделать загрузку с базы данных списка пользоватенлей в Словарь при запуске бота
}
