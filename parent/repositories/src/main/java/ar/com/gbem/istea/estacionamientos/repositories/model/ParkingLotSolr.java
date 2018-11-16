package ar.com.gbem.istea.estacionamientos.repositories.model;

import javax.persistence.Id;

import org.apache.solr.client.solrj.beans.Field;
import org.springframework.data.solr.core.mapping.SolrDocument;

@SolrDocument(collection = "est_core")
public class ParkingLotSolr {

	@Id
	@Field
	private long id;

	@Field
	private String description;

	@Field
	private String coordinates;

	@Field("address_id")
	private long addressId;

	@Field("lot_number")
	private int lotNumber;

	@Field("street_address")
	private String streetAddress;

	@Field("user_id")
	private long userId;

	@Field
	private boolean monday;

	@Field
	private boolean tuesday;

	@Field
	private boolean wednesday;

	@Field
	private boolean thursday;

	@Field
	private boolean friday;

	@Field
	private boolean saturday;

	@Field
	private boolean sunday;

	@Field("from_hour")
	private int fromHour;

	@Field("to_hour")
	private int toHour;

	@Field("user_full_name")
	private String userFullName;

	@Field
	private double value;

	public String getUserFullName() {
		return userFullName;
	}

	public void setUserFullName(String userFullName) {
		this.userFullName = userFullName;
	}

	public double getValue() {
		return value;
	}

	public void setValue(double value) {
		this.value = value;
	}

	public boolean isMonday() {
		return monday;
	}

	public void setMonday(boolean monday) {
		this.monday = monday;
	}

	public boolean isTuesday() {
		return tuesday;
	}

	public void setTuesday(boolean tuesday) {
		this.tuesday = tuesday;
	}

	public boolean isWednesday() {
		return wednesday;
	}

	public void setWednesday(boolean wednesday) {
		this.wednesday = wednesday;
	}

	public boolean isThursday() {
		return thursday;
	}

	public void setThursday(boolean thursday) {
		this.thursday = thursday;
	}

	public boolean isFriday() {
		return friday;
	}

	public void setFriday(boolean friday) {
		this.friday = friday;
	}

	public boolean isSaturday() {
		return saturday;
	}

	public void setSaturday(boolean saturday) {
		this.saturday = saturday;
	}

	public boolean isSunday() {
		return sunday;
	}

	public void setSunday(boolean sunday) {
		this.sunday = sunday;
	}

	public int getFromHour() {
		return fromHour;
	}

	public void setFromHour(int fromHour) {
		this.fromHour = fromHour;
	}

	public int getToHour() {
		return toHour;
	}

	public void setToHour(int toHour) {
		this.toHour = toHour;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getCoordinates() {
		return coordinates;
	}

	public void setCoordinates(String coordinates) {
		this.coordinates = coordinates;
	}

	public long getAddressId() {
		return addressId;
	}

	public void setAddressId(long addressId) {
		this.addressId = addressId;
	}

	public int getLotNumber() {
		return lotNumber;
	}

	public void setLotNumber(int lotNumber) {
		this.lotNumber = lotNumber;
	}

	public String getStreetAddress() {
		return streetAddress;
	}

	public void setStreetAddress(String streetAddress) {
		this.streetAddress = streetAddress;
	}

	public long getUserId() {
		return userId;
	}

	public void setUserId(long userId) {
		this.userId = userId;
	}

	public boolean potentialDay(int day) {
		switch (day) {
		case 1:
			return sunday;
		case 2:
			return monday;
		case 3:
			return tuesday;
		case 4:
			return wednesday;
		case 5:
			return thursday;
		case 6:
			return friday;
		case 7:
			return saturday;
		default:
			return false;
		}
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
		ParkingLotSolr other = (ParkingLotSolr) obj;
		return id == other.id;
	}

	@Override
	public String toString() {
		return "ParkingLotSolr [id=" + id + ", coordinates=" + coordinates + ", addressId=" + addressId + ", lotNumber="
				+ lotNumber + ", userId=" + userId + "]";
	}

}
