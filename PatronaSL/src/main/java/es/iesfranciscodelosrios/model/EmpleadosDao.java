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

public class EmpleadosDao implements IDao<Empleados, Integer> {
	Connection miConexion;
	int index = -1;
	ResultSet rs = null;
	PreparedStatement pst = null;
	boolean booleano = false;
	Persona prs;
	
	public EmpleadosDao() {
		this.miConexion = Connect.getConnect();
	}

	/**
	 * Metodo que inserta un empleado en la BBDD introducimos un empleado con sus
	 * atributos si ha podido insertarlo devuelve true, sino false.
	 */
	public boolean insert(Empleados empleado) {
		booleano = false;
		this.miConexion = Connect.getConnect();

		String sql = "INSERT INTO empleados VALUES (null,?,?,?,?)";
		try {
			pst = miConexion.prepareStatement(sql);
			pst.setString(1, empleado.getNombre());
			pst.setString(2, empleado.getDni());
			pst.setString(3, empleado.getEstado());
			pst.setString(4, empleado.getExperiencia());
			pst.executeUpdate();
			
			booleano = true;

		} catch (SQLException e) {
			e.printStackTrace();
		}
		return booleano;
	}

	/**
	 * Metodo que obtiene el Empleado a traves de su Identificador
	 */
	public Empleados get(Integer id) {
		Empleados idEmpleado = null;
		miConexion = Connect.getConnect();

		String sql = "SELECT id_e, nombre, dni , estado , experiencia FROM empleados WHERE id_e =?";

		try {
			pst = miConexion.prepareStatement(sql);
			rs = pst.executeQuery();
			idEmpleado = new Empleados();
			rs.next();
			idEmpleado.setId_e(rs.getInt("id_e"));
			idEmpleado.setNombre(rs.getString("nombre"));
			idEmpleado.setDni(rs.getString("dni"));
			idEmpleado.setEstado(rs.getString("estado"));
			idEmpleado.setExperiencia(rs.getString("experiencia"));
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return idEmpleado;
	}



	/**
	 * obtiene los datos obtenidos de un DNI especifico
	 * @param dni que se le pasa de tipo STRING
	 * @return devuelve true si existe un empleado con ese dni y false no lo devuelve.
	 */
	public boolean getDni( String dni) {
		String sql = "SELECT * FROM empleados WHERE dni =?";
		
		try {
			pst = miConexion.prepareStatement(sql);
			pst.setString(1, dni);
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
	 * obtiene el id del empleado dentro de la tabla trabaja
	 * @param id del empleado que se le pasa 
	 * @return devuelve true si existe un empleado en esta tabla con "int id_e" y false no lo devuelve.
	 */
	public boolean getId( int id_e) {
		String sql = "SELECT id_e FROM trabaja WHERE id_e =?";
		booleano = false;
		try {
			pst = miConexion.prepareStatement(sql);
			pst.setInt(1, id_e);
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
	 * Metodo que obtiene todos los Empleados en forma de coleccion busca los
	 * empleados y devuelve sus atributos
	 */
	@Override
	public Collection<Empleados> getALL() {
		miConexion = Connect.getConnect();
		Collection<Empleados> listaEmpleado = new ArrayList<Empleados>();
		String sql1 = "SELECT id_e, nombre, dni, estado, experiencia FROM empleados";

		try {
			pst = miConexion.prepareStatement(sql1);
			rs = pst.executeQuery();
			while (rs.next()) {
				Empleados tmpEmp = new Empleados();
				tmpEmp.setId_e(rs.getInt(1));
				tmpEmp.setNombre(rs.getString(2));
				tmpEmp.setDni(rs.getString(3));
				tmpEmp.setEstado(rs.getString(4));
				tmpEmp.setExperiencia(rs.getString(5));
				listaEmpleado.add(tmpEmp);
				pst.executeUpdate();
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return listaEmpleado;

	}

	/**
	 * muestra una lista completa de los empleados obtenidos de la BBDD
	 * 
	 * @return devuelve la lista
	 */
	public List<Empleados> obtenerDatosEmpleados() {
		miConexion = Connect.getConnect();
		ObservableList<Empleados> list = FXCollections.observableArrayList();
		String sql1 = "SELECT * FROM empleados";
		try {
			pst = miConexion.prepareStatement(sql1);
			rs = pst.executeQuery();
			while (rs.next()) {
				list.add(new Empleados(Integer.parseInt(rs.getString("id_e")), rs.getString("nombre"),
						rs.getString("dni"), rs.getString("estado"), rs.getString("experiencia")));
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}

	/**
	 * Metodo para recibir el puesto o puestos de un empleado dentro de la tabla
	 * trabaja
	 * 
	 * @param puesto
	 * @return devuelve el empleado seleccionado y sus puestos asigandos
	 */
	public List<Puestos> getAllPuestos(Empleados empleado) {
		miConexion = Connect.getConnect();
		List<Puestos> listaPuesto = new ArrayList<Puestos>();
		String sql1 = "SELECT p.id_p, p.nombre FROM puestos p , trabaja t, empleados e "
				+ "WHERE e.id_e= ? AND e.id_e =t.id_e AND t.id_p =p.id_p ";
		try {
			pst = miConexion.prepareStatement(sql1);
			pst.setInt(1, empleado.getId_e());
			rs = pst.executeQuery();
			while (rs.next()) {
				Puestos aux = new Puestos(rs.getInt("id_p"), rs.getString("nombre"));
				listaPuesto.add(aux);
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}
		return listaPuesto;
	}
	
	

	/**
	 * Metodo que permite actualizar un Empleado excepto el ID porque lo identifica.
	 * tambien lo actualiza de la BBDD
	 */
	@Override
	public boolean update(Empleados empleado) {
		booleano = false;
		miConexion = Connect.getConnect();
		String sql1 = "UPDATE empleados SET nombre = ?, dni = ?, estado = ?, experiencia = ? WHERE id_e ="
				+ empleado.getId_e();
		try {
			pst = miConexion.prepareStatement(sql1);
			pst.setString(1, empleado.getNombre());
			pst.setString(2, empleado.getDni());
			pst.setString(3, empleado.getEstado());
			pst.setString(4, empleado.getExperiencia());

			pst.executeUpdate();
			booleano = true;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return booleano;

	}

	/**
	 * Metodo que permite eliminar un Empleado completo y borrarlo de la BBDD
	 */
	@Override
	public boolean delete(Empleados empleado) {
		booleano = false;
		miConexion = Connect.getConnect();
		String sql1 = "DELETE FROM empleados WHERE id_e=" + empleado.getId_e();
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
