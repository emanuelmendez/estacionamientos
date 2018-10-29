package ar.gob.gbem.istea.estacionamientos.dtos;

import java.io.Serializable;
import java.util.Date;

public class ReservationDTO implements Serializable {

	private static final long serialVersionUID = -659632028731541922L;

	private long id;
	private String driverName;
	private String lenderName;
	private String vehicleDescription;
	private ParkingLotResultDTO parkingLot;
	private Date from;
	private Date to;
	private double value;
	private String status;

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getDriverName() {
		return driverName;
	}

	public void setDriverName(String driverName) {
		this.driverName = driverName;
	}

	public String getLenderName() {
		return lenderName;
	}

	public void setLenderName(String lenderName) {
		this.lenderName = lenderName;
	}

	public String getVehicleDescription() {
		return vehicleDescription;
	}

	public void setVehicleDescription(String vehicleDescription) {
		this.vehicleDescription = vehicleDescription;
	}

	public ParkingLotResultDTO getParkingLot() {
		return parkingLot;
	}

	public void setParkingLot(ParkingLotResultDTO parkingLot) {
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

	public double getValue() {
		return value;
	}

	public void setValue(double value) {
		this.value = value;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

}
