package Message;

import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardRow;

import java.util.ArrayList;
import java.util.List;

public class WishlistMessage extends BaseMessage {
    @Override
    protected String getMessageText() {
        return "Вы попали в избранное. Пока в разработке.";
    }

    @Override
    protected List<InlineKeyboardRow> createKeyboardRows() {
        List<InlineKeyboardRow> rows = new ArrayList<>();
        rows.add(createRow("На главное меню", "/back"));
        return rows;
    }
}