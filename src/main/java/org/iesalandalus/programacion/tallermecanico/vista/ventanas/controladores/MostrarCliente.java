package org.iesalandalus.programacion.tallermecanico.vista.ventanas.controladores;

import org.iesalandalus.programacion.tallermecanico.modelo.dominio.Cliente;
import org.iesalandalus.programacion.tallermecanico.vista.eventos.Evento;
import org.iesalandalus.programacion.tallermecanico.vista.ventanas.VistaVentanas;
import org.iesalandalus.programacion.tallermecanico.vista.ventanas.utilidades.Controlador;
import org.iesalandalus.programacion.tallermecanico.vista.ventanas.utilidades.Dialogos;

import javafx.fxml.FXML;
import javafx.scene.control.TextField;

public class MostrarCliente extends Controlador {

	@FXML private TextField tfNombre;
	@FXML private TextField tfDni;
	@FXML private TextField tfTelefono;

	public void actualizar(Cliente cliente) {
		tfNombre.setText(cliente.getNombre());
		tfDni.setText(cliente.getDni());
		tfTelefono.setText(cliente.getTelefono());
	}
	
	public Cliente getCliente() {
		String dni = tfDni.getText();
		return Cliente.get(dni);
	}

	public String getNombre() {
		return tfNombre.getText();
	}

	public String getTelefono() {
		return tfTelefono.getText();
	}

	@FXML
	void aceptar() {
		getEscenario().close();
	}

	@FXML
	void borrar() {
		if (Dialogos.mostrarDialogoConfirmacion(Evento.BORRAR_CLIENTE.toString(), "¿Estás seguro que deseas borrar el cliente?", getEscenario())) {
			VistaVentanas.getInstancia().getGestorEventos().notificar(Evento.BORRAR_CLIENTE);
			getEscenario().close();
		}
	}
	
	@FXML
	void modificar() {
		if (Dialogos.mostrarDialogoConfirmacion(Evento.MODIFICAR_CLIENTE.toString(), "¿Estás seguro que deseas modificar el cliente?", getEscenario())) {
			VistaVentanas.getInstancia().getGestorEventos().notificar(Evento.MODIFICAR_CLIENTE);
		}
	}
	
	@FXML
	void listarTrabajosCliente() {
		VistaVentanas.getInstancia().getGestorEventos().notificar(Evento.LISTAR_TRABAJOS_CLIENTE);
	}
}
