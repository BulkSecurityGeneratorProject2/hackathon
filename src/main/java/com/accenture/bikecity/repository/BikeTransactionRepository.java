package com.accenture.bikecity.repository;

import com.accenture.bikecity.domain.BikeTransaction;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the BikeTransaction entity.
 */
@SuppressWarnings("unused")
@Repository
public interface BikeTransactionRepository extends JpaRepository<BikeTransaction, Long> {

}
