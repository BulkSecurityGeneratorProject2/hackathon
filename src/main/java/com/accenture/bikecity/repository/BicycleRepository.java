package com.accenture.bikecity.repository;

import com.accenture.bikecity.domain.Bicycle;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the Bicycle entity.
 */
@SuppressWarnings("unused")
@Repository
public interface BicycleRepository extends JpaRepository<Bicycle, Long> {

}
