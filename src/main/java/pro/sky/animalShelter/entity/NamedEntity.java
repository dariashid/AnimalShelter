package pro.sky.animalShelter.entity;

import jakarta.persistence.Column;
import pro.sky.animalShelter.exception.ValidationException;
import pro.sky.animalShelter.service.Validation;

public class NamedEntity extends BaseEntity {
    @Column(name = "name")
    private String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        if (!Validation.validateBaseStr(name)) {
            throw new ValidationException(name);
        }
        this.name = name;
    }
}
