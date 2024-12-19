package com.github.PavelAnton.Chef_Bot.recipe;

import com.github.PavelAnton.Chef_Bot.dataBaseHandlers.DbHandlerRecipe;

import java.util.Random;

import java.util.Map;
import java.util.ArrayList;
import java.util.List;

public class Recipes {
    private final Map<Integer, Recipe> recipesDictonary;
    private final DbHandlerRecipe dbHandlerRecipe = DbHandlerRecipe.getInstance();

    public Recipes() { // Конструктор с БД
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


    public List<String> getAllIngredients() {
        return List.of(
                "лук",
                "томаты",
                "чеснок",
                "креветки",
                "сыр",
                "лимон",
                "огурцы"
        );
    }

    public List<Recipe> searchRecipes(String query) {
        List<Recipe> results = new ArrayList<>();

        for (Recipe recipe : recipesDictonary.values()) {
            if (recipe.getTitle().toLowerCase().contains(query.toLowerCase())) {
                results.add(recipe);
            }
        }
        return results;
    }

    public List<Recipe> searchRecipesByIngredients(List<String> ingredients) {
        List<Recipe> results = new ArrayList<>();

        for (Recipe recipe : recipesDictonary.values()) {
            if (ingredients.stream()
                    .allMatch(ingredient -> recipe.getIngredients().stream()
                            .anyMatch(recipeIngredient -> recipeIngredient.contains(ingredient)))) {
                results.add(recipe);
            }
        }

        return results;
    }

}
