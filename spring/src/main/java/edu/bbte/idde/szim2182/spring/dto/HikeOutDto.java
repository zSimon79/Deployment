package edu.bbte.idde.szim2182.spring.dto;

import edu.bbte.idde.szim2182.spring.models.Location;
import lombok.Data;

@Data
public class HikeOutDto {
    Long id;
    String name;
    String description;
    Integer difficultyLevel;
    Location location;
    Long locationId;
    Double distance;
}
