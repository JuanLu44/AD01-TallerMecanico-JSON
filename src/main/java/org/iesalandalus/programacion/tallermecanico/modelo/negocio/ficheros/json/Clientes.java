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
     *
     * @return Lista de clientes leídos.
     */
    private List<Cliente> leer() {
        File fichero = new File(FICHERO_CLIENTES);
        List<Cliente> clientes = new ArrayList<>();

        if (!fichero.exists()) {
            return clientes;
        }

        try {
            List<Cliente> clientesLeidos = mapper.readValue(
                    fichero,
                    new TypeReference<List<Cliente>>() {}
            );

            if (clientesLeidos != null) {
                clientes.addAll(clientesLeidos);
            }

        } catch (IOException | RuntimeException e) {
            System.out.printf(
                    "Error al leer los clientes. --> %s%n",
                    e.getMessage()
            );
        }

        return clientes;
    }

    /**
     * Guarda los clientes en el fichero JSON.
     *
     * @param clientes Lista de clientes que se va a guardar.
     */
    private void escribir(List<Cliente> clientes) {
        File fichero = new File(FICHERO_CLIENTES);
        File directorio = fichero.getParentFile();

        try {
            if (!directorio.exists() && !directorio.mkdirs()) {
                throw new IOException("No se pudo crear el directorio.");
            }

            mapper.writeValue(fichero, clientes);

        } catch (IOException e) {
            System.out.printf(
                    "Error al guardar los clientes. --> %s%n",
                    e.getMessage()
            );
        }
    }

    @Override
    public List<Cliente> get() {
        return leer();
    }

    @Override
    public void insertar(Cliente cliente) throws TallerMecanicoExcepcion {
        Objects.requireNonNull(
                cliente,
                "No se puede insertar un cliente nulo."
        );

        List<Cliente> clientes = leer();

        if (clientes.contains(cliente)) {
            throw new TallerMecanicoExcepcion(
                    "Ya existe un cliente con ese DNI."
            );
        }

        clientes.add(cliente);
        escribir(clientes);
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

        List<Cliente> clientes = leer();

        int indice = clientes.indexOf(cliente);

        if (indice == -1) {
            throw new TallerMecanicoExcepcion(
                    "No existe ningún cliente con ese DNI."
            );
        }

        Cliente clienteEncontrado = clientes.get(indice);

        if (nombre != null && !nombre.isBlank()) {
            clienteEncontrado.setNombre(nombre);
        }

        if (telefono != null && !telefono.isBlank()) {
            clienteEncontrado.setTelefono(telefono);
        }

        escribir(clientes);

        return clienteEncontrado;
    }

    @Override
    public Cliente buscar(Cliente cliente) {
        Objects.requireNonNull(
                cliente,
                "No se puede buscar un cliente nulo."
        );

        List<Cliente> clientes = leer();

        int indice = clientes.indexOf(cliente);

        return (indice == -1) ? null : clientes.get(indice);
    }

    @Override
    public void borrar(Cliente cliente) throws TallerMecanicoExcepcion {
        Objects.requireNonNull(
                cliente,
                "No se puede borrar un cliente nulo."
        );

        List<Cliente> clientes = leer();

        if (!clientes.contains(cliente)) {
            throw new TallerMecanicoExcepcion(
                    "No existe ningún cliente con ese DNI."
            );
        }

        clientes.remove(cliente);
        escribir(clientes);
    }
}
