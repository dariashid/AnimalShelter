package pro.sky.animalShelter.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

import java.io.File;

/**
 * Отчеты о животном
 * {@code animalPhoto} - фотография животного; <br>
 * {@code ration} - рацион животного; <br>
 */

@Entity
public class Report {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long reportId;
    private File animalPhoto;
    private String ration;

    private String healthAndChanges;

    public long getReportId() {
        return reportId;
    }

    public void setReportId(long reportId) {
        this.reportId = reportId;
    }

    public File getAnimalPhoto() {
        return animalPhoto;
    }

    public void setAnimalPhoto(File animalPhoto) {
        this.animalPhoto = animalPhoto;
    }

    public String getRation() {
        return ration;
    }

    public void setRation(String ration) {
        this.ration = ration;
    }
}
