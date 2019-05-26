package com.accenture.bikecity.service.dto;
import java.time.Instant;
import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the {@link com.accenture.bikecity.domain.BikeTransaction} entity.
 */
public class BikeTransactionDTO implements Serializable {

    private Long id;

    private Instant startTime;

    private Instant endTime;

    private Long userId;

    private Long bikeId;

    private Long startParkingId;

    private Long endParkingId;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Instant getStartTime() {
        return startTime;
    }

    public void setStartTime(Instant startTime) {
        this.startTime = startTime;
    }

    public Instant getEndTime() {
        return endTime;
    }

    public void setEndTime(Instant endTime) {
        this.endTime = endTime;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Long getBikeId() {
        return bikeId;
    }

    public void setBikeId(Long bikeId) {
        this.bikeId = bikeId;
    }

    public Long getStartParkingId() {
        return startParkingId;
    }

    public void setStartParkingId(Long startParkingId) {
        this.startParkingId = startParkingId;
    }

    public Long getEndParkingId() {
        return endParkingId;
    }

    public void setEndParkingId(Long endParkingId) {
        this.endParkingId = endParkingId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        BikeTransactionDTO bikeTransactionDTO = (BikeTransactionDTO) o;
        if (bikeTransactionDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), bikeTransactionDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "BikeTransactionDTO{" +
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
