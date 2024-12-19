package com.github.PavelAnton.Chef_Bot;

import com.github.PavelAnton.Chef_Bot.token.Tg_Token;
import org.telegram.telegrambots.longpolling.TelegramBotsLongPollingApplication;

public class Main {
    public static void main(String[] args) {

        Tg_Token tgToken = new Tg_Token();
        String botToken = tgToken.getToken();

        try (TelegramBotsLongPollingApplication botsApplication = new TelegramBotsLongPollingApplication()) {
            botsApplication.registerBot(botToken, new Bot(botToken));
            System.out.println("Chef_Bot successfully started!");
            Thread.currentThread().join();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}