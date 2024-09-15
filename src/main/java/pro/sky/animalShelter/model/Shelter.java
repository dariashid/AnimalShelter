package pro.sky.animalShelter.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;


@Entity
public class Shelter {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String greeting;
    private String info;
    private String datingRules;
    private String recommendationsArranging;

    public Shelter(Long id, String greeting, String info, String datingRules,
                   String recommendationsArranging) {
        this.id = id;
        this.greeting = greeting;
        this.info = info;
        this.datingRules = datingRules;
        this.recommendationsArranging = recommendationsArranging;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getGreeting() {
        return greeting;
    }

    public void setGreeting(String greeting) {
        this.greeting = greeting;
    }

    public String getInfo() {
        return info;
    }

    public void setInfo(String info) {
        this.info = info;
    }

    public String getDatingRules() {
        return datingRules;
    }

    public void setDatingRules(String datingRules) {
        this.datingRules = datingRules;
    }

    public String getRecommendationsArranging() {
        return recommendationsArranging;
    }

    public void setRecommendationsArranging(String recommendationsArranging) {
        this.recommendationsArranging = recommendationsArranging;
    }
}
