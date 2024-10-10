import java.util.ArrayList;
import java.util.Arrays;

public class User {
    private final Long id;
    private String userName;
    private String password;
    private String state;
    private ArrayList<Recipe> favoriteRecipes;
    private ArrayList<Recipe> createdRecipes;

    public User(Long id, String userName, String password) {
        this.id = id;
        this.userName = userName;
        this.password = password;
        state = "START";
    }
    public enum States {
        START,
        UNREGISTERED,
        LOGIN,
        AWAITING_NAME,
        AWAITING_PASSWORD,
        AWAITING_RECIPE_NAME,
        AWAITING_RECIPE_INGREDIENTS,
        COMPLETED;
        private static boolean isEnumContains(String str) {
            // Проверка есть ли элемент в перечеслении за одну строку кода
            return Arrays.stream(States.values()).anyMatch(e -> e.name().equals(str));
        }
    }

    public void setState(String state) {
        if(States.isEnumContains(state)) {
            this.state = state;
        }
        else
            this.state = "UNDEFINED";
    }

    public String getState() {
        return state;
    }


    public Long getId() {
        return id;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }


}
