package pro.sky.animalShelter.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import pro.sky.animalShelter.model.Photo;

import java.io.IOException;

@RestController
public class PhotoController {

    private final PhotoService photoService;

    public PhotoController(PhotoService photoService) {
        this.photoService = photoService;
    }

    @Operation(
            summary = "Выводит фото собаки по айди животного",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Фотография животного"
                    )
            }
    )
    @GetMapping("/{id}/photo-from-db")
    public ResponseEntity<byte[]> downloadPhoto(@PathVariable Long id) {
        Photo photo = photoService.findAnimalPhoto(id);
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.parseMediaType(photo.getMediaType()));
        headers.setContentLength(photo.getData().length);
        return ResponseEntity.status(HttpStatus.OK).headers(headers).body(photo.getData());
    }
    @Operation(
            summary = "Выводит фотографии всех животных",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Фотографии всех животных"
                    )
            }
    )
    @GetMapping(value = "/all-photos-from-db")
    public ResponseEntity<List<Photo>> downloadAllPhotos(@RequestParam("page") Integer pageNumber,
                                                         @RequestParam("size") Integer pageSize) {
        return ResponseEntity.ok(photoService.findAllAnimalsPhoto(pageNumber,pageSize));

    }
    @Operation(
            summary = "Сохраняет фотграфию животного",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Фотография сохранена"
                    )
            }
    )
    @PostMapping(value = "/{dogId}/save-animal-photo", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<String> savePhoto(@RequestParam Long dogId,
                                            @RequestBody MultipartFile multipartFile) throws IOException {
        photoService.uploadPhoto(dogId,multipartFile);
        return ResponseEntity.ok().build();
    }

}
