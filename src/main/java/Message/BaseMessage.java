package Message;

import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.methods.send.SendPhoto;
import org.telegram.telegrambots.meta.api.methods.updatingmessages.EditMessageText;
import org.telegram.telegrambots.meta.api.methods.updatingmessages.EditMessageMedia;
import org.telegram.telegrambots.meta.api.objects.InputFile;
import org.telegram.telegrambots.meta.api.objects.media.InputMediaPhoto;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardRow;

import java.util.List;

public abstract class BaseMessage {
    protected abstract String getMessageText();
    protected abstract List<InlineKeyboardRow> createKeyboardRows();
    protected String getPhotoUrl() {
        return null;
    }

    public SendMessage createMessage(long chatId) {
        return SendMessage.builder()
                .chatId(chatId)
                .text(getMessageText())
                .replyMarkup(createKeyboard())
                .build();
    }

    public SendPhoto createMessageWithPhoto(long chatId) {
        return SendPhoto.builder()
                .chatId(String.valueOf(chatId))
                .photo(new InputFile(getPhotoUrl()))
                .caption(getMessageText())
                .parseMode("HTML")
                .replyMarkup(createKeyboard())
                .build();
    }

    public EditMessageText createEditMessage(long chatId, int messageId) {
        return EditMessageText.builder()
                .chatId(String.valueOf(chatId))
                .messageId(messageId)
                .text(getMessageText())
                .replyMarkup(createKeyboard())
                .build();
    }

    public EditMessageMedia createEditMessageWithPhoto(long chatId, int messageId) {
        return EditMessageMedia.builder()
                .chatId(String.valueOf(chatId))
                .messageId(messageId)
                .media(InputMediaPhoto.builder()
                        .media(getPhotoUrl())
                        .caption(getMessageText())
                        .build())
                .replyMarkup(createKeyboard())
                .build();
    }

    private InlineKeyboardMarkup createKeyboard() {
        return InlineKeyboardMarkup.builder().keyboard(createKeyboardRows()).build();
    }

    protected InlineKeyboardRow createRow(String buttonText, String callbackData) {
        InlineKeyboardButton button = InlineKeyboardButton.builder()
                .text(buttonText)
                .callbackData(callbackData)
                .build();
        InlineKeyboardRow row = new InlineKeyboardRow();
        row.add(button);
        return row;
    }
}
