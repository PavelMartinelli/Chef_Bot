import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardRow;

import java.util.ArrayList;
import java.util.List;

public class MenuMessage {

    public SendMessage createMainMenuMessage(long chatId) {
        String mainMenuText = "✋ Добро пожаловать в главное меню бота Mix & Cook! Для просмотра рецептов воспользуйтесь кнопками ниже:";
        return SendMessage.builder()
                .chatId(chatId)
                .text(mainMenuText)
                .replyMarkup(createKeyboard())
                .build();
    }

    private  InlineKeyboardMarkup createKeyboard() {
        List<InlineKeyboardRow> rows = new ArrayList<>();

        rows.add(createRow("Поиск блюд, напитков", "/search"));
        rows.add(createRow("Случайный рецепт", "/random"));
        rows.add(createRow("Избранное", "/wishlist"));
        rows.add(createRow("Каталог", "/catalog"));
        rows.add(createRow("Справка", "/help"));

        return InlineKeyboardMarkup.builder().keyboard(rows).build();
    }

    private InlineKeyboardRow createRow(String buttonText, String callbackData) {
        InlineKeyboardButton button = InlineKeyboardButton.builder()
                .text(buttonText)
                .callbackData(callbackData)
                .build();
        InlineKeyboardRow row = new InlineKeyboardRow();
        row.add(button);
        return row;
    }

}