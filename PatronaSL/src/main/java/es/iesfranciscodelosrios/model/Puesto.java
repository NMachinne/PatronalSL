package es.iesfranciscodelosrios.model;

public class Puesto {
	private int idP;
	private String nombreP;

	public Puesto() {
	}

	public Puesto(int idP, String nombreP) {
		this.idP = idP;
		this.nombreP = nombreP;
	}

	public int getIdP() {
		return idP;
	}

	public void setIdP(int idP) {
		this.idP = idP;
	}

	public String getNombreP() {
		return nombreP;
	}

	public void setNombreP(String nombreP) {
		this.nombreP = nombreP;
	}

	@Override
	public String toString() {
		return "Puesto [idP=" + idP + ", nombreP=" + nombreP + "]";
	}

}
