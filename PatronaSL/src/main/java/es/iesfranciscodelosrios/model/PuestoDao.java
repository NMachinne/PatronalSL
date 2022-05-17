package es.iesfranciscodelosrios.model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Collection;

import es.iesfranciscodelosrios.interfaces.IDao;
import es.iesfranciscodelosrios.utils.Connect;

public class PuestoDao implements IDao<Puesto, Integer> {

	private Connection miconexion;

	public PuestoDao() {
		this.miconexion = Connect.getConnect();

	}

	public boolean insert(Empleados ob) {
		return false;
	}

	public Puesto get(Integer id) {
		Puesto p = null;
		String sql = "SELECT id_puesto, nombre FROM puesto WHERE id_puesto =?";
		try {
			PreparedStatement sentencia = miconexion.prepareStatement(sql);
			sentencia.setInt(1, id);
			ResultSet miRs = sentencia.executeQuery();
			p = new Puesto();
			miRs.next();
			p.setIdP(miRs.getInt("id_puesto"));
			p.setNombreP(miRs.getString("nombre"));

		} catch (SQLException e) {
			e.printStackTrace();
		}

		return p;
	}

	@Override
	public boolean insert(Puesto ob) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public Collection<Puesto> getALL() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int update(Puesto ob) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int delete(Puesto ob) {
		// TODO Auto-generated method stub
		return 0;
	}

}
