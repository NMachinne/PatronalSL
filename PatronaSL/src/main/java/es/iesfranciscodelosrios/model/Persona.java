package es.iesfranciscodelosrios.model;


public class Persona {

	private String nombre, dni;

	
	public Persona() {
		
	}
	
	public Persona(String nombre_p, String dni) {
		this.nombre = nombre_p;
		this.dni = dni;
	}
	
	public Persona(String nombre_p) {
		this.nombre = nombre_p;
	}
	

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getDni() {
		return dni;
	}

	public void setDni(String dni) {
		this.dni = dni;
	}


	@Override
	public String toString() {
		return "Persona [nombre_p=" + nombre + ", dni=" + dni + "]";
	}


}
