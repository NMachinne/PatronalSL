package es.iesfranciscodelosrios.controllers;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import java.awt.*;
import javax.swing.JOptionPane;

import es.iesfranciscodelosrios.model.Empleados;
import es.iesfranciscodelosrios.model.EmpleadosDao;
import es.iesfranciscodelosrios.model.Puestos;
import es.iesfranciscodelosrios.model.PuestosDao;
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
import javafx.scene.control.Hyperlink;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.ImageView;

import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.stage.Stage;


public class MenuController implements Initializable {
	@FXML
	public TableView<Empleados> tablaEmpleados;
	@FXML
	public TableView<Puestos> tablaPuestos;
	@FXML
	public TableView<Puestos> tablaOcupacion;
	@FXML
	private Button bt_add;
	@FXML
	private Button bt_addPuesto;
	@FXML
	private Button bt_del;
	@FXML
	private Button bt_delPuesto;
	@FXML
	private Button bt_empleados;
	@FXML
	private Button bt_inicio;
	@FXML
	private Button bt_modPuesto;
	@FXML
	private Button bt_ocup;
	@FXML
	private Button bt_puestos;
	@FXML
	private Button bt_upt;
	@FXML
	private Button bt_EP_Del;
	@FXML
	private Button bt_EP_as;
	@FXML
	private Button bt_EP_mo;
	@FXML
	private TableColumn<Puestos, String> col_Ocup_Puesto_DESCRIPCION;
	@FXML
	private TableColumn<Puestos, Integer> col_Ocup_Puesto_ID;
	@FXML
	private TableColumn<Puestos, String> col_Ocup_Puesto_NOMBRE;
	@FXML
	protected TableColumn<Empleados, String> col_dni;
	@FXML
	protected TableColumn<Empleados, String> col_estado;
	@FXML
	protected TableColumn<Empleados, String> col_experiencia;
	@FXML
	protected TableColumn<Empleados, Integer> col_id;
	@FXML
	protected TableColumn<Empleados, Integer> col_id_P;
	@FXML
	protected TableColumn<Empleados, String> col_nombre;
	@FXML
	private TableColumn<Puestos, String> col_nombre_P;
	@FXML
	private TextField filtroEmp;
	@FXML
	private TextField filtroPue;
	@FXML
	private Text txt_emp_select;
	@FXML
	private GridPane gr_welcome;
	@FXML
	private GridPane grd_empleaado;
	@FXML
	private GridPane grd_inicio;
	@FXML
	private GridPane grd_ocup;
	@FXML
	private GridPane grd_peustos;
	@FXML
	private Hyperlink linkHomePage;
	@FXML
	private ImageView logoAdd;
	@FXML
	private ImageView logoDel;
	@FXML
	private ImageView logoMod;
	@FXML
	private AnchorPane menuEmp;
	@FXML
	private AnchorPane menuPue;
	@FXML
	private AnchorPane menuOcup;
	@FXML
	private AnchorPane topmenu;

	EmpleadosDao eD = new EmpleadosDao();
	PuestosDao pD = new PuestosDao();
	Empleados emp;
	Puestos pue;
	private List<Puestos> listaPuestos;
	private List<Empleados> listaEmpleados;
	private ObservableList<Empleados> empObList;
	private ObservableList<Puestos> pueObList;
	private TabControllerEmpleados tabCtrlEmp;
	private TabControllerPuestos tabCtrlPue;
	

	public Empleados newEmpleado = null;
	public Puestos newPuesto = null;

	public int id_selected = -1;
	Connection miConexion;
	int index = -1;
	Connection conn = null;
	ResultSet rs = null;
	PreparedStatement pst = null;

	/**
	 * Cuando se pulse los botones "inicio","empleados" o "puestos" se cambiara de
	 * vista
	 */
	@FXML
	private void handleClick(ActionEvent event) {
		if (event.getSource() == bt_inicio) {
			topmenu.toFront();
			grd_inicio.toFront();

		}
		if (event.getSource() == bt_empleados) {
			grd_empleaado.toFront();
			menuEmp.toFront();
			textFinder(event);
			
		}
		if (event.getSource() == bt_ocup) {
			if (tablaEmpleados.getSelectionModel().getSelectedItem() == null) {
				JOptionPane.showMessageDialog(null, "Empleado no seleccionado");
			} else {
				ocupacionEmpleados(event);
				grd_ocup.toFront();
				menuOcup.toFront();
				UpdateTableEmpleados();
				UpdateTablePuestos();
			}
		}
		if (event.getSource() == bt_puestos) {
			grd_peustos.toFront();
			menuPue.toFront();
			textFinder(event);
			

		}
	}

