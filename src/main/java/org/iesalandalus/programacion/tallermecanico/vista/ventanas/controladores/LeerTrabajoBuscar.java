package org.iesalandalus.programacion.tallermecanico.vista.ventanas.controladores;

import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.DateCell;
import javafx.scene.control.DatePicker;
import javafx.scene.control.ListView;
import javafx.util.converter.LocalDateStringConverter;
import org.iesalandalus.programacion.tallermecanico.modelo.dominio.*;
import org.iesalandalus.programacion.tallermecanico.vista.eventos.Evento;
import org.iesalandalus.programacion.tallermecanico.vista.ventanas.VistaVentanas;
import org.iesalandalus.programacion.tallermecanico.vista.ventanas.utilidades.Controlador;

import java.time.LocalDate;
import java.util.List;

public class LeerTrabajoBuscar extends Controlador {

    @FXML private DatePicker dpFechaInicio;
    @FXML private ListView<Cliente> lvClientes;
    @FXML private ListView<Vehiculo> lvVehiculos;

    @FXML
    void initialize() {
        dpFechaInicio.setConverter(new LocalDateStringConverter(Trabajo.FORMATO_FECHA, null));
        dpFechaInicio.setDayCellFactory(celda -> new DateCell() {
            @Override
            public void updateItem(LocalDate fecha, boolean vacio) {
                super.updateItem(fecha, vacio);
                if (fecha.isAfter(LocalDate.now())) {
                    this.setDisable(true);
                }
            }
        });
    }

    @FXML
    void aceptar() {
        VistaVentanas.getInstancia().getGestorEventos().notificar(Evento.BUSCAR_TRABAJO);
        getEscenario().close();
    }

    @FXML
    void cancelar() {
        getEscenario().close();
    }

    public void setClientes(List<Cliente> clientes) {
        lvClientes.setItems(FXCollections.observableArrayList(clientes));
        lvClientes.refresh();
    }

    public void setVehiculos(List<Vehiculo> vehiculos) {
        lvVehiculos.setItems(FXCollections.observableArrayList(vehiculos));
        lvClientes.refresh();
    }

    public void limpiar() {
        lvClientes.getSelectionModel().clearSelection();
        lvVehiculos.getSelectionModel().clearSelection();
        dpFechaInicio.setValue(null);
    }

    public Trabajo getTrabajo() {
        Trabajo trabajo = null;
        Cliente cliente = lvClientes.getSelectionModel().getSelectedItem();
        Vehiculo vehiculo = lvVehiculos.getSelectionModel().getSelectedItem();
        LocalDate fechaInicio = dpFechaInicio.getValue();
        trabajo = new Mecanico(cliente, vehiculo, fechaInicio);
        return trabajo;
    }
}
