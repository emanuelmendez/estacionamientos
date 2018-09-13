package model;

import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity
@Table(name = "USER")
public class User {
	private long id;
	private String username;
	private String email;
	private Date since;
	private Date lastUpdated;
	private boolean active;
	private UserDetails userDetails;
	private List<Vehicle> vehicles;
	private List<ParkingLot> parkingLots;
	
	@Id
	@GeneratedValue
	@Column(name = "ID")
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	
	@Column(name = "USERNAME")
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	
	@Column(name = "EMAIL")
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	
	@Column(name = "SINCE")
	public Date getSince() {
		return since;
	}
	public void setSince(Date since) {
		this.since = since;
	}
	
	@Column(name = "LAST_UPDATED")
	public Date getLastUpdated() {
		return lastUpdated;
	}
	public void setLastUpdated(Date lastUpdated) {
		this.lastUpdated = lastUpdated;
	}
	
	@Column(name = "ACTIVE")
	public boolean isActive() {
		return active;
	}
	public void setActive(boolean active) {
		this.active = active;
	}
	
	@OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ID")
	public UserDetails getUserDetails() {
		return userDetails;
	}
	public void setUserDetails(UserDetails userDetails) {
		this.userDetails = userDetails;
	}
	
	@OneToMany(
	        mappedBy = "USER", 
	        cascade = CascadeType.ALL, 
	        orphanRemoval = true
	    )
	public List<Vehicle> getVehicles() {
		return vehicles;
	}
	public void setVehicles(List<Vehicle> vehicles) {
		this.vehicles = vehicles;
	}
	
	@OneToMany(
	        mappedBy = "USER", 
	        cascade = CascadeType.ALL, 
	        orphanRemoval = true
	    )
	public List<ParkingLot> getParkingLots() {
		return parkingLots;
	}
	public void setParkingLots(List<ParkingLot> parkingLots) {
		this.parkingLots = parkingLots;
	}
}
