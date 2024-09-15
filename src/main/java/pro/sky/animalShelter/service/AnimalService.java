package pro.sky.animalShelter.service;

import org.springframework.stereotype.Service;
import pro.sky.animalShelter.entity.animal.Animal;
import pro.sky.animalShelter.repository.AnimalRepository;
import pro.sky.animalShelter.exception.UploadFileException;
import pro.sky.animalShelter.repository.ReportPhotoRepository;
import pro.sky.animalShelter.repository.ReportRepository;

import java.util.List;
import java.util.Optional;

@Service
public class AnimalService {
    private final AnimalRepository animalRepository;

    public AnimalService(AnimalRepository animalRepository) {
        this.animalRepository = animalRepository;
    }

    public Optional<Animal> findById(int id) {
        return animalRepository.findById(id);
    }

    public boolean save(Animal animal) {
        if (!(animal == null
                || (animal.getName() == null || animal.getName().isBlank())
                || (animal.getAge() == null || animal.getAge() < 0)
                || animal.getHealthy() == null)) {
            animalRepository.save(animal);
            return true;
        }
        return false;
    }

    public List<Animal> findAll() {
        return animalRepository.findAll();
    }

    public Boolean deleteById(int id) {
        Optional<Animal> findAnimalById = findById(id);
        if (findAnimalById.isEmpty()) {
            return false;
        }
        animalRepository.deleteById(id);
        return true;
    }

    public Integer updateById(Integer id, String name, Integer age, Boolean isHealthy) {
        Optional<Animal> animal = findById(id);
        if (animal.isEmpty()
                || (name == null || name.isBlank())
                || (age == null || age < 0)
                || isHealthy == null) {
            return 0;
        } else {
            animalRepository.updateById(id, name, age, isHealthy);
            return 1;
        }
    }

}
