package ar.com.gbem.istea.estacionamientos.entities;

public class Estacionamiento {

	private long id;
	private Usuario prestador;
	private boolean disponible;

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public boolean isDisponible() {
		return disponible;
	}

	public void setDisponible(boolean disponible) {
		this.disponible = disponible;
	}

	public Usuario getPrestador() {
		return prestador;
	}

	public void setPrestador(Usuario prestador) {
		this.prestador = prestador;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + (disponible ? 1231 : 1237);
		result = prime * result + (int) (id ^ (id >>> 32));
		result = prime * result + ((prestador == null) ? 0 : prestador.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Estacionamiento other = (Estacionamiento) obj;
		if (disponible != other.disponible)
			return false;
		if (id != other.id)
			return false;
		if (prestador == null) {
			if (other.prestador != null)
				return false;
		} else if (!prestador.equals(other.prestador))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "Estacionamiento [id=" + id + ", prestador=" + prestador + ", disponible=" + disponible + "]";
	}

}
