package com.github.PavelAnton.Chef_Bot.message.BaseMessages;

import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardRow;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;

import java.util.ArrayList;
import java.util.List;

public class SearchIngredientsMessage extends BaseMessage {
    private final List<String> ingredients;
    private final List<String> selectedIngredients;
    private final int ingredientsPerPage = 6;
    private final int currentPage;

    public SearchIngredientsMessage(List<String> ingredients, List<String> selectedIngredients, int currentPage) {
        this.ingredients = ingredients;
        this.selectedIngredients = selectedIngredients;
        this.currentPage = currentPage;
    }

    @Override
    protected String getMessageText() {
        return "Выберите ингредиенты из списка. Нажмите на нужный, чтобы добавить или убрать.";
    }

    @Override
    protected List<InlineKeyboardRow> createKeyboardRows() {
        List<InlineKeyboardRow> rows = new ArrayList<>();

        int start = currentPage * ingredientsPerPage;
        int end = Math.min(start + ingredientsPerPage, ingredients.size());
        List<String> currentPageIngredients = ingredients.subList(start, end);

        for (String ingredient : currentPageIngredients) {
            String buttonText = selectedIngredients.contains(ingredient)
                    ? ingredient + " ✅"
                    : ingredient;
            rows.add(createRow(buttonText, "/toggle_ingredient$" + ingredient));
        }

        InlineKeyboardRow navigationRow = new InlineKeyboardRow();
        if (currentPage > 0) {
            navigationRow.add(InlineKeyboardButton.builder()
                    .text("◀️ Пред.")
                    .callbackData("/ingredients_page$" + (currentPage - 1))
                    .build());
        }
        if (end < ingredients.size()) {
            navigationRow.add(InlineKeyboardButton.builder()
                    .text("След. ▶️")
                    .callbackData("/ingredients_page$" + (currentPage + 1))
                    .build());
        }
        rows.add(navigationRow);

        rows.add(createRow("🔍 Найти рецепты", "/search_recipes"));
        rows.add(createRow("🏠 На главное меню", "/back"));
        return rows;
    }
}
