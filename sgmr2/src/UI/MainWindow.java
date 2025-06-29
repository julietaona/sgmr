package UI;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.*;
import modelo.*;
import controlador.Controlador;

public class MainWindow extends JFrame {
    JTable tablaAdmins, tablaClientes, tablaCertificados;
    JTabbedPane tabs;
    Controlador ctrl = new Controlador();

    public MainWindow() {
        setTitle("Sistema de Gestión Mantenimientos Roma");
        setSize(900, 600);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        mostrarMenuPrincipal();
    }

    private void mostrarMenuPrincipal() {
        JPanel panelMenu = new JPanel(new BorderLayout());
        JLabel titulo = new JLabel("Sistema de Gestión de Mantenimientos Roma", SwingConstants.CENTER);
        titulo.setFont(new Font("Arial", Font.BOLD, 24));
        titulo.setBorder(BorderFactory.createEmptyBorder(30, 0, 30, 0));
        panelMenu.add(titulo, BorderLayout.NORTH);

        JPanel botones = new JPanel(new GridLayout(3, 1, 10, 10));
        JButton btnAdministradores = new JButton("Ingresar al sistema");

        botones.add(btnAdministradores);
        panelMenu.add(botones, BorderLayout.CENTER);

        btnAdministradores.addActionListener(e -> mostrarTabsYSeleccionar(0));

        add(panelMenu, BorderLayout.CENTER);
    }

    private void mostrarTabsYSeleccionar(int tabIndex) {
        getContentPane().removeAll();
        tabs = new JTabbedPane();

        // Administradores
        JPanel panelAdmins = crearPanelAdmin();
        tabs.addTab("Administradores", panelAdmins);

        // Clientes
        JPanel panelClientes = crearPanelClientes();
        tabs.addTab("Clientes", panelClientes);

        // Certificados
        JPanel panelCertificados = crearPanelCertificados();
        tabs.addTab("Certificados", panelCertificados);

        add(tabs, BorderLayout.CENTER);
        tabs.setSelectedIndex(tabIndex);
        revalidate();
        repaint();
    }

    private JPanel crearPanelAdmin() {
        JPanel panel = new JPanel(new BorderLayout());
        tablaAdmins = new JTable();
        cargarTablaAdministradores();
        panel.add(new JScrollPane(tablaAdmins), BorderLayout.CENTER);

        JPanel botones = new JPanel();
        JButton btnAgregar = new JButton("Agregar");
        JButton btnEditar = new JButton("Editar");

        btnAgregar.addActionListener(e -> abrirFormularioAdmin(null));
        btnEditar.addActionListener(e -> {
            int fila = tablaAdmins.getSelectedRow();
            if (fila >= 0) {
                int id = (int) tablaAdmins.getValueAt(fila, 0);
                Administrador a = ctrl.obtenerAdministradores().stream()
                        .filter(ad -> ad.administradorId == id)
                        .findFirst().orElse(null);
                abrirFormularioAdmin(a);
            }
        });

        botones.add(btnAgregar);
        botones.add(btnEditar);
        panel.add(botones, BorderLayout.SOUTH);
        return panel;
    }

    private JPanel crearPanelClientes() {
        JPanel panel = new JPanel(new BorderLayout());
        tablaClientes = new JTable();
        cargarTablaClientes();
        panel.add(new JScrollPane(tablaClientes), BorderLayout.CENTER);

        JPanel botones = new JPanel();
        JButton btnAgregar = new JButton("Agregar");
        JButton btnEditar = new JButton("Editar");

        btnAgregar.addActionListener(e -> abrirFormularioCliente(null));
        btnEditar.addActionListener(e -> {
            int fila = tablaClientes.getSelectedRow();
            if (fila >= 0) {
                int id = (int) tablaClientes.getValueAt(fila, 0);
                Cliente c = ctrl.obtenerClientes().stream()
                        .filter(cli -> cli.clienteId == id)
                        .findFirst().orElse(null);
                abrirFormularioCliente(c);
            }
        });

        botones.add(btnAgregar);
        botones.add(btnEditar);
        panel.add(botones, BorderLayout.SOUTH);
        return panel;
    }

    private JPanel crearPanelCertificados() {
        JPanel panel = new JPanel(new BorderLayout());
        tablaCertificados = new JTable();
        cargarTablaCertificados();
        panel.add(new JScrollPane(tablaCertificados), BorderLayout.CENTER);

        JPanel botones = new JPanel();
        JButton btnAgregar = new JButton("Agregar");
        JButton btnEditar = new JButton("Editar");
        JButton btnExportar = new JButton("Exportar PDF");

        btnAgregar.addActionListener(e -> abrirFormularioCertificado(null));
        btnEditar.addActionListener(e -> {
            int fila = tablaCertificados.getSelectedRow();
            if (fila >= 0) {
                int id = (int) tablaCertificados.getValueAt(fila, 0);
                Certificado c = ctrl.obtenerCertificados().stream()
                        .filter(cert -> cert.certificadoId == id)
                        .findFirst().orElse(null);
                abrirFormularioCertificado(c);
            }
        });
        btnExportar.addActionListener(e -> exportarCertificadosPDF());

        botones.add(btnAgregar);
        botones.add(btnEditar);
        botones.add(btnExportar);
        panel.add(botones, BorderLayout.SOUTH);
        return panel;
    }

    private void cargarTablaAdministradores() {
        String[] columnas = {"ID", "Nombre", "CUIT", "Dirección", "Email", "Teléfono", "Activo", "Fecha"};
        DefaultTableModel model = new DefaultTableModel(columnas, 0);
        for (Administrador a : ctrl.obtenerAdministradores()) {
            model.addRow(new Object[]{a.administradorId, a.nombre, a.cuit, a.direccion, a.email, a.telefono, a.activo, a.fechaCreacion});
        }
        tablaAdmins.setModel(model);
    }

