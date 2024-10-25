import org.telegram.telegrambots.client.okhttp.OkHttpTelegramClient;
import org.telegram.telegrambots.longpolling.util.LongPollingSingleThreadUpdateConsumer;
import org.telegram.telegrambots.meta.api.methods.updatingmessages.EditMessageText;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.meta.generics.TelegramClient;

import static java.lang.Math.toIntExact;

public class Bot implements LongPollingSingleThreadUpdateConsumer {
    private final TelegramClient telegramClient;
    private final MenuMessage MenuContent;
    private final HelpMessage helpContent;

    public Bot(String botToken) {
        telegramClient = new OkHttpTelegramClient(botToken);
        MenuContent = new MenuMessage();
        helpContent = new HelpMessage();
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
                    telegramClient.execute(MenuContent.createMainMenuMessage(chat_id));
                } catch (TelegramApiException e) {
                    e.printStackTrace();
                }
                break;
            case "/help":
                try {
                    telegramClient.execute(helpContent.createHelpMessage(chat_id));
                } catch (TelegramApiException e) {
                    e.printStackTrace();
                }
                break;
        }
    }

    private void handleCallbackQuery(Update update) {
        String call_data = update.getCallbackQuery().getData();
        long message_id = update.getCallbackQuery().getMessage().getMessageId();
        long chat_id = update.getCallbackQuery().getMessage().getChatId();

        String answer;

        if (call_data.equals("/search")) {
            answer = "Поиск блюд, напитков. Вы нажали на первую кнопку.";
            EditMessageText new_message = EditMessageText.builder()
                    .chatId(chat_id)
                    .messageId(toIntExact(message_id))
                    .text(answer)
                    .build();

            try {
                telegramClient.execute(new_message);
            } catch (TelegramApiException e) {
                e.printStackTrace();
            }
        } else if (call_data.equals("/random")) {
            answer = "Случайный рецепт. Вы нажали на вторую кнопку.";
            EditMessageText new_message = EditMessageText.builder()
                    .chatId(chat_id)
                    .messageId(toIntExact(message_id))
                    .text(answer)
                    .build();

            try {
                telegramClient.execute(new_message);
            } catch (TelegramApiException e) {
                e.printStackTrace();
            }
        } else if (call_data.equals("/wishlist")) {
            answer = "Избранное";
            EditMessageText new_message = EditMessageText.builder()
                    .chatId(chat_id)
                    .messageId(toIntExact(message_id))
                    .text(answer)
                    .build();

            try {
                telegramClient.execute(new_message);
            } catch (TelegramApiException e) {
                e.printStackTrace();
            }
        } else if (call_data.equals("/catalog")) {
            answer = "Каталог";
            EditMessageText new_message = EditMessageText.builder()
                    .chatId(chat_id)
                    .messageId(toIntExact(message_id))
                    .text(answer)
                    .build();

            try {
                telegramClient.execute(new_message);
            } catch (TelegramApiException e) {
                e.printStackTrace();
            }
        } else if (call_data.equals("/help")) {
            try {
                telegramClient.execute(helpContent.createHelpMessage(chat_id));
            } catch (TelegramApiException e) {
                e.printStackTrace();
            }
        } else if (call_data.equals("/back")) {
            try {
                telegramClient.execute(MenuContent.createMainMenuMessage(chat_id));
            } catch (TelegramApiException e) {
                e.printStackTrace();
            }
        } else {
            answer = "Неверный запрос";
            EditMessageText new_message = EditMessageText.builder()
                    .chatId(chat_id)
                    .messageId(toIntExact(message_id))
                    .text(answer)
                    .build();

            try {
                telegramClient.execute(new_message);
            } catch (TelegramApiException e) {
                e.printStackTrace();
            }
        }

    }

}