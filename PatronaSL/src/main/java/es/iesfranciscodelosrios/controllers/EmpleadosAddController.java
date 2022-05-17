package es.iesfranciscodelosrios.controllers;

import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Collections;
import java.util.ResourceBundle;

import javax.swing.JOptionPane;

import es.iesfranciscodelosrios.model.Puesto;
import es.iesfranciscodelosrios.utils.Connect;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

public class EmpleadosAddController implements Initializable {

	@FXML
	private Button bt_save;

	@FXML
	public TextField txt_id;

	@FXML
	public TextField txt_dni;

	@FXML
	public ComboBox<String> txt_estado;

	@FXML
	public TextArea txt_exp;

	@FXML
	public TextField txt_nombre;

	@FXML
	public ComboBox<Puesto> txt_puesto;

	int index = -1;
	Connection conn = null;
	ResultSet rs = null;
	PreparedStatement pst = null;
	PreparedStatement pst2 = null;
	String dniRegexp = "\\d{8}[A-HJ-NP-TV-Z]";
	String NombreRegexp = "[a-zA-Z]+\\.?";



	/**
	 * permite añadir atributos de empleados y guardarlos en BBDD
	 */
	@FXML
	void guardar() {
		conn = Connect.getConnect();
		String sql1 = "INSERT INTO empleados (id_e, nombre, dni,estado, experiencia) VALUES (null,?,?,?,?) ";

		try {

			if (txt_dni.getText().matches(dniRegexp) && txt_nombre.getText().matches(NombreRegexp)) {
				pst = conn.prepareStatement(sql1);
				pst.setString(1, txt_nombre.getText());
				pst.setString(2, txt_dni.getText());
				pst.setString(3, txt_estado.getValue());
				pst.setString(4, txt_exp.getText());

				pst.executeUpdate();
				JOptionPane.showMessageDialog(null, "Empleado añadido");
			} else if (txt_nombre.getText() != NombreRegexp) {
				JOptionPane.showMessageDialog(null, "Nombre no valido");
			} else {
				JOptionPane.showMessageDialog(null, "DNI no valido");
			}

		} catch (Exception e) {
			JOptionPane.showMessageDialog(null, e);
		}

	}

	/**
	 * permite modificar atributos de un empleado y guardarlos en la BBDD
	 */
	@FXML
	void Actualizar() {
		conn = Connect.getConnect();
		String sql1 = "UPDATE empleados SET nombre = ?, dni = ?, estado = ?, experiencia = ? WHERE id_e = null";

		try {
			if (txt_dni.getText().matches(dniRegexp)) {
				pst = conn.prepareStatement(sql1);
				pst.setString(1, txt_nombre.getText());
				pst.setString(2, txt_dni.getText());
				pst.setString(3, txt_estado.getValue());
				pst.setString(4, txt_exp.getText());

				pst.executeUpdate();
				JOptionPane.showMessageDialog(null, "Empleado Modificado");
			} else if (txt_nombre.getText().equals(NombreRegexp)) {
				JOptionPane.showMessageDialog(null, "Nombre no valido");
			} else {
				JOptionPane.showMessageDialog(null, "DNI no valido");
			}
		} catch (Exception e) {
			JOptionPane.showMessageDialog(null, e);
		}
	}

	@FXML
	public void initialize(URL location, ResourceBundle resources) {
		ArrayList<String> EstadoLista = new ArrayList<>();
		Collections.addAll(EstadoLista, new String[] { "INACTIVO", "ACTIVO", "VACACIONES" });

		txt_estado.getItems().addAll(EstadoLista);
	}

}
