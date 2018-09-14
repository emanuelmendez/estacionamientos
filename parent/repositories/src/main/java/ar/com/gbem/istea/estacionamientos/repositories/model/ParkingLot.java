package ar.com.gbem.istea.estacionamientos.repositories.model;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "PARKING_LOT")
public class ParkingLot implements Comparable<ParkingLot> {

	@Id
	@GeneratedValue
	private long id;

	private int lotNumber;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "ADDRESS")
	private Address address;

	private String description;

	private Date since;

	private boolean active;

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public int getLotNumber() {
		return lotNumber;
	}

	public void setLotNumber(int lotNumber) {
		this.lotNumber = lotNumber;
	}

	public Address getAddress() {
		return address;
	}

	public void setAddress(Address address) {
		this.address = address;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Date getSince() {
		return since;
	}

	public void setSince(Date since) {
		this.since = since;
	}

	public boolean isActive() {
		return active;
	}

	public void setActive(boolean active) {
		this.active = active;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + (int) (id ^ (id >>> 32));
		result = prime * result + ((address == null) ? 0 : address.hashCode());
		result = prime * result + lotNumber;
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null) {
			return false;
		}
		if (getClass() != obj.getClass()) {
			return false;
		}
		ParkingLot other = (ParkingLot) obj;
		if (id != other.id) {
			return false;
		}
		if (lotNumber != other.lotNumber) {
			if (address == null) {
				if (other.address != null) {
					return false;
				}
			} else if (!address.equals(other.address)) {
				return false;
			}
			return false;
		}
		return true;
	}

	@Override
	public int compareTo(ParkingLot o) {
		if (this.id != o.id || !this.address.equals(o.address)) {
			return 0;
		} else {
			return Integer.compare(this.lotNumber, o.lotNumber);
		}
	}

}
