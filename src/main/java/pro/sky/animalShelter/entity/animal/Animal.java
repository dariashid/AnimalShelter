package pro.sky.animalShelter.entity.animal;

import jakarta.persistence.Entity;
import pro.sky.animalShelter.entity.NamedEntity;

@Entity
public class Animal extends NamedEntity {
    private Integer age;
    private Boolean isHealthy;
    private Boolean isAdopted;

    public Boolean getAdopted() {
        return isAdopted;
    }

    public void setAdopted(Boolean adopted) {
        isAdopted = adopted;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public Boolean getHealthy() {
        return isHealthy;
    }

    public void setHealthy(Boolean healthy) {
        isHealthy = healthy;
    }
}
