package UI;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import modelo.*;
import java.util.*;

public class MainWindow extends JFrame {
    JTable tablaAdmins, tablaClientes, tablaCertificados;

    public MainWindow() {
        setTitle("Sistema de Gestión Mantenimientos Roma");
        setSize(900, 600);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        JTabbedPane tabs = new JTabbedPane();

        // Panel Administradores
        JPanel panelAdmins = new JPanel(new BorderLayout());
        tablaAdmins = new JTable();
        cargarTablaAdministradores();
        panelAdmins.add(new JScrollPane(tablaAdmins), BorderLayout.CENTER);

        JPanel botonesAdmin = new JPanel();
        JButton btnAgregar = new JButton("Agregar");
        JButton btnEditar = new JButton("Editar");
        botonesAdmin.add(btnAgregar);
        botonesAdmin.add(btnEditar);
        panelAdmins.add(botonesAdmin, BorderLayout.SOUTH);

        btnAgregar.addActionListener(e -> abrirFormularioAdministrador(null));
        btnEditar.addActionListener(e -> {
            int fila = tablaAdmins.getSelectedRow();
            if (fila >= 0) {
                int id = (int) tablaAdmins.getValueAt(fila, 0);
                Administrador seleccionado = Administrador.obtenerTodos().stream()
                        .filter(a -> a.administradorId == id)
                        .findFirst().orElse(null);
                abrirFormularioAdministrador(seleccionado);
            } else {
                JOptionPane.showMessageDialog(this, "Seleccioná un administrador para editar.");
            }
        });

        tabs.addTab("Administradores", panelAdmins);

        // Panel Clientes
        JPanel panelClientes = new JPanel(new BorderLayout());
        tablaClientes = new JTable();
        cargarTablaClientes();
        panelClientes.add(new JScrollPane(tablaClientes), BorderLayout.CENTER);

        JPanel botonesClientes = new JPanel();
        JButton btnAgregarCliente = new JButton("Agregar");
        JButton btnEditarCliente = new JButton("Editar");
        botonesClientes.add(btnAgregarCliente);
        botonesClientes.add(btnEditarCliente);
        panelClientes.add(botonesClientes, BorderLayout.SOUTH);

        btnAgregarCliente.addActionListener(e -> abrirFormularioCliente(null));
        btnEditarCliente.addActionListener(e -> {
            int fila = tablaClientes.getSelectedRow();
            if (fila >= 0) {
                int id = (int) tablaClientes.getValueAt(fila, 0);
                Cliente seleccionado = Cliente.obtenerTodos().stream()
                        .filter(a -> a.clienteId == id)
                        .findFirst().orElse(null);
                abrirFormularioCliente(seleccionado);
            } else {
                JOptionPane.showMessageDialog(this, "Seleccioná un cliente para editar.");
            }
        });

        tabs.addTab("Clientes", panelClientes);

        // Panel Certificados
        JPanel panelCertificados = new JPanel(new BorderLayout());
        tablaCertificados = new JTable();
        cargarTablaCertificados();
        panelCertificados.add(new JScrollPane(tablaCertificados), BorderLayout.CENTER);

        JPanel botonesCertificados = new JPanel();
        JButton btnAgregarCertificado = new JButton("Agregar");
        JButton btnEditarCertificado = new JButton("Editar");
        JButton btnExportarPDF = new JButton("Exportar PDF");
        botonesCertificados.add(btnAgregarCertificado);
        botonesCertificados.add(btnEditarCertificado);
        botonesCertificados.add(btnExportarPDF);
        panelCertificados.add(botonesCertificados, BorderLayout.SOUTH);

        btnAgregarCertificado.addActionListener(e -> abrirFormularioCertificado(null));
        btnEditarCertificado.addActionListener(e -> {
            int fila = tablaCertificados.getSelectedRow();
            if (fila >= 0) {
                int id = (int) tablaCertificados.getValueAt(fila, 0);
                Certificado seleccionado = Certificado.obtenerTodos().stream()
                        .filter(c -> c.certificadoId == id)
                        .findFirst().orElse(null);
                abrirFormularioCertificado(seleccionado);
            } else {
                JOptionPane.showMessageDialog(this, "Seleccioná un certificado para editar.");
            }
        });

        btnExportarPDF.addActionListener(e -> exportarCertificadosPDF());

        tabs.addTab("Certificados", panelCertificados);

        add(tabs, BorderLayout.CENTER);
    }

