package ar.com.gbem.istea.estacionamientos.repositories.model;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "VEHICLE")
public class Vehicle implements Serializable {

	private static final long serialVersionUID = -4065131283540150875L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;

	private String plate;

	private boolean active;
	
	private String brand;
	
	private String model;
	
	private String color;
	
	@JoinColumn(name = "USER")
	@ManyToOne(fetch = FetchType.LAZY)
	private User user;
	
	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getPlate() {
		return plate;
	}

	public void setPlate(String plate) {
		this.plate = plate;
	}

	public boolean isActive() {
		return active;
	}

	public void setActive(boolean active) {
		this.active = active;
	}

	public String getBrand() {
		return brand;
	}

	public void setBrand(String brand) {
		this.brand = brand;
	}

	public String getModel() {
		return model;
	}

	public void setModel(String model) {
		this.model = model;
	}

	public String getColor() {
		return color;
	}

	public void setColor(String color) {
		this.color = color;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + (int) (id ^ (id >>> 32));
		result = prime * result + ((plate == null) ? 0 : plate.hashCode());
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
		Vehicle other = (Vehicle) obj;
		if (id != other.id) {
			return false;
		}
		if (plate == null) {
			if (other.plate != null) {
				return false;
			}
		} else if (!plate.equals(other.plate)) {
			return false;
		}
		return true;
	}

	@Override
	public String toString() {
		return brand + " " + model + " " + color + " (" + plate + ")";
	}

}
