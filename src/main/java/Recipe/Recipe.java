package Recipe;

import org.telegram.telegrambots.meta.api.methods.send.SendPhoto;
import org.telegram.telegrambots.meta.api.objects.InputFile;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardRow;

import java.util.ArrayList;
import java.util.List;


public class Recipe {
    private final Integer id;
    private String title;
    private String description;
    private String url_photo;
    private ArrayList<String> ingredients;

    public Recipe(Integer id, String title, String description, String url_photo, ArrayList<String> ingredients) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.url_photo = url_photo;
        this.ingredients = ingredients;
    }

    public Recipe(String title, String description, String url_photo, ArrayList<String> ingredients) {
        this.id = title.hashCode();
        this.title = title;
        this.description = description;
        this.url_photo = url_photo;
        this.ingredients = ingredients;
    }

    public Integer getId() {
        return id;
    }

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
