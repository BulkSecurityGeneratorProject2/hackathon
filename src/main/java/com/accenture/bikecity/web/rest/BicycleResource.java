package com.accenture.bikecity.web.rest;

import com.accenture.bikecity.service.BicycleService;
import com.accenture.bikecity.web.rest.errors.BadRequestAlertException;
import com.accenture.bikecity.service.dto.BicycleDTO;

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
 * REST controller for managing {@link com.accenture.bikecity.domain.Bicycle}.
 */
@RestController
@RequestMapping("/api")
public class BicycleResource {

    private final Logger log = LoggerFactory.getLogger(BicycleResource.class);

    private static final String ENTITY_NAME = "bicycle";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final BicycleService bicycleService;

    public BicycleResource(BicycleService bicycleService) {
        this.bicycleService = bicycleService;
    }

    /**
     * {@code POST  /bicycles} : Create a new bicycle.
     *
     * @param bicycleDTO the bicycleDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new bicycleDTO, or with status {@code 400 (Bad Request)} if the bicycle has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/bicycles")
    public ResponseEntity<BicycleDTO> createBicycle(@RequestBody BicycleDTO bicycleDTO) throws URISyntaxException {
        log.debug("REST request to save Bicycle : {}", bicycleDTO);
        if (bicycleDTO.getId() != null) {
            throw new BadRequestAlertException("A new bicycle cannot already have an ID", ENTITY_NAME, "idexists");
        }
        BicycleDTO result = bicycleService.save(bicycleDTO);
        return ResponseEntity.created(new URI("/api/bicycles/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /bicycles} : Updates an existing bicycle.
     *
     * @param bicycleDTO the bicycleDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated bicycleDTO,
     * or with status {@code 400 (Bad Request)} if the bicycleDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the bicycleDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/bicycles")
    public ResponseEntity<BicycleDTO> updateBicycle(@RequestBody BicycleDTO bicycleDTO) throws URISyntaxException {
        log.debug("REST request to update Bicycle : {}", bicycleDTO);
        if (bicycleDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        BicycleDTO result = bicycleService.save(bicycleDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, bicycleDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /bicycles} : get all the bicycles.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of bicycles in body.
     */
    @GetMapping("/bicycles")
    public List<BicycleDTO> getAllBicycles() {
        log.debug("REST request to get all Bicycles");
        return bicycleService.findAll();
    }

    /**
     * {@code GET  /bicycles/:id} : get the "id" bicycle.
     *
     * @param id the id of the bicycleDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the bicycleDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/bicycles/{id}")
    public ResponseEntity<BicycleDTO> getBicycle(@PathVariable Long id) {
        log.debug("REST request to get Bicycle : {}", id);
        Optional<BicycleDTO> bicycleDTO = bicycleService.findOne(id);
        return ResponseUtil.wrapOrNotFound(bicycleDTO);
    }

    /**
     * {@code DELETE  /bicycles/:id} : delete the "id" bicycle.
     *
     * @param id the id of the bicycleDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/bicycles/{id}")
    public ResponseEntity<Void> deleteBicycle(@PathVariable Long id) {
        log.debug("REST request to delete Bicycle : {}", id);
        bicycleService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString())).build();
    }
}
