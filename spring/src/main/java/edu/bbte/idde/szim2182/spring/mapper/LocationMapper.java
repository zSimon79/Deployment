package edu.bbte.idde.szim2182.spring.mapper;

import edu.bbte.idde.szim2182.spring.application.port.in.LocationInDto;
import edu.bbte.idde.szim2182.spring.application.port.out.LocationOutDto;
import edu.bbte.idde.szim2182.spring.application.domain.entity.Location;
import org.mapstruct.IterableMapping;
import org.mapstruct.Mapper;

import java.util.Collection;

@Mapper(componentModel = "spring")
public interface LocationMapper {

    Location dtoToLocation(LocationInDto dto);

    LocationOutDto locationToDto(Location location);

    @IterableMapping(elementTargetType = LocationOutDto.class)
    Collection<LocationOutDto> locationsToDtos(Collection<Location> locations);


}
