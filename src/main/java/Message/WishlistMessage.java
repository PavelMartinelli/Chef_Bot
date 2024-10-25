package Message;

import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.methods.updatingmessages.EditMessageText;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardRow;

import java.util.ArrayList;
import java.util.List;

public class WishlistMessage {
    String WishlistText = "Вы попали в избранное. Пока в разработке.";

    public SendMessage createMessage(long chatId) {
        return SendMessage.builder()
                .chatId(chatId)
                .text(WishlistText)
                .replyMarkup(createKeyboard())
                .build();
    }

    public EditMessageText createEditMessage(long chatId, int messageId) {
        return EditMessageText.builder()
                .chatId(String.valueOf(chatId))
                .messageId(messageId)
                .text(WishlistText)
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