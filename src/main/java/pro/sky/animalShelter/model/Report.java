package pro.sky.animalShelter.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;

import java.util.Objects;

/**
 * Сущность - ежедневный отчёт
 * {@code animalPhoto} - фотография животного; <br>
 * {@code ration} - рацион животного; <br>
 * {@code healthAndChanges} - состояние и изменение поведения животного; <br>
 */
@Entity
public class Report {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @OneToOne
    @JsonIgnore
    private ReportPhoto animalPhoto;
    private String caption;
    @ManyToOne
    private Client client;
    private boolean isVerified;

    public boolean isVerified() {
        return isVerified;
    }

    public void setVerified(boolean verified) {
        isVerified = verified;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long reportId) {
        this.id = reportId;
    }

    public ReportPhoto getAnimalPhoto() {
        return animalPhoto;
    }

    public void setAnimalPhoto(ReportPhoto animalPhoto) {
        this.animalPhoto = animalPhoto;
    }

    public String getCaption() {
        return caption;
    }

    public void setCaption(String caption) {
        this.caption = caption;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Report report = (Report) o;
        return id == report.id && Objects.equals(animalPhoto, report.animalPhoto) && Objects.equals(caption, report.caption);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, animalPhoto, caption);
    }

    @Override
    public String toString() {
        return "Report{" +
                "reportId=" + id +
                ", caption='" + caption + '\'' +
                '}';
    }

    public Client getClient() {
        return client;
    }

    public void setClient(Client client) {
        this.client = client;
    }
}