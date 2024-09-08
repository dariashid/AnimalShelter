package pro.sky.animalShelter.service;


import com.pengrad.telegrambot.request.SendPhoto;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Service;
import pro.sky.animalShelter.model.AddressPhoto;
import pro.sky.animalShelter.repository.AddressPhotoRepository;

import java.io.IOException;

@Service
public class AddressPhotoService {
    private final AddressPhotoRepository addressPhotoRepository;

    public AddressPhotoService(AddressPhotoRepository addressPhotoRepository) {
        this.addressPhotoRepository = addressPhotoRepository;
    }

    // Метод отправляет фото в ответ на команду
    public SendPhoto sendAddressPhoto(Long chatId,
                                      String filePath,
                                      String caption) throws IOException {
        SendPhoto sendPhoto = new SendPhoto(
                chatId,
                new ClassPathResource(filePath).getFile());
        sendPhoto.caption(caption);
        return sendPhoto;
    }

    // Находит путь к фото по номеру айди
    public AddressPhoto findAddressPhoto(Long addressPhotoId) {
        return addressPhotoRepository.findById(addressPhotoId).orElseThrow();
    }

}
