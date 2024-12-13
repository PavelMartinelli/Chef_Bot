import Message.BaseMessages.*;
import Message.BaseMessages.CatalogMessage;
import Message.BaseMessagePhoto.RecipeMessage;
import Recipe.*;
import User.*;
import Message.BaseMessages.BaseMessage;
import Message.BaseMessagePhoto.BaseMessagePhoto;

import org.telegram.telegrambots.client.okhttp.OkHttpTelegramClient;
import org.telegram.telegrambots.longpolling.util.LongPollingSingleThreadUpdateConsumer;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.meta.generics.TelegramClient;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Bot implements LongPollingSingleThreadUpdateConsumer {

    private final Recipes recipes;
    private final Users users;
    private final TelegramClient telegramClient;
    private final Map<Long, String> state;

    public Bot(String botToken) {

        this.telegramClient = new OkHttpTelegramClient(botToken);
        this.recipes = new Recipes();
        this.users = new Users();
        this.state = new HashMap<>();

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
            telegramClient.execute(message.createMessage(chatId));
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }

    private void sendMessageWithPhoto(BaseMessagePhoto message, long chatId) {
        try {
            telegramClient.execute(message.createMessageWithPhoto(chatId));
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }

    private void editMessage(BaseMessage message, long chatId, int messageId) {
        try {
            telegramClient.execute(message.createEditMessage(chatId, messageId));
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }

    private void editMessageWithPhoto(BaseMessagePhoto message, long chatId, int messageId) {
        try {
            telegramClient.execute(message.createEditMessageWithPhoto(chatId, messageId));
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }


    private void handleTextMessage(Update update) {

        long chatId = update.getMessage().getChatId();
        long userId = update.getMessage().getFrom().getId();
        String userName = update.getMessage().getFrom().getUserName();
        String messageText = update.getMessage().getText();

        if (users.isUserNotInUsers(userId)){
            User curUser = new User(userId, userName, null);
            users.addUser(curUser);
        }

        switch (messageText) {
            case "/start" -> sendMessage(new MenuMessage(), chatId);
            case "/search" -> sendMessage(new SearchPromptMessage(), chatId);
            case "/random" -> {
                Recipe recipe = recipes.getRandomRecipe();
                sendMessageWithPhoto(new RecipeMessage(recipe, users.getUser(userId).isRecipeInFavorites(recipe)),chatId);
            }
            case "/catalog" -> sendMessage( new CatalogMessage(),chatId);
            case "/wishlist" -> sendMessage(new WishlistMessage(users.getUser(userId).getFavoriteRecipes(recipes)),chatId);
            case "/help" -> sendMessage(new HelpMessage(), chatId);
            default -> {
                if (state.containsKey(chatId) && "awaiting_query".equals(state.get(chatId))) {
                    state.remove(chatId);
                    List<Recipe> results = recipes.searchRecipes(messageText);
                    sendMessage(new SearchResultsMessage(results), chatId);
                }
            }
        }
    }

    private void handleCallbackQuery(Update update) {
        long chatId = update.getCallbackQuery().getMessage().getChatId();
        long userId = update.getCallbackQuery().getFrom().getId();
        String userName = update.getCallbackQuery().getFrom().getUserName();
        int messageId = update.getCallbackQuery().getMessage().getMessageId();
        String callbackData = update.getCallbackQuery().getData();

        if (users.isUserNotInUsers(userId)){
            User curUser = new User(userId, userName, null);
            users.addUser(curUser);
        }

        if (callbackData.startsWith("/add_favourites$")) {
            Integer recipeId = Integer.valueOf(callbackData.split("\\$")[1]);
            users.getUser(userId).addFavoritesRecipe(recipeId);
            editMessageWithPhoto(new RecipeMessage(recipes.getRecipe(recipeId), true), chatId, messageId);
        } else if (callbackData.startsWith("/del_favourites$")) {
            Integer recipeId = Integer.valueOf(callbackData.split("\\$")[1]);
            users.getUser(userId).removeFavoritesRecipe(recipeId);
            editMessageWithPhoto(new RecipeMessage(recipes.getRecipe(recipeId), false), chatId, messageId);
        } else if (callbackData.startsWith("view_recipe$")) {
            Integer recipeId = Integer.valueOf(callbackData.split("\\$")[1]);
            Recipe recipe = recipes.getRecipe(recipeId);
            editMessageWithPhoto(new RecipeMessage(recipe, users.getUser(userId).isRecipeInFavorites(recipe)), chatId
                    , messageId);
        } else switch (callbackData) {
            case "/start" -> sendMessage(new MenuMessage(), chatId);
            case "/search" -> {
                state.put(chatId, "awaiting_query");
                editMessage(new SearchPromptMessage(), chatId, messageId);
            }
            case "/random" -> {
                Recipe recipe = recipes.getRandomRecipe();
                editMessageWithPhoto(new RecipeMessage(recipe, users.getUser(userId).isRecipeInFavorites(recipe)),chatId, messageId);
            }
            case "/catalog" -> sendMessage( new CatalogMessage(),chatId);
            case "/wishlist" -> editMessage(new WishlistMessage(users.getUser(userId).getFavoriteRecipes(recipes)), chatId, messageId);
            case "/help" -> editMessage(new HelpMessage(), chatId, messageId);
            case "/back" -> editMessage(new MenuMessage(), chatId, messageId);
            default -> {
                System.out.println("Неработает");
            }

        }

    }

}