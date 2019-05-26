package com.accenture.bikecity.repository;

import com.accenture.bikecity.domain.BikeTransaction;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

import java.util.List;


/**
 * Spring Data  repository for the BikeTransaction entity.
 */
@SuppressWarnings("unused")
@Repository
public interface BikeTransactionRepository extends JpaRepository<BikeTransaction, Long> {

    List<BikeTransaction> findAllByUserId(Long id);
}
