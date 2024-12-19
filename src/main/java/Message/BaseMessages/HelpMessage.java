package Message.BaseMessages;

import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardRow;

import java.util.ArrayList;
import java.util.List;

public class HelpMessage extends BaseMessage {
    @Override
    protected String getMessageText() {
        return "🤖 Это бот для приготовления всяких блюд.\n" +
                "\nℹ Информация по всем функциям бота доступна по команде /start\n" +
                "\n Подробная инструкция будет потом\n";
    }

    @Override
    protected List<InlineKeyboardRow> createKeyboardRows() {
        List<InlineKeyboardRow> rows = new ArrayList<>();
        rows.add(createRow("🏠 На главное меню", "/back"));
        return rows;
    }
}