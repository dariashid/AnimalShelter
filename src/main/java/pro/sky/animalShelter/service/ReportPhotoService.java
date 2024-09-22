package pro.sky.animalShelter.service;

import com.pengrad.telegrambot.TelegramBot;
import com.pengrad.telegrambot.model.PhotoSize;
import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.request.GetFile;
import com.pengrad.telegrambot.request.SendMessage;
import com.pengrad.telegrambot.request.SendPhoto;
import com.pengrad.telegrambot.response.SendResponse;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;
import pro.sky.animalShelter.exception.UploadFileException;
import pro.sky.animalShelter.model.Client;
import pro.sky.animalShelter.model.Report;
import pro.sky.animalShelter.model.ReportPhoto;
import pro.sky.animalShelter.repository.ClientRepository;
import pro.sky.animalShelter.repository.ReportPhotoRepository;
import pro.sky.animalShelter.repository.ReportRepository;

import java.io.*;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

import static java.nio.file.StandardOpenOption.CREATE_NEW;

/**
 * Сервис для работы с фотографиями к ежедневному отчету
 *-
 *-
 */
@Service
@Transactional
public class ReportPhotoService {
    @Value("${path.to.report-photo.folder}")
    private String reportPhotoDir;
    private final ReportPhotoRepository reportPhotoRepository;
    private final ReportRepository reportRepository;
    private final TelegramBot telegramBot;
    private final ClientRepository clientRepository;

    public ReportPhotoService(ReportPhotoRepository reportPhotoRepository,
                              ReportRepository reportRepository,
                              TelegramBot telegramBot,
                              ClientRepository clientRepository) {
        this.reportPhotoRepository = reportPhotoRepository;
        this.reportRepository = reportRepository;
        this.telegramBot = telegramBot;
        this.clientRepository = clientRepository;
    }

    /**
     * Метод для загрузки фото в базу данных и на жесткий диск
     * @param reportId - айди ежедневного отчета
     * @param file - файл фотографии
     * @throws IOException - выбрасывается исключение если файл некорректный или отсутвует
     */
    public void uploadReportPhoto(Long reportId, MultipartFile file) throws IOException {
        Report report = reportRepository.getReferenceById(reportId);
        Path filePath = Path.of(reportPhotoDir, reportId + "." + getExtension(file.getOriginalFilename()));
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
        ReportPhoto reportPhoto = new ReportPhoto();
        reportPhoto.setReport(report);
        reportPhoto.setData(file.getBytes());
        report.setAnimalPhoto(reportPhoto);
        reportPhotoRepository.save(reportPhoto);
    }

    /**
     * Берет расширение от имени файла
     * @param fileName - имя файла
     * @return - возращаяет расширение файла в формате строки
     */
    private String getExtension(String fileName) {
        return fileName.substring(fileName.lastIndexOf(".") + 1);
    }

    /**
     * Создает из отчета фотографию с описанием для отправки в телеграм бот
     * @param chatId айди чата
     * @param report объект класса {@link Report}
     * @return - возвращает экземпляр класса {@link SendPhoto}
     */
    public SendPhoto sendReportPhoto(Long chatId, Report report) {
        String caption = report.getCaption();
        byte[] filePhoto = report.getAnimalPhoto().getData();
        SendPhoto sendPhoto = new SendPhoto(chatId, filePhoto);
        sendPhoto.caption(caption);
        return sendPhoto;
    }

    /**
     * Возвращает фотографию с адресом проезда и режимом работы приюта
     * @return - возвращает экземпляр класса {@link Report}
     */
    public Report getAddressPhoto() {
        return reportRepository.findById(1L).orElseThrow();
    }

    /**
     * Возвращает пример формы ежедневного отчета о животном
     * @return - возвращает экземпляр класса {@link Report}
     */
    public Report getReportForm() {
        return reportRepository.findById(2L).orElseThrow();
    }


    /**
     * Сохраняет ежедневный отчет с фото в БД
     * @param file массив байт файла
     * @param caption текст отчета
     */
    private void saveReport(byte[] file, String caption, Long chatId) {
        ReportPhoto reportPhoto = new ReportPhoto();
        Report report = new Report();
        Client client = clientRepository.findByChatId(chatId);
        if (client == null) {
            SendResponse response = telegramBot.execute(new SendMessage(
                    chatId,
                    YOU_ARE_NOT_CLIENT));
        } else {
            reportPhoto.setData(file);
            reportPhotoRepository.save(reportPhoto);
            report.setCaption(caption);
            report.setAnimalPhoto(reportPhoto);
            report.setClient(client);
            reportRepository.save(report);
            client.getReports().add(report);
            client.setTimer(0);
            clientRepository.save(client);
            reportPhoto.setReport(report);
            reportPhotoRepository.save(reportPhoto);
        }
    }

    /**
     * Сохраняет отчет присланный пользователем в БД
     * @param update объект класса {@link Update}
     */
    public void uploadReportFromUser(Update update) {
        String caption = update.message().caption();
        var photo = update.message().photo();
        var document = update.message().document();
        if (caption == null) {
            SendResponse response = telegramBot.execute(
                    new SendMessage(update.message().chat().id(),
                            WARNING_NO_DESCRIPTION));
        }
        else if (photo != null ) {
            List<PhotoSize> list = Arrays.stream(photo).toList();
            String fileId = list.get(list.size() - 1).fileId();
            saveReport(getBytesFromFilByFileId(fileId),caption, update.message().chat().id());
        }
        else if (document != null) {
            String fileId = update.message().document().fileId();
            saveReport(getBytesFromFilByFileId(fileId),caption, update.message().chat().id());
        }
    }

    /**
     * Возращает массив байт по айди файла
     * @param fileId текстовое значение айди файла
     * @return массив байт
     */
    private byte[] getBytesFromFilByFileId(String fileId) {
        File file = telegramBot.execute(new GetFile(fileId)).file();
        try {
            return telegramBot.getFileContent(file);
        } catch (IOException e) {
            throw new UploadFileException(e);
        }
    }

    /**
     * Возращает весь список отчетов
     * @return лист отчетов, объектов класса {@link Report}
     */
    public List<Report> getAllReports() {
        return reportRepository.findAll();
    }

    /**
     * Возращает фотографию отчета по айди отчета
     * @param id айди отчета
     * @return объект класса {@link ReportPhoto}
     */
    public ReportPhoto getReportPhoto(Long id) {
        return reportPhotoRepository.findReportPhotoByReportId(id).orElseThrow();
    }

    /**
     * Поиск отчета по айди
     * @param reportId айди отчета
     * @return объект отчет {@link Report}
     */
    public Report findReportById(Long reportId) {
        return reportRepository.findById(reportId).orElseThrow();
    }

    /**
     * Помечает отчет, что он проверен волонтером, по айди.
     * @param reportId айди отчета
     * @return проверенный отчет {@link Report}
     */
    public Report verifiedReportById(Long reportId) {
        Report report = findReportById(reportId);
        report.setVerified(true);
        return reportRepository.save(report);
    }

    /**
     * Отправляет предупреждение пользователю, что отчет заполняется некорректно
     * @param clientId айди клиента
     */
    public void sendWarning(Long clientId) {
        SendResponse response =
                telegramBot.execute(
                        new SendMessage(clientRepository.findById(clientId).get().getChatId(),
                                WARNING_REPORT_NOT_CORRECT));
    }

}