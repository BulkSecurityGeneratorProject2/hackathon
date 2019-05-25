package com.accenture.bikecity.web.rest;

import com.accenture.bikecity.service.BikeTransactionService;
import com.accenture.bikecity.web.rest.errors.BadRequestAlertException;
import com.accenture.bikecity.service.dto.BikeTransactionDTO;

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
 * REST controller for managing {@link com.accenture.bikecity.domain.BikeTransaction}.
 */
@RestController
@RequestMapping("/api")
public class BikeTransactionResource {

    private final Logger log = LoggerFactory.getLogger(BikeTransactionResource.class);

    private static final String ENTITY_NAME = "bikeTransaction";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final BikeTransactionService bikeTransactionService;

    public BikeTransactionResource(BikeTransactionService bikeTransactionService) {
        this.bikeTransactionService = bikeTransactionService;
    }

    /**
     * {@code POST  /bike-transactions} : Create a new bikeTransaction.
     *
     * @param bikeTransactionDTO the bikeTransactionDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new bikeTransactionDTO, or with status {@code 400 (Bad Request)} if the bikeTransaction has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/bike-transactions")
    public ResponseEntity<BikeTransactionDTO> createBikeTransaction(@RequestBody BikeTransactionDTO bikeTransactionDTO) throws URISyntaxException {
        log.debug("REST request to save BikeTransaction : {}", bikeTransactionDTO);
        if (bikeTransactionDTO.getId() != null) {
            throw new BadRequestAlertException("A new bikeTransaction cannot already have an ID", ENTITY_NAME, "idexists");
        }
        BikeTransactionDTO result = bikeTransactionService.save(bikeTransactionDTO);
        return ResponseEntity.created(new URI("/api/bike-transactions/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /bike-transactions} : Updates an existing bikeTransaction.
     *
     * @param bikeTransactionDTO the bikeTransactionDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated bikeTransactionDTO,
     * or with status {@code 400 (Bad Request)} if the bikeTransactionDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the bikeTransactionDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/bike-transactions")
    public ResponseEntity<BikeTransactionDTO> updateBikeTransaction(@RequestBody BikeTransactionDTO bikeTransactionDTO) throws URISyntaxException {
        log.debug("REST request to update BikeTransaction : {}", bikeTransactionDTO);
        if (bikeTransactionDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        BikeTransactionDTO result = bikeTransactionService.save(bikeTransactionDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, bikeTransactionDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /bike-transactions} : get all the bikeTransactions.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of bikeTransactions in body.
     */
    @GetMapping("/bike-transactions")
    public List<BikeTransactionDTO> getAllBikeTransactions() {
        log.debug("REST request to get all BikeTransactions");
        return bikeTransactionService.findAll();
    }

    /**
     * {@code GET  /bike-transactions/:id} : get the "id" bikeTransaction.
     *
     * @param id the id of the bikeTransactionDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the bikeTransactionDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/bike-transactions/{id}")
    public ResponseEntity<BikeTransactionDTO> getBikeTransaction(@PathVariable Long id) {
        log.debug("REST request to get BikeTransaction : {}", id);
        Optional<BikeTransactionDTO> bikeTransactionDTO = bikeTransactionService.findOne(id);
        return ResponseUtil.wrapOrNotFound(bikeTransactionDTO);
    }

    /**
     * {@code DELETE  /bike-transactions/:id} : delete the "id" bikeTransaction.
     *
     * @param id the id of the bikeTransactionDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/bike-transactions/{id}")
    public ResponseEntity<Void> deleteBikeTransaction(@PathVariable Long id) {
        log.debug("REST request to delete BikeTransaction : {}", id);
        bikeTransactionService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString())).build();
    }
}
