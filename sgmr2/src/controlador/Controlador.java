package controlador;

import modelo.*;
import java.util.*;

public class Controlador {

    public List<Administrador> obtenerAdministradores() {
        return Administrador.obtenerTodos();
    }

    public boolean guardarAdministrador(Administrador admin) {
        if (admin.administradorId == 0) return admin.crear();
        else return admin.actualizar();
    }

    public List<Cliente> obtenerClientes() {
        return Cliente.obtenerTodos();
    }

    public boolean guardarCliente(Cliente cliente) {
        if (cliente.clienteId == 0) return cliente.crear();
        else return cliente.actualizar();
    }

    public List<Certificado> obtenerCertificados() {
        return Certificado.obtenerTodos();
    }

    public boolean guardarCertificado(Certificado cert) {
        if (cert.certificadoId == 0) return cert.crear();
        else return cert.actualizar();
    }
} 

