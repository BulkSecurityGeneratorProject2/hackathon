package com.accenture.bikecity.service.mapper;

import com.accenture.bikecity.domain.*;
import com.accenture.bikecity.service.dto.ParkingDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link Parking} and its DTO {@link ParkingDTO}.
 */
@Mapper(componentModel = "spring", uses = {})
public interface ParkingMapper extends EntityMapper<ParkingDTO, Parking> {



    default Parking fromId(Long id) {
        if (id == null) {
            return null;
        }
        Parking parking = new Parking();
        parking.setId(id);
        return parking;
    }
}
