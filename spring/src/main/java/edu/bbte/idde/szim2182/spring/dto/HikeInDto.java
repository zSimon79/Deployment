package edu.bbte.idde.szim2182.spring.dto;

import edu.bbte.idde.szim2182.spring.model.Location;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import lombok.Data;
import lombok.ToString;

@Data
@ToString(callSuper = true)
public class HikeInDto {


    @NotEmpty
    @Size(max = 256)
    String name;

    @NotEmpty
    @Size(max = 1024)
    String description;

    @Positive
    Integer difficultyLevel;

    @Valid
    Location location;

    @NotNull
    Long locationId;

    @Positive
    Double distance;
}
