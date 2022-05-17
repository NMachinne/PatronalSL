package es.iesfranciscodelosrios.model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Collection;

import es.iesfranciscodelosrios.interfaces.IDao;
import es.iesfranciscodelosrios.utils.Connect;

public class TrabajaDao implements IDao<trabaja, Integer> {

	private Connection miconexion;

	public TrabajaDao() {
		this.miconexion = Connect.getConnect();

	}

	public boolean insert(trabaja ob) {
		return false;
	}

	public trabaja get(Integer id) {
		trabaja p = null;
		String sql = "SELECT id_puesto, id_e FROM trabaja WHERE ?";
		try {
			PreparedStatement sentencia = miconexion.prepareStatement(sql);
			sentencia.setInt(1, id);
			ResultSet miRs = sentencia.executeQuery();
			p = new trabaja();
			miRs.next();
			p.setId_p(miRs.getInt("id_e"));
			p.setId_p(miRs.getInt("id_puesto"));

		} catch (SQLException e) {
			e.printStackTrace();
		}

		return p;
	}

	@Override
	public Collection<trabaja> getALL() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int update(trabaja ob) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int delete(trabaja ob) {
		// TODO Auto-generated method stub
		return 0;
	}

}