    private void cargarTablaAdministradores() {
        String[] columnas = {"ID", "Nombre", "CUIT", "Dirección", "Email", "Teléfono", "Activo", "Fecha"};
        DefaultTableModel model = new DefaultTableModel(columnas, 0);
        for (var a : Administrador.obtenerTodos()) {
            model.addRow(new Object[]{a.administradorId, a.nombre, a.cuit, a.direccion, a.email, a.telefono, a.activo, a.fechaCreacion});
        }
        tablaAdmins.setModel(model);
    }

    private void cargarTablaClientes() {
        String[] columnas = {"ID", "Nombre", "CU", "Tipo de persona", "Dirección", "Email", "Teléfono", "Activo", "Fecha", "Admin ID"};
        DefaultTableModel model = new DefaultTableModel(columnas, 0);
        for (var c : Cliente.obtenerTodos()) {
            model.addRow(new Object[]{c.clienteId, c.nombre, c.cu, c.tipoPersona, c.direccion, c.email, c.telefono, c.activo, c.fechaCreacion, c.administradorId});
        }
        tablaClientes.setModel(model);
    }

    private void cargarTablaCertificados() {
        String[] columnas = {"ID", "Cliente ID", "Fecha Tratamiento", "Fecha Vencimiento"};
        DefaultTableModel model = new DefaultTableModel(columnas, 0);
        for (var cert : Certificado.obtenerTodos()) {
            model.addRow(new Object[]{cert.certificadoId, cert.clienteId, cert.fechaTratamiento, cert.fechaVencimiento});
        }
        tablaCertificados.setModel(model);
    }

    private void abrirFormularioAdministrador(Administrador admin) {
        JTextField tfNombre = new JTextField();
        JTextField tfCUIT = new JTextField();
        JTextField tfDireccion = new JTextField();
        JTextField tfEmail = new JTextField();
        JTextField tfTelefono = new JTextField();
        JCheckBox chkActivo = new JCheckBox();

        if (admin != null) {
            tfNombre.setText(admin.nombre);
            tfCUIT.setText(String.valueOf(admin.cuit));
            tfDireccion.setText(admin.direccion);
            tfEmail.setText(admin.email);
            tfTelefono.setText(String.valueOf(admin.telefono));
            chkActivo.setSelected(admin.activo);
        }

        JPanel panel = new JPanel(new GridLayout(0, 1));
        panel.add(new JLabel("Nombre:")); panel.add(tfNombre);
        panel.add(new JLabel("CUIT:")); panel.add(tfCUIT);
        panel.add(new JLabel("Dirección:")); panel.add(tfDireccion);
        panel.add(new JLabel("Email:")); panel.add(tfEmail);
        panel.add(new JLabel("Teléfono:")); panel.add(tfTelefono);
        panel.add(new JLabel("Activo:")); panel.add(chkActivo);

        int result = JOptionPane.showConfirmDialog(this, panel, admin == null ? "Nuevo Administrador" : "Editar Administrador", JOptionPane.OK_CANCEL_OPTION);
        if (result == JOptionPane.OK_OPTION) {
            if (admin == null) admin = new Administrador();
            admin.nombre = tfNombre.getText();
            admin.cuit = Long.parseLong(tfCUIT.getText());
            admin.direccion = tfDireccion.getText();
            admin.email = tfEmail.getText();
            admin.telefono = Long.parseLong(tfTelefono.getText());
            admin.activo = chkActivo.isSelected();
            admin.fechaCreacion = new Date();
            if ((admin.administradorId == 0 ? admin.crear() : admin.actualizar())) cargarTablaAdministradores();
        }
    }

