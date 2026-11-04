package org.iesalandalus.programacion.tallermecanico.vista.ventanas.controladores;

import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.util.converter.LocalDateStringConverter;
import org.iesalandalus.programacion.tallermecanico.modelo.dominio.*;
import org.iesalandalus.programacion.tallermecanico.vista.eventos.Evento;
import org.iesalandalus.programacion.tallermecanico.vista.ventanas.VistaVentanas;
import org.iesalandalus.programacion.tallermecanico.vista.ventanas.utilidades.Controlador;

import java.time.LocalDate;
import java.util.List;

public class LeerTrabajo extends Controlador {

    @FXML private ComboBox<TipoTrabajo> cbTipo;
    @FXML private DatePicker dpFechaInicio;
    @FXML private ListView<Cliente> lvClientes;
    @FXML private ListView<Vehiculo> lvVehiculos;

    @FXML
    void initialize() {
        cbTipo.setItems(FXCollections.observableArrayList(TipoTrabajo.values()));
        cbTipo.setValue(TipoTrabajo.MECANICO);
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
        TipoTrabajo tipoTrabajo = cbTipo.getValue();
        if (tipoTrabajo.equals(TipoTrabajo.REVISION)) {
            VistaVentanas.getInstancia().getGestorEventos().notificar(Evento.INSERTAR_REVISION);
        } else if (tipoTrabajo.equals(TipoTrabajo.MECANICO)) {
            VistaVentanas.getInstancia().getGestorEventos().notificar(Evento.INSERTAR_MECANICO);
        }
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

    public void setCliente(Cliente cliente) {
        lvClientes.getSelectionModel().select(cliente);
        lvClientes.setDisable(true);
    }

    public void setVehiculos(List<Vehiculo> vehiculos) {
        lvVehiculos.setItems(FXCollections.observableArrayList(vehiculos));
        lvVehiculos.refresh();
    }

    public void setVehiculo(Vehiculo vehiculo) {
        lvVehiculos.getSelectionModel().select(vehiculo);
        lvVehiculos.setDisable(true);
    }

    public void limpiar() {
        lvClientes.getSelectionModel().clearSelection();
        lvClientes.setDisable(false);
        lvVehiculos.getSelectionModel().clearSelection();
        lvVehiculos.setDisable(false);
        dpFechaInicio.setValue(null);
        cbTipo.setValue(TipoTrabajo.MECANICO);
    }

    public Trabajo getTrabajo() {
        Trabajo trabajo = null;
        Cliente cliente = lvClientes.getSelectionModel().getSelectedItem();
        Vehiculo vehiculo = lvVehiculos.getSelectionModel().getSelectedItem();
        LocalDate fechaInicio = dpFechaInicio.getValue();
        TipoTrabajo tipoTrabajo = cbTipo.getValue();
        if (tipoTrabajo == TipoTrabajo.MECANICO) {
            trabajo = new Mecanico(cliente, vehiculo, fechaInicio);
        } else if (tipoTrabajo == TipoTrabajo.REVISION) {
            trabajo = new Revision(cliente, vehiculo, fechaInicio);
        }
        return trabajo;
    }

}
