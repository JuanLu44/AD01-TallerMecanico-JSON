package org.iesalandalus.programacion.tallermecanico.vista.ventanas.controladores;

import javafx.fxml.FXML;
import org.iesalandalus.programacion.tallermecanico.vista.eventos.Evento;
import org.iesalandalus.programacion.tallermecanico.vista.ventanas.VistaVentanas;
import org.iesalandalus.programacion.tallermecanico.vista.ventanas.utilidades.Controlador;
import org.iesalandalus.programacion.tallermecanico.vista.ventanas.utilidades.Controladores;
import org.iesalandalus.programacion.tallermecanico.vista.ventanas.utilidades.Dialogos;

public class VentanaPrincipal extends Controlador {

    public void inicializar() {
        Dialogos.setHojaEstilos("/estilos/ventanas.css");
        addIcono("/imagenes/iconoTaller.png");
        VistaVentanas.getInstancia().getGestorEventos().notificar(Evento.LISTAR_CLIENTES);
        VistaVentanas.getInstancia().getGestorEventos().notificar(Evento.LISTAR_VEHICULOS);
        VistaVentanas.getInstancia().getGestorEventos().notificar(Evento.LISTAR_TRABAJOS);
    }

    @FXML
    void insertarCliente() {
        LeerCliente leerCliente = (LeerCliente) Controladores.get("/vistas/LeerCliente.fxml", "Leer Cliente", getEscenario());
        leerCliente.limpiar();
        leerCliente.getEscenario().showAndWait();
    }

    @FXML
    void buscarCliente() {
        VistaVentanas.getInstancia().getGestorEventos().notificar(Evento.BUSCAR_CLIENTE);
    }

    @FXML
    void listarClientes() {
        VistaVentanas.getInstancia().getGestorEventos().notificar(Evento.LISTAR_CLIENTES);
    }

    @FXML
    void insertarVehiculo() {
        LeerVehiculo leerVehiculo = (LeerVehiculo) Controladores.get("/vistas/LeerVehiculo.fxml", "Leer Vehículo", getEscenario());
        leerVehiculo.limpiar();
        leerVehiculo.getEscenario().showAndWait();
    }

    @FXML
    void buscarVehiculo() {
        VistaVentanas.getInstancia().getGestorEventos().notificar(Evento.BUSCAR_VEHICULO);
    }

    @FXML
    void listarVehiculos() {
        VistaVentanas.getInstancia().getGestorEventos().notificar(Evento.LISTAR_VEHICULOS);
    }

    @FXML
    void insertarTrabajo() {
        LeerTrabajo leerTrabajo = (LeerTrabajo) Controladores.get("/vistas/LeerTrabajo.fxml", "Leer Trabajo", getEscenario());
        leerTrabajo.limpiar();
        leerTrabajo.setClientes(VistaVentanas.getInstancia().getClientes());
        leerTrabajo.setVehiculos(VistaVentanas.getInstancia().getVehiculos());
        leerTrabajo.getEscenario().showAndWait();
    }

    @FXML
    void buscarTrabajo() {
        LeerTrabajoBuscar leerTrabajoBuscar = (LeerTrabajoBuscar) Controladores.get("/vistas/LeerTrabajoBuscar.fxml", "Buscar Trabajo", getEscenario());
        leerTrabajoBuscar.limpiar();
        leerTrabajoBuscar.setClientes(VistaVentanas.getInstancia().getClientes());
        leerTrabajoBuscar.setVehiculos(VistaVentanas.getInstancia().getVehiculos());
        leerTrabajoBuscar.getEscenario().showAndWait();
    }

    @FXML
    void listarTrabajos() {
        VistaVentanas.getInstancia().getGestorEventos().notificar(Evento.LISTAR_TRABAJOS);
    }

    @FXML
    void mostrarEstadisticas() {
        MostrarEstadisticas mostrarEstadisticas = (MostrarEstadisticas) Controladores.get("/vistas/MostrarEstadisticas.fxml", "Mostrar estadísticas",  getEscenario());
        mostrarEstadisticas.limpiar();
        mostrarEstadisticas.getEscenario().show();
    }

    @FXML
    void acercaDe() {
        Controlador acercaDe = Controladores.get("/vistas/AcercaDe.fxml", "Acerca de...", getEscenario());
        acercaDe.getEscenario().showAndWait();
    }

    @FXML
    void salir() {
        if (Dialogos.mostrarDialogoConfirmacion("Salir", "¿Estás seguro de que quieres salir de la aplicación?", getEscenario())) {
            getEscenario().close();
            VistaVentanas.getInstancia().getGestorEventos().notificar(Evento.SALIR);
        }
    }

}
