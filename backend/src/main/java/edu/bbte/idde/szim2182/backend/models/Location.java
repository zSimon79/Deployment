package edu.bbte.idde.szim2182.backend.models;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.List;

@Getter
@Setter
@ToString
public class Location extends BaseEntity {
    private String startPoint;
    private String endPoint;
    private List<Hike> hikes;

    public Location() {
        super(0L);
    }

    public Location(String startPoint, String endPoint) {
        super(0L);
        this.startPoint = startPoint;
        this.endPoint = endPoint;
    }


    public String getStartPoint() {
        return startPoint;
    }

    public void setStartPoint(String startPoint) {
        this.startPoint = startPoint;
    }

    public String getEndPoint() {
        return endPoint;
    }

    public void setEndPoint(String endPoint) {
        this.endPoint = endPoint;
    }

    @Override
    public String toString() {
        return "Location{"
                + "id=" + getId()
                + ", startPoint='" + startPoint + '\''
                + ", endPoint='" + endPoint + '\''
                + '}';
    }

}
