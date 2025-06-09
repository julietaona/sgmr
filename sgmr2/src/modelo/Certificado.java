package modelo;
import java.sql.*;
import java.util.Date;
import java.util.List;
import java.util.ArrayList;

public class Certificado {
    public int certificadoId;
    public int clienteId;
    public Date fechaTratamiento;
    public Date fechaVencimiento;

    public static List<Certificado> obtenerTodos() {
        List<Certificado> lista = new ArrayList<>();
        String query = "SELECT * FROM certificados";
        try (Connection conn = ConexionDB.conectar();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {

            while (rs.next()) {
                Certificado cert = new Certificado();
                cert.certificadoId = rs.getInt("certificado_id");
                cert.clienteId = rs.getInt("cliente_id");
                cert.fechaTratamiento = rs.getDate("fecha_tratamiento");
                cert.fechaVencimiento = rs.getDate("fecha_vencimiento");
                lista.add(cert);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return lista;
    }
    
    public boolean crear() {
        String sql = "INSERT INTO certificados (cliente_id, fecha_tratamiento, fecha_vencimiento) " +
                     "VALUES (?, ?, ?)";
        try (Connection conn = ConexionDB.conectar();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, clienteId);
            stmt.setDate(2, new java.sql.Date(fechaTratamiento.getTime()));
            stmt.setDate(3, new java.sql.Date(fechaVencimiento.getTime()));

            return stmt.executeUpdate() > 0;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean actualizar() {
        String sql = "UPDATE certificados SET cliente_id = ?, fecha_tratamiento = ?, fecha_vencimiento = ? WHERE certificado_id = ?";
        try (Connection conn = ConexionDB.conectar();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, clienteId);
            stmt.setDate(2, new java.sql.Date(fechaTratamiento.getTime()));
            stmt.setDate(3, new java.sql.Date(fechaVencimiento.getTime()));
            stmt.setInt(10, certificadoId);

            return stmt.executeUpdate() > 0;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
}