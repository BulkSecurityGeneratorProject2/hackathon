package com.accenture.bikecity.service.impl;

import com.accenture.bikecity.service.BikeTransactionService;
import com.accenture.bikecity.domain.BikeTransaction;
import com.accenture.bikecity.repository.BikeTransactionRepository;
import com.accenture.bikecity.service.dto.BikeTransactionDTO;
import com.accenture.bikecity.service.mapper.BikeTransactionMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Service Implementation for managing {@link BikeTransaction}.
 */
@Service
@Transactional
public class BikeTransactionServiceImpl implements BikeTransactionService {

    private final Logger log = LoggerFactory.getLogger(BikeTransactionServiceImpl.class);

    private final BikeTransactionRepository bikeTransactionRepository;

    private final BikeTransactionMapper bikeTransactionMapper;

    public BikeTransactionServiceImpl(BikeTransactionRepository bikeTransactionRepository, BikeTransactionMapper bikeTransactionMapper) {
        this.bikeTransactionRepository = bikeTransactionRepository;
        this.bikeTransactionMapper = bikeTransactionMapper;
    }

    /**
     * Save a bikeTransaction.
     *
     * @param bikeTransactionDTO the entity to save.
     * @return the persisted entity.
     */
    @Override
    public BikeTransactionDTO save(BikeTransactionDTO bikeTransactionDTO) {
        log.debug("Request to save BikeTransaction : {}", bikeTransactionDTO);
        BikeTransaction bikeTransaction = bikeTransactionMapper.toEntity(bikeTransactionDTO);
        bikeTransaction = bikeTransactionRepository.save(bikeTransaction);
        return bikeTransactionMapper.toDto(bikeTransaction);
    }

    /**
     * Get all the bikeTransactions.
     *
     * @return the list of entities.
     */
    @Override
    @Transactional(readOnly = true)
    public List<BikeTransactionDTO> findAll() {
        log.debug("Request to get all BikeTransactions");
        return bikeTransactionRepository.findAll().stream()
            .map(bikeTransactionMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }

    public List<BikeTransactionDTO> findAllByUserId(Long id) {
        log.debug("Request to get all BikeTransactions");
        return bikeTransactionRepository.findAllByUserId(id).stream()
            .map(bikeTransactionMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }


    /**
     * Get one bikeTransaction by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<BikeTransactionDTO> findOne(Long id) {
        log.debug("Request to get BikeTransaction : {}", id);
        return bikeTransactionRepository.findById(id)
            .map(bikeTransactionMapper::toDto);
    }

    /**
     * Delete the bikeTransaction by id.
     *
     * @param id the id of the entity.
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete BikeTransaction : {}", id);
        bikeTransactionRepository.deleteById(id);
    }
}
