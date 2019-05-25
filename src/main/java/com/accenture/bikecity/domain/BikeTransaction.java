package com.accenture.bikecity.domain;



import javax.persistence.*;

import java.io.Serializable;
import java.time.Instant;
import java.util.Objects;

/**
 * A BikeTransaction.
 */
@Entity
@Table(name = "bike_transaction")
public class BikeTransaction implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @Column(name = "start_time")
    private Instant startTime;

    @Column(name = "end_time")
    private Instant endTime;

    @Column(name = "user_id")
    private Integer userId;

    @Column(name = "bike_id")
    private Integer bikeId;

    @Column(name = "start_parking_id")
    private Integer startParkingId;

    @Column(name = "end_parking_id")
    private Integer endParkingId;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Instant getStartTime() {
        return startTime;
    }

    public BikeTransaction startTime(Instant startTime) {
        this.startTime = startTime;
        return this;
    }

    public void setStartTime(Instant startTime) {
        this.startTime = startTime;
    }

    public Instant getEndTime() {
        return endTime;
    }

    public BikeTransaction endTime(Instant endTime) {
        this.endTime = endTime;
        return this;
    }

    public void setEndTime(Instant endTime) {
        this.endTime = endTime;
    }

    public Integer getUserId() {
        return userId;
    }

    public BikeTransaction userId(Integer userId) {
        this.userId = userId;
        return this;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public Integer getBikeId() {
        return bikeId;
    }

    public BikeTransaction bikeId(Integer bikeId) {
        this.bikeId = bikeId;
        return this;
    }

    public void setBikeId(Integer bikeId) {
        this.bikeId = bikeId;
    }

    public Integer getStartParkingId() {
        return startParkingId;
    }

    public BikeTransaction startParkingId(Integer startParkingId) {
        this.startParkingId = startParkingId;
        return this;
    }

    public void setStartParkingId(Integer startParkingId) {
        this.startParkingId = startParkingId;
    }

    public Integer getEndParkingId() {
        return endParkingId;
    }

    public BikeTransaction endParkingId(Integer endParkingId) {
        this.endParkingId = endParkingId;
        return this;
    }

    public void setEndParkingId(Integer endParkingId) {
        this.endParkingId = endParkingId;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof BikeTransaction)) {
            return false;
        }
        return id != null && id.equals(((BikeTransaction) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "BikeTransaction{" +
            "id=" + getId() +
            ", startTime='" + getStartTime() + "'" +
            ", endTime='" + getEndTime() + "'" +
            ", userId=" + getUserId() +
            ", bikeId=" + getBikeId() +
            ", startParkingId=" + getStartParkingId() +
            ", endParkingId=" + getEndParkingId() +
            "}";
    }
}
