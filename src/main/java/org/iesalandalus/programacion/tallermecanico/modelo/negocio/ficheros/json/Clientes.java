package org.iesalandalus.programacion.tallermecanico.modelo.negocio.ficheros.json;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

import org.iesalandalus.programacion.tallermecanico.modelo.TallerMecanicoExcepcion;
import org.iesalandalus.programacion.tallermecanico.modelo.dominio.Cliente;
import org.iesalandalus.programacion.tallermecanico.modelo.negocio.IClientes;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Clientes implements IClientes {

    private static final String FICHERO_CLIENTES =
            String.format("%s%sficheros%sjson%sclientes.json",
                    "datos", File.separator, File.separator,
                    File.separator);

    private final ObjectMapper mapper;
    private final List<Cliente> coleccionClientes;

    private static Clientes instancia;

    private Clientes() {
        mapper = new ObjectMapper();
        mapper.registerModule(new JavaTimeModule());
        mapper.enable(SerializationFeature.INDENT_OUTPUT);

        // Permite a Jackson acceder a los campos privados de Cliente.
        mapper.setVisibility(
                PropertyAccessor.FIELD,
                JsonAutoDetect.Visibility.ANY
        );

        coleccionClientes = new ArrayList<>();
    }

    static Clientes getInstancia() {
        if (instancia == null) {
            instancia = new Clientes();
        }

        return instancia;
    }

    @Override
    public void comenzar() {
        // La lectura se realiza cuando se solicitan los datos.
    }

    @Override
    public void terminar() {
        // Los cambios se guardan inmediatamente.
    }

    /**
     * Lee los clientes almacenados en el fichero JSON.
     */
    private void leer() {
        File fichero = new File(FICHERO_CLIENTES);

        coleccionClientes.clear();

        if (!fichero.exists()) {
            return;
        }

        try {
            List<Cliente> clientesLeidos = mapper.readValue(
                    fichero,
                    new TypeReference<List<Cliente>>() {}
            );

            if (clientesLeidos != null) {
                coleccionClientes.addAll(clientesLeidos);
            }

        } catch (IOException | RuntimeException e) {
            System.out.printf(
                    "Error al leer los clientes. --> %s%n",
                    e.getMessage()
            );
        }
    }

    /**
     * Guarda los clientes en el fichero JSON.
     */
    private void escribir() {
        File fichero = new File(FICHERO_CLIENTES);
        File directorio = fichero.getParentFile();

        try {
            if (!directorio.exists() && !directorio.mkdirs()) {
                throw new IOException("No se pudo crear el directorio.");
            }

            mapper.writeValue(fichero, coleccionClientes);

        } catch (IOException e) {
            System.out.printf(
                    "Error al guardar los clientes. --> %s%n",
                    e.getMessage()
            );
        }
    }

    @Override
    public List<Cliente> get() {
        leer();

        return new ArrayList<>(coleccionClientes);
    }

    @Override
    public void insertar(Cliente cliente) throws TallerMecanicoExcepcion {
        Objects.requireNonNull(
                cliente,
                "No se puede insertar un cliente nulo."
        );

        leer();

        if (coleccionClientes.contains(cliente)) {
            throw new TallerMecanicoExcepcion(
                    "Ya existe un cliente con ese DNI."
            );
        }

        coleccionClientes.add(cliente);
        escribir();
    }

    @Override
    public Cliente modificar(
            Cliente cliente,
            String nombre,
            String telefono
    ) throws TallerMecanicoExcepcion {

        Objects.requireNonNull(
                cliente,
                "No se puede modificar un cliente nulo."
        );

        leer();

        int indice = coleccionClientes.indexOf(cliente);

        if (indice == -1) {
            throw new TallerMecanicoExcepcion(
                    "No existe ningún cliente con ese DNI."
            );
        }

        Cliente clienteEncontrado = coleccionClientes.get(indice);

        if (nombre != null && !nombre.isBlank()) {
            clienteEncontrado.setNombre(nombre);
        }

        if (telefono != null && !telefono.isBlank()) {
            clienteEncontrado.setTelefono(telefono);
        }

        escribir();

        return clienteEncontrado;
    }

    @Override
    public Cliente buscar(Cliente cliente) {
        Objects.requireNonNull(
                cliente,
                "No se puede buscar un cliente nulo."
        );

        leer();

        int indice = coleccionClientes.indexOf(cliente);

        return (indice == -1) ? null : coleccionClientes.get(indice);
    }

    @Override
    public void borrar(Cliente cliente) throws TallerMecanicoExcepcion {
        Objects.requireNonNull(
                cliente,
                "No se puede borrar un cliente nulo."
        );

        leer();

        if (!coleccionClientes.contains(cliente)) {
            throw new TallerMecanicoExcepcion(
                    "No existe ningún cliente con ese DNI."
            );
        }

        coleccionClientes.remove(cliente);
        escribir();
    }
}
