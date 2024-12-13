import Message.Recipe.RecipeMessage;
import Recipe.*;
import User.*;
import Message.*;
import Message.MenuMessage;
import Message.Search.*;

import org.telegram.telegrambots.client.okhttp.OkHttpTelegramClient;
import org.telegram.telegrambots.longpolling.util.LongPollingSingleThreadUpdateConsumer;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.meta.generics.TelegramClient;


public class Bot implements LongPollingSingleThreadUpdateConsumer {

    private final Recipes recipes;
    private final Users users;
    private final TelegramClient telegramClient;

    public Bot(String botToken) {

        this.telegramClient = new OkHttpTelegramClient(botToken);
        this.recipes = new Recipes();
        this.users = new Users();

    }

    @Override
    public void consume(Update update) {

        if (update.hasMessage() && update.getMessage().hasText()) {
            handleTextMessage(update);
        } else if (update.hasCallbackQuery()) {
            handleCallbackQuery(update);
        }

    }

    private void sendMessage(BaseMessage message, long chatId) {
        try {
            if (message.getPhotoUrlCheck() != null) {
                telegramClient.execute(message.createMessageWithPhoto(chatId));
            } else {
                telegramClient.execute(message.createMessage(chatId));
            }
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }

    private void editMessage(BaseMessage message, long chatId, int messageId) {
        try {
            if (message.getPhotoUrlCheck() != null) {
                telegramClient.execute(message.createEditMessageWithPhoto(chatId, messageId));
            } else {
                telegramClient.execute(message.createEditMessage(chatId, messageId));
            }
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }


    private void handleTextMessage(Update update) {

        long chatId = update.getMessage().getChatId();
        long userId = update.getMessage().getFrom().getId();
        String messageText = update.getMessage().getText();

        switch (messageText) {
            case "/start" -> sendMessage(new MenuMessage(), chatId);
            case "/search" -> sendMessage(new SearchPromptMessage(), chatId);
            case "/random" ->  sendMessage(new RecipeMessage(recipes.getRandomRecipe(),false),chatId);
//            case "/catalog" -> sendMessage();
            case "/wishlist" -> sendMessage(new WishlistMessage(users.getUser(userId).getFavoriteRecipes(recipes)),chatId);
//            case "/add" ->  sendMessage();
            case "/help" -> sendMessage(new HelpMessage(), chatId);
            default -> System.out.println("Неверный запрос");
        }

    }

    private void handleCallbackQuery(Update update) {
        long chatId = update.getCallbackQuery().getMessage().getChatId();
        long userId = update.getCallbackQuery().getFrom().getId();
        int messageId = update.getCallbackQuery().getMessage().getMessageId();
        String callbackData = update.getCallbackQuery().getData();


        switch (callbackData) {
            case "/start" -> sendMessage(new MenuMessage(), chatId);
            case "/search" -> editMessage(new SearchPromptMessage(), chatId, messageId);
            case "/random" -> editMessage(new RecipeMessage(recipes.getRandomRecipe(),false),chatId, messageId);
//            case "/catalog" -> sendMessage();
            case "/wishlist" -> editMessage(new WishlistMessage(users.getUser(userId).getFavoriteRecipes(recipes)), chatId, messageId);
//            case "/add" ->  sendMessage();
            case "/help" -> editMessage(new HelpMessage(), chatId, messageId);
            case "/back" -> editMessage(new MenuMessage(), chatId, messageId);
            default -> {
                if (callbackData.startsWith("/add_favourites$")) {
                    Integer recipeId = Integer.valueOf(callbackData.split("\\$")[1]); // Извлекаем ID
                    users.getUser(userId).addFavoritesRecipe(recipeId); // Добавляем в избранное
                    editMessage(new RecipeMessage(recipes.getRecipe(recipeId), true), chatId, messageId); // Отредактируем сообщение
                } else if (callbackData.startsWith("/del_favourites$")) {
                    Integer recipeId = Integer.valueOf(callbackData.split("\\$")[1]); // Извлекаем ID
                    users.getUser(userId).removeFavoritesRecipe(recipeId); // Удаляем из избранного
                    editMessage(new RecipeMessage(recipes.getRecipe(recipeId), false), chatId, messageId); // Отредактируем сообщение
                } else {
                    System.out.println("a");
                }
            }

        }

    }

}