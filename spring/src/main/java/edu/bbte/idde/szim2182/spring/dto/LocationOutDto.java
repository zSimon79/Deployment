package edu.bbte.idde.szim2182.spring.dto;

import lombok.Data;
import lombok.ToString;

@Data
@ToString(callSuper = true)
public class LocationOutDto {
    Long id;
    private String startPoint;
    private String endPoint;
}
