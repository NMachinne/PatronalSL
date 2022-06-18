package es.iesfranciscodelosrios.utils;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;

public class Connect {

	private static Connection con;
	private static Connect _newInstance;

	private Connect() {
		try {
			DatosConexion dc = load();
			con = DriverManager.getConnection(dc.getServer() + "/" + dc.getDatabase(), dc.getUsername(),
					dc.getPassword());
		} catch (SQLException e) {
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

	public DatosConexion load() {
		DatosConexion dc = new DatosConexion();
		JAXBContext contexto;
		try {
			contexto = JAXBContext.newInstance(DatosConexion.class);
			Unmarshaller um = contexto.createUnmarshaller();
			dc = (DatosConexion) um.unmarshal(Connect.class.getResource("/conection/conexion.xml"));
		} catch (JAXBException e) {
			e.printStackTrace();
		}
		return dc;
	}
}
