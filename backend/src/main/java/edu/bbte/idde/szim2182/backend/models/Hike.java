package edu.bbte.idde.szim2182.backend.models;


public class Hike extends BaseEntity {
    private String name;
    private String description;
    private Integer difficultyLevel;
    private String startPoint;
    private String endPoint;
    private Double distance;

    public Hike() {
        super(0L);
    }

    public Hike(String name, String description, Integer difficultyLevel, String startPoint,
                String endPoint, Double distance) {
        super(0L);
        this.name = name;
        this.description = description;
        this.difficultyLevel = difficultyLevel;
        this.startPoint = startPoint;
        this.endPoint = endPoint;
        this.distance = distance;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public int getDifficultyLevel() {
        return difficultyLevel;
    }

    public String getStartPoint() {
        return startPoint;
    }

    public String getEndPoint() {
        return endPoint;
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

    public void setStartPoint(String startPoint) {
        this.startPoint = startPoint;
    }

    public void setEndPoint(String endPoint) {
        this.endPoint = endPoint;
    }

    public void setDistance(Double distance) {
        this.distance = distance;
    }


    @Override
    public String toString() {
        return "Hike{"
                + "id=" + id
                + ", name='" + name + '\''
                + ", description='" + description + '\''
                + ", difficultyLevel=" + difficultyLevel
                + ", startPoint='" + startPoint + '\''
                + ", endPoint='" + endPoint + '\''
                + ", distance=" + distance
                + '}';
    }
}
