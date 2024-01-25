package edu.bbte.idde.szim2182.spring.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.Cascade;


@Getter
@Setter
@Entity
@Table(name = "hikes")
public class Hike extends BaseEntity {


    @Column(nullable = false)
    private String name;
    private String description;
    private Integer difficultyLevel;

    @Cascade(org.hibernate.annotations.CascadeType.SAVE_UPDATE)
    @ManyToOne
    @JoinColumn(name = "locationId")
    private Location location;
    private Double distance;

    public Hike() {
        super();
    }

    public Hike(Long id, String name, String description, Integer difficultyLevel, Location location, Double distance) {
        super(id);
        this.name = name;
        this.description = description;
        this.difficultyLevel = difficultyLevel;
        this.location = location;
        this.distance = distance;
    }

    public int getDifficultyLevel() {
        return difficultyLevel;
    }

    public double getDistance() {
        return distance;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setDifficultyLevel(Integer difficultyLevel) {
        this.difficultyLevel = difficultyLevel;
    }


    public void setDistance(Double distance) {
        this.distance = distance;
    }


    @Override
    public String toString() {
        return "Hike{"
                + "id=" + getId()
                + ", name='" + name + '\''
                + ", description='" + description + '\''
                + ", difficultyLevel=" + difficultyLevel
                + ", location=" + location
                + ", distance=" + distance
                + '}';
    }

}
