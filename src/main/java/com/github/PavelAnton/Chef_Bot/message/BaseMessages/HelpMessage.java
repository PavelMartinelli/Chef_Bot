package com.github.PavelAnton.Chef_Bot.message.BaseMessages;

import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardRow;

import java.util.ArrayList;
import java.util.List;

public class HelpMessage extends BaseMessage {
    @Override
    protected String getMessageText() {
        return "🤖 Это бот для приготовления всяких блюд.\n" +
                "\nℹ Для вызова главного меню используйте команду" + "<b> /start </b>\n" +
                "\n🔍 Для поиска рецепта по названию используйте команду" + "<b> /search </b>\n" +
                "<i>Либо</i> \n" + "В главном меню нажмите кнопку <pre> 🔍 Поиск блюд </pre>\n" +
                "\n🍳 Для поиска рецепта по ингрединтам в главном меню нажмите кнопку " +
                "<pre>🍳 Поиск по ингредиентам </pre>\n" +
                "\n🎲 Для получения случайного рецепта используйте команду" + "<b> /random </b>\n" +
                "<i>Либо</i> \n" + "В главном меню нажмите кнопку <pre> 🎲 Случайный рецепт </pre>\n" +
                "\nВы можете <b> ❤️ добавлять рецепты в избранное ❤️ </b>, а также " +
                "<b> ❌ удалять рецепты из избранного ❌ </b> " +
                "в любом разделе нашего бота." + " Для этого под каждой карточкой рецепта есть кнопки:" +
                "<pre> ❤️ Добавить в избранное </pre>" + "<pre> ❌ Удалить из избранного </pre>\n" +
                "\n❤️ Для просмотра списка ваших любимых рецептов используйте команду" + "<b> /wishlist </b>\n" +
                "<i>Либо</i> \n" + "В главном меню нажмите кнопку <pre> ❤️ Избранное </pre>\n" +
                "\n<b> PS: </b>" +
                "\nПриятного пользования нашим ботом и пусть он подарит вам множество вкусных блюд " +
                "и интересных воспоминаний\n"+
                "\n С любовью команда разработки Chef_Bot 🍳🤖";
    }

    @Override
    protected List<InlineKeyboardRow> createKeyboardRows() {
        List<InlineKeyboardRow> rows = new ArrayList<>();
        rows.add(createRow("🏠 На главное меню", "/back"));
        return rows;
    }
}