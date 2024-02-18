package edu.bbte.idde.szim2182.spring.application.domain.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "locations")
public class Location extends BaseEntity {
    @Column(nullable = false)
    private String startPoint;

    @Column(nullable = false)
    private String endPoint;

    public Location() {
        super();
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
