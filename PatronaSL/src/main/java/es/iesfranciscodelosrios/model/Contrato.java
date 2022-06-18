package es.iesfranciscodelosrios.model;


public abstract class Contrato extends Persona {

	private String estado;
	private String experiencia;
	

	public Contrato() {
		super();
		this.estado = "";
		this.experiencia = "";
		
	}

	public Contrato(String nombre, String dni, String estado, String experiencia) {
		super(nombre, dni);
		this.estado = estado;
		this.experiencia = experiencia;
		
	}
	
	public Contrato(String nombre) {
		super(nombre);
	}

	public String getEstado() {
		return estado;
	}

	public void setEstado(String estado) {
		this.estado = estado;
	}

	public String getExperiencia() {
		return experiencia;
	}
	

	public void setExperiencia(String experiencia) {
		this.experiencia = experiencia;
	}
	
	abstract public float Sueldo ();  {
		
	}
}
