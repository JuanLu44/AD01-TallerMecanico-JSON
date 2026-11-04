package org.iesalandalus.programacion.tallermecanico.vista.ventanas.controladores;

import java.time.LocalDate;

import org.iesalandalus.programacion.tallermecanico.modelo.dominio.Trabajo;
import org.iesalandalus.programacion.tallermecanico.vista.eventos.Evento;
import org.iesalandalus.programacion.tallermecanico.vista.ventanas.VistaVentanas;
import org.iesalandalus.programacion.tallermecanico.vista.ventanas.utilidades.Controlador;

import javafx.fxml.FXML;
import javafx.scene.control.DateCell;
import javafx.scene.control.DatePicker;
import javafx.util.converter.LocalDateStringConverter;

public class LeerFechaFin extends Controlador {

	@FXML private DatePicker dpFechaFin;

	@FXML
	void initialize() {
		dpFechaFin.setConverter(new LocalDateStringConverter(Trabajo.FORMATO_FECHA, null));
		dpFechaFin.setDayCellFactory(celda -> new DateCell() {
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
	void cancelar() {
		getEscenario().close();
	}
	
	@FXML 
	void aceptar() {
		VistaVentanas.getInstancia().getGestorEventos().notificar(Evento.CERRAR_TRABAJO);
		getEscenario().close();
	}

	public LocalDate getFechaFin() {
		return dpFechaFin.getValue();
	}

	public void limpiar() {
		dpFechaFin.setValue(null);
	}

}
