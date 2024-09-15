package pro.sky.animalShelter.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pro.sky.animalShelter.model.Report;

@Repository
public interface ReportRepository extends JpaRepository<Report, Long> {
}
