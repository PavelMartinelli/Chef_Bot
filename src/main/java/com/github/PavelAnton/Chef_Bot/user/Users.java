package com.github.PavelAnton.Chef_Bot.user;

import com.github.PavelAnton.Chef_Bot.dataBaseHandlers.DbHandlerUser;
import java.util.Map;

public class Users {
    private final Map<Long, User> usersDictonary;
    private final DbHandlerUser dbHandlerUser = DbHandlerUser.getInstance();

    public Users(Map<Long, User> usersDictonary) { // Конструктор без БД
        this.usersDictonary = usersDictonary;
    }
    public Users(){ // Конструктор через БД
        this.usersDictonary = dbHandlerUser.getALL();
    }

    public User getUser(Long userId) {
        return usersDictonary.get(userId);
    }

    public boolean isUserNotInUsers(Long userId){
        return !usersDictonary.containsKey(userId);
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
