package pro.sky.animalShelter.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pro.sky.animalShelter.model.ReportPhoto;

@Repository
public interface ReportPhotoRepository extends JpaRepository<ReportPhoto, Long> {

}
