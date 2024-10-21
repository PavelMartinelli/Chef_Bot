import java.util.HashMap;
import java.util.Map;

public class Recipes {
    private Map<Integer, Recipe> recipesDictonary = new HashMap<Integer, Recipe>();

    Recipes(Map<Integer, Recipe> recipesDictonary) {
        this.recipesDictonary = recipesDictonary;
    }

    public Recipe getRecipe(Integer id) {
        return recipesDictonary.get(id);
    }

    public void addRecipe(Recipe recipe) {
        recipesDictonary.put(recipe.getId(), recipe);

        //TO DO: Сделать запись в базу данных
    }

    //TO DO: Сделать загрузку bp базы данных списка рецептов в Словарь при запуске бота
}
