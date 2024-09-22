package pro.sky.animalShelter.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import pro.sky.animalShelter.entity.animal.Animal;

public interface AnimalRepository extends JpaRepository<Animal, Integer> {
    @Query(value = "UPDATE Animal SET name = :name, age = :age, isHealthy = :isHealthy, isVaccinated = :isVaccinated WHERE id = :id")
    Integer updateById(@Param("id") Integer id,
                       @Param("name") String name,
                       @Param("age") Integer age,
                       @Param("isHealthy") Boolean isHealthy);
}

