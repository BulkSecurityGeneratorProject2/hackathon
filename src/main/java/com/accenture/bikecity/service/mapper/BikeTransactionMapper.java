package com.accenture.bikecity.service.mapper;

import com.accenture.bikecity.domain.*;
import com.accenture.bikecity.service.dto.BikeTransactionDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link BikeTransaction} and its DTO {@link BikeTransactionDTO}.
 */
@Mapper(componentModel = "spring", uses = {})
public interface BikeTransactionMapper extends EntityMapper<BikeTransactionDTO, BikeTransaction> {



    default BikeTransaction fromId(Long id) {
        if (id == null) {
            return null;
        }
        BikeTransaction bikeTransaction = new BikeTransaction();
        bikeTransaction.setId(id);
        return bikeTransaction;
    }
}
