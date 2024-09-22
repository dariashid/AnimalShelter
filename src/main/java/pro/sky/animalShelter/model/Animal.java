package pro.sky.animalShelter.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Data;

import java.util.Objects;

@Data
@Entity
public class Animal {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private String name;
    private int age;
    private boolean isHealthy;
    private boolean isAdopted;
    @OneToOne
    @JoinColumn(name = "client_id")
    @JsonIgnore
    private Client client;
    @OneToOne
    @JoinColumn(name = "photo_id")
    @JsonIgnore
    private Photo photo;

    public Animal(long id, String name, int age, boolean isHealthy, boolean isAdopted, Client client) {
        this.id = id;
        this.name = name;
        this.age = age;
        this.isHealthy = isHealthy;
        this.isAdopted = isAdopted;
        this.client = client;
    }
    public Animal(long id, String name, int age) {
        this.id = id;
        this.name = name;
        this.age = age;
    }
    public Animal() {
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public boolean isHealthy() {
        return isHealthy;
    }

    public void setHealthy(boolean healthy) {
        isHealthy = healthy;
    }

    public boolean isAdopted() {
        return isAdopted;
    }

    public void setAdopted(boolean adopted) {
        isAdopted = adopted;
    }

    public Client getClient() {
        return client;
    }

    public void setClient(Client client) {
        this.client = client;
    }

    public Photo getPhoto() {
        return photo;
    }

    public void setPhoto(Photo photo) {
        this.photo = photo;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Animal animal = (Animal) o;
        return id == animal.id && age == animal.age && isHealthy == animal.isHealthy && isAdopted == animal.isAdopted && Objects.equals(name, animal.name) && Objects.equals(client, animal.client);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, age, isHealthy, isAdopted, client);
    }

    @Override
    public String toString() {
        return "Animal{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", age=" + age +
                ", isHealthy=" + isHealthy +
                ", isAdopted=" + isAdopted +
                ", client=" + client +
                '}';
    }
}
