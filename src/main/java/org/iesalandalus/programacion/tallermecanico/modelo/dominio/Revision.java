package org.iesalandalus.programacion.tallermecanico.modelo.dominio;

import java.time.LocalDate;

public class Revision extends Trabajo {

    private static final float FACTOR_HORA = 35F;

    public Revision() {}
    public Revision(Cliente cliente, Vehiculo vehiculo, LocalDate fechaInicio) {
        super(cliente, vehiculo, fechaInicio);
    }

    public Revision(Revision revision) { super(revision); }

    @Override
    public float getPrecioEspecifico() {
        return estaCerrado() ? FACTOR_HORA * getHoras() : 0;
    }

    @Override
    public String toString() {
        if (!estaCerrado()) {
            return String.format("Revisión -> %s - %s (%s - ): %d horas",
                    getCliente(), getVehiculo(), getFechaInicio().format(FORMATO_FECHA), getHoras());
        } else {
            return String.format("Revisión -> %s - %s (%s - %s): %d horas, %.2f € total",
                    getCliente(), getVehiculo(), getFechaInicio().format(FORMATO_FECHA), getFechaFin().format(FORMATO_FECHA),
                    getHoras(), getPrecio());
        }
    }
}
