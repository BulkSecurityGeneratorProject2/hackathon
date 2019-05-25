package com.accenture.bikecity.service.dto;
import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the {@link com.accenture.bikecity.domain.Parking} entity.
 */
public class ParkingDTO implements Serializable {

    private Long id;

    private Integer capacity;

    private Integer currentAmount;

    private Double locationLatitude;

    private Double locationLongitude;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getCapacity() {
        return capacity;
    }

    public void setCapacity(Integer capacity) {
        this.capacity = capacity;
    }

    public Integer getCurrentAmount() {
        return currentAmount;
    }

    public void setCurrentAmount(Integer currentAmount) {
        this.currentAmount = currentAmount;
    }

    public Double getLocationLatitude() {
        return locationLatitude;
    }

    public void setLocationLatitude(Double locationLatitude) {
        this.locationLatitude = locationLatitude;
    }

    public Double getLocationLongitude() {
        return locationLongitude;
    }

    public void setLocationLongitude(Double locationLongitude) {
        this.locationLongitude = locationLongitude;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        ParkingDTO parkingDTO = (ParkingDTO) o;
        if (parkingDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), parkingDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "ParkingDTO{" +
            "id=" + getId() +
            ", capacity=" + getCapacity() +
            ", currentAmount=" + getCurrentAmount() +
            ", locationLatitude=" + getLocationLatitude() +
            ", locationLongitude=" + getLocationLongitude() +
            "}";
    }
}
