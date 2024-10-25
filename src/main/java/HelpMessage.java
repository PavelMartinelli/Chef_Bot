import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardRow;

import java.util.ArrayList;
import java.util.List;

public class HelpMessage {

    public SendMessage createHelpMessage(long chatId) {
        String mainMenuText = " Это бот для приготовление всяких блюд.\n" + "\n" + "Информация по всем функциям бота доступна по команде /start\n" + "\n" + "ℹ Подробная инструкция будет потом\n";
        return SendMessage.builder()
                .chatId(chatId)
                .text(mainMenuText)
                .replyMarkup(createKeyboard())
                .build();
    }

    private InlineKeyboardMarkup createKeyboard() {
        List<InlineKeyboardRow> rows = new ArrayList<>();

        rows.add(createRow("На главное меню", "/back"));

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