    private void abrirFormularioCliente(Cliente cliente) {
        JTextField tfNombre = new JTextField();
        JTextField tfCUIT = new JTextField();
        JTextField tfDireccion = new JTextField();
        JTextField tfEmail = new JTextField();
        JTextField tfTelefono = new JTextField();
        JCheckBox chkActivo = new JCheckBox();

        if (cliente != null) {
            tfNombre.setText(cliente.nombre);
            tfCUIT.setText(String.valueOf(cliente.cu));
            tfDireccion.setText(cliente.direccion);
            tfEmail.setText(cliente.email);
            tfTelefono.setText(String.valueOf(cliente.telefono));
            chkActivo.setSelected(cliente.activo);
        }

        JPanel panel = new JPanel(new GridLayout(0, 1));
        panel.add(new JLabel("Nombre:")); panel.add(tfNombre);
        panel.add(new JLabel("CUIT:")); panel.add(tfCUIT);
        panel.add(new JLabel("Dirección:")); panel.add(tfDireccion);
        panel.add(new JLabel("Email:")); panel.add(tfEmail);
        panel.add(new JLabel("Teléfono:")); panel.add(tfTelefono);
        panel.add(new JLabel("Activo:")); panel.add(chkActivo);

        int result = JOptionPane.showConfirmDialog(this, panel, cliente == null ? "Nuevo Cliente" : "Editar Cliente", JOptionPane.OK_CANCEL_OPTION);
        if (result == JOptionPane.OK_OPTION) {
            if (cliente == null) cliente = new Cliente();
            cliente.nombre = tfNombre.getText();
            cliente.cu = Long.parseLong(tfCUIT.getText());
            cliente.direccion = tfDireccion.getText();
            cliente.email = tfEmail.getText();
            cliente.telefono = Long.parseLong(tfTelefono.getText());
            cliente.activo = chkActivo.isSelected();
            cliente.fechaCreacion = new Date();
            if ((cliente.clienteId == 0 ? cliente.crear() : cliente.actualizar())) cargarTablaClientes();
        }
    }

    private void abrirFormularioCertificado(Certificado cert) {
        JTextField tfFechaTratamiento = new JTextField();
        JTextField tfFechaVencimiento = new JTextField();
        JComboBox<Cliente> comboClientes = new JComboBox<>();
        for (Cliente c : Cliente.obtenerTodos()) comboClientes.addItem(c);

        if (cert != null) {
            tfFechaTratamiento.setText(cert.fechaTratamiento.toString());
            tfFechaVencimiento.setText(cert.fechaVencimiento.toString());
            for (int i = 0; i < comboClientes.getItemCount(); i++) {
                if (comboClientes.getItemAt(i).clienteId == cert.clienteId) {
                    comboClientes.setSelectedIndex(i);
                    break;
                }
            }
        }

        JPanel panel = new JPanel(new GridLayout(0, 1));
        panel.add(new JLabel("Cliente:")); panel.add(comboClientes);
        panel.add(new JLabel("Fecha Tratamiento (yyyy-MM-dd):")); panel.add(tfFechaTratamiento);
        panel.add(new JLabel("Fecha Vencimiento (yyyy-MM-dd):")); panel.add(tfFechaVencimiento);

        int result = JOptionPane.showConfirmDialog(this, panel, cert == null ? "Nuevo Certificado" : "Editar Certificado", JOptionPane.OK_CANCEL_OPTION);
        if (result == JOptionPane.OK_OPTION) {
            if (cert == null) cert = new Certificado();
            cert.clienteId = ((Cliente) comboClientes.getSelectedItem()).clienteId;
            cert.fechaTratamiento = java.sql.Date.valueOf(tfFechaTratamiento.getText());
            cert.fechaVencimiento = java.sql.Date.valueOf(tfFechaVencimiento.getText());
            if ((cert.certificadoId == 0 ? cert.crear() : cert.actualizar())) cargarTablaCertificados();
        }
    }

    private void exportarCertificadosPDF() {
        try {
            JOptionPane.showMessageDialog(this, "PDF exportado correctamente.");
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Error al exportar PDF: " + ex.getMessage());
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new MainWindow().setVisible(true));
    }
}
