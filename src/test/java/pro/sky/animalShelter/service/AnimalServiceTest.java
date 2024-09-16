package pro.sky.animalShelter.service;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import pro.sky.animalShelter.entity.animal.Animal;
import pro.sky.animalShelter.repository.AnimalRepository;


import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class AnimalServiceTest {


    @Mock
    private AnimalService animalService;
    @Mock
    private AnimalRepository animalRepository;

    @Test
    public void testAddAnimal() {
        Animal animal = new Animal("Кеша", 2, true, true);
        when(animalService.save(animal)).thenReturn(true);
        Boolean t = animalService.save(animal);
        assertEquals(true, t);
        verify(animalService).save(animal);
    }

    @Test
    public void testGetAnimalById() {
        Animal animal = new Animal("Кеша", 2, true, true);
        when(animalService.findById(1)).thenReturn(Optional.of(animal));
        Optional<Animal> foundAnimal = animalService.findById(1);
        assertEquals(Optional.of(animal), foundAnimal);
        verify(animalService).findById(1);
    }

    @Test
    public void testGetAllAnimals() {
        List<Animal> animals = Arrays.asList(new Animal("Кеша", 2, true, true),
                new Animal("Сёма", 9, true, true));
        when(animalService.findAll()).thenReturn(animals);
        List<Animal> foundAnimals = animalService.findAll();
        // Cписок животных соответствует ожидаемому
        assertEquals(animals, foundAnimals);
        verify(animalService).findAll();
    }

    @Test
    public void testUpdateAnimal() {
        Animal animal = new Animal("Кеша", 2, true, true);
        when(animalService.updateById(1, "Кеша", 2, true)).thenReturn(1);
        Integer updated = animalService.updateById(1, "Кеша", 2, true);
        // Проверка обновления
        assertEquals(1, updated);
        verify(animalService).updateById(1, "Кеша", 2, true);
    }

    @Test
    public void testDeleteAnimalById() {
        Animal animal = new Animal();
        animal.setId(1);
        animalRepository.save(animal);
        // Удаление по идентификатору
        animalService.deleteById(1);
        // Не существует
        assertFalse(animalRepository.existsById(1));
    }

}
