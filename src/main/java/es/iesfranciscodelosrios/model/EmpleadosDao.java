package es.iesfranciscodelosrios.model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Collection;

import es.iesfranciscodelosrios.interfaces.IDao;
import es.iesfranciscodelosrios.utils.Connect;

public class EmpleadosDao implements IDao<Empleados, Integer> {

	private Connection miconexion;

	public EmpleadosDao() {
		this.miconexion = Connect.getConnect();

	}

	public boolean insert(Empleados ob) {
		return false;
	}

	public Empleados get(Integer id) {
		Empleados a = null;
		String sql = "SELECT id_e, nombre, dni FROM empleados WHERE id_e =?";
		System.out.println(id);
		
		try {
			PreparedStatement sentencia = miconexion.prepareStatement(sql);
			sentencia.setInt(1, id);
			ResultSet miRs = sentencia.executeQuery();
			a = new Empleados();
			miRs.next();
			a.setId_e(miRs.getInt("id_e"));
			a.setNombre(miRs.getString("nombre"));
			a.setDni(miRs.getString("dni"));
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return a;
	}

	/**
	 * 
	 */
	public Collection<Empleados> getALL() {
		// TODO Auto-generated method stub
		return null;
	}

	public int update(Empleados ob) {
		// TODO Auto-generated method stub
		return 0;
	}

	public int delete(Empleados ob) {
		// TODO Auto-generated method stub
		return 0;
	}
}
