package com.accenture.bikecity.repository;

import com.accenture.bikecity.domain.Parking;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the Parking entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ParkingRepository extends JpaRepository<Parking, Long> {

}
