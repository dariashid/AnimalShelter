package pro.sky.animalShelter.controller;

import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pro.sky.animalShelter.service.AnimalService;

@RestController
@RequestMapping("/animal")
@Tag(
        name = "Животные",
        description = "CRUD-операции для работы с животными"
)
@ApiResponses(value = {
        @ApiResponse(
                responseCode = "500",
                description = "Произошла ошибка"
        )
})
public class AnimalController {
    private final AnimalService animalService;

    public animalController(AnimalService animalService) {
        this.animalService = animalService;
    }
}
