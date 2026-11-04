package org.iesalandalus.programacion.tallermecanico.vista.ventanas.controladores;

import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import org.iesalandalus.programacion.tallermecanico.modelo.dominio.Cliente;
import org.iesalandalus.programacion.tallermecanico.vista.eventos.Evento;
import org.iesalandalus.programacion.tallermecanico.vista.ventanas.VistaVentanas;
import org.iesalandalus.programacion.tallermecanico.vista.ventanas.utilidades.Controlador;
import org.iesalandalus.programacion.tallermecanico.vista.ventanas.utilidades.Controles;

public class LeerCliente extends Controlador {

    @FXML private TextField tfNombre;
    @FXML private TextField tfDni;
    @FXML private TextField tfTelefono;

    @FXML
    private void initialize() {
    	tfNombre.textProperty().addListener((ob, ov, nv) -> Controles.validarCampoTexto(Cliente.ER_NOMBRE, tfNombre));
    	tfDni.textProperty().addListener((ob, ov, nv) -> Controles.validarCampoTexto(Cliente.ER_DNI, tfDni));
    	tfTelefono.textProperty().addListener((ob, ov, nv) -> Controles.validarCampoTexto(Cliente.ER_TELEFONO, tfTelefono));
    }

    @FXML
    void aceptar() {
        VistaVentanas.getInstancia().getGestorEventos().notificar(Evento.INSERTAR_CLIENTE);
        getEscenario().close();
    }

    @FXML
    void cancelar() {
        getEscenario().close();
    }

    public Cliente getCliente() {
    	String nombre = tfNombre.getText();
    	String dni = tfDni.getText();
    	String telefono = tfTelefono.getText();
    	return new Cliente(nombre, dni, telefono);
    }
    
    public void limpiar() {
    	Controles.limpiarCamposTexto(tfNombre, tfDni, tfTelefono);
    }

}
