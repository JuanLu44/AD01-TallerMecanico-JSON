package org.iesalandalus.programacion.tallermecanico.vista.ventanas.controladores;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import org.iesalandalus.programacion.tallermecanico.modelo.dominio.Mecanico;
import org.iesalandalus.programacion.tallermecanico.modelo.dominio.Trabajo;
import org.iesalandalus.programacion.tallermecanico.vista.eventos.Evento;
import org.iesalandalus.programacion.tallermecanico.vista.ventanas.VistaVentanas;
import org.iesalandalus.programacion.tallermecanico.vista.ventanas.utilidades.Controlador;
import org.iesalandalus.programacion.tallermecanico.vista.ventanas.utilidades.Controladores;
import org.iesalandalus.programacion.tallermecanico.vista.ventanas.utilidades.Dialogos;

public class MostrarTrabajo extends Controlador {

	@FXML private Button btAnadirHoras;
	@FXML private Button btAnadirPrecioMaterial;
	@FXML private Button btCerrar;
	@FXML private Label lbTrabajo;


	private Trabajo trabajo;
	private int horas;
	private float precioMaterial;

	@FXML
	void aceptar() {
		getEscenario().close();
	}

	@FXML
	void anadirHoras() {
		String cadenaHoras = Dialogos.mostrarDialogoTexto("Añadir horas", "Introduce las horas:", getEscenario(), "\\d+");
		if (cadenaHoras != null) {
			horas = 0;
			try {
				horas = Integer.parseInt(cadenaHoras);
			} catch (NumberFormatException nfe) {/**/}
			VistaVentanas.getInstancia().getGestorEventos().notificar(Evento.ANADIR_HORAS_TRABAJO);
		}
	}

	@FXML
	void anadirPrecioMaterial() {
		String cadenaPrecioMaterial = Dialogos.mostrarDialogoTexto("Añadir precio material", "Introduce el precio del material:", getEscenario(), "\\d*(\\.\\d+)?");
		if (cadenaPrecioMaterial != null) {
			precioMaterial = 0;
			try {
				precioMaterial = Float.parseFloat(cadenaPrecioMaterial);
			} catch (NumberFormatException nfe) {/**/}
			VistaVentanas.getInstancia().getGestorEventos().notificar(Evento.ANADIR_PRECIO_MATERIAL_TRABAJO);
		}
	}

	@FXML
	void cerrar() {
		LeerFechaFin leerFechaFin = (LeerFechaFin) Controladores.get("/vistas/LeerFechaFin.fxml", "Leer fecha fin", getEscenario());
		leerFechaFin.limpiar();
		leerFechaFin.getEscenario().showAndWait();
	}

	@FXML
	void borrar() {
		if (Dialogos.mostrarDialogoConfirmacion(Evento.BORRAR_TRABAJO.toString(), "¿Estás seguro que deseas borrar el trabajo?", getEscenario())) {
			VistaVentanas.getInstancia().getGestorEventos().notificar(Evento.BORRAR_TRABAJO);
			getEscenario().close();
		}
	}

	public int getHoras() {
		return horas;
	}

	public float getPrecioMaterial() {
		return precioMaterial;
	}

	private void revisarEstadoBotones() {
		btCerrar.setDisable(trabajo == null || trabajo.getFechaFin() != null);
		btAnadirHoras.setDisable(trabajo == null || trabajo.getFechaFin() != null);
		btAnadirPrecioMaterial.setDisable(!(trabajo instanceof Mecanico) || trabajo.getFechaFin() != null);
	}

	public void actualizar(Trabajo trabajo) {
		this.trabajo = trabajo;
		lbTrabajo.setText(trabajo.toString());
		revisarEstadoBotones();
	}

}
