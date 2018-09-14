package ar.com.gbem.istea.estacionamientos.repositories.model;

public enum Status {
	PENDING(1, "Pending"), 
	APPROVED(2, "Approved"), 
	IN_PROGRESS(3, "In Progress"), 
	DONE(4, "Done"), 
	CANCELLED(5, "Cancelled");

	private int key;
	private String description;

	private Status(int key, String description) {
		this.key = key;
		this.description = description;
	}

	public int key() {
		return key;
	}

	public String description() {
		return description;
	}
	
	@Override
	public String toString() {
		return description;
	}

}
