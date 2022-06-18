package es.iesfranciscodelosrios.controllers;

import java.net.URL;
import java.util.Collection;
import java.util.ResourceBundle;
import javax.swing.JOptionPane;


import es.iesfranciscodelosrios.model.Puestos;
import es.iesfranciscodelosrios.utils.Connect;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class TabControllerOcup extends MenuController implements Initializable {
	@FXML
	private ComboBox<Puestos> ComboPuestos;
	@FXML
	private Button bt_saveOcup;
	@FXML
	public TextField id_empl;
	private ObservableList<Puestos> OcupObList;
	private Collection<Puestos> puestos;
	/**
	 * permite asignar un Empleado a un puesto y guardarlos en BBDD
	 */
	@FXML
	void guardarOcup() {

		String sql1 = "INSERT INTO trabaja (id_e, id_p) VALUES (?,?)";
		
		
		this.miConexion = Connect.getConnect();
		
		try {
			
				pst = miConexion.prepareStatement(sql1);
				pst.setInt(1, Integer.parseInt(id_empl.getText()));	
				pst.setInt(2, ComboPuestos.getValue().getId_p());
				pst.executeUpdate();
				
				JOptionPane.showMessageDialog(null, "Puesto asignado");
				Stage stage = (Stage) bt_saveOcup.getScene().getWindow();
			    stage.close();
			
			
		} catch (Exception ex) {
			JOptionPane.showMessageDialog(null, ex);
		}

	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		puestos = pD.getALL();
		OcupObList = FXCollections.observableArrayList();
		OcupObList.addAll(puestos);
		
		ComboPuestos.setItems(OcupObList);
		

	}
}
