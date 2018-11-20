package ar.com.gbem.istea.estacionamientos.repositories.model;

import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity
@Table(name = "RESERVATION")
public class Reservation {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;

	@OneToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "DRIVER")
	private User driver;

	@OneToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "LENDER")
	private User lender;

	@OneToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "VEHICLE")
	private Vehicle vehicle;

	@OneToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "PARKING_LOT")
	private ParkingLot parkingLot;

	@Column(name = "DATE_FROM")
	private Date from;

	@Column(name = "DATE_TO")
	private Date to;

	@Column(name = "value", precision = 8, scale = 2)
	private BigDecimal value;

	@Enumerated
	private Status status;

	@OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true, mappedBy = "reservation")
	private Review review;

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public User getDriver() {
		return driver;
	}

	public void setDriver(User driver) {
		this.driver = driver;
	}

	public User getLender() {
		return lender;
	}

	public void setLender(User lender) {
		this.lender = lender;
	}

	public Vehicle getVehicle() {
		return vehicle;
	}

	public void setVehicle(Vehicle vehicle) {
		this.vehicle = vehicle;
	}

	public ParkingLot getParkingLot() {
		return parkingLot;
	}

	public void setParkingLot(ParkingLot parkingLot) {
		this.parkingLot = parkingLot;
	}

	public Date getFrom() {
		return from;
	}

	public void setFrom(Date from) {
		this.from = from;
	}

	public Date getTo() {
		return to;
	}

	public void setTo(Date to) {
		this.to = to;
	}

	public BigDecimal getValue() {
		return value;
	}

	public void setValue(BigDecimal value) {
		this.value = value;
	}

	public Status getStatus() {
		return status;
	}

	public void setStatus(Status status) {
		this.status = status;
	}

	public Review getReview() {
		return review;
	}

	public void setReview(Review review) {
		this.review = review;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + (int) (id ^ (id >>> 32));
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
		Reservation other = (Reservation) obj;
		if (id != other.id) {
			return false;
		}
		return true;
	}

}
