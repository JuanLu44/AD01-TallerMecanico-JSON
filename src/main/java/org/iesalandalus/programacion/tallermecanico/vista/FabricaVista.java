package org.iesalandalus.programacion.tallermecanico.vista;

import org.iesalandalus.programacion.tallermecanico.vista.texto.VistaTexto;
import org.iesalandalus.programacion.tallermecanico.vista.ventanas.VistaVentanas;

public enum FabricaVista {

    TEXTO {
        @Override
        public Vista crear() {
            return new VistaTexto();
        }
    },
    VENTANAS {
        @Override
        public Vista crear() {
            return VistaVentanas.getInstancia();
        }
    };

    public abstract Vista crear();
}
