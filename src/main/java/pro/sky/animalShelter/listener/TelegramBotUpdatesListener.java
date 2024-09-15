package pro.sky.animalShelter.listener;


import com.pengrad.telegrambot.TelegramBot;
import com.pengrad.telegrambot.UpdatesListener;
import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.request.SendMessage;
import jakarta.annotation.PostConstruct;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pro.sky.animalShelter.buttons.Buttons;
import pro.sky.animalShelter.constants.Constants;
import pro.sky.animalShelter.model.Client;
import pro.sky.animalShelter.repository.ClientRepository;
import pro.sky.animalShelter.service.ReportPhotoService;

import java.util.List;


/**
 * Класс, в котором принимаются ответы от пз, они обрабатываются, после чего выдается ответ.
 * Метод обработки и отправки данных  {@link #process(List)}
 */

@Service
public class TelegramBotUpdatesListener implements UpdatesListener {

    private Logger logger = LoggerFactory.getLogger(TelegramBotUpdatesListener.class);

    @Autowired
    private ReportPhotoService reportPhotoService;
    private final ClientRepository clientRepository;
    private final TelegramBot telegramBot;
    private final Buttons buttons;

    public TelegramBotUpdatesListener(ClientRepository clientRepository, TelegramBot telegramBot, Buttons buttons) {
        this.clientRepository = clientRepository;
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
//                long photoSizeCount = Arrays.stream(update.message().photo()).count();
//                long photoIndex = photoSizeCount > 1 ? photoSizeCount - 1 : 0;
//                byte[] file = Arrays.stream(update.message().photo()).toList().get((int) photoIndex).fileId().getBytes();
//                reportPhotoService.saveReport(file,update.message().caption());

                try {
                    logger.info("Processing update: {}", update);
                    buttons.ButtonsStage_0(update);
                    buttons.buttonsStage_volunteer(update);
                } catch (Exception e) {
                    logger.error("update not correct");
                }
            } else if (update.callbackQuery() != null) {
                try {
                    long chat_Id = update.callbackQuery().message().chat().id();
                    String text = update.callbackQuery().data();
                    if (text.equalsIgnoreCase("/a1")) {
                        buttons.buttonsStage_1(update);
                    } else if (text.equalsIgnoreCase("/a2")) {
                        buttons.buttonsStage_2(update);
                    } else if (update.callbackQuery().data().equalsIgnoreCase("/a4")) {
                        telegramBot.execute(new SendMessage(update.callbackQuery().message().chat().id(), Constants.PHONE_VOLUNTEER));
                    }
                    text = update.callbackQuery().data();
                    if (text.equalsIgnoreCase("/b1")) {
                        telegramBot.execute(new SendMessage(chat_Id, Constants.INFO_SHELTER));
                    } else if (text.equalsIgnoreCase("/b2")) {
                        telegramBot.execute(reportPhotoService.sendReportPhoto(chat_Id, reportPhotoService.getAddressPhoto()));
                    } else if (text.equalsIgnoreCase("/b3")) {
                        clientRepository.save(new Client(update.callbackQuery().message().chat().id(),
                                update.callbackQuery().message().chat().username()));
                        telegramBot.execute(new SendMessage(chat_Id, Constants.CALL_BACK));
                        if (update.message().contact().phoneNumber() != null) {
                            clientRepository.findByChatId(chat_Id).setPhone(update.callbackQuery().message().contact().phoneNumber());
                        }
                    } else if (text.equalsIgnoreCase("/b4")) {
                        telegramBot.execute(new SendMessage(chat_Id, Constants.PHONE_VOLUNTEER));
//                    }  else if (text.equalsIgnoreCase("/b1")) {
//                    telegramBot.execute(new SendMessage(update.callbackQuery().message().chat().id(), Constants.RULES_FOR_MEETING_ANIMALS));
                    } else if (text.equalsIgnoreCase("/a2")) {
                        telegramBot.execute(new SendMessage(chat_Id, Constants.RULES_FOR_MEETING_ANIMALS));
                    } else if (text.equalsIgnoreCase("/c1")) {
                        telegramBot.execute(new SendMessage(chat_Id, Constants.RECOMENDATIONS_FOR_HOUSE_FOR_ANIMAL));
                    } else if (text.equalsIgnoreCase("/c2")) {
                        telegramBot.execute(new SendMessage(update.callbackQuery().message().chat().id(), Constants.REASONS_FOR_REFUSAL));
                    } else if (text.equalsIgnoreCase("/c3")) {
                        clientRepository.save(new Client(update.callbackQuery().message().chat().id(),
                                update.callbackQuery().message().chat().username()));
                        if (update.message().contact().phoneNumber() != null) {
                            clientRepository.findByChatId(chat_Id).setPhone(update.callbackQuery().message().contact().phoneNumber());
                        }
                        telegramBot.execute(new SendMessage(chat_Id, Constants.CALL_BACK));
                    } else if (text.equalsIgnoreCase("/c4")) {
                        telegramBot.execute(new SendMessage(chat_Id, Constants.PHONE_VOLUNTEER));
                    }
                } catch (Exception e) {
                    logger.error("update not correct");
                }
            }
        });
        return UpdatesListener.CONFIRMED_UPDATES_ALL;

    }

}
