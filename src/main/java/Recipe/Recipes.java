package Recipe;

import DataBaseHandlers.DbHandlerRecipe;
import java.util.Random;

import java.util.Map;

public class Recipes {
    private final Map<Integer, Recipe> recipesDictonary;
    private final DbHandlerRecipe dbHandlerRecipe = DbHandlerRecipe.getInstance();

    public Recipes(Map<Integer, Recipe> recipesDictonary) { //Конструктор без БД
        this.recipesDictonary = recipesDictonary;
    }

    public Recipes()  { // Конструктор с БД
        this.recipesDictonary = dbHandlerRecipe.getALL();
    }

    public Recipe getRecipe(Integer id) {
        return recipesDictonary.get(id);
    }

    public Recipe getRandomRecipe() {
        Random generator = new Random();
        Object[] Keys = recipesDictonary.keySet().toArray();
        Object randomKey = Keys[generator.nextInt(Keys.length)];

        return recipesDictonary.get(randomKey);
    }

    public void addRecipe(Recipe recipe)  {
        recipesDictonary.put(recipe.getId(), recipe);
        dbHandlerRecipe.add(recipe);
    }

    public void deleteRecipe(Integer id)  {
        recipesDictonary.remove(id);

        dbHandlerRecipe.delete(id);
    }

}
