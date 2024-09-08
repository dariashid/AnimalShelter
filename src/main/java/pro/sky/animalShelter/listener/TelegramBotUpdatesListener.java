package pro.sky.animalShelter.listener;


import com.pengrad.telegrambot.TelegramBot;
import com.pengrad.telegrambot.UpdatesListener;
import com.pengrad.telegrambot.model.Update;
import jakarta.annotation.PostConstruct;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Класс, в котором принимаются ответы от пз, они обрабатываются, после чего выдается ответ.
 * Метод обработки и отправки данных  {@link #process(List)}
 */
@Service
public class TelegramBotUpdatesListener implements UpdatesListener {

    private Logger logger = LoggerFactory.getLogger(TelegramBotUpdatesListener.class);

    private final TelegramBot telegramBot;
    private final Buttons buttons;

    public TelegramBotUpdatesListener(TelegramBot telegramBot, Buttons buttons) {
        this.telegramBot = telegramBot;
        this.buttons = buttons;
    }

    @PostConstruct
    public void init() {
        telegramBot.setUpdatesListener(this);
    }

    /**
     * Метод для взаимодействия бота с пз
     *
     * @param updates
     * @return ответ на запрос пз
     * @throws Exception если зеачение update не корректное.
     */
    @Override
    public int process(List<Update> updates) {
        updates.forEach(update -> {
            if (update.message() != null) {
                try {
                    logger.info("Processing update: {}", update);

                    buttons.ButtonsStage_0(update);
                } catch (Exception e) {
                    logger.error("update not correct");
                }
            } else if (update.callbackQuery() != null) {
                String text = update.callbackQuery().data();


            }
        }
    }
}
