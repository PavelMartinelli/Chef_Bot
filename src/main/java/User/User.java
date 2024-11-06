package User;


public class User {
    private final Long id;
    private String userName;
    private String password;
    private String state;

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
        AWAITING_SEARCH_RECIPE_NAME,
        AWAITING_SEARCH_RECIPE_INGREDIENTS,
        AWAITING_NEW_RECIPE_NAME,
        AWAITING_NEW_RECIPE_NAME_DESCRIPTION,
        AWAITING_NEW_RECIPE_NAME_PHOTO,
        AWAITING_NEW_RECIPE_INGREDIENTS,
        COMPLETED,
        UNDEFINED
    }

    public void setState(String state) {
        this.state = state;
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
