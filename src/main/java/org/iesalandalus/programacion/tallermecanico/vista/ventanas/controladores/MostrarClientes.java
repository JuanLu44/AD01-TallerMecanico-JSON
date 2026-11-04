package org.iesalandalus.programacion.tallermecanico.vista.ventanas.controladores;


import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import org.iesalandalus.programacion.tallermecanico.modelo.dominio.Cliente;
import org.iesalandalus.programacion.tallermecanico.vista.ventanas.utilidades.Controlador;

import java.util.List;

public class MostrarClientes extends Controlador {

    @FXML private TableView<Cliente> tvClientes;
    @FXML private TableColumn<Cliente, String> tcDni;
    @FXML private TableColumn<Cliente, String> tcNombre;
    @FXML private TableColumn<Cliente, String> tcTelefono;

    private ObservableList<Cliente> clientes;
    
    @FXML
    void initialize() {
    	tcNombre.setCellValueFactory(new PropertyValueFactory<>("nombre"));
    	tcDni.setCellValueFactory(new PropertyValueFactory<>("dni"));
    	tcTelefono.setCellValueFactory(new PropertyValueFactory<>("telefono"));
    }
    
    @FXML
    void aceptar() {
    	getEscenario().close();
    }

    public void actualizar(List<Cliente> clientes) {
        this.clientes = FXCollections.observableArrayList(clientes);
        tvClientes.setItems(this.clientes);
        tvClientes.refresh();
    }

    public ObservableList<Cliente> getClientes() {
        return clientes;
    }
}
