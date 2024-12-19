package com.github.PavelAnton.Chef_Bot;

import com.github.PavelAnton.Chef_Bot.message.baseMessagePhoto.BaseMessagePhoto;
import com.github.PavelAnton.Chef_Bot.message.baseMessagePhoto.RecipeMessage;
import com.github.PavelAnton.Chef_Bot.message.baseMessages.*;
import com.github.PavelAnton.Chef_Bot.recipe.Recipe;
import com.github.PavelAnton.Chef_Bot.recipe.Recipes;
import com.github.PavelAnton.Chef_Bot.user.User;
import com.github.PavelAnton.Chef_Bot.user.Users;

import org.telegram.telegrambots.client.okhttp.OkHttpTelegramClient;
import org.telegram.telegrambots.longpolling.util.LongPollingSingleThreadUpdateConsumer;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.meta.generics.TelegramClient;
import org.telegram.telegrambots.meta.api.methods.AnswerCallbackQuery;

import java.util.List;

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

        if (    "/search".equals(messageText) ||
                "/start".equals(messageText) ||
                "/help".equals(messageText) ||
                "/catalog".equals(messageText) ||
                "/wishlist".equals(messageText)) {
            currentUser.setState(User.States.START);
        }

        switch (currentUser.getState()) {
            case START -> handleStartCommand(messageText, chatId, currentUser);
            case AWAITING_SEARCH_RECIPE_NAME -> handleRecipeSearch(messageText, chatId, currentUser);
        }

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
            case "/wishlist":
                sendMessage(new WishlistMessage(user.getFavoriteRecipes(recipes)), chatId);
                break;
            case "/help":
                sendMessage(new HelpMessage(), chatId);
                break;
            default:
                System.out.println("Неработает");
                sendMessage(new MenuMessage(), chatId);
                break;
        }
    }

    private void handleRecipeSearch(String messageText, long chatId, User user) {
        List<Recipe> results = recipes.searchRecipes(messageText);
        user.setSearchQuery(messageText);
        if (!results.isEmpty()) {
            user.setState(User.States.START);
        }
        sendMessage(new SearchResultsMessage(results, 0), chatId);
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

        if (    callbackData.startsWith("/add_favourites$") ||
                callbackData.startsWith("/del_favourites$") ||
                callbackData.startsWith("/view_recipe$") ||
                callbackData.startsWith("/search_page$") ||
                callbackData.startsWith("/ingredients_page$")) {
            handleActionParameterInt(callbackData, currentUser, chatId, messageId);
        }

        else if (callbackData.startsWith("/toggle_ingredient$")) {
            handleActionParameterStr(callbackData, currentUser, chatId, messageId);
        }

        else if (callbackData.equals("/search_recipes")) {
            handleActionCommand(callbackData, currentUser, chatId, messageId);
        }

        else {
            handleMenuActions(callbackData, userId, chatId, messageId, currentUser);
        }

        String callbackQueryId = update.getCallbackQuery().getId();
        AnswerCallbackQuery answer = new AnswerCallbackQuery(callbackQueryId);

        try {
            telegramClient.execute(answer);
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }

    private void handleActionParameterInt(String callbackData, User user, long chatId, int messageId) {
        int actionParameter = Integer.parseInt(callbackData.split("\\$")[1]);

        if (callbackData.startsWith("/search_page$")) {
            editMessage(new SearchResultsMessage(recipes.searchRecipes(user.getSearchQuery()), actionParameter), chatId, messageId);
        }

        else if (callbackData.startsWith("/ingredients_page$")) {
            editMessage(new SearchIngredientsMessage(recipes.getAllIngredients(), user.getSelectedIngredients(), actionParameter), chatId, messageId);
        }

        else if (callbackData.startsWith("/add_favourites$")) {
            user.addFavoritesRecipe(actionParameter);
            editMessageWithPhoto(new RecipeMessage(recipes.getRecipe(actionParameter), true), chatId, messageId);
        }

        else if (callbackData.startsWith("/del_favourites$")) {
            user.removeFavoritesRecipe(actionParameter);
            editMessageWithPhoto(new RecipeMessage(recipes.getRecipe(actionParameter), false), chatId, messageId);
        }

        else if (callbackData.startsWith("/view_recipe$")) {
            user.setSearchQuery(null);
            user.setSelectedIngredients(null);
            Recipe recipe = recipes.getRecipe(actionParameter);
            editMessageWithPhoto(new RecipeMessage(recipe, user.isRecipeInFavorites(recipe)), chatId, messageId);
        }
    }

    private void handleActionParameterStr(String callbackData, User user, long chatId, int messageId) {
        String actionParameter = callbackData.split("\\$")[1];

        if (callbackData.startsWith("/toggle_ingredient$")) {
            List<String> selectedIngredients = user.getSelectedIngredients();

            if (selectedIngredients.contains(actionParameter)) {
                selectedIngredients.remove(actionParameter);
            }

            else {
                selectedIngredients.add(actionParameter);
            }

            List<String> allIngredients = recipes.getAllIngredients();
            editMessage(new SearchIngredientsMessage(allIngredients, selectedIngredients, 0), chatId, messageId);
        }
    }

    private void handleActionCommand(String callbackData, User user, long chatId, int messageId) {
        if (callbackData.equals("/search_recipes")){
            List<String> selectedIngredients = user.getSelectedIngredients();
            List<Recipe> results = recipes.searchRecipesByIngredients(selectedIngredients);
            editMessage(new SearchResultsMessage(results, 0), chatId, messageId);
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
            case "/search_by_ingredients":
                currentUser.setState(User.States.AWAITING_SEARCH_RECIPE_INGREDIENTS);
                editMessage(new SearchIngredientsMessage(recipes.getAllIngredients(), currentUser.getSelectedIngredients(), 0), chatId, messageId);
                break;
            case "/random":
                Recipe randomRecipe = recipes.getRandomRecipe();
                editMessageWithPhoto(new RecipeMessage(randomRecipe, currentUser.isRecipeInFavorites(randomRecipe)), chatId, messageId);
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