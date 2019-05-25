package com.accenture.bikecity.service;

import com.accenture.bikecity.service.dto.BicycleDTO;

import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing {@link com.accenture.bikecity.domain.Bicycle}.
 */
public interface BicycleService {

    /**
     * Save a bicycle.
     *
     * @param bicycleDTO the entity to save.
     * @return the persisted entity.
     */
    BicycleDTO save(BicycleDTO bicycleDTO);

    /**
     * Get all the bicycles.
     *
     * @return the list of entities.
     */
    List<BicycleDTO> findAll();


    /**
     * Get the "id" bicycle.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<BicycleDTO> findOne(Long id);

    /**
     * Delete the "id" bicycle.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
