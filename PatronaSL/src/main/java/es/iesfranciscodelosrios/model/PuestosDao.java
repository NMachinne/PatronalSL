package es.iesfranciscodelosrios.model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import es.iesfranciscodelosrios.interfaces.IDao;
import es.iesfranciscodelosrios.utils.Connect;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class PuestosDao implements IDao<Puestos, Integer> {
	private Connection miConexion;
	int index = -1;
	ResultSet rs = null;
	PreparedStatement pst = null;
	boolean booleano;

	public PuestosDao() {
		this.miConexion = Connect.getConnect();
	}

	/**
	 * Metodo que inserta un puesto en la BBDD introducimos un puesto con sus
	 * atributos si ha podido insertarlo devuelve true, sino false.
	 */
	public boolean insert(Puestos puesto) {
		booleano = false;
		miConexion = Connect.getConnect();
		String sql = "INSERT INTO puestos VALUES (null,?)";
		try {
			pst = miConexion.prepareStatement(sql);
			
			pst.setString(1, puesto.getNombre());
			pst.executeUpdate();
			
			booleano = true;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return booleano;
	}

	/**
	 * Metodo que obtiene el puesto a traves de su Identificador
	 */
	public Puestos get(Integer id) {
		Puestos idPuesto = null;
		miConexion = Connect.getConnect();
		String sql = "SELECT id_p, nombre FROM puestos WHERE id_p =?";

		try {
			pst = miConexion.prepareStatement(sql);
			pst.setInt(1, id);
			rs = pst.executeQuery();
			idPuesto = new Puestos();
			rs.next();
			idPuesto.setId_p(rs.getInt("id_p"));
			idPuesto.setNombre(rs.getString("nombre"));
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return idPuesto;
	}

	/**
	 * Metodo que obtiene todos los puestos en forma de coleccion busca los puestos
	 * y devuelve sus atributos
	 */
	@Override
	public Collection<Puestos> getALL() {
		Collection<Puestos> listaPuesto = new ArrayList<Puestos>();
		miConexion = Connect.getConnect();
		String sql1 = "SELECT id_p, nombre FROM puestos";

		try {
			pst = miConexion.prepareStatement(sql1);
			rs = pst.executeQuery();
			while (rs.next()) {
				Puestos tmpPto = new Puestos();
				tmpPto.setId_p(rs.getInt(1));
				tmpPto.setNombre(rs.getString(2));

				listaPuesto.add(tmpPto);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return listaPuesto;

	}
	
	/**
	 * obtiene el id del puesto dentro de la tabla trabaja
	 * @param id del puesto que se le pasa 
	 * @return devuelve true si existe un puesto en esta tabla con "int puesto" y false no lo devuelve.
	 */
	public boolean getPuesto( int puesto) {
		String sql = "SELECT id_p FROM trabaja WHERE id_p =?";
		booleano = false;
		try {
			pst = miConexion.prepareStatement(sql);
			pst.setInt(1, puesto);
			rs = pst.executeQuery();
			 if (rs.next()) 
	            {
	                booleano=true;
	            }		
		} catch (SQLException e) {
			e.printStackTrace();
		}	
		return booleano;
	}

	/**
	 * Metodo que permite actualizar un Puesto excepto el ID porque lo identifica.
	 * tambien lo actualiza de la BBDD
	 */
	@Override
	public boolean update(Puestos puesto) {
		booleano = false;
		miConexion = Connect.getConnect();
		String sql1 = "UPDATE puestos SET nombre = ? WHERE id_p =" + puesto.getId_p();
		try {
			pst = miConexion.prepareStatement(sql1);
			pst.setString(1, puesto.getNombre());
			pst.executeUpdate();
			booleano = true;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return booleano;

	}

	/**
	 * muestra una lista completa de los peustos obtenidos de la BBDD
	 * 
	 * @return devuelve la lista
	 */
	public List<Puestos> obtenerDatosPuestos() {
		miConexion = Connect.getConnect();
		ObservableList<Puestos> list = FXCollections.observableArrayList();
		String sql1 = "SELECT * FROM puestos";
		try {
			pst = miConexion.prepareStatement(sql1);
			rs = pst.executeQuery();
			while (rs.next()) {
				list.add(new Puestos(Integer.parseInt(rs.getString("id_p")), rs.getString("nombre")));
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}
	
	

	/**
	 * Metodo que permite eliminar un puesto completo y borrarlo de la BBDD
	 */
	@Override
	public boolean delete(Puestos puesto) {
		booleano = false;
		miConexion = Connect.getConnect();
		String sql1 = "DELETE FROM puestos WHERE id_p=" + puesto.getId_p();
		try {
			pst = miConexion.prepareStatement(sql1);
			pst.executeUpdate();
			booleano = true;
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return booleano;
	}

}
