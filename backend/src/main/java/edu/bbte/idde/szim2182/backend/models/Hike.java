package edu.bbte.idde.szim2182.backend.models;


public class Hike extends BaseEntity {
    private final String name;
    private final String description;
    private final Integer difficultyLevel;
    private final String startPoint;
    private final String endPoint;
    private final Double distance;

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
