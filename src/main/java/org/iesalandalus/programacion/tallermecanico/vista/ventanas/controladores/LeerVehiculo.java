package org.iesalandalus.programacion.tallermecanico.vista.ventanas.controladores;

import org.iesalandalus.programacion.tallermecanico.modelo.dominio.Vehiculo;
import org.iesalandalus.programacion.tallermecanico.vista.eventos.Evento;
import org.iesalandalus.programacion.tallermecanico.vista.ventanas.VistaVentanas;
import org.iesalandalus.programacion.tallermecanico.vista.ventanas.utilidades.Controlador;
import org.iesalandalus.programacion.tallermecanico.vista.ventanas.utilidades.Controles;

import javafx.fxml.FXML;
import javafx.scene.control.TextField;

public class LeerVehiculo extends Controlador {

    @FXML private TextField tfMarca;
    @FXML private TextField tfModelo;
    @FXML private TextField tfMatricula;
    

    @FXML
    private void initialize() {
    	tfMarca.textProperty().addListener((ob, ov, nv) -> Controles.validarCampoTexto(Vehiculo.ER_MARCA, tfMarca));
    	tfModelo.textProperty().addListener((ob, ov, nv) -> Controles.validarCampoTexto(".+", tfModelo));
    	tfMatricula.textProperty().addListener((ob, ov, nv) -> Controles.validarCampoTexto(Vehiculo.ER_MATRICULA, tfMatricula));
    }

    @FXML
    void aceptar() {
		VistaVentanas.getInstancia().getGestorEventos().notificar(Evento.INSERTAR_VEHICULO);
		getEscenario().close();
    }

    @FXML
    void cancelar() {
		getEscenario().close();
    }

	public Vehiculo getVehiculo() {
    	String marca = tfMarca.getText();
    	String modelo = tfModelo.getText();
    	String matricula = tfMatricula.getText();
    	return new Vehiculo(marca, modelo, matricula);
    }
    
    public void limpiar() {
    	Controles.limpiarCamposTexto(tfMarca, tfModelo, tfMatricula);
    }

}
