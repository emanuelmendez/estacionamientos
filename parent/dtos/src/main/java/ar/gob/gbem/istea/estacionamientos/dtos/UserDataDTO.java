package ar.gob.gbem.istea.estacionamientos.dtos;

public class UserDataDTO {

	private String username;
	private String email;
	private String phone;
	private String name;
	private String surname;
	private boolean hasVehicles;
	private boolean hasParkingLots;

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
