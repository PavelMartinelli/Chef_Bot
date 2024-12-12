package Message.Wishlist;

import Message.BaseMessage;
import Recipe.Recipe;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardRow;

import java.util.ArrayList;
import java.util.List;

public class WishlistMessage extends BaseMessage {

    private final List<Recipe> wishlistResults;

    public WishlistMessage(List<Recipe> wishlistResults) {
        this.wishlistResults = wishlistResults;
    }

    @Override
    protected String getMessageText() {
        if (wishlistResults.isEmpty()) {
            return "Рецепты не найдены. Вы пока ничего не добавили в избранное.";
        }
        return "Ваши избранные рецепты ниже:";
    }

    @Override
    protected List<InlineKeyboardRow> createKeyboardRows() {
        List<InlineKeyboardRow> rows = new ArrayList<>();
        for (Recipe recipe : wishlistResults) {
            rows.add(createRow(recipe.getTitle(), "view_recipe$" + recipe.getId()));
        }
        rows.add(createRow("Вернуться в главное меню", "/back"));
        return rows;
    }
}