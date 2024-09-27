import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.meta.api.methods.CopyMessage;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.methods.send.SendPhoto;
import org.telegram.telegrambots.meta.api.objects.CallbackQuery;
import org.telegram.telegrambots.meta.api.objects.InputFile;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.updatesreceivers.DefaultBotSession;

import java.util.List;

public class Bot extends TelegramLongPollingBot {
    private InlineKeyboardMarkup menu;
    Tg_Token token = new Tg_Token();

    public static void main(String[] args) throws TelegramApiException {
        TelegramBotsApi botsApi = new TelegramBotsApi(DefaultBotSession.class);
        Bot bot = new Bot();
        botsApi.registerBot(bot);
        bot.sendText(798230948L, "Доброе утро");
        bot.sendPhoto(798230948L,
                new InputFile("https://yandex.ru/images/search?from=tabbar&img_url=http%3A%2F%2Fi.pinimg.com%2F736x%2Fac%2F69%2F84%2Fac6984675570e6d5f6557e0c325a7487.jpg&lr=54&pos=1&rpt=simage&text=%D0%B5%D0%B4%D0%B0%20%D1%80%D0%BE%D0%B1%D0%BE%D1%82"));
    }


    @Override
    public void onUpdateReceived(Update update) {
        createMenu();
        var message = update.getMessage();

        if(update.hasCallbackQuery()) {
            CallbackQuery callbackQuery = update.getCallbackQuery();
            String call_data = update.getCallbackQuery().getData();
            System.out.println(update.getCallbackQuery().getData());
            long chatId = callbackQuery.getMessage().getChatId();

            switch (call_data) {
                case "food":
                    sendText(chatId, "Тут будет еда");

                    return;

                case "drink":
                    sendText(chatId, "Тут будут напитки");
                    break;

                case "back":
                    sendText(chatId, "Тут будет выход");
                    break;

                default:
                    return;
            }
            return;
        }
        var user = message.getFrom();
        var id = user.getId();
        switch (message.getText()){

                    case "/find" :
                    sendMenu(id, menu, "<b>Рицепт чего хотите найти?</b>");
                    return;

                    case "/add" :
                        sendMenu(id, menu, "<b>Рицепт чего хотите добавить?</b>");
                    break;

                    case "/login" :
                    sendText(id, "Тут будет вход");
                    break;

                    case "/register" :
                    sendText(id, "Тут будет регистрация");
                    break;
                    default: return;
        }
    }

    ///////////////////////////////////////////////////////////////////
    public void sendMenu(Long id, InlineKeyboardMarkup menu_keyboard, String txt){

        SendMessage message = SendMessage.builder()
                .chatId(id.toString()).parseMode("HTML")
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

        menu = InlineKeyboardMarkup.builder()
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