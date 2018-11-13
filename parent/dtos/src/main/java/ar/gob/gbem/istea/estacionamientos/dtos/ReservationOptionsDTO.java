package ar.gob.gbem.istea.estacionamientos.dtos;

import java.io.Serializable;
import java.util.Date;

public class ReservationOptionsDTO implements Serializable {

	private static final long serialVersionUID = -8387368811760938425L;

	private long parkingLotId;
	private Date dateFrom;
	private Date dateTo;

	public long getParkingLotId() {
		return parkingLotId;
	}

	public void setParkingLotId(long parkingLotId) {
		this.parkingLotId = parkingLotId;
	}

	public Date getDateFrom() {
		return dateFrom;
	}

	public void setDateFrom(Date dateFrom) {
		this.dateFrom = dateFrom;
	}

	public Date getDateTo() {
		return dateTo;
	}

	public void setDateTo(Date dateTo) {
		this.dateTo = dateTo;
	}

	@Override
	public String toString() {
		return "ReservationOptionsDTO [parkingLotId=" + parkingLotId + ", dateFrom=" + dateFrom + ", dateTo=" + dateTo
				+ "]";
	}

}
