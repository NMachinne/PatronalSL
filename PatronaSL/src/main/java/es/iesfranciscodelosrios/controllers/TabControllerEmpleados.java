package es.iesfranciscodelosrios.controllers;

import java.net.URL;
import java.util.ArrayList;
import java.util.Collections;
import java.util.ResourceBundle;
import javax.swing.JOptionPane;
import es.iesfranciscodelosrios.model.Empleados;
import es.iesfranciscodelosrios.model.EmpleadosDao;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class TabControllerEmpleados implements Initializable {
	@FXML
	private Button bt_save;
	@FXML
	private Button bt_upt;
	@FXML
	public TextField txt_dni;
	@FXML
	public ComboBox<String> txt_estado;
	@FXML
	public TextArea txt_exp;
	@FXML
	public TextField txt_nombre;
	@FXML
	public TextField txt_id;

	EmpleadosDao eD = new EmpleadosDao();
	Empleados emp;
	String dniRegexp = "\\d{8}[A-HJ-NP-TV-Z]";
	String NombreRegexp = "[a-zA-Z]+\\.?";
	MenuController mC;

	/**
	 * permite crear un Empleado con los datos añadidos y guardarlos en BBDD
	 */
	@FXML
	void guardarEmpleado(ActionEvent event) {
		mC.emp = null;
		try {
			emp = new Empleados(txt_nombre.getText(), txt_dni.getText(), txt_estado.getValue(), txt_exp.getText());
			if (txt_nombre.getText() == null || !txt_nombre.getText().matches(NombreRegexp)) {
				JOptionPane.showMessageDialog(null, "Nombre no valido");
			} else if (txt_dni.getText() == null || !txt_dni.getText().matches(dniRegexp)) {
				JOptionPane.showMessageDialog(null, "DNI no valido ");
			} else if (eD.getDni(txt_dni.getText())) {
				JOptionPane.showMessageDialog(null, "DNI  repetido");
			} else if (txt_estado.getValue() == null) {
				JOptionPane.showMessageDialog(null, "el Empleado debe tener un ESTADO");
			} else {
				eD.insert(emp);
				mC.newEmpleado = emp;
				JOptionPane.showMessageDialog(null, "Empleado añadido");
				Stage stage = (Stage) bt_save.getScene().getWindow();
				stage.close();
			}

		} catch (Exception e) {
			JOptionPane.showMessageDialog(null, e);
		}
	}

	/**
	 * permite modificar atributos de un empleado y actualizarlos en la BBDD
	 */
	@FXML
	void actualizarEmpleado() {
		Stage stage = (Stage) bt_upt.getScene().getWindow();
		try {
			emp = new Empleados(Integer.parseInt(txt_id.getText()), txt_nombre.getText(), txt_dni.getText(),
					txt_estado.getValue(), txt_exp.getText());
			if (txt_nombre.getText() == null || !txt_nombre.getText().matches(NombreRegexp)) {
				JOptionPane.showMessageDialog(null, "Nombre no valido");
			} else if (txt_dni.getText() == null || !txt_dni.getText().matches(dniRegexp)) {
				JOptionPane.showMessageDialog(null, "DNI no valido");
				if (eD.getDni(txt_dni.getText())) {
					JOptionPane.showMessageDialog(null, "DNI  repetido");
				}
			}
			 else if (txt_estado.getValue() == null) {
				JOptionPane.showMessageDialog(null, "el Empleado debe tener un ESTADO");
			} else {
				eD.update(emp);
				JOptionPane.showMessageDialog(null, "Empleado modificado ");

				stage.close();
			}

		} catch (Exception e) {
			JOptionPane.showMessageDialog(null, e);
		}
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {

		ArrayList<String> EstadoLista = new ArrayList<>();
		Collections.addAll(EstadoLista, new String[] { "INACTIVO", "ACTIVO", "VACACIONES" });
		txt_estado.getItems().addAll(EstadoLista);

	}

}
