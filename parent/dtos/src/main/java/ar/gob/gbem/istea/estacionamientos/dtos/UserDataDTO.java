package ar.gob.gbem.istea.estacionamientos.dtos;

import java.io.Serializable;

public class UserDataDTO implements Serializable {

	private static final long serialVersionUID = 1099734696077576531L;

	private String username;
	private String email;
	private String phone;
	private String name;
	private String surname;
	private boolean hasVehicles;
	private boolean hasParkingLots;
	private String deviceToken;

	public String getDeviceToken() {
		return deviceToken;
	}

	public void setDeviceToken(String deviceToken) {
		this.deviceToken = deviceToken;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getSurname() {
		return surname;
	}

	public void setSurname(String surname) {
		this.surname = surname;
	}

	public boolean isHasVehicles() {
		return hasVehicles;
	}

	public void setHasVehicles(boolean hasVehicles) {
		this.hasVehicles = hasVehicles;
	}

	public boolean isHasParkingLots() {
		return hasParkingLots;
	}

	public void setHasParkingLots(boolean hasParkingLots) {
		this.hasParkingLots = hasParkingLots;
	}

}
