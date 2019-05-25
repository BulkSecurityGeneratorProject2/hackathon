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

    private Integer userId;

    private Integer bikeId;

    private Integer startParkingId;

    private Integer endParkingId;


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

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public Integer getBikeId() {
        return bikeId;
    }

    public void setBikeId(Integer bikeId) {
        this.bikeId = bikeId;
    }

    public Integer getStartParkingId() {
        return startParkingId;
    }

    public void setStartParkingId(Integer startParkingId) {
        this.startParkingId = startParkingId;
    }

    public Integer getEndParkingId() {
        return endParkingId;
    }

    public void setEndParkingId(Integer endParkingId) {
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
