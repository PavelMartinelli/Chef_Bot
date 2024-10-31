import Message.*;

import Recipe.*;
import User.*;
import org.telegram.telegrambots.client.okhttp.OkHttpTelegramClient;
import org.telegram.telegrambots.longpolling.util.LongPollingSingleThreadUpdateConsumer;
import org.telegram.telegrambots.meta.api.methods.send.SendPhoto;
import org.telegram.telegrambots.meta.api.methods.updatingmessages.EditMessageText;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.meta.generics.TelegramClient;

public class Bot implements LongPollingSingleThreadUpdateConsumer {

    private final TelegramClient telegramClient;
    private final MenuMessage menuContent = new MenuMessage();
    private final RandomMessage randomContent= new RandomMessage();
    private final CatalogMessage catalogContent= new CatalogMessage();
    private final WishlistMessage wishlistContent= new WishlistMessage();
    private final HelpMessage helpContent = new HelpMessage();

    private final Recipes recipes = new Recipes();
    //private final Users users = new Users()


    public Bot(String botToken) {
        telegramClient = new OkHttpTelegramClient(botToken);
    }

    @Override
    public void consume(Update update) {
        System.out.println(update);
        if (update.hasMessage() && update.getMessage().hasText()) {
            handleTextMessage(update);
        } else if (update.hasCallbackQuery()) {
            handleCallbackQuery(update);
        }
    }


    private void handleTextMessage(Update update) {

        long chat_id = update.getMessage().getChatId();
        String messageText = update.getMessage().getText();

        switch (messageText) {
            case "/start":
                try {
                    telegramClient.execute(menuContent.createMessage(chat_id));
                } catch (TelegramApiException e) {
                    e.printStackTrace();
                }
                break;
            case "/random":
                try {
                    telegramClient.execute(randomContent.createMessage(chat_id));
                } catch (TelegramApiException e) {
                    e.printStackTrace();
                }
                break;
            case "/catalog":
                try {
                    telegramClient.execute(catalogContent.createMessage(chat_id));
                } catch (TelegramApiException e) {
                    e.printStackTrace();
                }
                break;
            case "/wishlist":
                try {
                    telegramClient.execute(wishlistContent.createMessage(chat_id));
                } catch (TelegramApiException e) {
                    e.printStackTrace();
                }
                break;
            case "/help":
                try {
                    telegramClient.execute(helpContent.createMessage(chat_id));
                } catch (TelegramApiException e) {
                    e.printStackTrace();
                }
                break;
        }
    }

    private void handleCallbackQuery(Update update) {
        String callData = update.getCallbackQuery().getData();
        long chatId = update.getCallbackQuery().getMessage().getChatId();
        int messageId = update.getCallbackQuery().getMessage().getMessageId();

        switch (callData) {
            case "/search":
                sendResponse(chatId, messageId, "Поиск блюд, напитков. Вы нажали на первую кнопку.");
                break;
            case "/random":
                execute(randomContent.createEditMessage(chatId, messageId));
                execute(recipes.getRandomRecipe().createRecipeMessage(chatId));
                break;
            case "/catalog":
                execute(catalogContent.createEditMessage(chatId, messageId));
                execute(recipes.getRecipe(1).createRecipeMessage(chatId));
                break;
            case "/wishlist":
                execute(wishlistContent.createEditMessage(chatId, messageId));
                break;
            case "/help":
                execute(helpContent.createEditMessage(chatId, messageId));
                break;
            case "/back":
                execute(menuContent.createEditMessage(chatId, messageId));
                break;
            default:
                sendResponse(chatId, messageId, "Неверный запрос");
        }
    }
    private void execute(EditMessageText message) {
        try {
            telegramClient.execute(message);
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }
    private void execute(SendPhoto message) {
        try {
            telegramClient.execute(message);
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }

    private void sendResponse(long chatId, int messageId, String messageText) {
        EditMessageText newMessage = EditMessageText.builder()
                .chatId(String.valueOf(chatId))
                .text(messageText)
                .messageId(messageId >= 0 ? messageId : 0)
                .build();

        execute(newMessage);
    }

}