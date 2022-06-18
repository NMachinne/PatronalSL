package es.iesfranciscodelosrios.controllers;

import javax.swing.JOptionPane;
import es.iesfranciscodelosrios.model.Puestos;
import es.iesfranciscodelosrios.model.PuestosDao;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class TabControllerPuestos{

	@FXML
	private TextField AddNombrePuesto;
	@FXML
	private Button bt_savePuesto;
	@FXML
	private Button bt_UptPuesto;
	@FXML
	public TextField uptNombrePuesto;
	@FXML
	public TextField idPuesto;
	
	PuestosDao pD = new PuestosDao();
	Puestos pue;
	String NombreRegexp = "[a-zA-Z]+\\.?";
	MenuController mC;

	/**
	 * permite crear un Puesto con los datos añadidos y guardarlos en BBDD
	 */
	@FXML
	void guardarPuesto(ActionEvent event) {
		mC.emp = null;
		try {
			pue = new Puestos(AddNombrePuesto.getText());
			if (AddNombrePuesto.getText() == null || !AddNombrePuesto.getText().matches(NombreRegexp)) {
				JOptionPane.showMessageDialog(null, "Nombre no valido");
			} else {
				pD.insert(pue);
				mC.newPuesto=pue;
				JOptionPane.showMessageDialog(null, "Puesto añadido");		
				Stage stage = (Stage) bt_savePuesto.getScene().getWindow();
			    stage.close();
			}
		} catch (Exception e) {
			JOptionPane.showMessageDialog(null, e);
		}
	}
	
 
	/**
	 * permite modificar atributos de un puesto y actualizarlos en la BBDD
	 */
	@FXML
	void actualizarPuesto() {
		try {
			pue = new Puestos(Integer.parseInt(idPuesto.getText()), uptNombrePuesto.getText());
			if (uptNombrePuesto.getText() == null || !uptNombrePuesto.getText().matches(NombreRegexp)) {
				JOptionPane.showMessageDialog(null, "Nombre no valido");
			} else {
				pD.update(pue);
				JOptionPane.showMessageDialog(null, "Puesto Modificado");
				Stage stage = (Stage) bt_UptPuesto.getScene().getWindow();
			    stage.close();
			}
		} catch (Exception e) {
			JOptionPane.showMessageDialog(null, e);
		}
	}

}
