import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.meta.api.methods.AnswerCallbackQuery;
import org.telegram.telegrambots.meta.api.methods.CopyMessage;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.methods.send.SendPhoto;
import org.telegram.telegrambots.meta.api.objects.InputFile;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboard;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.updatesreceivers.DefaultBotSession;

import java.util.ArrayList;
import java.util.List;

public class Bot extends TelegramLongPollingBot {
    private InlineKeyboardMarkup  menu_find;
    Tg_Token token = new Tg_Token();

    public static void main(String[] args) throws TelegramApiException {
        TelegramBotsApi botsApi = new TelegramBotsApi(DefaultBotSession.class);
        Bot bot = new Bot();
        botsApi.registerBot(bot);
        bot.sendText(798230948L, "Доброе утро");
        bot.sendPhoto(798230948L,
                new InputFile("https://i.pinimg.com/originals/a1/57/e3/a157e33152dbf0d9da5627b28a070bd6.jpg"));
    }


    @Override
    public void onUpdateReceived(Update update) {
        var message = update.getMessage();
        var user = message.getFrom();
        var id = user.getId();
        createMenu();
        if(update.hasCallbackQuery()) {

            String call_data = update.getCallbackQuery().getData();
            System.out.println(update.getCallbackQuery().getData());

            if (call_data.equals("food")) {
                sendText(id, "Тут будет еда");
            }
        }

        switch (message.getText()){

                    case "/find" :
                    sendMenu(id, menu_find);
                    break;

                    case "/add" :
                    sendText(id, "Тут будет добавление рецептов");
                    break;

                    case "/login" :
                    sendText(id, "Тут будет вход");
                    break;

                    case "/register" :
                    sendText(id, "Тут будет регистрация");
                    break; default: break;
        }
    }

    ///////////////////////////////////////////////////////////////////
    public void sendMenu(Long id, InlineKeyboardMarkup menu_keyboard){
        String txt = "<b>Рицепт чего хотите найти?</b>";

        SendMessage message = SendMessage.builder()
                .chatId(id.toString())
                .text(txt).replyMarkup(menu_keyboard).
                build();
        try {
            execute(message);
        } catch (TelegramApiException e) {
            throw new RuntimeException(e);
        }
    }

    public void createMenu(){
        var back = InlineKeyboardButton.builder()
                .text("Назад").callbackData("back").build();

        var drinks = InlineKeyboardButton.builder()
                .text("Напитки").callbackData("drink").build();

        var food = InlineKeyboardButton.builder()
                .text("Еда").callbackData("food").build();

        menu_find = InlineKeyboardMarkup.builder()
                .keyboardRow(List.of(drinks, food))
                .keyboardRow(List.of(back))
                .build();
    }

    public void copyMessage(Long who, Integer msgId){
        CopyMessage cm = CopyMessage.builder()
                .fromChatId(who.toString())
                .chatId(who.toString())
                .messageId(msgId)
                .build();
        try {
            execute(cm);
        } catch (TelegramApiException e) {
            throw new RuntimeException(e);
        }
    }

    public void sendText(Long who, String what){
        SendMessage sm = SendMessage.builder()
                .chatId(who.toString())
                .text(what).build();
        try {
            execute(sm);
        } catch (TelegramApiException e) {
            throw new RuntimeException(e);
        }
    }

    public void sendPhoto(Long who, InputFile what){
        SendPhoto sm = SendPhoto.builder()
                .chatId(who.toString()).photo(what)
                .build();
        try {
            execute(sm);
        } catch (TelegramApiException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public String getBotUsername() {
        return "@Chef_A_P_bot";
    }

    @Override
    public String getBotToken() {
        return token.getToken();
    }
}