package es.iesfranciscodelosrios.controllers;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ResourceBundle;
import javax.swing.JOptionPane;

import es.iesfranciscodelosrios.model.Empleados;
import es.iesfranciscodelosrios.utils.Connect;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class EmpleadosController implements Initializable {

	@FXML
	private Button bt_add;

	@FXML
	private Button bt_del;

	@FXML
	private Button bt_upt;

	@FXML
	private TextField filtro;

	@FXML
	private TableView<Empleados> tablaEmpleado;

	@FXML
	private TableColumn<Empleados, Integer> col_id;

	@FXML
	private TableColumn<Empleados, String> col_dni;

	@FXML
	private TableColumn<Empleados, String> col_estado;

	@FXML
	private TableColumn<Empleados, String> col_experiencia;

	@FXML
	private TableColumn<Empleados, String> col_nombre;

	private Empleados empleado;
	private ObservableList<Empleados> empleados;

	int index = -1;

	Connection conn = null;
	ResultSet rs = null;
	PreparedStatement pst = null;
	int PosEmpleado;

	/**
	 * Muestra / actualiza dentro de "EmpleadosVista" todos los Empleados añadidos
	 */
	public void UpdateTable() {
		empleados = FXCollections.observableArrayList();

		col_id.setCellValueFactory(new PropertyValueFactory<Empleados, Integer>("id_e"));
		col_nombre.setCellValueFactory(new PropertyValueFactory<Empleados, String>("nombre"));
		col_dni.setCellValueFactory(new PropertyValueFactory<Empleados, String>("dni"));
		col_estado.setCellValueFactory(new PropertyValueFactory<Empleados, String>("estado"));
		col_experiencia.setCellValueFactory(new PropertyValueFactory<Empleados, String>("experiencia"));

		empleados = Connect.obtenerDatosEmpleados();
		tablaEmpleado.setItems(empleados);
	}

	public void initialize(URL location, ResourceBundle resources) {
		UpdateTable();
		EmpleadoFind();
	}

	/**
	 * permite dentro de "EmpleadosVista" seleccionar el boton "Añadir" y muestre
	 * una ventana y se añada atributos
	 * 
	 * @param event
	 */
	@FXML
	void EmpleadoAdd(ActionEvent event) {
		FXMLLoader loader = new FXMLLoader(getClass().getResource("EmpleadosAdd.fxml"));

		try {
			Parent root = loader.load();

			Scene scene = new Scene(root);
			Stage stage = new Stage();
			stage.initModality(Modality.APPLICATION_MODAL);
			stage.setScene(scene);
			stage.showAndWait();

			UpdateTable();
			EmpleadoFind();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	/**
	 * Permite modificar un Empleado ya creado
	 * 
	 * @param event
	 */
	@FXML
	void EmpleadoMod(ActionEvent event) {
		conn = Connect.getConnect();
		PosEmpleado = tablaEmpleado.getSelectionModel().getSelectedItem().getId_e();
		FXMLLoader loader = new FXMLLoader(getClass().getResource("EmpleadosMod.fxml"));

		try {
			Parent root = loader.load();

			Scene scene = new Scene(root);
			Stage stage = new Stage();
			stage.initModality(Modality.APPLICATION_MODAL);
			stage.setScene(scene);
			stage.showAndWait();

			UpdateTable();
			EmpleadoFind();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	/**
	 * Permite al seleccionar un Empleado y pulsar el boton de "Eliminar" se borre
	 * de la base de datos
	 * 
	 * @param event
	 */
	@FXML
	void EmpleadoDel(ActionEvent event) {
		conn = Connect.getConnect();
		PosEmpleado = tablaEmpleado.getSelectionModel().getSelectedItem().getId_e();

		String sql1 = "DELETE FROM `empleados` WHERE id_e= " + PosEmpleado;

		try {
			pst = conn.prepareStatement(sql1);
			pst.executeUpdate();

			UpdateTable();
			EmpleadoFind();

			JOptionPane.showMessageDialog(null, "Empleado eliminado");

		} catch (Exception e) {
			JOptionPane.showMessageDialog(null, "Error al Eliminar Empleado" + e);
		}
	}

	/**
	 * permite buscar un empleado con el TextField filtro
	 */
	@FXML
	void EmpleadoFind() {
		empleados = FXCollections.observableArrayList();

		col_id.setCellValueFactory(new PropertyValueFactory<Empleados, Integer>("id_e"));
		col_nombre.setCellValueFactory(new PropertyValueFactory<Empleados, String>("nombre"));
		col_dni.setCellValueFactory(new PropertyValueFactory<Empleados, String>("dni"));
		col_estado.setCellValueFactory(new PropertyValueFactory<Empleados, String>("estado"));
		col_experiencia.setCellValueFactory(new PropertyValueFactory<Empleados, String>("experiencia"));

		empleados = Connect.obtenerDatosEmpleados();
		tablaEmpleado.setItems(empleados);
		FilteredList<Empleados> filtroEmpleados = new FilteredList<>(empleados, b -> true);
		filtro.textProperty().addListener((observable, oldValue, newValue) -> {
			filtroEmpleados.setPredicate(empleado -> {
				if (newValue == null || newValue.isEmpty()) {
					return true;
				}
				String FiltroMinus = newValue.toLowerCase();

				if (empleado.getNombre().toLowerCase().indexOf(FiltroMinus) != -1) {
					return true;
				} else if (empleado.getEstado().toLowerCase().indexOf(FiltroMinus) != -1) {
					return true;
				} else if (String.valueOf(empleado.getExperiencia()).indexOf(FiltroMinus) != -1)
					return true;
				else
					return false;
			});

		});
		SortedList<Empleados> listaFiltro = new SortedList<>(filtroEmpleados);
		listaFiltro.comparatorProperty().bind(tablaEmpleado.comparatorProperty());
		tablaEmpleado.setItems(listaFiltro);

	}

	public Empleados getEmpleados() {

		return empleado;
	}

}
