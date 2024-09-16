package pro.sky.animalShelter.buttons;

import com.pengrad.telegrambot.TelegramBot;
import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.model.request.InlineKeyboardButton;
import com.pengrad.telegrambot.model.request.InlineKeyboardMarkup;
import com.pengrad.telegrambot.request.SendMessage;
import com.pengrad.telegrambot.response.SendResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import pro.sky.animalShelter.constants.Constants;

@Service
public class Buttons {
    private final TelegramBot telegramBot;

    @Value("${chat.id.volunteer}")
    private Long chatIdVolunteer;
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

    public void buttonsStage_1(Update update) {
        long chat_Id = update.callbackQuery().message().chat().id();
        InlineKeyboardMarkup markup = new InlineKeyboardMarkup();
        markup.addRow(new InlineKeyboardButton(
                        "Информация о приюте").callbackData("/b1"),
                new InlineKeyboardButton(
                        "Расписание и адрес приюта").callbackData("/b2"));
        markup.addRow(new InlineKeyboardButton(
                        "Обратная связь").callbackData("/b3"),
                new InlineKeyboardButton(
                        "Позвать волонтера").callbackData("/b4"));
        SendMessage send = new SendMessage(chat_Id, "Выберете один из вариантов:").
                replyMarkup(markup);
        telegramBot.execute(send);


    }

    public void buttonsStage_2(Update update) {
        long chat_Id = update.callbackQuery().message().chat().id();
        InlineKeyboardMarkup markup = new InlineKeyboardMarkup();
        markup.addRow(new InlineKeyboardButton(
                        "Список животных").callbackData("/c1"),
                new InlineKeyboardButton(
                        "Правила знакомства с животным").callbackData("/c2"));
                new InlineKeyboardButton(
                        "Обустройство дома для животного").callbackData("/c3");
        markup.addRow(new InlineKeyboardButton(
                        "Причины отказа").callbackData("/c4"));
        markup.addRow(new InlineKeyboardButton(
                        "Обратная связь").callbackData("/c5"),
                new InlineKeyboardButton(
                        "Позвать волонтера").callbackData("/c6"));
        SendMessage send = new SendMessage(chat_Id, "Выберете один из вариантов:").
                replyMarkup(markup);
        telegramBot.execute(send);
    }

    public void buttonsStage_volunteer(Update update) {
        long chatId = update.message().chat().id();
        String comMsg = update.message().text();
        if (comMsg.equalsIgnoreCase("/imvolunteer") && chatIdVolunteer == chatId) {
            SendResponse response = telegramBot.execute(new SendMessage(chatId, "Привет! Это меню для волонтера."));
            InlineKeyboardMarkup markup = new InlineKeyboardMarkup();
            markup.addRow(new InlineKeyboardButton(
                    "Пользователи, которые не отправили отчет").callbackData("/v1"));
            markup.addRow(new InlineKeyboardButton(
                    "Отправить предупреждение пользователю").callbackData("/v2"));
            markup.addRow(new InlineKeyboardButton(
                    "У кого закончился закончился испытательный срок").callbackData("/v3"));
            SendMessage send = new SendMessage(chatId, "Выберете один из вариантов:").
                    replyMarkup(markup);
            telegramBot.execute(send);}
    }
}
