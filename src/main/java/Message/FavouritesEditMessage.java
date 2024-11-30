package Message;

import org.telegram.telegrambots.meta.api.methods.updatingmessages.EditMessageCaption;
import org.telegram.telegrambots.meta.api.methods.updatingmessages.EditMessageMedia;
import org.telegram.telegrambots.meta.api.methods.updatingmessages.EditMessageText;

public class FavouritesEditMessage {
    private final String favourite_add = "Рецепт добавлен в избранное!";
    private final String favourite_remove = "Рецепт удален из избранного :(";

    public EditMessageCaption editRecipeCard(long chatId, int messageId, boolean isRemove){
        if (isRemove){
            return removeMessage(chatId, messageId);
        }
        else {
            return addMessage(chatId, messageId);
        }
    }

    private EditMessageCaption addMessage(long chatId, int messageId) {
        return EditMessageCaption.builder()
                .chatId(String.valueOf(chatId))
                .messageId(messageId)
                .caption(favourite_add)
                .build();
    }


    private EditMessageCaption removeMessage(long chatId, int messageId) {
        return EditMessageCaption.builder()
                .chatId(String.valueOf(chatId))
                .messageId(messageId)
                .caption(favourite_remove)
                .build();
    }
}
