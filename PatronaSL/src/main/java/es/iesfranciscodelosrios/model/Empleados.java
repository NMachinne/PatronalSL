package es.iesfranciscodelosrios.model;

import java.io.Serializable;

import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlAccessType;

@XmlRootElement(name="Empleados") 
@XmlAccessorType(XmlAccessType.FIELD)
public class Empleados extends Contrato implements Serializable {
	private static final long serialVersionUID = 1L;
	private int id_e;
	
	public Empleados() {
		super();
	}

	public Empleados(int id_e, String nombre, String dni, String estado, String experiencia) {
		super(nombre, dni, estado, experiencia);
		this.id_e = id_e;

	}

	public Empleados(String nombre, String dni, String estado, String experiencia) {
		super(nombre, dni, estado, experiencia);
	}

	public Empleados(int id_e, String nombre) {
		super(nombre);
		this.id_e = id_e;

	}

	public int getId_e() {
		return id_e;
	}

	public void setId_e(int id_e) {
		this.id_e = id_e;
	}

	
	@Override
	public String toString() {
		return "Empleados [id_e=" + id_e + "]";
	}


	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (!super.equals(obj))
			return false;
		if (getClass() != obj.getClass())
			return false;
		Empleados other = (Empleados) obj;
		return id_e == other.id_e;
	}

	@Override
	public float Sueldo() {
		
		return 0;
	}
	
	

}
