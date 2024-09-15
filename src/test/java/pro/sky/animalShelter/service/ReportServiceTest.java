package pro.sky.animalShelter.service;

import com.pengrad.telegrambot.TelegramBot;
import com.pengrad.telegrambot.model.Message;
import com.pengrad.telegrambot.model.Update;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import pro.sky.animalShelter.repository.ClientRepository;
import  pro.sky.animalShelter.model.Report;
import  pro.sky.animalShelter.repository.ClientRepository;
import static org.mockito.Mockito.mock;


@ExtendWith(MockitoExtension.class)

public class ReportServiceTest {


    @Test
    public void informationOfShelter_positive() {
        // фиктивный объект `Update`
        Update update = mock(Update.class);

        // Создаем фиктивный объект `ClientRepository`
        ClientRepository clientRepository = mock(ClientRepository.class);

        // фиктивный объект `TelegramBot`
        TelegramBot telegramBot = mock(TelegramBot.class);

        // экземпляр класса `ReportService`
        ReportService reportService = new ReportService(clientRepository, telegramBot);

        // вызов метода `informationOfShelter()`
        reportService.informationOfShelter(update);

    }
}