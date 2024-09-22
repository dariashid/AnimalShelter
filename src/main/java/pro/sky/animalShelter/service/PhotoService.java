package pro.sky.animalShelter.service;

import com.pengrad.telegrambot.TelegramBot;
import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.request.SendPhoto;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import pro.sky.animalShelter.model.Animal;
import pro.sky.animalShelter.model.Photo;
import pro.sky.animalShelter.repository.AnimalRepository;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

import static java.nio.file.StandardOpenOption.CREATE_NEW;

@Service
@Transactional
public class PhotoService {
    @Value("${path.to.animal-photo.folder}")
    private String photoDir;

    private final PhotoRepository photoRepository;

    private final AnimalRepository animalRepository;

    private final TelegramBot telegramBot;

    public PhotoService(PhotoRepository photoRepository, AnimalRepository animalRepository, TelegramBot telegramBot) {
        this.photoRepository = photoRepository;
        this.animalRepository = animalRepository;
        this.telegramBot = telegramBot;
    }

    /**
     * Метод реализует загрузку фотографий собак.
     * @param animalId айди собаки
     * @param file файл фотографии
     * @throws IOException
     */
    public void uploadPhoto(Long animalId, MultipartFile file) throws IOException {
        Animal animal = animalRepository.getReferenceById(animalId);
        Path filePath = Path.of(photoDir, animalId + "." + getExtension(file.getOriginalFilename()));
        Files.createDirectories(filePath.getParent());
        Files.deleteIfExists(filePath);
        try (
                InputStream is = file.getInputStream();
                OutputStream os = Files.newOutputStream(filePath, CREATE_NEW);
                BufferedInputStream bis = new BufferedInputStream(is, 1024);
                BufferedOutputStream bos = new BufferedOutputStream(os, 1024)
        ) {
            bis.transferTo(bos);
        }
        Photo photo = findAnimalPhoto(animalId);
        photo.setAnimal(animal);
        photo.setPath(filePath.toString());
        photo.setFileSize(file.getSize());
        photo.setMediaType(file.getContentType());
        photo.setData(file.getBytes());
        animal.setPhoto(photo);
        photoRepository.save(photo);
    }

    /**
     * Берет расширение от имени файла
     * @param fileName - имя файла
     * @return - возвращаяет расширение файла в формате строки
     */
    private String getExtension(String fileName) {
        return fileName.substring(fileName.lastIndexOf(".") + 1);
    }

    /**
     * Поиск фотографии по айди собаки
     * @param animalId айди собаки
     * @return фотография собаки, объект класса {@link Photo}
     */
    public Photo findAnimalPhoto(Long animalId) {
        return photoRepository.findByAnimalId(animalId).orElse(new Photo());
    }

    /**
     * Выводит все фотографии собак, которые есть в базе
     * @param pageNumber номер страницы
     * @param pageSize количество фотографий на странице
     * @return заданное количество фотграфий
     */
    public List<Photo> findAllAnimalsPhoto(Integer pageNumber, Integer pageSize) {
        return photoRepository
                .findAll(PageRequest.of(pageNumber - 1, pageSize))
                .getContent();
    }

    /**
     * Создает объект класса {@link SendPhoto} для отправки фотографии в чат
     * @param chatId айди чата
     * @param photo фото собаки, объект класса {@link Photo}
     * @return объект класса {@link SendPhoto}
     */
    public SendPhoto createSendPhoto(Long chatId, Photo photo) {
        byte[] filePhoto = photo.getData();
        return new SendPhoto(chatId, filePhoto);
    }

    /**
     * Отправляет фотографию собаки в чат в ответ на команду /g из callbackQuery.data
     * @param update объект класса {@link Update}
     */
    public void sendAnimalPhoto(Update update) {
        String callback = update.callbackQuery().data();
        if (callback.startsWith("/g")) {
            Long idAnimal = Long.parseLong(callback.replace("/g", ""));
            telegramBot.execute(createSendPhoto(
                    update.callbackQuery().message().chat().id(),
                    photoRepository.findByAnimalId(idAnimal).get()));
        }
    }
}