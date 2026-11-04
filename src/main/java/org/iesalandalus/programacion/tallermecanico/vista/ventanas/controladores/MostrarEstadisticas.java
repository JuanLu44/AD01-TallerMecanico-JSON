package org.iesalandalus.programacion.tallermecanico.vista.ventanas.controladores;

import java.time.LocalDate;
import java.util.Map;
import java.util.Map.Entry;

import org.iesalandalus.programacion.tallermecanico.modelo.dominio.TipoTrabajo;
import org.iesalandalus.programacion.tallermecanico.modelo.dominio.Trabajo;
import org.iesalandalus.programacion.tallermecanico.vista.eventos.Evento;
import org.iesalandalus.programacion.tallermecanico.vista.ventanas.VistaVentanas;
import org.iesalandalus.programacion.tallermecanico.vista.ventanas.utilidades.Controlador;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.chart.PieChart;
import javafx.scene.chart.PieChart.Data;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Tooltip;
import javafx.util.converter.LocalDateStringConverter;

public class MostrarEstadisticas extends Controlador {
	
    @FXML private DatePicker dpMes;
    @FXML private PieChart pcGrafica;
    
    ObservableList<Data> datos = FXCollections.observableArrayList();

    @FXML
    private void initialize() {
		dpMes.setConverter(new LocalDateStringConverter(Trabajo.FORMATO_FECHA, null));
    	pcGrafica.setData(datos);
    }

    @FXML
    void aceptar() {
    	getEscenario().close();
    }
    
    @FXML
    private void mesCambiado() {
		if (getMes() != null) {
			VistaVentanas.getInstancia().getGestorEventos().notificar(Evento.MOSTRAR_ESTADISTICAS_MENSUALES);
		}
    }

	public void setEstadisticas( Map<TipoTrabajo, Integer> estadisticas) {
		datos.clear();
		for (Entry<TipoTrabajo, Integer> dato : estadisticas.entrySet()) {
			datos.add(new Data(dato.getKey().toString(), dato.getValue()));
		}
		pcGrafica.getData().forEach(dato -> {
			String etiqueta = String.format("%.0f veces", dato.getPieValue());
			Tooltip toolTip = new Tooltip(etiqueta);
			Tooltip.install(dato.getNode(), toolTip);
		});

	}

	public LocalDate getMes() {
		return dpMes.getValue();
	}
	
    public void limpiar() {
    	dpMes.setValue(null);
		datos.clear();
    }

}
