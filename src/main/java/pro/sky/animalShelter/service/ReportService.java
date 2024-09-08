package pro.sky.animalShelter.service;

import com.pengrad.telegrambot.TelegramBot;
import com.pengrad.telegrambot.model.Update;
import org.springframework.stereotype.Service;
import pro.sky.animalShelter.repository.ClientRepository;

@Service
public class ReportService {
    private final ClientRepository clientRepository;
    private final TelegramBot telegramBot;

    public ReportService(ClientRepository clientRepository, TelegramBot telegramBot) {
        this.clientRepository = clientRepository;
        this.telegramBot = telegramBot;
    }

    public void informationOfShelter(Update update) {

    }
}
