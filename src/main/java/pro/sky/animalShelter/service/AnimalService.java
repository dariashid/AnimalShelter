package pro.sky.animalShelter.service;

import lombok.Data;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

import pro.sky.animalShelter.model.Animal;
import pro.sky.animalShelter.model.Client;
import pro.sky.animalShelter.repository.AnimalRepository;


import java.util.List;
import java.util.Optional;

@Service
@Data
public class AnimalService {

    private final AnimalRepository animalRepository;

    public AnimalService(AnimalRepository animalRepository) {
        this.animalRepository = animalRepository;
    }


    /**
     * Сохраняет заданную сущность.
     * Используется метод репозитория {@link JpaRepository#save(Object)}
     *
     * @param animal сохраняемая сущность
     */
    public void addAnimal(Animal animal) {
        animalRepository.save(animal);
    }

    /**
     * Позволяет получить информацию о питомце
     *
     * @param id идентификатор питомца
     * @return Optional <Animal>
     */

    public Optional<Animal> getAnimalById(long id) {
        return animalRepository.findById(id);
    }

    /**
     * Позволяет обновить информацию о питомце
     *
     * @param animal сущность питомца
     * @return обновленные данные питомца
     */

    public Animal updateAnimal(Animal animal) {
        return animalRepository.save(animal);
    }

    /**
     * Позволяет удалить питомца из базы данных
     *
     * @param id идентификатор питомца
     */

    public void deleteAnimal(long id) {
        animalRepository.deleteById(id);
    }

    /**
     * Позволяет получить список всех питомцев
     *
     * @return список всех питомцев
     */

    public List<Animal> getAll() {
        return animalRepository.findAll();
    }

    /**
     * Позволяет взять под опеку питомца
     *
     * @param id идентификатор животного
     * @return обновленная информация о питомце
     */
    public Animal connectAnimalToClient(long id, Client client) {
        Animal animal = animalRepository.findById(id).get();
        animal.setAdopted(true);
        animal.setClient(client);
        return animalRepository.save(animal);
    }

    /**
     * Выводи список не усыновленных собак
     * @return список собак
     */
    public List<Animal> findAllAnimalIsAdopted() {
        return animalRepository.findAllIsAdopted();
    }

}