package User;
import Recipe.*;
import DataBaseHandlers.DbHandlerUser;

import java.util.ArrayList;
import java.util.List;

public class User {
    private final Long id;
    private String userName;
    private String password;
    private States state;
    private String searchQuery;
    private List<String> selectedIngredients;
    private ArrayList<Integer> idFavoritesRecipe;

    private final DbHandlerUser dbHandlerUser = DbHandlerUser.getInstance();

    public User(Long id, String userName, String password) {
        this.id = id;
        this.userName = userName;
        this.password = password;
        state = States.START;
        idFavoritesRecipe = new ArrayList<>();
    }

    public User(Long id, String userName, String password, ArrayList<Integer> idFavoritesRecipe) {
        this.id = id;
        this.userName = userName;
        this.password = password;
        state = States.START;
        searchQuery = "";
        selectedIngredients = new ArrayList<>();
        this.idFavoritesRecipe = idFavoritesRecipe;
    }


    public enum States {
        START,
        AWAITING_SEARCH_RECIPE_NAME,
        AWAITING_SEARCH_RECIPE_INGREDIENTS,
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

    public List<Recipe> getFavoriteRecipes(Recipes recipes) {
        List<Recipe> favoriteRecipes = new ArrayList<>();
        for (int recipeId : idFavoritesRecipe) {
            Recipe recipe = recipes.getRecipe(recipeId);
            favoriteRecipes.add(recipe);

        }
        return favoriteRecipes;
    }

    public String getSearchQuery() {
        return searchQuery;
    }

    public void setSearchQuery(String searchQuery) {
        this.searchQuery = searchQuery != null ? searchQuery : "";
    }

    public List<String> getSelectedIngredients() {
        return selectedIngredients;
    }

    public void setSelectedIngredients(List<String> selectedIngredients) {
        this.selectedIngredients = selectedIngredients != null ? selectedIngredients : new ArrayList<>();
    }

    public boolean isRecipeInFavorites(Recipe recipe) {
        return idFavoritesRecipe.contains(recipe.getId());
    }

    public void addFavoritesRecipe(Integer recipeId){
        if (idFavoritesRecipe == null) {
            idFavoritesRecipe = new ArrayList<>();
        }

        if (idFavoritesRecipe.contains(recipeId)){
            return;
        }
        idFavoritesRecipe.add(recipeId);
        dbHandlerUser.updateUserFavorites(id, idFavoritesRecipe);
    }

    public void removeFavoritesRecipe(Integer recipeId){
        idFavoritesRecipe.remove(recipeId);
        dbHandlerUser.updateUserFavorites(id, idFavoritesRecipe);
    }

}
