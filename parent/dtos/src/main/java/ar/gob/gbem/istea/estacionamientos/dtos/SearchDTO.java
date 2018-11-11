package ar.gob.gbem.istea.estacionamientos.dtos;

import java.io.Serializable;
import java.util.Date;

public class SearchDTO implements Serializable {

	private static final long serialVersionUID = -2758540112668072805L;

	private double latitude;
	private double longitude;
	private double ratio;
	private Date fromDate;
	private Date toDate;

	public double getLatitude() {
		return latitude;
	}

	public void setLatitude(double latitude) {
		this.latitude = latitude;
	}

	public double getLongitude() {
		return longitude;
	}

	public void setLongitude(double longitude) {
		this.longitude = longitude;
	}

	public double getRatio() {
		return ratio;
	}

	public void setRatio(double ratio) {
		this.ratio = ratio;
	}

	public Date getFromDate() {
		return fromDate;
	}

	public void setFromDate(Date fromDate) {
		this.fromDate = fromDate;
	}

	public Date getToDate() {
		return toDate;
	}

	public void setToDate(Date toDate) {
		this.toDate = toDate;
	}

	@Override
	public String toString() {
		return "SearchDTO [latitude=" + latitude + ", longitude=" + longitude + ", ratio=" + ratio + ", fromDate="
				+ fromDate + ", toDate=" + toDate + "]";
	}

}
