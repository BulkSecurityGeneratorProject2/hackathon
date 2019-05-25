package com.accenture.bikecity.service.impl;

import com.accenture.bikecity.service.BicycleService;
import com.accenture.bikecity.domain.Bicycle;
import com.accenture.bikecity.repository.BicycleRepository;
import com.accenture.bikecity.service.dto.BicycleDTO;
import com.accenture.bikecity.service.mapper.BicycleMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Service Implementation for managing {@link Bicycle}.
 */
@Service
@Transactional
public class BicycleServiceImpl implements BicycleService {

    private final Logger log = LoggerFactory.getLogger(BicycleServiceImpl.class);

    private final BicycleRepository bicycleRepository;

    private final BicycleMapper bicycleMapper;

    public BicycleServiceImpl(BicycleRepository bicycleRepository, BicycleMapper bicycleMapper) {
        this.bicycleRepository = bicycleRepository;
        this.bicycleMapper = bicycleMapper;
    }

    /**
     * Save a bicycle.
     *
     * @param bicycleDTO the entity to save.
     * @return the persisted entity.
     */
    @Override
    public BicycleDTO save(BicycleDTO bicycleDTO) {
        log.debug("Request to save Bicycle : {}", bicycleDTO);
        Bicycle bicycle = bicycleMapper.toEntity(bicycleDTO);
        bicycle = bicycleRepository.save(bicycle);
        return bicycleMapper.toDto(bicycle);
    }

    /**
     * Get all the bicycles.
     *
     * @return the list of entities.
     */
    @Override
    @Transactional(readOnly = true)
    public List<BicycleDTO> findAll() {
        log.debug("Request to get all Bicycles");
        return bicycleRepository.findAll().stream()
            .map(bicycleMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }


    /**
     * Get one bicycle by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<BicycleDTO> findOne(Long id) {
        log.debug("Request to get Bicycle : {}", id);
        return bicycleRepository.findById(id)
            .map(bicycleMapper::toDto);
    }

    /**
     * Delete the bicycle by id.
     *
     * @param id the id of the entity.
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete Bicycle : {}", id);
        bicycleRepository.deleteById(id);
    }
}
