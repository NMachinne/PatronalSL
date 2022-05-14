package es.iesfranciscodelosrios.model;

import java.util.Objects;


public class Empleados{

	private int id_e;
	private String Nombre,Dni,Estado, Experiencia;
	
	
	public Empleados () {
		
	}
	
	public Empleados(int id_e, String nombre, String dni, String estado, String experiencia) {
		super();
		this.id_e = id_e;
		this.Nombre = nombre;
		this.Dni = dni;
		this.Estado= estado;
		this.Experiencia = experiencia;
	}

	


	public int getId_e() {
		return id_e;
	}

	public void setId_e(int id_e) {
		this.id_e = id_e;
	}

	public String getNombre() {
		return Nombre;
	}

	public void setNombre(String nombre) {
		Nombre = nombre;
	}

	public String getDni() {
		return Dni;
	}

	public void setDni(String dni) {
		Dni = dni;
	}



	public String getEstado() {
		return Estado;
	}

	public void setEstado(String estado) {
		Estado = estado;
	}

	public String getExperiencia() {
		return Experiencia;
	}

	public void setExperiencia(String experiencia) {
		Experiencia = experiencia;
	}

	
	
	
	@Override
	public String toString() {
		return "Empleados [id_e=" + id_e + ", Nombre=" + Nombre + ", Dni=" + Dni + ", Estado=" + Estado
				+ ", Experiencia=" + Experiencia + "]";
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Empleados other = (Empleados) obj;
		return Objects.equals(Dni, other.Dni) && id_e == other.id_e;
	}
	
	
	
	
}
