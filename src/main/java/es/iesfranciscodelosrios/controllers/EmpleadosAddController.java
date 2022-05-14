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
import javafx.event.ActionEvent;
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
	private TextField txt_id;

	@FXML
	private TextField txt_dni;

	@FXML
    private ComboBox<String> txt_estado;

	@FXML
	private TextArea txt_exp;

	@FXML
	private TextField txt_nombre;

	@FXML
    private ComboBox<Puesto> txt_puesto;

	int index = -1;
	Connection conn = null;
	ResultSet rs = null;
	PreparedStatement pst = null;
	int PosEmpleado;
	
	
	@FXML
	private void comboboxevent(ActionEvent event) {
	}
	
	/**
	 * permite añadir atributos de empleados y guardarlos en BBDD
	 */
	@FXML
	void guardar() {
		conn = Connect.getConnect();
		String sql1 = "INSERT INTO empleados (id_e, nombre, dni,estado, experiencia) VALUES (null,?,?,?,?)";
		String dniRegexp = "\\d{8}[A-HJ-NP-TV-Z]";
		String NombreRegexp = "[a-zA-Z]+\\.?";
		try {
			
			do {
				
				pst = conn.prepareStatement(sql1);
				pst.setString(1, txt_nombre.getText());
				pst.setString(2, txt_dni.getText());
				pst.setString(3, txt_estado.getValue());
				pst.setString(4, txt_exp.getText());
				pst.executeUpdate();
				if (txt_dni.getText() != (dniRegexp)) {
					JOptionPane.showMessageDialog(null, "DNI no valido");
				} 
				else if (txt_nombre.getText() != (NombreRegexp)) {
					JOptionPane.showMessageDialog(null, "Nombre no valido");
				}
			} while (txt_dni.getText() ==dniRegexp && txt_nombre.getText() ==(NombreRegexp));
			
			
			
			
			JOptionPane.showMessageDialog(null, "Empleado añadido");
		} catch (Exception e ) {
			JOptionPane.showMessageDialog(null, e);
		}

	}
	
	/**
	 * permite modificar atributos de un empleado y guardarlos en la BBDD
	 */
	@FXML
	void Actualizar() {
		conn = Connect.getConnect();
		
		
		String sql1 = "UPDATE empleados SET id_e=\"" +txt_id.getText() +
				"\""+",nombre=\"" +txt_nombre.getText()+
				"\""+",dni=\"" +txt_dni.getText()+
				"\""+",estado=\""+txt_estado.getValue()+
				"\""+",experiencia=\"" +txt_exp.getText()+
				"\""+" where id_e\""+txt_id.getText()+"\"";
	

		try {
			pst = conn.prepareStatement(sql1);
			
			pst.executeUpdate();

			JOptionPane.showMessageDialog(null, "Empleado modificado");
		} catch (Exception e) {
			JOptionPane.showMessageDialog(null, e);
		}
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		ArrayList<String> EstadoLista = new ArrayList<>();
		Collections.addAll(EstadoLista, new String[] {"ACTIVO", "VACACIONES",});
		
		txt_estado.getItems().addAll(EstadoLista);
	}
	
	

}
