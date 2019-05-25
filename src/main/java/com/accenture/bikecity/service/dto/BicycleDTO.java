package com.accenture.bikecity.service.dto;
import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the {@link com.accenture.bikecity.domain.Bicycle} entity.
 */
public class BicycleDTO implements Serializable {

    private Long id;

    private Integer parkingId;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getParkingId() {
        return parkingId;
    }

    public void setParkingId(Integer parkingId) {
        this.parkingId = parkingId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        BicycleDTO bicycleDTO = (BicycleDTO) o;
        if (bicycleDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), bicycleDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "BicycleDTO{" +
            "id=" + getId() +
            ", parkingId=" + getParkingId() +
            "}";
    }
}
