package es.iesfranciscodelosrios.model;

import java.io.Serializable;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name="Puestos") 
@XmlAccessorType(XmlAccessType.FIELD)
public class Puestos implements Serializable {
	private static final long serialVersionUID = 1L;
	private int id_p;
	private String nombre_p;

	public Puestos() {
	}

	public Puestos(int id_e, String nombreP) {
		super();
		this.id_p = id_e;
		this.nombre_p = nombreP;
	}

	public Puestos(String nombreP) {
		super();
		this.nombre_p = nombreP;
	}

	public int getId_p() {
		return id_p;
	}

	public void setId_p(int id_p) {
		this.id_p = id_p;
	}

	public String getNombre() {
		return nombre_p;
	}

	public void setNombre(String nombre) {
		this.nombre_p = nombre;
	
	}
	@Override
	public String toString() {
		return id_p +" - " +nombre_p;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Puestos other = (Puestos) obj;
		return id_p == other.id_p;
	}

}
