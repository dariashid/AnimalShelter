package pro.sky.animalShelter.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pro.sky.animalShelter.model.AddressPhoto;

@Repository
public interface AddressPhotoRepository extends JpaRepository<AddressPhoto, Long> {
}
