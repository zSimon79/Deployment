package edu.bbte.idde.szim2182.backend;

public class Hike {
    private final int id;
    private final String name;
    private final String description;
    private final int difficultyLevel;
    private final String startPoint;
    private final String endPoint;
    private final double distance;

    public Hike(int id, String name, String description, int difficultyLevel, String startPoint,
                String endPoint, double distance) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.difficultyLevel = difficultyLevel;
        this.startPoint = startPoint;
        this.endPoint = endPoint;
        this.distance = distance;
    }

    public int getId() {
        return id;
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


    // toString metódus a könnyebb megjelenítéshez
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
