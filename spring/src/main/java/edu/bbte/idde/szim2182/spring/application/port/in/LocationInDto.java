package edu.bbte.idde.szim2182.spring.application.port.in;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;
import lombok.ToString;

@Data
@ToString(callSuper = true)
public class LocationInDto {
    @NotNull
    Long id;

    @NotEmpty
    @Size(max = 256)
    String startPoint;

    @NotEmpty
    @Size(max = 256)
    String endPoint;
}
