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

        if (users.isUserNotInUsers(userId)) {
            User curUser = new User(userId, userName, null);
            users.addUser(curUser);
        }

        User currentUser = users.getUser(userId);

        switch (currentUser.getState()) {
            case START -> handleStartCommand(messageText, chatId, currentUser);
            case AWAITING_SEARCH_RECIPE_NAME -> handleRecipeSearch(messageText, chatId, currentUser);
            default -> System.out.println("Неработает1");
        }

    }

    private void handleRecipeSearch(String messageText, long chatId, User user) {
        List<Recipe> results = recipes.searchRecipes(messageText);
        if (!results.isEmpty()) {
            user.setState(User.States.START);
        }
        sendMessage(new SearchResultsMessage(results), chatId);
    }

    private void handleStartCommand(String messageText, long chatId, User user) {
        switch (messageText) {
            case "/start":
                sendMessage(new MenuMessage(), chatId);
                break;
            case "/search":
                user.setState(User.States.AWAITING_SEARCH_RECIPE_NAME);
                sendMessage(new SearchPromptMessage(), chatId);
                break;
            case "/random":
                Recipe randomRecipe = recipes.getRandomRecipe();
                sendMessageWithPhoto(new RecipeMessage(randomRecipe, user.isRecipeInFavorites(randomRecipe)), chatId);
                break;
            case "/catalog":
                sendMessage(new CatalogMessage(), chatId);
                break;
            case "/wishlist":
                sendMessage(new WishlistMessage(user.getFavoriteRecipes(recipes)), chatId);
                break;
            case "/help":
                sendMessage(new HelpMessage(), chatId);
                break;
            default:
                System.out.println("Неработает");
                break;
        }
    }


    private void handleCallbackQuery(Update update) {
        long chatId = update.getCallbackQuery().getMessage().getChatId();
        long userId = update.getCallbackQuery().getFrom().getId();
        String userName = update.getCallbackQuery().getFrom().getUserName();
        int messageId = update.getCallbackQuery().getMessage().getMessageId();
        String callbackData = update.getCallbackQuery().getData();

        if (users.isUserNotInUsers(userId)) {
            User curUser = new User(userId, userName, null);
            users.addUser(curUser);
        }

        User currentUser = users.getUser(userId);

        if (callbackData.startsWith("/add_favourites$") || callbackData.startsWith("/del_favourites$") || callbackData.startsWith("/view_recipe$")) {
            handleRecipeActions(callbackData, userId, chatId, messageId);
        } else {
            handleMenuActions(callbackData, userId, chatId, messageId, currentUser);
        }
    }

    private Integer extractRecipeId(String callbackData) {
        try {
            return Integer.valueOf(callbackData.split("\\$")[1]);
        } catch (Exception e) {
            System.err.println("Ошибка извлечения recipeId: " + e.getMessage());
            return null;
        }
    }

    private void handleRecipeActions(String callbackData, long userId, long chatId, int messageId) {
        Integer recipeId = extractRecipeId(callbackData);
        switch (callbackData.split("\\$")[0]) {
            case "/add_favourites":
                users.getUser(userId).addFavoritesRecipe(recipeId);
                editMessageWithPhoto(new RecipeMessage(recipes.getRecipe(recipeId), true), chatId, messageId);
                break;
            case "/del_favourites":
                users.getUser(userId).removeFavoritesRecipe(recipeId);
                editMessageWithPhoto(new RecipeMessage(recipes.getRecipe(recipeId), false), chatId, messageId);
                break;
            case "/view_recipe":
                Recipe recipe = recipes.getRecipe(recipeId);
                editMessageWithPhoto(new RecipeMessage(recipe, users.getUser(userId).isRecipeInFavorites(recipe)), chatId, messageId);
                break;
        }
    }

    private void handleMenuActions(String callbackData, long userId, long chatId, int messageId, User currentUser) {
        switch (callbackData) {
            case "/start":
                sendMessage(new MenuMessage(), chatId);
                break;
            case "/search":
                currentUser.setState(User.States.AWAITING_SEARCH_RECIPE_NAME);
                editMessage(new SearchPromptMessage(), chatId, messageId);
                break;
            case "/random":
                Recipe randomRecipe = recipes.getRandomRecipe();
                editMessageWithPhoto(new RecipeMessage(randomRecipe, currentUser.isRecipeInFavorites(randomRecipe)), chatId, messageId);
                break;
            case "/catalog":
                sendMessage(new CatalogMessage(), chatId);
                break;
            case "/wishlist":
                editMessage(new WishlistMessage(currentUser.getFavoriteRecipes(recipes)), chatId, messageId);
                break;
            case "/help":
                editMessage(new HelpMessage(), chatId, messageId);
                break;
            case "/back":
                currentUser.setState(User.States.START);
                editMessage(new MenuMessage(), chatId, messageId);
                break;
            default:
                sendMessage(new MenuMessage(), chatId);
                break;
        }
    }

}