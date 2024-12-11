package Message;

import Recipe.Recipe;

import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardRow;

import java.util.ArrayList;
import java.util.List;

public class RecipeMessage extends BaseMessage {
    private final Recipe recipe;
    private final boolean isInFavourites;

    public RecipeMessage(Recipe recipe, boolean isInFavourites) {
        this.recipe = recipe;
        this.isInFavourites = isInFavourites;
    }

    @Override
    protected String getMessageText() {
        return "<b>" + recipe.getTitle() + "</b>\n\n" +
                recipe.getDescription() + "\n\n" +
                "Ингредиенты:\n" + String.join("\n", recipe.getIngredients());
    }

    @Override
    protected String getPhotoUrl() {
        return recipe.getUrl_photo();
    }

    @Override
    protected List<InlineKeyboardRow> createKeyboardRows() {
        List<InlineKeyboardRow> rows = new ArrayList<>();
        rows.add(createRow(
                isInFavourites ? "Удалить из избранного" : "Добавить в избранное",
                (isInFavourites ? "/del_favourites$" : "/add_favourites$") + recipe.getId()
        ));
        rows.add(createRow("На главное меню", "/back"));
        return rows;
    }
}
