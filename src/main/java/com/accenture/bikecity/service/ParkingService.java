package com.accenture.bikecity.service;

import com.accenture.bikecity.service.dto.ParkingDTO;

import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing {@link com.accenture.bikecity.domain.Parking}.
 */
public interface ParkingService {

    /**
     * Save a parking.
     *
     * @param parkingDTO the entity to save.
     * @return the persisted entity.
     */
    ParkingDTO save(ParkingDTO parkingDTO);

    /**
     * Get all the parkings.
     *
     * @return the list of entities.
     */
    List<ParkingDTO> findAll();


    /**
     * Get the "id" parking.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<ParkingDTO> findOne(Long id);

    /**
     * Delete the "id" parking.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
