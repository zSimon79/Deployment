package edu.bbte.idde.szim2182.spring.models;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Location extends BaseEntity {
    private String startPoint;
    private String endPoint;

    public Location() {
        super(0L);
    }

    public Location(String startPoint, String endPoint) {
        super(0L);
        this.startPoint = startPoint;
        this.endPoint = endPoint;
    }

    public void setStartPoint(String startPoint) {
        this.startPoint = startPoint;
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
