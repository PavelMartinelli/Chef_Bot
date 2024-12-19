package com.github.PavelAnton.Chef_Bot.message.BaseMessages;

import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardRow;

import java.util.ArrayList;
import java.util.List;

public class SearchPromptMessage extends BaseMessage {

    @Override
    protected String getMessageText() {
        return "🔍 Введите название блюда для поиска:";
    }

    @Override
    protected List<InlineKeyboardRow> createKeyboardRows() {
        List<InlineKeyboardRow> rows = new ArrayList<>();
        rows.add(createRow("🏠 На главное меню", "/back"));
        return rows;
    }
}
