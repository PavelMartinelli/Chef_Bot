package User;


import DataBaseHandlers.DbHandlerUser;

import java.util.ArrayList;

public class User {
    private final Long id;
    private String userName;
    private String password;
    private States state;
    private ArrayList<Integer> idFavoritesRecipe;

    private final DbHandlerUser dbHandlerUser = DbHandlerUser.getInstance();

    public User(Long id, String userName, String password) {
        this.id = id;
        this.userName = userName;
        this.password = password;
        state = States.START;
        idFavoritesRecipe = null;
    }

    public User(Long id, String userName, String password, ArrayList<Integer> idFavoritesRecipe) {
        this.id = id;
        this.userName = userName;
        this.password = password;
        state = States.START;
        this.idFavoritesRecipe = idFavoritesRecipe;
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
    public void setState(States state) {
        this.state = state;
    }

    public States getState() {
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

    public ArrayList<Integer> getIdFavoritesRecipe() {
        return idFavoritesRecipe;
    }

    public void addFavoritesRecipe(Integer recipeId){
        if (idFavoritesRecipe.contains(recipeId)){
            return;
        }
        if (idFavoritesRecipe == null) {
            idFavoritesRecipe = new ArrayList<>();
        }
        idFavoritesRecipe.add(recipeId);
        dbHandlerUser.updateUserFavorites(id, idFavoritesRecipe);
    }

    public void removeFavoritesRecipe(Integer recipeId){
        idFavoritesRecipe.remove(recipeId);
        dbHandlerUser.updateUserFavorites(id, idFavoritesRecipe);
    }

}
