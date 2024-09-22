package pro.sky.animalShelter.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import pro.sky.animalShelter.entity.animal.Animal;

import java.util.List;
import java.util.Objects;

@Entity
public class Client {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @Column(name = "chat_Id")
    private long chatId;
    @Column(name = "name")
    private String name;
    @Column(name = "has_pet")
    private boolean hasPet;
    @Column(name = "phone")
    private String phone;
    @Column(name = "timer")
    private Integer timer;
    @Column(name = "probationary_period")
    private Integer probationaryPeriod;

    @OneToMany(mappedBy = "client")
    @JsonIgnore
    private List<Report> reports;

    @OneToOne
    @JsonIgnore
    private Animal animal;

    public Client(long chatId, String name) {
        this.chatId = chatId;
        this.name = name;

    }

    public Client(long chatId, String name, boolean hasPet, Integer timer, Animal animal, int probationaryPeriod) {
        this.chatId = chatId;
        this.name = name;
        this.hasPet = hasPet;
        this.timer = timer;
        this.animal = animal;
        this.probationaryPeriod = probationaryPeriod;
    }

    public List<Report> getReports() {
        return reports;
    }

    public void setReports(List<Report> reports) {
        this.reports = reports;
    }

    public Client() {

    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getChatId() {
        return chatId;
    }

    public void setChatId(long chatId) {
        this.chatId = chatId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean isHasPet() {
        return hasPet;
    }

    public void setHasPet(boolean hasPet) {
        this.hasPet = hasPet;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public Integer getTimer() {
        return timer;
    }


    public int getProbationaryPeriod() {
        return probationaryPeriod;
    }

    public void setProbationaryPeriod(int probationaryPeriod) {
        this.probationaryPeriod = probationaryPeriod;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Client client = (Client) o;
        return id == client.id && chatId == client.chatId && hasPet == client.hasPet && probationaryPeriod == client.probationaryPeriod && Objects.equals(name, client.name) && Objects.equals(phone, client.phone) && Objects.equals(timer, client.timer);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, chatId, name, hasPet, phone, timer, probationaryPeriod);
    }

    @Override
    public String toString() {
        return "Client{" +
                "id=" + id +
                ", chatId=" + chatId +
                ", name='" + name + '\'' +
                ", hasPet=" + hasPet +
                ", phone='" + phone + '\'' +
                ", timer=" + timer +
                ", probationaryPeriod=" + probationaryPeriod +
                '}';
    }

    public Animal getAnimal() {
        return animal;
    }

    public void setAnimal(Animal animal) {
        this.animal = animal;
    }

    public void setTimer(Integer timer) {
        this.timer = timer;
    }
}