package com.accenture.bikecity.service;

import com.accenture.bikecity.service.dto.BikeTransactionDTO;

import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing {@link com.accenture.bikecity.domain.BikeTransaction}.
 */
public interface BikeTransactionService {

    /**
     * Save a bikeTransaction.
     *
     * @param bikeTransactionDTO the entity to save.
     * @return the persisted entity.
     */
    BikeTransactionDTO save(BikeTransactionDTO bikeTransactionDTO);

    /**
     * Get all the bikeTransactions.
     *
     * @return the list of entities.
     */
    List<BikeTransactionDTO> findAll();

    List<BikeTransactionDTO> findAllByUserId(Long id);


    /**
     * Get the "id" bikeTransaction.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<BikeTransactionDTO> findOne(Long id);

    /**
     * Delete the "id" bikeTransaction.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
