import java.util.HashMap;
import java.util.Map;

public class Recipes {
    private Map<Integer, Recipe> recipesDictonary = new HashMap<>();
    private final DbHandlerRecipe dbHandlerRecipe = DbHandlerRecipe.getInstance();

    Recipes(Map<Integer, Recipe> recipesDictonary) { //Конструктор без БД
        this.recipesDictonary = recipesDictonary;
    }

    Recipes()  { // Конструктор с БД
        this.recipesDictonary = dbHandlerRecipe.getALLRecipes();
    }

    public Recipe getRecipe(Integer id) {
        return recipesDictonary.get(id);
    }

    public void addRecipe(Recipe recipe)  {
        recipesDictonary.put(recipe.getId(), recipe);
        dbHandlerRecipe.addRecipe(recipe);
    }

}
