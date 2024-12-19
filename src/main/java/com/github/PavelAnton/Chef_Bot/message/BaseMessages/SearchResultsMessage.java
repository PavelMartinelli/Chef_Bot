package com.github.PavelAnton.Chef_Bot.message.BaseMessages;

import com.github.PavelAnton.Chef_Bot.recipe.Recipe;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardRow;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;

import java.util.ArrayList;
import java.util.List;

public class SearchResultsMessage extends BaseMessage {

    private final List<Recipe> searchResults;
    private final int recipesPerPage = 6;
    private final int currentPage;

    public SearchResultsMessage(List<Recipe> searchResults, int currentPage) {
        this.searchResults = searchResults;
        this.currentPage = currentPage;
    }

    @Override
    protected String getMessageText() {
        if (searchResults.isEmpty()) {
            return "❌ Рецепты не найдены. Попробуйте другой запрос:";
        }
        return "🔍 Найдено совпадений: " + searchResults.size() + "\nВыберите рецепт из списка ниже:";
    }

    @Override
    protected List<InlineKeyboardRow> createKeyboardRows() {
        List<InlineKeyboardRow> rows = new ArrayList<>();

        int start = currentPage * recipesPerPage;
        int end = Math.min(start + recipesPerPage, searchResults.size());

        List<Recipe> currentPageRecipes = searchResults.subList(start, end);
        for (Recipe recipe : currentPageRecipes) {
            rows.add(createRow( recipe.getTitle(), "/view_recipe$" + recipe.getId()));
        }

        if (searchResults.size() > recipesPerPage) {
            InlineKeyboardRow navigationRow = new InlineKeyboardRow();
            if (currentPage > 0) {
                InlineKeyboardButton button = InlineKeyboardButton.builder()
                        .text("◀️ Пред. страница")
                        .callbackData(("/search_page$" + (currentPage - 1)))
                        .build();
                navigationRow.add(button);
            }
            if (end < searchResults.size()) {
                InlineKeyboardButton button = InlineKeyboardButton.builder()
                        .text("След. страница ▶️")
                        .callbackData("/search_page$" + (currentPage + 1))
                        .build();
                navigationRow.add(button);
            }
            rows.add(navigationRow);
        }

        rows.add(createRow("🏠 На главное меню", "/back"));
        return rows;
    }
}