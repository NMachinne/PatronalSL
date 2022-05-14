package es.iesfranciscodelosrios.utils;

import java.io.File;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;

import es.iesfranciscodelosrios.model.Empleados;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class Connect extends Empleados{

	private static Connection con;
	private static String file = "conexion.xml";
	private static Connect _newInstance;

	private Connect() {
		try {
			DatosConexion dc = load();
			con = DriverManager.getConnection(dc.getServer() + "/" + dc.getDatabase(), dc.getUsername(),
					dc.getPassword());
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			con = null;
		}
	}

	public static Connection getConnect() {
		if (_newInstance == null) {
			_newInstance = new Connect();
		}
		return con;
	}

	public static ObservableList<Empleados> obtenerDatosEmpleados() {
		Connection conn = getConnect();
		ObservableList<Empleados> list = FXCollections.observableArrayList();

		try {
			PreparedStatement ps = conn.prepareStatement("SELECT * FROM empleados");
			ResultSet rs = ps.executeQuery();

			while (rs.next()) {
				list.add(new Empleados(Integer.parseInt(rs.getString("id_e")), rs.getString("nombre"), rs.getString("dni"),rs.getString("estado"), rs.getString("experiencia")));
				
			}
		} catch (Exception e) {
		}

		return list;
	}

	public DatosConexion load() {
		DatosConexion dc = new DatosConexion();
		JAXBContext contexto;
		try {
			contexto = JAXBContext.newInstance(DatosConexion.class);
			Unmarshaller um = contexto.createUnmarshaller();
			dc = (DatosConexion) um.unmarshal(new File(file));
		} catch (JAXBException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return dc;
	}
}
