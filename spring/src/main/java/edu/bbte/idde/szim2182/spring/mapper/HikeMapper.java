package edu.bbte.idde.szim2182.spring.mapper;

import edu.bbte.idde.szim2182.spring.application.port.in.HikeInDto;
import edu.bbte.idde.szim2182.spring.application.port.out.HikeOutDto;
import edu.bbte.idde.szim2182.spring.application.domain.entity.Hike;
import org.mapstruct.IterableMapping;
import org.mapstruct.Mapper;

import java.util.Collection;


@Mapper(componentModel = "spring")
public interface HikeMapper {

    Hike dtoToHike(HikeInDto dto);

    HikeOutDto hikeToDto(Hike hike);

    @IterableMapping(elementTargetType = HikeOutDto.class)
    Collection<HikeOutDto> hikesToDtos(Collection<Hike> hikes);

}
