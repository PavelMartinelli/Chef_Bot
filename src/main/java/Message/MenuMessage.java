package Message;

import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardRow;

import java.util.ArrayList;
import java.util.List;

public class MenuMessage extends BaseMessage {
    @Override
    protected String getMessageText() {
        return "✋ Добро пожаловать в главное меню бота Mix & Cook! Для просмотра рецептов воспользуйтесь кнопками ниже:";
    }

    @Override
    protected List<InlineKeyboardRow> createKeyboardRows() {
        List<InlineKeyboardRow> rows = new ArrayList<>();
        rows.add(createRow("Поиск блюд, напитков", "/search"));
        rows.add(createRow("Случайный рецепт", "/random"));
        rows.add(createRow("Избранное", "/wishlist"));
        rows.add(createRow("Каталог", "/catalog"));
        rows.add(createRow("Добавить рецепт", "/add"));
        rows.add(createRow("Справка", "/help"));
        return rows;
    }
}