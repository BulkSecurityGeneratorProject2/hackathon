package com.accenture.bikecity.domain;



import javax.persistence.*;

import java.io.Serializable;
import java.util.Objects;

/**
 * A Bicycle.
 */
@Entity
@Table(name = "bicycle")
public class Bicycle implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @Column(name = "parking_id")
    private Integer parkingId;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getParkingId() {
        return parkingId;
    }

    public Bicycle parkingId(Integer parkingId) {
        this.parkingId = parkingId;
        return this;
    }

    public void setParkingId(Integer parkingId) {
        this.parkingId = parkingId;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Bicycle)) {
            return false;
        }
        return id != null && id.equals(((Bicycle) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "Bicycle{" +
            "id=" + getId() +
            ", parkingId=" + getParkingId() +
            "}";
    }
}
