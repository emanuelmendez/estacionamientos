package ar.com.gbem.istea.estacionamientos.repositories.model;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name = "USER")
public class User {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;

	private String username;

	private String email;

	private String phone;

	private String name;

	private String surname;

	private String token;

	private Date since;

	@Column(name = "LAST_UPDATED")
	private Date lastUpdated;

	private boolean active;

	@JoinColumn(name = "USER")
	@OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
	private List<Vehicle> vehicles;

	@JoinColumn(name = "USER")
	@OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
	private List<ParkingLot> parkingLots;

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
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

	public Date getSince() {
		return since;
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

	public void setSince(Date since) {
		this.since = since;
	}

	public Date getLastUpdated() {
		return lastUpdated;
	}

	public void setLastUpdated(Date lastUpdated) {
		this.lastUpdated = lastUpdated;
	}

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

	public boolean isActive() {
		return active;
	}

	public void setActive(boolean active) {
		this.active = active;
	}

	public List<Vehicle> getVehicles() {
		if (vehicles == null) {
			vehicles = new ArrayList<>();
		}
		return vehicles;
	}

	public void setVehicles(List<Vehicle> vehicles) {
		this.vehicles = vehicles;
	}

	public List<ParkingLot> getParkingLots() {
		if (parkingLots == null) {
			parkingLots = new ArrayList<>();
		}
		
		return parkingLots;
	}

	public void setParkingLots(List<ParkingLot> parkingLots) {
		this.parkingLots = parkingLots;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + (int) (id ^ (id >>> 32));
		result = prime * result + ((token == null) ? 0 : token.hashCode());
		result = prime * result + ((phone == null) ? 0 : phone.hashCode());
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
		User other = (User) obj;
		if (id != other.id) {
			return false;
		}
		if (token == null) {
			if (other.token != null) {
				return false;
			}
		} else if (!token.equals(other.token)) {
			return false;
		}

		if (username == null) {
			if (other.username != null) {
				return false;
			}
		} else if (!username.equals(other.username)) {
			return false;
		}

		return true;
	}

}
