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
