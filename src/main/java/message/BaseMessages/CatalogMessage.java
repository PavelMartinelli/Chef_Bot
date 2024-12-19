package message.BaseMessages;

import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardRow;

import java.util.ArrayList;
import java.util.List;

public class CatalogMessage extends BaseMessage {
    @Override
    protected String getMessageText() {
        return "Вы попали в Каталог. Пока в разработке.";
    }

    @Override
    protected List<InlineKeyboardRow> createKeyboardRows() {
        List<InlineKeyboardRow> rows = new ArrayList<>();
        rows.add(createRow("🏠 На главное меню", "/back"));
        return rows;
    }
}