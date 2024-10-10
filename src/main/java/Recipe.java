import java.util.ArrayList;

public class Recipe {
    private String title;
    private String description;
    private String url_photo;
    private ArrayList<String> ingredients;

    public Recipe(String title, String description, String url_photo, ArrayList<String> ingredients) {
        this.title = title;
        this.description = description;
        this.url_photo = url_photo;
        this.ingredients = ingredients;
    }

    public void SendRecipe() {

    }

    //TO DO Закрузка рецептов из базы данных

    //TO DO Запись рецептов в базу данных

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getUrl_photo() {
        return url_photo;
    }

    public void setUrl_photo(String url_photo) {
        this.url_photo = url_photo;
    }

    public ArrayList<String> getIngredients() {
        return ingredients;
    }

    public void setIngredients(ArrayList<String> ingredients) {
        this.ingredients = ingredients;
    }
}
