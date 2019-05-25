package com.accenture.bikecity.domain;



import javax.persistence.*;

import java.io.Serializable;
import java.util.Objects;

/**
 * A Parking.
 */
@Entity
@Table(name = "parking")
public class Parking implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @Column(name = "capacity")
    private Integer capacity;

    @Column(name = "current_amount")
    private Integer currentAmount;

    @Column(name = "location_latitude")
    private Double locationLatitude;

    @Column(name = "location_longitude")
    private Double locationLongitude;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getCapacity() {
        return capacity;
    }

    public Parking capacity(Integer capacity) {
        this.capacity = capacity;
        return this;
    }

    public void setCapacity(Integer capacity) {
        this.capacity = capacity;
    }

    public Integer getCurrentAmount() {
        return currentAmount;
    }

    public Parking currentAmount(Integer currentAmount) {
        this.currentAmount = currentAmount;
        return this;
    }

    public void setCurrentAmount(Integer currentAmount) {
        this.currentAmount = currentAmount;
    }

    public Double getLocationLatitude() {
        return locationLatitude;
    }

    public Parking locationLatitude(Double locationLatitude) {
        this.locationLatitude = locationLatitude;
        return this;
    }

    public void setLocationLatitude(Double locationLatitude) {
        this.locationLatitude = locationLatitude;
    }

    public Double getLocationLongitude() {
        return locationLongitude;
    }

    public Parking locationLongitude(Double locationLongitude) {
        this.locationLongitude = locationLongitude;
        return this;
    }

    public void setLocationLongitude(Double locationLongitude) {
        this.locationLongitude = locationLongitude;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Parking)) {
            return false;
        }
        return id != null && id.equals(((Parking) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "Parking{" +
            "id=" + getId() +
            ", capacity=" + getCapacity() +
            ", currentAmount=" + getCurrentAmount() +
            ", locationLatitude=" + getLocationLatitude() +
            ", locationLongitude=" + getLocationLongitude() +
            "}";
    }
}