	/**
	 * Muestra / actualiza dentro de "VistaPrincip" la tabla de Empleados
	 */
	public void UpdateTableEmpleados() {
		this.miConexion = Connect.getConnect();
		listaEmpleados = eD.obtenerDatosEmpleados();
		empObList = FXCollections.observableArrayList();
		empObList.addAll(listaEmpleados);
		col_id.setCellValueFactory(new PropertyValueFactory<>("id_e"));
		col_nombre.setCellValueFactory(new PropertyValueFactory<>("nombre"));
		col_dni.setCellValueFactory(new PropertyValueFactory<>("dni"));
		col_estado.setCellValueFactory(new PropertyValueFactory<>("estado"));
		col_experiencia.setCellValueFactory(new PropertyValueFactory<>("experiencia"));

		try {
			id_selected = tablaEmpleados.getSelectionModel().getSelectedItem().getId_e();
		} catch (Exception e) {
			
		}
		
		tablaEmpleados.setItems(empObList);
		tablaEmpleados.refresh();
		
	}

	/**
	 * Muestra / actualiza dentro de "VistaPrincip" la tabla de puestos
	 */
	public void UpdateTablePuestos() {
		this.miConexion = Connect.getConnect();
		listaPuestos = pD.obtenerDatosPuestos();
		pueObList = FXCollections.observableArrayList();
		pueObList.addAll(listaPuestos);
		col_id_P.setCellValueFactory(new PropertyValueFactory<>("id_p"));
		col_nombre_P.setCellValueFactory(new PropertyValueFactory<>("nombre"));
		
		try {
			id_selected = tablaPuestos.getSelectionModel().getSelectedItem().getId_p();
		} catch (Exception e) {
			
		}

		tablaPuestos.setItems(pueObList);
		tablaPuestos.refresh();

	}
	
	/**
	 * permite dentro de la ventana "EMPLEADOS" seleccionar un empleado y acceder a
	 * los puestos que tiene acceso y mostrarlos en una tabla.
	 * 
	 */
	void ocupacionEmpleados(ActionEvent event) {	
			emp = tablaEmpleados.getSelectionModel().getSelectedItem();
			
			if (emp != null) {
				txt_emp_select.setText(emp.getNombre());
			}
			
	
		try {
			List<Puestos> puesT = new ArrayList<Puestos>();
			puesT = eD.getAllPuestos(emp);
			pueObList = FXCollections.observableArrayList();
			pueObList.addAll(puesT);	
			col_Ocup_Puesto_ID.setCellValueFactory(new PropertyValueFactory<Puestos, Integer>("id_p"));
			col_Ocup_Puesto_NOMBRE.setCellValueFactory(new PropertyValueFactory<Puestos, String>("nombre"));
			
		} catch (Exception e) {
			JOptionPane.showMessageDialog(null, e);
		}
		tablaOcupacion.setItems(pueObList);
		tablaOcupacion.refresh();
	}

