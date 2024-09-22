package pro.sky.animalShelter.service;

import com.pengrad.telegrambot.TelegramBot;
import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.request.SendMessage;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;
import pro.sky.animalShelter.model.Client;
import pro.sky.animalShelter.model.Animal;
import pro.sky.animalShelter.repository.AnimalRepository;
import pro.sky.animalShelter.repository.ClientRepository;

/**
 * Сервис для работы с Клиентами
 */
@Service
@Transactional
public class ClientService {
    private final ClientRepository clientRepository;
    private final AnimalService animalService;
    private final AnimalRepository animalRepository;

    private final TelegramBot telegramBot;

    public ClientService(ClientRepository clientRepository, AnimalService animalService, AnimalRepository animalRepository, TelegramBot telegramBot) {
        this.clientRepository = clientRepository;
        this.animalService = animalService;
        this.animalRepository = animalRepository;
        this.telegramBot = telegramBot;
    }

    /**
     * Сохраняет клиента с питомцем или добавляет старому клиенту питомца
     * @param update апдейт телеграмма
     */
    public void saveClient(Update update) {
        String callback = update.callbackQuery().data();
        if (callback.startsWith("/x")) {
            Long idAnimal = Long.parseLong(callback.replace("/x", ""));
            Client client = getByChatId(update.callbackQuery().message().chat().id());
            Animal animal = animalService.getAnimalById(idAnimal).orElseThrow();
            if (client != null) {
                if (client.getAnimal() == null) {
                    client.setHasPet(true);
                    client.setProbationaryPeriod(30);
                    client.setTimer(0);
                    client.setAnimal(animal);
                    animal.setClient(client);
                    animal.setAdopted(true);
                    clientRepository.save(client);
                    animalRepository.save(animal);
                    telegramBot.execute(new SendMessage(
                            update.callbackQuery().message().chat().id(),
                            "Поздравляем вы усыновили питомца: " + animal.getName()));
                }
                telegramBot.execute(new SendMessage(
                        update.callbackQuery().message().chat().id(),
                        "У вас уже есть питомец: " + client.getAnimal().getName()));
            } else {
                animal.setClient(clientRepository.save(new Client(
                        update.callbackQuery().message().chat().id(),
                        update.callbackQuery().message().chat().username(),
                        true,
                        0,
                        animal,
                        30
                )));
                animal.setAdopted(true);
                animalRepository.save(animal);
                telegramBot.execute(new SendMessage(update.callbackQuery().message().chat().id(),
                        "Поздравляем вы усыновили питомца: " + animal.getName()));
            }
        }
    }

    /**
     * Получение клиента по чатайди
     * @param chatId чатайди клиента
     * @return объект класса клиента {@link Client}
     */
    public Client getByChatId(Long chatId) {
        return clientRepository.findByChatId(chatId);
    }
}
