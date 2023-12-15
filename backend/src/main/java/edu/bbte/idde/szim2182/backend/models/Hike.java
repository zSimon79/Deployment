package edu.bbte.idde.szim2182.backend.models;

import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
public class Hike extends BaseEntity {
    private String name;
    private String description;
    private Integer difficultyLevel;
    private Location location; // Replace startPoint and endPoint with Location
    private Long locationId;
    private Double distance;

    public Hike() {
        super(0L);
    }

    public Hike(Long id, String name, String description, Integer difficultyLevel, Location location, Double distance) {
        super(id);
        this.name = name;
        this.description = description;
        this.difficultyLevel = difficultyLevel;
        this.location = location;
        this.locationId = location.id;
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

    public void setLocationId(Long locationId) {
        this.locationId = locationId;
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
