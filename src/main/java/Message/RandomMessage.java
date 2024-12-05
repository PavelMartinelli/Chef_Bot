package Message;

import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardRow;

import java.util.ArrayList;
import java.util.List;

public class RandomMessage extends BaseMessage {
    @Override
    protected String getMessageText() {
        return "Это рандом да..... Пока в разработке.";
    }

    @Override
    protected List<InlineKeyboardRow> createKeyboardRows() {
        List<InlineKeyboardRow> rows = new ArrayList<>();
        rows.add(createRow("На главное меню", "/back"));
        return rows;
    }
}