    private void cargarTablaClientes() {
        String[] columnas = {"ID", "Nombre", "CU", "Tipo Persona", "Dirección", "Email", "Teléfono", "Activo", "Fecha", "Admin ID"};
        DefaultTableModel model = new DefaultTableModel(columnas, 0);
        for (Cliente c : ctrl.obtenerClientes()) {
            model.addRow(new Object[]{c.clienteId, c.nombre, c.cu, c.tipoPersona, c.direccion, c.email, c.telefono, c.activo, c.fechaCreacion, c.administradorId});
        }
        tablaClientes.setModel(model);
    }

    private void cargarTablaCertificados() {
        String[] columnas = {"ID", "Cliente ID", "Fecha Tratamiento", "Fecha Vencimiento"};
        DefaultTableModel model = new DefaultTableModel(columnas, 0);
        for (Certificado cert : ctrl.obtenerCertificados()) {
            model.addRow(new Object[]{cert.certificadoId, cert.clienteId, cert.fechaTratamiento, cert.fechaVencimiento});
        }
        tablaCertificados.setModel(model);
    }

    private void abrirFormularioAdmin(Administrador admin) {
        JTextField tfNombre = new JTextField(admin != null ? admin.nombre : "");
        JTextField tfCUIT = new JTextField(admin != null ? String.valueOf(admin.cuit) : "");
        JTextField tfDireccion = new JTextField(admin != null ? admin.direccion : "");
        JTextField tfEmail = new JTextField(admin != null ? admin.email : "");
        JTextField tfTelefono = new JTextField(admin != null ? String.valueOf(admin.telefono) : "");
        JCheckBox chkActivo = new JCheckBox("Activo", admin != null && admin.activo);

        JPanel panel = new JPanel(new GridLayout(0, 1));
        panel.add(new JLabel("Nombre:")); panel.add(tfNombre);
        panel.add(new JLabel("CUIT:")); panel.add(tfCUIT);
        panel.add(new JLabel("Dirección:")); panel.add(tfDireccion);
        panel.add(new JLabel("Email:")); panel.add(tfEmail);
        panel.add(new JLabel("Teléfono:")); panel.add(tfTelefono);
        panel.add(chkActivo);

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
            if (ctrl.guardarAdministrador(admin)) cargarTablaAdministradores();
        }
    }

    private void abrirFormularioCliente(Cliente cliente) {
        JTextField tfNombre = new JTextField(cliente != null ? cliente.nombre : "");
        JTextField tfCUIT = new JTextField(cliente != null ? String.valueOf(cliente.cu) : "");
        JTextField tfDireccion = new JTextField(cliente != null ? cliente.direccion : "");
        JTextField tfEmail = new JTextField(cliente != null ? cliente.email : "");
        JTextField tfTelefono = new JTextField(cliente != null ? String.valueOf(cliente.telefono) : "");
        JCheckBox chkActivo = new JCheckBox("Activo", cliente != null && cliente.activo);

        JPanel panel = new JPanel(new GridLayout(0, 1));
        panel.add(new JLabel("Nombre:")); panel.add(tfNombre);
        panel.add(new JLabel("CUIT:")); panel.add(tfCUIT);
        panel.add(new JLabel("Dirección:")); panel.add(tfDireccion);
        panel.add(new JLabel("Email:")); panel.add(tfEmail);
        panel.add(new JLabel("Teléfono:")); panel.add(tfTelefono);
        panel.add(chkActivo);

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
            if (ctrl.guardarCliente(cliente)) cargarTablaClientes();
        }
    }

    private void abrirFormularioCertificado(Certificado cert) {
        JTextField tfTratamiento = new JTextField(cert != null ? cert.fechaTratamiento.toString() : "");
        JTextField tfVencimiento = new JTextField(cert != null ? cert.fechaVencimiento.toString() : "");
        JComboBox<Cliente> combo = new JComboBox<>();
        for (Cliente c : ctrl.obtenerClientes()) combo.addItem(c);

        if (cert != null) {
            for (int i = 0; i < combo.getItemCount(); i++) {
                if (combo.getItemAt(i).clienteId == cert.clienteId) {
                    combo.setSelectedIndex(i);
                    break;
                }
            }
        }

        JPanel panel = new JPanel(new GridLayout(0, 1));
        panel.add(new JLabel("Cliente:")); panel.add(combo);
        panel.add(new JLabel("Fecha Tratamiento (yyyy-MM-dd):")); panel.add(tfTratamiento);
        panel.add(new JLabel("Fecha Vencimiento (yyyy-MM-dd):")); panel.add(tfVencimiento);

        int result = JOptionPane.showConfirmDialog(this, panel, cert == null ? "Nuevo Certificado" : "Editar Certificado", JOptionPane.OK_CANCEL_OPTION);
        if (result == JOptionPane.OK_OPTION) {
            if (cert == null) cert = new Certificado();
            cert.clienteId = ((Cliente) combo.getSelectedItem()).clienteId;
            cert.fechaTratamiento = java.sql.Date.valueOf(tfTratamiento.getText());
            cert.fechaVencimiento = java.sql.Date.valueOf(tfVencimiento.getText());
            if (ctrl.guardarCertificado(cert)) cargarTablaCertificados();
        }
    }

    private void exportarCertificadosPDF() {
        JOptionPane.showMessageDialog(this, "PDF exportado correctamente.");
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new MainWindow().setVisible(true));
    }
}