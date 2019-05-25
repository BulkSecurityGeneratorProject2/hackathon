package com.accenture.bikecity.web.rest;

import com.accenture.bikecity.service.ParkingService;
import com.accenture.bikecity.web.rest.errors.BadRequestAlertException;
import com.accenture.bikecity.service.dto.ParkingDTO;

import io.github.jhipster.web.util.HeaderUtil;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;

import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing {@link com.accenture.bikecity.domain.Parking}.
 */
@RestController
@RequestMapping("/api")
public class ParkingResource {

    private final Logger log = LoggerFactory.getLogger(ParkingResource.class);

    private static final String ENTITY_NAME = "parking";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final ParkingService parkingService;

    public ParkingResource(ParkingService parkingService) {
        this.parkingService = parkingService;
    }

    /**
     * {@code POST  /parkings} : Create a new parking.
     *
     * @param parkingDTO the parkingDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new parkingDTO, or with status {@code 400 (Bad Request)} if the parking has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/parkings")
    public ResponseEntity<ParkingDTO> createParking(@RequestBody ParkingDTO parkingDTO) throws URISyntaxException {
        log.debug("REST request to save Parking : {}", parkingDTO);
        if (parkingDTO.getId() != null) {
            throw new BadRequestAlertException("A new parking cannot already have an ID", ENTITY_NAME, "idexists");
        }
        ParkingDTO result = parkingService.save(parkingDTO);
        return ResponseEntity.created(new URI("/api/parkings/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /parkings} : Updates an existing parking.
     *
     * @param parkingDTO the parkingDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated parkingDTO,
     * or with status {@code 400 (Bad Request)} if the parkingDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the parkingDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/parkings")
    public ResponseEntity<ParkingDTO> updateParking(@RequestBody ParkingDTO parkingDTO) throws URISyntaxException {
        log.debug("REST request to update Parking : {}", parkingDTO);
        if (parkingDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        ParkingDTO result = parkingService.save(parkingDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, parkingDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /parkings} : get all the parkings.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of parkings in body.
     */
    @GetMapping("/parkings")
    public List<ParkingDTO> getAllParkings() {
        log.debug("REST request to get all Parkings");
        return parkingService.findAll();
    }

    /**
     * {@code GET  /parkings/:id} : get the "id" parking.
     *
     * @param id the id of the parkingDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the parkingDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/parkings/{id}")
    public ResponseEntity<ParkingDTO> getParking(@PathVariable Long id) {
        log.debug("REST request to get Parking : {}", id);
        Optional<ParkingDTO> parkingDTO = parkingService.findOne(id);
        return ResponseUtil.wrapOrNotFound(parkingDTO);
    }

    /**
     * {@code DELETE  /parkings/:id} : delete the "id" parking.
     *
     * @param id the id of the parkingDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/parkings/{id}")
    public ResponseEntity<Void> deleteParking(@PathVariable Long id) {
        log.debug("REST request to delete Parking : {}", id);
        parkingService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString())).build();
    }
}
