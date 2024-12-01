package Message;

import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.methods.updatingmessages.EditMessageText;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardRow;

import java.util.ArrayList;
import java.util.List;

public class AddRecipeMessages {
    private final String recipeNameMessage = "Хотите добавить свой рецепт? \n " +
            "Тогда введите название вашего рецепта на Русском языке";

    private final String recipeDescriptionMessage = "Напишите описание вашего рецепта и/или \n " +
            "Этапы приготовления и/или \n " +
            "Ваши советы по приготовлению рецепта";

    private final String recipeImageMessage = "Отправте фото с вашим рецептом или URL ссылку на картинку";

    private final String recipeIngredientsMessage = "Напишите списком через запятую основные ингредиенты вашего рецепта";

    public EditMessageText addNameMessages(long chatId, int messageId){
        List<InlineKeyboardRow> rows = new ArrayList<>();

        InlineKeyboardButton button = InlineKeyboardButton.builder()
                .text("На главное меню")
                .callbackData("/back")
                .build();
        InlineKeyboardRow row = new InlineKeyboardRow();
        row.add(button);
        rows.add(row);

        InlineKeyboardMarkup backButton =  InlineKeyboardMarkup.builder().keyboard(rows).build();

        return EditMessageText.builder()
                .chatId(String.valueOf(chatId))
                .messageId(messageId)
                .text(recipeNameMessage)
                .replyMarkup(backButton)
                .build();
    }

    public SendMessage addDescriptionMessages(long chatId, int messageId){
        return SendMessage.builder()
                .chatId(String.valueOf(chatId)).
                text(recipeDescriptionMessage).
                build();
    }

    public SendMessage addRecipeImageMessage(long chatId, int messageId){
        return SendMessage.builder()
                .chatId(String.valueOf(chatId)).
                text(recipeImageMessage).
                build();
    }
    public SendMessage addRecipeIngredientsMessage(long chatId, int messageId){
        return SendMessage.builder()
                .chatId(String.valueOf(chatId)).
                text(recipeIngredientsMessage).
                build();
    }
}
