package pro.sky.animalShelter.buttons;

import com.pengrad.telegrambot.TelegramBot;
import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.model.request.InlineKeyboardButton;
import com.pengrad.telegrambot.model.request.InlineKeyboardMarkup;
import com.pengrad.telegrambot.request.SendMessage;
import com.pengrad.telegrambot.response.SendResponse;
import org.springframework.stereotype.Service;
import pro.sky.animalShelter.constants.Constants;

@Service
public class Buttons {
    private final TelegramBot telegramBot;

    public Buttons(TelegramBot telegramBot) {
        this.telegramBot = telegramBot;
    }

    /**
     * первоначальное меню
     *
     * @param update
     */
    public void ButtonsStage_0(Update update) {
        String comMsg = update.message().text();
        Long chatId = update.message().chat().id();
        if (comMsg.equalsIgnoreCase("/start")) {
            SendResponse response = telegramBot.execute(new SendMessage(chatId, Constants.MEET));
        }
        InlineKeyboardMarkup markup = new InlineKeyboardMarkup();
        markup.addRow(new InlineKeyboardButton(
                        "Информация о приюте").callbackData("/a1"),
                new InlineKeyboardButton(
                        "Как взять животное?").callbackData("/a2"));
        markup.addRow(new InlineKeyboardButton(
                        "Прислать отчет о питомце").callbackData("/a3"),
                new InlineKeyboardButton(
                        "Позвать волонтера").callbackData("/a4"));
        SendMessage send = new SendMessage(chatId, "Выберете один из вариантов:").
                replyMarkup(markup);
        telegramBot.execute(send);

    }
}
