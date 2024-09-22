package pro.sky.animalShelter.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pro.sky.animalShelter.model.Photo;

import java.util.Optional;

public interface PhotoRepository extends JpaRepository<Photo, Long> {
    Optional<Photo> findByDogId(Long id);
}
