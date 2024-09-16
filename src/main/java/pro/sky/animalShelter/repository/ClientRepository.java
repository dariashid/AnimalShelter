package pro.sky.animalShelter.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import pro.sky.animalShelter.model.Client;

public interface ClientRepository extends JpaRepository<Client, Long> {

        public Client findByChatId(long chat_Id);
    }

