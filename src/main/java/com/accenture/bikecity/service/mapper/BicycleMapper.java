package com.accenture.bikecity.service.mapper;

import com.accenture.bikecity.domain.*;
import com.accenture.bikecity.service.dto.BicycleDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link Bicycle} and its DTO {@link BicycleDTO}.
 */
@Mapper(componentModel = "spring", uses = {})
public interface BicycleMapper extends EntityMapper<BicycleDTO, Bicycle> {



    default Bicycle fromId(Long id) {
        if (id == null) {
            return null;
        }
        Bicycle bicycle = new Bicycle();
        bicycle.setId(id);
        return bicycle;
    }
}
