package pro.sky.animalShelter.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pro.sky.animalShelter.entity.animal.Animal;
import pro.sky.animalShelter.exception.AnimalAlreadyExistsException;
import pro.sky.animalShelter.service.AnimalService;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/animal")
public class AnimalController {
    @Autowired
    private AnimalService animalService;

    @Operation(
            summary = "Добавление питомца в приют"

    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Питомец добавлен"
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "Параметры запроса отсутствуют или имеют некорректный формат"
            )
    })
    @PostMapping("/addAnimal")
    public void addAnimal @RequestBody
    Animal animal) {
        if (animalService.getAnimalById(animal.getId()).isPresent()) {
            throw new AnimalAlreadyExistsException("Такой питомец уже существует");
        }
        animalService.addAnimal(animal);
    }

    @Operation(
            summary = "Получение информации о питомце"
    )

    @GetMapping("/getInfoAnimal/{id}")
    public ResponseEntity<Optional<Animal>> getAnimal(@PathVariable long id) {
        Optional<Animal> animalToFind = animalService.getAnimalById(id);
        if (animalToFind.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(animalService.getAnimalById(id));
    }

    @Operation(
            summary = "Коррекция информации о питомце"
    )

    @PutMapping("/updateInfoAnimal")
    public ResponseEntityAnimal> updateAnimal(@RequestBody Animal animal) {
        Animal animalToUpdate = animalService.updateAnimal(animal);
        if (animalToUpdate == null) {
            return ResponseEntity.badRequest().build();
        }
        return ResponseEntity.ok(animalToUpdate);

    }

    @Operation(
            summary = "Удаление данных о питомце"
    )

    @DeleteMapping("/deleteDog/{id}")
    public ResponseEntity<Void> deleteAnimal(@PathVariable long id) {
        animalService.deleteAnimal(id);
        return ResponseEntity.ok().build();
    }

    @Operation(
            summary = "Получение списка всех питомцев"
    )

    @GetMapping("/getListOfAllAnimals")
    public ResponseEntity<List<Animal>> getListOfAllAnimals() {
        if (animalService.getAll().isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(animalService.getAll());
    }

}
