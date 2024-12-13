package Message.BaseMessages;

import Recipe.Recipe;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardRow;

import java.util.ArrayList;
import java.util.List;

public class SearchResultsMessage extends BaseMessage {

    private final List<Recipe> searchResults;

    public SearchResultsMessage(List<Recipe> searchResults) {
        this.searchResults = searchResults;
    }

    @Override
    protected String getMessageText() {
        if (searchResults.isEmpty()) {
            return "Рецепты не найдены. Попробуйте другой запрос.";
        }
        return "Найдено совпадений: " + searchResults.size() + "\nВыберите рецепт из списка ниже:";
    }

    @Override
    protected List<InlineKeyboardRow> createKeyboardRows() {
        List<InlineKeyboardRow> rows = new ArrayList<>();
        for (Recipe recipe : searchResults) {
            rows.add(createRow(recipe.getTitle(), "view_recipe$" + recipe.getId()));
        }
        rows.add(createRow("На главное меню", "/back"));
        return rows;
    }
}