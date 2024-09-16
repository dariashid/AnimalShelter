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
                        "Оформление пропуска").callbackData("/b3"),
                new InlineKeyboardButton(
                        "Рекомендации техники безопасности").callbackData("/b4"));
        markup.addRow(new InlineKeyboardButton(
                        "Обратная связь").callbackData("/b5"),
                new InlineKeyboardButton(
                        "Позвать волонтера").callbackData("/b6"));
        SendMessage send = new SendMessage(chat_Id, "Выберете один из вариантов:").
                replyMarkup(markup);
        telegramBot.execute(send);


    }

    public void buttonsStage_2(Update update) {
        long chat_Id = update.callbackQuery().message().chat().id();
        InlineKeyboardMarkup markup = new InlineKeyboardMarkup();
        markup.addRow(new InlineKeyboardButton(
                markup.addRow(new InlineKeyboardButton(
                        "Список животных для усыновления").callbackData("/c1"),
                        new InlineKeyboardButton(
                                "Правила знакомства с животным").callbackData("/c2"));
        markup.addRow(new InlineKeyboardButton(
                        "Список документов").callbackData("/c3"),
                new InlineKeyboardButton(
                        "Транспортировка животного").callbackData("/c4"));
        markup.addRow(new InlineKeyboardButton(
                        "Обустройство дома для щенка").callbackData("/c5"),
                new InlineKeyboardButton(
                        "Обустройство дома для взрослой собаки").callbackData("/c6"));
        markup.addRow(new InlineKeyboardButton(
                        "Обустройство дома для животного с ограниченными возможностями").callbackData("/c7"),
                new InlineKeyboardButton(
                        "Первичное общение с собакой").callbackData("/c8"));
        markup.addRow(new InlineKeyboardButton(
                        "Проверенные кинологи").callbackData("/c9"),
                new InlineKeyboardButton(
                        "Причины отказа в усыновлении").callbackData("/c10"));
        markup.addRow(new InlineKeyboardButton(
                        "Обратная связь").callbackData("/c11"),
                new InlineKeyboardButton(
                        "Позвать волонтера").callbackData("/c12"));
        SendMessage send = new SendMessage(chat_Id, "Выберете один из вариантов:").
                replyMarkup(markup);
        telegramBot.execute(send);


        public void buttonsStage_volunteer (Update update){
            long chatId = update.message().chat().id();
            String comMsg = update.message().text();
            if (comMsg.equalsIgnoreCase("/imvolunteer") && chatIdVolunteer == chatId) {
                SendResponse response = telegramBot.execute(new SendMessage(chatId, "Привет! Это меню для волонтера."));
                InlineKeyboardMarkup markup = new InlineKeyboardMarkup();
                markup.addRow(new InlineKeyboardButton(
                        "Пользователи, которые не отправили отчет").callbackData("/d1"));
                markup.addRow(new InlineKeyboardButton(
                        "Отправить предупреждение пользователю").callbackData("/d2"));
                markup.addRow(new InlineKeyboardButton(
                        "У кого закончился закончился испытательный срок").callbackData("/d3"));
                SendMessage send = new SendMessage(chatId, "Выберете один из вариантов:").
                        replyMarkup(markup);
                telegramBot.execute(send);
            }
        }
    }
}
