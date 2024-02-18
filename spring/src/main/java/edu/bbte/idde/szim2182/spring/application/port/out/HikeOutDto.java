package edu.bbte.idde.szim2182.spring.application.port.out;

import edu.bbte.idde.szim2182.spring.application.domain.entity.Location;
import lombok.Data;
import lombok.ToString;

@Data
@ToString(callSuper = true)
public class HikeOutDto {
    Long id;
    String name;
    String description;
    Integer difficultyLevel;
    Location location;
    Double distance;
}
