package com.accenture.bikecity.service.impl;

import com.accenture.bikecity.service.ParkingService;
import com.accenture.bikecity.domain.Parking;
import com.accenture.bikecity.repository.ParkingRepository;
import com.accenture.bikecity.service.dto.ParkingDTO;
import com.accenture.bikecity.service.mapper.ParkingMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Service Implementation for managing {@link Parking}.
 */
@Service
@Transactional
public class ParkingServiceImpl implements ParkingService {

    private final Logger log = LoggerFactory.getLogger(ParkingServiceImpl.class);

    private final ParkingRepository parkingRepository;

    private final ParkingMapper parkingMapper;

    public ParkingServiceImpl(ParkingRepository parkingRepository, ParkingMapper parkingMapper) {
        this.parkingRepository = parkingRepository;
        this.parkingMapper = parkingMapper;
    }

    /**
     * Save a parking.
     *
     * @param parkingDTO the entity to save.
     * @return the persisted entity.
     */
    @Override
    public ParkingDTO save(ParkingDTO parkingDTO) {
        log.debug("Request to save Parking : {}", parkingDTO);
        Parking parking = parkingMapper.toEntity(parkingDTO);
        parking = parkingRepository.save(parking);
        return parkingMapper.toDto(parking);
    }

    /**
     * Get all the parkings.
     *
     * @return the list of entities.
     */
    @Override
    @Transactional(readOnly = true)
    public List<ParkingDTO> findAll() {
        log.debug("Request to get all Parkings");
        return parkingRepository.findAll().stream()
            .map(parkingMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }


    /**
     * Get one parking by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<ParkingDTO> findOne(Long id) {
        log.debug("Request to get Parking : {}", id);
        return parkingRepository.findById(id)
            .map(parkingMapper::toDto);
    }

    /**
     * Delete the parking by id.
     *
     * @param id the id of the entity.
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete Parking : {}", id);
        parkingRepository.deleteById(id);
    }
}
