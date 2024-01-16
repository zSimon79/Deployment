package edu.bbte.idde.szim2182.spring.dto;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class LocationInDto {
    @NotEmpty
    @Size(max = 256)
    String startPoint;

    @NotEmpty
    @Size(max = 256)
    String endPoint;
}