	/**
	 * permite dentro de la pestaña "EMPLEADOS" seleccionar el boton "Añadir" y
	 * muestre una ventana.
	 */
	@FXML
	void EmpleadoAdd(ActionEvent event) {

		FXMLLoader loader = new FXMLLoader(getClass().getResource("EmpleadosAdd.fxml"));
		try {
			Parent root = loader.load();
			TabControllerEmpleados c = loader.getController();
			c.mC = this;
			Scene scene = new Scene(root);
			Stage stage = new Stage();
			stage.initModality(Modality.APPLICATION_MODAL);
			stage.setScene(scene);
			stage.showAndWait();
			if (newEmpleado != null) {
				this.listaEmpleados.add(newEmpleado);
				this.empObList.add(newEmpleado);
				this.newEmpleado = null;
			} 
			UpdateTableEmpleados();

		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * permite dentro de la pestaña "EMPLEADOS/OCUPACION" seleccionar el boton
	 * "Asignar" y muestre una ventana.
	 */
	@FXML
	void OcupAdd(ActionEvent event) {
		emp = tablaEmpleados.getSelectionModel().getSelectedItem();
		
		FXMLLoader loader = new FXMLLoader(getClass().getResource("EmpPueAdd.fxml"));

		try {
			Parent root = loader.load();
			TabControllerOcup t = loader.getController();
			Scene scene = new Scene(root);
			Stage stage = new Stage();
			
			stage.initModality(Modality.APPLICATION_MODAL);
			stage.setScene(scene);
			t.id_empl.setText("" + id_selected);
			t.id_empl.setVisible(false);
			stage.showAndWait();
			ocupacionEmpleados(event);
			

		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	

	/**
	 * permite dentro de la pestaña "PUESTOS" seleccionar el boton "Añadir" y
	 * muestre una ventana.
	 */
	@FXML
	void PuestoAdd(ActionEvent event) {
		FXMLLoader loader = new FXMLLoader(getClass().getResource("PuestosAdd.fxml"));
		try {
			Parent root = loader.load();
			TabControllerPuestos c = loader.getController();
			c.mC = this;
			Scene scene = new Scene(root);
			Stage stage = new Stage();
			stage.initModality(Modality.APPLICATION_MODAL);
			stage.setScene(scene);
			stage.showAndWait();
			if (newPuesto != null) {
				this.listaPuestos.add(newPuesto);
				this.pueObList.add(newPuesto);
				this.newPuesto = null;
			}
			UpdateTablePuestos();

		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * permite dentro de la pestaña "EMPLEADOS" seleccionar el boton "MODIFICAR" y
	 * muestre una ventana y devuelva los datos del empleado.
	 * 
	 */
	@FXML
	void EmpleadoMod(ActionEvent event) {
		tablaEmpleados.getSelectionModel().getSelectedItem();
		if (this.emp == null) {
			JOptionPane.showMessageDialog(null, "Empleado no Seleccionado");
		} else {
			FXMLLoader loader = new FXMLLoader(getClass().getResource("EmpleadosMod.fxml"));
			try {
				Parent root = loader.load();
				tabCtrlEmp = loader.getController();
				getAtribEmpleado();
				Scene scene = new Scene(root);
				Stage stage = new Stage();
				stage.initModality(Modality.APPLICATION_MODAL);
				stage.setScene(scene);
				stage.showAndWait();
				UpdateTableEmpleados();
				

			} catch (IOException e) {
				e.printStackTrace();
			}
		}

	}

	/**
	 * permite dentro de la pestaña "PUESTOS" seleccionar el boton "MODIFICAR" y
	 * muestre una ventana y devuelva los datos del puesto.
	 *
	 */
	@FXML
	void PuestoMod(ActionEvent event) {
		if (this.pue == null) {
			JOptionPane.showMessageDialog(null, "Puesto no Seleccionado");
		} else {
			FXMLLoader loader = new FXMLLoader(getClass().getResource("PuestosMod.fxml"));
			try {
				Parent root = loader.load();
				tabCtrlPue = loader.getController();
				getAtribPuesto();
				Scene scene = new Scene(root);
				Stage stage = new Stage();

				stage.initModality(Modality.APPLICATION_MODAL);
				stage.setScene(scene);
				stage.showAndWait();
				UpdateTablePuestos();

			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	/**
	 * permite dentro de la pestaña "EMPLEADOS" seleccionar un empleado y cuando
	 * selecione el boton "ELIMINAR" lo elimine de la BBDD
	 * 
	 */
	@FXML
	void EmpleadoDel(ActionEvent event) {
		emp = tablaEmpleados.getSelectionModel().getSelectedItem();
		try {
			if (emp == null) {
				JOptionPane.showMessageDialog(null, "Empleado No Seleccionado");
			} else {
				int resp = JOptionPane.showConfirmDialog(null, "¿Estas seguro de ELIMINAR a " + emp.getNombre() + "?",
						"ELIMINAR EMPLEADO", JOptionPane.YES_NO_OPTION);
				if (resp == JOptionPane.YES_OPTION) {
					eD.delete(emp);
					UpdateTableEmpleados();
					JOptionPane.showMessageDialog(null, "Empleado eliminado");
				} else if (resp == JOptionPane.NO_OPTION) {
					JOptionPane.showMessageDialog(null, "Buena respuesta");
				}

			}
		} catch (Exception e) {
			JOptionPane.showMessageDialog(null, "Error al eliminar Empleado" + e);
		}
	}

	/**
	 * permite dentro de la pestaña "PUESTOS" seleccionar un puesto y cuando
	 * selecione el boton "ELIMINAR" lo elimine de la BBDD
	 * 
	 */
	@FXML
	void PuestoDel(ActionEvent event) {
		pue = tablaPuestos.getSelectionModel().getSelectedItem();
		try {
			if (pue == null) {
				JOptionPane.showMessageDialog(null, "Puesto No Seleccionado");
			} else {
				int resp = JOptionPane.showConfirmDialog(null, "¿Estas seguro de ELIMINAR a " + pue.getNombre() + "?",
						"ELIMINAR PUESTO", JOptionPane.YES_NO_OPTION);
				if (resp == JOptionPane.YES_OPTION) {
					pD.delete(pue);
					UpdateTablePuestos();
					
					JOptionPane.showMessageDialog(null, "Puesto eliminado");
				} else if (resp == JOptionPane.NO_OPTION) {
					JOptionPane.showMessageDialog(null, "Buena respuesta");
				}
			}
		} catch (Exception e) {
			JOptionPane.showMessageDialog(null, "Error al eliminar Puesto" + e);
		}
	}

	/**
	 * permite dentro de la pestaña "EMPLEADOS/OCUPACION" seleccionar un empleado y
	 * cuando selecione el boton "ELIMINAR" elimine el puesto asignado de la BBDD
	 * 
	 */
	@FXML
	void OcupDel(ActionEvent event) {
		pue = tablaOcupacion.getSelectionModel().getSelectedItem();
		String sql1 = "DELETE FROM trabaja WHERE id_e = ? AND id_p = ?";
		this.miConexion = Connect.getConnect();
		try {
			if (pue == null) {
				JOptionPane.showMessageDialog(null, "Ocupacion No Seleccionada");
			} else {
				int resp = JOptionPane.showConfirmDialog(null, "¿Estas seguro de ELIMINAR el puesto " + pue.getNombre()
						+ " del empleado " + emp.getNombre() + "?", "ELIMINAR PUESTO ASIGNADO",
						JOptionPane.YES_NO_OPTION);
				if (resp == JOptionPane.YES_OPTION) {
					pst = miConexion.prepareStatement(sql1);
					pst.setInt(1, id_selected);
					pst.setInt(2, pue.getId_p());
					pst.executeUpdate();
					JOptionPane.showMessageDialog(null, "Ocupacion eliminada");
					
				} else if (resp == JOptionPane.NO_OPTION) {
					JOptionPane.showMessageDialog(null, "Buena respuesta");
				}
			}
		} catch (Exception e) {
			JOptionPane.showMessageDialog(null, "Error al eliminar Ocupacion" + e);
		}

	}

	/**
	 * Metodo que permite buscar un empleado o puesto por nombre, dni o estado.
	 * 
	 * @param event se le pasa el boton seleccionado
	 */
	@FXML
	void textFinder(ActionEvent event) {
		this.miConexion = Connect.getConnect();

		if (event.getSource() == bt_empleados) { // el evento pasado muestra la pestaña empleados

			empObList = FXCollections.observableArrayList();
			empObList.addAll(listaEmpleados);
			listaEmpleados = eD.obtenerDatosEmpleados();

			col_id.setCellValueFactory(new PropertyValueFactory<>("id_e"));
			col_nombre.setCellValueFactory(new PropertyValueFactory<>("nombre"));
			col_dni.setCellValueFactory(new PropertyValueFactory<>("dni"));
			col_estado.setCellValueFactory(new PropertyValueFactory<>("estado"));
			col_experiencia.setCellValueFactory(new PropertyValueFactory<>("experiencia"));
			try {

				FilteredList<Empleados> filtroEmpleados = new FilteredList<>(empObList, e -> true);
				filtroEmp.textProperty().addListener((observable, oldValue, newValue) -> {
					filtroEmpleados.setPredicate(Empleados -> {
						if (newValue == null || newValue.isEmpty()) {
							return true;
						}
						String filtroLetra = newValue.toLowerCase();
						if (Empleados.getNombre().toLowerCase().indexOf(filtroLetra) != -1) {

							return true;
						} else if (Empleados.getEstado().toLowerCase().indexOf(filtroLetra) != -1)
							return true;
						else
							return false;

					});
				});
				SortedList<Empleados> sortedEmp = new SortedList<>(filtroEmpleados);
				sortedEmp.comparatorProperty().bind(tablaEmpleados.comparatorProperty());
				tablaEmpleados.setItems(sortedEmp);

			} catch (Exception e) {

				e.printStackTrace();
			}

		} else if (event.getSource() == bt_puestos) {
			this.miConexion = Connect.getConnect();

			pueObList = FXCollections.observableArrayList();
			pueObList.addAll(listaPuestos);
			listaPuestos = pD.obtenerDatosPuestos();

			col_id_P.setCellValueFactory(new PropertyValueFactory<>("id_p"));
			col_nombre_P.setCellValueFactory(new PropertyValueFactory<>("nombre"));

			try {

				FilteredList<Puestos> filtroPuestos = new FilteredList<>(pueObList, p -> true);
				filtroPue.textProperty().addListener((observable, oldValue, newValue) -> {
					filtroPuestos.setPredicate(Puestos -> {
						if (newValue == null || newValue.isEmpty()) {
							return true;
						}
						String filtroLetra = newValue.toLowerCase();
						if (Puestos.getNombre().toLowerCase().indexOf(filtroLetra) != -1)

							return true;
						else
							return false;

					});
				});
				SortedList<Puestos> sortedPue = new SortedList<>(filtroPuestos);
				sortedPue.comparatorProperty().bind(tablaPuestos.comparatorProperty());
				tablaPuestos.setItems(sortedPue);

			} catch (Exception e) {

				e.printStackTrace();
			}
		}

	}

	/**
	 * obtiene los datos del Empleado seleccionado y lo prepara para asignarlo al
	 * texto tentro de "EmpleadoMod"
	 */
	public void getAtribEmpleado() {
		emp = tablaEmpleados.getSelectionModel().getSelectedItem();

		if (emp == null) {
			JOptionPane.showMessageDialog(null, "Empleado no Seleccionado");
		} else if (tabCtrlEmp != null) {
			tabCtrlEmp.txt_id.setText("" + emp.getId_e());
			tabCtrlEmp.txt_id.setVisible(false);
			tabCtrlEmp.txt_dni.setText(emp.getDni());
			tabCtrlEmp.txt_nombre.setText(emp.getNombre());
			tabCtrlEmp.txt_estado.setValue(emp.getEstado());
			tabCtrlEmp.txt_exp.setText(emp.getExperiencia());
		}
	}

	/**
	 * obtiene los datos del Puesto seleccionado y lo prepara para asignarlo al
	 * texto tentro de "PuestoMod"
	 */
	public void getAtribPuesto() {
		pue = tablaPuestos.getSelectionModel().getSelectedItem();

		if (pue == null) {
			JOptionPane.showMessageDialog(null, "Puesto no Seleccionado");
		} else if (tabCtrlPue != null) {
			tabCtrlPue.idPuesto.setText("" + pue.getId_p());
			tabCtrlPue.idPuesto.setVisible(false);
			tabCtrlPue.uptNombrePuesto.setText(pue.getNombre());
		}
	}

	/**
	 * permite acceder a la direccion de la pagina web
	 * 
	 * @throws URISyntaxException
	 * @throws IOException
	 */
	@FXML
	void openLink(ActionEvent event) throws URISyntaxException, IOException {
		Desktop.getDesktop().browse(new URI("https://github.com/NMachinne"));
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		UpdateTableEmpleados();
		UpdateTablePuestos();
	}

}
